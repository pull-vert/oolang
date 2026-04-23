/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.element;

import org.jspecify.annotations.NonNull;

import java.util.Locale;

public record ElementModifier(@NonNull ModifierType type, @NonNull String modifier) implements Element {
    @Override
    public @NonNull String description() {
        return "ElementModifier(" + modifier + " [" + type.name().toLowerCase(Locale.US) + "Modifier])";
    }

    public enum ModifierType {
        CLASS,
        INHERITANCE,
        MEMBER,
        PARAMETER,
        VISIBILITY,
    }
}
