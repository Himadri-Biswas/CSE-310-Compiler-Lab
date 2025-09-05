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
	;Source code line: 3 allocating 2 bytes for local variable i
	MOV BP, SP
	;Source code line: 3 allocating 2 bytes for local variable j
	PUSH BX
	;Source code line: 3 allocating 2 bytes for local variable k
	PUSH BX
	;Source code line: 5 using local variable i
	PUSH BX
	;Source code line: 5 loading integer constant 3
	PUSH [BP+-2]
	;Source code line: 5 assigning value to variable i
; 	PUSH 3 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 3
	;Source code line: 5 storing value in local variable i
	;Source code line: 5 cleaning expression result from stack
	MOV [BP+-2], AX
	;Source code line: 6 using local variable j
	POP AX
	;Source code line: 6 loading integer constant 8
	PUSH [BP+-4]
	;Source code line: 6 assigning value to variable j
; 	PUSH 8 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 8
	;Source code line: 6 storing value in local variable j
	;Source code line: 6 cleaning expression result from stack
	MOV [BP+-4], AX
	;Source code line: 7 using local variable k
	POP AX
	;Source code line: 7 loading integer constant 6
	PUSH [BP+-6]
	;Source code line: 7 assigning value to variable k
; 	PUSH 6 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 6
	;Source code line: 7 storing value in local variable k
	;Source code line: 7 cleaning expression result from stack
	MOV [BP+-6], AX
	;Source code line: 10 using local variable i
	POP AX
	;Source code line: 10 loading integer constant 3
	PUSH [BP+-2]
	;Source code line: 10 evaluating relational operation ==
; 	PUSH 3 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 3
	POP AX
	CMP AX, BX
	;Source code line: 10 if relational operation result is false
	JE L_1
	PUSH 0
	JMP L_2
	;Source code line: 10 if relational operation result is true
L_1:
	PUSH 1
	;Source code line: 10 checking IF condition
L_2:
	POP AX
	CMP AX, 0
	JE L_4
	JMP L_3
	;Source code line: 11 printing local variable j
L_3:
	PUSH [BP+-4]
	CALL print_output
	;Source code line: 14 using local variable j
L_4:
	;Source code line: 14 loading integer constant 8
	PUSH [BP+-4]
	;Source code line: 14 evaluating relational operation <
; 	PUSH 8 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 8
	POP AX
	CMP AX, BX
	;Source code line: 14 if relational operation result is false
	JL L_5
	PUSH 0
	JMP L_6
	;Source code line: 14 if relational operation result is true
L_5:
	PUSH 1
	;Source code line: 14 checking IF condition
L_6:
	POP AX
	CMP AX, 0
	JE L_8
	JMP L_7
	;Source code line: 15 printing local variable i
L_7:
	PUSH [BP+-2]
	CALL print_output
	JMP L_9
	;Source code line: 18 printing local variable k
L_8:
	PUSH [BP+-6]
	CALL print_output
	;Source code line: 21 using local variable k
L_9:
	;Source code line: 21 loading integer constant 6
	PUSH [BP+-6]
	;Source code line: 21 evaluating relational operation !=
; 	PUSH 6 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 6
	POP AX
	CMP AX, BX
	;Source code line: 21 if relational operation result is false
	JNE L_10
	PUSH 0
	JMP L_11
	;Source code line: 21 if relational operation result is true
L_10:
	PUSH 1
	;Source code line: 21 checking IF condition
L_11:
	POP AX
	CMP AX, 0
	JE L_13
	JMP L_12
	;Source code line: 22 printing local variable k
L_12:
	PUSH [BP+-6]
	CALL print_output
	JMP L_14
	;Source code line: 24 using local variable j
L_13:
	;Source code line: 24 loading integer constant 8
	PUSH [BP+-4]
	;Source code line: 24 evaluating relational operation >
; 	PUSH 8 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 8
	POP AX
	CMP AX, BX
	;Source code line: 24 if relational operation result is false
	JG L_15
	PUSH 0
	JMP L_16
	;Source code line: 24 if relational operation result is true
L_15:
	PUSH 1
	;Source code line: 24 checking IF condition
L_16:
	POP AX
	CMP AX, 0
	JE L_18
	JMP L_17
	;Source code line: 25 printing local variable j
L_17:
	PUSH [BP+-4]
	CALL print_output
	JMP L_19
	;Source code line: 27 using local variable i
L_18:
	;Source code line: 27 loading integer constant 5
	PUSH [BP+-2]
	;Source code line: 27 evaluating relational operation <
; 	PUSH 5 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 5
	POP AX
	CMP AX, BX
	;Source code line: 27 if relational operation result is false
	JL L_20
	PUSH 0
	JMP L_21
	;Source code line: 27 if relational operation result is true
L_20:
	PUSH 1
	;Source code line: 27 checking IF condition
L_21:
	POP AX
	CMP AX, 0
	JE L_23
	JMP L_22
	;Source code line: 28 printing local variable i
L_22:
	PUSH [BP+-2]
	CALL print_output
	JMP L_24
	;Source code line: 31 using local variable k
L_23:
	;Source code line: 31 loading integer constant 0
	PUSH [BP+-6]
	;Source code line: 31 assigning value to variable k
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 0
	;Source code line: 31 storing value in local variable k
	;Source code line: 31 cleaning expression result from stack
	MOV [BP+-6], AX
	;Source code line: 32 printing local variable k
	POP AX
	PUSH [BP+-6]
	CALL print_output
L_24:
L_19:
	;Source code line: 36 loading integer constant 0
L_14:
	;Source code line: 36 returning from main function
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
