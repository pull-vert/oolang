/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.compiler;

import org.jspecify.annotations.NonNull;

final class Descriptors {
    // un-instantiable
    private Descriptors() {
    }

    // primitive types
    static final @NonNull String INTEGER = "I";
    static final @NonNull String LONG = "J";
    static final @NonNull String FLOAT = "F";
    static final @NonNull String DOUBLE = "D";
    static final @NonNull String BOOLEAN = "Z";
    static final @NonNull String VOID = "V";


    static final @NonNull String STRING = "Ljava/lang/String;";
    static final @NonNull String OBJECT = "Ljava/lang/Object;";
}
