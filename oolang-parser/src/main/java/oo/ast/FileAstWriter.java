package oo.ast;

import oo.ast.element.ElementDeclaration;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

public final class FileAstWriter {
    private final @NonNull FileAst fileAst;

    public FileAstWriter(final @NonNull FileAst fileAst) {
        this.fileAst = Objects.requireNonNull(fileAst);
    }

    public void write(final @NonNull Appendable appendable) {
        try {
            for (var element : fileAst.rootElements()) {
                write(element, appendable, 0);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void write(final @NonNull Ast ast,
                       final @NonNull Appendable appendable,
                       final int depth) throws IOException {
        assert ast != null;
        assert appendable != null;

        indent(appendable, depth);
        appendable.append(ast.description()).append(System.lineSeparator());
        if (ast instanceof AstNode astNode) {
            for (final var child : astNode.children()) {
                write(child, appendable, depth + 1);
            }
        }
    }

    private static void indent(final @NonNull Appendable appendable, final int depth) throws IOException {
        assert appendable != null;
        for (var i = 0; i < depth; i++) {
            appendable.append("  ");
        }
    }
}
