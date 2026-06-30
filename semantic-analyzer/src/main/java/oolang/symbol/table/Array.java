/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * The Array pseudo-class represents an array type in the Oolang language.
 */
enum Array implements Klass {
    INSTANCE;

    @Override
    public boolean isAssignableFrom(final @NonNull Type otherType) {
        throw new IllegalCallerException("Array is a pseudo-class, don't call it!");
    }

    @Override
    public @Nullable Type accessibleProperty(final @NonNull String name) {
        throw new IllegalCallerException("Array is a pseudo-class, don't call it!");
    }

    @Override
    public @NonNull String descriptorString() {
        throw new IllegalCallerException("Array is a pseudo-class, don't call it!");
    }

    @Override
    public @NonNull Klass arrayType() {
        throw new IllegalCallerException("Array is a pseudo-class, don't call it!");
    }
}
