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
	;Source code line: 2 allocating 2 bytes for local variable i
	MOV BP, SP
	;Source code line: 3 using local variable i
	PUSH BX
	;Source code line: 3 loading integer constant 0
	PUSH [BP+-2]
	;Source code line: 3 evaluating relational operation >
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 0
	POP AX
	CMP AX, BX
	;Source code line: 3 if relational operation result is false
	JG L_1
	PUSH 0
	JMP L_2
	;Source code line: 3 if relational operation result is true
L_1:
	PUSH 1
	;Source code line: 3 using local variable i
L_2:
	;Source code line: 3 loading integer constant 10
	PUSH [BP+-2]
	;Source code line: 3 evaluating relational operation <
; 	PUSH 10 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 10
	POP AX
	CMP AX, BX
	;Source code line: 3 if relational operation result is false
	JL L_3
	PUSH 0
	JMP L_4
	;Source code line: 3 if relational operation result is true
L_3:
	PUSH 1
	;Source code line: 3 evaluating logical OR operation
L_4:
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
	;Source code line: 3 checking IF condition
L_6:
	POP AX
	CMP AX, 0
	JE L_8
	JMP L_7
	;Source code line: 4 using local variable i
L_7:
	;Source code line: 4 loading integer constant 100
	PUSH [BP+-2]
	;Source code line: 4 assigning value to variable i
; 	PUSH 100 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 100
	;Source code line: 4 storing value in local variable i
	;Source code line: 4 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	JMP L_9
	;Source code line: 6 using local variable i
L_8:
	;Source code line: 6 loading integer constant 200
	PUSH [BP+-2]
	;Source code line: 6 assigning value to variable i
; 	PUSH 200 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 200
	;Source code line: 6 storing value in local variable i
	;Source code line: 6 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	;Source code line: 8 using local variable i
L_9:
	;Source code line: 8 loading integer constant 20
	PUSH [BP+-2]
	;Source code line: 8 evaluating relational operation >
; 	PUSH 20 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 20
	POP AX
	CMP AX, BX
	;Source code line: 8 if relational operation result is false
	JG L_10
	PUSH 0
	JMP L_11
	;Source code line: 8 if relational operation result is true
L_10:
	PUSH 1
	;Source code line: 8 using local variable i
L_11:
	;Source code line: 8 loading integer constant 30
	PUSH [BP+-2]
	;Source code line: 8 evaluating relational operation <
; 	PUSH 30 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 30
	POP AX
	CMP AX, BX
	;Source code line: 8 if relational operation result is false
	JL L_12
	PUSH 0
	JMP L_13
	;Source code line: 8 if relational operation result is true
L_12:
	PUSH 1
	;Source code line: 8 evaluating logical AND operation
L_13:
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
	;Source code line: 8 checking IF condition
L_15:
	POP AX
	CMP AX, 0
	JE L_17
	JMP L_16
	;Source code line: 9 using local variable i
L_16:
	;Source code line: 9 loading integer constant 300
	PUSH [BP+-2]
	;Source code line: 9 assigning value to variable i
; 	PUSH 300 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 300
	;Source code line: 9 storing value in local variable i
	;Source code line: 9 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	JMP L_18
	;Source code line: 11 using local variable i
L_17:
	;Source code line: 11 loading integer constant 400
	PUSH [BP+-2]
	;Source code line: 11 assigning value to variable i
; 	PUSH 400 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 400
	;Source code line: 11 storing value in local variable i
	;Source code line: 11 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	;Source code line: 13 using local variable i
L_18:
	;Source code line: 13 loading integer constant 40
	PUSH [BP+-2]
	;Source code line: 13 evaluating relational operation >
; 	PUSH 40 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 40
	POP AX
	CMP AX, BX
	;Source code line: 13 if relational operation result is false
	JG L_19
	PUSH 0
	JMP L_20
	;Source code line: 13 if relational operation result is true
L_19:
	PUSH 1
	;Source code line: 13 using local variable i
L_20:
	;Source code line: 13 loading integer constant 50
	PUSH [BP+-2]
	;Source code line: 13 evaluating relational operation <
; 	PUSH 50 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 50
	POP AX
	CMP AX, BX
	;Source code line: 13 if relational operation result is false
	JL L_21
	PUSH 0
	JMP L_22
	;Source code line: 13 if relational operation result is true
L_21:
	PUSH 1
	;Source code line: 13 evaluating logical AND operation
L_22:
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
	;Source code line: 13 using local variable i
L_24:
	;Source code line: 13 loading integer constant 60
	PUSH [BP+-2]
	;Source code line: 13 evaluating relational operation <
; 	PUSH 60 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 60
	POP AX
	CMP AX, BX
	;Source code line: 13 if relational operation result is false
	JL L_25
	PUSH 0
	JMP L_26
	;Source code line: 13 if relational operation result is true
L_25:
	PUSH 1
	;Source code line: 13 using local variable i
L_26:
	;Source code line: 13 loading integer constant 70
	PUSH [BP+-2]
	;Source code line: 13 evaluating relational operation >
; 	PUSH 70 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 70
	POP AX
	CMP AX, BX
	;Source code line: 13 if relational operation result is false
	JG L_27
	PUSH 0
	JMP L_28
	;Source code line: 13 if relational operation result is true
L_27:
	PUSH 1
	;Source code line: 13 evaluating logical AND operation
L_28:
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
	;Source code line: 13 evaluating logical OR operation
L_30:
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
	;Source code line: 13 checking IF condition
L_32:
	POP AX
	CMP AX, 0
	JE L_34
	JMP L_33
	;Source code line: 14 using local variable i
L_33:
	;Source code line: 14 loading integer constant 500
	PUSH [BP+-2]
	;Source code line: 14 assigning value to variable i
; 	PUSH 500 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 500
	;Source code line: 14 storing value in local variable i
	;Source code line: 14 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	JMP L_35
	;Source code line: 16 using local variable i
L_34:
	;Source code line: 16 loading integer constant 600
	PUSH [BP+-2]
	;Source code line: 16 assigning value to variable i
; 	PUSH 600 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 600
	;Source code line: 16 storing value in local variable i
	;Source code line: 16 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	;Source code line: 17 printing local variable i
L_35:
	PUSH [BP+-2]
	;Source code line: 19 loading integer constant 0
	CALL print_output
	;Source code line: 19 returning from main function
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
    MOV BP, SP   ;BP points to the top of the stack
    ;if(BX >= 0) then the number is positive
    MOV BX, [BP+4]   ;The number to be printed
    CMP BX, 0
    ;else, the number is negative
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
        MOV BX, 10  ;BX has the divisor, AX has the dividend
        ;quotient is in AX and remainder is in DX
        DIV BX
        PUSH DX   ;Push the remainder
        ;if(AX == 0) then break the loop
        INC CX  ;CX++
        CMP AX, 0
        ;else continue
        JE END_PUSH_WHILE
        JMP PUSH_WHILE
    END_PUSH_WHILE:
    MOV AH, 2
    POP_WHILE:
        POP DX  ;Pop the remainder
        ADD DL, '0'
        INT 21H  ; DL has the desired character now
        ;if(CX <= 0) then end loop
        DEC CX  ;CX--
        CMP CX, 0
        ;else continue
        JLE END_POP_WHILE
        JMP POP_WHILE
    ;Print newline
    END_POP_WHILE:
    MOV DL, 0DH
    INT 21H
    MOV DL, 0AH
    INT 21H
    POP BP  ; Restore BP
    RET 2
PRINT_OUTPUT ENDP
END main
