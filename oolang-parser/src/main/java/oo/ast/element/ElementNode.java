package oo.ast.element;

import oo.ast.AstNode;

public sealed interface ElementNode extends Element, AstNode
        permits ElementAnnotation, ElementDeclaration, ElementIdentifier {
}
