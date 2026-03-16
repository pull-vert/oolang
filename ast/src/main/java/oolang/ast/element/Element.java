/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.element;

import oolang.ast.Ast;

public sealed interface Element extends Ast permits ElementModifier, ElementNode {
}
