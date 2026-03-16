/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.element;

import oolang.ast.Ast;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public record ElementIdentifier(
        @NonNull String identifier,
        boolean nullable,
        @Override
        @NonNull List<@NonNull Ast> children
) implements ElementNode {
    @NonNull String rawName() {
        final var sb = new StringBuilder();
        sb.append(identifier);
        if (nullable) {
            sb.append("?");
        }
        return sb.toString();
    }

    @Override
    public @NonNull String description() {
        return "ElementIdentifier(" + rawName() + ")";
    }

    public static final class Builder {
        private final @NonNull String identifier;
        private boolean nullable = false;
        private final @NonNull List<@NonNull Ast> children = new ArrayList<>();

        public Builder(final @NonNull String identifier) {
            this.identifier = identifier;
        }

        public @NonNull ElementIdentifier build() {
            return new ElementIdentifier(identifier, nullable, List.copyOf(children));
        }
    }
}
