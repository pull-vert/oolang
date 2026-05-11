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

public final class RealExpression implements ExpressionNode {
    public /* lateinit */ ExpressionType type;
    public @NonNull List<@NonNull Identifier> identifiers = new ArrayList<>();
    public @Nullable List<@NonNull Annotation> annotations = null;
    public @NonNull List<@NonNull Ast> children = new ArrayList<>();

    public RealExpression() {
    }

    public RealExpression(final @NonNull ExpressionType type) {
        this.type = type;
    }

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
        final var content = new ArrayList<Ast>();
        if (annotations != null) {
            content.addAll(annotations);
        }
        content.addAll(children);
        return content;
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
