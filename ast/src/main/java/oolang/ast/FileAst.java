/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import oolang.ast.element.ElementDeclaration;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record FileAst(@NonNull List<@NonNull ElementDeclaration> rootElements) {
    public static final class Builder {
        private final @NonNull List<@NonNull ElementDeclaration> rootElements = new ArrayList<>();

        public Builder addRootElement(final @NonNull ElementDeclaration element) {
            rootElements.add(Objects.requireNonNull(element));
            return this;
        }

        public FileAst build() {
            return new FileAst(rootElements);
        }
    }
}
