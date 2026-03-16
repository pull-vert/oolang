package oo.ast;

import org.jspecify.annotations.NonNull;

import java.util.List;

public interface AstNode extends Ast {
    @NonNull List<@NonNull Ast> children();
}
