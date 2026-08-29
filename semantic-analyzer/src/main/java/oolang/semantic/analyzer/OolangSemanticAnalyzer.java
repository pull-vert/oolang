/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.semantic.analyzer;

import oolang.ast.AstFile;
import oolang.ast.element.ClassBody;
import oolang.ast.element.RealElement;
import oolang.ast.statement.CodeBlock;
import oolang.semantic.analyzer.Scopes.Variable;
import oolang.symbol.table.OolangKlass;
import oolang.symbol.table.SymbolTableBuilder;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

public final class OolangSemanticAnalyzer {
    // un-instantiable
    private OolangSemanticAnalyzer() {
    }

    public static void semanticAnalysis(final @NonNull AstFile ast) {
        Objects.requireNonNull(ast);

        // build the symbol table
        final var symbolTable = SymbolTableBuilder.build(ast);

        final var packageHeader = ast.packageHeader;
        final var imports = ast.imports;
        for (final var element : ast.rootElements) {
            final var scopes = new Scopes(packageHeader.raw(), imports);
            switch (element.elementType) {
                case CLASS -> visitClass(element, scopes, symbolTable.nextKlass());
                case INTERFACE -> throw new UnsupportedOperationException();
                default -> throw new IllegalStateException("Unknown element lazyType: " + element.elementType);
            }
        }
    }

    private static void visitClass(final @NonNull RealElement classElement,
                                   final @NonNull Scopes scopes,
                                   final @NonNull OolangKlass klass) {
        assert classElement != null;
        assert scopes != null;
        assert klass != null;

        for (final var child : classElement.children) {
            if (child instanceof ClassBody classBody) {
                visitClassBody(classBody.children, scopes, klass);
            }
        }
    }

    private static void visitClassBody(final @NonNull List<@NonNull RealElement> children,
                                       final @NonNull Scopes scopes,
                                       final @NonNull OolangKlass klass) {
        assert children != null;
        assert scopes != null;
        assert klass != null;

        for (final var child : children) {
            switch (child.elementType) {
                case CLASS -> visitClass(child, scopes, klass.nextKlass());
                case INTERFACE,
                     CONSTRUCTOR,
                     VAR,
                     VAL -> throw new UnsupportedOperationException();
                case FUN -> visitFun(child, scopes, klass);
                default -> throw new IllegalStateException("Unknown element lazyType: " + child.elementType);
            }
        }
    }

    private static void visitFun(final @NonNull RealElement funElement,
                                 final @NonNull Scopes scopes,
                                 final @NonNull OolangKlass klass) {
        assert funElement != null;
        assert scopes != null;
        assert klass != null;

        final var function = klass.nextFunction();

        final Scopes effectiveScopes = (function.isStatic)
                ? scopes.newScopes() // static function = start a new fresh Scopes
                : scopes;
        if (!function.isAbstract) {
            effectiveScopes.push();
        }

        for (final var parameter : function.parameters) {
            if (!function.isAbstract) {
                effectiveScopes.current.add(parameter.name, new Variable(parameter.type(), true));
            }
        }

        for (final var child : funElement.children) {
            if (child instanceof CodeBlock blk) {
                // todo visit block
                break; // micro-optimization: this is the last child
            }
        }
    }
}
