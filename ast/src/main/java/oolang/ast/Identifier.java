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
        @NonNull List<@NonNull Type> parameters
) implements Ast {
    public @NonNull String rawName() {
        final var sb = new StringBuilder();
        sb.append(identifier);
        if (!parameters.isEmpty()) {
            sb.append("<");
            for (var i = 0; i < parameters.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(parameters.get(i).rawName());
            }
            sb.append(">");
        }
        return sb.toString();
    }

    @Override
    public @NonNull String description() {
        return "Identifier(" + rawName() + ")";
    }

    public static final class Builder {
        private final @NonNull String identifier;
        private final @NonNull List<@NonNull Type> parameters = new ArrayList<>();

        public Builder(final @NonNull String identifier) {
            this.identifier = Objects.requireNonNull(identifier);
        }

        public @NonNull Builder addParameter(final @NonNull Type parameter) {
            this.parameters.add(Objects.requireNonNull(parameter));
            return this;
        }

        public @NonNull Identifier build() {
            return new Identifier(
                    identifier,
                    List.copyOf(parameters));
        }
    }
}
