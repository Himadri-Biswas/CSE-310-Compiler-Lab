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
	;Source code line: 5 using local variable i
	PUSH [BP+-2]
	;Source code line: 5 loading integer constant 3
	PUSH 3
	;Source code line: 5 assigning value to variable i
	POP AX
	;Source code line: 5 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 5 cleaning expression result from stack
	POP AX
	;Source code line: 6 using local variable j
	PUSH [BP+-4]
	;Source code line: 6 loading integer constant 8
	PUSH 8
	;Source code line: 6 assigning value to variable j
	POP AX
	;Source code line: 6 storing value in local variable j
	MOV [BP+-4], AX
	;Source code line: 6 cleaning expression result from stack
	POP AX
	;Source code line: 7 using local variable k
	PUSH [BP+-6]
	;Source code line: 7 loading integer constant 6
	PUSH 6
	;Source code line: 7 assigning value to variable k
	POP AX
	;Source code line: 7 storing value in local variable k
	MOV [BP+-6], AX
	;Source code line: 7 cleaning expression result from stack
	POP AX
	;Source code line: 10 using local variable i
	PUSH [BP+-2]
	;Source code line: 10 loading integer constant 3
	PUSH 3
	;Source code line: 10 evaluating relational operation ==
	POP BX
	POP AX
	CMP AX, BX
	JE L_1
	;Source code line: 10 if relational operation result is false
	PUSH 0
	JMP L_2
L_1:
	;Source code line: 10 if relational operation result is true
	PUSH 1
L_2:
	;Source code line: 10 checking IF condition
	POP AX
	CMP AX, 0
	JE L_4
	JMP L_3
L_3:
	;Source code line: 11 printing local variable j
	PUSH [BP+-4]
	CALL print_output
L_4:
	;Source code line: 14 using local variable j
	PUSH [BP+-4]
	;Source code line: 14 loading integer constant 8
	PUSH 8
	;Source code line: 14 evaluating relational operation <
	POP BX
	POP AX
	CMP AX, BX
	JL L_5
	;Source code line: 14 if relational operation result is false
	PUSH 0
	JMP L_6
L_5:
	;Source code line: 14 if relational operation result is true
	PUSH 1
L_6:
	;Source code line: 14 checking IF condition
	POP AX
	CMP AX, 0
	JE L_8
	JMP L_7
L_7:
	;Source code line: 15 printing local variable i
	PUSH [BP+-2]
	CALL print_output
	JMP L_9
L_8:
	;Source code line: 18 printing local variable k
	PUSH [BP+-6]
	CALL print_output
L_9:
	;Source code line: 21 using local variable k
	PUSH [BP+-6]
	;Source code line: 21 loading integer constant 6
	PUSH 6
	;Source code line: 21 evaluating relational operation !=
	POP BX
	POP AX
	CMP AX, BX
	JNE L_10
	;Source code line: 21 if relational operation result is false
	PUSH 0
	JMP L_11
L_10:
	;Source code line: 21 if relational operation result is true
	PUSH 1
L_11:
	;Source code line: 21 checking IF condition
	POP AX
	CMP AX, 0
	JE L_13
	JMP L_12
L_12:
	;Source code line: 22 printing local variable k
	PUSH [BP+-6]
	CALL print_output
	JMP L_14
L_13:
	;Source code line: 24 using local variable j
	PUSH [BP+-4]
	;Source code line: 24 loading integer constant 8
	PUSH 8
	;Source code line: 24 evaluating relational operation >
	POP BX
	POP AX
	CMP AX, BX
	JG L_15
	;Source code line: 24 if relational operation result is false
	PUSH 0
	JMP L_16
L_15:
	;Source code line: 24 if relational operation result is true
	PUSH 1
L_16:
	;Source code line: 24 checking IF condition
	POP AX
	CMP AX, 0
	JE L_18
	JMP L_17
L_17:
	;Source code line: 25 printing local variable j
	PUSH [BP+-4]
	CALL print_output
	JMP L_19
L_18:
	;Source code line: 27 using local variable i
	PUSH [BP+-2]
	;Source code line: 27 loading integer constant 5
	PUSH 5
	;Source code line: 27 evaluating relational operation <
	POP BX
	POP AX
	CMP AX, BX
	JL L_20
	;Source code line: 27 if relational operation result is false
	PUSH 0
	JMP L_21
L_20:
	;Source code line: 27 if relational operation result is true
	PUSH 1
L_21:
	;Source code line: 27 checking IF condition
	POP AX
	CMP AX, 0
	JE L_23
	JMP L_22
L_22:
	;Source code line: 28 printing local variable i
	PUSH [BP+-2]
	CALL print_output
	JMP L_24
L_23:
	;Source code line: 31 using local variable k
	PUSH [BP+-6]
	;Source code line: 31 loading integer constant 0
	PUSH 0
	;Source code line: 31 assigning value to variable k
	POP AX
	;Source code line: 31 storing value in local variable k
	MOV [BP+-6], AX
	;Source code line: 31 cleaning expression result from stack
	POP AX
	;Source code line: 32 printing local variable k
	PUSH [BP+-6]
	CALL print_output
L_24:
L_19:
L_14:
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
