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

fragment A:[aA];
fragment B:[bB];
fragment C:[cC];
fragment D:[dD];
fragment E:[eE];
fragment F:[fF];
fragment G:[gG];
fragment H:[hH];
fragment I:[iI];
fragment J:[jJ];
fragment K:[kK];
fragment L:[lL];
fragment M:[mM];
fragment N:[nN];
fragment O:[oO];
fragment P:[pP];
fragment Q:[qQ];
fragment R:[rR];
fragment S:[sS];
fragment T:[tT];
fragment U:[uU];
fragment V:[vV];
fragment W:[wW];
fragment X:[xX];
fragment Y:[yY];
fragment Z:[zZ];

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
END            : E N D.;
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
STRING         : S T R I N G;
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

INTEGER        : I N T E G E R;
WORD           : W O R D;
LONGINT        : L O N G I N T;
REAL           : R E A L;
CHAR           : C H A R;
BOOLEAN        : B O O L E A N;


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
