/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import oolang.ast.AstType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

import static oolang.symbol.table.Type.GenericType;
import static oolang.symbol.table.TypeResolver.resolveType;

@SuppressWarnings("preview")
final class LazyType {
    private final @NonNull LazyConstant<@NonNull Type> lazyType;

    LazyType(final @Nullable AstType astType, final @NonNull Map<String, String> imports) {
        assert imports != null;

        lazyType = LazyConstant.of(() -> {
            final var type = resolveType(astType, imports, false);

            // type resolution, the compiler will use that descriptor string
            if (astType != null) {
                astType.descriptorString = switch (type) {
                    case Klass klass -> klass.descriptorString();
                    case GenericType genericType -> genericType.klass().descriptorString();
                };
            }

            return type;
        });
    }

    @NonNull Type type() {
        return lazyType.get();
    }
}
