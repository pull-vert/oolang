/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.expression;

import oolang.ast.Annotation;
import oolang.ast.Ast;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static oolang.ast.Identifier.SimpleIdentifier;

public non-sealed class RealExpression implements ExpressionNode {
    public /* lateinit */ ExpressionType type;
    public final @NonNull List<@NonNull SimpleIdentifier> identifiers = new ArrayList<>();
    public @Nullable List<@NonNull Annotation> annotations = null;
    public final @NonNull List<@NonNull Expression> children = new ArrayList<>();

    // set during semantic analysis phase.
    public @Nullable String invocation; // getstatic / invokevirtual / invokedynamic etc.
    public @Nullable String ownerDescriptorString;
    public @Nullable String typeDescriptorString;
    public @Nullable String descriptorString;

    public RealExpression() {
    }

    public RealExpression(final @NonNull ExpressionType type) {
        assert type != null;
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
        final var content = new ArrayList<@NonNull Ast>();
        if (annotations != null) {
            content.addAll(annotations);
        }
        content.addAll(children);
        return content;
    }

    @Override
    public @Nullable String descriptorString() {
        return descriptorString;
    }

    public enum ExpressionType {
        PROP_ACCESS("propAccess"),
        FUN_CALL("funCall"),
        FUN_CALL_PARAMETER("funCallParameter"),
        STRING_LITERAL("stringLiteral"),
        ADD("add"),
        SUB("sub"),
        INDEXING("indexing");

        private final @NonNull String label;

        ExpressionType(final @NonNull String label) {
            assert label != null;
            this.label = label;
        }
    }
}
