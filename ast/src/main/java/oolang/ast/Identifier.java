/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Identifier implements Ast {
    public final @NonNull String identifier;
    public final @NonNull List<@NonNull Type> parameters = new ArrayList<>();

    public Identifier(final @NonNull String identifier) {
        this.identifier = Objects.requireNonNull(identifier);
    }

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
}
