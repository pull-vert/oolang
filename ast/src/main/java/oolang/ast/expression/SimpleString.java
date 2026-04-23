/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.expression;

import org.jspecify.annotations.NonNull;

public record SimpleString(@NonNull String value) implements Expression {
    @Override
    public @NonNull String description() {
        return "SimpleString(\"" + value + "\")"; // todo limit to X chars
    }
}
