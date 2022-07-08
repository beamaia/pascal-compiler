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
import parser.PASParser.ExprBooleanValContext;

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

    private StrTable stringTable = new StrTable();
    private VarTable variableTable = new VarTable();
    private FuncTable functionTable = new FuncTable();

    private boolean passed = true;
    private boolean isArray = false;
    
    // Last declared array indexes
    private int start;
    private int end;

    private String lastFuncVisited;


    /**
     * Visita programa e cria no da arvore AST
     * @param ctx ProgramContext
     */
    @Override
    public AST visitProgram(ProgramContext ctx) {
        this.root = AST.newSubtree(PROGRAM_NODE, NO_TYPE);
        // Cada tabela de simbolos eh criada como uma sub-arvore da raiz da arvore AST
 
        // funcSect = AST.newSubtree(FUNC_LIST_NODE, NO_TYPE); 
        // stmtSect = AST.newSubtree(STMT_LIST_NODE, NO_TYPE);

        // Visita cada secao da arvore e adiciona a sub-arvore criada ao no da raiz da arvore AST
        // System.out.println(ctx.func_sect.size());

        // Visita sessao de declaracao de variaveis
        if (ctx.vars_sect() != null) {
            visit(ctx.vars_sect());
		    root.addChild(varsRoot);
        }

        // sorry
        
        // Visita sessão de funções e procedures
        if (ctx.func_and_procedures_sect() != null) {
            visit(ctx.func_and_procedures_sect());
            root.addChild(funcRoot);
        }

        /*
         * fnc_and_procedures_sect: opt_fnc_and_procedures_sect;
            opt_fnc_and_procedures_sect:
                | fnc_and_procedures_list;
            fnc_and_procedures_list: fnc_and_procedures_list fnc_and_procedures 
                | fnc_and_procedures;
            fnc_and_procedures: fnc_sign_decl_list | procedures_decl_list;
         */

        // Visita sessao de statements
        // if (ctx.stmt_sect() != null) {
        //     AST stmtSect = visit(ctx.stmt_sect());
        //     root.addChild(stmtSect); 
        // }
        
        return this.root;
    }
	
    @Override
    public AST visitVars_sect(Vars_sectContext ctx) {	 
        varsRoot = AST.newSubtree(VAR_LIST_NODE, NO_TYPE);
		if (ctx.getChildCount() > 0) {
	    	visit(ctx.opt_var_decl());
		}
        AST.varTable = variableTable;
        // System.out.println(varsRoot.printNodeDot());
		return varsRoot;
    }	    

    @Override
    public AST visitVar_decl(Var_declContext ctx) {

        this.isArray = false;

        // Visita a declaração de tipo para definir a variável lastDeclType.
    	visit(ctx.type_spec());

        // Visita a declaração dos ids para colocar a variável na tabela de variáveis.
        visit(ctx.id_list());
		
        return AST.newSubtree(VAR_DECL_NODE, NO_TYPE);
    }
// tava olhando o parser do zambom ai confundi
    public AST visitFnc_and_procedures_sect(Fnc_and_procedures_sectContext ctx) {
        funcRoot = 
        if (ctx.opt_fnc_and_procedures_sect() != null) {
            visit(ctx.opt_fnc_and_procedures_sect());
        }
    }

    
    // sim, mas isso é facil de checar
    // saquei, então tem que ter um metodo novo? pro opt_fnc_and_procedures_sect
    
    
    @Override
    public AST visitFnc_sign_decl(Fnc_sign_declContext ctx) {
        this.lastEnteredScope = Scope.FUNCTION;

        String funcName = ctx.ID().getText();
        int startLine = ctx.getStart().getLine();
        visit(ctx.type_spec());

        // Variavavel com esse nome ja declarada
        if (variableTable.containsKey(funcName)) {
            System.out.println("Erro: there is already a variable with " + funcName + " name!");
            passed = false; // 
            return new AST(FUNC_DECL_NODE, -1, NO_TYPE);
        } else if  (functionTable.containsKey(funcName)) {
            // funcao ja declarada
            System.out.println("Erro: the function " + funcName + " has already been declared!");
            passed = false;
            return new AST(FUNC_DECL_NODE, -1, NO_TYPE);
        } else {
            EntryFunc newFunc = new EntryFunc(funcName, startLine, lastDeclType);
            this.lastFuncVisited = funcName;
            functionTable.addFunc(newFunc);

            // Visita parametros para adicionar a lista de simbolos da funcao
            visit(ctx.fnc_sign_params_sect());
            visit(ctx.func_vars_sect());
        }
        this.lastEnteredScope = Scope.GLOBAL;
        return new AST(FUNC_DECL_NODE, 0, NO_TYPE);
    }

    // @Override
    // public Type visitProcedures_decl(Procedures_declContext ctx) {
    //     this.lastEnteredScope = Scope.FUNCTION;

    //     String procName = ctx.ID().getText();
    //     int startLine = ctx.getStart().getLine();

    //     if (variableTable.containsKey(procName)) {
    //         System.out.println("Erro: there is already a variable with " + procName + " name!");
    //         passed = false;
    //         return NO_TYPE;
    //     } else if  (functionTable.containsKey(procName)) {
    //         System.out.println("Erro: the procedure " + procName + " has already been declared!");
    //         passed = false;
    //         return NO_TYPE;
    //     } else {
    //         EntryFunc newProc = new EntryFunc(procName, startLine, NO_TYPE);
    //         this.lastFuncVisited = procName;
    //         functionTable.addFunc(newProc);

    //         // Visita parametros para adicionar a lista de simbolos da funcao
    //         visit(ctx.procedure_params_sect());
    //     }

    //     this.lastEnteredScope = Scope.GLOBAL;
    //     return NO_TYPE;
    // }

    // @Override
    // public Type visitFnc_sign_param_decl(Fnc_sign_param_declContext ctx) {
        
    //     this.isArray = false;

    //     // Visita a declaração de tipo para definir a variável lastDeclType.
    // 	visit(ctx.type_spec());

    //     // Visita a declaração dos ids para colocar a variável na tabela de variáveis.
    //     visit(ctx.proc_func_id_list());

    // 	return NO_TYPE;
    // }

    // @Override
    // public Type visitProc_func_id_node(Proc_func_id_nodeContext ctx) {
    //     String id = ctx.getText();
    //     int line = ctx.getStart().getLine();

        
    //     // Check if id is already in global table
    //     if (variableTable.containsKey(id)) {
    //         System.out.println("Error: variable " + id + " already declared");
    //         passed = false;
    //         return NO_TYPE;  
    //     } else if (functionTable.funcContainsVar(lastFuncVisited, id)) {
    //         System.out.println("Error: variable " + id + " already declared in function");
    //         passed = false;
    //         return NO_TYPE;  
    //     }  else {
    //         // Add to function table
    //         EntryInput entry;
    //         if(isArray){
    //             entry = new EntryArray(id, line, lastDeclType, start, end);
    //         }else{
    //             entry = new EntryInput(id, line, lastDeclType, false);
    //         }
    //         functionTable.addVarToFunc(lastFuncVisited, entry);
    //     }
        
    //     return NO_TYPE;   
    // }

    @Override
    public AST visitBoolType(BoolTypeContext ctx) {
        this.lastDeclType = BOOL_TYPE;
        return new AST(TYPE_NODE, 0, BOOL_TYPE);
    }

    @Override
    public AST visitIntType(IntTypeContext ctx) {
        this.lastDeclType = INT_TYPE;
        return new AST(TYPE_NODE, 0, INT_TYPE);
    }

    @Override
    public AST visitRealType(RealTypeContext ctx) {
        this.lastDeclType = REAL_TYPE;
        return new AST(TYPE_NODE, 0, REAL_TYPE);
    }

    @Override
    public AST visitCharType(CharTypeContext ctx) {
        this.lastDeclType = CHAR_TYPE;
        return new AST(TYPE_NODE, 0, CHAR_TYPE);
    }

    @Override
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
  
            System.out.println("Adding variable " + entry.toString() + " to table");
            int idx = variableTable.addVar(entry);
         
            AST node = new AST(VAR_DECL_NODE, idx, lastDeclType);
			varsRoot.addChild(node);

            System.out.println("Size:" + varsRoot.children.size());
         
			return node;
        }
    }

    // @Override
    // public Type visitArrayTypeDecl(ArrayTypeDeclContext ctx) {
    //     this.isArray = true;
    //     visit(ctx.array_start());
    //     visit(ctx.array_end());
    //     return NO_TYPE;
    // }

    // @Override
    // public Type visitArray_start(Array_startContext ctx) {
    //     this.start = Integer.parseInt(ctx.getText());
    //     return NO_TYPE;
    // }

    // @Override
    // public Type visitArray_end(Array_endContext ctx) {
    //     this.end = Integer.parseInt(ctx.getText());
    //     return NO_TYPE;
    // }

    // @Override
    // public Type visitAssignStmtSimple(AssignStmtSimpleContext ctx) {
    //     String varName = ctx.ID().getText();
    //     Type varType, exprType;

    //     if(variableTable.containsKey(varName)){
    //         varType = variableTable.getType(varName);
    //     } else if (lastEnteredScope == Scope.FUNCTION && functionTable.funcContainsVar(lastFuncVisited, varName)) {
    //         varType = functionTable.getFuncVar(lastFuncVisited, varName).type;
    //     }else {
    //         System.out.println("Error: variable " + varName + " not declared");
    //         passed = false;
    //         return NO_TYPE;
    //     }
        
    //     exprType = visit(ctx.expr());
        
    //     if(exprType != varType){
    //         System.out.println("Error: expression type doesn't match variable type. Variable is of type " + varType + "while expression is "+exprType+".\n");
    //         passed = false;
    //     }
        
    //     return NO_TYPE;
    // }

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

    // @Override
    // public Type visitExprIntVal(ExprIntValContext ctx) {
    //     return INT_TYPE;
    // }

    // @Override
    // public Type visitExprRealVal(ExprRealValContext ctx) {
    //     return REAL_TYPE;
    // }

    // @Override
    // public Type visitExprCharVal(ExprCharValContext ctx) {

    //     return CHAR_TYPE;
        
    // }

    // @Override
    // public Type visitExprBooleanVal(ExprBooleanValContext ctx) {
    //     return BOOL_TYPE;
    // }

    // @Override
    // public Type visitExprId(ExprIdContext ctx) {
    //     String name = ctx.getText();

    //     if (variableTable.containsKey(name)) {
    //         return variableTable.getType(name);
    //     } else if (lastEnteredScope == Scope.FUNCTION && functionTable.funcContainsVar(lastFuncVisited, name)) {
    //         return functionTable.getFuncVar(lastFuncVisited, name).type;
    //     } else {
    //         System.out.println("Error: variable " + name + " not declared");
    //         passed = false;
    //         return NO_TYPE;
    //     }
    // }

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
        System.out.println("oi");
        String id = ctx.getText();
        int line = (ctx.getStart().getLine());

        if (stringTable.contains(id)) {
            System.out.println("Error: string " + id + " already declared");
            passed = false;
            return new AST(STR_VAL_NODE, -1, STRING_TYPE); 
            // contains não é == lookup? saquei, deixa eu mudar
        } else {
            // Add to table
            EntryStr entry = new EntryStr(id, line);
            int idx = stringTable.addStr(entry);
            System.out.println(id + " " + idx);
            return new AST(STR_VAL_NODE, idx, STRING_TYPE);
        }
	}

    private void typeError(int lineNo, String op, Type t1, Type t2) {
    	System.out.printf("SEMANTIC ERROR (%d): incompatible types for operator '%s', LHS is '%s' and RHS is '%s'.\n",
    			lineNo, op, t1.toString(), t2.toString());
    	passed = false;
    }

    public Type showTables() {
        System.out.println(variableTable);
        System.out.println("*******************************");
        System.out.println(functionTable);
        System.out.println("*******************************");
        System.out.println(stringTable);
        return NO_TYPE;
    }
}
