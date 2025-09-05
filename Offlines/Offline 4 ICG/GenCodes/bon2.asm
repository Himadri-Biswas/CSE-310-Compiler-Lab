.MODEL SMALL
.STACK 1000H
.DATA
	CR EQU 0DH
	LF EQU 0AH
.Code
func PROC
	PUSH BP
	MOV BP, SP
	;Source code line: 2 allocating 2 bytes for local variable i
	PUSH BX
	;Source code line: 3 using local variable n
	PUSH [BP+4]
	;Source code line: 3 loading integer constant 0
	PUSH 0
	;Source code line: 3 evaluating relational operation ==
	POP BX
	POP AX
	CMP AX, BX
	JE L_1
	;Source code line: 3 if relational operation result is false
	PUSH 0
	JMP L_2
L_1:
	;Source code line: 3 if relational operation result is true
	PUSH 1
L_2:
	;Source code line: 3 checking IF condition
	POP AX
	CMP AX, 0
	JE L_4
	JMP L_3
L_3:
	;Source code line: 3 loading integer constant 0
	PUSH 0
	;Source code line: 3 returning from function func
	POP AX
	MOV SP, BP
	POP BP
	RET 2
L_4:
	;Source code line: 4 using local variable i
	PUSH [BP+-2]
	;Source code line: 4 using local variable n
	PUSH [BP+4]
	;Source code line: 4 assigning value to variable i
	POP AX
	;Source code line: 4 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 4 cleaning expression result from stack
	POP AX
	;Source code line: 5 using local variable n
	PUSH [BP+4]
	;Source code line: 5 loading integer constant 1
	PUSH 1
	;Source code line: 5 evaluating arithmetic operation -
	POP BX
	POP AX
	SUB AX, BX
	PUSH AX
	;Source code line: 5 calling function func
	CALL func
	PUSH AX
	;Source code line: 5 using local variable i
	PUSH [BP+-2]
	;Source code line: 5 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 5 returning from function func
	POP AX
	MOV SP, BP
	POP BP
	RET 2
func ENDP
func2 PROC
	PUSH BP
	MOV BP, SP
	;Source code line: 9 allocating 2 bytes for local variable i
	PUSH BX
	;Source code line: 10 using local variable n
	PUSH [BP+4]
	;Source code line: 10 loading integer constant 0
	PUSH 0
	;Source code line: 10 evaluating relational operation ==
	POP BX
	POP AX
	CMP AX, BX
	JE L_5
	;Source code line: 10 if relational operation result is false
	PUSH 0
	JMP L_6
L_5:
	;Source code line: 10 if relational operation result is true
	PUSH 1
L_6:
	;Source code line: 10 checking IF condition
	POP AX
	CMP AX, 0
	JE L_8
	JMP L_7
L_7:
	;Source code line: 10 loading integer constant 0
	PUSH 0
	;Source code line: 10 returning from function func2
	POP AX
	MOV SP, BP
	POP BP
	RET 2
L_8:
	;Source code line: 11 using local variable i
	PUSH [BP+-2]
	;Source code line: 11 using local variable n
	PUSH [BP+4]
	;Source code line: 11 assigning value to variable i
	POP AX
	;Source code line: 11 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 11 cleaning expression result from stack
	POP AX
	;Source code line: 12 using local variable n
	PUSH [BP+4]
	;Source code line: 12 loading integer constant 1
	PUSH 1
	;Source code line: 12 evaluating arithmetic operation -
	POP BX
	POP AX
	SUB AX, BX
	PUSH AX
	;Source code line: 12 calling function func
	CALL func
	PUSH AX
	;Source code line: 12 using local variable i
	PUSH [BP+-2]
	;Source code line: 12 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 12 returning from function func2
	POP AX
	MOV SP, BP
	POP BP
	RET 2
func2 ENDP
main PROC
	MOV AX, @DATA
	MOV DS, AX
	PUSH BP
	MOV BP, SP
	;Source code line: 16 allocating 2 bytes for local variable a
	PUSH BX
	;Source code line: 17 using local variable a
	PUSH [BP+-2]
	;Source code line: 17 loading integer constant 7
	PUSH 7
	;Source code line: 17 calling function func
	CALL func
	PUSH AX
	;Source code line: 17 assigning value to variable a
	POP AX
	;Source code line: 17 storing value in local variable a
	MOV [BP+-2], AX
	;Source code line: 17 cleaning expression result from stack
	POP AX
	;Source code line: 18 printing local variable a
	PUSH [BP+-2]
	CALL print_output
	;Source code line: 19 loading integer constant 0
	PUSH 0
	;Source code line: 19 returning from main function
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
