.MODEL SMALL
.STACK 1000H
.DATA
	CR EQU 0DH
	LF EQU 0AH
	a DW 1 DUP (0000H)
	b DW 1 DUP (0000H)
	c DW 1 DUP (0000H)
.Code
func_a PROC
	PUSH BP
	MOV BP, SP
	;Source code line: 4 using global variable a
	PUSH a
	;Source code line: 4 loading integer constant 7
	PUSH 7
	;Source code line: 4 assigning value to variable a
	POP AX
	;Source code line: 4 storing value in global variable a
	MOV a, AX
	;Source code line: 4 cleaning expression result from stack
	POP AX
	MOV SP, BP
POP BP
		RET 
func_a ENDP
foo PROC
	PUSH BP
	MOV BP, SP
	;Source code line: 8 using local variable a
	PUSH [BP+4]
	;Source code line: 8 using local variable a
	PUSH [BP+4]
	;Source code line: 8 loading integer constant 3
	PUSH 3
	;Source code line: 8 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 8 assigning value to variable a
	POP AX
	;Source code line: 8 storing value in local variable a
	MOV [BP+4], AX
	;Source code line: 8 cleaning expression result from stack
	POP AX
	;Source code line: 9 using local variable a
	PUSH [BP+4]
	;Source code line: 9 returning from function foo
	POP AX
	MOV SP, BP
	POP BP
	RET 2
foo ENDP
bar PROC
	PUSH BP
	MOV BP, SP
	;Source code line: 14 using global variable c
	PUSH c
	;Source code line: 14 loading integer constant 4
	PUSH 4
	;Source code line: 14 using local variable a
	PUSH [BP+6]
	;Source code line: 14 evaluating multiplication/division operation *
	POP BX
	POP AX
	IMUL BX
	;Pushing multiplication/division result onto stack
	PUSH AX
	;Source code line: 14 loading integer constant 2
	PUSH 2
	;Source code line: 14 using local variable b
	PUSH [BP+4]
	;Source code line: 14 evaluating multiplication/division operation *
	POP BX
	POP AX
	IMUL BX
	;Pushing multiplication/division result onto stack
	PUSH AX
	;Source code line: 14 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 14 assigning value to variable c
	POP AX
	;Source code line: 14 storing value in global variable c
	MOV c, AX
	;Source code line: 14 cleaning expression result from stack
	POP AX
	;Source code line: 15 using global variable c
	PUSH c
	;Source code line: 15 returning from function bar
	POP AX
	MOV SP, BP
	POP BP
	RET 4
bar ENDP
main PROC
	MOV AX, @DATA
	MOV DS, AX
	PUSH BP
	MOV BP, SP
	;Source code line: 20 allocating 2 bytes for local variable i
	PUSH BX
	;Source code line: 20 allocating 2 bytes for local variable j
	PUSH BX
	;Source code line: 20 allocating 2 bytes for local variable k
	PUSH BX
	;Source code line: 20 allocating 2 bytes for local variable l
	PUSH BX
	;Source code line: 22 using local variable i
	PUSH [BP+-2]
	;Source code line: 22 loading integer constant 5
	PUSH 5
	;Source code line: 22 assigning value to variable i
	POP AX
	;Source code line: 22 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 22 cleaning expression result from stack
	POP AX
	;Source code line: 23 using local variable j
	PUSH [BP+-4]
	;Source code line: 23 loading integer constant 6
	PUSH 6
	;Source code line: 23 assigning value to variable j
	POP AX
	;Source code line: 23 storing value in local variable j
	MOV [BP+-4], AX
	;Source code line: 23 cleaning expression result from stack
	POP AX
	;Source code line: 25 calling function func_a
	CALL func_a
	PUSH AX
	;Source code line: 25 cleaning expression result from stack
	POP AX
	;Source code line: 26 printing global variable a
	PUSH a
	CALL print_output
	;Source code line: 28 using local variable k
	PUSH [BP+-6]
	;Source code line: 28 using local variable i
	PUSH [BP+-2]
	;Source code line: 28 calling function foo
	CALL foo
	PUSH AX
	;Source code line: 28 assigning value to variable k
	POP AX
	;Source code line: 28 storing value in local variable k
	MOV [BP+-6], AX
	;Source code line: 28 cleaning expression result from stack
	POP AX
	;Source code line: 29 printing local variable k
	PUSH [BP+-6]
	CALL print_output
	;Source code line: 31 using local variable l
	PUSH [BP+-8]
	;Source code line: 31 using local variable i
	PUSH [BP+-2]
	;Source code line: 31 using local variable j
	PUSH [BP+-4]
	;Source code line: 31 calling function bar
	CALL bar
	PUSH AX
	;Source code line: 31 assigning value to variable l
	POP AX
	;Source code line: 31 storing value in local variable l
	MOV [BP+-8], AX
	;Source code line: 31 cleaning expression result from stack
	POP AX
	;Source code line: 32 printing local variable l
	PUSH [BP+-8]
	CALL print_output
	;Source code line: 34 using local variable j
	PUSH [BP+-4]
	;Source code line: 34 loading integer constant 6
	PUSH 6
	;Source code line: 34 using local variable i
	PUSH [BP+-2]
	;Source code line: 34 using local variable j
	PUSH [BP+-4]
	;Source code line: 34 calling function bar
	CALL bar
	PUSH AX
	;Source code line: 34 evaluating multiplication/division operation *
	POP BX
	POP AX
	IMUL BX
	;Pushing multiplication/division result onto stack
	PUSH AX
	;Source code line: 34 loading integer constant 2
	PUSH 2
	;Source code line: 34 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 34 loading integer constant 3
	PUSH 3
	;Source code line: 34 using local variable i
	PUSH [BP+-2]
	;Source code line: 34 calling function foo
	CALL foo
	PUSH AX
	;Source code line: 34 evaluating multiplication/division operation *
	POP BX
	POP AX
	IMUL BX
	;Pushing multiplication/division result onto stack
	PUSH AX
	;Source code line: 34 evaluating arithmetic operation -
	POP BX
	POP AX
	SUB AX, BX
	PUSH AX
	;Source code line: 34 assigning value to variable j
	POP AX
	;Source code line: 34 storing value in local variable j
	MOV [BP+-4], AX
	;Source code line: 34 cleaning expression result from stack
	POP AX
	;Source code line: 35 printing local variable j
	PUSH [BP+-4]
	CALL print_output
	;Source code line: 38 loading integer constant 0
	PUSH 0
	;Source code line: 38 returning from main function
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
