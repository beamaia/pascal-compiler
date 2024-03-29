package ast;

import static types.Type.NO_TYPE;

import java.util.ArrayList;
import java.util.List;

import tables.*;
import types.Type;


// Implementação dos nós da AST.
public class AST {
    public final NodeKind kind;
    public final int intData;
    public final float floatData;
    public final Type type;
    public final List<AST> children;

    private static int nr;
    public VarTable varTable;
    public static FuncTable functionTable;
    public static StrTable stringTable;

    private AST(NodeKind kind, int intData, float floatData, Type type, VarTable varTable) {
        this.kind = kind;
        this.intData = intData;
        this.floatData = floatData;
        this.type = type;
        this.children = new ArrayList<AST>();
        this.varTable = varTable;
    }

    // Quando eh inicializado um no de valor inteiro ou bool (ele usa 1 pra true e 0 pra false)
    public AST(NodeKind kind, int intData, Type type, VarTable varTable) {
        this(kind, intData, 0.0f, type, varTable);
    }
    
    // Quando eh inicializado um no de valor real
    public AST(NodeKind kind, float floatData, Type type, VarTable varTable) {
        this(kind, 0, floatData, type, varTable);
    }

    public void addChild(AST child) {
        // check if intData child is -1 (it means that it is a list)
        this.children.add(child);
    }

    public AST getChild(int idx) {
        try {
            return this.children.get(idx);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static AST newSubtree(NodeKind kind, Type type, VarTable varTable, AST... children) {
        AST node = new AST(kind, 0, type, varTable);
        for (AST child : children) {
            node.addChild(child);
        }
        return node;
    }

    public int printNodeDot() {
		int myNr = nr++;

	    System.err.printf("node%d[label=\"", myNr);
	    if (this.type != NO_TYPE) {
	    	System.err.printf("(%s) ", this.type.toString());
	    }
	    if (this.kind == NodeKind.VAR_DECL_NODE || this.kind == NodeKind.VAR_USE_NODE) {
	    	System.err.printf("%s@", varTable.getName(this.intData)); 
	    } else if (this.kind == NodeKind.FUNC_DECL_NODE || this.kind == NodeKind.FUNC_USE_NODE) {
            System.err.printf("%s@", functionTable.getName(this.intData)); 
        } else if (this.kind == NodeKind.ARRAY_ELMT_NODE) {
            System.err.printf("%s[]@", varTable.getName(this.intData));
        }
        else {
	    	System.err.printf("%s", this.kind.toString());
	    }
	    if (NodeKind.hasData(this.kind)) {
	        if (this.kind == NodeKind.REAL_VAL_NODE) {
	        	System.err.printf("%.2f", this.floatData);
	        } else if (this.kind == NodeKind.STR_VAL_NODE || this.kind == NodeKind.CHAR_VAL_NODE) {
	        	System.err.printf("@%d", this.intData);
	        } else {
	        	System.err.printf("%d", this.intData);
	        }
	    }
	    System.err.printf("\"];\n");

        for (AST child : this.children) {
            if(child != null){
                int childNr = child.printNodeDot();
                System.err.printf("node%d -> node%d;\n", myNr, childNr);
            }
        }
	    return myNr;
	}

  
    // Imprime a árvore toda em stderr.
	public static void printDot(AST tree) {
	    nr = 0;
	    System.err.printf("digraph {\ngraph [ordering=\"out\"];\n");
	    tree.printNodeDot();
	    System.err.printf("}\n");
	}
}
