/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.element;

import oolang.ast.Annotation;
import oolang.ast.Ast;
import oolang.ast.AstNode;
import oolang.ast.Identifier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static oolang.ast.AstUtils.identifierName;

public record RealElement(
        @NonNull ElementType elementType,
        @Nullable Identifier identifier,
        @NonNull List<@NonNull Identifier> type,
        @NonNull List<@NonNull Annotation> annotations,
        @NonNull List<@NonNull ElementModifier> modifiers,
        @NonNull List<@NonNull AstNode> children
) implements ElementNode {

    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder();
        sb.append("Element(");
        sb.append(elementType.name().toLowerCase(Locale.US));
        if (identifier != null) {
            sb.append(" ").append(identifier.rawName());
        }
        if (!type.isEmpty()) {
            sb.append(":").append(identifierName(type));
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public @NonNull List<@NonNull Ast> content() {
        final var content = new ArrayList<Ast>(annotations);
        content.addAll(modifiers);
        content.addAll(children);
        return content;
    }

    public static final class Builder {
        private ElementType elementType = null;
        private @Nullable Identifier identifier = null;
        private @NonNull List<@NonNull Identifier> type = new ArrayList<>();
        private @Nullable List<@NonNull Annotation> annotations = null;
        private final @NonNull List<@NonNull ElementModifier> modifiers = new ArrayList<>();
        private final @NonNull List<@NonNull AstNode> children = new ArrayList<>();

        public @NonNull Builder elementType(final @NonNull ElementType type) {
            this.elementType = Objects.requireNonNull(type);
            return this;
        }

        public @NonNull Builder identifier(final @NonNull Identifier identifier) {
            this.identifier = Objects.requireNonNull(identifier);
            return this;
        }

        public @NonNull Builder type(final @NonNull List<@NonNull Identifier> type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public @NonNull Builder annotations(final @NonNull List<@NonNull Annotation> annotations) {
            this.annotations = Objects.requireNonNull(annotations);
            return this;
        }

        public @NonNull Builder addModifier(final @NonNull ElementModifier modifier) {
            this.modifiers.add(Objects.requireNonNull(modifier));
            return this;
        }

        public @NonNull Builder addChild(final @NonNull AstNode child) {
            this.children.add(Objects.requireNonNull(child));
            return this;
        }

        public @NonNull RealElement build() {
            Objects.requireNonNull(elementType, "type must be set");
            return new RealElement(
                    elementType,
                    identifier,
                    List.copyOf(type),
                    (annotations != null) ? List.copyOf(annotations) : List.of(),
                    List.copyOf(modifiers),
                    List.copyOf(children));
        }
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
