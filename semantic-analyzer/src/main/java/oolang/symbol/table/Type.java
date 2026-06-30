/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import org.jspecify.annotations.NonNull;

public sealed interface Type permits Klass, Type.GenericType {

    boolean isAssignableFrom(final @NonNull Type otherType);

    record GenericType(@NonNull Klass klass, @NonNull Type @NonNull [] parameters) implements Type {
        @Override
        public boolean isAssignableFrom(final @NonNull Type otherType) {
            assert otherType != null;

            throw new UnsupportedOperationException();
        }
    }
}
