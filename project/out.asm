.data:
.text:
	ADDI $s0, $zero, 0x20
	ADDI $s1, $zero, 0x2
	LI $t0, 0
	LI $t1, 0
	LI $t2, 0
	LI $t3, 0
	ADDI $t3, $t3, 0x20
	ADD $t3, $t3, $s0
	ADD $t2, $t2, $t3
	DIV $t2, $t2, $s1
	ADD $t1, $t1, $t2
	ADDI $t4, $zero, 0x2
	DIV $t1, $t1, $t4
	ADD $t0, $t0, $t1
	ADDI $t5, $zero, 0xa
	MUL $t0, $t0, $t5
	ADD $s0, $zero, $t0
