package code;

import static code.Instruction.*;
import static code.OpCode.*;
import code.Registers.*;
import code.RegistersOrg.*;
import code.Registers;

import java.math.BigInteger;
import java.util.Stack;
import java.io.PrintWriter;

import ast.*;
import tables.*;
import types.*;
import types.Type.*;

/*
 * Visitador da AST para gerar o código.
 */
public final class CodeGen extends ASTBaseVisitor<AST> {
    private final Instruction code[]; // Memória
    private final StrTable st;
    private final VarTable vt;
    private RegistersOrg registers;
    private PrintWriter writer;

    private Stack<AST> call_stack;

    private AST caller;

    // Contadores
    private static int nextInstr; // contador de instrução
    private static int intRegsCount; // contador de registrador (vamos supor inf)
    private static int floatRegsCount; // contador de registrador (vamos supor inf)

    public CodeGen(StrTable st, VarTable vt) {
        this.st = st;
        this.vt = vt;
        this.code = new Instruction[INSTR_MEM_SIZE];
        this.registers = new RegistersOrg();
        this.call_stack = new Stack<AST>();
    }

    // Inicialização e execução da visita da AST
    @Override
    public void execute(AST root) {
        nextInstr = 0;
        intRegsCount = 0;
        floatRegsCount = 0;

        try {
            writer = new PrintWriter("out.asm", "UTF-8");
        } catch (Exception e) {
        }

        // Prints data
        writer.printf(".data:\n");
        dumpStrTable();

        writer.printf(".main:\n");

        visit(root);
        // emit(HALT);
        dumpProgram();

        writer.close();
    }

    // Funções de print
    void dumpProgram() {
        for (int addr = 0; addr < nextInstr; addr++) {
            writer.printf("\t%s\n", code[addr].toString());
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
            writer.printf("\tstring%05d: .asciiz \"%s\"\n", i, aux);
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

    @Override
    protected AST visitAssignNode(AST node) {
        AST lf = node.children.get(0);
        AST rt = node.children.get(1);
        OpCode op;
        String o1, o2, o3;
        o3 = "$zero";
        call_stack.push(lf);
        visit(rt);

        if (lf.kind == NodeKind.VAR_USE_NODE) {
            String var = lf.varTable.getName(lf.intData);

            // TODO conserta pra char
            // Muda addi pra li (load int)
            if (lf.type == Type.INT_TYPE || lf.type == Type.BOOL_TYPE || rt.kind == NodeKind.CHAR_VAL_NODE) {
                op = OpCode.ADDI;
            } else if (lf.type == Type.REAL_TYPE) {
                op = OpCode.ADD_S;
            } else {
                throw new RuntimeException("Tipo de variável não suportado"); // TODO ver se fica como exception msm
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

            // Lidando com tipos primitivos
            if (rt.kind == NodeKind.INT_VAL_NODE || rt.kind == NodeKind.BOOL_VAL_NODE) {
                o2 = "" + Word.integerToHex(rt.intData);
                emit(op, o1, o3, o2);
            } else if (rt.kind == NodeKind.REAL_VAL_NODE) {
                o2 = "" + rt.floatData;
                emit(OpCode.ADDI, "$t0", "$zero", Word.floatToHex(rt.floatData));
                emit(op, o1, o3, "$t0");
            }
        }
        call_stack.pop();
        // TODO levanta erro

        return node;
    }

    @Override
    protected AST visitEqNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitLtNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitGtNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitLeNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitGeNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitNeqNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitNotNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitAndNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitOrNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitWhileNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitProgramNode(AST node) {
        for (int i = 0; i < node.children.size(); i++) {
            visit(node.getChild(i));
        }
        return node;
    }

    @Override
    protected AST visitStmtListNode(AST node) {
        for (int i = 0; i < node.children.size(); i++) {
            visit(node.getChild(i));
        }

        return node;
    }

    @Override
    protected AST visitIfNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitElseNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitIntValNode(AST node) {
        // writer.println("Integer value: " + node.intData);
        // writer.println(node.children.size());
        return node;
    }

    @Override
    protected AST visitBoolValNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitRealValNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitCharValNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitStrValNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitVarDeclNode(AST node) {
        return node;
    }

    @Override
    protected AST visitVarListNode(AST node) {

        for (int i = 0; i < node.children.size(); i++) {
            visit(node.getChild(i));
        }
        return node;
    }

    @Override
    protected AST visitVarUseNode(AST node) {
        for (int i = 0; i < node.children.size(); i++) {
            visit(node.getChild(i));
        }
        return node;
    }

    @Override
    protected AST visitNilValNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitArrayNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitArrayElmtNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitArrayIndexNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitFuncListNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitFuncUseNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitFuncDeclNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitMinusNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitOverNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitPlusNode(AST node) {
        AST lf = node.children.get(0);
        AST rt = node.children.get(1);
        OpCode op;
        String o1, o2, o3 = "$zero";

        if (node.type == Type.INT_TYPE || node.type == Type.BOOL_TYPE) {
            op = OpCode.ADD;
        } else if (node.type == Type.REAL_TYPE) {
            op = OpCode.ADD_S;
        } else {
            throw new RuntimeException("Invalid type for + operator");
        }

        System.out.println(call_stack.peek().kind);

        if (registers.contains(call_stack.peek().varTable.getName(call_stack.peek().intData))) {
            if (op == OpCode.ADD) {
                o1 = "$r" + registers.getIntReg(call_stack.peek().varTable.getName(call_stack.peek().intData));
            } else {
                o1 = "$f" + registers.getFloatReg(call_stack.peek().varTable.getName(call_stack.peek().intData));
            }

        } else {
            if (op == OpCode.ADD) {
                o1 = "$r" + registers.addIntReg(call_stack.peek().varTable.getName(call_stack.peek().intData));
            } else {
                o1 = "$f" + registers.addFloatReg(call_stack.peek().varTable.getName(call_stack.peek().intData));
            }
        }

        // Se for uma variável, declara-se o registrador
        // para ter o valor armanezado na memória

        String var = lf.varTable.getName(lf.intData);

        if (lf.type == Type.INT_TYPE || lf.type == Type.BOOL_TYPE || rt.kind == NodeKind.CHAR_VAL_NODE) {
            op = OpCode.ADDI;
        } else if (lf.type == Type.REAL_TYPE) {
            op = OpCode.ADD_S;
        } else {
            throw new RuntimeException("Tipo de variável não suportado"); // TODO ver se fica como exception msm
        }

        if (registers.contains(var)) {
            if (op == OpCode.ADDI) {
                o2 = "$r" + registers.getIntReg(var);
            } else {
                o2 = "$f" + registers.getFloatReg(var);
            }

        } else {
            if (op == OpCode.ADDI) {
                o2 = "$r" + registers.addIntReg(var);
            } else {
                o2 = "$f" + registers.addFloatReg(var);
            }
        }

        if (lf.kind == NodeKind.INT_VAL_NODE || lf.kind == NodeKind.BOOL_VAL_NODE) {
            o2 = "" + Word.integerToHex(lf.intData);

        } else if (rt.kind == NodeKind.REAL_VAL_NODE) {
            o2 = "" + Word.floatToHex(lf.floatData);
        }

        System.out.println(o2);

        var = rt.varTable.getName(rt.intData);

        if (registers.contains(var)) {
            if (op == OpCode.ADDI) {
                o3 = "$r" + registers.getIntReg(var);
            } else {
                o3 = "$f" + registers.getFloatReg(var);
            }

        } else {
            if (op == OpCode.ADDI) {
                o3 = "$r" + registers.addIntReg(var);
            } else {
                o3 = "$f" + registers.addFloatReg(var);
            }
        }

        if (rt.kind == NodeKind.INT_VAL_NODE || rt.kind == NodeKind.BOOL_VAL_NODE) {
            o3 = "" + Word.integerToHex(rt.intData);

        } else if (rt.kind == NodeKind.REAL_VAL_NODE) {
            o3 = "" + Word.floatToHex(rt.floatData);
        }

        emit(op, o1, o2, o3);

        return node;
    }

    @Override
    protected AST visitTimesNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitModNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitDivNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitB2INode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitB2RNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitB2SNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitI2RNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitI2SNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitR2SNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitTypeNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitNoNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

    @Override
    protected AST visitEndNode(AST node) {
        if (node.getChild(0) != null)
            visit(node.getChild(0));
        return node;
    }

}