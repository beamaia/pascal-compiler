package types;

import types.UnifyRules;

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
    STRING {
        public String toString() {
            return "string";
        }
    };

    public Type unifyArith(Type that) {
        return UnifyRules.arithmetics[this.ordinal()][that.ordinal()];
    }
    
    public Type unifyIntDiv(Type that) {
        return UnifyRules.int_div[this.ordinal()][that.ordinal()];
    }
    
    public Type unifyComp(Type that) {
        return UnifyRules.comp[this.ordinal()][that.ordinal()];
    }
}