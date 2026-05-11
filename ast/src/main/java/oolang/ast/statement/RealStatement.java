/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.statement;

import oolang.ast.Annotation;
import oolang.ast.Ast;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class RealStatement implements StatementNode {
    public @Nullable List<@NonNull Annotation> annotations = null;
    public @NonNull List<@NonNull Ast> children = new ArrayList<>();

    @Override
    public @NonNull String description() {
        return "Statement";
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
