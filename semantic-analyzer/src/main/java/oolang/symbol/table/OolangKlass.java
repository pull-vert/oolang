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
        return new OolangKlass(VIRTUAL, packageName);
    }

    private final @NonNull String fullName;

    // ---------------------------------------------------------------------------------------------
    // Optional properties / functions / subclasses use LazyConstant to avoid initialization cycles.
    // They are only consumed once for semantic analysis, so we expose the lazy iterators as well.
    // ---------------------------------------------------------------------------------------------

    private final @NonNull LazyConstant<@NonNull Map<@NonNull String, @NonNull PropertySymbol>> properties =
            LazyConstant.of(HashMap::new);
    private final @NonNull LazyConstant<@NonNull Iterator<@NonNull PropertySymbol>> propertiesIterator =
            LazyConstant.of(() -> properties.get().values().iterator());

    private final @NonNull LazyConstant<@NonNull List<@NonNull FunctionSymbol>> functions =
            LazyConstant.of(ArrayList::new);
    private final @NonNull LazyConstant<@NonNull Iterator<@NonNull FunctionSymbol>> functionsIterator =
            LazyConstant.of(() -> functions.get().iterator());

    private final @NonNull LazyConstant<@NonNull Map<@NonNull String, @NonNull OolangKlass>> klasses =
            LazyConstant.of(HashMap::new);
    private final @NonNull LazyConstant<@NonNull Iterator<@NonNull OolangKlass>> klassesIterator =
            LazyConstant.of(() -> klasses.get().values().iterator());

    OolangKlass(final @NonNull BaseSymbol baseSymbol, final @NonNull String prefix) {
        assert baseSymbol != null;
        assert prefix != null;
        super(baseSymbol);

        if (baseSymbol.name.isEmpty()) {
            this.fullName = prefix; // the root of a symbol table is a virtual klass, fullName = package
        } else {
            this.fullName = (OolangUtils.containsUppercase(prefix))
                    ? prefix + "$" + baseSymbol.name // this class is a nested class
                    : prefix + "." + baseSymbol.name; // this class is a top-level class

            KLASSES_CACHE.get().put(fullName, this);
        }
    }

    void addProperty(final @NonNull PropertySymbol property) {
        assert property != null;
        properties.get().put(property.name, property);
    }

    public @NonNull PropertySymbol nextProperty() {
        return propertiesIterator.get().next();
    }

    void addFunction(final @NonNull FunctionSymbol function) {
        assert function != null;
        functions.get().add(function);
    }

    public @NonNull FunctionSymbol nextFunction() {
        return functionsIterator.get().next();
    }

    @NonNull OolangKlass addKlass(final @NonNull BaseSymbol baseSymbol) {
        assert baseSymbol != null;

        final var childKlass = new OolangKlass(baseSymbol, fullName);
        klasses.get().put(baseSymbol.name, childKlass);
        return childKlass;
    }

    public @NonNull OolangKlass nextKlass() {
        return klassesIterator.get().next();
    }

    @Override
    public @Nullable Type accessibleProperty(@NonNull String name) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isAssignableFrom(final @NonNull Type otherType) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public @NonNull String descriptorString() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public @NonNull Klass arrayType() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
