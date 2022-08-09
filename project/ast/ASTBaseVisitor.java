package ast;

/*
 * Classe abstrata que define a interface do visitor para a AST.
 * Implementa o despacho do método 'visit' conforme o 'kind' do nó.
 * Com isso, basta herdar desta classe para criar um interpretador
 * ou gerador de código.
 */
public abstract class ASTBaseVisitor<T> {
    // Metodo que começa a visita da AST a partír do nó raiz
    public void execute(AST root) {
        visit(root);
    }

    // Para cada "Kind" de um nó, executa o método especializado
    protected T visit(AST node) {
        switch (node.kind) {
            case ASSIGN_NODE:
                return visitAssignNode(node);

            case EQ_NODE:
                return visitEqNode(node);
            case LT_NODE:
                return visitLtNode(node);
            case GT_NODE:
                return visitGtNode(node);
            case LE_NODE:
                return visitLeNode(node);
            case GE_NODE:
                return visitGeNode(node);
            case NEQ_NODE:
                return visitNeqNode(node);
            case NOT_NODE:
                return visitNotNode(node);
            case AND_NODE:
                return visitAndNode(node);
            case OR_NODE:
                return visitOrNode(node);

            case WHILE_NODE:
                return visitWhileNode(node);
            case PROGRAM_NODE:
                return visitProgramNode(node);
            case STMT_LIST_NODE:
                return visitStmtListNode(node);

            case IF_NODE:
                return visitIfNode(node);
            case ELSE_NODE:
                return visitElseNode(node);

            case INT_VAL_NODE:
                return visitIntValNode(node);
            case BOOL_VAL_NODE:
                return visitBoolValNode(node);
            case REAL_VAL_NODE:
                return visitRealValNode(node);
            case CHAR_VAL_NODE:
                return visitCharValNode(node);
            case STR_VAL_NODE:
                return visitStrValNode(node);
            case VAR_DECL_NODE:
                return visitVarDeclNode(node);
            case VAR_LIST_NODE:
                return visitVarListNode(node);
            case VAR_USE_NODE:
                return visitVarUseNode(node);
            case NIL_VAL_NODE:
                return visitNilValNode(node);
            case ARRAY_NODE:
                return visitArrayNode(node);
            case ARRAY_ELMT_NODE:
                return visitArrayElmtNode(node);
            case ARRAY_INDEX_NODE:
                return visitArrayIndexNode(node);
            case FUNC_LIST_NODE:
                return visitFuncListNode(node);
            case FUNC_USE_NODE:
                return visitFuncUseNode(node);
            case FUNC_DECL_NODE:
                return visitFuncDeclNode(node);

            case MINUS_NODE:
                return visitMinusNode(node);
            case OVER_NODE:
                return visitOverNode(node);
            case TIMES_NODE:
                return visitTimesNode(node);
            case MOD_NODE:
                return visitModNode(node);
            case DIV_NODE:
                return visitDivNode(node);

            case B2I_NODE:
                return visitB2INode(node);
            case B2R_NODE:
                return visitB2RNode(node);
            case B2S_NODE:
                return visitB2SNode(node);
            case I2R_NODE:
                return visitI2RNode(node);
            case I2S_NODE:
                return visitI2SNode(node);
            case R2S_NODE:
                return visitR2SNode(node);

            case TYPE_NODE:
                return visitTypeNode(node);
            case NO_NODE:
                return visitNoNode(node);
            case END_NODE:
                return visitEndNode(node);
            
            default:
                System.err.printf("Invalid kind: %s!\n", node.kind.toString());
                System.exit(1);
                return null;
        }
    }

    protected abstract T visitAssignNode(AST node);

    protected abstract T visitEqNode(AST node);

    protected abstract T visitLtNode(AST node);

    protected abstract T visitGtNode(AST node);

    protected abstract T visitLeNode(AST node);

    protected abstract T visitGeNode(AST node);

    protected abstract T visitNeqNode(AST node);

    protected abstract T visitNotNode(AST node);

    protected abstract T visitAndNode(AST node);

    protected abstract T visitOrNode(AST node);

    protected abstract T visitWhileNode(AST node);

    protected abstract T visitProgramNode(AST node);

    protected abstract T visitStmtListNode(AST node);

    protected abstract T visitIfNode(AST node);

    protected abstract T visitElseNode(AST node);

    protected abstract T visitIntValNode(AST node);

    protected abstract T visitBoolValNode(AST node);

    protected abstract T visitRealValNode(AST node);

    protected abstract T visitCharValNode(AST node);

    protected abstract T visitStrValNode(AST node);

    protected abstract T visitVarDeclNode(AST node);

    protected abstract T visitVarListNode(AST node);

    protected abstract T visitVarUseNode(AST node);

    protected abstract T visitNilValNode(AST node);

    protected abstract T visitArrayNode(AST node);

    protected abstract T visitArrayElmtNode(AST node);

    protected abstract T visitArrayIndexNode(AST node);

    protected abstract T visitFuncListNode(AST node);

    protected abstract T visitFuncUseNode(AST node);

    protected abstract T visitFuncDeclNode(AST node);

    protected abstract T visitMinusNode(AST node);

    protected abstract T visitOverNode(AST node);

    protected abstract T visitTimesNode(AST node);

    protected abstract T visitModNode(AST node);

    protected abstract T visitDivNode(AST node);

    protected abstract T visitB2INode(AST node);

    protected abstract T visitB2RNode(AST node);

    protected abstract T visitB2SNode(AST node);

    protected abstract T visitI2RNode(AST node);

    protected abstract T visitI2SNode(AST node);

    protected abstract T visitR2SNode(AST node);

    protected abstract T visitTypeNode(AST node);

    protected abstract T visitNoNode(AST node);

    protected abstract T visitEndNode(AST node);
}