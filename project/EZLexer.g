// Quando só temos um scanner (lexer), precisamos indicar na declaração da
// gramática, como abaixo.
grammar EZLexer;

program: PROGRAM ID SEMI vars_sect stmt_sect;
vars_sect: VAR opt_var_decl;
opt_var_decl:  | var_decl_list;
var_decl_list: var_decl_list var_decl | var_decl;
var_decl: type_spec ID SEMI;
type_spec: BOOL | INT | REAL | STRING;
stmt_sect: BEGINE stmt_list END;
stmt_list: stmt_list stmt | stmt;
stmt: if_stmt | repeat_stmt | assign_stmt | read_stmt | write_stmt;
if_stmt: IF expr THEN stmt_list END
    | IF expr THEN stmt_list ELSE stmt_list END;
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
WS       : [ \t\n]+      -> skip ;
COMMENTS : '{' ~[}]* '}' -> skip ; // no flex a expressão do meio seria [^}]*

// Note que não são necessários comandos de impressão abaixo porque o ANTLR
// já exibe os tokens no terminal por padrão.

BEGINE   : 'begin'   ;
BOOL    : 'bool'    ;
ELSE    : 'else'    ;
END     : 'end'     ;
FALSE   : 'false'   ;
IF      : 'if'      ;
INT     : 'int'     ;
PROGRAM : 'program' ;
READ    : 'read'    ;
REAL    : 'real'    ;
REPEAT  : 'repeat'  ;
STRING  : 'string'  ;
THEN    : 'then'    ;
TRUE    : 'true'    ;
UNTIL   : 'until'   ;
VAR     : 'var'     ;
WRITE   : 'write'   ;

ASSIGN : ':=' ;
EQ     : '='  ;
LPAR   : '('  ;
LT     : '<'  ;
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
