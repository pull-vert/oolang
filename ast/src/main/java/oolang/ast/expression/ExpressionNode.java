/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.expression;

import oolang.ast.AstNode;

public sealed interface ExpressionNode extends Expression, AstNode permits RealExpression {
}
