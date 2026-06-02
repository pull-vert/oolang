/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static oolang.ast.Identifier.identifierName;

public final class Import implements Ast {
    public final @NonNull List<@NonNull Identifier> identifiers = new ArrayList<>();
    public boolean starProjection = false;
    public @Nullable Identifier alias = null;

    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder("Import(");
        identifierName(identifiers, sb);
        if (starProjection) {
            sb.append(".*");
        } else if (alias != null) {
            sb.append(" as ").append(alias.rawName());
        }
        sb.append(")");
        return sb.toString();
    }
}
