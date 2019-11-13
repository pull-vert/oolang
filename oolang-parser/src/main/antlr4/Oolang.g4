grammar Oolang;

classDeclaration
	:	normalClassDeclaration
//	|	enumDeclaration
	;

normalClassDeclaration
	:	annotation* classModifier? 'class' identifier typeParameters? parents? classBody
	;

classModifier
	:	'abstract'
	|	'open'
	;

typeParameters
	:	'<' typeParameterList '>'
	;

typeParameterList
	:	typeParameter (',' typeParameter)*
	;

parents
	:	'is' parentTypeList
	;

parentTypeList
	:	parentType (',' parentType)*
	;

classBody
	:	'{  }'
	;

annotation
	:	normalAnnotation
	|	markerAnnotation
	|	singleElementAnnotation
	;

normalAnnotation
	:	'@' typeName '(' elementValuePairList? ')'
	;

elementValuePairList
	:	elementValuePair (',' elementValuePair)*
	;

elementValuePair
	:	identifier '=' elementValue
	;

elementValue
	:	conditionalExpression
	|	elementValueArrayInitializer
	|	annotation
	;

elementValueArrayInitializer
	:	'{' elementValueList? ','? '}'
	;

elementValueList
	:	elementValue (',' elementValue)*
	;

markerAnnotation
	:	'@' typeName
	;

singleElementAnnotation
	:	'@' typeName '(' elementValue ')'
	;

// LEXER

// Reserved Words (todo complete if needed)

ABSTRACT : 'abstract';
BREAK : 'break';
CATCH : 'catch';
CLASS : 'class';
CONST : 'const';
CONTINUE : 'continue';
ELSE : 'else';
ENUM : 'enum';
FINAL : 'final';
FINALLY : 'finally';
FOR : 'for';
IF : 'if';
IMPORT : 'import';
INTERFACE : 'interface';
IS : 'is';
NEW : 'new';
PACKAGE : 'package';
RETURN : 'return';
STATIC : 'static';
SUPER : 'super';
SWITCH : 'switch';
THIS : 'this';
THROW : 'throw';
//TRANSIENT : 'transient';
TRY : 'try';
WHILE : 'while';
UNDER_SCORE : '_';

// Identifiers (must appear after all reserved words in the grammar)

identifier
  : JAVA_LETTER JAVA_LETTER_OR_DIGIT*
  ;

fragment
JAVA_LETTER
	:	[a-zA-Z$_] // these are the "java letters" below 0x7F
	|	// covers all characters above 0x7F which are not a surrogate
		~[\u0000-\u007F\uD800-\uDBFF]
		{Character.isJavaIdentifierStart(_input.LA(-1))}?
	|	// covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
		[\uD800-\uDBFF] [\uDC00-\uDFFF]
		{Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
	;

fragment
JAVA_LETTER_OR_DIGIT
	:	[a-zA-Z0-9$_] // these are the "java letters or digits" below 0x7F
	|	// covers all characters above 0x7F which are not a surrogate
		~[\u0000-\u007F\uD800-\uDBFF]
		{Character.isJavaIdentifierPart(_input.LA(-1))}?
	|	// covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
		[\uD800-\uDBFF] [\uDC00-\uDFFF]
		{Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
	;

//
// Whitespace and comments
//

WS  :  [ \t\r\n\u000C]+ -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> channel(HIDDEN)
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> channel(HIDDEN)
    ;
