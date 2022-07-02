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
fnc_sign_decl: FUNCTION ID LPAR fnc_sign_params_sect RPAR COLON type_spec SEMI vars_sect stmt_sect SEMI;

// Aceita parametros de function no formato 
// argument(s): type1; argument(s): type2; ...
fnc_sign_params_sect: 
    | opt_fnc_sign_param_decl;
opt_fnc_sign_param_decl:  
    | fnc_sign_param_decl_list;
fnc_sign_param_decl_list: fnc_sign_param_decl_list SEMI fnc_sign_param_decl
    | fnc_sign_param_decl;
fnc_sign_param_decl: id_list COLON type_spec;

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
procedure_param_decl: id_list COLON type_spec
    | VAR id_list COLON type_spec;

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
id_node: ID # idNode;

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
case_target: case_l_target_list COLON stmt_sect SEMI
    | case_l_target_list COLON stmt;
case_else: ELSE stmt_sect
    | ELSE stmt;

case_l_target_list: case_l_target_list COMMA case_l_target
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
array_indexes: array_indexes COMMA array_index
    | array_index;
array_index: expr;

// Expressões
expr_list: expr_list COMMA expr
    | expr;
expr: expr op=(LT | LTE | BT | BTE | EQ | NEQ | AND | OR) expr     # exprOpLogic
    | MINUS expr       # exprUnaryMinus
    | expr op=(PLUS | OVER | TIMES | MINUS) expr   # exprArithmetic
    | expr op=(DIV | MOD) expr    # exprDiv
    | LPAR expr RPAR   # exprLparRpar
    | expr COLON expr  # exprColon
    | fnc              # exprFnc
    | INT_VAL          # exprIntVal
    | REAL_VAL         # exprRealVal
    | CHAR_VAL         # exprCharVal
    | SQSTR            # exprStrVal
    | ID               # exprId
    | ID array_access  # exprArrayAccess
    | TRUE             # exprBooleanVal
    | FALSE            # exprBooleanVal
    ;    