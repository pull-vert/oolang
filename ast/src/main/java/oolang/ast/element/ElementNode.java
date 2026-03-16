/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.element;

import oolang.ast.AstNode;

public sealed interface ElementNode extends Element, AstNode
        permits ElementAnnotation, ElementDeclaration, ElementIdentifier {
}
