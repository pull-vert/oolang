lexer grammar OolangLexer;

// Keywords = Reserved Words (todo complete if needed)

ABSTRACT_KW : 'abstract';
AS_KW : 'as';
BREAK_KW : 'break';
BY_KW : 'by';
CATCH_KW : 'catch';
CLASS_KW : 'class';
CONST_KW : 'const';
CONTINUE_KW : 'continue';
ELSE_KW : 'else';
ENUM_KW : 'enum';
FINAL_KW : 'final';
FINALLY_KW : 'finally';
FOR_KW : 'for';
IF_KW : 'if';
IMPORT_KW : 'import';
INTERFACE_KW : 'interface';
IS_KW : 'is';
NEW_KW : 'new';
OPEN_KW : 'open';
PACKAGE_KW : 'package';
RETURN_KW : 'return';
STATIC_KW : 'static';
SUPER_KW : 'super';
SWITCH_KW : 'switch';
THIS_KW : 'this';
THROW_KW : 'throw';
//TRANSIENT_KW : 'transient';
TRY_KW : 'try';
WHILE_KW : 'while';
//UNDER_SCORE_KW : '_';

// The Null Literal

NullLiteral         :	'null'
	;

// Separators

LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';
LBRACK : '[';
RBRACK : ']';
SEMI : ';';
COMMA : ',';
DOT : '.';
ELLIPSIS : '...';
AT : '@';
COLONCOLON : '::';

// Identifiers (must appear after all reserved words in the grammar)

Identifier          : JavaLetter JavaLetterOrDigit* ;

fragment
JavaLetter          : [a-zA-Z$_] // these are the "java letters" below 0x7F
                    | // covers all characters above 0x7F which are not a surrogate
                        ~[\u0000-\u007F\uD800-\uDBFF]
                        {Character.isJavaIdentifierStart(_input.LA(-1))}?
                    | // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
                        [\uD800-\uDBFF] [\uDC00-\uDFFF]
                        {Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
                    ;

fragment
JavaLetterOrDigit   : [a-zA-Z0-9$_] // these are the "java letters or digits" below 0x7F
                    | // covers all characters above 0x7F which are not a surrogate
                        ~[\u0000-\u007F\uD800-\uDBFF]
                        {Character.isJavaIdentifierPart(_input.LA(-1))}?
                    | // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
                        [\uD800-\uDBFF] [\uDC00-\uDFFF]
                        {Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
                    ;

//
// Whitespace and comments
//

// Whitespace
NEWLINE             : '\r\n' | 'r' | '\n' ;
WS                  : [\t ]+ ;

COMMENT             : '/*' .*? '*/' -> channel(HIDDEN) ;

LINE_COMMENT        : '//' ~[\r\n]* -> channel(HIDDEN) ;
