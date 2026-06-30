/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

import static oolang.ast.Identifier.SimpleIdentifier;
import static oolang.ast.Identifier.identifierName;

public final class PackageHeader implements Ast {
    public final @NonNull List<@NonNull SimpleIdentifier> identifiers = new ArrayList<>();

    public @NonNull String raw() {
        final var sb = new StringBuilder();
        identifierName(identifiers, sb);
        return sb.toString();
    }

    @Override
    public @NonNull String description() {
        return "PackageHeader(" + raw() + ")";
    }
}
