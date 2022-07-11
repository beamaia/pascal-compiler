package checker;

import ast.AST;
import static ast.NodeKind.*;

import types.Type;
import static types.Type.*;

import org.antlr.v4.runtime.Token;

import parser.PASParser;
import parser.PASParserBaseVisitor;

import parser.PASParser.ProgramContext;
import parser.PASParser.Vars_sectContext;
import parser.PASParser.Var_decl_listContext;
import parser.PASParser.*;

import parser.PASParser.IntTypeContext;
import parser.PASParser.RealTypeContext;
import parser.PASParser.BoolTypeContext;
import parser.PASParser.CharTypeContext;

import parser.PASParser.Id_nodeContext;
import parser.PASParser.ArrayTypeDeclContext;
import parser.PASParser.Array_startContext;
import parser.PASParser.Array_endContext;

import parser.PASParser.ExprOpLogicContext;
import parser.PASParser.ExprUnaryMinusContext;
import parser.PASParser.ExprUnaryNotContext;
import parser.PASParser.ExprArithmeticContext;
import parser.PASParser.ExprDivModContext;
import parser.PASParser.ExprLparRparContext;
import parser.PASParser.ExprFncContext;
import parser.PASParser.ExprIntValContext;
import parser.PASParser.ExprRealValContext;
import parser.PASParser.ExprCharValContext;
import parser.PASParser.ExprStrValContext;
import parser.PASParser.ExprIdContext;
import parser.PASParser.ExprArrayAccessContext;

import parser.PASParser.Var_declContext;

import parser.PASParser.Fnc_and_procedures_sectContext;
import parser.PASParser.Fnc_sign_declContext;
import parser.PASParser.Fnc_sign_param_declContext;
import parser.PASParser.Proc_func_id_nodeContext;
import parser.PASParser.Procedures_declContext;

import parser.PASParser.Assign_stmtContext;
import parser.PASParser.AssignStmtSimpleContext;
import parser.PASParser.AssignStmtArrayContext;

import tables.EntryInput;
import tables.EntryStr;
import tables.EntryArray;
import tables.EntryFunc;
import tables.StrTable;
import tables.VarTable;
import tables.FuncTable;

import checker.Scope;


public class SemanticChecker extends PASParserBaseVisitor<AST> {
    
    Type lastDeclType;
    Scope lastEnteredScope = Scope.GLOBAL;

    AST root;
    AST varsRoot;
    AST funcRoot;
    AST funcVarsRoot;

    private StrTable stringTable = new StrTable();
    private VarTable variableTable = new VarTable();
    private FuncTable functionTable = new FuncTable();

    private boolean passed = true;
    private boolean isArray = false;
    
    // Last declared array indexes
    private int start;
    private int end;

    private String lastFuncVisited;

    public SemanticChecker() {
        this.root = AST.newSubtree(PROGRAM_NODE, NO_TYPE);
        // AST.printDot(this.root, variableTable);
    }	

    /*************************** DECLARACAO DE VARIAVEIS ***************************/

    /**
    Visita secao de declaraca de variaveis. Inicializa a raiz de variaveis que sera 
    adicionado as variaveis. Se tiver variaveis declaradas no contexto do filho,
    visita-lo. Os filhos sao adicionados dentro do contexto de declaracao deles.
    Adiciona a subarvore com declaracoes de variaveis a raiz.
    @param ctx Contexto da secao de declaracao de variaveis.
    */
    @Override
    public AST visitVars_sect(Vars_sectContext ctx) {	 
        varsRoot = AST.newSubtree(VAR_LIST_NODE, NO_TYPE);
		if (ctx.getChildCount() > 0) {
	    	visit(ctx.opt_var_decl());
            varsRoot.varTable = variableTable;
		}

        root.varTable = variableTable;
        root.addChild(varsRoot);
		return varsRoot;
    }	    


    @Override
    /**
    Visita contexto de declaracao de tipo para mudar o ultimo tipo visitado.
    Visita o no de declaracao de variavel para adicionar variaveis a raiz de vars.
    @param ctx Contexto de declaracao de variavel.
    */
    public AST visitVar_decl(Var_declContext ctx) {

        this.isArray = false;

        // Visita a declaração de tipo para definir a variável lastDeclType.
    	visit(ctx.type_spec());

        // Visita a declaração dos ids para colocar a variável na tabela de variáveis.
        visit(ctx.id_list());
		
        return AST.newSubtree(VAR_DECL_NODE, NO_TYPE);
    }

    @Override
    /**
    Adiciona id para AST de variaveis.
    @param ctx Contexto de id.
    */
    public AST visitId_node(Id_nodeContext ctx) {
        String id = ctx.getText();
        int line = (ctx.getStart().getLine());

        // Check if id is already in table
        if (variableTable.contains(id)) {
            System.out.println("Error: variable " + id + " already declared");
            passed = false;
            return null;  
        } else {
            // Add to table
            EntryInput entry;
            if(isArray){
                entry = new EntryArray(id, line, lastDeclType, start, end);
            }else{
                entry = new EntryInput(id, line, lastDeclType, false);
            }
  
            int idx = variableTable.addVar(entry);
         
            AST node = new AST(VAR_DECL_NODE, idx, lastDeclType);
            node.varTable = variableTable;
			varsRoot.addChild(node);         
			return node;
        }
    }

    @Override
    /**
    Visita contexto que atualiza o tamanho do array.
    @param ctx Contexto de array.
     */
    public AST visitArrayTypeDecl(ArrayTypeDeclContext ctx) {
        this.isArray = true;
        visit(ctx.array_start());
        visit(ctx.array_end());
        return null;
    }

    /*************************** DECLARACAO DE FUNCOES E PROCEDURES ***************************/

    @Override
    /**
    Visita secao de declaracao de funcoes e procedures. Similar as variaveis, as funcoes
    serao adicionadas dentro do contexto que ela é declarada. Os proximos contextos a serem 
    visitados sao fnc_sign_decl_list e procedures_decl_list.
    @param ctx Contexto de declaracao de funcoes e procedures.
    */
    public AST visitFnc_and_procedures_sect(Fnc_and_procedures_sectContext ctx) {
        funcRoot = AST.newSubtree(FUNC_LIST_NODE, NO_TYPE);
        visit(ctx.opt_fnc_and_procedures_sect());

        root.functionTable = functionTable;
        root.addChild(funcRoot);
        return this.funcRoot;
    }

    @Override
    /**
    Visita lista de declaracoes de funcoes e procedures. Verifica se cada contexto
    existe antes de visita-lo. O retorno de cada visit nao eh utilizado, as funcoes
    (procedures sao considerados como funcoes) sao adicionadas no contexto de
    proc_func_id_node.
    @param ctx Contexto de lista de declaracoes de funcoes e procedures.
     */
    public AST visitFnc_and_procedures(Fnc_and_proceduresContext ctx) {
        funcVarsRoot = AST.newSubtree(VAR_LIST_NODE, NO_TYPE);

        if (ctx.fnc_sign_decl_list() != null) {
            visit(ctx.fnc_sign_decl_list());
        } 

        if (ctx.procedures_decl_list() != null) {
            visit(ctx.procedures_decl_list());
        }

        return new AST(FUNC_LIST_NODE, 0, NO_TYPE);
    }

    @Override
    /**
    Visita declaracao de funcao. Se ja existe uma variavel com o mesmo nome da funcao
    ou se existe uma funcao ja declarada com o nome da funcao, ocorre erro. Caso contrario,
    cria Entry de funcao, visita secao de parametros e corpo da funcao, e adiciona as 
    variaveis locais ao Entry da funcao. Cada vez que visita uma declaracao de variavel,
    seja por parametro ou uma secao var dentro de funcao, no contexto proc_func_id_node,
    elas sao adicionadas a funcVarsRoot. No fim, eh criado um no AST de FUNC_DECL_NODE
    e eh adicionado a funcRoot. A tabela de varTables tambem eh atualizada, para que no
    printDot, seja possivel mostrar as variaveis da funcao.
    @param ctx Contexto de declaracao de funcao.
     */
    public AST visitFnc_sign_decl(Fnc_sign_declContext ctx) {
        AST func;

        this.lastEnteredScope = Scope.FUNCTION;
        String funcName = ctx.ID().getText();

        int startLine = ctx.getStart().getLine();
        visit(ctx.type_spec());

        // Variavavel com esse nome ja declarada
        if (variableTable.contains(funcName)) {
            System.out.println("Erro: there is already a variable with " + funcName + " name!");
            passed = false; // 
            return new AST(FUNC_DECL_NODE, -1, NO_TYPE);
        } else if  (functionTable.getFunc(funcName) != null) {
            // funcao ja declarada
            System.out.println("Erro: the function " + funcName + " has already been declared!");
            passed = false;
            return new AST(FUNC_DECL_NODE, -1, NO_TYPE);
        } else {
            // Cria entry de funcao
            EntryFunc newFunc = new EntryFunc(funcName, startLine, lastDeclType);
            this.lastFuncVisited = funcName;

            int idx = functionTable.addFunc(newFunc);
            func = new AST(FUNC_DECL_NODE, idx, lastDeclType);

            // Visita parametros para adicionar a lista de simbolos da funcao
            if (ctx.fnc_sign_params_sect().getChildCount() > 0) {
                visit(ctx.fnc_sign_params_sect());
            }

            // Visita sesao de vars para adicionar a lista de simnolos da funcao
            if (ctx.func_vars_sect().getChildCount() > 0) {
               visit(ctx.func_vars_sect());
            }

            func.addChild(funcVarsRoot);
            func.varTable = functionTable.getVarTable(idx);
        }

        funcRoot.addChild(func);
        this.lastEnteredScope = Scope.GLOBAL;
        return func;
    }

    @Override
    /** 
    Visita declaracao de procedures. Tem o mesmo comportamento que declaracao de funcao.
    @param ctx Contexto de declaracao de procedures.
     */
    public AST visitProcedures_decl(Procedures_declContext ctx) {
        this.lastEnteredScope = Scope.FUNCTION;

        String procName = ctx.ID().getText();
        int startLine = ctx.getStart().getLine();

        AST func;

        if (variableTable.contains(procName)) {
            System.out.println("Erro: there is already a variable with " + procName + " name!");
            passed = false; 
            return new AST(FUNC_DECL_NODE, -1, NO_TYPE);
        } else if  (functionTable.getFunc(procName) != null) {
            // funcao ja declarada
            System.out.println("Erro: the function " + procName + " has already been declared!");
            passed = false;
            return new AST(FUNC_DECL_NODE, -1, NO_TYPE);
        } else {
            EntryFunc newProc = new EntryFunc(procName, startLine, NO_TYPE);
            this.lastFuncVisited = procName;
            int idx = functionTable.addFunc(newProc);

            func = new AST(FUNC_DECL_NODE, idx, lastDeclType);
            // Visita parametros para adicionar a lista de simbolos da funcao
            if (ctx.procedure_params_sect() != null) {
                visit(ctx.procedure_params_sect());
            }

            System.out.println(funcVarsRoot);
            func.addChild(funcVarsRoot);
            func.varTable = functionTable.getVarTable(idx);
        }

        funcRoot.addChild(func);
        this.lastEnteredScope = Scope.GLOBAL;
        return func;
    }

    @Override
    /**
    Visita secao de parametros da declaracao de func/procedure. Visita type_spec 
    para atualizar o ultimo tipo visitado e a lista de ids para adicionar a 
    funcVarsRoot. O retorno nao eh utilizado no programa.
    @param ctx Contexto de secao de parametros da declaracao de func/procedure.
     */
    public AST visitFnc_sign_param_decl(Fnc_sign_param_declContext ctx) {
        
        this.isArray = false;

        // Visita a declaração de tipo para definir a variável lastDeclType.
    	visit(ctx.type_spec());

        // Visita a declaração dos ids para colocar a variável na tabela de variáveis.
        visit(ctx.proc_func_id_list());

    	return AST.newSubtree(VAR_DECL_NODE, NO_TYPE);
    }

    @Override 
    /**
    Visita secao de vars declaracao de funcoes.
    @param ctx Contexto de secao de vars da declaracao de funcoes.
     */
    public AST visitFunc_vars_sect(Func_vars_sectContext ctx) {
		if (ctx.getChildCount() > 0) {
	    	visit(ctx.func_opt_var_decl());
		}

		return AST.newSubtree(VAR_DECL_NODE, NO_TYPE);
    }
    

    @Override
    /**
    Visita declaracao de var de funcoes/procedures. Se a variavel ja foi declarada,
    seja globalmente ou localmente, retorna uma AST com intData = -1 e atualiza o 
    atributo passed para false, indicando que houve erro. Um no AST eh criado para
    variavel. Ela eh adicionara a variavel da funcao, utilizando lastFuncVisited 
    para encontra varTable da funcao. Eh atualizado a varTable da AST com a varTable
    da funcao. O no AST eh adicionado a funcVarsRoot. O retorno nao eh utilizado.
    @param ctx Contexto de declaracao de var de funcoes/procedures. 
     */
    public AST visitProc_func_id_node(Proc_func_id_nodeContext ctx) {
        String id = ctx.getText();
        int line = ctx.getStart().getLine();

        // Check if id is already in global table
        if (variableTable.contains(id)) {
            System.out.println("Error: variable " + id + " already declared");
            passed = false;
            return new AST(FUNC_DECL_NODE, -1, NO_TYPE);
 
        } else if (functionTable.funcContainsVar(lastFuncVisited, id)) {
            System.out.println("Error: variable " + id + " already declared in function");
            passed = false;
            return new AST(FUNC_DECL_NODE, -1, NO_TYPE);
        }  else {
            // Add to function table
            EntryInput entry;
            int idx;
            if(isArray){
                entry = new EntryArray(id, line, lastDeclType, start, end);
            }else{
                entry = new EntryInput(id, line, lastDeclType, false);
            }

            idx = functionTable.addVarToFunc(lastFuncVisited, entry);

            AST node = new AST(VAR_DECL_NODE, idx, lastDeclType);
            node.varTable = functionTable.getVarTable(lastFuncVisited);
            funcVarsRoot.addChild(node);
            return node; 
        }
        
    }

    /*************************** MANIPULACAO DO TIPO ***************************/

    @Override
    /**
    Atualiza o lastDeclType para BOOL_TYPE.
    @param ctx Contexto de tipo bool.
     */
    public AST visitBoolType(BoolTypeContext ctx) {
        this.lastDeclType = BOOL_TYPE;
        return new AST(TYPE_NODE, 0, BOOL_TYPE);
    }

    @Override
    /**
    Atualiza o lastDeclType para INT_TYPE.
    @param ctx Contexto de tipo inteiro.
     */
    public AST visitIntType(IntTypeContext ctx) {
        this.lastDeclType = INT_TYPE;
        return new AST(TYPE_NODE, 0, INT_TYPE);
    }

    @Override
    /**
    Atualiza o lastDeclType para REAL_TYPE.
    @param ctx Contexto de tipo real.
     */
    public AST visitRealType(RealTypeContext ctx) {
        this.lastDeclType = REAL_TYPE;
        return new AST(TYPE_NODE, 0, REAL_TYPE);
    }

    @Override
    /**
    Atualiza o lastDeclType para CHAR_TYPE.
    @param ctx Contexto de tipo char.
     */
    public AST visitCharType(CharTypeContext ctx) {
        this.lastDeclType = CHAR_TYPE;
        return new AST(TYPE_NODE, 0, CHAR_TYPE);
    }

    @Override
    /**
    Atualiza o atributo start, indicando o inicio do intervalo de indices.
    @param ctx Contexto de inicio de intervalo de indices.
     */
    public AST visitArray_start(Array_startContext ctx) {
        this.start = Integer.parseInt(ctx.getText());
        return null;
    }

    @Override
    /**
    Atualiza o atributo start, indicando o final do intervalo de indices.
    @param ctx Contexto de final de intervalo de indices.
     */
    public AST visitArray_end(Array_endContext ctx) {
        this.end = Integer.parseInt(ctx.getText());
        return null;
    }

    /*************************** DECLARACAO DE STATEMENTS ***************************/

    @Override
    public AST visitStmt_sect(Stmt_sectContext ctx) {
        AST stmtTree = AST.newSubtree(STMT_LIST_NODE, NO_TYPE);
        AST child;

        if (ctx.getChildCount() > 0) {
            child = visit(ctx.stmt_list());
            // TODO esta null
            System.out.println("dad");
            if (child != null) {
                stmtTree.addChild(child);
            }
            // stmtTree.addChild(child);
        }

        root.addChild(stmtTree);
        return stmtTree;
    }

    @Override
    public AST visitStmt_list(Stmt_listContext ctx) {
        // AST child;
        // if (ctx.getChildCount() > 0) {
        //     if (ctx.stmt() != null) {
        //         child = visit(ctx.stmt());
        //         if (child != null) {
        //             return child;
        //         }
        //     } else {
        //         return visit(ctx.stmt_list());
        //     }
        // } 
        // return stmtTree;
    }

    @Override
    public AST visitAssignStmtSimple(AssignStmtSimpleContext ctx) {
        System.out.println("visitAssignStmtSimple");
        String varName = ctx.ID().getText();
        Type varType;
        AST exprNode;

        if(variableTable.contains(varName)){
            varType = variableTable.getType(variableTable.getEntryId(varName));
        } else if (lastEnteredScope == Scope.FUNCTION && functionTable.funcContainsVar(lastFuncVisited, varName)) {
            varType = functionTable.getFuncVar(lastFuncVisited, varName).type;
        }else {
            System.out.println("Error: variable " + varName + " not declared");
            passed = false;
            return new AST(ASSIGN_NODE, -1, NO_TYPE);
        }
        
        exprNode = visit(ctx.expr());
        
        Type unif = varType.unifyAttrib(lastDeclType);

        if(unif == NO_TYPE){
            System.out.println("Error: expression type doesn't match variable type. Variable " + varName + " is of type " + varType + "while expression is "+lastDeclType+".\n");
            passed = false;
            return new AST(ASSIGN_NODE, -1, NO_TYPE);
        }
        
        AST assign = new AST(ASSIGN_NODE, 0, NO_TYPE);

        if(exprNode != null){
            assign.addChild(exprNode);
        } else {
            System.out.println("null expr");
            System.out.println(ctx.getText() + "\n\n\n");
        }
        return assign;
    }

    // @Override
    // public Type visitAssignStmtArray(AssignStmtArrayContext ctx) {
    //     String varName = ctx.ID().getText();
    //     Type indexType = visit(ctx.array_index());

    //     Type varType = NO_TYPE;

    //     if (indexType != INT_TYPE) {
    //         System.out.println("Error: array index must be an int");
    //         passed = false;
    //         return NO_TYPE;
    //     } else if (variableTable.containsKey(varName)) {
    //         varType = variableTable.getType(varName);
    //     } else if (lastEnteredScope == Scope.FUNCTION && functionTable.funcContainsVar(lastFuncVisited, varName)) {
    //         varType = functionTable.getFuncVar(lastFuncVisited, varName).type;
    //     } else {
    //         System.out.println("Error: variable " + varName + " not declared");
    //         passed = false;
    //         return NO_TYPE;
    //     }
               
    //     Type exprType = visit(ctx.expr());
    //     if (exprType != varType) {
    //         System.out.println("Error: expression type doesn't match variable type. Variable is of type " + varType + "while expression is "+exprType+".\n");
    //         passed = false;
    //     }        
        
    //     return NO_TYPE;
    // }

	// @Override
	// public Type visitExprArithmetic(ExprArithmeticContext ctx) {
	// 	Type l = visit(ctx.left);
	// 	Type r = visit(ctx.right);

    //     if (l == NO_TYPE || r == NO_TYPE) {
    //         return NO_TYPE;
    //     }

    //     if (l == STRING_TYPE || r == STRING_TYPE) {
    //         typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
    //         passed = false;
    //         return NO_TYPE;
    //     }

	// 	Type unif = NO_TYPE;
    //     int op = ctx.op.getType();
	// 	if (op == PASParser.PLUS || op == PASParser.MINUS || op == PASParser.TIMES || op == PASParser.OVER) {
	// 		unif = l.unifyArith(r);
	// 	} 

	// 	if (unif == NO_TYPE) {
	// 		typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
    //         passed = false;
    //         return NO_TYPE;
	// 	}

	// 	return unif;
	// }

    // @Override
    // public Type visitExprOpLogic(ExprOpLogicContext ctx) {

	// 	Type l = visit(ctx.left);
	// 	Type r = visit(ctx.right);

    //     if (l == NO_TYPE || r == NO_TYPE) {
    //         return NO_TYPE;
    //     }

    //     if (l == STRING_TYPE || r == STRING_TYPE) {
    //         typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
    //         passed = false;
    //         return NO_TYPE;
    //     }

    //     Type unif = NO_TYPE;
    //     int op = ctx.op.getType();

    //     if (op == PASParser.EQ || op == PASParser.NEQ || op == PASParser.LT || op == PASParser.LTE || op == PASParser.BT || op == PASParser.BTE || op == PASParser.AND || op == PASParser.OR) {
    //         unif = l.unifyComp(r);
    //     }

    //     if (unif == NO_TYPE) {
    //         typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
    //         passed = false;
    //         return NO_TYPE;
    //     }

    //     return unif;
    // }

    // @Override
    // public Type visitExprUnaryMinus(ExprUnaryMinusContext ctx) {

	// 	Type expr = visit(ctx.expr());
    //     if (expr == INT_TYPE || expr == REAL_TYPE) {
    //         return expr;
    //     } else {
    //         System.out.println("Error: unary minus can only be applied to int or real");
    //         passed = false;
    //         return NO_TYPE;
    //     }
    // }

    // @Override
    // public Type visitExprUnaryNot(ExprUnaryNotContext ctx) {
    //     Type expr = visit(ctx.expr());
        
    //     if (expr == BOOL_TYPE) {
    //         return expr;
    //     } else {
    //         System.out.println("Error: unary not can only be applied to bool");
    //         passed = false;
    //         return NO_TYPE;
    //     }
    // }

    // @Override
    // public Type visitExprDivMod(ExprDivModContext ctx) {
    //     Type l = visit(ctx.left);
	// 	Type r = visit(ctx.right);

    //     if (l == NO_TYPE || r == NO_TYPE) {
    //         return NO_TYPE;
    //     }

    //     if (l == STRING_TYPE || r == STRING_TYPE) {
    //         typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
    //         passed = false;
    //         return NO_TYPE;
    //     }

    //     Type unif = NO_TYPE;
    //     int op = ctx.op.getType();

    //     if (op == PASParser.DIV || op == PASParser.MOD) {
    //         unif = l.unifyIntDiv(r);
    //     }

    //     if (unif == NO_TYPE) {
    //         typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
    //         passed = false;
    //         return NO_TYPE;
    //     }

    //     return unif;
    // }

    @Override
    public AST visitExprIntVal(ExprIntValContext ctx) {
        int value = Integer.parseInt(ctx.INT_VAL().getText());
        lastDeclType = INT_TYPE;
        return new AST(TYPE_NODE, value, INT_TYPE);
    }

    @Override
    public AST visitExprRealVal(ExprRealValContext ctx) {
        float value = Float.parseFloat(ctx.REAL_VAL().getText());
        lastDeclType = REAL_TYPE;
        return new AST(TYPE_NODE, value, REAL_TYPE);
    }

    @Override
    public AST visitExprCharVal(ExprCharValContext ctx) {
        lastDeclType = CHAR_TYPE;

        String id = ctx.getText();
        int line = (ctx.getStart().getLine());

        EntryStr entry = new EntryStr(id, line);
        int idx = stringTable.addStr(entry);

        return new AST(TYPE_NODE, idx, CHAR_TYPE);        
    }

    @Override
    public AST visitExprBooleanValTrue(ExprBooleanValTrueContext ctx) {
        lastDeclType = BOOL_TYPE;
        return new AST(TYPE_NODE, 1, BOOL_TYPE);
    }

    @Override
    public AST visitExprBooleanValFalse(ExprBooleanValFalseContext ctx) {
        lastDeclType = BOOL_TYPE;
        return new AST(TYPE_NODE, 0, BOOL_TYPE);
    }

    @Override
    public AST visitExprId(ExprIdContext ctx) {
        String name = ctx.getText();

        if (variableTable.contains(name)) {
            int idx = variableTable.getEntryId(name);
            Type type = variableTable.getType(idx);

            return new AST(VAR_USE_NODE, idx, type);
        } else if (lastEnteredScope == Scope.FUNCTION && functionTable.funcContainsVar(lastFuncVisited, name)) {
            Type type = functionTable.getFuncVar(lastFuncVisited, name).type;
            int idx = functionTable.getVarTable(lastFuncVisited).getEntryId(name);

            return new AST(VAR_USE_NODE, idx, type);
        } else {
            System.out.println("Error: variable " + name + " not declared");
            passed = false;
            return new AST(VAR_USE_NODE, -1, NO_TYPE);
        }
    }

    // @Override
    // public Type visitExprArrayAccess(ExprArrayAccessContext ctx) {
    //     String name = ctx.ID().getText();
    //     Type index_type = visit(ctx.array_index());

    //     if (index_type != INT_TYPE) {
    //         System.out.println("Error: array index must be an int");
    //         passed = false;
    //         return NO_TYPE;
    //     } else if (variableTable.containsKey(name)) {
    //         return variableTable.getType(name);
    //     } else if (lastEnteredScope == Scope.FUNCTION && functionTable.funcContainsVar(lastFuncVisited, name)) {
    //         return functionTable.getFuncVar(lastFuncVisited, name).type;
    //     } else {
    //         System.out.println("Error: variable " + name + " not declared");
    //         passed = false;
    //         return NO_TYPE;
    //     }
    // }

    // // TODO
    // @Override
    // public Type visitExprFnc(ExprFncContext ctx) {
    //     // Pega o nome da função
    //     String funID = ctx.getChild(0).getChild(0).getText();

    //     if (functionTable.containsKey(funID)) {
    //         return functionTable.getFunc(funID).type;
    //     } else {
    //         System.out.println("Error: function " + funID + " not declared");
    //         passed = false;
    //         return NO_TYPE;
    //     }
    // }

    @Override
	public AST visitExprStrVal(ExprStrValContext ctx) {
		// Adiciona a string na tabela de strings.
        String id = ctx.getText();
        int line = (ctx.getStart().getLine());

        if (stringTable.contains(id)) {
            System.out.println("Error: string " + id + " already declared");
            passed = false;
            return new AST(STR_VAL_NODE, -1, STRING_TYPE); 
        } else {
            // Add to table
            EntryStr entry = new EntryStr(id, line);
            int idx = stringTable.addStr(entry);
            return new AST(STR_VAL_NODE, idx, STRING_TYPE);
        }
	}

    private void typeError(int lineNo, String op, Type t1, Type t2) {
    	System.out.printf("SEMANTIC ERROR (%d): incompatible types for operator '%s', LHS is '%s' and RHS is '%s'.\n",
    			lineNo, op, t1.toString(), t2.toString());
    	passed = false;
    }

    public void showTables() {
        System.out.println(variableTable);
        System.out.println("*******************************");
        System.out.println(functionTable);
        System.out.println("*******************************");
        System.out.println(stringTable);
        AST.printDot(this.root);
    }
}
