/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.semantic.analyzer;

import oolang.ast.AstFile;
import oolang.ast.Import;
import oolang.ast.element.ClassBody;
import oolang.ast.element.RealElement;
import oolang.ast.expression.Expression;
import oolang.ast.expression.RealExpression;
import oolang.ast.expression.SimpleString;
import oolang.ast.statement.CodeBlock;
import oolang.ast.statement.RealStatement;
import oolang.symbol.table.BaseSymbol.Function;
import oolang.symbol.table.*;
import org.jspecify.annotations.NonNull;

import java.util.*;

import static oolang.ast.expression.RealExpression.ExpressionType.FUN_CALL_PARAMETER;
import static oolang.ast.expression.RealExpression.ExpressionType.PROP_ACCESS;
import static oolang.symbol.table.TypeResolver.*;
import static oolang.symbol.table.Visibility.PRIVATE;

public final class OolangSemanticAnalyzer {
    // un-instantiable
    private OolangSemanticAnalyzer() {
    }

    public static void semanticAnalysis(final @NonNull AstFile ast) {
        Objects.requireNonNull(ast);

        final var imports = buildImports(ast.imports);

        // build the symbol table
        final var symbolTable = SymbolTableBuilder.build(ast, imports);

        for (final var element : ast.rootElements) {
            final var scopes = new Scopes();
            switch (element.elementType) {
                case CLASS -> {
                    assert symbolTable.klasses != null;
                    visitClass(element, scopes, symbolTable.klasses.get(elementName(element)), imports);
                }
                case INTERFACE -> throw new UnsupportedOperationException("interface");
                default -> throw new IllegalStateException("Unknown element: " + element.elementType);
            }
        }
    }

    private static void visitClass(final @NonNull RealElement classElement,
                                   final @NonNull Scopes scopes,
                                   final @NonNull OolangKlass klass,
                                   final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert classElement != null;
        assert scopes != null;
        assert klass != null;
        assert imports != null;

        final Scopes effectiveScopes = (klass.isStatic)
                ? scopes.newScopes() // static class = start a new fresh Scopes
                : scopes;

        for (final var child : classElement.children) {
            if (child instanceof ClassBody classBody) {
                effectiveScopes.push();
                visitClassBody(classBody.children, scopes, klass, imports);
                effectiveScopes.pop();
            }
        }
    }

    private static void visitClassBody(final @NonNull List<@NonNull RealElement> children,
                                       final @NonNull Scopes scopes,
                                       final @NonNull OolangKlass klass,
                                       final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert children != null;
        assert scopes != null;
        assert klass != null;
        assert imports != null;

        // first loop to resolve all properties
        for (final var child : children) {
            switch (child.elementType) {
                case VAR, VAL -> visitProperty(child, scopes, klass, imports);
            }
        }

        // then loop for everything else
        var funIndex = 0;
        for (final var child : children) {
            switch (child.elementType) {
                case CONSTRUCTOR, FUN -> visitFunction(child, scopes, klass, funIndex++, imports);
                case CLASS -> {
                    assert klass.klasses != null;
                    visitClass(child, scopes, klass.klasses.get(elementName(child)), imports);
                }
                case INTERFACE -> throw new UnsupportedOperationException("Unsupported element: " + child.elementType);
            }
        }
    }

    private static void visitProperty(final @NonNull RealElement propertyElement,
                                      final @NonNull Scopes scopes,
                                      final @NonNull OolangKlass klass,
                                      final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert propertyElement != null;
        assert scopes != null;
        assert klass != null;
        assert imports != null;

        final var property = klass.properties().get(elementName(propertyElement));

        scopes.current.put(property.name, new SimpleVariable(property.type(), property.isFinal()));
    }

    private static void visitFunction(final @NonNull RealElement funElement,
                                      final @NonNull Scopes scopes,
                                      final @NonNull OolangKlass klass,
                                      final int funIndex,
                                      final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert funElement != null;
        assert scopes != null;
        assert klass != null;
        assert imports != null;

        assert klass.functions != null;
        final var function = klass.functions.get(funIndex);
        //noinspection ResultOfMethodCallIgnored
        function.returnType(); // resolve function's return type

        final Scopes effectiveScopes = (function.isStatic)
                ? scopes.newScopes() // static function = start a new fresh Scopes
                : scopes;
        if (!function.isAbstract) {
            effectiveScopes.push();
        }

        for (final var parameter : function.parameters) {
            final var parameterType = parameter.type(); // resolve param type
            if (!function.isAbstract) {
                effectiveScopes.current.put(parameter.name, new SimpleVariable(parameterType, true));
            }
        }

        if (!function.isAbstract) {
            // code block is the last child of a non-abstract function
            visitCodeBlock((CodeBlock) funElement.children.getLast(), effectiveScopes, klass, imports);
            effectiveScopes.pop();
        }
    }

    private static void visitCodeBlock(final @NonNull CodeBlock codeBlock,
                                       final @NonNull Scopes scopes,
                                       final @NonNull OolangKlass klass,
                                       final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert codeBlock != null;
        assert scopes != null;
        assert klass != null;
        assert imports != null;

        for (final var statement : codeBlock.children) {
            switch (statement) {
                case RealStatement realStatement -> visitStatement(realStatement, scopes, klass, imports);
                default -> throw new UnsupportedOperationException("Unsupported statement: " + statement);
            }
        }
    }

    private static void visitStatement(final @NonNull RealStatement statement,
                                       final @NonNull Scopes scopes,
                                       final @NonNull OolangKlass klass,
                                       final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert statement != null;
        assert scopes != null;
        assert klass != null;
        assert imports != null;

        for (final var child : statement.children) {
            switch (child) {
                case Expression expression -> visitExpression(expression, scopes, klass, imports);
                default -> throw new UnsupportedOperationException("Unsupported statement child: " + child);
            }
        }
    }

    private static @NonNull Type visitExpression(final @NonNull Expression expression,
                                                 final @NonNull Scopes scopes,
                                                 final @NonNull OolangKlass klass,
                                                 final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert expression != null;
        assert scopes != null;
        assert klass != null;
        assert imports != null;

        return switch (expression) {
            case RealExpression realExpression -> visitRealExpression(realExpression, scopes, klass, imports);
            case SimpleString _ -> STRING_KLASS;
        };
    }

    private static @NonNull Type visitRealExpression(final @NonNull RealExpression realExpression,
                                                     final @NonNull Scopes scopes,
                                                     final @NonNull OolangKlass klass,
                                                     final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert realExpression != null;
        assert scopes != null;
        assert klass != null;
        assert imports != null;

        return switch (realExpression.type) {
            case FUN_CALL -> visitFunCall(realExpression, scopes, klass, imports);
            default -> throw new UnsupportedOperationException("Unsupported expression type: " + realExpression.type);
        };
    }

    private static @NonNull Type visitFunCall(final @NonNull RealExpression funCallExpression,
                                              final @NonNull Scopes scopes,
                                              final @NonNull OolangKlass klass,
                                              final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert funCallExpression != null;
        assert scopes != null;
        assert klass != null;
        assert imports != null;

        Klass caller;
        if (funCallExpression.identifiers.size() == 1) {
            caller = klass;
        } else {
            var identifier = funCallExpression.identifiers.getFirst().identifier;
            // 1) first identifier may refer to a property from this class's scope
            var variable = scopes.resolveVariable(identifier);
            // 2) else, first identifier may refer to a variable from a parent
            if (variable == null) {
                variable = klass.accessibleProperty(identifier, false);
            }
            if (variable != null) {
                throw new UnsupportedOperationException("Variable resolution not implemented");
            }

            // 3) else, identifier is a static class access
            caller = resolveKlassFromSimpleName(identifier, imports);
            // loop until size - 1, because we must keep the last identifier for function name
            final var lastIdIndex = funCallExpression.identifiers.size() - 1;
            var idIndex = 1;
            while (caller == null && idIndex < lastIdIndex) {
                identifier = identifier + "." + funCallExpression.identifiers.get(idIndex++).identifier;
                caller = getKlassFromName(identifier);
            }
            if (caller == null) {
                // todo semantic exception
                throw new RuntimeException("Could not resolve static klass from identifiers " + identifier);
            }

            var childIndex = 0;
            while (idIndex < lastIdIndex) { // chained properties until last identifier
                final var id = funCallExpression.identifiers.get(idIndex++);
                identifier = id.identifier;
                final var property = caller.accessibleProperty(identifier, true);
                if (property == null) {
                    // todo semantic exception
                    throw new RuntimeException("Could not resolve property " + identifier +
                            " from class " + caller.descriptorString());
                }
                // mutate AST to add property call Expression before fun call parameters
                final var propAccessExpression = new RealExpression(PROP_ACCESS);
                propAccessExpression.identifiers.add(id);
                propAccessExpression.invocation = property.isStatic ? GET_STATIC : GET_FIELD;
                propAccessExpression.ownerDescriptorString = caller.descriptorString();
                propAccessExpression.typeDescriptorString = property.descriptorString();
                funCallExpression.children.add(childIndex++, propAccessExpression);

                // caller becomes property's klass
                caller = getKlassFromType(property.type());
            }

            // only keep the function identifier
            funCallExpression.identifiers.subList(0, lastIdIndex).clear();
        }

        // last step: resolve the function to call and its parameters
        final var funName = funCallExpression.identifiers.getFirst().identifier;
        final var funParameters = new ArrayList<@NonNull Type>();
        for (final var rawFunChild : funCallExpression.children) {
            if (rawFunChild instanceof RealExpression funChild && funChild.type == FUN_CALL_PARAMETER) {
                funParameters.add(visitFunParameter(funChild, scopes, klass, imports));
            }
        }

        var includeProtected = false;
        for (final var superType : klass.genericSuperKlasses()) {
            if (TypeResolver.getKlassFromType(superType).equals(caller)) {
                includeProtected = true;
                break;
            }
        }
        final var function = caller.accessibleFunction(funName, funParameters, includeProtected);
        if (function == null) {
            // todo semantic exception
            throw new RuntimeException("Could not resolve function '" + funName +
                    "' from class " + caller.descriptorString());
        }
        funCallExpression.invocation = functionInvocation(caller, function);
        funCallExpression.ownerDescriptorString = caller.descriptorString();
        funCallExpression.typeDescriptorString = function.descriptorString();
        return function.returnType();
    }

    private static @NonNull Type visitFunParameter(final @NonNull RealExpression funChild,
                                                   final @NonNull Scopes scopes,
                                                   final @NonNull OolangKlass klass,
                                                   final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert funChild != null;
        assert scopes != null;
        assert klass != null;
        assert imports != null;

        if (!funChild.identifiers.isEmpty()) {
            throw new UnsupportedOperationException("Resolution of variable as parameter not implemented");
        }
        // else there is an expression to resolve
        return visitExpression(funChild.children.getFirst(), scopes, klass, imports);
    }

    private static @NonNull String functionInvocation(final @NonNull Klass caller, final @NonNull Function function) {
        assert caller != null;
        assert function != null;

        if (function.isStatic) {
            return INVOKE_STATIC;
        }
        if (function.declaringKlass.isInterface()) {
            return INVOKE_INTERFACE;
        }
        if (function.visibility == PRIVATE
                || !caller.equals(function.declaringKlass)
                || function.name.equals(CONSTRUCTOR_NAME)) {
            return INVOKE_SPECIAL;
        }
        return INVOKE_VIRTUAL;
        // todo INVOKE_DYNAMIC
    }

    static @NonNull Map<@NonNull String, @NonNull String> buildImports(
            final @NonNull List<@NonNull Import> astImports
    ) {
        assert astImports != null;

        final var imports = new HashMap<@NonNull String, @NonNull String>(astImports.size());
        for (final var astImport : astImports) {
            // the key in the map is either the import alias or the last element of its identifiers
            imports.put(
                    (astImport.alias != null) ? astImport.alias.rawName() : astImport.identifiers.getLast().rawName(),
                    astImport.raw());
        }
        return imports;
    }

    private static @NonNull String elementName(final @NonNull RealElement element) {
        assert element != null;

        assert element.identifier != null;
        return element.identifier.identifier; // a function, property or class has a simple identifier
    }

    private record SimpleVariable(@NonNull Type type, boolean isFinal) implements Variable {
    }

    // field access instructions

    private static final @NonNull String GET_STATIC = "getStatic";
    private static final @NonNull String PUT_STATIC = "putStatic";
    private static final @NonNull String GET_FIELD = "getField";
    private static final @NonNull String PUT_FIELD = "putField";


    // function invocation instructions

    /**
     * For static functions.
     */
    private static final @NonNull String INVOKE_STATIC = "invokeStatic";
    /**
     * For non-private, non-static, non-constructor instance functions.
     */
    private static final @NonNull String INVOKE_VIRTUAL = "invokeVirtual";
    /**
     * For private functions, superclass function calls, constructors.
     */
    private static final @NonNull String INVOKE_SPECIAL = "invokeSpecial";
    /**
     * For interface functions (similar to {@link #INVOKE_VIRTUAL} but for interfaces).
     */
    private static final @NonNull String INVOKE_INTERFACE = "invokeInterface";
    /**
     * For lambda expressions.
     */
    private static final @NonNull String INVOKE_DYNAMIC = "invokeDynamic";

    private static final @NonNull String CONSTRUCTOR_NAME = "<init>";
}
