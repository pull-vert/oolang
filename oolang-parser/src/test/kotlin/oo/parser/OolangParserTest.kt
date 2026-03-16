package oo.parser

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.Token
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OolangParserTest {
    @Test
    fun parseSimplestClass() {
        val parser = parserForCode("class A")
        val classDecl = parser.classDeclaration()
        assertThat(classDecl.childCount).isEqualTo(2)
        assertThat(classDecl.CLASS().text).isEqualTo("class")
        assertThat(classDecl.simpleIdentifier().text)
            .isEqualTo("A")
        assertThat(classDecl.classBody()).isNull()
    }

    @Test
    fun parseSimplestClassWithBraces() {
        val parser = parserForCode("class A {}")
        val classDecl = parser.classDeclaration()
        assertThat(classDecl.childCount).isEqualTo(3)
        assertThat(classDecl.CLASS().text).isEqualTo("class")
        assertThat(classDecl.simpleIdentifier().text)
            .isEqualTo("A")
        assertThat(classDecl.classBody().text).isEqualTo("{}")
    }

    @Test
    fun parseSimplestInterface() {
        val parser = parserForCode("interface B")
        val classDecl = parser.classDeclaration()
        assertThat(classDecl.childCount).isEqualTo(2)
        assertThat(classDecl.INTERFACE().text).isEqualTo("interface")
        assertThat(classDecl.simpleIdentifier().text)
            .isEqualTo("B")
        assertThat(classDecl.classBody()).isNull()
    }

    private fun parserForCode(code: String) =
        OolangParser(CommonTokenStream(OolangLexer(CharStreams.fromString(code))))
}