package code;

import java.util.Vector;

import tables.VarTable;

/*
 * Implementa um vetor de words (nossa memória)
 */
 public final class Memory extends Vector<Word> {
    // Memória com a tabela de variáveis.
    // OBS:. A memória foi alocada porém não preenchida.
    public Memory(VarTable vt) {
        for (int i = 0 ; i < vt.size() ; i++) {
            this.add(Word.fromInt(0));
        }
    }

    // Métodos para preencher e ler valores inteiros e reais.
    public void storei(int addr, int value) {
        this.set(addr, Word.fromInt(value));
    }

    public int loadi(int addr) {
        return this.get(addr).toInt();
    }

    public void storef(int addr, float value) {
        this.set(addr, Word.fromFloat(value));
    }

    public float loadf(int addr) {
        return this.get(addr).toFloat();
    }
 }