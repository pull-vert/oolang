/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.statement;

import oolang.ast.Annotation;
import oolang.ast.Ast;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record RealStatement(
        @NonNull List<@NonNull Annotation> annotations,
        @NonNull List<@NonNull Ast> children
) implements StatementNode {

    @Override
    public @NonNull String description() {
        return "Statement";
    }

    @Override
    public @NonNull List<@NonNull Ast> content() {
        final var content = new ArrayList<Ast>(annotations);
        content.addAll(children);
        return content;
    }

    public static final class Builder {
        private @Nullable List<@NonNull Annotation> annotations = null;
        private final @NonNull List<@NonNull Ast> children = new ArrayList<>();

        public @NonNull Builder annotations(final @NonNull List<@NonNull Annotation> annotations) {
            this.annotations = Objects.requireNonNull(annotations);
            return this;
        }

        public @NonNull Builder addChild(final @NonNull Ast child) {
            this.children.add(Objects.requireNonNull(child));
            return this;
        }

        public @NonNull RealStatement build() {
            return new RealStatement(
                    (annotations != null) ? List.copyOf(annotations) : List.of(),
                    List.copyOf(children));
        }
    }
}
