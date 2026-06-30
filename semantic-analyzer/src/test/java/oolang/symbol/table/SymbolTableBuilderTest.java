/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import oolang.KlassesCacheInterceptor;
import oolang.parser.OolangAstVisitor;
import oolang.parser.generated.OolangLexer;
import oolang.parser.generated.OolangParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.NoSuchElementException;

import static oolang.symbol.table.Visibility.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(KlassesCacheInterceptor.class)
public class SymbolTableBuilderTest {
    @Test
    public void symbolTableOfSimplestClass() {
        var symbolTable = symbolTableForCode("""
                package com.example
                class Example""");
        var rootKlass = symbolTable.nextKlass();
        assertThat(rootKlass.name).isEqualTo("Example");
        assertNoMoreContent(rootKlass);
    }

    @Test
    public void symbolTableOfClassWithEmptyBody() { // exact same behavior as the simplest above
        var symbolTable = symbolTableForCode("""
                package com.example
                class Example {}""");
        var rootKlass = symbolTable.nextKlass();
        assertThat(rootKlass.name).isEqualTo("Example");
        assertNoMoreContent(rootKlass);
    }

    @Test
    public void symbolTableOfInnerClassWithEmptyBody() { // exact same behavior as the simplest above
        var symbolTable = symbolTableForCode("""
                package com.example
                class Example {
                class Inner
                }""");
        var rootKlass = symbolTable.nextKlass();
        assertThat(rootKlass.name).isEqualTo("Example");
        var childKlass = rootKlass.nextKlass();
        assertThat(childKlass.name).isEqualTo("Inner");
        assertNoMoreContent(childKlass);
        assertNoMoreContent(rootKlass);
    }

    @Test
    public void symbolTableOfClassWithProperty() {
        var symbolTable = symbolTableForCode("""
                package com.example
                class Example {
                val foo: String
                }""");
        var rootKlass = symbolTable.nextKlass();
        assertThat(rootKlass.name).isEqualTo("Example");
        var childProperty = rootKlass.nextProperty();
        assertThat(childProperty.name).isEqualTo("foo");
        assertThat(childProperty.visibility).isEqualTo(PUBLIC);
        assertThat(childProperty.isStatic).isFalse();
        assertThat(childProperty.isAbstract).isFalse();
        assertThat(childProperty.isFinal).isTrue();
        var klass = (Klass) childProperty.type();
        assertThat(klass.descriptorString()).isEqualTo("Ljava/lang/String;");
        assertNoMoreContent(rootKlass);
    }

    @Test
    public void symbolTableOfClassWithStaticProperty() {
        var symbolTable = symbolTableForCode("""
                package com.example
                import java.math.BigInteger
                class Example {
                protected static var FOO: BigInteger
                }""");
        var rootKlass = symbolTable.nextKlass();
        assertThat(rootKlass.name).isEqualTo("Example");
        var childProperty = rootKlass.nextProperty();
        assertThat(childProperty.name).isEqualTo("FOO");
        assertThat(childProperty.visibility).isEqualTo(OOLANG_PROTECTED);
        assertThat(childProperty.isStatic).isTrue();
        assertThat(childProperty.isAbstract).isFalse();
        assertThat(childProperty.isFinal).isFalse();
        var klass = (Klass) childProperty.type();
        assertThat(klass.descriptorString()).isEqualTo("Ljava/math/BigInteger;");
        assertNoMoreContent(rootKlass);
    }

    @Test
    public void symbolTableOfClassWithSimplestFunction() {
        var symbolTable = symbolTableForCode("""
                package com.example
                class Example {
                private fun foo() {}
                }""");
        var rootKlass = symbolTable.nextKlass();
        assertThat(rootKlass.name).isEqualTo("Example");
        var childFunction = rootKlass.nextFunction();
        assertThat(childFunction.name).isEqualTo("foo");
        assertThat(childFunction.visibility).isEqualTo(PRIVATE);
        assertThat(childFunction.isStatic).isFalse();
        assertThat(childFunction.isAbstract).isFalse();
        assertThat(childFunction.isFinal).isTrue();
        var klass = (Klass) childFunction.returnType();
        assertThat(klass.descriptorString()).isEqualTo("V");
        assertThat(childFunction.parameters).isEmpty();
        assertNoMoreContent(rootKlass);
    }

    @Test
    public void symbolTableOfClassWithSimpleFunctionAndReturnType() {
        var symbolTable = symbolTableForCode("""
                package com.example
                class Example {
                private fun foo(): Void {}
                }""");
        var rootKlass = symbolTable.nextKlass();
        assertThat(rootKlass.name).isEqualTo("Example");
        var childFunction = rootKlass.nextFunction();
        assertThat(childFunction.name).isEqualTo("foo");
        assertThat(childFunction.visibility).isEqualTo(PRIVATE);
        assertThat(childFunction.isStatic).isFalse();
        assertThat(childFunction.isAbstract).isFalse();
        assertThat(childFunction.isFinal).isTrue();
        var klass = (Klass) childFunction.returnType();
        assertThat(klass.descriptorString()).isEqualTo("V");
        assertThat(childFunction.parameters).isEmpty();
        assertNoMoreContent(rootKlass);
    }

    @Test
    public void symbolTableOfClassWithFunctionWithParameter() {
        var symbolTable = symbolTableForCode("""
                package com.example
                class Example {
                fun foo(bar: Int): String {}
                }""");
        var rootKlass = symbolTable.nextKlass();
        assertThat(rootKlass.name).isEqualTo("Example");
        var childFunction = rootKlass.nextFunction();
        assertThat(childFunction.name).isEqualTo("foo");
        assertThat(childFunction.visibility).isEqualTo(PUBLIC);
        assertThat(childFunction.isStatic).isFalse();
        assertThat(childFunction.isAbstract).isFalse();
        assertThat(childFunction.isFinal).isTrue();
        var klass = (Klass) childFunction.returnType();
        assertThat(klass.descriptorString()).isEqualTo("Ljava/lang/String;");
        assertThat(childFunction.parameters).hasSize(1);
        var parameter = childFunction.parameters.getFirst();
        assertThat(parameter.name).isEqualTo("bar");
        klass = (Klass) parameter.type();
        assertThat(klass.descriptorString()).isEqualTo("I");
        assertNoMoreContent(rootKlass);
    }

    @Test
    public void parseClassWithMainFunction() {
        var symbolTable = symbolTableForCode("""
                package com.example
                class Example {
                static fun main(args: Array<String>) {}
                }""");
        var rootKlass = symbolTable.nextKlass();
        assertThat(rootKlass.name).isEqualTo("Example");
        var childFunction = rootKlass.nextFunction();
        assertThat(childFunction.name).isEqualTo("main");
        assertThat(childFunction.visibility).isEqualTo(PUBLIC);
        assertThat(childFunction.isStatic).isTrue();
        assertThat(childFunction.isAbstract).isFalse();
        assertThat(childFunction.isFinal).isTrue();
        var klass = (Klass) childFunction.returnType();
        assertThat(klass.descriptorString()).isEqualTo("V");
        assertThat(childFunction.parameters).hasSize(1);
        var parameter = childFunction.parameters.getFirst();
        assertThat(parameter.name).isEqualTo("args");
        klass = (Klass) parameter.type();
        assertThat(klass.descriptorString()).isEqualTo("[Ljava/lang/String;"); // = an array '[' for strings
        assertNoMoreContent(rootKlass);
    }

    private static void assertNoMoreContent(OolangKlass klass) {
        assertThatThrownBy(klass::nextProperty).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(klass::nextFunction).isInstanceOf(NoSuchElementException.class);
        assertThatThrownBy(klass::nextKlass).isInstanceOf(NoSuchElementException.class);
    }

    private static OolangKlass symbolTableForCode(String code) {
        var parser = new OolangParser(new CommonTokenStream(new OolangLexer(CharStreams.fromString(code))));
        var oolangFile = parser.oolangFile();
        var visitor = new OolangAstVisitor();
        var astFile = visitor.visitOolangFile(oolangFile);
        return SymbolTableBuilder.build(astFile);
    }
}
