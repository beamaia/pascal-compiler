
grammar PASLexer;

// ===================================================
// PARSER
// ===================================================

program: PROGRAM ID SEMI vars_sect procedures_sect stmt_sect;

// fnc_sign_sect: opt_fnc_sign_decl;
// opt_fnc_sign_decl:
//     | fnc_sign_decl_list;
// fnc_sign_decl_list: fnc_sign_decl_list fnc_sign_decl 
//     | fnc_sign_decl;
// fnc_sign_decl: FUNCTION ID LPAR vars_comma_sect RPAR ':' type_spec SEMI stmt_sect SEMI;

// vars_comma_sect: opt_var_comma_decl;
// opt_var_comma_decl:  
//     | var_comma_decl_list;
// var_comma_decl_list: var_comma_decl_list ',' var_comma_decl 
//     | var_comma_decl;
// var_comma_decl: ID ':' type_spec;

// Aceita procedures no formato
// Procedure <id>;
// Begin 
//   <stmt>
// End;
procedures_sect: opt_procedures_decl;
opt_procedures_decl:
     | procedures_decl_list;
procedures_decl_list: procedures_decl_list procedures_decl
     | procedures_decl;
procedures_decl: PROCEDURE ID SEMI stmt_sect SEMI;

// Aceita multiplas declarações de variaveis no formato
// VAR
//    <id> : <type>;
//    ... restante das variaveis
vars_sect: VAR opt_var_decl;
opt_var_decl:  
    | var_decl_list;
var_decl_list: var_decl_list var_decl 
    | var_decl;
var_decl: id_list ':' type_spec SEMI;
id_list: id_list ',' ID
    | ID;

// Definição de tipos de variaveis
type_spec: INTEGER | WORD | LONGINT | REAL | CHAR | BOOLEAN | STRING;

// Tipos de statements
stmt_sect: BEGINE stmt_list END;
stmt_list: 
    | stmt_list stmt 
    | stmt;
stmt: if_stmt 
    | repeat_stmt 
    | assign_stmt 
    | read_stmt 
    | write_stmt
    | fnc_stmt;

// If then else
if_stmt: IF expr THEN stmt_list
    | IF expr THEN stmt_list ELSE stmt_list;

// Repeat -> TROCAR PARA WHILE
repeat_stmt: REPEAT stmt_list UNTIL expr;

// Atribuição
assign_stmt: ID ASSIGN expr SEMI;

// ?
read_stmt: READ ID SEMI;
// ?
write_stmt: WRITE expr SEMI;

// Chamada de funções
fnc: ID LPAR expr RPAR | ID | ID LPAR RPAR;
fnc_stmt: fnc SEMI | ID LPAR fnc RPAR SEMI;

// Expressões
expr: expr LT expr
    | expr EQ expr
    | expr NEQ expr
    | expr PLUS expr
    | MINUS expr
    | expr MINUS expr
    | expr TIMES expr
    | expr OVER expr
    | LPAR expr RPAR
    | TRUE
    | FALSE
    | INT_VAL
    | REAL_VAL
    | SQSTR 
    | DQSTR
    | ID;

// ===================================================
// SCANNER
// ===================================================

// Reconhece e descarta espaços em branco e comentários

WS         : [ \t\n\r]+      -> skip ;
COMMENTSPR : '(*' ~[*)]* '*)' -> skip ; // no flex a expressão do meio seria [^}]*
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
CONST          : C O N S T;                  // Nao 
CONSTRUCTOR    : C O N S T R U C T O R;
DESTRUCTOR     : D E S T R U C T O R;
DIV            : D I V;
DO             : D O;
DOWNTO         : D O W N T O;
ELSE           : E L S E;
END            : E N D '.' | E N D;
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

// Tipos primitivos da linguagem

INTEGER        : I N T E G E R;
WORD           : W O R D;
LONGINT        : L O N G I N T;
REAL           : R E A L;
CHAR           : C H A R;
BOOLEAN        : B O O L E A N;

// 

ASSIGN : ':=' ;
EQ     : '='  ;
NEQ    : '<>' ;
LT     : '<'  ;
RT     : '>'  ;
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

// Valores inteiros, decimais e strings

INT_VAL  : [0-9]+;
REAL_VAL : [0-9]+ '.' [0-9]+ 
    | [0-9]+ '.' [0-9] E MINUS [0-9]+;

SQSTR : '\'' (~['"] )* '\'';

// Nomes de variaveis, funções, etc

ID : [a-zA-Z_]+ ;
