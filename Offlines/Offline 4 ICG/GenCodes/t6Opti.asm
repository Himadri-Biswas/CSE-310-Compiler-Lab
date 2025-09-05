.MODEL SMALL
.STACK 1000H
.DATA
	CR EQU 0DH
	LF EQU 0AH
	w DW 10 DUP (0000H)
.Code
main PROC
	MOV AX, @DATA
	MOV DS, AX
	;Source code line: 4 allocating 2 bytes for local variable i
	PUSH BP
	;Source code line: 5 allocating 20 bytes for array x
	MOV BP, SP
	;Source code line: 6 loading integer constant 0
	PUSH BX
	;Source code line: 6 accessing array element w
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	SUB SP, 20
	;Source code line: 6 loading global array element w
	MOV BX, 0
	;Pushing array element value and index for potential assignment
	SHL BX, 1
	MOV AX, w[BX]
	;Source code line: 6 loading integer constant 2
	PUSH AX
	;Source code line: 6 evaluating unary minus operation
; 	PUSH 2 ; PUSH/POP converted to MOV
; 	POP AX
	PUSH BX
	MOV AX, 2
	;Source code line: 6 assigning value to variable w[0]
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 6 getting array element address for assignment to w
	;Source code line: 6 storing value in global array element w[BX]
	NEG AX
	;Source code line: 6 cleaning expression result from stack
	POP BX
	;Source code line: 7 loading integer constant 0
	MOV w[BX], AX
	;Source code line: 7 accessing array element x
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	POP AX
	;Source code line: 7 loading local array element x
	MOV BX, 0
	SHL BX, 1
	NEG BX
	ADD BX, -4
	PUSH BP
	ADD BP, BX
	MOV BX, BP
	;Pushing array element value and index for potential assignment
	MOV AX, [BP]
	POP BP
	;Source code line: 7 loading integer constant 0
	PUSH AX
	;Source code line: 7 accessing array element w
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH BX
	;Source code line: 7 loading global array element w
	MOV BX, 0
	;Pushing array element value and index for potential assignment
	SHL BX, 1
	MOV AX, w[BX]
	;Source code line: 7 cleanup address from stack for w[0]
; 	PUSH BX ; PUSH/POP pair eliminated
; 	POP BX
	;Source code line: 7 assigning value to variable x[0]
	;Source code line: 7 getting array element address for assignment to x
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 7 storing value in local array element x[BX]
	POP BX
	PUSH BP
	MOV BP, BX
	;Source code line: 7 cleaning expression result from stack
	MOV [BP], AX
	;Source code line: 8 using local variable i
	POP BP
	;Source code line: 8 loading integer constant 0
	POP AX
	;Source code line: 8 accessing array element x
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH [BP+-2]
	;Source code line: 8 loading local array element x
	MOV BX, 0
	SHL BX, 1
	NEG BX
	ADD BX, -4
	PUSH BP
	ADD BP, BX
	MOV BX, BP
	;Pushing array element value and index for potential assignment
	MOV AX, [BP]
	POP BP
	;Source code line: 8 cleanup address from stack for x[0]
; 	PUSH BX ; PUSH/POP pair eliminated
; 	POP BX
	;Source code line: 8 assigning value to variable i
	;Source code line: 8 storing value in local variable i
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 8 cleaning expression result from stack
	;Source code line: 9 printing local variable i
	MOV [BP+-2], AX
	POP AX
	;Source code line: 10 loading integer constant 1
	PUSH [BP+-2]
	;Source code line: 10 accessing array element x
; 	PUSH 1 ; PUSH/POP converted to MOV
; 	POP BX
	CALL print_output
	;Source code line: 10 loading local array element x
	MOV BX, 1
	SHL BX, 1
	NEG BX
	ADD BX, -4
	PUSH BP
	ADD BP, BX
	MOV BX, BP
	;Pushing array element value and index for potential assignment
	MOV AX, [BP]
	POP BP
	;Source code line: 10 loading integer constant 0
	PUSH AX
	;Source code line: 10 accessing array element w
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH BX
	;Source code line: 10 loading global array element w
	MOV BX, 0
	;Pushing array element value and index for potential assignment
	SHL BX, 1
	MOV AX, w[BX]
; 	PUSH BX ; PUSH/POP pair eliminated
; 	POP BX
	;Source code line: 10 actually incrementing value of variable w[0]
	PUSH AX
	;Source code line: 10 storing incremented value in global array element w[BX]
	MOV AX, [BX]
	;Source code line: 10 assigning value to variable x[1]
	INC AX
	;Source code line: 10 getting array element address for assignment to x
	MOV w[BX], AX
	;Source code line: 10 storing value in local array element x[BX]
	POP AX
	POP BX
	PUSH BP
	MOV BP, BX
	;Source code line: 10 cleaning expression result from stack
	MOV [BP], AX
	;Source code line: 11 using local variable i
	POP BP
	;Source code line: 11 loading integer constant 1
	POP AX
	;Source code line: 11 accessing array element x
; 	PUSH 1 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH [BP+-2]
	;Source code line: 11 loading local array element x
	MOV BX, 1
	SHL BX, 1
	NEG BX
	ADD BX, -4
	PUSH BP
	ADD BP, BX
	MOV BX, BP
	;Pushing array element value and index for potential assignment
	MOV AX, [BP]
	POP BP
	;Source code line: 11 cleanup address from stack for x[1]
; 	PUSH BX ; PUSH/POP pair eliminated
; 	POP BX
	;Source code line: 11 assigning value to variable i
	;Source code line: 11 storing value in local variable i
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 11 cleaning expression result from stack
	;Source code line: 12 printing local variable i
	MOV [BP+-2], AX
	POP AX
	;Source code line: 13 using local variable i
	PUSH [BP+-2]
	;Source code line: 13 loading integer constant 0
	CALL print_output
	;Source code line: 13 accessing array element w
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH [BP+-2]
	;Source code line: 13 loading global array element w
	MOV BX, 0
	;Pushing array element value and index for potential assignment
	SHL BX, 1
	MOV AX, w[BX]
	;Source code line: 13 cleanup address from stack for w[0]
; 	PUSH BX ; PUSH/POP pair eliminated
; 	POP BX
	;Source code line: 13 assigning value to variable i
	;Source code line: 13 storing value in local variable i
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 13 cleaning expression result from stack
	;Source code line: 14 printing local variable i
	MOV [BP+-2], AX
	POP AX
	;Source code line: 16 using local variable i
	PUSH [BP+-2]
	;Source code line: 16 using local variable i
	CALL print_output
	;Source code line: 16 loading integer constant 0
	PUSH [BP+-2]
	;Source code line: 16 evaluating arithmetic operation +
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH [BP+-2]
	MOV BX, 0
	POP AX
	;Source code line: 16 assigning value to variable i
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 16 storing value in local variable i
	;Source code line: 16 cleaning expression result from stack
	ADD AX, BX
	;Source code line: 17 using local variable i
	MOV [BP+-2], AX
	;Source code line: 17 using local variable i
	POP AX
	;Source code line: 17 loading integer constant 0
	PUSH [BP+-2]
	;Source code line: 17 evaluating arithmetic operation -
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH [BP+-2]
	MOV BX, 0
	POP AX
	;Source code line: 17 assigning value to variable i
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 17 storing value in local variable i
	;Source code line: 17 cleaning expression result from stack
	SUB AX, BX
	;Source code line: 18 using local variable i
	MOV [BP+-2], AX
	;Source code line: 18 using local variable i
	POP AX
	;Source code line: 18 loading integer constant 1
	PUSH [BP+-2]
	;Source code line: 18 evaluating multiplication/division operation *
; 	PUSH 1 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH [BP+-2]
	MOV BX, 1
	;Pushing multiplication/division result onto stack
	POP AX
	;Source code line: 18 assigning value to variable i
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 18 storing value in local variable i
	;Source code line: 18 cleaning expression result from stack
	IMUL BX
	;Source code line: 19 printing local variable i
	MOV [BP+-2], AX
	POP AX
	;Source code line: 21 using local variable i
	PUSH [BP+-2]
	;Source code line: 21 loading integer constant 0
	CALL print_output
	;Source code line: 21 evaluating relational operation >
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH [BP+-2]
	MOV BX, 0
	POP AX
	;Source code line: 21 if relational operation result is false
	CMP AX, BX
	JG L_1
	PUSH 0
	;Source code line: 21 if relational operation result is true
	JMP L_2
L_1:
	;Source code line: 21 using local variable i
	PUSH 1
	;Source code line: 21 loading integer constant 10
L_2:
	;Source code line: 21 evaluating relational operation <
; 	PUSH 10 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH [BP+-2]
	MOV BX, 10
	POP AX
	;Source code line: 21 if relational operation result is false
	CMP AX, BX
	JL L_3
	PUSH 0
	;Source code line: 21 if relational operation result is true
	JMP L_4
L_3:
	;Source code line: 21 evaluating logical AND operation
	PUSH 1
L_4:
	POP BX
	POP AX
	CMP AX, 0
	JE L_5
	CMP BX, 0
	JE L_5
	PUSH 1
	JMP L_6
L_5:
	;Source code line: 21 using local variable i
	PUSH 0
	;Source code line: 21 loading integer constant 0
L_6:
	;Source code line: 21 evaluating relational operation <
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	PUSH [BP+-2]
	MOV BX, 0
	POP AX
	;Source code line: 21 if relational operation result is false
	CMP AX, BX
	JL L_7
	PUSH 0
	;Source code line: 21 if relational operation result is true
	JMP L_8
L_7:
	;Source code line: 21 using local variable i
	PUSH 1
	;Source code line: 21 loading integer constant 10
L_8:
	;Source code line: 21 evaluating unary minus operation
; 	PUSH 10 ; PUSH/POP converted to MOV
; 	POP AX
	PUSH [BP+-2]
	MOV AX, 10
	;Source code line: 21 evaluating relational operation >
; 	PUSH AX ; PUSH/POP converted to MOV
; 	POP BX
	NEG AX
	MOV BX, AX
	POP AX
	;Source code line: 21 if relational operation result is false
	CMP AX, BX
	JG L_9
	PUSH 0
	;Source code line: 21 if relational operation result is true
	JMP L_10
L_9:
	;Source code line: 21 evaluating logical AND operation
	PUSH 1
L_10:
	POP BX
	POP AX
	CMP AX, 0
	JE L_11
	CMP BX, 0
	JE L_11
	PUSH 1
	JMP L_12
L_11:
	;Source code line: 21 evaluating logical OR operation
	PUSH 0
L_12:
	POP BX
	POP AX
	CMP AX, 0
	JNE L_13
	CMP BX, 0
	JNE L_13
	PUSH 0
	JMP L_14
L_13:
	;Source code line: 21 checking IF condition
	PUSH 1
L_14:
	POP AX
	CMP AX, 0
	JE L_16
	;Source code line: 22 using local variable i
	JMP L_15
	;Source code line: 22 loading integer constant 100
L_15:
	;Source code line: 22 assigning value to variable i
; 	PUSH 100 ; PUSH/POP converted to MOV
; 	POP AX
	PUSH [BP+-2]
	;Source code line: 22 storing value in local variable i
	;Source code line: 22 cleaning expression result from stack
	MOV AX, 100
	MOV [BP+-2], AX
	POP AX
	;Source code line: 24 using local variable i
	JMP L_17
	;Source code line: 24 loading integer constant 200
L_16:
	;Source code line: 24 assigning value to variable i
; 	PUSH 200 ; PUSH/POP converted to MOV
; 	POP AX
	PUSH [BP+-2]
	;Source code line: 24 storing value in local variable i
	;Source code line: 24 cleaning expression result from stack
	MOV AX, 200
	MOV [BP+-2], AX
	;Source code line: 25 printing local variable i
	POP AX
L_17:
	;Source code line: 27 loading integer constant 0
	PUSH [BP+-2]
	;Source code line: 27 returning from main function
	CALL print_output
	PUSH 0
	MOV AX,4CH

	INT 21H
main ENDP
NEW_LINE PROC
	PUSH AX
	PUSH DX
	MOV AH, 2
	MOV DL, cr
	INT 21H
	MOV AH, 2
	MOV DL, lf
	INT 21H
	POP DX
	POP AX

	RET
NEW_LINE ENDP
PRINT_OUTPUT PROC NEAR
    PUSH BP   ;Save BP
    ;if(BX >= 0) then the number is positive
    MOV BP, SP   ;BP points to the top of the stack
    MOV BX, [BP+4]   ;The number to be printed
    ;else, the number is negative
    CMP BX, 0
    JGE POSITIVE
    MOV AH, 2
    MOV DL, '-'   ;Print a '-' sign
    INT 21H
    NEG BX    ;make BX positive by applying 2s complement
    POSITIVE:
    MOV AX, BX
    MOV CX, 0     ;Initialize character count
    PUSH_WHILE:
        XOR DX, DX  ;clear DX
        ;quotient is in AX and remainder is in DX
        MOV BX, 10  ;BX has the divisor, AX has the dividend
        DIV BX
        ;if(AX == 0) then break the loop
        PUSH DX   ;Push the remainder
        INC CX  ;CX++
        ;else continue
        CMP AX, 0
        JE END_PUSH_WHILE
        JMP PUSH_WHILE
    END_PUSH_WHILE:
    MOV AH, 2
    POP_WHILE:
        POP DX  ;Pop the remainder
        ADD DL, '0'
        ;if(CX <= 0) then end loop
        INT 21H  ; DL has the desired character now
        DEC CX  ;CX--
        ;else continue
        CMP CX, 0
        JLE END_POP_WHILE
    ;Print newline
        JMP POP_WHILE
    END_POP_WHILE:
    MOV DL, 0DH
    INT 21H
    MOV DL, 0AH
    INT 21H
    POP BP  ; Restore BP
    RET 2
PRINT_OUTPUT ENDP
END main
