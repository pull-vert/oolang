package oo.parser

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.Token
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OolangLexerTest {
    @Test
    fun lexSimplestClass() {
        assertThat(tokens(lexerForCode("class A")))
            .containsExactly("CLASS", "HexDigitOrSeparator", "EOF")
    }

    @Test
    fun lexSimplestClassWithBraces() {
        assertThat(tokens(lexerForCode("class A {}")))
            .containsExactly("CLASS", "HexDigitOrSeparator", "LBRACE", "RBRACE", "EOF")
    }

    private fun tokens(lexer: OolangLexer): List<String> {
        val tokens = mutableListOf<String>()
        var t: Token
        do {
            t = lexer.nextToken()
            if (-1 == t.type) {
                tokens.add("EOF")
            } else {
                if (t.type != OolangLexer.WS) {
                    tokens.add(OolangLexer.ruleNames[t.type])
                }
            }
        } while (t.type != -1)
        return tokens
    }

    private fun lexerForCode(code: String) = OolangLexer(CharStreams.fromString(code))
}