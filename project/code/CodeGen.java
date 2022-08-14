package code;

import static code.Instruction.*;
import static code.OpCode.*;
import code.Registers.*;
import code.RegistersOrg.*;
import code.Registers;

import java.math.BigInteger;


import ast.*;
import tables.*;
import types.*;
import types.Type.*;

/*
 * Visitador da AST para gerar o código.
 */
public final class CodeGen extends ASTBaseVisitor<Integer> {
    private final Instruction code[]; // Memória
    private final StrTable st;
    private final VarTable vt;
    private RegistersOrg registers;

    // Contadores
    private static int nextInstr;      // contador de instrução
    private static int intRegsCount;   // contador de registrador (vamos supor inf)
    private static int floatRegsCount; // contador de registrador (vamos supor inf)

    public CodeGen(StrTable st, VarTable vt) {
        this.st = st;
        this.vt = vt;
        this.code = new Instruction[INSTR_MEM_SIZE];
        this.registers = new RegistersOrg();
    }

    // Inicialização e execução da visita da AST
    @Override
    public void execute(AST root) {
        nextInstr = 0;
        intRegsCount = 0;
        floatRegsCount = 0;

        // Prints data
        System.out.printf(".data:\n");
        dumpStrTable();

        System.out.printf(".main:\n");

        visit(root);
        emit(HALT);
        dumpProgram();
    }

    // Funções de print
    void dumpProgram() {
	    for (int addr = 0; addr < nextInstr; addr++) {
	    	System.out.printf("%s\n", code[addr].toString());
	    }
	}

    public static String removeFirstandLast(String str) {
 
        // Removing first and last character
        // of a string using substring() method
        str = str.substring(1, str.length() - 1);
 
        // Return the modified string
        return str;
    }

	void dumpStrTable() {
        String aux;

	    for (int i = 0; i < st.size(); i++) {
            aux = removeFirstandLast(st.getText(i));
	        System.out.printf("\tstring%05d: .asciiz \"%s\"\n", i, aux);
	    }
	}

    // Funções de emit de acordo com o tamanho de cada operando
    // (sobrecarga de métodos)
    private void emit(OpCode op, String o1, String o2, String o3) {
		Instruction instr = new Instruction(op, o1, o2, o3);
	    code[nextInstr] = instr;
	    nextInstr++;
	}
	
	private void emit(OpCode op) {
		emit(op, "", "", "");
	}
	
	private void emit(OpCode op, String o1) {
		emit(op, o1, "", "");
	}
	
	private void emit(OpCode op, String o1, String o2) {
		emit(op, o1, o2, "");
	}

    // Incrementadores de registradores
    private int newIntReg() {
        return intRegsCount++;
    }

    private int newFloatReg() {
        return floatRegsCount++;
    }

    // Visitadores especializados
    public static String floatToHex(float f) {
        // change the float to raw integer bits(according to the OP's requirement)
        int intValue = Float.floatToRawIntBits(f);
        String bin = Integer.toBinaryString(intValue);
        BigInteger b = new BigInteger(bin, 2);
        return "0x" + b.toString(16);
    }

    @Override
    protected Integer visitAssignNode(AST node) {
        AST lf = node.children.get(0);
        AST rt = node.children.get(1);
        OpCode op;
        String o1, o2, o3;
        o3 = "$zero";

        if (lf.kind == NodeKind.VAR_USE_NODE) {
            String var = lf.varTable.getName(lf.intData);

            if (lf.type == Type.INT_TYPE || lf.type == Type.BOOL_TYPE || lf.type == Type.CHAR_TYPE) {
                op = OpCode.ADDI;
            } else if (lf.type == Type.REAL_TYPE) {
                op = OpCode.ADD_F;
            } else {
                throw new RuntimeException("Tipo de variável não suportado");
            }


            if (registers.contains(var)) {
                if (op == OpCode.ADDI) {
                    o1 = "$r" + registers.getIntReg(var);
                } else {
                    o1 = "$f" + registers.getFloatReg(var);
                }

            } else {
                if (op == OpCode.ADDI) {
                    o1 = "$r" + registers.addIntReg(var);
                } else {
                    o1 = "$f" + registers.addFloatReg(var);
                }
            }

            if (rt.kind == NodeKind.INT_VAL_NODE || rt.kind == NodeKind.BOOL_VAL_NODE || rt.kind == NodeKind.CHAR_VAL_NODE) {
                o2 = "" + rt.intData; 
                emit(op, o1, o3, o2);
            } else if (rt.kind == NodeKind.REAL_VAL_NODE) {
                o2 = "" + rt.floatData;
                emit(OpCode.ADDI, "$t0", "$zero", floatToHex(rt.floatData));
                emit(op, o1, o3, "$t0");
            }
        }

        
        return -1;
    }

    @Override
    protected Integer visitEqNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitLtNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitGtNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitLeNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitGeNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitNeqNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitNotNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitAndNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitOrNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitWhileNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitProgramNode(AST node) {
        for (int i = 0; i < node.children.size(); i++) {
            visit(node.getChild(i));        }
        return -1;
    }

    @Override
    protected Integer visitStmtListNode(AST node) {
        for (int i = 0; i < node.children.size(); i++) {
            visit(node.getChild(i));
        }

        return -1;
    }

    @Override
    protected Integer visitIfNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitElseNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitIntValNode(AST node) {
        System.out.println("Integer value: " + node.intData);
        System.out.println(node.children.size());
        return -1;
    }

    @Override
    protected Integer visitBoolValNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitRealValNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitCharValNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitStrValNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitVarDeclNode(AST node) {
        return -1;
    }

    @Override
    protected Integer visitVarListNode(AST node) {
        // visit(node.getChild(0));
        for (int i = 0; i < node.children.size(); i++) {
            visit(node.getChild(i));
        }
        return -1;
    }

    @Override
    protected Integer visitVarUseNode(AST node) {
        for (int i = 0; i < node.children.size(); i++) {
            visit(node.getChild(i));
        }
        return -1;
    }

    @Override
    protected Integer visitNilValNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitArrayNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitArrayElmtNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitArrayIndexNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitFuncListNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitFuncUseNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitFuncDeclNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitMinusNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitOverNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitTimesNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitModNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitDivNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitB2INode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitB2RNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitB2SNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitI2RNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitI2SNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitR2SNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitTypeNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitNoNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

    @Override
    protected Integer visitEndNode(AST node) {
        visit(node.getChild(0));
        return -1;
    }

}