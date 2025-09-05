%option noyywrap
%x KEY_START
%x KEY_END
%x COLON_GOT
%x VALUE_START
%x VALUE_END




%{
#include <bits/stdc++.h>
#include <iostream>
#include <string>

using namespace std;

stack<char> bracketStack;
int mismatch = 0;               // Flag for unmatched brackets
int lineCount=1;

bool errorReported = false;

void push(char c) {
    bracketStack.push(c);
}

int pop(char c) {
    if (bracketStack.empty()) {
        return 0;  // Stack empty, no match
    }
    char expected;
    switch (c) {
        case '}': expected = '{'; break;
        default: return 0;
    }
    if (bracketStack.top() == expected) {
        bracketStack.pop();
        return 1;  // Match found
    }
    return 0;  // Mismatch
}


%}

WHITESPACE [ \t\f\r\v]+
LETTER [a-zA-Z_]
ALPHA_NUMERIC [a-zA-Z_0-9]
DIGIT [0-9]
NEWLINE \n

%%

{NEWLINE} {}
{WHITESPACE} {}

"{"                     { push('{'); }

\" {
    BEGIN KEY_START;
}

<KEY_START>[\"] {
    BEGIN KEY_END;
}

<KEY_START>. {

}

<KEY_END>[:] {
    BEGIN COLON_GOT;
}

<KEY_END>. {
    cout<< "Error: missing seperator" <<endl;
    exit(1);
}


<COLON_GOT>[\"] {
    BEGIN VALUE_START;
}

<COLON_GOT>. {
    cout << "Error: must be a quoted string" <<endl;
    exit(1);
}

"}"                     { if (!pop('}')) mismatch = 1; }

<VALUE_START>[\"] {
    BEGIN VALUE_END;
}

<VALUE_START>. {

}




.                       { /* Ignore other characters */ }
<<EOF>>                 {
                            FILE *log = fopen("log.txt", "w");
                            if (mismatch || !bracketStack.empty()) {
                                cout<<"Error : unmatched { "<<endl;
                            } else {
                                cout<<"Valid JSON "<<endl;
                            }
                            fclose(log);
                            return 0;
                        }

%%

int main(int argc,char *argv[]){
	
	if(argc!=2){
		printf("Please provide input file name and try again\n");
		return 0;
	}
	
	FILE *fin=fopen(argv[1],"r");
	if(fin==NULL){
		printf("Cannot open specified file\n");
		return 0;
	}
	
	//logout= fopen("log.txt","w");
	//tokenout= fopen("token.txt","w");

	yyin= fin;
	yylex();
	fclose(yyin);
	//fclose(tokenout);
	//fclose(logout);
	return 0;
}




