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
	;Source code line: 2 allocating 2 bytes for local variable i
	PUSH BX
	;Source code line: 3 using local variable i
	PUSH [BP+-2]
	;Source code line: 3 loading integer constant 0
	PUSH 0
	;Source code line: 3 evaluating relational operation >
	POP BX
	POP AX
	CMP AX, BX
	JG L_1
	;Source code line: 3 if relational operation result is false
	PUSH 0
	JMP L_2
L_1:
	;Source code line: 3 if relational operation result is true
	PUSH 1
L_2:
	;Source code line: 3 using local variable i
	PUSH [BP+-2]
	;Source code line: 3 loading integer constant 10
	PUSH 10
	;Source code line: 3 evaluating relational operation <
	POP BX
	POP AX
	CMP AX, BX
	JL L_3
	;Source code line: 3 if relational operation result is false
	PUSH 0
	JMP L_4
L_3:
	;Source code line: 3 if relational operation result is true
	PUSH 1
L_4:
	;Source code line: 3 evaluating logical OR operation
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
	;Source code line: 3 checking IF condition
	POP AX
	CMP AX, 0
	JE L_8
	JMP L_7
L_7:
	;Source code line: 4 using local variable i
	PUSH [BP+-2]
	;Source code line: 4 loading integer constant 100
	PUSH 100
	;Source code line: 4 assigning value to variable i
	POP AX
	;Source code line: 4 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 4 cleaning expression result from stack
	POP AX
	JMP L_9
L_8:
	;Source code line: 6 using local variable i
	PUSH [BP+-2]
	;Source code line: 6 loading integer constant 200
	PUSH 200
	;Source code line: 6 assigning value to variable i
	POP AX
	;Source code line: 6 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 6 cleaning expression result from stack
	POP AX
L_9:
	;Source code line: 8 using local variable i
	PUSH [BP+-2]
	;Source code line: 8 loading integer constant 20
	PUSH 20
	;Source code line: 8 evaluating relational operation >
	POP BX
	POP AX
	CMP AX, BX
	JG L_10
	;Source code line: 8 if relational operation result is false
	PUSH 0
	JMP L_11
L_10:
	;Source code line: 8 if relational operation result is true
	PUSH 1
L_11:
	;Source code line: 8 using local variable i
	PUSH [BP+-2]
	;Source code line: 8 loading integer constant 30
	PUSH 30
	;Source code line: 8 evaluating relational operation <
	POP BX
	POP AX
	CMP AX, BX
	JL L_12
	;Source code line: 8 if relational operation result is false
	PUSH 0
	JMP L_13
L_12:
	;Source code line: 8 if relational operation result is true
	PUSH 1
L_13:
	;Source code line: 8 evaluating logical AND operation
	POP BX
	POP AX
	CMP AX, 0
	JE L_14
	CMP BX, 0
	JE L_14
	PUSH 1
	JMP L_15
L_14:
	PUSH 0
L_15:
	;Source code line: 8 checking IF condition
	POP AX
	CMP AX, 0
	JE L_17
	JMP L_16
L_16:
	;Source code line: 9 using local variable i
	PUSH [BP+-2]
	;Source code line: 9 loading integer constant 300
	PUSH 300
	;Source code line: 9 assigning value to variable i
	POP AX
	;Source code line: 9 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 9 cleaning expression result from stack
	POP AX
	JMP L_18
L_17:
	;Source code line: 11 using local variable i
	PUSH [BP+-2]
	;Source code line: 11 loading integer constant 400
	PUSH 400
	;Source code line: 11 assigning value to variable i
	POP AX
	;Source code line: 11 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 11 cleaning expression result from stack
	POP AX
L_18:
	;Source code line: 13 using local variable i
	PUSH [BP+-2]
	;Source code line: 13 loading integer constant 40
	PUSH 40
	;Source code line: 13 evaluating relational operation >
	POP BX
	POP AX
	CMP AX, BX
	JG L_19
	;Source code line: 13 if relational operation result is false
	PUSH 0
	JMP L_20
L_19:
	;Source code line: 13 if relational operation result is true
	PUSH 1
L_20:
	;Source code line: 13 using local variable i
	PUSH [BP+-2]
	;Source code line: 13 loading integer constant 50
	PUSH 50
	;Source code line: 13 evaluating relational operation <
	POP BX
	POP AX
	CMP AX, BX
	JL L_21
	;Source code line: 13 if relational operation result is false
	PUSH 0
	JMP L_22
L_21:
	;Source code line: 13 if relational operation result is true
	PUSH 1
L_22:
	;Source code line: 13 evaluating logical AND operation
	POP BX
	POP AX
	CMP AX, 0
	JE L_23
	CMP BX, 0
	JE L_23
	PUSH 1
	JMP L_24
L_23:
	PUSH 0
L_24:
	;Source code line: 13 using local variable i
	PUSH [BP+-2]
	;Source code line: 13 loading integer constant 60
	PUSH 60
	;Source code line: 13 evaluating relational operation <
	POP BX
	POP AX
	CMP AX, BX
	JL L_25
	;Source code line: 13 if relational operation result is false
	PUSH 0
	JMP L_26
L_25:
	;Source code line: 13 if relational operation result is true
	PUSH 1
L_26:
	;Source code line: 13 using local variable i
	PUSH [BP+-2]
	;Source code line: 13 loading integer constant 70
	PUSH 70
	;Source code line: 13 evaluating relational operation >
	POP BX
	POP AX
	CMP AX, BX
	JG L_27
	;Source code line: 13 if relational operation result is false
	PUSH 0
	JMP L_28
L_27:
	;Source code line: 13 if relational operation result is true
	PUSH 1
L_28:
	;Source code line: 13 evaluating logical AND operation
	POP BX
	POP AX
	CMP AX, 0
	JE L_29
	CMP BX, 0
	JE L_29
	PUSH 1
	JMP L_30
L_29:
	PUSH 0
L_30:
	;Source code line: 13 evaluating logical OR operation
	POP BX
	POP AX
	CMP AX, 0
	JNE L_31
	CMP BX, 0
	JNE L_31
	PUSH 0
	JMP L_32
L_31:
	PUSH 1
L_32:
	;Source code line: 13 checking IF condition
	POP AX
	CMP AX, 0
	JE L_34
	JMP L_33
L_33:
	;Source code line: 14 using local variable i
	PUSH [BP+-2]
	;Source code line: 14 loading integer constant 500
	PUSH 500
	;Source code line: 14 assigning value to variable i
	POP AX
	;Source code line: 14 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 14 cleaning expression result from stack
	POP AX
	JMP L_35
L_34:
	;Source code line: 16 using local variable i
	PUSH [BP+-2]
	;Source code line: 16 loading integer constant 600
	PUSH 600
	;Source code line: 16 assigning value to variable i
	POP AX
	;Source code line: 16 storing value in local variable i
	MOV [BP+-2], AX
	;Source code line: 16 cleaning expression result from stack
	POP AX
L_35:
	;Source code line: 17 printing local variable i
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
