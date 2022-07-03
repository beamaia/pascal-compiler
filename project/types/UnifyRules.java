package types;

import types.Type;


public class UnifyRules {
    // tabela de unificação de tipos primitivos para o operador '+', '-', '*', '/'
    public static Type arithmetics[][] = {
        { Type.INT_TYPE, Type.REAL_TYPE, Type.NO_TYPE, Type.NO_TYPE }, // INT PARA OUTROS
        { Type.REAL_TYPE, Type.REAL_TYPE, Type.NO_TYPE, Type.NO_TYPE }, // REAL PARA OUTROS
        { Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE }, // BOOLEAN PARA OUTROS
        { Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE } // STRING PARA OUTROS
    };

    // tabela de unificação do operador div
    public static Type int_div[][] = {
        { Type.INT_TYPE, Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE }, // INT PARA OUTROS
        { Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE }, // REAL PARA OUTROS
        { Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE }, // BOOLEAN PARA OUTROS
        { Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE } // STRING PARA OUTROS
    };

    // tabela de unificação de tipos primitivos para o operador '=', '<>', '<', '>', '<=', '>='
    public static Type comp[][] = {
        { Type.BOOL_TYPE, Type.BOOL_TYPE, Type.NO_TYPE, Type.NO_TYPE },
        { Type.BOOL_TYPE, Type.BOOL_TYPE, Type.NO_TYPE, Type.NO_TYPE },
        { Type.NO_TYPE, Type.NO_TYPE, Type.BOOL_TYPE, Type.NO_TYPE },
        { Type.NO_TYPE, Type.NO_TYPE, Type.NO_TYPE, Type.BOOL_TYPE }
    };

    
}