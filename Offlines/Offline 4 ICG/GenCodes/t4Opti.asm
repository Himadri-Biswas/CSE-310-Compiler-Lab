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
	;Source code line: 4 using global variable a
	MOV BP, SP
	;Source code line: 4 loading integer constant 7
	PUSH a
	;Source code line: 4 assigning value to variable a
; 	PUSH 7 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 7
	;Source code line: 4 storing value in global variable a
	;Source code line: 4 cleaning expression result from stack
	MOV a, AX
	POP AX
	MOV SP, BP
POP BP
		RET 
func_a ENDP
foo PROC
	PUSH BP
	;Source code line: 8 using local variable a
	MOV BP, SP
	;Source code line: 8 using local variable a
	PUSH [BP+4]
	;Source code line: 8 loading integer constant 3
	PUSH [BP+4]
	;Source code line: 8 evaluating arithmetic operation +
; 	PUSH 3 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 3
	POP AX
	ADD AX, BX
	;Source code line: 8 assigning value to variable a
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 8 storing value in local variable a
	;Source code line: 8 cleaning expression result from stack
	MOV [BP+4], AX
	;Source code line: 9 using local variable a
	POP AX
	;Source code line: 9 returning from function foo
; 	PUSH [BP+4] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+4]
	MOV SP, BP
	POP BP
	RET 2
foo ENDP
bar PROC
	PUSH BP
	;Source code line: 14 using global variable c
	MOV BP, SP
	;Source code line: 14 loading integer constant 4
	PUSH c
	;Source code line: 14 using local variable a
	PUSH 4
	;Source code line: 14 evaluating multiplication/division operation *
; 	PUSH [BP+6] ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, [BP+6]
	POP AX
	;Pushing multiplication/division result onto stack
	IMUL BX
	;Source code line: 14 loading integer constant 2
	PUSH AX
	;Source code line: 14 using local variable b
	PUSH 2
	;Source code line: 14 evaluating multiplication/division operation *
; 	PUSH [BP+4] ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, [BP+4]
	POP AX
	;Pushing multiplication/division result onto stack
	IMUL BX
	;Source code line: 14 evaluating arithmetic operation +
; 	PUSH AX ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, AX
	POP AX
	ADD AX, BX
	;Source code line: 14 assigning value to variable c
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 14 storing value in global variable c
	;Source code line: 14 cleaning expression result from stack
	MOV c, AX
	;Source code line: 15 using global variable c
	POP AX
	;Source code line: 15 returning from function bar
; 	PUSH c ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, c
	MOV SP, BP
	POP BP
	RET 4
bar ENDP
main PROC
	MOV AX, @DATA
	MOV DS, AX
	PUSH BP
	;Source code line: 20 allocating 2 bytes for local variable i
	MOV BP, SP
	;Source code line: 20 allocating 2 bytes for local variable j
	PUSH BX
	;Source code line: 20 allocating 2 bytes for local variable k
	PUSH BX
	;Source code line: 20 allocating 2 bytes for local variable l
	PUSH BX
	;Source code line: 22 using local variable i
	PUSH BX
	;Source code line: 22 loading integer constant 5
	PUSH [BP+-2]
	;Source code line: 22 assigning value to variable i
; 	PUSH 5 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 5
	;Source code line: 22 storing value in local variable i
	;Source code line: 22 cleaning expression result from stack
	MOV [BP+-2], AX
	;Source code line: 23 using local variable j
	POP AX
	;Source code line: 23 loading integer constant 6
	PUSH [BP+-4]
	;Source code line: 23 assigning value to variable j
; 	PUSH 6 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 6
	;Source code line: 23 storing value in local variable j
	;Source code line: 23 cleaning expression result from stack
	MOV [BP+-4], AX
	;Source code line: 25 calling function func_a
	POP AX
	CALL func_a
	;Source code line: 25 cleaning expression result from stack
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 26 printing global variable a
	PUSH a
	;Source code line: 28 using local variable k
	CALL print_output
	;Source code line: 28 using local variable i
	PUSH [BP+-6]
	;Source code line: 28 calling function foo
	PUSH [BP+-2]
	CALL foo
	;Source code line: 28 assigning value to variable k
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 28 storing value in local variable k
	;Source code line: 28 cleaning expression result from stack
	MOV [BP+-6], AX
	;Source code line: 29 printing local variable k
	POP AX
	PUSH [BP+-6]
	;Source code line: 31 using local variable l
	CALL print_output
	;Source code line: 31 using local variable i
	PUSH [BP+-8]
	;Source code line: 31 using local variable j
	PUSH [BP+-2]
	;Source code line: 31 calling function bar
	PUSH [BP+-4]
	CALL bar
	;Source code line: 31 assigning value to variable l
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 31 storing value in local variable l
	;Source code line: 31 cleaning expression result from stack
	MOV [BP+-8], AX
	;Source code line: 32 printing local variable l
	POP AX
	PUSH [BP+-8]
	;Source code line: 34 using local variable j
	CALL print_output
	;Source code line: 34 loading integer constant 6
	PUSH [BP+-4]
	;Source code line: 34 using local variable i
	PUSH 6
	;Source code line: 34 using local variable j
	PUSH [BP+-2]
	;Source code line: 34 calling function bar
	PUSH [BP+-4]
	CALL bar
	;Source code line: 34 evaluating multiplication/division operation *
; 	PUSH AX ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, AX
	POP AX
	;Pushing multiplication/division result onto stack
	IMUL BX
	;Source code line: 34 loading integer constant 2
	PUSH AX
	;Source code line: 34 evaluating arithmetic operation +
; 	PUSH 2 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 2
	POP AX
	ADD AX, BX
	;Source code line: 34 loading integer constant 3
	PUSH AX
	;Source code line: 34 using local variable i
	PUSH 3
	;Source code line: 34 calling function foo
	PUSH [BP+-2]
	CALL foo
	;Source code line: 34 evaluating multiplication/division operation *
; 	PUSH AX ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, AX
	POP AX
	;Pushing multiplication/division result onto stack
	IMUL BX
	;Source code line: 34 evaluating arithmetic operation -
; 	PUSH AX ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, AX
	POP AX
	SUB AX, BX
	;Source code line: 34 assigning value to variable j
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 34 storing value in local variable j
	;Source code line: 34 cleaning expression result from stack
	MOV [BP+-4], AX
	;Source code line: 35 printing local variable j
	POP AX
	PUSH [BP+-4]
	;Source code line: 38 loading integer constant 0
	CALL print_output
	;Source code line: 38 returning from main function
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
