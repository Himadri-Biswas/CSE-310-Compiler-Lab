.MODEL SMALL
.STACK 1000H
.DATA
	CR EQU 0DH
	LF EQU 0AH
.Code
f PROC
	PUSH BP
	MOV BP, SP
	;Source code line: 2 allocating 2 bytes for local variable k
	PUSH BX
	;Source code line: 3 using local variable k
	PUSH [BP+-2]
	;Source code line: 3 loading integer constant 5
	PUSH 5
	;Source code line: 3 assigning value to variable k
	POP AX
	;Source code line: 3 storing value in local variable k
	MOV [BP+-2], AX
	;Source code line: 3 cleaning expression result from stack
	POP AX
L_1:
	;Source code line: 4 using local variable k
	PUSH [BP+-2]
	;Source code line: 4 loading integer constant 0
	PUSH 0
	;Source code line: 4 evaluating relational operation >
	POP BX
	POP AX
	CMP AX, BX
	JG L_4
	;Source code line: 4 if relational operation result is false
	PUSH 0
	JMP L_5
L_4:
	;Source code line: 4 if relational operation result is true
	PUSH 1
L_5:
	;Source code line: 4 checking WHILE condition
	POP AX
	CMP AX, 0
	JE L_2
	JMP L_3
L_3:
	;Source code line: 5 using local variable a
	PUSH [BP+4]
	;Source code line: 5 getting value in AX and saving original value for post-incrementing variable a
	POP AX
	PUSH AX
	;Source code line: 5 actually incrementing value of variable a
	INC AX
	;Source code line: 5 storing incremented value in local variable a
	MOV [BP+4], AX
	;Source code line: 5 cleaning expression result from stack
	POP AX
	;Source code line: 6 using local variable k
	PUSH [BP+-2]
	POP AX
	PUSH AX
	DEC AX
	MOV [BP+-2], AX
	;Source code line: 6 cleaning expression result from stack
	POP AX
	JMP L_1
L_2:
	;Source code line: 8 loading integer constant 3
	PUSH 3
	;Source code line: 8 using local variable a
	PUSH [BP+4]
	;Source code line: 8 evaluating multiplication/division operation *
	POP BX
	POP AX
	IMUL BX
	;Pushing multiplication/division result onto stack
	PUSH AX
	;Source code line: 8 loading integer constant 7
	PUSH 7
	;Source code line: 8 evaluating arithmetic operation -
	POP BX
	POP AX
	SUB AX, BX
	PUSH AX
	;Source code line: 8 returning from function f
	POP AX
	MOV SP, BP
	POP BP
	RET 2
	;Source code line: 9 using local variable a
	PUSH [BP+4]
	;Source code line: 9 loading integer constant 9
	PUSH 9
	;Source code line: 9 assigning value to variable a
	POP AX
	;Source code line: 9 storing value in local variable a
	MOV [BP+4], AX
	;Source code line: 9 cleaning expression result from stack
	POP AX
f ENDP
g PROC
	PUSH BP
	MOV BP, SP
	;Source code line: 14 allocating 2 bytes for local variable x
	PUSH BX
	;Source code line: 14 allocating 2 bytes for local variable i
	PUSH BX
	;Source code line: 15 using local variable x
	PUSH [BP+-2]
	;Source code line: 15 using local variable a
	PUSH [BP+6]
	;Source code line: 15 calling function f
	CALL f
	PUSH AX
	;Source code line: 15 using local variable a
	PUSH [BP+6]
	;Source code line: 15 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 15 using local variable b
	PUSH [BP+4]
	;Source code line: 15 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 15 assigning value to variable x
	POP AX
	;Source code line: 15 storing value in local variable x
	MOV [BP+-2], AX
	;Source code line: 15 cleaning expression result from stack
	POP AX
	;Source code line: 17 using local variable i
	PUSH [BP+-4]
	;Source code line: 17 loading integer constant 0
	PUSH 0
	;Source code line: 17 assigning value to variable i
	POP AX
	;Source code line: 17 storing value in local variable i
	MOV [BP+-4], AX
	;Source code line: 17 initializing FOR loop
L_6:
	;Source code line: 17 using local variable i
	PUSH [BP+-4]
	;Source code line: 17 loading integer constant 7
	PUSH 7
	;Source code line: 17 evaluating relational operation <
	POP BX
	POP AX
	CMP AX, BX
	JL L_9
	;Source code line: 17 if relational operation result is false
	PUSH 0
	JMP L_10
L_9:
	;Source code line: 17 if relational operation result is true
	PUSH 1
L_10:
	;Source code line: 17 checking loop condition
	POP AX
	CMP AX, 0
	JE L_7
	JMP L_8
L_8:
	;Source code line: 18 using local variable i
	PUSH [BP+-4]
	;Source code line: 18 loading integer constant 3
	PUSH 3
	;Source code line: 18 evaluating multiplication/division operation %
	POP BX
	POP AX
	XOR DX, DX
	IDIV BX
	MOV AX,DX
	;Pushing multiplication/division result onto stack
	PUSH AX
	;Source code line: 18 loading integer constant 0
	PUSH 0
	;Source code line: 18 evaluating relational operation ==
	POP BX
	POP AX
	CMP AX, BX
	JE L_11
	;Source code line: 18 if relational operation result is false
	PUSH 0
	JMP L_12
L_11:
	;Source code line: 18 if relational operation result is true
	PUSH 1
L_12:
	;Source code line: 18 checking IF condition
	POP AX
	CMP AX, 0
	JE L_14
	JMP L_13
L_13:
	;Source code line: 19 using local variable x
	PUSH [BP+-2]
	;Source code line: 19 using local variable x
	PUSH [BP+-2]
	;Source code line: 19 loading integer constant 5
	PUSH 5
	;Source code line: 19 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 19 assigning value to variable x
	POP AX
	;Source code line: 19 storing value in local variable x
	MOV [BP+-2], AX
	;Source code line: 19 cleaning expression result from stack
	POP AX
	JMP L_15
L_14:
	;Source code line: 22 using local variable x
	PUSH [BP+-2]
	;Source code line: 22 using local variable x
	PUSH [BP+-2]
	;Source code line: 22 loading integer constant 1
	PUSH 1
	;Source code line: 22 evaluating arithmetic operation -
	POP BX
	POP AX
	SUB AX, BX
	PUSH AX
	;Source code line: 22 assigning value to variable x
	POP AX
	;Source code line: 22 storing value in local variable x
	MOV [BP+-2], AX
	;Source code line: 22 cleaning expression result from stack
	POP AX
L_15:
	;Source code line: 24 incrementing variable i
	PUSH [BP+-4]
	POP AX
	PUSH AX
	INC AX
	MOV [BP+-4], AX
	POP AX
	;Source code line: 24 jumping back to loop condition check
	JMP L_6
L_7:
	;Source code line: 26 using local variable x
	PUSH [BP+-2]
	;Source code line: 26 returning from function g
	POP AX
	MOV SP, BP
	POP BP
	RET 4
g ENDP
main PROC
	MOV AX, @DATA
	MOV DS, AX
	PUSH BP
	MOV BP, SP
	;Source code line: 30 allocating 2 bytes for local variable a
	PUSH BX
	;Source code line: 30 allocating 2 bytes for local variable b
	PUSH BX
	;Source code line: 30 allocating 2 bytes for local variable i
	PUSH BX
	;Source code line: 31 using local variable a
	PUSH [BP+-2]
	;Source code line: 31 loading integer constant 1
	PUSH 1
	;Source code line: 31 assigning value to variable a
	POP AX
	;Source code line: 31 storing value in local variable a
	MOV [BP+-2], AX
	;Source code line: 31 cleaning expression result from stack
	POP AX
	;Source code line: 32 using local variable b
	PUSH [BP+-4]
	;Source code line: 32 loading integer constant 2
	PUSH 2
	;Source code line: 32 assigning value to variable b
	POP AX
	;Source code line: 32 storing value in local variable b
	MOV [BP+-4], AX
	;Source code line: 32 cleaning expression result from stack
	POP AX
	;Source code line: 33 using local variable a
	PUSH [BP+-2]
	;Source code line: 33 using local variable a
	PUSH [BP+-2]
	;Source code line: 33 using local variable b
	PUSH [BP+-4]
	;Source code line: 33 calling function g
	CALL g
	PUSH AX
	;Source code line: 33 assigning value to variable a
	POP AX
	;Source code line: 33 storing value in local variable a
	MOV [BP+-2], AX
	;Source code line: 33 cleaning expression result from stack
	POP AX
	;Source code line: 34 printing local variable a
	PUSH [BP+-2]
	CALL print_output
	;Source code line: 35 using local variable i
	PUSH [BP+-6]
	;Source code line: 35 loading integer constant 0
	PUSH 0
	;Source code line: 35 assigning value to variable i
	POP AX
	;Source code line: 35 storing value in local variable i
	MOV [BP+-6], AX
	;Source code line: 35 initializing FOR loop
L_16:
	;Source code line: 35 using local variable i
	PUSH [BP+-6]
	;Source code line: 35 loading integer constant 4
	PUSH 4
	;Source code line: 35 evaluating relational operation <
	POP BX
	POP AX
	CMP AX, BX
	JL L_19
	;Source code line: 35 if relational operation result is false
	PUSH 0
	JMP L_20
L_19:
	;Source code line: 35 if relational operation result is true
	PUSH 1
L_20:
	;Source code line: 35 checking loop condition
	POP AX
	CMP AX, 0
	JE L_17
	JMP L_18
L_18:
	;Source code line: 36 using local variable a
	PUSH [BP+-2]
	;Source code line: 36 loading integer constant 3
	PUSH 3
	;Source code line: 36 assigning value to variable a
	POP AX
	;Source code line: 36 storing value in local variable a
	MOV [BP+-2], AX
	;Source code line: 36 cleaning expression result from stack
	POP AX
L_21:
	;Source code line: 37 using local variable a
	PUSH [BP+-2]
	;Source code line: 37 loading integer constant 0
	PUSH 0
	;Source code line: 37 evaluating relational operation >
	POP BX
	POP AX
	CMP AX, BX
	JG L_24
	;Source code line: 37 if relational operation result is false
	PUSH 0
	JMP L_25
L_24:
	;Source code line: 37 if relational operation result is true
	PUSH 1
L_25:
	;Source code line: 37 checking WHILE condition
	POP AX
	CMP AX, 0
	JE L_22
	JMP L_23
L_23:
	;Source code line: 38 using local variable b
	PUSH [BP+-4]
	;Source code line: 38 getting value in AX and saving original value for post-incrementing variable b
	POP AX
	PUSH AX
	;Source code line: 38 actually incrementing value of variable b
	INC AX
	;Source code line: 38 storing incremented value in local variable b
	MOV [BP+-4], AX
	;Source code line: 38 cleaning expression result from stack
	POP AX
	;Source code line: 39 using local variable a
	PUSH [BP+-2]
	POP AX
	PUSH AX
	DEC AX
	MOV [BP+-2], AX
	;Source code line: 39 cleaning expression result from stack
	POP AX
	JMP L_21
L_22:
	;Source code line: 41 incrementing variable i
	PUSH [BP+-6]
	POP AX
	PUSH AX
	INC AX
	MOV [BP+-6], AX
	POP AX
	;Source code line: 41 jumping back to loop condition check
	JMP L_16
L_17:
	;Source code line: 42 printing local variable a
	PUSH [BP+-2]
	CALL print_output
	;Source code line: 43 printing local variable b
	PUSH [BP+-4]
	CALL print_output
	;Source code line: 44 printing local variable i
	PUSH [BP+-6]
	CALL print_output
	;Source code line: 45 loading integer constant 0
	PUSH 0
	;Source code line: 45 returning from main function
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
