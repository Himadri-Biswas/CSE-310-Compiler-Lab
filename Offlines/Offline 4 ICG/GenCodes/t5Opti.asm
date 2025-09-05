.MODEL SMALL
.STACK 1000H
.DATA
	CR EQU 0DH
	LF EQU 0AH
.Code
f PROC
	PUSH BP
	;Source code line: 2 allocating 2 bytes for local variable k
	MOV BP, SP
	;Source code line: 3 using local variable k
	PUSH BX
	;Source code line: 3 loading integer constant 5
	PUSH [BP+-2]
	;Source code line: 3 assigning value to variable k
; 	PUSH 5 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 5
	;Source code line: 3 storing value in local variable k
	;Source code line: 3 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	;Source code line: 4 using local variable k
L_1:
	;Source code line: 4 loading integer constant 0
	PUSH [BP+-2]
	;Source code line: 4 evaluating relational operation >
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 0
	POP AX
	CMP AX, BX
	;Source code line: 4 if relational operation result is false
	JG L_4
	PUSH 0
	JMP L_5
	;Source code line: 4 if relational operation result is true
L_4:
	PUSH 1
	;Source code line: 4 checking WHILE condition
L_5:
	POP AX
	CMP AX, 0
	JE L_2
	JMP L_3
	;Source code line: 5 using local variable a
L_3:
	;Source code line: 5 getting value in AX and saving original value for post-incrementing variable a
; 	PUSH [BP+4] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+4]
	;Source code line: 5 actually incrementing value of variable a
	PUSH AX
	;Source code line: 5 storing incremented value in local variable a
	INC AX
	;Source code line: 5 cleaning expression result from stack
	MOV [BP+4], AX
	;Source code line: 6 using local variable k
	POP AX
; 	PUSH [BP+-2] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+-2]
	PUSH AX
	DEC AX
	;Source code line: 6 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	JMP L_1
	;Source code line: 8 loading integer constant 3
L_2:
	;Source code line: 8 using local variable a
	PUSH 3
	;Source code line: 8 evaluating multiplication/division operation *
; 	PUSH [BP+4] ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, [BP+4]
	POP AX
	;Pushing multiplication/division result onto stack
	IMUL BX
	;Source code line: 8 loading integer constant 7
	PUSH AX
	;Source code line: 8 evaluating arithmetic operation -
; 	PUSH 7 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 7
	POP AX
	SUB AX, BX
	;Source code line: 8 returning from function f
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	MOV SP, BP
	POP BP
	;Source code line: 9 using local variable a
	RET 2
	;Source code line: 9 loading integer constant 9
	PUSH [BP+4]
	;Source code line: 9 assigning value to variable a
; 	PUSH 9 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 9
	;Source code line: 9 storing value in local variable a
	;Source code line: 9 cleaning expression result from stack
	MOV [BP+4], AX
	POP AX
f ENDP
g PROC
	PUSH BP
	;Source code line: 14 allocating 2 bytes for local variable x
	MOV BP, SP
	;Source code line: 14 allocating 2 bytes for local variable i
	PUSH BX
	;Source code line: 15 using local variable x
	PUSH BX
	;Source code line: 15 using local variable a
	PUSH [BP+-2]
	;Source code line: 15 calling function f
	PUSH [BP+6]
	CALL f
	;Source code line: 15 using local variable a
	PUSH AX
	;Source code line: 15 evaluating arithmetic operation +
; 	PUSH [BP+6] ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, [BP+6]
	POP AX
	ADD AX, BX
	;Source code line: 15 using local variable b
	PUSH AX
	;Source code line: 15 evaluating arithmetic operation +
; 	PUSH [BP+4] ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, [BP+4]
	POP AX
	ADD AX, BX
	;Source code line: 15 assigning value to variable x
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 15 storing value in local variable x
	;Source code line: 15 cleaning expression result from stack
	MOV [BP+-2], AX
	;Source code line: 17 using local variable i
	POP AX
	;Source code line: 17 loading integer constant 0
	PUSH [BP+-4]
	;Source code line: 17 assigning value to variable i
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 0
	;Source code line: 17 storing value in local variable i
	;Source code line: 17 initializing FOR loop
	MOV [BP+-4], AX
	;Source code line: 17 using local variable i
L_6:
	;Source code line: 17 loading integer constant 7
	PUSH [BP+-4]
	;Source code line: 17 evaluating relational operation <
; 	PUSH 7 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 7
	POP AX
	CMP AX, BX
	;Source code line: 17 if relational operation result is false
	JL L_9
	PUSH 0
	JMP L_10
	;Source code line: 17 if relational operation result is true
L_9:
	PUSH 1
	;Source code line: 17 checking loop condition
L_10:
	POP AX
	CMP AX, 0
	JE L_7
	JMP L_8
	;Source code line: 18 using local variable i
L_8:
	;Source code line: 18 loading integer constant 3
	PUSH [BP+-4]
	;Source code line: 18 evaluating multiplication/division operation %
; 	PUSH 3 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 3
	POP AX
	XOR DX, DX
	IDIV BX
	;Pushing multiplication/division result onto stack
	MOV AX,DX
	;Source code line: 18 loading integer constant 0
	PUSH AX
	;Source code line: 18 evaluating relational operation ==
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 0
	POP AX
	CMP AX, BX
	;Source code line: 18 if relational operation result is false
	JE L_11
	PUSH 0
	JMP L_12
	;Source code line: 18 if relational operation result is true
L_11:
	PUSH 1
	;Source code line: 18 checking IF condition
L_12:
	POP AX
	CMP AX, 0
	JE L_14
	JMP L_13
	;Source code line: 19 using local variable x
L_13:
	;Source code line: 19 using local variable x
	PUSH [BP+-2]
	;Source code line: 19 loading integer constant 5
	PUSH [BP+-2]
	;Source code line: 19 evaluating arithmetic operation +
; 	PUSH 5 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 5
	POP AX
	ADD AX, BX
	;Source code line: 19 assigning value to variable x
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 19 storing value in local variable x
	;Source code line: 19 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	JMP L_15
	;Source code line: 22 using local variable x
L_14:
	;Source code line: 22 using local variable x
	PUSH [BP+-2]
	;Source code line: 22 loading integer constant 1
	PUSH [BP+-2]
	;Source code line: 22 evaluating arithmetic operation -
; 	PUSH 1 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 1
	POP AX
	SUB AX, BX
	;Source code line: 22 assigning value to variable x
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 22 storing value in local variable x
	;Source code line: 22 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	;Source code line: 24 incrementing variable i
L_15:
; 	PUSH [BP+-4] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+-4]
	PUSH AX
	INC AX
	MOV [BP+-4], AX
	;Source code line: 24 jumping back to loop condition check
	POP AX
	JMP L_6
	;Source code line: 26 using local variable x
L_7:
	;Source code line: 26 returning from function g
; 	PUSH [BP+-2] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+-2]
	MOV SP, BP
	POP BP
	RET 4
g ENDP
main PROC
	MOV AX, @DATA
	MOV DS, AX
	PUSH BP
	;Source code line: 30 allocating 2 bytes for local variable a
	MOV BP, SP
	;Source code line: 30 allocating 2 bytes for local variable b
	PUSH BX
	;Source code line: 30 allocating 2 bytes for local variable i
	PUSH BX
	;Source code line: 31 using local variable a
	PUSH BX
	;Source code line: 31 loading integer constant 1
	PUSH [BP+-2]
	;Source code line: 31 assigning value to variable a
; 	PUSH 1 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 1
	;Source code line: 31 storing value in local variable a
	;Source code line: 31 cleaning expression result from stack
	MOV [BP+-2], AX
	;Source code line: 32 using local variable b
	POP AX
	;Source code line: 32 loading integer constant 2
	PUSH [BP+-4]
	;Source code line: 32 assigning value to variable b
; 	PUSH 2 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 2
	;Source code line: 32 storing value in local variable b
	;Source code line: 32 cleaning expression result from stack
	MOV [BP+-4], AX
	;Source code line: 33 using local variable a
	POP AX
	;Source code line: 33 using local variable a
	PUSH [BP+-2]
	;Source code line: 33 using local variable b
	PUSH [BP+-2]
	;Source code line: 33 calling function g
	PUSH [BP+-4]
	CALL g
	;Source code line: 33 assigning value to variable a
; 	PUSH AX ; PUSH/POP pair eliminated
; 	POP AX
	;Source code line: 33 storing value in local variable a
	;Source code line: 33 cleaning expression result from stack
	MOV [BP+-2], AX
	;Source code line: 34 printing local variable a
	POP AX
	PUSH [BP+-2]
	;Source code line: 35 using local variable i
	CALL print_output
	;Source code line: 35 loading integer constant 0
	PUSH [BP+-6]
	;Source code line: 35 assigning value to variable i
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 0
	;Source code line: 35 storing value in local variable i
	;Source code line: 35 initializing FOR loop
	MOV [BP+-6], AX
	;Source code line: 35 using local variable i
L_16:
	;Source code line: 35 loading integer constant 4
	PUSH [BP+-6]
	;Source code line: 35 evaluating relational operation <
; 	PUSH 4 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 4
	POP AX
	CMP AX, BX
	;Source code line: 35 if relational operation result is false
	JL L_19
	PUSH 0
	JMP L_20
	;Source code line: 35 if relational operation result is true
L_19:
	PUSH 1
	;Source code line: 35 checking loop condition
L_20:
	POP AX
	CMP AX, 0
	JE L_17
	JMP L_18
	;Source code line: 36 using local variable a
L_18:
	;Source code line: 36 loading integer constant 3
	PUSH [BP+-2]
	;Source code line: 36 assigning value to variable a
; 	PUSH 3 ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, 3
	;Source code line: 36 storing value in local variable a
	;Source code line: 36 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	;Source code line: 37 using local variable a
L_21:
	;Source code line: 37 loading integer constant 0
	PUSH [BP+-2]
	;Source code line: 37 evaluating relational operation >
; 	PUSH 0 ; PUSH/POP converted to MOV
; 	POP BX
	MOV BX, 0
	POP AX
	CMP AX, BX
	;Source code line: 37 if relational operation result is false
	JG L_24
	PUSH 0
	JMP L_25
	;Source code line: 37 if relational operation result is true
L_24:
	PUSH 1
	;Source code line: 37 checking WHILE condition
L_25:
	POP AX
	CMP AX, 0
	JE L_22
	JMP L_23
	;Source code line: 38 using local variable b
L_23:
	;Source code line: 38 getting value in AX and saving original value for post-incrementing variable b
; 	PUSH [BP+-4] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+-4]
	;Source code line: 38 actually incrementing value of variable b
	PUSH AX
	;Source code line: 38 storing incremented value in local variable b
	INC AX
	;Source code line: 38 cleaning expression result from stack
	MOV [BP+-4], AX
	;Source code line: 39 using local variable a
	POP AX
; 	PUSH [BP+-2] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+-2]
	PUSH AX
	DEC AX
	;Source code line: 39 cleaning expression result from stack
	MOV [BP+-2], AX
	POP AX
	JMP L_21
	;Source code line: 41 incrementing variable i
L_22:
; 	PUSH [BP+-6] ; PUSH/POP converted to MOV
; 	POP AX
	MOV AX, [BP+-6]
	PUSH AX
	INC AX
	MOV [BP+-6], AX
	;Source code line: 41 jumping back to loop condition check
	POP AX
	JMP L_16
	;Source code line: 42 printing local variable a
L_17:
	PUSH [BP+-2]
	;Source code line: 43 printing local variable b
	CALL print_output
	PUSH [BP+-4]
	;Source code line: 44 printing local variable i
	CALL print_output
	PUSH [BP+-6]
	;Source code line: 45 loading integer constant 0
	CALL print_output
	;Source code line: 45 returning from main function
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
