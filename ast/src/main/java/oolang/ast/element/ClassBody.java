/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.element;

import oolang.ast.Ast;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public record ClassBody(@Override @NonNull List<@NonNull Ast> content) implements ElementNode {
    @Override
    public @NonNull String description() {
        return "ClassBody";
    }

    public static final class Builder {
        private final @NonNull List<@NonNull RealElement> children = new ArrayList<>();

        public @NonNull Builder addChild(final @NonNull RealElement child) {
            children.add(child);
            return this;
        }

        public @NonNull ClassBody build() {
            return new ClassBody(List.copyOf(children));
        }
    }
}
