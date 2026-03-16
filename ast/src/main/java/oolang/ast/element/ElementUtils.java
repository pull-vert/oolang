/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.element;

import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.stream.Collectors;

final class ElementUtils {
    // un-instantiable
    private ElementUtils() {
    }

    static String identifierName(final @NonNull List<@NonNull ElementIdentifier> list) {
        return list.stream()
                .map(ElementIdentifier::rawName)
                .collect(Collectors.joining("."));
    }
}
