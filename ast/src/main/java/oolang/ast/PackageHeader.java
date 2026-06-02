/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

import static oolang.ast.Identifier.identifierName;

public final class PackageHeader implements Ast {
    public final @NonNull List<@NonNull Identifier> identifiers = new ArrayList<>();

    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder("PackageHeader(");
        identifierName(identifiers, sb);
        sb.append(")");
        return sb.toString();
    }
}
