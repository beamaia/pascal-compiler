.data:
	string00000: .asciiz "d"
.main:
	ADDI $t0, $zero, 0xc20a3333
	ADD.S $f0, $zero, $t0
	ADDI $r0, $zero, 2
	ADDI $r1, $zero, 10
	ADDI $r1, $zero, 9933
	ADDI $r1, $zero, -99999
	li $v0, 1
	mov.s $f12, $f0   # Move contents of register $f3 to register $f12
  	syscall
	li $v0, 1
 	move $a0, $t0
	syscall 
