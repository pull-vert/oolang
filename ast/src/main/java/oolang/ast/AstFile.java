/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import oolang.ast.element.RealElement;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public final class AstFile implements AstNode {
    public /* lateinit */ PackageHeader packageHeader;
    public final @NonNull List<@NonNull Import> imports = new ArrayList<>();
    public final @NonNull List<@NonNull RealElement> rootElements = new ArrayList<>();

    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder();
        new AstFileWriter(this).write(sb);
        return sb.toString();
    }

    @Override
    public @NonNull List<? extends @NonNull Ast> content() {
        final var content = new ArrayList<Ast>();
        content.add(packageHeader);
        content.addAll(imports);
        content.addAll(rootElements);
        return content;
    }
}
