.data:
	string00000: .asciiz "d"
.main:
	ADDI $t0, $zero, 0xc20a3333
	ADD.S $f0, $zero, $t0
	ADDI $r0, $zero, 0x2
	ADDI $r1, $zero, 0x5a
	ADDI $r1, $zero, 0x26cd
	ADDI $r1, 0x1, $r1
