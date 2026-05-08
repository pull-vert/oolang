/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public record RealIdentifier(
        @Override @NonNull String identifier,
        @NonNull List<@NonNull Identifier> parameters,
        @NonNull List<@NonNull Annotation> annotations,
        boolean nullable,
        @Override @NonNull List<@NonNull Ast> content
) implements Identifier, AstNode {
    public @NonNull String rawName() {
        final var sb = new StringBuilder();
        sb.append(identifier);
        if (!parameters.isEmpty()) {
            sb.append("<");
            sb.append(parameters.stream()
                    .map(Identifier::rawName)
                    .collect(Collectors.joining(", ")));
            sb.append(">");
        }
        if (nullable) {
            sb.append("?");
        }
        return sb.toString();
    }
}
