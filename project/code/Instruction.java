package code;

import java.util.Formatter;

// Implementa a quadrupla de intrução de acordo com nossos OpCodes.
public final class Instruction {
    // OpCode da instrução.
    public final OpCode op;
    // Operandos da instrução.
    public int o1;
    public int o2;
    public int o3;

    public Instruction(OpCode op, int o1, int o2, int o3) {
        this.op = op;
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
    }

    // Retorna a instrução em forma de ASM.
    // Neste passo, necessário se certificar de que os operandos estão bem formatados
    // i.e: operandos com $ no início ou sem.
    public String toString() {
        StringBuilder sb = new StringBuilder();
		Formatter f = new Formatter(sb);
		f.format("%s", this.op.toString());
		if (this.op.opCount == 1) {
			f.format(" %d", this.o1);
		} else if (this.op.opCount == 2) {
			f.format(" %d, %d", this.o1, this.o2);
		} else if (this.op.opCount == 3) {
			f.format(" %d, %d, %d", this.o1, this.o2, this.o3);
		}
		f.close();
		return sb.toString();
    }

    // Neste momento, iremos supor que o número de registradores
    // é superior ao número real de registradores em uma máquina MIPS.
    public static final int INT_REGS_COUNT   = 32768;  // i0 to i32767: int registers.
    public static final int FLOAT_REGS_COUNT = 32768;	// f0 to f32767: float registers.
    // O mesmo faremos para a memória de instruções e de dados.
    public static final int INSTR_MEM_SIZE = 65536;	// instr_mem[]
    public static final int DATA_MEM_SIZE  = 65536;  // data_mem[]
}