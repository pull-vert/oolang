/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.parser;

import oolang.ast.AstFile;
import oolang.ast.AstFileWriter;
import oolang.ast.element.ClassBody;
import oolang.ast.element.ElementModifier;
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

import static oolang.ast.element.RealElement.ElementType.*;
import static oolang.ast.expression.RealExpression.ExpressionType.FUN_CALL;
import static org.assertj.core.api.Assertions.assertThat;

public class OolangAstVisitorTest {
    @Test
    public void parseSimplestClass() {
        var astFile = astForCode("""
                package com.example
                class Example""");
        verifyPackage(astFile);
        var imports = astFile.imports;
        assertThat(imports).isEmpty();
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.modifiers).isEmpty();
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        print(astFile);
    }

    @Test
    public void parseFinalClass() {
        var astFile = astForCode("""
                package com.example
                final class Example""");
        verifyPackage(astFile);
        var imports = astFile.imports;
        assertThat(imports).isEmpty();
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.modifiers).extracting(ElementModifier::modifier)
                .containsExactly("final");
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        print(astFile);
    }

    @Test
    public void parseAbstractClass() {
        var astFile = astForCode("""
                package com.example
                abstract class Example""");
        verifyPackage(astFile);
        var imports = astFile.imports;
        assertThat(imports).isEmpty();
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.modifiers).extracting(ElementModifier::modifier)
                .containsExactly("abstract");
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        print(astFile);
    }

    @Test
    public void parseOpenClass() {
        var astFile = astForCode("""
                package com.example
                open class Example""");
        verifyPackage(astFile);
        var imports = astFile.imports;
        assertThat(imports).isEmpty();
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.modifiers).extracting(ElementModifier::modifier)
                .containsExactly("open");
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        print(astFile);
    }

    @Test
    public void parseClassWithEmptyBody() {
        var astFile = astForCode("""
                package com.example
                class Example {}""");
        verifyPackage(astFile);
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children).hasSize(1);
        assertThat(root.children.getFirst()).isInstanceOf(ClassBody.class);
        print(astFile);
    }

    @Test
    public void parseAnnotatedClassWithImport() {
        var astFile = astForCode("""
                package com.example
                import jakarta.enterprise.inject.Default
                @Default
                class Example""");
        verifyPackage(astFile);
        var imports = astFile.imports;
        assertThat(imports).hasSize(1);
        assertThat(imports.getFirst().description()).isEqualTo("Import(jakarta.enterprise.inject.Default)");
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.annotations).hasSize(1);
        var annotation = root.annotations.getFirst();
        assertThat(annotation.description()).isEqualTo("Annotation(Default)");
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        print(astFile);
    }

    @Test
    public void parseEnumClass() {
        var astFile = astForCode("""
                package com.example
                enum class Example""");
        verifyPackage(astFile);
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.modifiers).extracting(ElementModifier::modifier)
                .containsExactly("enum");
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        print(astFile);
    }

    @Test
    public void parsePublicEnumClass() {
        var astFile = astForCode("""
                package com.example
                public enum class Example""");
        verifyPackage(astFile);
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.modifiers).extracting(ElementModifier::modifier)
                .containsExactly("public", "enum");
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        print(astFile);
    }

    @Test
    public void parseClassWithPrimaryConstructor() {
        var astFile = astForCode("""
                package com.example
                class Example(val foo: String)""");
        verifyPackage(astFile);
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children).hasSize(1);
        var constructor = (RealElement) root.children.getFirst();
        assertThat(constructor.elementType).isEqualTo(CONSTRUCTOR);
        assertThat(constructor.children).hasSize(1);
        var constructorParam = (RealElement) constructor.children.getFirst();
        assertThat(constructorParam.elementType).isEqualTo(VAL);
        assertThat(constructorParam.description()).isEqualTo("Element(val foo:String)");
        print(astFile);
    }

    @Test
    public void parseClassWithUseSiteAnnotatedConstructor() {
        var astFile = astForCode("""
                package com.example
                class Example(@get:JsonIgnore val foo: String)""");
        verifyPackage(astFile);
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children).hasSize(1);
        var constructor = (RealElement) root.children.getFirst();
        assertThat(constructor.children).hasSize(1);
        var constructorParam = (RealElement) constructor.children.getFirst();
        var annotation = constructorParam.annotations.getFirst();
        assertThat(annotation.description()).isEqualTo("Annotation(get:JsonIgnore)");
        print(astFile);
    }

    @Test
    public void parseUseSiteMultipleAnnotatedClass() {
        var astFile = astForCode("""
                package com.example
                class Example(@set:[Inject VisibleForTesting] val foo: String)""");
        verifyPackage(astFile);
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children).hasSize(1);
        var constructor = (RealElement) root.children.getFirst();
        assertThat(constructor.children).hasSize(1);
        var constructorParam = (RealElement) constructor.children.getFirst();
        assertThat(constructorParam.annotations).hasSize(2);
        // Inject
        var annotation = constructorParam.annotations.getFirst();
        assertThat(annotation.description()).isEqualTo("Annotation(set:Inject)");
        // VisibleForTesting
        annotation = constructorParam.annotations.getLast();
        assertThat(annotation.description()).isEqualTo("Annotation(set:VisibleForTesting)");
        print(astFile);
    }

    @Test
    public void symbolTableOfInnerClassWithEmptyBody() { // exact same behavior as the simplest above
        var astFile = astForCode("""
                package com.example
                class Example {
                class Inner
                }""");
        verifyPackage(astFile);
        var imports = astFile.imports;
        assertThat(imports).isEmpty();
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.modifiers).isEmpty();
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children).hasSize(1);
        assertThat(root.children.getFirst()).isInstanceOf(ClassBody.class);
        var classBody = (ClassBody) root.children.getFirst();
        assertThat(classBody.content()).hasSize(1);
        var innerClass = (RealElement) classBody.content().getFirst();
        assertThat(innerClass.elementType).isEqualTo(CLASS);
        assertThat(innerClass.modifiers).isEmpty();
        assertThat(innerClass.identifier).isNotNull();
        assertThat(innerClass.identifier.identifier).isEqualTo("Inner");
        assertThat(innerClass.type).isNull();
        assertThat(innerClass.children).isEmpty();
        print(astFile);
    }

    @Test
    public void symbolTableOfClassWithProperty() {
        var astFile = astForCode("""
                package com.example
                class Example {
                val foo: String
                }""");
        verifyPackage(astFile);
        var imports = astFile.imports;
        assertThat(imports).isEmpty();
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.modifiers).isEmpty();
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children).hasSize(1);
        assertThat(root.children.getFirst()).isInstanceOf(ClassBody.class);
        var classBody = (ClassBody) root.children.getFirst();
        assertThat(classBody.content()).hasSize(1);
        var property = (RealElement) classBody.content().getFirst();
        assertThat(property.elementType).isEqualTo(VAL);
        assertThat(property.modifiers).isEmpty();
        assertThat(property.identifier).isNotNull();
        assertThat(property.identifier.identifier).isEqualTo("foo");
        assertThat(property.type).isNotNull();
        assertThat(property.type.description()).isEqualTo("Type(String)");
        assertThat(property.children).isEmpty();
        print(astFile);
    }

    @Test
    public void parseClassWithSimplestFunction() {
        var astFile = astForCode("""
                package com.example
                class Example {
                fun foo() {}
                }""");
        verifyPackage(astFile);
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children).hasSize(1);
        assertThat(root.children.getFirst()).isInstanceOf(ClassBody.class);
        var classBody = (ClassBody) root.children.getFirst();
        assertThat(classBody.content()).hasSize(1);
        var funDeclaration = (RealElement) classBody.content().getFirst();
        assertThat(funDeclaration.elementType).isEqualTo(FUN);
        assertThat(funDeclaration.identifier).isNotNull();
        assertThat(funDeclaration.identifier.identifier).isEqualTo("foo");
        assertThat(funDeclaration.type).isNull();
        assertThat(funDeclaration.children).hasSize(1);
        assertThat(funDeclaration.children.getFirst()).isInstanceOf(CodeBlock.class);
        print(astFile);
    }

    @Test
    public void parseClassWithAnnotatedFunction() {
        var astFile = astForCode("""
                package com.example
                class Example {
                @Test
                fun foo() {}
                }""");
        verifyPackage(astFile);
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children).hasSize(1);
        assertThat(root.children.getFirst()).isInstanceOf(ClassBody.class);
        var classBody = (ClassBody) root.children.getFirst();
        assertThat(classBody.content()).hasSize(1);
        var funDeclaration = (RealElement) classBody.content().getFirst();
        assertThat(funDeclaration.elementType).isEqualTo(FUN);
        assertThat(funDeclaration.identifier).isNotNull();
        assertThat(funDeclaration.identifier.identifier).isEqualTo("foo");
        assertThat(funDeclaration.type).isNull();
        var annotation = funDeclaration.annotations.getFirst();
        assertThat(annotation.description()).isEqualTo("Annotation(Test)");
        assertThat(funDeclaration.children.getFirst()).isInstanceOf(CodeBlock.class);
        print(astFile);
    }

    @Test
    public void parseClassWithFunctionWithParameter() {
        var astFile = astForCode("""
                package com.example
                class Example {
                fun foo(bar: String) {}
                }""");
        verifyPackage(astFile);
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children).hasSize(1);
        var classBody = (ClassBody) root.children.getFirst();
        assertThat(classBody.content()).hasSize(1);
        var funDeclaration = (RealElement) classBody.content().getFirst();
        assertThat(funDeclaration.description()).isEqualTo("Element(fun foo)");
        assertThat(funDeclaration.elementType).isEqualTo(FUN);
        assertThat(funDeclaration.identifier).isNotNull();
        assertThat(funDeclaration.identifier.identifier).isEqualTo("foo");
        assertThat(funDeclaration.type).isNull();
        assertThat(funDeclaration.children).hasSize(2);
        var funParam = (RealElement) funDeclaration.children.getFirst();
        assertThat(funParam.elementType).isEqualTo(PARAMETER);
        assertThat(funParam.description()).isEqualTo("Element(parameter bar:String)");
        assertThat(funDeclaration.children.getLast()).isInstanceOf(CodeBlock.class);
        print(astFile);
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
        verifyPackage(astFile);
        var root = astFile.rootElements.getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType).isEqualTo(CLASS);
        assertThat(root.identifier).isNotNull();
        assertThat(root.identifier.identifier).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children).hasSize(1);
        var classBody = (ClassBody) root.children.getFirst();
        assertThat(classBody.content()).hasSize(1);
        assertThat(classBody.content()).hasSize(1);
        var funDeclaration = (RealElement) classBody.content().getFirst();
        assertThat(funDeclaration.description()).isEqualTo("Element(fun main)");
        assertThat(funDeclaration.elementType).isEqualTo(FUN);
        assertThat(funDeclaration.identifier).isNotNull();
        assertThat(funDeclaration.identifier.identifier).isEqualTo("main");
        assertThat(funDeclaration.type).isNull();
        assertThat(funDeclaration.modifiers).extracting(ElementModifier::modifier)
                .containsExactly("static");
        assertThat(funDeclaration.children).hasSize(2);
        var funParam = (RealElement) funDeclaration.children.getFirst();
        assertThat(funParam.elementType).isEqualTo(PARAMETER);
        assertThat(funParam.description()).isEqualTo("Element(parameter args:Array<String>)");
        assertThat(funDeclaration.children.getLast()).isInstanceOf(CodeBlock.class);
        var codeBlock = (CodeBlock) funDeclaration.children.getLast();
        assertThat(codeBlock.description()).isEqualTo("CodeBlock");
        assertThat(codeBlock.content()).hasSize(1);
        var statement = (RealStatement) codeBlock.content().getFirst();
        assertThat(statement.description()).isEqualTo("Statement");
        assertThat(statement.content()).hasSize(1);
        var funCall = (RealExpression) statement.content().getFirst();
        assertThat(funCall.description()).isEqualTo("Expression(funCall System.out.println)");
        assertThat(funCall.type).isEqualTo(FUN_CALL);
        assertThat(funCall.content()).hasSize(1);
        var funCallParam = (RealExpression) funCall.content().getFirst();
        assertThat(funCallParam.description()).isEqualTo("Expression(argument)");
        assertThat(funCallParam.content()).hasSize(1);
        var funCallParamString = (SimpleString) funCallParam.content().getFirst();
        assertThat(funCallParamString.description()).isEqualTo("SimpleString(\"Hello, World!\")");
        print(astFile);
    }

    private static AstFile astForCode(String code) {
        var parser = new OolangParser(new CommonTokenStream(new OolangLexer(CharStreams.fromString(code))));
        var oolangFile = parser.oolangFile();
        var visitor = new OolangAstVisitor();
        return visitor.visitOolangFile(oolangFile);
    }

    private static void verifyPackage(AstFile astFile) {
        assertThat(astFile.packageHeader.description()).isEqualTo("PackageHeader(com.example)");
    }

    private static void print(AstFile astFile) {
        System.out.println("AstFile:");
        new AstFileWriter(astFile).write(System.out);
        System.out.println();
    }
}
