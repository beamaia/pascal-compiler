lexer grammar PASLexer;

@header {
    package parser;
}

// ===================================================
// SCANNER
// ===================================================

// Reconhece e descarta espaços em branco e comentários

ENDC : '*)';

// Espaco em branco.  
WS         : [ \t\n\r]+      -> skip ;

// Comentarios do tipo (* *) e { }
COMMENTSPR : '(*' .*?  '*)' -> skip ; // no flex a expressão do meio seria [^}]*
COMMENTS   : '{' ~[}]* '}'   -> skip ; 

// Fragments para case insensitive

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];

// Palavras reservadas

ABSOLUTE       : A B S O L U T E;
AND            : A N D;
ARRAY          : A R R A Y;
ASM            : A S M;
BEGINE         : B E G I N;
CASE           : C A S E;
CONST          : C O N S T;                 
CONSTRUCTOR    : C O N S T R U C T O R;
DESTRUCTOR     : D E S T R U C T O R;
DIV            : D I V;
DO             : D O;
DOWNTO         : D O W N T O;
ELSE           : E L S E;
END            : E N D;
FILE           : F I L E;
FOR            : F O R;
FUNCTION       : F U N C T I O N;
GOTO           : G O T O;
IF             : I F;
IMPLEMENTATION : I M P L E M E N T A T I O N;
IN             : I N;
INHERITED      : I N H E R I T E D;
INLINE         : I N L I N E;
INTERFACE      : I N T E R F A C E;
LABEL          : L A B E L;
MOD            : M O D;
NIL            : N I L;
NOT            : N O T;
OBJECT         : O B J E C T;
OF             : O F;
OPERATOR       : O P E R A T O R;
OR             : O R;
PACKED         : P A C K E D;
PROCEDURE      : P R O C E D U R E;
PROGRAM        : P R O G R A M;
RECORD         : R E C O R D;
REINTRODUCE    : R E I N T R O D U C E;
REPEAT         : R E P E A T;
SELF           : S E L F;
SET            : S E T;
SHL            : S H L;
SHR            : S H R;
THEN           : T H E N;
TO             : T O;
TYPE           : T Y P E;
UNIT           : U N I T;
UNTIL          : U N T I L;
USES           : U S E S;
VAR            : V A R;
WHILE          : W H I L E;
WITH           : W I T H;
XOR            : X O R;

// Tipos primitivos da linguagem

INTEGER        : I N T E G E R;
REAL           : R E A L;
CHAR           : C H A R;
BOOLEAN        : B O O L E A N;

// Operadores

ASSIGN : ':=' ;
EQ     : '='  ;
NEQ    : '<>' ;
LT     : '<'  ;
LTE    : '<=' ;
BT     : '>'  ;
BTE    : '>=' ;
PLUS   : '+'  ;
MINUS  : '-'  ;
TIMES  : '*'  ;
OVER   : '/'  ;
SEMI   : ';'  ;
LPAR   : '('  ;
RPAR   : ')'  ;
LSB    : '['  ;
RSB    : ']'  ;
RCB    : '{'  ;
LCB    : '}'  ;
DOL    : '$'  ;
HASH   : '#'  ;
TWODOTS: '..'  ;

// Valores inteiros, decimais e strings

INT_VAL  : [0-9]+;
REAL_VAL : [0-9]+ '.' [0-9]+ 
    | [0-9]+ '.' [0-9] E MINUS [0-9]+;
CHAR_VAL : '\'' [a-zA-Z] '\'';
SQSTR : '\'' (~['"] )* '\'';

TRUE: T R U E;
FALSE: F A L S E;
// Nomes de variaveis, funções, etc

ID : [a-zA-Z][a-zA-Z0-9_]* ;

// Pontuacao
DOT :  '.';
COLON: ':';
COMMA: ',';