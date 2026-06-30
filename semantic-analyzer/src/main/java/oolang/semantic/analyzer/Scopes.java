/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.semantic.analyzer;

import oolang.ast.Import;
import oolang.symbol.table.Symbol;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

final class Scopes {
    private @Nullable Scopes.Scope head = null;

    private final @NonNull String packageHeader;
    private final @NonNull List<@NonNull Import> imports;

    Scopes(final @NonNull String packageHeader, final @NonNull List<@NonNull Import> imports) {
        assert packageHeader != null;
        assert imports != null;

        this.packageHeader = packageHeader;
        this.imports = imports;
    }

    public @NonNull Scopes.Scope push() {
        final var newHead = new Scope();
        if (head != null) {
            newHead.previous = head;
        }
        head = newHead;
        return newHead;
    }

    public void pop() {
        final var currentHead = head;
        assert currentHead != null;
        if (currentHead.previous != null) {
            head = currentHead.previous;
            currentHead.previous = null; // release this reference for GC
        } else {
            head = null;
        }
    }

    class Scope extends HashMap<@NonNull String, @NonNull Symbol> {
        private @Nullable Scopes.Scope previous = null;
    }
}
