/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;

import java.util.List;

public interface AstNode extends Ast {
    @NonNull List<@NonNull Ast> content();
}
