/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.semantic.analyzer;

import oolang.ast.AstFile;
import oolang.ast.element.ClassBody;
import oolang.ast.element.RealElement;
import oolang.ast.statement.CodeBlock;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

import static oolang.ast.element.RealElement.ElementType.PARAMETER;

public final class OolangSemanticAnalyzer {
    // un-instantiable
    private OolangSemanticAnalyzer() {
    }

    private static final System.Logger LOGGER = System.getLogger("oolang.semantic.analyzer.OolangSemanticAnalyzer");

    public static void semanticAnalysis(final @NonNull AstFile ast) {
        Objects.requireNonNull(ast);

        // build the symbol table
        final var symbolTable = SymbolTableBuilder.build(ast);

        final var packageHeader = ast.packageHeader;
        final var imports = ast.imports;
        for (final var element : ast.rootElements) {
            final var scopes = new Scopes(packageHeader.raw(), imports);
            switch (element.elementType) {
                case CLASS -> visitClass(element, scopes);
                case INTERFACE -> throw new UnsupportedOperationException();
                default -> throw new IllegalStateException("Unknown element type: " + element.elementType);
            }
        }
    }

    private static void visitClass(final @NonNull RealElement classElement, final @NonNull Scopes scopes) {
        assert classElement != null;
        assert scopes != null;

        for (final var child : classElement.children) {
            if (child instanceof ClassBody classBody) {
                visitClassBody(classBody.children, scopes);
            }
        }
    }

    private static void visitClassBody(final @NonNull List<@NonNull RealElement> children,
                                       final @NonNull Scopes scopes) {
        assert children != null;
        assert scopes != null;

        for (final var child : children) {
            switch (child.elementType) {
                case CLASS -> visitClass(child, scopes);
                case INTERFACE,
                     CONSTRUCTOR,
                     VAR,
                     VAL -> throw new UnsupportedOperationException();
                case FUN -> visitFun(child, scopes);
                default -> throw new IllegalStateException("Unknown element type: " + child.elementType);
            }
        }
    }

    private static void visitFun(final @NonNull RealElement funElement, final @NonNull Scopes scopes) {
        assert funElement != null;
        assert scopes != null;

        final var isAbstract = funElement.modifiers.stream()
                .anyMatch(modifier -> "abstract".equals(modifier.modifier()));

        final var funNode = (!isAbstract) ? scopes.push() : null;
        for (final var child : funElement.children) {
            if (child instanceof RealElement element && element.elementType == PARAMETER) {
                // add param to the scopes if not abstract
                if (!isAbstract) {
                    final var paramName = element.identifier.identifier; // a function parameter has a simple identifier
//                    funNode.put(paramName, new Symbol.Variable(paramName, PRIVATE, Type.));
                }
            } else if (child instanceof CodeBlock blk) {
                break; // this is the last child
            }
        }
    }
}
