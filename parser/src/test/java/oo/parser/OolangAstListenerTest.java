/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.parser;

import oolang.ast.FileAst;
import oolang.ast.FileAstWriter;
import oolang.ast.element.ElementDeclaration;
import oolang.ast.element.ElementModifier;
import oolang.parser.generated.OolangLexer;
import oolang.parser.generated.OolangParser;
import oolang.parser.OolangAstListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import static oolang.ast.element.ElementDeclaration.ElementType.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OolangAstListenerTest {
    @Test
    public void parseSimplestClass() {
        var fileAst = astForCode("class Example");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
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
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
        assertThat(root.expressions()).hasSize(1);
        var classBody = (ElementDeclaration) root.expressions().getFirst();
        assertThat(classBody.elementType()).isEqualTo(CLASS_BODY);
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
        assertThat(annotation.description()).isEqualTo("ElementAnnotation(Service)");
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("ExampleTest");
        assertThat(root.description()).isEqualTo("ElementDeclaration(class ExampleTest)");
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
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
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
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
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
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
        assertThat(root.expressions()).hasSize(1);
        var constructor = (ElementDeclaration) root.expressions().getFirst();
        assertThat(constructor.elementType()).isEqualTo(CONSTRUCTOR);
        assertThat(constructor.expressions()).hasSize(1);
        var constructorParam = (ElementDeclaration) constructor.expressions().getFirst();
        assertThat(constructorParam.elementType()).isEqualTo(VAL);
        assertThat(constructorParam.description()).isEqualTo("ElementDeclaration(val foo:String)");
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
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
        assertThat(root.expressions()).hasSize(1);
        var constructor = (ElementDeclaration) root.expressions().getFirst();
        assertThat(constructor.expressions()).hasSize(1);
        var constructorParam = (ElementDeclaration) constructor.expressions().getFirst();
        var annotation = constructorParam.annotations().getFirst();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("ElementAnnotation(get:JsonIgnore)");
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
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
        assertThat(root.expressions()).hasSize(1);
        var constructor = (ElementDeclaration) root.expressions().getFirst();
        assertThat(constructor.expressions()).hasSize(1);
        var constructorParam = (ElementDeclaration) constructor.expressions().getFirst();
        assertThat(constructorParam.annotations()).hasSize(2);
        // Inject
        var annotation = constructorParam.annotations().getFirst();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("ElementAnnotation(set:Inject)");
        // VisibleForTesting
        annotation = constructorParam.annotations().getLast();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("ElementAnnotation(set:VisibleForTesting)");
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
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
        assertThat(root.expressions()).hasSize(1);
        var classBody = (ElementDeclaration) root.expressions().getFirst();
        assertThat(classBody.elementType()).isEqualTo(CLASS_BODY);
        assertThat(classBody.expressions()).hasSize(1);
        var funDeclaration = (ElementDeclaration) classBody.expressions().getFirst();
        assertThat(funDeclaration.elementType()).isEqualTo(FUN);
        assertThat(funDeclaration.identifier()).isNotNull();
        assertThat(funDeclaration.identifier().identifier()).isEqualTo("foo");
        assertThat(funDeclaration.type()).isEmpty();
        assertThat(funDeclaration.expressions()).hasSize(1);
        var funBody = (ElementDeclaration) funDeclaration.expressions().getFirst();
        assertThat(funBody.elementType()).isEqualTo(FUN_BODY);
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
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
        assertThat(root.expressions()).hasSize(1);
        var classBody = (ElementDeclaration) root.expressions().getFirst();
        assertThat(classBody.expressions()).hasSize(1);
        var funDeclaration = (ElementDeclaration) classBody.expressions().getFirst();
        assertThat(funDeclaration.elementType()).isEqualTo(FUN);
        assertThat(funDeclaration.identifier()).isNotNull();
        assertThat(funDeclaration.identifier().identifier()).isEqualTo("foo");
        assertThat(funDeclaration.type()).isEmpty();
        var annotation = funDeclaration.annotations().getFirst();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("ElementAnnotation(Test)");
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
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
        assertThat(root.expressions()).hasSize(1);
        var classBody = (ElementDeclaration) root.expressions().getFirst();
        assertThat(classBody.expressions()).hasSize(1);
        var funDeclaration = (ElementDeclaration) classBody.expressions().getFirst();
        assertThat(funDeclaration.elementType()).isEqualTo(FUN);
        assertThat(funDeclaration.identifier()).isNotNull();
        assertThat(funDeclaration.identifier().identifier()).isEqualTo("foo");
        assertThat(funDeclaration.type()).isEmpty();
        assertThat(funDeclaration.expressions()).hasSize(2);
        var funParam = (ElementDeclaration) funDeclaration.expressions().getFirst();
        assertThat(funParam.elementType()).isEqualTo(PARAMETER);
        assertThat(funParam.description()).isEqualTo("ElementDeclaration(parameter bar:String)");
        var funBody = (ElementDeclaration) funDeclaration.expressions().getLast();
        assertThat(funBody.elementType()).isEqualTo(FUN_BODY);
        print(fileAst);
    }

    @Test
    public void parseClassWithMainFunction() {
        var fileAst = astForCode("class Example {\nstatic fun main(args: Array<String>) {\nSystem.out.println(\"Hello, World!\", 2)}\n}");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("Example");
        assertThat(root.description()).isEqualTo("ElementDeclaration(class Example)");
        assertThat(root.expressions()).hasSize(1);
        var classBody = (ElementDeclaration) root.expressions().getFirst();
        assertThat(classBody.expressions()).hasSize(1);
        var funDeclaration = (ElementDeclaration) classBody.expressions().getFirst();
        assertThat(funDeclaration.elementType()).isEqualTo(FUN);
        assertThat(funDeclaration.identifier()).isNotNull();
        assertThat(funDeclaration.identifier().identifier()).isEqualTo("main");
        assertThat(funDeclaration.type()).isEmpty();
        assertThat(funDeclaration.modifiers()).extracting(ElementModifier::modifier)
                .containsExactly("static");
        assertThat(funDeclaration.expressions()).hasSize(2);
        var funParam = (ElementDeclaration) funDeclaration.expressions().getFirst();
        assertThat(funParam.elementType()).isEqualTo(PARAMETER);
        assertThat(funParam.description()).isEqualTo("ElementDeclaration(parameter args:Array<String>)");
        var funBody = (ElementDeclaration) funDeclaration.expressions().getLast();
        assertThat(funBody.elementType()).isEqualTo(FUN_BODY);
        print(fileAst);
    }

    private static FileAst astForCode(String code) {
        var parser = new OolangParser(new CommonTokenStream(new OolangLexer(CharStreams.fromString(code))));
        var oolangFile = parser.oolangFile();
        var listener = new OolangAstListener();
        ParseTreeWalker.DEFAULT.walk(listener, oolangFile);
        return listener.fileAst();
    }

    private static void print(FileAst fileAst) {
        System.out.println("FileAst:");
        new FileAstWriter(fileAst).write(System.out);
        System.out.println();
    }
}
