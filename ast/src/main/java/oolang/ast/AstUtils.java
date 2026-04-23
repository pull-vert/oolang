/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public final class AstUtils {
    // un-instantiable
    private AstUtils() {
    }

    public static @NonNull String identifierName(final @NonNull List<@NonNull Identifier> list) {
        assert list != null;

        return list.stream()
                .map(Identifier::rawName)
                .collect(Collectors.joining("."));
    }
}
