/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.semantic.analyzer;

import oolang.KlassesCacheInterceptor;
import oolang.ast.AstFile;
import oolang.ast.AstFileWriter;
import oolang.ast.Import;
import oolang.ast.element.ClassBody;
import oolang.ast.element.RealElement;
import oolang.ast.expression.RealExpression;
import oolang.ast.expression.SimpleString;
import oolang.ast.statement.CodeBlock;
import oolang.ast.statement.RealStatement;
import oolang.parser.OolangAstVisitor;
import oolang.parser.generated.OolangLexer;
import oolang.parser.generated.OolangParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;

import static oolang.ast.expression.RealExpression.ExpressionType.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(KlassesCacheInterceptor.class)
public class OolangSemanticAnalyzerTest {

    @Test
    public void symbolTableOfClassWithProperty() {
        var astFile = astForCode("""
                package com.example
                class Example {
                val foo: String
                }""");
        var root = astFile.rootElements.getFirst();
        var classBody = (ClassBody) root.children.getFirst();
        var property = classBody.children.getFirst();
        assertThat(property.type).isNotNull();
        assertThat(property.type.descriptorString).isEqualTo("Ljava/lang/String;");
        assertThat(property.children).isEmpty();
    }

    @Test
    public void parseClassWithSimpleFunction() {
        var astFile = astForCode("""
                package com.example
                import java.math.BigInteger
                class Example {
                fun foo(): BigInteger {}
                }""");
        var root = astFile.rootElements.getFirst();
        var classBody = (ClassBody) root.children.getFirst();
        var funDeclaration = classBody.children.getFirst();
        assertThat(funDeclaration.type).isNotNull();
        assertThat(funDeclaration.type.descriptorString).isEqualTo("Ljava/math/BigInteger;");
    }

    @Test
    public void parseClassWithFunctionWithParameter() {
        var astFile = astForCode("""
                package com.example
                class Example {
                fun foo(bar: Int) {}
                }""");
        var root = astFile.rootElements.getFirst();
        var classBody = (ClassBody) root.children.getFirst();
        var funDeclaration = classBody.children.getFirst();
        assertThat(funDeclaration.type).isNull();
        var funParam = (RealElement) funDeclaration.children.getFirst();
        assertThat(funParam.type).isNotNull();
        assertThat(funParam.type.descriptorString).isEqualTo("I");
    }

    @Test
    public void parseClassWithMainFunction() {
        var astFile = astForCode("""
                package com.example
                class Example {
                static fun main(args: Array<String>) {
                System.out.println("Hello, World!")
                }
                }""");
        var root = astFile.rootElements.getFirst();
        var classBody = (ClassBody) root.children.getFirst();
        var funDeclaration = classBody.children.getFirst();
        assertThat(funDeclaration.type).isNull();
        assertThat(funDeclaration.children).hasSize(2);
        var funParam = (RealElement) funDeclaration.children.getFirst();
        assertThat(funParam.type).isNotNull();
        assertThat(funParam.type.descriptorString).isEqualTo("[Ljava/lang/String;");
        assertThat(funDeclaration.children.getLast()).isInstanceOf(CodeBlock.class);
        var codeBlock = (CodeBlock) funDeclaration.children.getLast();
        var statement = (RealStatement) codeBlock.children.getFirst();
        var funCall = (RealExpression) statement.children.getFirst();
        assertThat(funCall.type).isEqualTo(FUN_CALL);
        assertThat(funCall.children).hasSize(2);
        var propAccess = (RealExpression) funCall.children.getFirst();
        assertThat(propAccess.type).isEqualTo(PROP_ACCESS);
        assertThat(propAccess.invocation).isEqualTo("getStatic");
        assertThat(propAccess.ownerDescriptorString).isEqualTo("Ljava/lang/System;");
        assertThat(propAccess.identifiers).hasSize(1);
        assertThat(propAccess.identifiers.getFirst().identifier).isEqualTo("out");
        assertThat(propAccess.typeDescriptorString).isEqualTo("Ljava/io/PrintStream;");
        assertThat(propAccess.children).isEmpty();
        var funCallParam = (RealExpression) funCall.children.getLast();
        assertThat(funCallParam.type).isEqualTo(FUN_CALL_PARAMETER);
        assertThat(funCallParam.ownerDescriptorString).isNull();
        assertThat(funCallParam.identifiers).isEmpty();
        assertThat(funCallParam.typeDescriptorString).isNull();
        assertThat(funCallParam.children).hasSize(1);
        var funCallParamString = (SimpleString) funCallParam.children.getFirst();
        assertThat(funCallParamString.value()).isEqualTo("Hello, World!");
        assertThat(funCall.invocation).isEqualTo("invokeVirtual");
        assertThat(funCall.ownerDescriptorString).isEqualTo("Ljava/io/PrintStream;");
        assertThat(funCall.identifiers).hasSize(1);
        assertThat(funCall.identifiers.getFirst().identifier).isEqualTo("println");
        assertThat(funCall.typeDescriptorString).isEqualTo("(Ljava/lang/String;)V");
        print(astFile);
    }

    private static AstFile astForCode(String code) {
        var parser = new OolangParser(new CommonTokenStream(new OolangLexer(CharStreams.fromString(code))));
        var oolangFile = parser.oolangFile();
        var visitor = new OolangAstVisitor();
        var astFile = visitor.visitOolangFile(oolangFile);
        OolangSemanticAnalyzer.semanticAnalysis(astFile);
        return astFile;
    }

    /**
     * Expose this package-protected function to other tests
     */
    public static Map<String, String> buildImports(final List<Import> astImports) {
        return OolangSemanticAnalyzer.buildImports(astImports);
    }

    private static void print(AstFile astFile) {
        System.out.println("AstFile:");
        new AstFileWriter(astFile).write(System.out);
        System.out.println();
    }
}
