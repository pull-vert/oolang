/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.parser;

import oo.parser.OolangLexer;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class OolangLexerTest {

    private OolangLexer lexerForCode(String code) {
        return new OolangLexer(CharStreams.fromString(code));
    }

    private List<String> tokens(OolangLexer lexer) {
        List<String> tokens = new ArrayList<>();
        Token t;
        do {
            t = lexer.nextToken();
            if (-1 == t.getType()) {
                tokens.add("EOF");
            } else {
                if (t.getType() != OolangLexer.WS) {
                    tokens.add(OolangLexer.ruleNames[t.getType() - 1]);
                }
            }
        } while (t.getType() != -1);
        return tokens;
    }

    @Test
    void parseSimplestClass() {
        assertThat(tokens(lexerForCode("class A")))
                .containsExactly("CLASS_KW", "Identifier", "EOF");
    }

    @Test
    void parseSimplestClassWithBraces() {
        assertThat(tokens(lexerForCode("class A {}")))
                .containsExactly("CLASS_KW", "Identifier", "LBRACE", "RBRACE", "EOF");
    }
}
