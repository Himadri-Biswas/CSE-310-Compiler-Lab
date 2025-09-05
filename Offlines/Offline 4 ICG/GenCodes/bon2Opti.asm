.MODEL SMALL
.STACK 1000H
.DATA
	CR EQU 0DH
	LF EQU 0AH
.Code
func PROC
	PUSH BP
	;Source code line: 2 allocating 2 bytes for local variable i
	MOV BP, SP
	;Source code line: 3 using local variable n
	PUSH BX
	;Source code line: 3 loading integer constant 0
	PUSH [BP+4]
	;Source code line: 3 evaluating relational operation ==
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 0
	POP AX
	CMP AX, BX
	;Source code line: 3 if relational operation result is false
	JE L_1
	PUSH 0
	JMP L_2
	;Source code line: 3 if relational operation result is true
L_1:
	PUSH 1
	;Source code line: 3 checking IF condition
L_2:
	POP AX
	CMP AX, 0
	JE L_4
	JMP L_3
	;Source code line: 3 loading integer constant 0
L_3:
	;Source code line: 3 returning from function func
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 0
	MOV SP, BP
	POP BP
	RET 2
	;Source code line: 4 using local variable i
L_4:
	;Source code line: 4 using local variable n
	PUSH [BP+-2]
	;Source code line: 4 assigning value to variable i
; 	PUSH [BP+4] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+4]
	;Source code line: 4 storing value in local variable i
	;Source code line: 4 cleaning expression result from stack
	MOV [BP+-2], AX
	;Source code line: 5 using local variable n
	POP AX
	;Source code line: 5 loading integer constant 1
	PUSH [BP+4]
	;Source code line: 5 evaluating arithmetic operation -
; 	PUSH 1 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 1
	POP AX
	SUB AX, BX
	;Source code line: 5 calling function func
	PUSH AX
	CALL func
	;Source code line: 5 using local variable i
	PUSH AX
	;Source code line: 5 evaluating arithmetic operation +
; 	PUSH [BP+-2] ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, [BP+-2]
	POP AX
	ADD AX, BX
	;Source code line: 5 returning from function func
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	MOV SP, BP
	POP BP
	RET 2
func ENDP
func2 PROC
	PUSH BP
	;Source code line: 9 allocating 2 bytes for local variable i
	MOV BP, SP
	;Source code line: 10 using local variable n
	PUSH BX
	;Source code line: 10 loading integer constant 0
	PUSH [BP+4]
	;Source code line: 10 evaluating relational operation ==
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 0
	POP AX
	CMP AX, BX
	;Source code line: 10 if relational operation result is false
	JE L_5
	PUSH 0
	JMP L_6
	;Source code line: 10 if relational operation result is true
L_5:
	PUSH 1
	;Source code line: 10 checking IF condition
L_6:
	POP AX
	CMP AX, 0
	JE L_8
	JMP L_7
	;Source code line: 10 loading integer constant 0
L_7:
	;Source code line: 10 returning from function func2
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 0
	MOV SP, BP
	POP BP
	RET 2
	;Source code line: 11 using local variable i
L_8:
	;Source code line: 11 using local variable n
	PUSH [BP+-2]
	;Source code line: 11 assigning value to variable i
; 	PUSH [BP+4] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+4]
	;Source code line: 11 storing value in local variable i
	;Source code line: 11 cleaning expression result from stack
	MOV [BP+-2], AX
	;Source code line: 12 using local variable n
	POP AX
	;Source code line: 12 loading integer constant 1
	PUSH [BP+4]
	;Source code line: 12 evaluating arithmetic operation -
; 	PUSH 1 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 1
	POP AX
	SUB AX, BX
	;Source code line: 12 calling function func
	PUSH AX
	CALL func
	;Source code line: 12 using local variable i
	PUSH AX
	;Source code line: 12 evaluating arithmetic operation +
; 	PUSH [BP+-2] ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, [BP+-2]
	POP AX
	ADD AX, BX
	;Source code line: 12 returning from function func2
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	MOV SP, BP
	POP BP
	RET 2
func2 ENDP
main PROC
	MOV AX, @DATA
	MOV DS, AX
	PUSH BP
	;Source code line: 16 allocating 2 bytes for local variable a
	MOV BP, SP
	;Source code line: 17 using local variable a
	PUSH BX
	;Source code line: 17 loading integer constant 7
	PUSH [BP+-2]
	;Source code line: 17 calling function func
	PUSH 7
	CALL func
	;Source code line: 17 assigning value to variable a
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 17 storing value in local variable a
	;Source code line: 17 cleaning expression result from stack
	MOV [BP+-2], AX
	;Source code line: 18 printing local variable a
	POP AX
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
