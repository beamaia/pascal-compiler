package checker;

import static types.Type.INT_TYPE;
import static types.Type.REAL_TYPE;
import static types.Type.BOOL_TYPE;
import static types.Type.STRING;
import static types.Type.CHAR_TYPE;
import static types.Type.NO_TYPE;

import org.antlr.v4.runtime.Token;

import parser.PASParser;
import parser.PASParserBaseVisitor;
import parser.PASParser.IntTypeContext;
import parser.PASParser.RealTypeContext;
import parser.PASParser.BoolTypeContext;
import parser.PASParser.CharTypeContext;

import parser.PASParser.ExprOpLogicContext;
import parser.PASParser.ExprUnaryMinusContext;
import parser.PASParser.ExprArithmeticContext;
import parser.PASParser.ExprDivContext;
import parser.PASParser.ExprLparRparContext;
import parser.PASParser.ExprColonContext;
import parser.PASParser.ExprFncContext;
import parser.PASParser.ExprIntValContext;
import parser.PASParser.ExprRealValContext;
import parser.PASParser.ExprCharValContext;
import parser.PASParser.ExprStrValContext;
import parser.PASParser.ExprIdContext;
import parser.PASParser.ExprArrayAccessContext;
import parser.PASParser.ExprBooleanValContext;

import parser.PASParser.Var_declContext;

import tables.EntryInput;
import tables.StrTable;
import tables.VarTable;

import types.Type;

public class SemanticChecker extends PASParserBaseVisitor<Type> {
    
    Type lastDeclType;

    private StrTable stringTable;
    private VarTable variableTable = new VarTable();

    private boolean passed = true;
    
    
    @Override
    public Type visitVar_decl(Var_declContext ctx) {

        // Visita a declaração de tipo para definir a variável lastDeclType.
    	visit(ctx.type_spec());
        
        // For each id in the id_list, split string by comma
        String[] ids = ctx.id_list().getText().split(",");

        int line = (ctx.getStart().getLine());

        // For each id, add to variable table
        for (String id : ids) {
            // Check if id is already in table
           if (variableTable.containsKey(id)) {
               System.out.println("Error: variable " + id + " already declared");
               passed = false;
               return NO_TYPE;  
           } else {
                // Add to table
                variableTable.addVar(id, line ,lastDeclType);
            }
        }

        System.out.println(this.variableTable.toString());

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

    // // Visita a regra expr: expr op=(PLUS | MINUS) expr
	// @Override
	// public Type visitPlusMinus(PlusMinusContext ctx) {
	// 	Type l = visit(ctx.expr(0));
	// 	Type r = visit(ctx.expr(1));
	// 	if (l == NO_TYPE || r == NO_TYPE) {
	// 		return NO_TYPE;
	// 	}
	// 	Type unif;
	// 	// Aqui precisamos diferenciar entre '+' e '-', 
	// 	// por isso que a regra na gramática associa o nome 'op' ao
	// 	// operador.
	// 	if (ctx.op.getType() == EZParser.PLUS) {
	// 		unif = l.unifyPlus(r);
	// 	} else {
	// 		unif = l.unifyOtherArith(r);
	// 	}
	// 	if (unif == NO_TYPE) {
	// 		typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
	// 	}
	// 	return unif;
	// }

    private void typeError(int lineNo, String op, Type t1, Type t2) {
    	System.out.printf("SEMANTIC ERROR (%d): incompatible types for operator '%s', LHS is '%s' and RHS is '%s'.\n",
    			lineNo, op, t1.toString(), t2.toString());
    	passed = false;
    }

    public Type visitExprOpLogic(ExprOpLogicContext ctx) {

        Type l = visit(ctx.expr(0));
        Type r = visit(ctx.expr(1));

        if (l == NO_TYPE || r == NO_TYPE) {
            return NO_TYPE;
        }

        Type unif = l.unifyComp(r);

        if (unif == NO_TYPE) {
            typeError(ctx.op.getLine(), ctx.op.getText(), l, r);
        }
        return unif;
    }
    public Type visitExprUnaryMinus(ExprUnaryMinusContext ctx) {
        return NO_TYPE;
    }
    public Type visitExprArithmetic(ExprArithmeticContext ctx) {
        return NO_TYPE;
    }
    public Type visitExprDiv(ExprDivContext ctx) {
        return NO_TYPE;
    }
    public Type visitExprLparRpar(ExprLparRparContext ctx) {
        return NO_TYPE;
    }
    public Type visitExprColon(ExprColonContext ctx) {
        return NO_TYPE;
    }
    public Type visitExprFnc(ExprFncContext ctx) {
        return NO_TYPE;
    }
    public Type visitExprIntVal(ExprIntValContext ctx) {
        return INT_TYPE;
    }
    public Type visitExprRealVal(ExprRealValContext ctx) {
        return REAL_TYPE;
    }
    public Type visitExprCharVal(ExprCharValContext ctx) {
        return CHAR_TYPE;
    }
    public Type visitExprStrVal(ExprStrValContext ctx) {
        return STRING;
    }
    public Type visitExprId(ExprIdContext ctx) {
        return NO_TYPE;
    }
    public Type visitExprArrayAccess(ExprArrayAccessContext ctx) {
        return NO_TYPE;
    }
    public Type visitExprBooleanVal(ExprBooleanValContext ctx) {
        return NO_TYPE;
    }


    // public showTables() {

    // }

    
}