/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.statement;

import oolang.ast.AstNode;

public sealed interface StatementNode extends Statement, AstNode permits Block, RealStatement {
}
