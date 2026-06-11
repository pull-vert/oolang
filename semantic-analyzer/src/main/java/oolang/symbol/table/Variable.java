/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import org.jspecify.annotations.NonNull;

public sealed interface Variable permits BaseSymbol.Property, Variable.SimpleVariable {
    @NonNull Type type();
    boolean isFinal();

    record SimpleVariable(@NonNull Type type, boolean isFinal, int slot) implements Variable {
    }
}
