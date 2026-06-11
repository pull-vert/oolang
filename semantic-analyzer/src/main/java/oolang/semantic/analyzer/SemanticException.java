/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.semantic.analyzer;

import org.jspecify.annotations.NonNull;

public class SemanticException extends RuntimeException {
    public SemanticException(final @NonNull String message) {
        super(message);
    }
}
