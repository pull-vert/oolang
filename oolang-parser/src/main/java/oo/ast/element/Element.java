package oo.ast.element;

import oo.ast.Ast;

public sealed interface Element extends Ast permits ElementModifier, ElementNode {
}
