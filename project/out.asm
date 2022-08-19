.data:
.text:
	ADDI $s0, $zero, 0x20
	LI $t0, 1
	LI $t1, 1
	J 2
	J 5
	LI $t1, 16
	LI $t2, 16
	BEQ $t1, $t2, -3
	LI $t1, 0
	BEQ $t0, $t1, 2
	ADDI $s0, $zero, 0x1e
	ADDI $s0, $zero, 0x32
