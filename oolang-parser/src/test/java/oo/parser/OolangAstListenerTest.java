package oo.parser;

import oo.ast.FileAst;
import oo.ast.FileAstWriter;
import oo.ast.element.ElementModifier;
import oo.parser.generated.OolangLexer;
import oo.parser.generated.OolangParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import static oo.ast.element.ElementDeclaration.ElementType.CLASS;
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
    public void parseAnnotatedClass() {
        var fileAst = astForCode("@Test\nclass ExampleTest");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.annotations()).hasSize(1);
        var annotation = root.annotations().getFirst();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("ElementAnnotation(Test)");
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("ExampleTest");
        assertThat(root.description()).isEqualTo("ElementDeclaration(class ExampleTest)");
        print(fileAst);
    }

    @Test
    public void parseUseSiteAnnotatedClass() {
        var fileAst = astForCode("@get:Test\nclass ExampleTest");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.annotations()).hasSize(1);
        var annotation = root.annotations().getFirst();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("ElementAnnotation(get:Test)");
        assertThat(root.identifier()).isNotNull();
        assertThat(root.identifier().identifier()).isEqualTo("ExampleTest");
        assertThat(root.description()).isEqualTo("ElementDeclaration(class ExampleTest)");
        print(fileAst);
    }

    @Test
    public void parseUseSiteMultipleAnnotatedClass() {
        var fileAst = astForCode("@set:[Inject VisibleForTesting]\nclass ExampleTest");
        var root = fileAst.rootElements().getFirst();
        assertThat(root).isNotNull();
        assertThat(root.elementType()).isEqualTo(CLASS);
        assertThat(root.annotations()).hasSize(2);
        // Inject
        var annotation = root.annotations().getFirst();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("ElementAnnotation(set:Inject)");
        // VisibleForTesting
        annotation = root.annotations().getLast();
        assertThat(annotation.identifier()).hasSize(1);
        assertThat(annotation.description()).isEqualTo("ElementAnnotation(set:VisibleForTesting)");
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
