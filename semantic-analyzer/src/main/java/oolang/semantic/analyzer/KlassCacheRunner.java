/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.semantic.analyzer;

import oolang.symbol.table.Klass;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class KlassCacheRunner {
    // un-instantiable
    private KlassCacheRunner() {
    }

    public static void run(final @NonNull Runnable op) {
        Objects.requireNonNull(op);
        ScopedValue.where(Klass.KLASSES_CACHE, new ConcurrentHashMap<>()).run(op);
    }
}
