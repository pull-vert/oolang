/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record Identifier(
        @NonNull String identifier,
        boolean nullable,
        @Override @NonNull List<@NonNull Ast> content
) implements AstNode {
    public @NonNull String rawName() {
        final var sb = new StringBuilder();
        sb.append(identifier);
        if (nullable) {
            sb.append("?");
        }
        return sb.toString();
    }

    @Override
    public @NonNull String description() {
        return "Identifier(" + rawName() + ")";
    }

    public static final class Builder {
        private final @NonNull String identifier;
        private boolean nullable = false;
        private final @NonNull List<@NonNull Ast> children = new ArrayList<>();

        public Builder(final @NonNull String identifier) {
            this.identifier = Objects.requireNonNull(identifier);
        }

        public @NonNull Identifier build() {
            return new Identifier(identifier, nullable, List.copyOf(children));
        }
    }
}
