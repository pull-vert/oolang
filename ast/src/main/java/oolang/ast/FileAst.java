/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import oolang.ast.element.RealElement;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record FileAst(@NonNull List<@NonNull RealElement> rootElements) implements Ast {
    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder();
        new FileAstWriter(this).write(sb);
        return sb.toString();
    }

    public static final class Builder {
        private final @NonNull List<@NonNull RealElement> rootElements = new ArrayList<>();

        public Builder addRootElement(final @NonNull RealElement element) {
            rootElements.add(Objects.requireNonNull(element));
            return this;
        }

        public FileAst build() {
            return new FileAst(rootElements);
        }
    }
}
