/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Type(
        @NonNull List<@NonNull Identifier> identifiers,
        @NonNull List<@NonNull Annotation> annotations,
        boolean nullable,
        @NonNull List<@NonNull Ast> children
) implements AstNode {
    public @NonNull String rawName() {
        final var sb = new StringBuilder();
        for (var i = 0; i < identifiers.size(); i++) {
            if (i > 0) {
                sb.append(".");
            }
            sb.append(identifiers.get(i).rawName());
        }
        if (nullable) {
            sb.append("?");
        }
        return sb.toString();
    }

    @Override
    public @NonNull String description() {
        return "Type(" + rawName() + ")";
    }

    @Override
    public @NonNull List<@NonNull Ast> content() {
        final var content = new ArrayList<Ast>(annotations);
        content.addAll(children);
        return content;
    }

    public static final class Builder {
        private final @NonNull List<@NonNull Identifier> identifier = new ArrayList<>();
        private boolean nullable = false;
        private @Nullable List<@NonNull Annotation> annotations = null;
        private final @NonNull List<@NonNull Ast> children = new ArrayList<>();

        public @NonNull Builder addIdentifier(final @NonNull Identifier identifier) {
            this.identifier.add(Objects.requireNonNull(identifier));
            return this;
        }

        public @NonNull Builder annotations(final @NonNull List<@NonNull Annotation> annotations) {
            this.annotations = Objects.requireNonNull(annotations);
            return this;
        }

        public @NonNull Type build() {
            return new Type(
                    identifier,
                    (annotations != null) ? List.copyOf(annotations) : List.of(),
                    nullable,
                    List.copyOf(children));
        }
    }
}
