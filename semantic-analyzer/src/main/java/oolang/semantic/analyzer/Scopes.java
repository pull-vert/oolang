/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.semantic.analyzer;

import oolang.ast.Import;
import oolang.symbol.table.Type;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

final class Scopes {
    @NonNull Scope current = new Scope();

    private final @NonNull String packageHeader;
    private final @NonNull List<@NonNull Import> imports;

    Scopes(final @NonNull String packageHeader, final @NonNull List<@NonNull Import> imports) {
        assert packageHeader != null;
        assert imports != null;

        this.packageHeader = packageHeader;
        this.imports = imports;
    }

    @NonNull Scopes newScopes() {
        return new Scopes(packageHeader, imports);
    }

    void push() {
        final var newHead = new Scope();
        newHead.previous = current;
        current = newHead;
    }

    void pop() {
        final var currentHead = current;
        assert currentHead.previous != null;
        current = currentHead.previous;
        currentHead.previous = null; // release this reference for GC
    }

    class Scope extends HashMap<@NonNull String, @NonNull Variable> {
        private @Nullable Scope previous = null;

        public void add(@NonNull String name, final @NonNull Variable variable) {
        }
    }

    record Variable(@NonNull Type type, boolean isConst) {
    }
}
