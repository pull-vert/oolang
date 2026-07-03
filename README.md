# Oolang

Oolang is a JVM language
* Kotlin-like syntax
* does not need any std-lib
* null-safe by nature
* fully compatible from / to Java

```
package com.example

class Example {
  static fun main(args: Array<String>) {
    System.out.println("Hello, World!")
  }
}
```

## Oolang compiler
Compiling Oolang source code involves 3 stages
1. lexer + parser
2. semantic analyzer
3. compiler

## Parser
The lexer and the parser phase (aka syntax analysis) use [ANTLR v4](https://github.com/antlr/antlr4) to parse your
Oolang source code. \
The output of this first stage is an Abstract Syntax Tree, or AST.

## Semantic analyzer
The semantic analysis phase checks that your syntactically correct Oolang source code is also *meaningful*: variables
are declared before use, types match, function signatures are respected. \
During this phase the AST is enriched with the resolved types and method signatures.

## Compiler
The compiler phase transforms Oolang AST to JVM bytecode using
[Java Class-File API](https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/lang/classfile/package-summary.html).

You can also read our [draft ideas page](DRAFT_IDEAS.md).
