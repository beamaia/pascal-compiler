.data:
.text:
	ADDI $s0, $zero, 0x2
	LI $t0, 0
	LI $t1, 0
	ADDI $t2, $zero, 0x2
	ADD $t1, $t1, $t2
	MUL $t1, $t1, $s0
	ADD $t0, $t0, $t1
	MUL $t0, $t0, $s0
	ADD $s0, $zero, $t0
