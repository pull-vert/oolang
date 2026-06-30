/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import oolang.ast.AstType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

@SuppressWarnings("preview")
final class LazyType {
    private final @NonNull LazyConstant<@NonNull Type> lazyType;

    LazyType(final @Nullable AstType astType, final @NonNull Map<@NonNull String, @NonNull String> imports) {
        assert imports != null;

        lazyType = LazyConstant.of(() -> {
            final var type = TypeResolver.resolveType(astType, imports, false);

            // type resolution, the compiler will use that descriptor string
            if (astType != null) {
                astType.descriptorString = TypeResolver.getKlassFromType(type).descriptorString();
            }

            return type;
        });
    }

    @NonNull Type type() {
        return lazyType.get();
    }
}
