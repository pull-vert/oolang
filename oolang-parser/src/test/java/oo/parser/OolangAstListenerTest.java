package oo.parser;

import oo.ast.FileAst;
import oo.ast.FileAstWriter;
import oo.ast.element.ElementDeclaration;
import oo.ast.element.ElementModifier;
import oo.parser.generated.OolangLexer;
import oo.parser.generated.OolangParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import static oo.ast.element.ElementDeclaration.ElementType.CLASS;
import static oo.ast.element.ElementDeclaration.ElementType.FUN;
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
        assertThat(constructor.expressions()).hasSize(1);
        var constructorParam = (ElementDeclaration) constructor.expressions().getFirst();
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
        assertThat(classBody.expressions()).hasSize(1);
        var funDeclaration = (ElementDeclaration) classBody.expressions().getFirst();
        assertThat(funDeclaration.elementType()).isEqualTo(FUN);
        assertThat(funDeclaration.identifier()).isNotNull();
        assertThat(funDeclaration.identifier().identifier()).isEqualTo("foo");
        assertThat(funDeclaration.type()).isEmpty();
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
