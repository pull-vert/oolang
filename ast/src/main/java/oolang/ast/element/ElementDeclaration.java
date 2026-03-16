/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.element;

import oolang.ast.Ast;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static oolang.ast.element.ElementUtils.identifierName;

public record ElementDeclaration(
        @NonNull ElementType elementType,
        @Nullable ElementIdentifier identifier,
        @NonNull List<@NonNull ElementIdentifier> type,
        @NonNull List<@NonNull ElementAnnotation> annotations,
        @NonNull List<@NonNull ElementModifier> modifiers,
        @NonNull List<@NonNull Ast> expressions
) implements ElementNode {

    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder();
        sb.append("ElementDeclaration(");
        sb.append(elementType.label);
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
    public @NonNull List<@NonNull Ast> children() {
        final var children = new ArrayList<Ast>(annotations);
        children.addAll(modifiers);
        children.addAll(expressions);
        return children;
    }

    public static final class Builder {
        private ElementType elementType = null;
        private @Nullable ElementIdentifier identifier = null;
        private @NonNull List<@NonNull ElementIdentifier> type = new ArrayList<>();
        private final @NonNull List<@NonNull ElementAnnotation> annotations = new ArrayList<>();
        private final @NonNull List<@NonNull ElementModifier> modifiers = new ArrayList<>();
        private final @NonNull List<@NonNull Ast> expressions = new ArrayList<>();

        public @NonNull Builder elementType(final @NonNull ElementType type) {
            this.elementType = Objects.requireNonNull(type);
            return this;
        }

        public @NonNull Builder identifier(final @NonNull ElementIdentifier identifier) {
            this.identifier = Objects.requireNonNull(identifier);
            return this;
        }

        public @NonNull Builder type(final @NonNull List<@NonNull ElementIdentifier> type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public @NonNull Builder addAnnotation(final @NonNull ElementAnnotation annotation) {
            this.annotations.add(Objects.requireNonNull(annotation));
            return this;
        }

        public @NonNull Builder addModifier(final @NonNull ElementModifier modifier) {
            this.modifiers.add(Objects.requireNonNull(modifier));
            return this;
        }

        public @NonNull Builder addChild(final @NonNull Ast child) {
            this.expressions.add(Objects.requireNonNull(child));
            return this;
        }

        public @NonNull ElementDeclaration build() {
            Objects.requireNonNull(elementType, "type must be set");
            return new ElementDeclaration(
                    elementType,
                    identifier,
                    List.copyOf(type),
                    List.copyOf(annotations),
                    List.copyOf(modifiers),
                    List.copyOf(expressions));
        }
    }

    public enum ElementType {
        CLASS("class"),
        INTERFACE("interface"),
        CONSTRUCTOR("constructor"),
        CLASS_BODY("classBody"),
        PARAMETER("parameter"),
        VAR("var"),
        VAL("val"),
        FUN("fun"),
        FUN_BODY("funBody"),
        // expressions
        FUN_CALL("funCall");

        final @NonNull String label;

        ElementType(final @NonNull String label) {
            assert label != null;
            this.label = label;
        }
    }
}
