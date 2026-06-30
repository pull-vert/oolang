/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import oolang.symbol.table.BaseSymbol.BaseFunction;
import oolang.symbol.table.BaseSymbol.Property;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.SequencedCollection;
import java.util.SortedSet;

/**
 * The Array pseudo-class represents an array type in the Oolang language.
 */
final class Array implements Klass {
    private static /* lateinit */ Array INSTANCE;

    // only called once by getInstance()
    private Array() {
    }

    static Array getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Array();
        }
        return INSTANCE;
    }

    @Override
    public boolean isInterface() {
        throw new IllegalCallerException("Array is a pseudo-class, don't call it!");
    }

    @Override
    public @NonNull String packageName() {
        throw new IllegalCallerException("Array is a pseudo-class, don't call it!");
    }

    @Override
    public @NonNull SequencedCollection<@NonNull Type> genericSuperKlasses() {
        throw new IllegalCallerException("Array is a pseudo-class, don't call it!");
    }

    @Override
    public @NonNull SequencedCollection<@NonNull Type> genericSuperInterfaces() {
        throw new IllegalCallerException("Array is a pseudo-class, don't call it!");
    }

    @Override
    public @NonNull Map<@NonNull String, @NonNull Property> properties() {
        throw new IllegalCallerException("Array is a pseudo-class, don't call it!");
    }

    @Override
    public @NonNull SortedSet<@NonNull BaseFunction> functions() {
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
