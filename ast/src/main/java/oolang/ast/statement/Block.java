/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.statement;

import oolang.ast.Ast;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public record Block(@Override @NonNull List<@NonNull Ast> content) implements StatementNode {
    @Override
    public @NonNull String description() {
        return "Block";
    }

    public static final class Builder {
        private final @NonNull List<@NonNull Statement> children = new ArrayList<>();

        public @NonNull Builder addChild(final @NonNull Statement child) {
            children.add(child);
            return this;
        }

        public @NonNull Block build() {
            return new Block(List.copyOf(children));
        }
    }
}
