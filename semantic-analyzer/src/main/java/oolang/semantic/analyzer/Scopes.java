/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.semantic.analyzer;

import oolang.symbol.table.Variable;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;

final class Scopes {
    @NonNull Scope current = new Scope();

    @NonNull Scopes newScopes() {
        return new Scopes();
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

    @Nullable Variable resolveVariable(final @NonNull String name) {
        var scope = current;
        while (scope != null) {
            final var variable = scope.get(name);
            if (variable != null) {
                return variable;
            }
            scope = scope.previous;
        }
        return null;
    }

    static final class Scope extends HashMap<@NonNull String, @NonNull Variable> {
        private @Nullable Scope previous = null;
    }
}
