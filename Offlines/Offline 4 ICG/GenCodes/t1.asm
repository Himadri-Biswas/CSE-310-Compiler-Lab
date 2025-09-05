.MODEL SMALL
.STACK 1000H
.DATA
	CR EQU 0DH
	LF EQU 0AH
	i DW 1 DUP (0000H)
	j DW 1 DUP (0000H)
.Code
main PROC
	MOV AX, @DATA
	MOV DS, AX
	PUSH BP
	MOV BP, SP
	;Source code line: 4 allocating 2 bytes for local variable k
	PUSH BX
	;Source code line: 4 allocating 2 bytes for local variable ll
	PUSH BX
	;Source code line: 4 allocating 2 bytes for local variable m
	PUSH BX
	;Source code line: 4 allocating 2 bytes for local variable n
	PUSH BX
	;Source code line: 4 allocating 2 bytes for local variable o
	PUSH BX
	;Source code line: 4 allocating 2 bytes for local variable p
	PUSH BX
	;Source code line: 6 using global variable i
	PUSH i
	;Source code line: 6 loading integer constant 1
	PUSH 1
	;Source code line: 6 assigning value to variable i
	POP AX
	;Source code line: 6 storing value in global variable i
	MOV i, AX
	;Source code line: 6 cleaning expression result from stack
	POP AX
	;Source code line: 7 printing global variable i
	PUSH i
	CALL print_output
	;Source code line: 9 using global variable j
	PUSH j
	;Source code line: 9 loading integer constant 5
	PUSH 5
	;Source code line: 9 loading integer constant 8
	PUSH 8
	;Source code line: 9 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 9 assigning value to variable j
	POP AX
	;Source code line: 9 storing value in global variable j
	MOV j, AX
	;Source code line: 9 cleaning expression result from stack
	POP AX
	;Source code line: 10 printing global variable j
	PUSH j
	CALL print_output
	;Source code line: 12 using local variable k
	PUSH [BP+-2]
	;Source code line: 12 using global variable i
	PUSH i
	;Source code line: 12 loading integer constant 2
	PUSH 2
	;Source code line: 12 using global variable j
	PUSH j
	;Source code line: 12 evaluating multiplication/division operation *
	POP BX
	POP AX
	IMUL BX
	;Pushing multiplication/division result onto stack
	PUSH AX
	;Source code line: 12 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 12 assigning value to variable k
	POP AX
	;Source code line: 12 storing value in local variable k
	MOV [BP+-2], AX
	;Source code line: 12 cleaning expression result from stack
	POP AX
	;Source code line: 13 printing local variable k
	PUSH [BP+-2]
	CALL print_output
	;Source code line: 15 using local variable m
	PUSH [BP+-6]
	;Source code line: 15 using local variable k
	PUSH [BP+-2]
	;Source code line: 15 loading integer constant 9
	PUSH 9
	;Source code line: 15 evaluating multiplication/division operation %
	POP BX
	POP AX
	XOR DX, DX
	IDIV BX
	MOV AX,DX
	;Pushing multiplication/division result onto stack
	PUSH AX
	;Source code line: 15 assigning value to variable m
	POP AX
	;Source code line: 15 storing value in local variable m
	MOV [BP+-6], AX
	;Source code line: 15 cleaning expression result from stack
	POP AX
	;Source code line: 16 printing local variable m
	PUSH [BP+-6]
	CALL print_output
	;Source code line: 18 using local variable n
	PUSH [BP+-8]
	;Source code line: 18 using local variable m
	PUSH [BP+-6]
	;Source code line: 18 using local variable ll
	PUSH [BP+-4]
	;Source code line: 18 evaluating relational operation <=
	POP BX
	POP AX
	CMP AX, BX
	JLE L_1
	;Source code line: 18 if relational operation result is false
	PUSH 0
	JMP L_2
L_1:
	;Source code line: 18 if relational operation result is true
	PUSH 1
L_2:
	;Source code line: 18 assigning value to variable n
	POP AX
	;Source code line: 18 storing value in local variable n
	MOV [BP+-8], AX
	;Source code line: 18 cleaning expression result from stack
	POP AX
	;Source code line: 19 printing local variable n
	PUSH [BP+-8]
	CALL print_output
	;Source code line: 21 using local variable o
	PUSH [BP+-10]
	;Source code line: 21 using global variable i
	PUSH i
	;Source code line: 21 using global variable j
	PUSH j
	;Source code line: 21 evaluating relational operation !=
	POP BX
	POP AX
	CMP AX, BX
	JNE L_3
	;Source code line: 21 if relational operation result is false
	PUSH 0
	JMP L_4
L_3:
	;Source code line: 21 if relational operation result is true
	PUSH 1
L_4:
	;Source code line: 21 assigning value to variable o
	POP AX
	;Source code line: 21 storing value in local variable o
	MOV [BP+-10], AX
	;Source code line: 21 cleaning expression result from stack
	POP AX
	;Source code line: 22 printing local variable o
	PUSH [BP+-10]
	CALL print_output
	;Source code line: 24 using local variable p
	PUSH [BP+-12]
	;Source code line: 24 using local variable n
	PUSH [BP+-8]
	;Source code line: 24 using local variable o
	PUSH [BP+-10]
	;Source code line: 24 evaluating logical OR operation
	POP BX
	POP AX
	CMP AX, 0
	JNE L_5
	CMP BX, 0
	JNE L_5
	PUSH 0
	JMP L_6
L_5:
	PUSH 1
L_6:
	;Source code line: 24 assigning value to variable p
	POP AX
	;Source code line: 24 storing value in local variable p
	MOV [BP+-12], AX
	;Source code line: 24 cleaning expression result from stack
	POP AX
	;Source code line: 25 printing local variable p
	PUSH [BP+-12]
	CALL print_output
	;Source code line: 27 using local variable p
	PUSH [BP+-12]
	;Source code line: 27 using local variable n
	PUSH [BP+-8]
	;Source code line: 27 using local variable o
	PUSH [BP+-10]
	;Source code line: 27 evaluating logical AND operation
	POP BX
	POP AX
	CMP AX, 0
	JE L_7
	CMP BX, 0
	JE L_7
	PUSH 1
	JMP L_8
L_7:
	PUSH 0
L_8:
	;Source code line: 27 assigning value to variable p
	POP AX
	;Source code line: 27 storing value in local variable p
	MOV [BP+-12], AX
	;Source code line: 27 cleaning expression result from stack
	POP AX
	;Source code line: 28 printing local variable p
	PUSH [BP+-12]
	CALL print_output
	;Source code line: 30 using local variable p
	PUSH [BP+-12]
	;Source code line: 30 getting value in AX and saving original value for post-incrementing variable p
	POP AX
	PUSH AX
	;Source code line: 30 actually incrementing value of variable p
	INC AX
	;Source code line: 30 storing incremented value in local variable p
	MOV [BP+-12], AX
	;Source code line: 30 cleaning expression result from stack
	POP AX
	;Source code line: 31 printing local variable p
	PUSH [BP+-12]
	CALL print_output
	;Source code line: 33 using local variable k
	PUSH [BP+-2]
	;Source code line: 33 using local variable p
	PUSH [BP+-12]
	;Source code line: 33 evaluating unary minus operation
	POP AX
	NEG AX
	PUSH AX
	;Source code line: 33 assigning value to variable k
	POP AX
	;Source code line: 33 storing value in local variable k
	MOV [BP+-2], AX
	;Source code line: 33 cleaning expression result from stack
	POP AX
	;Source code line: 34 printing local variable k
	PUSH [BP+-2]
	CALL print_output
	;Source code line: 36 loading integer constant 0
	PUSH 0
	;Source code line: 36 returning from main function
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
    MOV BP, SP   ;BP points to the top of the stack
    MOV BX, [BP+4]   ;The number to be printed
    ;if(BX >= 0) then the number is positive
    CMP BX, 0
    JGE POSITIVE
    ;else, the number is negative
    MOV AH, 2
    MOV DL, '-'   ;Print a '-' sign
    INT 21H
    NEG BX    ;make BX positive by applying 2s complement
    POSITIVE:
    MOV AX, BX
    MOV CX, 0     ;Initialize character count
    PUSH_WHILE:
        XOR DX, DX  ;clear DX
        MOV BX, 10  ;BX has the divisor, AX has the dividend
        DIV BX
        ;quotient is in AX and remainder is in DX
        PUSH DX   ;Push the remainder
        INC CX  ;CX++
        ;if(AX == 0) then break the loop
        CMP AX, 0
        JE END_PUSH_WHILE
        ;else continue
        JMP PUSH_WHILE
    END_PUSH_WHILE:
    MOV AH, 2
    POP_WHILE:
        POP DX  ;Pop the remainder
        ADD DL, '0'
        INT 21H  ; DL has the desired character now
        DEC CX  ;CX--
        ;if(CX <= 0) then end loop
        CMP CX, 0
        JLE END_POP_WHILE
        ;else continue
        JMP POP_WHILE
    END_POP_WHILE:
    ;Print newline
    MOV DL, 0DH
    INT 21H
    MOV DL, 0AH
    INT 21H
    POP BP  ; Restore BP
    RET 2
PRINT_OUTPUT ENDP
END main
