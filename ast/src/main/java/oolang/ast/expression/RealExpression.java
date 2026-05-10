/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.expression;

import oolang.ast.Annotation;
import oolang.ast.Ast;
import oolang.ast.Identifier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record RealExpression(
        @NonNull ExpressionType type,
        @NonNull List<@NonNull Identifier> identifiers,
        @NonNull List<@NonNull Annotation> annotations,
        @NonNull List<@NonNull Ast> children
) implements ExpressionNode {
    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder();
        sb.append("Expression(");
        sb.append(type.label);
        if (!identifiers.isEmpty()) {
            sb.append(" ");
            for (var i = 0; i < identifiers.size(); i++) {
                if (i > 0) {
                    sb.append(".");
                }
                sb.append(identifiers.get(i).rawName());
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public @NonNull List<@NonNull Ast> content() {
        final var content = new ArrayList<Ast>(annotations);
        content.addAll(children);
        return content;
    }

    public static final class Builder {
        private ExpressionType type = null;
        private final @NonNull List<@NonNull Identifier> identifiers = new ArrayList<>();
        private @Nullable List<@NonNull Annotation> annotations = null;
        private final @NonNull List<@NonNull Ast> children = new ArrayList<>();

        public @NonNull Builder type(final @NonNull ExpressionType type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public @NonNull Builder addIdentifier(final @NonNull Identifier identifier) {
            this.identifiers.add(Objects.requireNonNull(identifier));
            return this;
        }

        public @NonNull Builder annotations(final @NonNull List<@NonNull Annotation> annotations) {
            this.annotations = Objects.requireNonNull(annotations);
            return this;
        }

        public @NonNull Builder addChild(final @NonNull Ast child) {
            this.children.add(Objects.requireNonNull(child));
            return this;
        }

        public @NonNull RealExpression build() {
            Objects.requireNonNull(type, "type must be set");
            return new RealExpression(
                    type,
                    List.copyOf(identifiers),
                    (annotations != null) ? List.copyOf(annotations) : List.of(),
                    List.copyOf(children));
        }
    }

    public enum ExpressionType {
        FUN_CALL("funCall"),
        ARGUMENT("argument"),
        STRING_LITERAL("stringLiteral");

        private final @NonNull String label;

        ExpressionType(final @NonNull String label) {
            assert label != null;
            this.label = label;
        }
    }
}
