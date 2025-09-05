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
	;Source code line: 3 allocating 2 bytes for local variable ll
	PUSH BX
	;Source code line: 5 using local variable i
	PUSH BX
	;Source code line: 5 loading integer constant 0
	PUSH [BP+-2]
	;Source code line: 5 assigning value to variable i
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 0
	;Source code line: 5 storing value in local variable i
	;Source code line: 5 initializing FOR loop
	MOV [BP+-2], AX
	;Source code line: 5 using local variable i
L_1:
	;Source code line: 5 loading integer constant 6
	PUSH [BP+-2]
	;Source code line: 5 evaluating relational operation <
; 	PUSH 6 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 6
	POP AX
	CMP AX, BX
	;Source code line: 5 if relational operation result is false
	JL L_4
	PUSH 0
	JMP L_5
	;Source code line: 5 if relational operation result is true
L_4:
	PUSH 1
	;Source code line: 5 checking loop condition
L_5:
	POP AX
	CMP AX, 0
	JE L_2
	JMP L_3
	;Source code line: 6 printing local variable i
L_3:
	PUSH [BP+-2]
	;Source code line: 7 incrementing variable i
	CALL print_output
; 	PUSH [BP+-2] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+-2]
	PUSH AX
	INC AX
	MOV [BP+-2], AX
	;Source code line: 7 jumping back to loop condition check
	POP AX
	JMP L_1
	;Source code line: 9 using local variable k
L_2:
	;Source code line: 9 loading integer constant 4
	PUSH [BP+-6]
	;Source code line: 9 assigning value to variable k
; 	PUSH 4 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 4
	;Source code line: 9 storing value in local variable k
	;Source code line: 9 cleaning expression result from stack
	MOV [BP+-6], AX
	;Source code line: 10 using local variable ll
	POP AX
	;Source code line: 10 loading integer constant 6
	PUSH [BP+-8]
	;Source code line: 10 assigning value to variable ll
; 	PUSH 6 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 6
	;Source code line: 10 storing value in local variable ll
	;Source code line: 10 cleaning expression result from stack
	MOV [BP+-8], AX
	POP AX
	;Source code line: 11 using local variable k
L_6:
	;Source code line: 11 loading integer constant 0
	PUSH [BP+-6]
	;Source code line: 11 evaluating relational operation >
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 0
	POP AX
	CMP AX, BX
	;Source code line: 11 if relational operation result is false
	JG L_9
	PUSH 0
	JMP L_10
	;Source code line: 11 if relational operation result is true
L_9:
	PUSH 1
	;Source code line: 11 checking WHILE condition
L_10:
	POP AX
	CMP AX, 0
	JE L_7
	JMP L_8
	;Source code line: 12 using local variable ll
L_8:
	;Source code line: 12 using local variable ll
	PUSH [BP+-8]
	;Source code line: 12 loading integer constant 3
	PUSH [BP+-8]
	;Source code line: 12 evaluating arithmetic operation +
; 	PUSH 3 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 3
	POP AX
	ADD AX, BX
	;Source code line: 12 assigning value to variable ll
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 12 storing value in local variable ll
	;Source code line: 12 cleaning expression result from stack
	MOV [BP+-8], AX
	;Source code line: 13 using local variable k
	POP AX
; 	PUSH [BP+-6] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+-6]
	PUSH AX
	DEC AX
	;Source code line: 13 cleaning expression result from stack
	MOV [BP+-6], AX
	POP AX
	JMP L_6
	;Source code line: 16 printing local variable ll
L_7:
	PUSH [BP+-8]
	;Source code line: 17 printing local variable k
	CALL print_output
	PUSH [BP+-6]
	;Source code line: 19 using local variable k
	CALL print_output
	;Source code line: 19 loading integer constant 4
	PUSH [BP+-6]
	;Source code line: 19 assigning value to variable k
; 	PUSH 4 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 4
	;Source code line: 19 storing value in local variable k
	;Source code line: 19 cleaning expression result from stack
	MOV [BP+-6], AX
	;Source code line: 20 using local variable ll
	POP AX
	;Source code line: 20 loading integer constant 6
	PUSH [BP+-8]
	;Source code line: 20 assigning value to variable ll
; 	PUSH 6 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 6
	;Source code line: 20 storing value in local variable ll
	;Source code line: 20 cleaning expression result from stack
	MOV [BP+-8], AX
	POP AX
	;Source code line: 22 using local variable k
L_11:
; 	PUSH [BP+-6] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+-6]
	PUSH AX
	DEC AX
	;Source code line: 22 checking WHILE condition
	MOV [BP+-6], AX
	POP AX
	CMP AX, 0
	JE L_12
	JMP L_13
	;Source code line: 23 using local variable ll
L_13:
	;Source code line: 23 using local variable ll
	PUSH [BP+-8]
	;Source code line: 23 loading integer constant 3
	PUSH [BP+-8]
	;Source code line: 23 evaluating arithmetic operation +
; 	PUSH 3 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 3
	POP AX
	ADD AX, BX
	;Source code line: 23 assigning value to variable ll
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 23 storing value in local variable ll
	;Source code line: 23 cleaning expression result from stack
	MOV [BP+-8], AX
	POP AX
	JMP L_11
	;Source code line: 26 printing local variable ll
L_12:
	PUSH [BP+-8]
	;Source code line: 27 printing local variable k
	CALL print_output
	PUSH [BP+-6]
	;Source code line: 30 loading integer constant 0
	CALL print_output
	;Source code line: 30 returning from main function
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
