/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.lang.invoke.TypeDescriptor;
import java.util.concurrent.ConcurrentHashMap;

public sealed interface Klass extends TypeDescriptor.OfField<Klass>, Type
        permits Array, ExistingKlass, OolangKlass {

    @Nullable Type accessibleProperty(final @NonNull String name);

    /**
     * The cache of all resolved {@linkplain Klass classes}.
     */
    @NonNull ScopedValue<@NonNull ConcurrentHashMap<@NonNull String, @NonNull Klass>> KLASSES_CACHE =
            ScopedValue.newInstance();

    @Override
    default boolean isPrimitive() {
        throw new UnsupportedOperationException("We don't need this method");
    }

    @Override
    default boolean isArray() {
        throw new UnsupportedOperationException("We don't need this method");
    }

    @Override
    default @Nullable Klass componentType() {
        throw new UnsupportedOperationException("We don't need this method");
    }
}
