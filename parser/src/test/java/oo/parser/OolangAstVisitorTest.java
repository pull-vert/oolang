/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.parser;

import oolang.ast.FileAst;
import oolang.ast.FileAstWriter;
import oolang.ast.element.ClassBody;
import oolang.ast.element.RealElement;
import oolang.ast.element.ElementModifier;
import oolang.ast.expression.RealExpression;
import oolang.ast.expression.SimpleString;
import oolang.ast.statement.Block;
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
        var fileAst = astForCode("class Example");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        print(fileAst);
    }

    @Test
    public void parseClass() {
        var fileAst = astForCode("class Example {}");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children()).hasSize(1);
        assertThat(root.children().getFirst()).isInstanceOf(ClassBody.class);
        print(fileAst);
    }

    @Test
    public void parseAnnotatedClass() {
        var fileAst = astForCode("@Service\nclass ExampleTest");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.annotations()).hasSize(1);
        var annotation = root.annotations().getFirst();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("Annotation(Service)");
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("ExampleTest");
        assertThat(root.description()).isEqualTo("Element(class ExampleTest)");
        print(fileAst);
    }

    @Test
    public void parseEnumClass() {
        var fileAst = astForCode("enum class Example");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.modifiers()).extracting(ElementModifier::modifier)
                .containsExactly("enum");
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        print(fileAst);
    }

    @Test
    public void parsePublicEnumClass() {
        var fileAst = astForCode("public enum class Example");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.modifiers()).extracting(ElementModifier::modifier)
                .containsExactly("public", "enum");
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        print(fileAst);
    }

    @Test
    public void parseClassWithPrimaryConstructor() {
        var fileAst = astForCode("class Example(val foo: String)");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children()).hasSize(1);
        var constructor = (RealElement) root.children().getFirst();
        assertThat(constructor.elementType()).isEqualTo(CONSTRUCTOR);
        assertThat(constructor.children()).hasSize(1);
        var constructorParam = (RealElement) constructor.children().getFirst();
        assertThat(constructorParam.elementType()).isEqualTo(VAL);
        assertThat(constructorParam.description()).isEqualTo("Element(val foo:String)");
        print(fileAst);
    }

    @Test
    public void parseClassWithUseSiteAnnotatedConstructor() {
        var fileAst = astForCode("class Example(@get:JsonIgnore val foo: String)");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children()).hasSize(1);
        var constructor = (RealElement) root.children().getFirst();
        assertThat(constructor.children()).hasSize(1);
        var constructorParam = (RealElement) constructor.children().getFirst();
        var annotation = constructorParam.annotations().getFirst();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("Annotation(get:JsonIgnore)");
        print(fileAst);
    }

    @Test
    public void parseUseSiteMultipleAnnotatedClass() {
        var fileAst = astForCode("class Example(@set:[Inject VisibleForTesting] val foo: String)");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children()).hasSize(1);
        var constructor = (RealElement) root.children().getFirst();
        assertThat(constructor.children()).hasSize(1);
        var constructorParam = (RealElement) constructor.children().getFirst();
        assertThat(constructorParam.annotations()).hasSize(2);
        // Inject
        var annotation = constructorParam.annotations().getFirst();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("Annotation(set:Inject)");
        // VisibleForTesting
        annotation = constructorParam.annotations().getLast();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("Annotation(set:VisibleForTesting)");
        print(fileAst);
    }

    @Test
    public void parseClassWithSimplestFunction() {
        var fileAst = astForCode("class Example {\nfun foo() {}\n}");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children()).hasSize(1);
        assertThat(root.children().getFirst()).isInstanceOf(ClassBody.class);
        var classBody = (ClassBody) root.children().getFirst();
        assertThat(classBody.content()).hasSize(1);
        var funDeclaration = (RealElement) classBody.content().getFirst();
        assertThat(funDeclaration.elementType()).isEqualTo(FUN);
        assertThat(funDeclaration.identifier()).isNotNull();
        assertThat(funDeclaration.identifier().identifier()).isEqualTo("foo");
        assertThat(funDeclaration.type()).isEmpty();
        assertThat(funDeclaration.children()).hasSize(1);
        assertThat(funDeclaration.children().getFirst()).isInstanceOf(Block.class);
        print(fileAst);
    }

    @Test
    public void parseClassWithAnnotatedFunction() {
        var fileAst = astForCode("class Example {\n@Test\nfun foo() {}\n}");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children()).hasSize(1);
        assertThat(root.children().getFirst()).isInstanceOf(ClassBody.class);
        var classBody = (ClassBody) root.children().getFirst();
        assertThat(classBody.content()).hasSize(1);
        var funDeclaration = (RealElement) classBody.content().getFirst();
        assertThat(funDeclaration.elementType()).isEqualTo(FUN);
        assertThat(funDeclaration.identifier()).isNotNull();
        assertThat(funDeclaration.identifier().identifier()).isEqualTo("foo");
        assertThat(funDeclaration.type()).isEmpty();
        var annotation = funDeclaration.annotations().getFirst();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("Annotation(Test)");
        assertThat(funDeclaration.children().getFirst()).isInstanceOf(Block.class);
        print(fileAst);
    }

    @Test
    public void parseClassWithFunctionWithParameter() {
        var fileAst = astForCode("class Example {\nfun foo(bar: String) {}\n}");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children()).hasSize(1);
        var classBody = (ClassBody) root.children().getFirst();
        assertThat(classBody.content()).hasSize(1);
        var funDeclaration = (RealElement) classBody.content().getFirst();
        assertThat(funDeclaration.description()).isEqualTo("Element(fun foo)");
        assertThat(funDeclaration.elementType()).isEqualTo(FUN);
        assertThat(funDeclaration.identifier()).isNotNull();
        assertThat(funDeclaration.identifier().identifier()).isEqualTo("foo");
        assertThat(funDeclaration.type()).isEmpty();
        assertThat(funDeclaration.children()).hasSize(2);
        var funParam = (RealElement) funDeclaration.children().getFirst();
        assertThat(funParam.elementType()).isEqualTo(PARAMETER);
        assertThat(funParam.description()).isEqualTo("Element(parameter bar:String)");
        assertThat(funDeclaration.children().getLast()).isInstanceOf(Block.class);
        print(fileAst);
    }

    @Test
    public void parseClassWithMainFunction() {
        var fileAst = astForCode(
                "class Example {\nstatic fun main(args: Array<String>) {\nSystem.out.println(\"Hello, World!\")}\n}");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("Element(class Example)");
        assertThat(root.children()).hasSize(1);
        var classBody = (ClassBody) root.children().getFirst();
        assertThat(classBody.content()).hasSize(1);
        assertThat(classBody.content()).hasSize(1);
        var funDeclaration = (RealElement) classBody.content().getFirst();
        assertThat(funDeclaration.description()).isEqualTo("Element(fun main)");
        assertThat(funDeclaration.elementType()).isEqualTo(FUN);
        assertThat(funDeclaration.identifier()).isNotNull();
        assertThat(funDeclaration.identifier().identifier()).isEqualTo("main");
        assertThat(funDeclaration.type()).isEmpty();
        assertThat(funDeclaration.modifiers()).extracting(ElementModifier::modifier)
                .containsExactly("static");
        assertThat(funDeclaration.children()).hasSize(2);
        var funParam = (RealElement) funDeclaration.children().getFirst();
        assertThat(funParam.elementType()).isEqualTo(PARAMETER);
        assertThat(funParam.description()).isEqualTo("Element(parameter args:Array<String>)");
        assertThat(funDeclaration.children().getLast()).isInstanceOf(Block.class);
        var codeBlock = (Block) funDeclaration.children().getLast();
        assertThat(codeBlock.description()).isEqualTo("Block");
        assertThat(codeBlock.content()).hasSize(1);
        var statement = (RealStatement) codeBlock.content().getFirst();
        assertThat(statement.description()).isEqualTo("Statement");
        assertThat(statement.content()).hasSize(1);
        var funCall = (RealExpression) statement.content().getFirst();
        assertThat(funCall.description()).isEqualTo("Expression(funCall System.out.println)");
        assertThat(funCall.type()).isEqualTo(FUN_CALL);
        assertThat(funCall.content()).hasSize(1);
        var funCallParam = (RealExpression) funCall.content().getFirst();
        assertThat(funCallParam.description()).isEqualTo("Expression(argument)");
        assertThat(funCallParam.content()).hasSize(1);
        var funCallParamString = (SimpleString) funCallParam.content().getFirst();
        assertThat(funCallParamString.description()).isEqualTo("SimpleString(\"Hello, World!\")");
        print(fileAst);
    }

    private static FileAst astForCode(String code) {
        var parser = new OolangParser(new CommonTokenStream(new OolangLexer(CharStreams.fromString(code))));
        var oolangFile = parser.oolangFile();
        var visitor = new OolangAstVisitor();
        return visitor.visitOolangFile(oolangFile);
    }

    private static void print(FileAst fileAst) {
        System.out.println("FileAst:");
        new FileAstWriter(fileAst).write(System.out);
        System.out.println();
    }
}
