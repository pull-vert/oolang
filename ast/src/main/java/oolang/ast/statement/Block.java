/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.statement;

import oolang.ast.Ast;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class Block implements StatementNode {
    public final @NonNull List<@NonNull Statement> children = new ArrayList<>();

    @Override
    public @NonNull String description() {
        return "Block";
    }

    @Override
    public @NonNull List<? extends @NonNull Ast> content() {
        return children;
    }
}
