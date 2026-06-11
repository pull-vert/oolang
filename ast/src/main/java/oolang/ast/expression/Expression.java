/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.expression;

import oolang.ast.Ast;

import java.lang.invoke.TypeDescriptor;

public sealed interface Expression extends Ast, TypeDescriptor
        permits ConstantExpression, ExpressionNode, LoadExpression {
}
