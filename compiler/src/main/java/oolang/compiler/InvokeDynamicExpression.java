/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.compiler;

import oolang.ast.expression.RealExpression;
import org.jspecify.annotations.NonNull;

import java.lang.constant.ConstantDesc;
import java.lang.constant.DirectMethodHandleDesc;
import java.util.ArrayList;
import java.util.List;

import static oolang.ast.expression.JvmInvocationUtils.INVOKE_DYNAMIC;
import static oolang.ast.expression.RealExpression.ExpressionType.FUN_CALL;

final class InvokeDynamicExpression extends RealExpression {
    final @NonNull DirectMethodHandleDesc bootstrapMethod;
    final @NonNull List<@NonNull ConstantDesc> bootstrapArgs = new ArrayList<>();

    InvokeDynamicExpression(final @NonNull DirectMethodHandleDesc bootstrapMethod) {
        assert bootstrapMethod != null;
        super(FUN_CALL);

        this.invocation = INVOKE_DYNAMIC;
        this.bootstrapMethod = bootstrapMethod;
    }
}
