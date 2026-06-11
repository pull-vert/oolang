/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.expression;

import org.jspecify.annotations.NonNull;

import static oolang.ast.Identifier.SimpleIdentifier;

/**
 * An expression that loads a value from a variable or a field into the operand stack. It is set during the semantic
 * analysis phase.
 */
public record LoadExpression(
        @NonNull SimpleIdentifier identifier,
        int slot,
        @NonNull String descriptorString
) implements Expression {
    @Override
    public @NonNull String description() {
        return "LoadExpression(" + identifier.rawName() + ", " + slot + ")";
    }
}
