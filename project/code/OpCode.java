package code;

/*
 * Enumeração das instruções aceitas pelo MIPS.
 * A referência utilizada foi: http://alumni.cs.ucr.edu/~vladimir/cs161/mips.html
 */
public enum OpCode {
    
    // Arithmetic and Logical Instructions
    ADD(name: "ADD", opCount: 3),
    ADDU(name: "ADDU", opCount: 3),
    ADDI(name: "ADDI", opCount: 3),
    ADDIU(name: "ADDIU", opCount: 3),
    AND(name: "AND", opCount: 3),
    ANDI(name: "ANDI", opCount: 3),

    DIV(name: "DIV", opCount: 2),
    DIVU(name: "DIVU", opCount: 2),
    
    MULT(name: "MULT", opCount: 2),
    MULTU(name: "MULTU", opCount: 2),

    NOR(name: "NOR", opCount: 3),
    OR(name: "OR", opCount: 3),
    ORI(name: "ORI", opCount: 3),

    SLL(name: "SLL", opCount: 3),
    SLLV(name: "SLLV", opCount: 3),

    SRA(name: "SRA", opCount: 3),
    SRAV(name: "SRAV", opCount: 3),

    SRL(name: "SRL", opCount: 3),
    SRLV(name: "SRLV", opCount: 3),

    SUB(name: "SUB", opCount: 3),
    SUBU(name: "SUBU", opCount: 3),

    XOR(name: "XOR", opCount: 3),
    XORI(name: "XORI", opCount: 3),

    // Constant-Manipulating Instructions
    LHI(name: "LHI", opCount: 2),
    LLO(name: "LLO", opCount: 2),

    // Comparison Instructions
    SLT(name: "SLT", opCount: 3),
    SLTU(name: "SLTU", opCount: 3),
    SLTI(name: "SLTI", opCount: 3),
    SLTIU(name: "SLTIU", opCount: 3),

    // Branch Instructions
    BEQ(name: "BEQ", opCount: 3),
    BGTZ(name: "BGTZ", opCount: 2),
    BLEZ(name: "BLEZ", opCount: 2),
    BNE(name: "BNE", opCount: 3),

    // Jump Instructions
    J(name: "J", opCount: 1),
    JAL(name: "JAL", opCount: 1),
    JALR(name: "JALR", opCount: 1),
    JR(name: "JR", opCount: 1),

    // Load Instructions
    LB(name: "LB", opCount: 3),
    LBU(name: "LBU", opCount: 3),
    LH(name: "LH", opCount: 3),
    LHU(name: "LHU", opCount: 3),
    LW(name: "LW", opCount: 3),

    // Store Instructions
    SB(name: "SB", opCount: 3),
    SH(name: "SH", opCount: 3),
    SW(name: "SW", opCount: 3),

    // Data Movement Instructions
    MFHI(name: "MFHI", opCount: 1),
    MFLO(name: "MFLO", opCount: 1),
    MTHI(name: "MTHI", opCount: 1),
    MTLO(name: "MTLO", opCount: 1),

    // Exception and Interrupt Instructions
    TRAP(name: "TRAP", opCount: 1);


    public final String name;
	public final int opCount;
	
	private OpCode(String name, int opCount) {
		this.name = name;
		this.opCount = opCount;
	}
	
	public String toString() {
		return this.name;
	}
}
