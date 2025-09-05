.MODEL SMALL
.STACK 1000H
.DATA
	CR EQU 0DH
	LF EQU 0AH
.Code
foo PROC
	PUSH BP
	;Source code line: 2 using local variable a
	MOV BP, SP
	;Source code line: 2 using local variable b
	PUSH [BP+6]
	;Source code line: 2 evaluating arithmetic operation +
; 	PUSH [BP+4] ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, [BP+4]
	POP AX
	ADD AX, BX
	;Source code line: 2 loading integer constant 5
	PUSH AX
	;Source code line: 2 evaluating relational operation <=
; 	PUSH 5 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 5
	POP AX
	CMP AX, BX
	;Source code line: 2 if relational operation result is false
	JLE L_1
	PUSH 0
	JMP L_2
	;Source code line: 2 if relational operation result is true
L_1:
	PUSH 1
	;Source code line: 2 checking IF condition
L_2:
	POP AX
	CMP AX, 0
	JE L_4
	JMP L_3
	;Source code line: 3 loading integer constant 7
L_3:
	;Source code line: 3 returning from function foo
; 	PUSH 7 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 7
	MOV SP, BP
	POP BP
	RET 4
	;Source code line: 5 using local variable a
L_4:
	;Source code line: 5 loading integer constant 2
	PUSH [BP+6]
	;Source code line: 5 evaluating arithmetic operation -
; 	PUSH 2 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 2
	POP AX
	SUB AX, BX
	;Source code line: 5 using local variable b
	PUSH AX
	;Source code line: 5 loading integer constant 1
	PUSH [BP+4]
	;Source code line: 5 evaluating arithmetic operation -
; 	PUSH 1 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 1
	POP AX
	SUB AX, BX
	;Source code line: 5 calling function foo
	PUSH AX
	CALL foo
	;Source code line: 5 loading integer constant 2
	PUSH AX
	;Source code line: 5 using local variable a
	PUSH 2
	;Source code line: 5 loading integer constant 1
	PUSH [BP+6]
	;Source code line: 5 evaluating arithmetic operation -
; 	PUSH 1 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 1
	POP AX
	SUB AX, BX
	;Source code line: 5 using local variable b
	PUSH AX
	;Source code line: 5 loading integer constant 2
	PUSH [BP+4]
	;Source code line: 5 evaluating arithmetic operation -
; 	PUSH 2 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 2
	POP AX
	SUB AX, BX
	;Source code line: 5 calling function foo
	PUSH AX
	CALL foo
	;Source code line: 5 evaluating multiplication/division operation *
; 	PUSH AX ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, AX
	POP AX
	;Pushing multiplication/division result onto stack
	IMUL BX
	;Source code line: 5 evaluating arithmetic operation +
; 	PUSH AX ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, AX
	POP AX
	ADD AX, BX
	;Source code line: 5 returning from function foo
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	MOV SP, BP
	POP BP
	RET 4
foo ENDP
main PROC
	MOV AX, @DATA
	MOV DS, AX
	PUSH BP
	;Source code line: 10 allocating 2 bytes for local variable i
	MOV BP, SP
	;Source code line: 10 allocating 2 bytes for local variable j
	PUSH BX
	;Source code line: 10 allocating 2 bytes for local variable k
	PUSH BX
	;Source code line: 11 using local variable i
	PUSH BX
	;Source code line: 11 loading integer constant 7
	PUSH [BP+-2]
	;Source code line: 11 assigning value to variable i
; 	PUSH 7 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 7
	;Source code line: 11 storing value in local variable i
	;Source code line: 11 cleaning expression result from stack
	MOV [BP+-2], AX
	;Source code line: 12 using local variable j
	POP AX
	;Source code line: 12 loading integer constant 3
	PUSH [BP+-4]
	;Source code line: 12 assigning value to variable j
; 	PUSH 3 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 3
	;Source code line: 12 storing value in local variable j
	;Source code line: 12 cleaning expression result from stack
	MOV [BP+-4], AX
	;Source code line: 14 using local variable k
	POP AX
	;Source code line: 14 using local variable i
	PUSH [BP+-6]
	;Source code line: 14 using local variable j
	PUSH [BP+-2]
	;Source code line: 14 calling function foo
	PUSH [BP+-4]
	CALL foo
	;Source code line: 14 assigning value to variable k
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 14 storing value in local variable k
	;Source code line: 14 cleaning expression result from stack
	MOV [BP+-6], AX
	;Source code line: 15 printing local variable k
	POP AX
	PUSH [BP+-6]
	;Source code line: 17 loading integer constant 0
	CALL print_output
	;Source code line: 17 returning from main function
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
