parser grammar PASParser;


options {
    tokenVocab = PASLexer; // Indica que os tokens estão na "lexer grammar".
}

@header {
    package parser;
}


// ===================================================
// PARSER
// ===================================================

program: PROGRAM ID SEMI vars_sect fnc_and_procedures_sect stmt_sect DOT;

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
fnc_sign_decl: FUNCTION ID LPAR fnc_sign_params_sect RPAR COLON type_spec SEMI func_vars_sect stmt_sect SEMI;

// Aceita parametros de function no formato 
// argument(s): type1; argument(s): type2; ...
fnc_sign_params_sect: 
    | opt_fnc_sign_param_decl;
opt_fnc_sign_param_decl:  
    | fnc_sign_param_decl_list;
fnc_sign_param_decl_list: fnc_sign_param_decl_list SEMI fnc_sign_param_decl
    | fnc_sign_param_decl;
fnc_sign_param_decl: proc_func_id_list COLON type_spec;

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
procedure_param_decl: proc_func_id_list COLON type_spec
    | VAR proc_func_id_list COLON type_spec;


// Aceita multiplas declarações de variaveis no formato em funcoes
// VAR
//    <id> : <type>;
//    ... restante das variaveis 
func_vars_sect: 
    | VAR func_opt_var_decl;
func_opt_var_decl:  
    | func_var_decl_list;
func_var_decl_list: func_var_decl_list func_var_decl 
    | func_var_decl;
func_var_decl: proc_func_id_list COLON type_spec SEMI;


// Secao de declaracao de ids params/dentro de func
proc_func_id_list: proc_func_id_list COMMA proc_func_id_node
            | proc_func_id_node;
proc_func_id_node: ID;


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
var_decl: id_list COLON type_spec SEMI;
id_list: id_list COMMA id_node
    | id_node;
id_node: ID;

// Definição de tipos de variaveis
type_spec: primitive_types #primitiveTypeSpec
        | array_type_decl primitive_types #arrayTypeSpec
        ; 
array_type_decl: ARRAY LSB array_start TWODOTS array_end RSB OF #arrayTypeDecl;

array_start: integer_val;
array_end: integer_val;

//para aceitar inteiros positivos e negativos no array
integer_val: INT_VAL
    | MINUS INT_VAL;

//tipos primitivos do Pascal
primitive_types: INTEGER #intType 
        | REAL #realType
        | CHAR #charType
        | BOOLEAN #boolType
        ;

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
    | for_stmt;

// If then else
if_stmt: IF expr THEN stmt_sect SEMI        #ifThenStmt
    | IF expr THEN stmt_sect SEMI else_stmt #ifThenElseStmt
    | IF expr THEN stmt                     #inlineIfThenStmt
    | IF expr THEN stmt else_stmt           #inlineIfThenElseStmt; 
else_stmt: ELSE stmt_sect SEMI #elseStmt
    | ELSE stmt                #inlineElseStmt
    | ELSE if_stmt             #elseIfStmt;

// Repeat -> TROCAR PARA WHILE
repeat_stmt: REPEAT stmt_list UNTIL expr SEMI;

// WHILE
while_stmt: WHILE expr DO stmt_sect SEMI
    | WHILE expr DO stmt;

// FOR
for_stmt: FOR ID ASSIGN expr TO expr DO stmt_sect SEMI
    | FOR ID ASSIGN expr TO expr DO stmt ;

// Atribuição
assign_stmt: ID ASSIGN expr SEMI #assignStmtSimple
    | ID LSB array_index RSB ASSIGN expr SEMI #assignStmtArray;

// Chamada de funções
fnc: ID LPAR expr RPAR // func(expr)
    | ID // func
    | ID LPAR RPAR // func()
    | ID LPAR expr_list RPAR; // func(expr, expr, expr...) 
fnc_stmt: fnc SEMI | ID LPAR fnc RPAR SEMI;

// Acesso ao array
// array_access: LSB array_index RSB;
array_index: expr;

// Expressões
expr_list: expr_list COMMA expr
    | expr;
expr: left=expr op=(LT | LTE | BT | BTE | EQ | NEQ | AND | OR) right=expr     # exprOpLogic
    | MINUS expr       # exprUnaryMinus
    | NOT expr         # exprUnaryNot
    | left=expr op=(PLUS | OVER | TIMES | MINUS) right=expr   # exprArithmetic
    | left=expr op=(DIV | MOD) right=expr    # exprDivMod
    | LPAR expr RPAR   # exprLparRpar
    | INT_VAL          # exprIntVal
    | REAL_VAL         # exprRealVal
    | CHAR_VAL         # exprCharVal
    | SQSTR            # exprStrVal
    | ID               # exprId
    | ID LSB array_index RSB  # exprArrayAccess
    | fnc              # exprFnc
    | TRUE             # exprBooleanVal
    | FALSE            # exprBooleanVal
    ;    