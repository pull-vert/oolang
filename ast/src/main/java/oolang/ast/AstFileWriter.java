/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

public final class AstFileWriter {
    private final @NonNull AstFile astFile;

    public AstFileWriter(final @NonNull AstFile astFile) {
        this.astFile = Objects.requireNonNull(astFile);
    }

    public void write(final @NonNull Appendable appendable) {
        Objects.requireNonNull(appendable);

        try {
            for (final var ast : astFile.content()) {
                write(ast, appendable, 0);
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
            for (final var child : astNode.content()) {
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
