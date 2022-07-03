package checker;

import types.Type;
import static types.Type.INT_TYPE;
import static types.Type.REAL_TYPE;
import static types.Type.BOOL_TYPE;
import static types.Type.CHAR_TYPE;
import static types.Type.NO_TYPE;
import static types.Type.STRING_TYPE;

import org.antlr.v4.runtime.Token;

import parser.PASParser;
import parser.PASParserBaseVisitor;
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
import parser.PASParser.ExprArithmeticContext;
import parser.PASParser.ExprDivContext;
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

import parser.PASParser.Fnc_sign_declContext;
import parser.PASParser.Fnc_sign_param_declContext;
import parser.PASParser.Proc_func_id_nodeContext;
import parser.PASParser.Procedures_declContext;

import parser.PASParser.Assign_stmtContext;

import tables.EntryInput;
import tables.EntryStr;
import tables.EntryArray;
import tables.EntryFunc;
import tables.StrTable;
import tables.VarTable;
import tables.FuncTable;

import checker.Scope;

public class SemanticChecker extends PASParserBaseVisitor<Type> {
    
    Type lastDeclType;
    Scope lastEnteredScope = Scope.GLOBAL;

    private StrTable stringTable = new StrTable();
    private VarTable variableTable = new VarTable();
    private FuncTable functionTable = new FuncTable();

    private boolean passed = true;
    private boolean isArray = false;
    
    private int start;
    private int end;

    private String lastFuncVisited;
    
    @Override
    public Type visitVar_decl(Var_declContext ctx) {

        this.isArray = false;

        // Visita a declaração de tipo para definir a variável lastDeclType.
    	visit(ctx.type_spec());

        // Visita a declaração dos ids para colocar a variável na tabela de variáveis.
        visit(ctx.id_list());

    	return NO_TYPE;
    }

    @Override
    public Type visitFnc_sign_decl(Fnc_sign_declContext ctx) {
        this.lastEnteredScope = Scope.FUNCTION;

        String funcName = ctx.ID().getText();
        int startLine = ctx.getStart().getLine();

        if (variableTable.containsKey(funcName)) {
            System.out.println("There is already a variable with " + funcName + " name!");
            passed = false;
            return NO_TYPE;
        }
        else if  (functionTable.containsKey(funcName)) {
            System.out.println("The function " + funcName + " has already been declared!");
            passed = false;
            return NO_TYPE;
        } else {
            EntryFunc newFunc = new EntryFunc(funcName, startLine);
            this.lastFuncVisited = funcName;
            functionTable.addFunc(newFunc);

            // Visita parametros para adicionar a lista de simbolos da funcao
            visit(ctx.fnc_sign_params_sect());
            visit(ctx.func_vars_sect());
        }

        this.lastEnteredScope = Scope.GLOBAL;
        return NO_TYPE;
    }

    @Override
    public Type visitProcedures_decl(Procedures_declContext ctx) {
        this.lastEnteredScope = Scope.FUNCTION;

        String procName = ctx.ID().getText();
        int startLine = ctx.getStart().getLine();

        if (variableTable.containsKey(procName)) {
            System.out.println("There is already a variable with " + procName + " name!");
            passed = false;
            return NO_TYPE;
        }
        else if  (functionTable.containsKey(procName)) {
            System.out.println("The procedure " + procName + " has already been declared!");
            passed = false;
            return NO_TYPE;
        } else {
            EntryFunc newProc = new EntryFunc(procName, startLine);
            this.lastFuncVisited = procName;
            functionTable.addFunc(newProc);

            // Visita parametros para adicionar a lista de simbolos da funcao
            visit(ctx.procedure_params_sect());
        }

        this.lastEnteredScope = Scope.GLOBAL;
        return NO_TYPE;
    }

    @Override
    public Type visitFnc_sign_param_decl(Fnc_sign_param_declContext ctx) {
        
        this.isArray = false;

        // Visita a declaração de tipo para definir a variável lastDeclType.
    	visit(ctx.type_spec());

        // Visita a declaração dos ids para colocar a variável na tabela de variáveis.
        visit(ctx.proc_func_id_list());

    	return NO_TYPE;
    }

    @Override
    public Type visitProc_func_id_node(Proc_func_id_nodeContext ctx) {
        String id = ctx.getText();
        int line = ctx.getStart().getLine();

        
        // Check if id is already in global table
        if (variableTable.containsKey(id)) {
            System.out.println("Error: variable " + id + " already declared");
            passed = false;
            return NO_TYPE;  
        } else if (functionTable.funcContainsVar(lastFuncVisited, id)) {
            System.out.println("Error: variable " + id + " already declared in function");
            passed = false;
            return NO_TYPE;  
        }  else {
            // Add to function table
            EntryInput entry;
            if(isArray){
                entry = new EntryArray(id, line, lastDeclType, start, end);
            }else{
                entry = new EntryInput(id, line, lastDeclType, false);
            }
            functionTable.addVarToFunc(lastFuncVisited, entry);
        }
        
        return NO_TYPE;   
    }

    @Override
    public Type visitBoolType(BoolTypeContext ctx) {
        this.lastDeclType = BOOL_TYPE;
        return BOOL_TYPE;
    }

    @Override
    public Type visitIntType(IntTypeContext ctx) {
        this.lastDeclType = INT_TYPE;
        return INT_TYPE;
    }

    @Override
    public Type visitRealType(RealTypeContext ctx) {
        this.lastDeclType = REAL_TYPE;
        return REAL_TYPE;
    }

    @Override
    public Type visitCharType(CharTypeContext ctx) {
        this.lastDeclType = CHAR_TYPE;
        return CHAR_TYPE;
    }

    @Override
    public Type visitId_node(Id_nodeContext ctx) {
        String id = ctx.getText();
        int line = (ctx.getStart().getLine());

        
        // Check if id is already in table
        if (variableTable.containsKey(id)) {
            System.out.println("Error: variable " + id + " already declared");
            passed = false;
            return NO_TYPE;  
        } else {
            // Add to table
            EntryInput entry;
            if(isArray){
                entry = new EntryArray(id, line, lastDeclType, start, end);
            }else{
                entry = new EntryInput(id, line, lastDeclType, false);
            }
            variableTable.addVar(entry);
        }
        
        return NO_TYPE;
    }

    @Override
    public Type visitArrayTypeDecl(ArrayTypeDeclContext ctx) {
        this.isArray = true;
        visit(ctx.array_start());
        visit(ctx.array_end());
        return NO_TYPE;
    }

    @Override
    public Type visitArray_start(Array_startContext ctx) {
        this.start = Integer.parseInt(ctx.getText());
        return NO_TYPE;
    }

    @Override
    public Type visitArray_end(Array_endContext ctx) {
        this.end = Integer.parseInt(ctx.getText());
        return NO_TYPE;
    }


    @Override
    public Type visitAssign_stmt(Assign_stmtContext ctx) {
        visit(ctx.expr());
        return NO_TYPE;
    }

	@Override
	public Type visitExprArithmetic(ExprArithmeticContext ctx) {
		Type l = visit(ctx.left);
		Type r = visit(ctx.right);

        if (l == NO_TYPE || r == NO_TYPE) {
            return NO_TYPE;
        }

        if (l == STRING_TYPE || r == STRING_TYPE) {
            typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
            passed = false;
            return NO_TYPE;
        }

		Type unif = NO_TYPE;
        int op = ctx.op.getType();
		if (op == PASParser.PLUS || op == PASParser.MINUS || op == PASParser.TIMES || op == PASParser.OVER) {
			unif = l.unifyArith(r);
		} 

		if (unif == NO_TYPE) {
			typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
            passed = false;
            return NO_TYPE;
		}

		return unif;
	}

    @Override
    public Type visitExprOpLogic(ExprOpLogicContext ctx) {

		Type l = visit(ctx.left);
		Type r = visit(ctx.right);

        if (l == NO_TYPE || r == NO_TYPE) {
            return NO_TYPE;
        }


        Type unif = NO_TYPE;
        int op = ctx.op.getType();

        if (op == PASParser.EQ || op == PASParser.NEQ || op == PASParser.LT || op == PASParser.LTE || op == PASParser.BT || op == PASParser.BTE || op == PASParser.AND || op == PASParser.OR) {
            unif = l.unifyComp(r);
        }

        if (unif == NO_TYPE) {
            typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
            passed = false;
            return NO_TYPE;
        }

        return unif;
    }

    // TODO
    public Type visitExprUnaryMinus(ExprUnaryMinusContext ctx) {
        return NO_TYPE;
    }

    // TODO
    public Type visitExprDiv(ExprDivContext ctx) {
        return NO_TYPE;
    }

    // TODO
    public Type visitExprLparRpar(ExprLparRparContext ctx) {
        return NO_TYPE;
    }

    // TODO
    public Type visitExprFnc(ExprFncContext ctx) {
        return NO_TYPE;
    }

    @Override
    public Type visitExprIntVal(ExprIntValContext ctx) {
        return INT_TYPE;
    }

    @Override
    public Type visitExprRealVal(ExprRealValContext ctx) {
        return REAL_TYPE;
    }

    @Override
    public Type visitExprCharVal(ExprCharValContext ctx) {
        return CHAR_TYPE;
    }

    @Override
    public Type visitExprId(ExprIdContext ctx) {
        String name = ctx.getText();

        if (variableTable.containsKey(name)) {
            System.out.println();
            return variableTable.getType(name);
        } else {
            System.out.println("Error: variable " + name + " not declared");
            passed = false;
            return NO_TYPE;
        }
    }

    public Type visitExprArrayAccess(ExprArrayAccessContext ctx) {
        System.out.println("visitin array expr" + ctx.getText());
        return NO_TYPE;
    }

    @Override
    public Type visitExprBooleanVal(ExprBooleanValContext ctx) {
        return BOOL_TYPE;
    }

    @Override
	public Type visitExprStrVal(ExprStrValContext ctx) {
		// Adiciona a string na tabela de strings.
        String id = ctx.getText();
        int line = (ctx.getStart().getLine());

        if (stringTable.containsKey(id)) {
            System.out.println("Error: string " + id + " already declared");
            passed = false;
            return NO_TYPE;
        } else {
            // Add to table
            EntryStr entry = new EntryStr(id, line);
            stringTable.addStr(entry);
        }

		return STRING_TYPE;
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