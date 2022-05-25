
grammar PASLexer;

// ===================================================
// PARSER
// ===================================================

program: PROGRAM ID SEMI types_sect vars_sect fnc_and_procedures_sect stmt_sect '.';

// Aceita functions e procedures em qualquer ordem
fnc_and_procedures_sect: opt_fnc_and_procedures_sect;
opt_fnc_and_procedures_sect:
    | fnc_and_procedures_list;
fnc_and_procedures_list: fnc_and_procedures_list fnc_and_procedures 
    | fnc_and_procedures;
fnc_and_procedures: fnc_sign_decl_list | procedures_decl_list;

// Aceita functions no formato
// function_name ID (param_list) : return_type
// variaveis
// Begin
//   <stmt>
// End;
fnc_sign_decl_list: fnc_sign_decl_list fnc_sign_decl 
     | fnc_sign_decl;
fnc_sign_decl: FUNCTION ID LPAR fnc_sign_params_sect RPAR ':' type_spec SEMI vars_sect stmt_sect SEMI;

// Aceita parametros de function no formato 
// argument(s): type1; argument(s): type2; ...
fnc_sign_params_sect: 
    | opt_fnc_sign_param_decl;
opt_fnc_sign_param_decl:  
    | fnc_sign_param_decl_list;
fnc_sign_param_decl_list: fnc_sign_param_decl_list SEMI fnc_sign_param_decl
    | fnc_sign_param_decl;
fnc_sign_param_decl: id_list ':' type_spec;

// Aceita procedures no formato
// Procedure <id>;
// Begin 
//   <stmt>
// End;
procedures_decl_list : procedures_decl_list procedures_decl 
     | procedures_decl;
procedures_decl: PROCEDURE ID LPAR procedure_params_sect RPAR SEMI stmt_sect SEMI;

// Aceita parametros de procedure no formato
// argument(s): type1; argument(s): type2; ...
procedure_params_sect: 
    | opt_procedure_param_decl;
opt_procedure_param_decl:  
    | procedure_param_decl_list;
procedure_param_decl_list: procedure_param_decl_list SEMI procedure_param_decl
    | procedure_param_decl;
procedure_param_decl: id_list ':' type_spec
    | VAR id_list ':' type_spec;

// Aceita multiplas declarações de arrays no formato
// TYPE
//    <id> = <type>;
//    ... restante dos arrays 
types_sect: 
    | TYPE opt_type_decl;
opt_type_decl:  
    | type_decl_list;
type_decl_list: type_decl_list type_decl 
    | type_decl;
type_decl: ID EQ array_type SEMI;

array_type_list: array_type | type_spec;
array_type: ARRAY LSB index_list RSB OF array_type_list
    | ARRAY OF array_type_list;
index_list: index
    | index_list ',' index;
index: INT_VAL TWODOTS INT_VAL;

// Aceita multiplas declarações de variaveis no formato
// VAR
//    <id> : <type>;
//    ... restante das variaveis 
vars_sect: 
    | VAR opt_var_decl;
opt_var_decl:  
    | var_decl_list;
var_decl_list: var_decl_list var_decl 
    | var_decl;
var_decl: id_list ':' type_spec SEMI;
id_list: id_list ',' ID
    | ID;

// Definição de tipos de variaveis
type_spec: INTEGER | REAL | CHAR | BOOLEAN | ID;

// Tipos de statements
stmt_sect: BEGINE stmt_list END;
stmt_list: 
    | stmt_list stmt 
    | stmt;
stmt: if_stmt 
    | repeat_stmt 
    | assign_stmt 
    | fnc_stmt
    | while_stmt
    | for_stmt
    | case_stmt;

// If then else
if_stmt: IF expr THEN stmt_sect SEMI
    | IF expr THEN stmt_sect SEMI else_stmt
    | IF expr THEN stmt
    | IF expr THEN stmt else_stmt;
else_stmt: ELSE stmt_sect SEMI
    | ELSE stmt
    | ELSE if_stmt;

// Repeat -> TROCAR PARA WHILE
repeat_stmt: REPEAT stmt_list UNTIL expr SEMI;

// WHILE
while_stmt: WHILE expr DO stmt_sect SEMI
    | WHILE expr DO stmt;

// FOR
for_stmt: FOR ID ASSIGN expr TO expr DO stmt_sect SEMI
    | FOR ID ASSIGN expr TO expr DO stmt ;

// Atribuição
assign_stmt: ID ASSIGN expr SEMI
    | ID array_access ASSIGN expr SEMI;

// Case
case_stmt: CASE case_arg OF case_target_list END SEMI
    | CASE case_arg OF case_target_list case_else END SEMI;
case_arg: LPAR ID RPAR
    | ID;

case_target_list: case_target_list case_target
    | case_target;
case_target: case_l_target_list ':' stmt_sect SEMI
    | case_l_target_list ':' stmt;
case_else: ELSE stmt_sect
    | ELSE stmt;

case_l_target_list: case_l_target_list ',' case_l_target
    | case_l_target;
case_l_target: SQSTR | INT_VAL | INT_VAL TWODOTS INT_VAL;


// Chamada de funções
fnc: ID LPAR expr RPAR 
    | ID 
    | ID LPAR RPAR
    | ID LPAR expr_list RPAR; 
fnc_stmt: fnc SEMI | ID LPAR fnc RPAR SEMI;

// Acesso ao array
array_access: LSB array_indexes RSB;
array_indexes: array_indexes ',' array_index
    | array_index;
array_index: expr;

// Expressões
expr_list: expr_list ',' expr
    | expr;
expr: expr LT expr
    | expr LTE expr
    | expr BT expr
    | expr BTE expr
    | expr EQ expr
    | expr NEQ expr
    | expr PLUS expr
    | expr AND expr
    | expr OR expr
    | expr DIV expr
    | expr MOD expr
    | MINUS expr
    | expr MINUS expr
    | expr TIMES expr
    | expr OVER expr
    | LPAR expr RPAR
    | expr ':' expr
    | fnc
    | INT_VAL
    | REAL_VAL
    | SQSTR 
    | ID
    | ID array_access
    | TRUE
    | FALSE;

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

SQSTR : '\'' (~['"] )* '\'';

TRUE: T R U E;
FALSE: F A L S E;
// Nomes de variaveis, funções, etc

ID : [a-zA-Z][a-zA-Z0-9_]* ;
