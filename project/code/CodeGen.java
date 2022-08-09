package code;

import static code.Instruction.*;
import static code.OpCode.*;


import ast.*;
import tables.*;
import types.*;

/*
 * Visitador da AST para gerar o código.
 */
public final class CodeGen extends ASTBaseVisitor<Integer> {
    private final Instruction code[]; // Memória
    private final StrTable st;
    private final VarTable vt;

    // Contadores
    private static int nextInstr;      // contador de instrução
    private static int intRegsCount;   // contador de registrador (vamos supor inf)
    private static int floatRegsCount; // contador de registrador (vamos supor inf)

    public CodeGen(StrTable st, VarTable vt) {
        this.st = st;
        this.vt = vt;
        this.code = new Instruction[INSTR_MEM_SIZE];
    }

    // Inicialização e execução da visita da AST
    @Override
    public void execute(AST root) {
        nextInstr = 0;
        intRegsCount = 0;
        floatRegsCount = 0;
        dumpStrTable();
        visit(root);
        emit(TRAP);
        dumpProgram();
    }

    // Funções de print
    void dumpProgram() {
	    for (int addr = 0; addr < nextInstr; addr++) {
	    	System.out.printf("%s\n", code[addr].toString());
	    }
	}

	void dumpStrTable() {
	    for (int i = 0; i < st.size(); i++) {
	        System.out.printf("SSTR %s\n", st.getText(i));
	    }
	}

    // Funções de emit de acordo com o tamanho de cada operando
    // (sobrecarga de métodos)
    private void emit(OpCode op, int o1, int o2, int o3) {
		Instruction instr = new Instruction(op, o1, o2, o3);
	    code[nextInstr] = instr;
	    nextInstr++;
	}
	
	private void emit(OpCode op) {
		emit(op, 0, 0, 0);
	}
	
	private void emit(OpCode op, int o1) {
		emit(op, o1, 0, 0);
	}
	
	private void emit(OpCode op, int o1, int o2) {
		emit(op, o1, o2, 0);
	}

    // Incrementadores de registradores
    private int newIntReg() {
        return intRegsCount++;
    }

    private int newFloatReg() {
        return floatRegsCount++;
    }

    // Visitadores especializados

    @Override
    protected Integer visitAssignNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitEqNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitLtNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitGtNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitLeNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitGeNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitNeqNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitNotNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitAndNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitOrNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitWhileNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitProgramNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitStmtListNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitIfNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitElseNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitIntValNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitBoolValNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitRealValNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitCharValNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitStrValNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitVarDeclNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitVarListNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitVarUseNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitNilValNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitArrayNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitArrayElmtNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitArrayIndexNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitFuncListNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitFuncUseNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitFuncDeclNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitMinusNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitOverNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitTimesNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitModNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitDivNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitB2INode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitB2RNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitB2SNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitI2RNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitI2SNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitR2SNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitTypeNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitNoNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitEndNode(AST node) {
        return -1;
    }

}