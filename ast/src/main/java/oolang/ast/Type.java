/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class Type implements AstNode {
    public final @NonNull List<@NonNull Identifier> identifiers = new ArrayList<>();
    public @Nullable List<@NonNull Annotation> annotations = null;
    public boolean nullable = false;
    public final @NonNull List<@NonNull Ast> children = new ArrayList<>();

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
        final var content = new ArrayList<Ast>();
        if (annotations != null) {
            content.addAll(annotations);
        }
        content.addAll(children);
        return content;
    }
}
