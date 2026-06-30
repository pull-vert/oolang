/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.compiler;

import oolang.ast.AstFile;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.Objects;

public final class OolangCompiler {
    // un-instantiable
    private OolangCompiler() {
    }

    public static void compile(final @NonNull AstFile ast, final @NonNull Path classPath) {
        Objects.requireNonNull(ast);
        Objects.requireNonNull(classPath);


    }
}
