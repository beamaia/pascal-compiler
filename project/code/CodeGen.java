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
    private Stack<String> register_stack;

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
        this.register_stack = new Stack<String>();
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

        writer.printf(".text:\n");
        //writer.printf("\tmain\n");

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
    void setAstRegister(AST r) {
        String var = r.varTable.getName(r.intData);
        if (registers.contains(var)) 
            if (r.type == Type.INT_TYPE) {
                r.register = "$s" + registers.getIntReg(var);
            } else {
                r.register = "$f" + registers.getFloatReg(var);
            }
        else
            if (r.type == Type.INT_TYPE) {
                r.register = "$s" + registers.addIntReg(var);
            } else {
                r.register = "$f" + registers.addFloatReg(var);
            }
    }

    @Override
    protected AST visitAssignNode(AST node) {
        if (node.getChild(0) == null)
            return null;
        
        AST r = node.children.get(0);
        call_stack.push(r);
        AST e = node.children.get(1);
        visit(e);
        // get the register name
        this.setAstRegister(r);

        // adds register to stack
        register_stack.push(r.register);

        OpCode op = OpCode.ADDI;
        String o1 = r.register;
        String o2, o3 = "$zero";

        // checks if last register in stack is the same as r
        if(call_stack.peek() == r) {
            o2 = "" + Word.integerToHex(e.intData);
            emit(op, o1, o3, o2);
        } else {
            op = OpCode.ADD;
            AST fst_aux = call_stack.pop();
            o2 = fst_aux.register;
            if(call_stack.peek() == r) {
                emit(op, o1, o3, o2);
            }
        }

        call_stack.pop();
        register_stack.pop();
        return node;
    }

    @Override
    protected AST visitEqNode(AST node) {
        if (node.getChild(0) == null)
            return null;

        AST r = node.children.get(0);
        AST l = node.children.get(1);

        String o1, o2, o3;
        int data1, data2;

        // left operand and right operand should be the same type (not considering float as possibilities)
        if (r.type == l.type) {
            if (r.kind == NodeKind.VAR_USE_NODE) {
                o2 = "$s" + registers.getIntReg(r.varTable.getName(r.intData));
            } else if (r.type.toString() == "") {
                data1 = r.intData;
                o2 = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
                emit(OpCode.ADDI, o2, "$zero", Word.integerToHex(data1));
            }

            if (l.kind == NodeKind.VAR_USE_NODE) {
                o3 = "$s" + registers.getIntReg(r.varTable.getName(l.intData));
            } else if (l.type.toString() == "") {
                data2 = l.intData;
                o3 = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
                emit(OpCode.ADDI, o3, "$zero", Word.integerToHex(data2));
            }
        }


        OpCode op = OpCode.SUB;

        node.register = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");


        call_stack.push(node);
    
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
            return null;
        


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
        if (node.getChild(0) == null)
            return null;
        
        AST conditional = node.getChild(0);
        AST then = node.getChild(1);

        // register for conditional expression
        node.register = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
        emit(OpCode.LI, node.register, "1"); // true value

        visit(conditional);
        AST conditionalResponse = call_stack.pop();
        String o2 = conditionalResponse.register;

        // current instruction counter to contabilize the jump after "then"
        int currentNextInstr = nextInstr + 0;
        // writing the jump instruction
        emit(OpCode.BEQ, node.register, o2, currentNextInstr + "");
        // counting "then" statement
        visit(then);
        // counting the jump after "then"
        int elapsedNextInstr = nextInstr - currentNextInstr;
        // overwriting the jump instruction
        code[currentNextInstr].o3 = elapsedNextInstr + "";

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
        if (node.getChild(0) != null)
            visit(node.getChild(0));
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
        setAstRegister(node);
        //visit(node.getChild(i));
        //for (int i = 0; i < node.children.size(); i++) {
        //    System.out.println("Used: " + node.getChild(i).intData);
        //}
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
        if (node.getChild(0) == null)
            return null; // aqui deveria fazer Throw de soma invalida
        
        AST r = node.children.get(0);
        AST l = node.children.get(1);
        OpCode op = OpCode.ADDI;


        
        call_stack.push(node);
        node.register = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
        emit(OpCode.LI, node.register, "0");
        register_stack.push(node.register);

        
        if(r.kind.toString() == "" && l.kind.toString() == "") {
            emit(op, node.register, node.register, String.valueOf(r.intData));

            emit(OpCode.SUB, node.register, node.register, String.valueOf(l.intData));
            
            return node;
        }
        
        AST rv = visit(r);

        if(call_stack.peek() == node) {
            if(rv.register.length() > 0) {
                op = OpCode.ADD;
                emit(op, node.register, node.register, rv.register);
            } else {
                op = OpCode.ADDI;             
                emit(op, node.register, node.register, String.valueOf(rv.intData));
            }
        } else {
            op = OpCode.ADD;
            AST fst = call_stack.pop();
            String o2 = fst.register;
            emit(op, node.register, node.register, o2);
        }

        AST lv = visit(l);
        

        
        if(call_stack.peek() == node) {
            if(lv.register.length() > 0) {
                op = OpCode.SUB;
                emit(op, node.register, node.register, lv.register);
            } else {
                op = OpCode.ADDI;
                emit(op, node.register, node.register, String.valueOf(-lv.intData));
            }
        } else {
            op = OpCode.ADD;
            AST fst = call_stack.pop();
            String o2 = fst.register;
            emit(op, node.register, node.register, o2);
        }

        return node;
    }

    @Override
    protected AST visitOverNode(AST node) {
        if (node.getChild(0) == null)
            return null; // aqui deveria fazer Throw de soma invalida
        
        AST r = node.children.get(0);
        AST l = node.children.get(1);
        OpCode op = OpCode.ADD;
        
        call_stack.push(node);
        node.register = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
        emit(OpCode.LI, node.register, "0");
        register_stack.push(node.register);

        
        
        AST rv = visit(r);
        
        if(r.kind.toString() == "" && l.kind.toString() == "") {
            emit(op, node.register, node.register, String.valueOf(r.intData));
            emit(OpCode.DIV, node.register, node.register, String.valueOf(l.intData));
            
            return node;
        }
        
        if(call_stack.peek() == node) {
            if(rv.register.length() > 0) {
                op = OpCode.ADD;
                emit(op, node.register, node.register, rv.register);
            } else {
                op = OpCode.ADDI;
                String aux = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
                emit(op, aux, "$zero", Word.integerToHex(rv.intData));
                op = OpCode.ADD;
                emit(op, node.register, node.register, aux);
            }
        } else {
            op = OpCode.ADD;
            AST fst = call_stack.pop();
            String o2 = fst.register;
            emit(op, node.register, node.register, o2);
        }

        AST lv = visit(l);        

        
        if(call_stack.peek() == node) {
            if(lv.register.length() > 0) {
                op = OpCode.DIV;
                emit(op, node.register, node.register, lv.register);
            } else {
                op = OpCode.ADDI;
                String aux = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
                emit(op, aux, "$zero", Word.integerToHex(lv.intData));
                op = OpCode.DIV;
                emit(op, node.register, node.register, aux);
            }
        } else {
            op = OpCode.ADD;
            AST fst = call_stack.pop();
            String o2 = fst.register;
            emit(op, node.register, node.register, o2);
        }

        return node;
    }

    @Override
    protected AST visitPlusNode(AST node) {
        if (node.getChild(0) == null)
            return null; // aqui deveria fazer Throw de soma invalida
        
        AST r = node.children.get(0);
        AST l = node.children.get(1);
        OpCode op = OpCode.ADDI;
        
        call_stack.push(node);
        node.register = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
        emit(OpCode.LI, node.register, "0");
        register_stack.push(node.register);

        if(r.kind.toString() == "" && l.kind.toString() == "") {
            emit(op, node.register, node.register, String.valueOf(r.intData));
            emit(op, node.register, node.register, String.valueOf(l.intData));
            
            return node;
        }

        AST rv = visit(r);

        if(call_stack.peek() == node) {
            if(rv.register.length() > 0) {
                op = OpCode.ADD;
                emit(op, node.register, node.register, rv.register);
            } else {
                op = OpCode.ADDI;
                emit(op, node.register, node.register, Word.integerToHex(rv.intData));
            }
        } else {
            op = OpCode.ADD;
            AST fst = call_stack.pop();
            String o2 = fst.register;
            emit(op, node.register, node.register, o2);
        }

        AST lv = visit(l);
        

        
        if(call_stack.peek() == node) {
            if(lv.register.length() > 0) {
                op = OpCode.ADD;
                emit(op, node.register, node.register, lv.register);
            } else {
                op = OpCode.ADDI;
                emit(op, node.register, node.register, Word.integerToHex(lv.intData));
            }
        } else {
            op = OpCode.ADD;
            AST fst = call_stack.pop();
            String o2 = fst.register;
            emit(op, node.register, node.register, o2);
        }

        return node;
    }

    @Override
    protected AST visitTimesNode(AST node) {
        if (node.getChild(0) == null)
            return null; // aqui deveria fazer Throw de soma invalida
        
        AST r = node.children.get(0);
        AST l = node.children.get(1);
        OpCode op = OpCode.ADD;
        
        call_stack.push(node);
        node.register = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
        emit(OpCode.LI, node.register, "0");
        register_stack.push(node.register);

        
        
        AST rv = visit(r);
        
        if(r.kind.toString() == "" && l.kind.toString() == "") {
            emit(op, node.register, node.register, String.valueOf(r.intData));
            emit(OpCode.MUL, node.register, node.register, String.valueOf(l.intData));
            
            return node;
        }
        

        if(call_stack.peek() == node) {
            if(rv.register.length() > 0) {
                op = OpCode.ADD;
                emit(op, node.register, node.register, rv.register);
            } else {
                op = OpCode.ADDI;
                String aux = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
                emit(op, aux, "$zero", Word.integerToHex(rv.intData));
                op = OpCode.ADD;
                emit(op, node.register, node.register, aux);
            }
        } else {
            op = OpCode.ADD;
            AST fst = call_stack.pop();
            String o2 = fst.register;
            emit(op, node.register, node.register, o2);
        }

        AST lv = visit(l);        

        
        if(call_stack.peek() == node) {
            if(lv.register.length() > 0) {
                op = OpCode.MUL;
                emit(op, node.register, node.register, lv.register);
            } else {
                op = OpCode.ADDI;
                String aux = "$t" + registers.addTempReg(registers.getTempRegAmount() + 1 + "");
                emit(op, aux, "$zero", Word.integerToHex(lv.intData));
                op = OpCode.MUL;
                emit(op, node.register, node.register, aux);
            }
        } else {
            op = OpCode.ADD;
            AST fst = call_stack.pop();
            String o2 = fst.register;
            emit(op, node.register, node.register, o2);
        }

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