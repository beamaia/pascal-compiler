package types;

import types.Conv.*;
import static types.Conv.I2R;
import static types.Conv.NONE;


public enum Type {
    INT_TYPE {
        public String toString() {
            return "int";
        }
    },
    REAL_TYPE {
        public String toString() {
            return "real";
        }
    },
    BOOL_TYPE {
        public String toString() {
            return "boolean";
        }
    },
    CHAR_TYPE {
        public String toString() {
            return "char";
        }
    },
    NO_TYPE { // Indica um erro de tipos.
		public String toString() {
            return "no_type";
        }
	},
    IO_TYPE { // "Tipo" do writeln e readln
        public String toString() {
            return "io";
        }
    },
    STRING_TYPE {
        public String toString() {
            return "string";
        }
    };

    // tabela de unificação de tipos primitivos para o operador '+', '-', '*', '/'
    public static Unif arithmetics[][] = {
        { new Unif(INT_TYPE, NONE, NONE), new Unif(REAL_TYPE, I2R, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE)}, // INT PARA OUTROS
        { new Unif(REAL_TYPE, NONE, I2R), new Unif(REAL_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE)}, // REAL PARA OUTROS
        { new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(BOOL_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE)}, // BOOLEAN PARA OUTROS
		{ new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE),   new Unif(CHAR_TYPE, NONE, NONE)}  // CHAR PARA OUTROS
    };

    // tabela de unificação do operador div
    public static Unif int_div[][] = {
        { new Unif(INT_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE)}, // INT PARA OUTROS
        { new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE)}, // REAL PARA OUTROS
        { new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE)}, // BOOLEAN PARA OUTROS
		{ new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE)}  // CHAR PARA OUTROS
    };

    // tabela de unificação de tipos primitivos para o operador '=', '<>', '<', '>', '<=', '>='
    public static Unif comp[][] = {
 		{ new Unif(BOOL_TYPE, NONE, NONE), new Unif(BOOL_TYPE, I2R, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE)   }, // INT PARA OUTROS
		{ new Unif(BOOL_TYPE, NONE, I2R),  new Unif(BOOL_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE)   }, // REAL PARA OUTROS
		{ new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE),   new Unif(BOOL_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE)   }, // BOOLEAN PARA OUTROS
		{ new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE),   new Unif(BOOL_TYPE, NONE, NONE) }  // CHAR PARA OUTROS
    };

    // tabela de unificacoa de tipos primitivos para verificacao de widening
    public static Unif attrib[][] = {
        { new Unif(INT_TYPE, NONE, NONE), new Unif(REAL_TYPE, I2R, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE)}, // INT PARA OUTROS
        { new Unif(REAL_TYPE, NONE, I2R), new Unif(REAL_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE)}, // REAL PARA OUTROS
        { new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(BOOL_TYPE, NONE, NONE), new Unif(NO_TYPE, NONE, NONE)}, // BOOLEAN PARA OUTROS
		{ new Unif(NO_TYPE, NONE, NONE),  new Unif(NO_TYPE, NONE, NONE),   new Unif(NO_TYPE, NONE, NONE),   new Unif(CHAR_TYPE, NONE, NONE)}  // CHAR PARA OUTROS
    };

    public Unif unifyArith(Type that) {
        return arithmetics[this.ordinal()][that.ordinal()];
    }
    
    public Unif unifyIntDiv(Type that) {
        return int_div[this.ordinal()][that.ordinal()];
    }
    
    public Unif unifyComp(Type that) {
        return comp[this.ordinal()][that.ordinal()];
    }

    public Unif unifyAttrib(Type that) {
        return attrib[this.ordinal()][that.ordinal()];
    }
}