/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.expression;

import org.jspecify.annotations.NonNull;

import java.lang.constant.ConstantDesc;

public final class ConstantExpression implements Expression {
    public final @NonNull ConstantDesc value;
    // set during semantic analysis phase.
    public /* lateinit */ String descriptorString;

    public ConstantExpression(final @NonNull ConstantDesc value) {
        assert value != null;
        this.value = value;
    }

    @Override
    public @NonNull String description() {
        final String printableValue;
        if (value instanceof String string) {
            final var formatted = (string.length() > 64) ? string.substring(0, 64) + "..." : string;
            printableValue = "\"" + formatted + "\"";
        } else {
            printableValue = value.toString();
        }
        return "ConstantExpression(" + printableValue + ")";
    }

    @Override
    public @NonNull String descriptorString() {
        return descriptorString;
    }
}
