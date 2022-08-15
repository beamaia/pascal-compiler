package code;

/*
 * Enumeração das instruções aceitas pelo MIPS.
 * A referência utilizada foi: http://alumni.cs.ucr.edu/~vladimir/cs161/mips.html
 */
public enum OpCode {
    
    // Arithmetic and Logical Instructions
    ADD("ADD", 3),
    ADD_S("ADD.S", 3),
    ADDU("ADDU", 3),
    ADDI("ADDI", 3),
    ADDIU("ADDIU", 3),
    AND("AND", 3),
    ANDI("ANDI", 3),

    DIV("DIV", 3),
    DIV_S("DIV.S", 3),
    DIVU("DIVU", 3),
    
    MULT("MULT", 3),
    MULT_S("MULT.S", 3),
    MULTU("MULTU", 3),

    NOR("NOR", 3),
    OR("OR", 3),
    ORI("ORI", 3),

    SLL("SLL", 3),
    SLLV("SLLV", 3),

    SRA("SRA", 3),
    SRAV("SRAV", 3),

    SRL("SRL", 3),
    SRLV("SRLV", 3),

    SUB("SUB", 3),
    SUB_S("SUB.S", 3),
    SUBU("SUBU", 3),

    XOR("XOR", 3),
    XORI("XORI", 3),

    // Constant-Manipulating Instructions
    LHI("LHI", 2),
    LLO("LLO", 2),

    // Comparison Instructions
    SLT("SLT", 3),
    SLTU("SLTU", 3),
    SLTI("SLTI", 3),
    SLTIU("SLTIU", 3),

    // Branch Instructions
    BEQ("BEQ", 3),
    BGTZ("BGTZ", 2),
    BLEZ("BLEZ", 2),
    BNE("BNE", 3),

    // Jump Instructions
    J("J", 1),
    JAL("JAL", 1),
    JALR("JALR", 1),
    JR("JR", 1),

    // Load Instructions
    LB("LB", 3),
    LBU("LBU", 3),
    LH("LH", 3),
    LHU("LHU", 3),
    LW("LW", 3),

    // Store Instructions
    SB("SB", 3),
    SH("SH", 3),
    SW("SW", 3),

    // Data Movement Instructions
    MFHI("MFHI", 1),
    MFLO("MFLO", 1),
    MTHI("MTHI", 1),
    MTLO("MTLO", 1),

    // Exception and Interrupt Instructions
    TRAP("TRAP", 1),
    HALT("HALT", 1);

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
