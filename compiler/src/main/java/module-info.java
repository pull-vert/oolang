/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

module oolang.compiler {
    requires transitive oolang.ast;

    requires static org.jspecify;

    exports oolang.compiler;
}
