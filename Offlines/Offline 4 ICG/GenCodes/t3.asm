.MODEL SMALL
.STACK 1000H
.DATA
	CR EQU 0DH
	LF EQU 0AH
.Code
main PROC
	MOV AX, @DATA
	MOV DS, AX
	PUSH BP
	MOV BP, SP
	;Source code line: 3 allocating 2 bytes for local variable i
	PUSH BX
	;Source code line: 3 allocating 2 bytes for local variable j
	PUSH BX
	;Source code line: 3 allocating 2 bytes for local variable k
	PUSH BX
	;Source code line: 3 allocating 2 bytes for local variable ll
	PUSH BX
	;Source code line: 5 using local variable i
	PUSH [BP+-2]
	;Source code line: 5 loading integer constant 0
	PUSH 0
	;Source code line: 5 assigning value to variable i
	POP AX
	;Source code line: 5 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 5 initializing FOR loop
L_1:
	;Source code line: 5 using local variable i
	PUSH [BP+-2]
	;Source code line: 5 loading integer constant 6
	PUSH 6
	;Source code line: 5 evaluating relational operation <
	POP BX
	POP AX
	CMP AX, BX
	JL L_4
	;Source code line: 5 if relational operation result is false
	PUSH 0
	JMP L_5
L_4:
	;Source code line: 5 if relational operation result is true
	PUSH 1
L_5:
	;Source code line: 5 checking loop condition
	POP AX
	CMP AX, 0
	JE L_2
	JMP L_3
L_3:
	;Source code line: 6 printing local variable i
	PUSH [BP+-2]
	CALL print_output
	;Source code line: 7 incrementing variable i
	PUSH [BP+-2]
	POP AX
	PUSH AX
	INC AX
	MOV [BP+-2], AX
	POP AX
	;Source code line: 7 jumping back to loop condition check
	JMP L_1
L_2:
	;Source code line: 9 using local variable k
	PUSH [BP+-6]
	;Source code line: 9 loading integer constant 4
	PUSH 4
	;Source code line: 9 assigning value to variable k
	POP AX
	;Source code line: 9 storing value in local variable k
	MOV [BP+-6], AX
	;Source code line: 9 cleaning expression result from stack
	POP AX
	;Source code line: 10 using local variable ll
	PUSH [BP+-8]
	;Source code line: 10 loading integer constant 6
	PUSH 6
	;Source code line: 10 assigning value to variable ll
	POP AX
	;Source code line: 10 storing value in local variable ll
	MOV [BP+-8], AX
	;Source code line: 10 cleaning expression result from stack
	POP AX
L_6:
	;Source code line: 11 using local variable k
	PUSH [BP+-6]
	;Source code line: 11 loading integer constant 0
	PUSH 0
	;Source code line: 11 evaluating relational operation >
	POP BX
	POP AX
	CMP AX, BX
	JG L_9
	;Source code line: 11 if relational operation result is false
	PUSH 0
	JMP L_10
L_9:
	;Source code line: 11 if relational operation result is true
	PUSH 1
L_10:
	;Source code line: 11 checking WHILE condition
	POP AX
	CMP AX, 0
	JE L_7
	JMP L_8
L_8:
	;Source code line: 12 using local variable ll
	PUSH [BP+-8]
	;Source code line: 12 using local variable ll
	PUSH [BP+-8]
	;Source code line: 12 loading integer constant 3
	PUSH 3
	;Source code line: 12 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 12 assigning value to variable ll
	POP AX
	;Source code line: 12 storing value in local variable ll
	MOV [BP+-8], AX
	;Source code line: 12 cleaning expression result from stack
	POP AX
	;Source code line: 13 using local variable k
	PUSH [BP+-6]
	POP AX
	PUSH AX
	DEC AX
	MOV [BP+-6], AX
	;Source code line: 13 cleaning expression result from stack
	POP AX
	JMP L_6
L_7:
	;Source code line: 16 printing local variable ll
	PUSH [BP+-8]
	CALL print_output
	;Source code line: 17 printing local variable k
	PUSH [BP+-6]
	CALL print_output
	;Source code line: 19 using local variable k
	PUSH [BP+-6]
	;Source code line: 19 loading integer constant 4
	PUSH 4
	;Source code line: 19 assigning value to variable k
	POP AX
	;Source code line: 19 storing value in local variable k
	MOV [BP+-6], AX
	;Source code line: 19 cleaning expression result from stack
	POP AX
	;Source code line: 20 using local variable ll
	PUSH [BP+-8]
	;Source code line: 20 loading integer constant 6
	PUSH 6
	;Source code line: 20 assigning value to variable ll
	POP AX
	;Source code line: 20 storing value in local variable ll
	MOV [BP+-8], AX
	;Source code line: 20 cleaning expression result from stack
	POP AX
L_11:
	;Source code line: 22 using local variable k
	PUSH [BP+-6]
	POP AX
	PUSH AX
	DEC AX
	MOV [BP+-6], AX
	;Source code line: 22 checking WHILE condition
	POP AX
	CMP AX, 0
	JE L_12
	JMP L_13
L_13:
	;Source code line: 23 using local variable ll
	PUSH [BP+-8]
	;Source code line: 23 using local variable ll
	PUSH [BP+-8]
	;Source code line: 23 loading integer constant 3
	PUSH 3
	;Source code line: 23 evaluating arithmetic operation +
	POP BX
	POP AX
	ADD AX, BX
	PUSH AX
	;Source code line: 23 assigning value to variable ll
	POP AX
	;Source code line: 23 storing value in local variable ll
	MOV [BP+-8], AX
	;Source code line: 23 cleaning expression result from stack
	POP AX
	JMP L_11
L_12:
	;Source code line: 26 printing local variable ll
	PUSH [BP+-8]
	CALL print_output
	;Source code line: 27 printing local variable k
	PUSH [BP+-6]
	CALL print_output
	;Source code line: 30 loading integer constant 0
	PUSH 0
	;Source code line: 30 returning from main function
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
