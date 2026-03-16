/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oo.parser;

import oolang.parser.generated.OolangLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OolangLexerTest {
    @Test
    public void lexSimplestClass() {
        assertThat(tokens(lexerForCode("class A")))
                .containsExactly("CLASS", "HexDigitOrSeparator", "EOF");
    }

    @Test
    public void lexSimplestClassWithBraces() {
        assertThat(tokens(lexerForCode("class A {}")))
                .containsExactly("CLASS", "HexDigitOrSeparator", "LBRACE", "RBRACE", "EOF");
    }

    private static List<String> tokens(OolangLexer lexer) {
        List<String> tokens = new ArrayList<>();
        Token t;
        do {
            t = lexer.nextToken();
            if (t.getType() == -1) {
                tokens.add("EOF");
            } else {
                if (t.getType() != OolangLexer.WS) {
                    tokens.add(OolangLexer.ruleNames[t.getType()]);
                }
            }
        } while (t.getType() != -1);
        return tokens;
    }

    private static OolangLexer lexerForCode(String code) {
        return new OolangLexer(CharStreams.fromString(code));
    }
}
