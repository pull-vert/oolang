/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

@SuppressWarnings("preview")
public final class OolangKlass extends BaseSymbol implements Klass {
    // the root of a symbol table is a virtual klass
    private static final @NonNull BaseSymbol VIRTUAL =
            new BaseSymbol("", Visibility.PUBLIC, false, false, false);

    static @NonNull OolangKlass createRoot(final @NonNull String packageName) {
        return new OolangKlass(VIRTUAL, false, packageName);
    }

    private final boolean isInterface;
    private final @NonNull String fullName;

    // --------------------------------------------
    // Optional properties / functions / subclasses
    // --------------------------------------------

    private final @NonNull Map<@NonNull String, @NonNull Property> properties = new HashMap<>();
    public @NonNull List<@NonNull Function> functions = new ArrayList<>();
    public @Nullable Map<@NonNull String, @NonNull OolangKlass> klasses = null;

    OolangKlass(final @NonNull BaseSymbol baseSymbol,
                final boolean isInterface,
                final @NonNull String prefix) {
        assert baseSymbol != null;
        assert prefix != null;
        super(baseSymbol);

        this.isInterface = isInterface;
        if (baseSymbol.name.isEmpty()) {
            this.fullName = prefix; // the root of a symbol table is a virtual klass, fullName = package
        } else {
            // todo top-level (".") / nested ("$")
            this.fullName = prefix + "." + baseSymbol.name;

            KLASSES_CACHE.get().put(fullName, this);
        }
    }

    @NonNull OolangKlass addKlass(final @NonNull BaseSymbol baseSymbol, final boolean isInterface) {
        assert baseSymbol != null;

        if (klasses == null) {
            klasses = new HashMap<>();
        }
        final var childKlass = new OolangKlass(baseSymbol, isInterface, fullName);
        klasses.put(baseSymbol.name, childKlass);
        return childKlass;
    }

    private final @NonNull LazyConstant<@NonNull SortedSet<@NonNull BaseFunction>> searchableFunctions =
            LazyConstant.of(() -> new TreeSet<>(functions));

    @Override
    public boolean isInterface() {
        return isInterface;
    }

    @Override
    public @NonNull Map<@NonNull String, @NonNull Property> properties() {
        return properties;
    }

    @Override
    public @NonNull SortedSet<@NonNull BaseFunction> functions() {
        return searchableFunctions.get();
    }

    @Override
    public @NonNull String descriptorString() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public @NonNull Klass arrayType() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // todo implement methods below

    @Override
    public @NonNull String packageName() {
        return "";
    }

    @Override
    public @NonNull SequencedCollection<@NonNull Type> genericSuperKlasses() {
        return List.of();
    }

    @Override
    public @NonNull SequencedCollection<@NonNull Type> genericSuperInterfaces() {
        return List.of();
    }
}
