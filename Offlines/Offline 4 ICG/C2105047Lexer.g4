lexer grammar C2105047Lexer;

@lexer::header {
    #pragma once
    #include <iostream>
    #include <fstream>
    #include <string>
}

// ------------------------------
// 1) Comments (skipped)
// ------------------------------

// Single-line comments: '//' then anything except newline
LINE_COMMENT
    : '//' ~[\r\n]* -> skip
    ;

// Multi-line comments
BLOCK_COMMENT
  : '/*' ( . | '\r' | '\n' )*? '*/' -> skip
  ;

// ------------------------------
// 2) String literals (skipped)
// ------------------------------

// A basic string rule with escape support
STRING
    : '"' ( '\\' . | ~["\\\r\n] )* '"' -> skip
    ;

// ------------------------------
// 3) Whitespace & Newlines (skipped)
// ------------------------------

WS : [ \t\r\n]+ -> channel(HIDDEN);

// ------------------------------
// 4) Keywords & Symbols
// ------------------------------

IF       : 'if' ;
ELSE     : 'else' ;
FOR      : 'for' ;
WHILE    : 'while' ;
PRINTLN  : 'println' ;
RETURN   : 'return' ;
INT      : 'int' ;
FLOAT    : 'float' ;
VOID     : 'void' ;

LPAREN   : '(' ;
RPAREN   : ')' ;
LCURL    : '{' ;
RCURL    : '}' ;
LTHIRD   : '[' ;
RTHIRD   : ']' ;
SEMICOLON: ';' ;
COMMA    : ',' ;

ADDOP    : [+\-] ;
MULOP    : [*/%] ;
INCOP    : '++' ;
DECOP    : '--' ;
NOT      : '!' ;
RELOP    : '<=' | '==' | '>=' | '>' | '<' | '!=' ;
LOGICOP  : '&&' | '||' ;
ASSIGNOP : '=' ;

// ------------------------------
// 5) Identifiers & Numbers
// ------------------------------

ID         : [A-Za-z_] [A-Za-z0-9_]* ;
CONST_INT  : [0-9]+ ;
CONST_FLOAT
    : [0-9]+ ('.' [0-9]*)? ([Ee][+\-]? [0-9]+)?
    | '.' [0-9]+ ([Ee][+\-]? [0-9]+)?
    | [0-9]+ '.' ([Ee][+\-]? [0-9]+)?
    ;

UNRECOGNIZED_CHAR
    : . {
        {
            // Report error for unrecognized character
            std::cout << "Error at line " << getLine() << ": Unrecognized character " << getText() << std::endl;
        }
    } 
    ;