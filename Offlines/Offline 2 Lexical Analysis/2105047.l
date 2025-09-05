%option noyywrap
%x CHARACTER_STATE
%x STRING_STATE
%x SINGLE_COMMENT
%x MULTI_COMMENT

%{
#include<bits/stdc++.h>
#include<iostream>
#include<string>
#include "2105047_SymbolTable.cpp"

using namespace std;

int lineCount=1;
int errorCount=0;
int specialCharFlag=0;
int multiStringFlag=0;
int start=0;

ofstream logFile;
ofstream tokenFile;

int totalBuckets=7;
//int id=1;

vector<int> idStack = {1}; // Start with root ID 1

SymbolTable symbolTable(1,totalBuckets,logFile); //symbol table with id 1 and total buckets 7

string charInput; // for catching character constant and logging
string charInputForToken; // for tokenizing , may have PRINTABLE special characters
string charOutput; // for logging
string tokenStringInput; // for tokenizing string characters
string logStringInput; // for catching string characters and logging
string stringOutput; // for logging
string commentOut; // for logging , no need to tokenize

string convertToUpperCase(string str){
    for(int i=0; str[i] != '\0'; i++){
        if(str[i] >= 'a' && str[i] <= 'z'){
            str[i] = str[i] - 32;
        }
    }
    return str;
}

%}

WHITESPACE [ \t\f\r\v]+
LETTER [a-zA-Z_]
ALPHA_NUMERIC [a-zA-Z_0-9]
DIGIT [0-9]
NEWLINE \n

%%

{NEWLINE} {lineCount++;}
{WHITESPACE} { /* Ignore whitespace */ }

"if" | 
"else" |
"for" |
"while" |
"do" |
"break" |
"int" |
"char" |
"float" |
"double" |
"void" |
"return" |
"switch" |
"case" |
"default" |
"continue" |
"goto" |
"long" |
"short" |
"static" |
"unsigned" {
    tokenFile<<"<"<<convertToUpperCase(yytext)<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <"<<convertToUpperCase(yytext)<<"> Lexeme "<<yytext<<" found"<<endl<<endl;
}

{LETTER}{ALPHA_NUMERIC}* {
    tokenFile<<"<ID, "<<yytext<<">"<<" ";
    logFile<< "Line no "<<lineCount<<": Token <ID> Lexeme "<<yytext<<" found"<<endl<<endl;

    
    SymbolInfo* symbol = new SymbolInfo(yytext, "ID");


    if(symbolTable.insert(*symbol,logFile)==true)
    {
        symbolTable.printAllScopeTable(logFile);
    }
    // else
    // {
    //     logFile<<"\t"<<yytext<<" already exists in the current ScopeTable"<<endl<<endl;
    // }
}

{DIGIT}+ {
	tokenFile<<"<CONST_INT, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <CONST_INT> Lexeme "<<yytext<<" found"<<endl<<endl;  

    SymbolInfo* symbol = new SymbolInfo(yytext, "CONST_INT");


    if(symbolTable.insert(*symbol,logFile)==true)
    {
        symbolTable.printAllScopeTable(logFile);
    }
    // else
    // {
    //     logFile<<"\t"<<yytext<<" already exists in the current ScopeTable"<<endl<<endl;
    // }
}


{DIGIT}+(\.{DIGIT}+)?([Ee][+-]?{DIGIT}+)? {
    string lexeme = yytext;
    // for (int i = 0; i < lexeme.size(); i++) {
    //     if (lexeme[i] == 'E') lexeme[i] = 'e'; // Convert 'E' to 'e'
    // }
    // new sample io removed the 'E' to 'e' conversion....
    tokenFile << "<CONST_FLOAT, " << lexeme << ">" << " ";
    logFile << "Line no " << lineCount << ": Token <CONST_FLOAT> Lexeme " << lexeme << " found" << endl<<endl;

    SymbolInfo* symbol = new SymbolInfo(yytext, "CONST_FLOAT");


    if(symbolTable.insert(*symbol,logFile)==true)
    {
        symbolTable.printAllScopeTable(logFile);
    }
    // else
    // {
    //     logFile<<"\t"<<yytext<<" already exists in the current ScopeTable"<<endl<<endl;
    // }
}

{DIGIT}*(\.{DIGIT}*)(\.{DIGIT}*)+([Ee][+-]?{DIGIT}+)? {
	errorCount++;
    //Too many decimal point error for character sequence like 1.2.345.789
    logFile<< "Error at line no "<<lineCount<<": Too many decimal points "<<yytext<<endl<<endl;
}

{DIGIT}+(\.{DIGIT}*)?([Ee][+-]?{DIGIT}*(\.{DIGIT}*)?)* {
	errorCount++;
    //Ill formed number such as 1E10.7
    logFile<< "Error at line no "<<lineCount<<": Ill formed number "<<yytext<<endl<<endl; 
}

{DIGIT}+(\.{DIGIT}+)?([Ee][+-]?{DIGIT}+)?{LETTER}{ALPHA_NUMERIC}* {
	errorCount++; 
    //Invalid Suffix on numeric constant or invalid prefix on identifier for character sequence like 12abcd
	logFile<< "Error at line no "<<lineCount<<": Invalid prefix on ID or invalid suffix on Number "<<yytext<<endl<<endl;
}

"+" |
"-" {
    tokenFile<<"<ADDOP, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <ADDOP> Lexeme "<<yytext<<" found"<<endl<<endl;  	
}

"*" |
"/" |
"%" {
	tokenFile<<"<MULOP, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <MULOP> Lexeme "<<yytext<<" found"<<endl<<endl;  	
} 

"++" |
"--" {
	tokenFile<<"<INCOP, "<<yytext<<">"<<" ";
    logFile<< "Line no "<<lineCount<<": Token <INCOP> Lexeme "<<yytext<<" found"<<endl<<endl;  	
}

"<" |
"<=" |
">" |
">=" |
"==" |
"!=" {
	tokenFile<<"<RELOP, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <RELOP> Lexeme "<<yytext<<" found"<<endl<<endl;  	
}

"=" {
	tokenFile<<"<ASSIGNOP, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <ASSIGNOP> Lexeme "<<yytext<<" found"<<endl<<endl;  
}

"&&" |
"||" {
	tokenFile<<"<LOGICOP, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <LOGICOP> Lexeme "<<yytext<<" found"<<endl<<endl;  
}

"&" |
"|" |
"^" |
"<<" |
">>" {
	tokenFile<<"<BITOP, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <BITOP> Lexeme "<<yytext<<" found"<<endl<<endl;  
}

"!" {
	tokenFile<<"<NOT, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <NOT> Lexeme "<<yytext<<" found"<<endl<<endl;  
}

"(" {
	tokenFile<<"<LPAREN, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <LPAREN> Lexeme "<<yytext<<" found"<<endl<<endl;  
}

")" {
	tokenFile<<"<RPAREN, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <RPAREN> Lexeme "<<yytext<<" found"<<endl<<endl;  
}

"{" {
	tokenFile<<"<LCURL, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <LCURL> Lexeme "<<yytext<<" found"<<endl<<endl;  


    // Generate next child ID 
    int nextChild = 1;
    if (!idStack.empty()) {
        nextChild = 1; // Always start with 1 for new nesting level
    }
    else {
        nextChild = idStack.back() + 1; // Increment from the last ID
    }
    idStack.push_back(nextChild); // Push new child ID

    // Enter new scope , nestedId will be generated in the scopeTable constructor
    symbolTable.enterScope(nextChild, totalBuckets, logFile, 1);
}

"}" {
	tokenFile<<"<RCURL, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <RCURL> Lexeme "<<yytext<<" found"<<endl<<endl;

    symbolTable.exitScope(logFile);
    if (!idStack.empty()) {
        idStack.pop_back(); // Pop current scope ID
    }

	//symbolTable.exitScope(logFile);
}
"[" {
	tokenFile<<"<LTHIRD, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <LTHIRD> Lexeme "<<yytext<<" found"<<endl<<endl;  
}
"]" {
	tokenFile<<"<RTHIRD, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <RTHIRD> Lexeme "<<yytext<<" found"<<endl<<endl;  
}
"," {
	tokenFile<<"<COMMA, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <COMMA> Lexeme "<<yytext<<" found"<<endl<<endl;  
}
";" {
	tokenFile<<"<SEMICOLON, "<<yytext<<">"<<" ";
	logFile<< "Line no "<<lineCount<<": Token <SEMICOLON> Lexeme "<<yytext<<" found"<<endl<<endl;  
}

"'" {
    //cerr << "DEBUG : received a single quote, so beginning character state" << endl;
    charInput.clear();
    charInputForToken.clear();
	specialCharFlag = 0;
	start = lineCount; // this is the line where the character constant began
	BEGIN CHARACTER_STATE;
}


\" {
    //cerr << "DEBUG : received a double quote, so beginning string state" << endl;
    stringOutput = "\""; // add the opening quote to the string output(for logging)
    logStringInput.clear();
    tokenStringInput.clear();
    multiStringFlag = 0;
    start = lineCount; // this is the line where the string began
    //tokenStringInput = "\""; // Add opening quote to token string , removed as per new sample io
    BEGIN STRING_STATE;
}

\/\/ {
    //cerr << "DEBUG : received a single line comment, so beginning single line comment state" << endl;
    commentOut.clear();
	commentOut += "//"; //logging comment will have comment sign
	start = lineCount; // this is the line where single line comment began
	BEGIN SINGLE_COMMENT;
}

\/\* {
    //cerr << "DEBUG : received a multi line comment, so beginning multi line comment state" << endl;
	commentOut.clear();  
    commentOut += "/*"; //logging comment will have comment sign
	start = lineCount; // this is the line where multi line comment began
    BEGIN MULTI_COMMENT;
}

<CHARACTER_STATE>(["\\"][abfnrtv0\\\'\"]) {
    // in character state, if we encounter a backslash followed by a special character (CONTROL CHARACTER)
    //cerr << "DEBUG : processing escape sequence(CONTROL CHAR) in character state : " << yytext[0] << yytext[1] << endl;
    charInput += yytext[0]; // Add the backslash
    charInput += yytext[1]; // Add the character after the backslash
    specialCharFlag = 1;
    if (yytext[1] == 'a') charInputForToken += "\a";
    else if (yytext[1] == 'b') charInputForToken += "\b";
    else if (yytext[1] == 'f') charInputForToken += "\f";
    else if (yytext[1] == 'n') charInputForToken += "\n";
    else if (yytext[1] == 'r') charInputForToken += "\r";
    else if (yytext[1] == 't') charInputForToken += "\t";
    else if (yytext[1] == 'v') charInputForToken += "\v";
    else if (yytext[1] == '0') charInputForToken += "\0";
    else charInputForToken += yytext; // single backslash or single quote or double quote after a backslash, double quote as character was not in spec
    //any other character after a backslash will go to multi-character constant ERROR
}


<CHARACTER_STATE><<EOF>> {
    // if we reach EOF in character state, we need to log the error
    //cerr << "DEBUG : received EOF in character state, so ERROR" << endl;
    charOutput = "'"; // Add the opening quote to the output
    charOutput += charInput; 
        
    errorCount++;
    logFile << "Error at line no " << lineCount << ": Unterminated character " << charOutput << endl << endl;

    charInput.clear();
    charInputForToken.clear();
    charOutput.clear();
    specialCharFlag = 0;

    BEGIN INITIAL;
}

<CHARACTER_STATE>"'" {
    // got the closing single quote while in character state
    //cerr << "DEBUG : received a single quote in character state, so ending the character and tokenizing" << endl;
    charOutput = "'";
	charOutput += charInput;
	charOutput += "'";
    //prepared for logging

    if(charInput.size()==0){
        errorCount++;
        //Empty character constant error
        logFile<< "Error at line no "<<lineCount<<": Empty character constant error "<<charOutput<<endl<<endl;
	}
    else if(specialCharFlag==1){
        // For escape sequence, use the ascii (printable) value for tokenization
        //cerr << "DEBUG : the string in charInputForToken is " << charInputForToken << endl;
        //cerr << "DEBUG : the string in charInput is " << charInput << endl;
        tokenFile<<"<CONST_CHAR, "<<charInputForToken<<">"<<" ";

        //tokenFile<<"<CONST_CHAR, "<<charInput<<">"<<" ";
	    logFile<< "Line no "<<start<<": Token <CONST_CHAR> Lexeme "<<charOutput<<" found " << "--> <CONST_CHAR, "<<charInputForToken<<">" <<endl<<endl;

        SymbolInfo* symbol = new SymbolInfo(charOutput, "CONST_CHAR");


        if(symbolTable.insert(*symbol,logFile)==true)
        {
            symbolTable.printAllScopeTable(logFile);
        }
        // else
        // {
        //     logFile<<"\t"<<yytext<<" already exists in the current ScopeTable"<<endl<<endl;
        // }
	}
    else if(charInput.size()==1){
        tokenFile<<"<CONST_CHAR, "<<charInput<<">"<<" ";
	    logFile<< "Line no "<<start<<": Token <CONST_CHAR> Lexeme "<<charOutput<<" found " << "--> <CONST_CHAR, "<<charInput<<">"<<endl<<endl;

        SymbolInfo* symbol = new SymbolInfo(charOutput, "CONST_CHAR");


        if(symbolTable.insert(*symbol,logFile)==true)
        {
            symbolTable.printAllScopeTable(logFile);
        }
        // else
        // {
        //     logFile<<"\t"<<yytext<<" already exists in the current ScopeTable"<<endl<<endl;
        // }
	}
	else{
        errorCount++;
        //Multi character constant error for character sequence like 'abc'
		logFile<< "Error at line no "<<lineCount<<": Multi character constant error "<<charOutput<<endl<<endl;
	}

    charInput.clear();
    charInputForToken.clear();
	charOutput.clear();
	specialCharFlag = 0;
	BEGIN INITIAL;
}

<CHARACTER_STATE>{NEWLINE} {
    // got a bare newline in character state, error
    //cerr << "DEBUG : received a NEWLINE in character state, so ERROR" << endl;
    charOutput = "'";
    charOutput += charInput;

    errorCount++;
    logFile << "Error at line no " << lineCount << ": Unterminated character " << charOutput << endl << endl;

    lineCount++;

    charInput.clear();
    charInputForToken.clear();
    charOutput.clear();
    specialCharFlag = 0;
    BEGIN INITIAL;  
}

<CHARACTER_STATE>. {
    //cerr << "DEBUG : eating a character NORMALLY in character state : " << yytext[0] << endl;
    // take any other character in character state
	charInput += yytext[0];
}


<STRING_STATE>\\\" {
    //cerr << "DEBUG : received a backslash and double quote in string state(SPECIAL)" << endl;
    tokenStringInput += "\"";
    logStringInput += "\\\""; // Include backslash in log output
}

<STRING_STATE>\\a {       
    tokenStringInput += "\\a";
    logStringInput += "a";
}

<STRING_STATE>\\b {
    tokenStringInput += "\\b";
    logStringInput += "b";
}

<STRING_STATE>\\f {
    tokenStringInput += "\\f";
    logStringInput += "f";
}

<STRING_STATE>\\n {
    //cerr << "DEBUG : received a backslash and newline in string state(CONTROL CHAR)" << endl;
    tokenStringInput += "\\n";
    logStringInput += "n";
}

<STRING_STATE>\\r {
    //cerr << "DEBUG : received a backslash and carriage return in string state(CONTROL CHAR)" << endl;
    tokenStringInput += "\\r";
    logStringInput += "r";
}

<STRING_STATE>\\t {
    tokenStringInput += "\\t";
    logStringInput += "t";
}

<STRING_STATE>\\v {
    tokenStringInput += "\\v";
    logStringInput += "v";
}

<STRING_STATE>\\0 {
    tokenStringInput += "\\0";
    logStringInput += "0";
}

<STRING_STATE>[\r]?\n {
    //cerr<< "DEBUG : received a NEWLINE in string state without backslash , so ERROR"<<endl;

    //multiStringFlag was not needed later actually
    ////cerr<< "DEBUG : multiStringFlag is NOT set, so ending the string and throwing UNTERMINATED STRING ERROR"<<endl;
    ////cerr << "before logging error Linecount is " << lineCount << endl;
    stringOutput = "\"";
    stringOutput += logStringInput;

    errorCount++;
    //lineCount= start;
    //int tempLineCount = start;
    logFile << "Error at line no " << start << ": Unterminated string " << stringOutput << endl << endl;

    logStringInput.clear();
    tokenStringInput.clear();
    stringOutput.clear();
    BEGIN INITIAL;
    
    ////cerr<< "DEBUG : i began INITIAL state " <<endl;
    ////cerr<< "DEBUG : now lineCount is " << lineCount << endl;
    lineCount++;
    ////cerr<< "After incrementing lineCount, now lineCount is " << lineCount << endl;
}

<STRING_STATE><<EOF>> {
    //cerr<< "DEBUG : received EOF in string state, so ERROR"<<endl;
    stringOutput = "\"";
    stringOutput += logStringInput;
        
    errorCount++;
    logFile << "Error at line no " << lineCount << ": Unterminated String " << stringOutput << endl << endl;

    logStringInput.clear();
    tokenStringInput.clear();
    stringOutput.clear();
    multiStringFlag = 0;

    BEGIN INITIAL;
}

<STRING_STATE>["] {
    //cerr<< "DEBUG : received a double quote in string state, so ending the string and tokenizng"<<endl;
    stringOutput += logStringInput;
    stringOutput += "\"";

    // Add closing quote to token string
    //tokenStringInput += "\"";
    
    // Use the exact lexeme (with escape sequences) for the token
    tokenFile<<"<STRING, "<<tokenStringInput<<">"<<" ";
    //logFile<< "Line no "<<start<<": Token <STRING> Lexeme "<<stringOutput<<" found"<<endl<<endl;
    logFile<< "Line no "<<lineCount<<": Token <STRING> Lexeme "<<stringOutput<<" found " << "--> <STRING, "<<tokenStringInput<<">" <<endl<<endl;

    logStringInput.clear();
    tokenStringInput.clear();
    stringOutput.clear();
    multiStringFlag = 0;

    BEGIN INITIAL;
}

<STRING_STATE>\\[\r]?\n {
    //multiStringFlag was not needed later actually
    //cerr<< "DEBUG : received a backslash and newline in string state, so continuing the string"<<endl;
	multiStringFlag = 1;
	tokenStringInput += "";
	logStringInput += "\\\n";
	lineCount++;
}


<STRING_STATE>. {
    //cerr<< "DEBUG : eating a character NORMALLY in string state : "<<yytext[0]<<endl;
    tokenStringInput += yytext[0];
    logStringInput += yytext[0];
}

<SINGLE_COMMENT>\\[\r]?\n {
    //cerr<< "DEBUG : received a backslash and newline in single line comment state, so continuing the comment"<<endl;
    lineCount++;
	commentOut += "\\\n";
}

<SINGLE_COMMENT>[\r]?\n {  
    //cerr<< "DEBUG : received a NEWLINE in single line comment state, so ending the comment and logging, not tokenizing"<<endl;
    logFile<< "Line no "<<lineCount<<": Token <COMMENT> Lexeme "<<commentOut<<" found"<<endl<<endl; 
    lineCount++;
    BEGIN INITIAL;
}
<SINGLE_COMMENT><<EOF>> {
    // Single line comment could also end at EOF, not an error
	logFile<< "Line no "<<lineCount<<": Token <COMMENT> Lexeme "<<commentOut<<" found"<<endl<<endl; 
    BEGIN INITIAL;
}

<SINGLE_COMMENT>\\ {
    //cerr<< "DEBUG : received a backslash in single line comment state, not a problem"<<endl;
	commentOut += "\\";
}
<SINGLE_COMMENT>. {
	commentOut += yytext[0];
}

<MULTI_COMMENT>\*\/ {
    //cerr<< "DEBUG : received a closing comment in multi line comment state, so ending the comment and logging, not tokenizing"<<endl;
    commentOut += "*/";
    logFile<< "Line no "<<lineCount<<": Token <COMMENT> Lexeme "<<commentOut<<" found"<<endl<<endl; 
    BEGIN INITIAL;
}
<MULTI_COMMENT><<EOF>> {
    //cerr<< "DEBUG : received EOF in multi line comment state, so ERROR"<<endl;
	errorCount++;
	//lineCount--;
    //Unfinished comment
	logFile<< "Error at line no "<<start<<": Unterminated comment "<<commentOut<<endl<<endl;

	commentOut.clear();  
	BEGIN INITIAL;
}
<MULTI_COMMENT>\n {
    //cerr<< "DEBUG : received a NEWLINE in multi line comment state, so continuing the comment"<<endl;
	lineCount++;
	commentOut += "\n";
}
<MULTI_COMMENT>. {
    //cerr<< "DEBUG : eating a character NORMALLY in multi line comment state : "<<yytext[0]<<endl;
	commentOut += yytext[0];
}

. {
    //cerr<< "DEBUG : unrecognized character : "<<yytext[0]<<endl;
	errorCount++;
    // Unrecognized character
    logFile<< "Error at line no "<<lineCount<<": Unrecognized character "<<yytext<<endl<<endl;
}

%%

int main(int argc, char* argv[]) {
	if(argc!=2){
		printf("Please provide input file name and try again\n");
		return 0;
	}
	
	FILE *fin=fopen(argv[1],"r");

	if(fin == NULL){
		printf("Cannot open specified file\n");
		return 0;
	}
	
	logFile.open("2105047_log.txt");
	tokenFile.open("2105047_token.txt");

	yyin= fin;
	yylex();
	symbolTable.printAllScopeTable(logFile);

    logFile<<"Total lines: "<<lineCount<<endl;
	logFile<< "Total errors: "<<errorCount<<endl;
	fclose(yyin);
	tokenFile.close();
	logFile.close();
	return 0;
}