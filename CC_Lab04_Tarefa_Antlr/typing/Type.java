package typing;

// Enumeração dos tipos primitivos que podem existir em EZLang.
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
            return "bool";
        }
	},
    cha {
		public String toString() {
            return "string";
        }
	},
	NO_TYPE { // Indica um erro de tipos.
		public String toString() {
            return "no_type";
        }
	};

	// Tabela de unificação de tipos primitivos para o
	// operador '+'.
	private static Type plus[][] = {
		{ INT_TYPE,  REAL_TYPE, INT_TYPE,  STR_TYPE },
		{ REAL_TYPE, REAL_TYPE, REAL_TYPE, STR_TYPE },
		{ INT_TYPE,  REAL_TYPE, BOOL_TYPE, STR_TYPE },
		{ STR_TYPE,  STR_TYPE,  STR_TYPE,  STR_TYPE }
	};
	
	public Type unifyPlus(Type that) {
		return plus[this.ordinal()][that.ordinal()];
	}
	
	// Tabela de unificação de tipos primitivos para os
	// demais operadores aritméticos.
	private static Type other[][] = {
		{ INT_TYPE,  REAL_TYPE, NO_TYPE, NO_TYPE },
		{ REAL_TYPE, REAL_TYPE, NO_TYPE, NO_TYPE },
		{ NO_TYPE,   NO_TYPE,   NO_TYPE, NO_TYPE },
		{ NO_TYPE,   NO_TYPE,   NO_TYPE, NO_TYPE }
	};

	public Type unifyOtherArith(Type that) {
	    return other[this.ordinal()][that.ordinal()];
	}

	// Tabela de unificação de tipos primitivos para os
	// operadores de comparação.
	private static Type comp[][] = {
		{ BOOL_TYPE, BOOL_TYPE, NO_TYPE, NO_TYPE },
		{ BOOL_TYPE, BOOL_TYPE, NO_TYPE, NO_TYPE },
		{ NO_TYPE,   NO_TYPE,   NO_TYPE, NO_TYPE },
		{ NO_TYPE,   NO_TYPE,   NO_TYPE, BOOL_TYPE}
	};

	public Type unifyComp(Type that) {
		return comp[this.ordinal()][that.ordinal()];
	}
}
