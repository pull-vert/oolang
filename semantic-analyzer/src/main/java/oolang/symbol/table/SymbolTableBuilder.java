/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import oolang.ast.AstFile;
import oolang.ast.element.ClassBody;
import oolang.ast.element.RealElement;
import oolang.symbol.table.BaseSymbol.FunctionSymbol;
import oolang.symbol.table.BaseSymbol.FunctionSymbol.Parameter;
import oolang.symbol.table.BaseSymbol.PropertySymbol;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static oolang.ast.element.RealElement.ElementType.PARAMETER;
import static oolang.symbol.table.OolangUtils.buildImports;

public final class SymbolTableBuilder {
    // un-instantiable
    private SymbolTableBuilder() {
    }

    /**
     * @return a virtual {@linkplain OolangKlass class} representing the symbol table of the given {@link AstFile}.
     */
    public static @NonNull OolangKlass build(final @NonNull AstFile ast) {
        assert ast != null;

        final var imports = buildImports(ast.imports);
        final var symbolTable = OolangKlass.createRoot(ast.packageHeader.raw());
        for (final var element : ast.rootElements) {
            switch (element.elementType) {
                case CLASS -> visitClass(element,
                        symbolTable.addKlass(baseSymbol(element, true, true)),
                        imports);
                case INTERFACE -> throw new UnsupportedOperationException();
            }
        }
        return symbolTable;
    }

    private static void visitClass(final @NonNull RealElement classElement,
                                   final @NonNull OolangKlass klass,
                                   final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert classElement != null;
        assert klass != null;
        assert imports != null;

        for (final var child : classElement.children) {
            if (child instanceof ClassBody classBody) {
                visitClassBody(classBody.children, klass, imports);
            }
        }
    }

    private static void visitClassBody(final @NonNull List<@NonNull RealElement> children,
                                       final @NonNull OolangKlass klass,
                                       final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert children != null;
        assert klass != null;
        assert imports != null;

        for (final var child : children) {
            switch (child.elementType) {
                case CLASS -> visitClass(child,
                        klass.addKlass(baseSymbol(child, true, true)),
                        imports);
                case FUN -> visitFun(child, klass, imports);
                case VAR, VAL -> visitProperty(child, klass, imports);
                case INTERFACE, CONSTRUCTOR -> throw new UnsupportedOperationException();
                default -> throw new IllegalStateException("Unknown element lazyType: " + child.elementType);
            }
        }
    }

    private static void visitFun(final @NonNull RealElement funElement,
                                 final @NonNull OolangKlass klass,
                                 final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert funElement != null;
        assert klass != null;
        assert imports != null;

        final List<@NonNull Parameter> parameters;
        if (funElement.children.isEmpty()) {
            parameters = List.of();
        } else {
            parameters = new ArrayList<@NonNull Parameter>();
            for (final var child : funElement.children) {
                if (child instanceof RealElement element && element.elementType == PARAMETER) {
                    assert element.identifier != null;
                    final var paramName = element.identifier.identifier; // a function parameter has a simple identifier
                    assert element.type != null;
                    parameters.add(new Parameter(paramName, new LazyType(element.type, imports)));
                }
            }
        }

        klass.addFunction(new FunctionSymbol(
                baseSymbol(funElement, false, true),
                new LazyType(funElement.type, imports),
                parameters));
    }

    private static void visitProperty(final @NonNull RealElement propertyElement,
                                      final @NonNull OolangKlass klass,
                                      final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert propertyElement != null;
        assert klass != null;
        assert imports != null;

        assert propertyElement.type != null;
        klass.addProperty(new PropertySymbol(
                baseSymbol(propertyElement, false,
                        propertyElement.elementType == RealElement.ElementType.VAL),
                new LazyType(propertyElement.type, imports)));
    }

    private static @NonNull BaseSymbol baseSymbol(final @NonNull RealElement element,
                                                  final boolean defaultStatic,
                                                  final boolean defaultFinal) {
        assert element != null;

        assert element.identifier != null;
        final var name = element.identifier.identifier; // a function or a property has a simple identifier

        Visibility visibility = null;
        var isStatic = defaultStatic; // a class is static by default
        var isAbstract = false;
        var isFinal = defaultFinal;
        for (final var modifier : element.modifiers) {
            switch (modifier.modifier()) {
                case "private" -> visibility = Visibility.PRIVATE;
                case "static" -> isStatic = true;
                case "abstract" -> isAbstract = true;
                case "protected" -> visibility = Visibility.OOLANG_PROTECTED;
                case "open" -> isFinal = false;
                case "inner" -> isStatic = false;
                case "public" -> visibility = Visibility.PUBLIC;
                case "final" -> isFinal = true;
            }
        }

        return new BaseSymbol(
                name, (visibility != null) ? visibility : Visibility.PUBLIC, isStatic, isAbstract, isFinal);
    }
}
