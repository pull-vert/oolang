/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import oolang.ast.Import;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class OolangUtils {
    // un-instantiable
    private OolangUtils() {
    }


    static @NonNull Map<@NonNull String, @NonNull String> buildImports(
            final @NonNull List<@NonNull Import> astImports
    ) {
        assert astImports != null;

        final var imports = new HashMap<@NonNull String, @NonNull String>(astImports.size());
        for (final var astImport : astImports) {
            // the key in the map is either the import alias or the last element of its identifiers
            imports.put(
                    (astImport.alias != null) ? astImport.alias.rawName() : astImport.identifiers.getLast().rawName(),
                    astImport.raw());
        }
        return imports;
    }

    static boolean containsUppercase(final @NonNull String string) {
        assert string != null;
        return string.chars().anyMatch(Character::isUpperCase); // todo Unsafe access to underlying byte array
    }
}
