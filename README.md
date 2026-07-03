# Oolang

Oolang is a JVM language
* modern & simple syntax
* no additional std-lib
* NPE-safety
* fully compatible from / to Java

```
package com.example

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UserService(private val repository: UserRepository) {
  private static val LOGGER: Logger by lazy { LoggerFactory.getLogger(UserService.class) }

  fun getByName(name: String) = repository.getByName(name)
  
  fun save(user: User) {
    LOGGER.info { "Saving new user ${user.name}" }
    repository.save(user)
  }
}
```

## Oolang compiler
Compiling Oolang source code involves 3 stages
1. lexer + parser
2. semantic analyzer
3. compiler

## Parser
The lexer and the parser phases (aka syntax analysis) use [ANTLR v4](https://github.com/antlr/antlr4) to parse your
Oolang source code. \
The output of this first stage is a raw Abstract Syntax Tree, or AST.

## Semantic analyzer
The semantic analysis phase checks that your syntactically correct Oolang source code is also *meaningful*: variables
are declared before use, types match, function signatures are respected. \
During this phase the AST is enriched with the resolved types, function signatures, field and function calls, etc.

## Compiler
The compiler phase transforms the enriched Oolang AST to JVM bytecode using
[Java Class-File API](https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/lang/classfile/package-summary.html).

You can also read our [draft ideas page](DRAFT_IDEAS.md).
