package oo.ast.element;

import org.jspecify.annotations.NonNull;

import java.util.Locale;

public record ElementModifier(@NonNull String modifier, @NonNull ModifierType type) implements Element {
    @Override
    public @NonNull String description() {
        return "ElementModifier(" + modifier + ", " + type.name().toLowerCase(Locale.US) + "Modifier)";
    }

    public enum ModifierType {
        CLASS,
        INHERITANCE,
        MEMBER,
        PARAMETER,
        VISIBILITY,
    }
}
