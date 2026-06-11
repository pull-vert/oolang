/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.compiler;

import oolang.ast.FileAst;
import oolang.parser.OolangAstVisitor;
import oolang.parser.generated.OolangLexer;
import oolang.parser.generated.OolangParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.reflect.AccessFlag;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class OolangCompilerTest {
    @TempDir
    public Path tempDir;

    @Test
    public void compileSimplestClass() {
        var fileAst = astForCode("""
                package com.example
                class Example""");
        var classModel = compileAst(fileAst);
        assertThat(classModel.minorVersion()).isEqualTo(0);
        assertThat(classModel.majorVersion()).isEqualTo(69);
        assertThat(classModel.flags().flags()).containsExactlyInAnyOrder(
                AccessFlag.PUBLIC,
                AccessFlag.FINAL
        );
        assertThat(classModel.thisClass().asInternalName()).isEqualTo("com/example/Example");
        assertThat(classModel.superclass()).hasValueSatisfying(superClass ->
                assertThat(superClass.asInternalName()).isEqualTo("java/lang/Object"));
        assertThat(classModel.interfaces()).isEmpty();
        assertThat(classModel.fields()).isEmpty();
        assertThat(classModel.methods()).isEmpty();
    }

    @Test
    public void compileSimplestClassWithMainFunction() {
        var fileAst = astForCode("""
                package com.example
                class Example {
                static fun main(args: Array<java.lang.String>) {
                java.lang.System.out.println("Hello, World!")
                }
                }""");
        var classModel = compileAst(fileAst);
        assertThat(classModel.minorVersion()).isEqualTo(0);
        assertThat(classModel.majorVersion()).isEqualTo(69);
        assertThat(classModel.flags().flags()).containsExactlyInAnyOrder(
                AccessFlag.PUBLIC,
                AccessFlag.FINAL
        );
        assertThat(classModel.thisClass().asInternalName()).isEqualTo("com/example/Example");
        assertThat(classModel.superclass()).hasValueSatisfying(superClass ->
                assertThat(superClass.asInternalName()).isEqualTo("java/lang/Object"));
        assertThat(classModel.interfaces()).isEmpty();
        assertThat(classModel.fields()).isEmpty();
        assertThat(classModel.methods()).hasSize(1);
        var methodModel = classModel.methods().getFirst();
        assertThat(methodModel.flags().flags()).containsExactlyInAnyOrder(
                AccessFlag.PUBLIC,
                AccessFlag.STATIC,
                AccessFlag.FINAL
        );
        assertThat(methodModel.methodName().equalsString("main")).isTrue();
        assertThat(methodModel.methodTypeSymbol().descriptorString()).isEqualTo("([Ljava/lang/String;)V");
    }

    private @NonNull ClassModel compileAst(FileAst ast) {
        assertThat(ast.rootElements).hasSize(1);
        var classPath = OolangCompiler.compile(ast.packageHeader, ast.imports, ast.rootElements.getFirst(), tempDir);
        var classFile = ClassFile.of();
        try {
            return classFile.parse(classPath);
        } catch (IOException e) {
            fail(e);
            return null;
        }
    }

    private static FileAst astForCode(String code) {
        var parser = new OolangParser(new CommonTokenStream(new OolangLexer(CharStreams.fromString(code))));
        var oolangFile = parser.oolangFile();
        var visitor = new OolangAstVisitor();
        return visitor.visitOolangFile(oolangFile);
    }
}
