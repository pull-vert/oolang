/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.element;

import oolang.ast.*;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static oolang.ast.Identifier.SimpleIdentifier;

public final class RealElement implements ElementNode {
    public final @NonNull ElementType elementType;
    public /* lateinit */ SimpleIdentifier identifier;
    public @Nullable AstType type;
    public @Nullable List<@NonNull Annotation> annotations = null;
    public @NonNull List<@NonNull ElementModifier> modifiers = new ArrayList<>();
    public @NonNull List<@NonNull AstNode> children = new ArrayList<>();

    public RealElement(final @NonNull ElementType elementType) {
        this.elementType = Objects.requireNonNull(elementType);
    }

    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder();
        sb.append("Element(");
        sb.append(elementType.name().toLowerCase(Locale.US));
        if (identifier != null) {
            sb.append(" ").append(identifier.rawName());
        }
        if (type != null) {
            sb.append(":").append(type.rawName());
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public @NonNull List<@NonNull Ast> content() {
        final var content = new ArrayList<Ast>();
        if (annotations != null) {
            content.addAll(annotations);
        }
        content.addAll(modifiers);
        content.addAll(children);
        return content;
    }

    public enum ElementType {
        CLASS,
        INTERFACE,
        CONSTRUCTOR,
        PARAMETER,
        VAR,
        VAL,
        FUN
    }
}
