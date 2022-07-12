package ast;

/*
 * Creates a NodeKind for the AST (Pascal)
 */

public enum NodeKind {
    ASSIGN_NODE {
        public String toString() {
            return ":=";
        }
    },

    /* COMPARISONS */
    EQ_NODE {
        public String toString() {
            return "=";
        }
    },
    LT_NODE {
        public String toString() {
            return "<";
        }
    },
    GT_NODE {
        public String toString() {
            return ">";
        }
    },
    LE_NODE {
        public String toString() {
            return "<=";
        }
    },
    GE_NODE {
        public String toString() {
            return ">=";
        }
    },
    NEQ_NODE {
        public String toString() {
            return "<>";
        }
    },

    /* BLOCK */
    BLOCK_NODE {
        public String toString() {
            return "block";
        }
    },
    PROGRAM_NODE {
        public String toString() {
            return "program";
        }
    },
    STMT_LIST_NODE {
        public String toString() {
            return "stmt_list";
        }
    },
    

    /* IF */
    IF_NODE {
        public String toString() {
            return "if";
        }
    },
    
    /* TYPES */
    INT_VAL_NODE {
        public String toString() {
            return "";
        }
    },
    BOOL_VAL_NODE {
        public String toString() {
            return "";
        }
    },
    REAL_VAL_NODE {
        public String toString() {
            return "";
        }
    },
    CHAR_VAL_NODE {
        public String toString() {
            return "";
        }
    },
    STR_VAL_NODE {
        public String toString() {
            return "";
        }
    },
    VAR_DECL_NODE {
		public String toString() {
            return "var_decl";
        }
	},
    VAR_LIST_NODE {
		public String toString() {
            return "var_list";
        }
	},
    VAR_USE_NODE {
		public String toString() {
            return "var_use";
        }
	},
    NIL_VAL_NODE {
        public String toString() {
            return "";
        }
    },
    ARRAY_NODE {
        public String toString() {
            return "";
        }
    },
    FUNC_LIST_NODE {
        public String toString() {
            return "func_list";
        }
    },
    FUNC_USE_NODE {
        public String toString() {
            return "func_use";
        }
    },
    FUNC_DECL_NODE {
        public String toString() {
            return "func_decl";
        }
    },

    /* ARITHMETIC */
    MINUS_NODE {
        public String toString() {
            return "-";
        }
    },
    OVER_NODE {
        public String toString() {
            return "/";
        }
    },
    PLUS_NODE {
        public String toString() {
            return "+";
        }
    },
    TIMES_NODE {
        public String toString() {
            return "*";
        }
    },
    MOD_NODE {
        public String toString() {
            return "mod";
        }
    },
    DIV_NODE {
        public String toString() {
            return "div";
        }
    },
    
    /* TYPE CASTING */
    B2I_NODE { // Type conversion.
		public String toString() {
            return "B2I";
        }
	},
    B2R_NODE {
		public String toString() {
            return "B2R";
        }
	},
    B2S_NODE {
		public String toString() {
            return "B2S";
        }
	},
    I2R_NODE {
		public String toString() {
            return "I2R";
        }
	},
    I2S_NODE {
		public String toString() {
            return "I2S";
        }
	},
    R2S_NODE {
		public String toString() {
            return "R2S";
        }
	},

    /* SETTING TYPE */
    TYPE_NODE {
		public String toString() {
            return "Type";
        }
	},

    NO_NODE {
        public String toString() {
            return "";
        }
    },

    END_NODE {
        public String toString() {
            return "end";
        }
    };

    public static boolean hasData(NodeKind kind) {
        switch(kind) {
            case TYPE_NODE:
            case BOOL_VAL_NODE:
	        case INT_VAL_NODE:
	        case REAL_VAL_NODE:
            case CHAR_VAL_NODE:
	        case STR_VAL_NODE:
	        case VAR_DECL_NODE:
	        case VAR_USE_NODE:
            case FUNC_DECL_NODE:
            case FUNC_USE_NODE:
	            return true;
	        default:
	            return false;
        }
    }

    public static NodeKind getNodeKindArth(String op){
        switch(op){
            case "+":
                return PLUS_NODE;
            case "-":
                return MINUS_NODE;
            case "*":
                return TIMES_NODE;
            case "/":
                return OVER_NODE;
            case "div":
                return DIV_NODE;
            case "mod":
                return MOD_NODE;
            default:
                return NO_NODE;
        }
    }
    
}
