// Quando só temos um scanner (lexer), precisamos indicar na declaração da
// gramática, como abaixo.
grammar PASLexer;

program: PROGRAM ID SEMI uses_sect vars_sect stmt_sect;
uses_sect: opt_uses_decl;
opt_uses_decl: | uses_decl_list;
uses_decl_list: uses_decl_list uses_decl | uses_decl;
uses_decl: USES ID SEMI;
vars_sect: VAR opt_var_decl;
opt_var_decl:  | var_decl_list;
var_decl_list: var_decl_list var_decl | var_decl;
var_decl: ID ':' type_spec SEMI;
type_spec: INTEGER | WORD | LONGINT | REAL | CHAR | BOOLEAN;
stmt_sect: BEGINE stmt_list END;
stmt_list: | stmt_list stmt | stmt;
stmt: if_stmt | repeat_stmt | assign_stmt | read_stmt | write_stmt;
if_stmt: IF expr THEN stmt_list
    | IF expr THEN stmt_list ELSE stmt_list;
repeat_stmt: REPEAT stmt_list UNTIL expr;
assign_stmt: ID ASSIGN expr SEMI;
read_stmt: READ ID SEMI;
write_stmt: WRITE expr SEMI;
expr: expr LT expr
    | expr EQ expr
    | expr PLUS expr
    | expr MINUS expr
    | expr TIMES expr
    | expr OVER expr
    | LPAR expr RPAR
    | TRUE
    | FALSE
    | INT_VAL
    | REAL_VAL
    | STR_VAL
    | ID;


// Reconhece e descarta espaços em branco e comentários.
WS       : [ \t\n\r]+      -> skip ;
COMMENTS : '(*' ~[*)]* '*)' -> skip ; // no flex a expressão do meio seria [^}]*

// Note que não são necessários comandos de impressão abaixo porque o ANTLR
// já exibe os tokens no terminal por padrão.

ABSOLUTE       : 'absolute';
AND            : 'and';
ARRAY          : 'array';
ASM            : 'asm';
BEGINE         : 'begin';
CASE           : 'case';
CONST          : 'const';
CONSTRUCTOR    : 'constructor';
DESTRUCTOR     : 'destructor';
DIV            : 'div';
DO             : 'do';
DOWNTO         : 'downto';
ELSE           : 'else';
END            : 'end.';
FILE           : 'file';
FOR            : 'for';
FUNCTION       : 'function';
GOTO           : 'goto';
IF             : 'if';
IMPLEMENTATION : 'implementation';
IN             : 'in';
INHERITED      : 'inherited';
INLINE         : 'inline';
INTERFACE      : 'interface';
LABEL          : 'label';
MOD            : 'mod';
NIL            : 'nil';
NOT            : 'not';
OBJECT         : 'object';
OF             : 'of';
OPERATOR       : 'operator';
OR             : 'or';
PACKED         : 'packed';
PROCEDURE      : 'procedure';
PROGRAM        : 'program';
RECORD         : 'record';
REINTRODUCE    : 'reintroduce';
REPEAT         : 'repeat';
SELF           : 'self';
SET            : 'set';
SHL            : 'shl';
SHR            : 'shr';
STRING         : 'string';
THEN           : 'then';
TO             : 'to';
TYPE           : 'type';
UNIT           : 'unit';
UNTIL          : 'until';
USES           : 'uses';
VAR            : 'var';
WHILE          : 'while';
WITH           : 'with';
XOR            : 'xor';

INTEGER        : 'integer';
WORD           : 'word';
LONGINT        : 'longint';
REAL           : 'real';
CHAR           : 'char';
BOOLEAN        : 'boolean';


ASSIGN : ':=' ;
EQ     : '='  ;
LPAR   : '('  ;
LT     : '<'  ;
RT     : '>'  ;
MINUS  : '-'  ;
OVER   : '/'  ;
PLUS   : '+'  ;
RPAR   : ')'  ;
SEMI   : ';'  ;
TIMES  : '*'  ;

INT_VAL  : [0-9]+            ;
REAL_VAL : [0-9]+ '.' [0-9]+ ;
STR_VAL  : '"' ~["]* '"'     ;

ID : [a-zA-Z]+ ;

// O ANTLR já possui um sistema de tratamento de erros embutido. Assim, não
// é necessária uma regra para detectar e indicar os erros léxicos.
// Veja a execução para um dos casos de teste 'lexerr0[1-3].ezl'.
