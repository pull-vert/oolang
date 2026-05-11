/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast.element;

import oolang.ast.Ast;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class ClassBody implements ElementNode {
    public final @NonNull List<@NonNull RealElement> children = new ArrayList<>();

    @Override
    public @NonNull String description() {
        return "ClassBody";
    }

    @Override
    public @NonNull List<? extends @NonNull Ast> content() {
        return children;
    }
}