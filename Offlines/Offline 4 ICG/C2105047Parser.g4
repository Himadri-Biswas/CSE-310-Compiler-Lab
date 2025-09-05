parser grammar C2105047Parser;

options {
    tokenVocab = C2105047Lexer;
}

@parser::header {
    #include <iostream>
    #include <fstream>
    #include <string>
    #include <cstdlib>
    #include <sstream>
    #include <vector>
    #include "2105047_SymbolTable.h"
    #include "C2105047Lexer.h"

    extern ofstream errorFile;
    extern int syntaxErrorCount;
    extern SymbolTable symbolTable;
    extern int lineCount;

    extern ofstream codeFile;
    extern int labelCount;
    extern int stackOffset;
    extern string currentFunctionName;
    extern string currentFunctionReturnType;
    extern bool hasReturnStatement;

    extern int asm_LineCount;
    extern int asm_CS_endLine;
    extern int asm_DS_endLine;
    extern bool codeSecWritten;

    extern int currentParameterCount;
    extern int currentLocalOffset;
    extern vector<string> currentParameterNames;
    extern bool inControlStructure;

    using namespace std;
}

@parser::members {
    
    void writeIntoErrorFile(const string message) 
    {
        if (!errorFile) 
        {
            cout << "Error opening errorLog.txt" << endl;
            return;
        }
        errorFile << message << endl;
        errorFile.flush();
    }

    void writeCode(const string& code) 
    {
        if (syntaxErrorCount == 0) 
        {
            writeCS(code);
        }
    }

    // writes 1 line into code.asm at the specified line number
    void writeMiddle(const string& str, int lineNum) 
    {
        // Read current file
        ifstream inFile("code.asm");
        vector<string> lines;
        string line;
        
        while(getline(inFile, line)) 
        {
            lines.push_back(line);
        }
        inFile.close();
        
        // Insert new line at position
        if(lineNum < lines.size()) 
        {
            lines.insert(lines.begin() + lineNum, str);
        } 
        else 
        {
            lines.push_back(str);
        }
        
        // Write back to file
        ofstream outFile("code.asm");
        for(const string& l : lines) 
        {
            outFile << l << endl;
        }
        outFile.close();
        
        asm_LineCount++; //as we are inserting 1 new line
    }
    
    // writes 1 line into code.asm at the current end of the code section
    void writeCS(const string& str) 
    {
        if(!codeSecWritten) 
        {
            writeMiddle(".Code", asm_DS_endLine);
            asm_CS_endLine = asm_DS_endLine + 1;
            asm_LineCount++;
            codeSecWritten = true;
        }
        writeMiddle(str, asm_CS_endLine);
        asm_CS_endLine++; // total lineCount already increases in writeMiddle, so we just increment the end line tarcker of code section
    }
    
    // writes 1 line into code.asm at the current end of the data section
    void writeDS(const string& str) 
    {
        writeMiddle(str, asm_DS_endLine);
        asm_DS_endLine++; // total lineCount alrady increases in writeMiddle, so we just increment the end line tracker of data section
    }

    string getTypeName(antlr4::ParserRuleContext* ctx) 
    {
        if (ctx->getText() == "int") return "INT";
        if (ctx->getText() == "float") return "FLOAT";
        if (ctx->getText() == "void") return "VOID";
        return "";
    }

    bool isTypeCompatible(const string& leftType, const string& rightType) 
    {
        if (leftType == "INT" && rightType == "CONST_INT") return true;
        if (leftType == "FLOAT" && rightType == "CONST_FLOAT") return true;
        if (leftType == "FLOAT" && (rightType == "INT" || rightType == "CONST_INT")) return true;
        return leftType == rightType;
    }

    bool isTypeCompatibleForFunction(const string& expectedType, const string& actualType) 
    {
        if (expectedType == actualType) return true;
        if (expectedType == "INT" && actualType == "CONST_INT") return true;
        if (expectedType == "FLOAT" && actualType == "CONST_FLOAT") return true;
        if (expectedType == "FLOAT" && (actualType == "INT" || actualType == "CONST_INT")) return true;
        return false;
    }

    // Resets local variable offsets for a new scope
    void resetLocalVariables() 
    {
        currentLocalOffset = -2;
    }

    string generateLabel() 
    {
        return "L_" + to_string(labelCount++);
    }

    void generateGlobalVariable(const string& varName, const string& varType, int arraySize = 0) 
    {
        if (syntaxErrorCount == 0) 
        {
            string varDecl;
            if (arraySize > 0) 
            {
                varDecl = "\t" + varName + " DW " + to_string(arraySize) + " DUP (0000H)"; // ASM: Declare global array with specified size in data section
            } 
            else 
            {
                varDecl = "\t" + varName + " DW 1 DUP (0000H)"; // ASM: Declare single global variable (2 bytes) in data section
            }
            writeDS(varDecl);  // ASM: Write variable declaration to .DATA section , as we must wrute global variable declaration in data section
        }
    }

    void generateFunctionProlog(const string& funcName) 
    {
        if (syntaxErrorCount == 0) 
        {
            writeCS(funcName + " PROC"); // ASM: Start function definition with PROC
            if (funcName == "main") 
            {
                writeCS("\tMOV AX, @DATA"); // ASM: Load data segment address for main function initialization
                writeCS("\tMOV DS, AX"); // ASM: Set data sgment register to point to programs data
                writeCS("\tPUSH BP");  // ASM: Save caller's base pointer on stack ( Save old BP of caller )
                writeCS("\tMOV BP, SP"); // ASM: Set up new stack frame base pointer ( Set new BP to current SP pointing to callee's stack frame )
            } 
            else 
            {
                writeCS("\tPUSH BP"); // ASM: Save caller's base pointer for non-main functions ( Save old BP of caller )
                writeCS("\tMOV BP, SP"); // ASM: Establish new function's stack frame base ( Set new BP to current SP pointing to callee's stack frame )
            }
        }
    }

    void generateFunctionEpilog(const string& funcName, int paramCount = 0) 
    {
        if (syntaxErrorCount == 0) 
        {
            if (funcName == "main") 
            {
                writeCode("\tMOV AX,4CH"); // ASM: Load DOS terminate program function number
                writeCode("\tINT 21H"); // ASM: Call DOS interruppt to terminate program
            } 
            else 
            {
                writeCode("\tMOV SP, BP"); // ASM: Restore stack to caller's frame level i.e. restore stack pointer to point to the caller's frame again
                writeCode("POP BP"); // ASM: Restore caller's base pointer from stack i.e. restore old BP of caller
                if (paramCount > 0) 
                {
                    writeCode("\tRET " + to_string(paramCount * 2)); // ASM: Return to caller and clean parameter bytes from stack
                } 
                else 
                {
                    writeCode("\t\tRET "); // ASM: Return to caller without parameter cleanup
                }
            }
            writeCode(funcName + " ENDP"); // ASM: End function definition with ENDP , this is a must to end all kind of functons
        }
    }
}

start
    : program
    {
        lineCount = $program.stop->getLine();
        if (syntaxErrorCount == 0) 
        {
            symbolTable.printAllScopeTable(errorFile);
        }
    }
    ;

program
    : p=program u=unit
    {
        lineCount = $u.stop->getLine();
    }
    | unit
    {
        lineCount = $unit.stop->getLine();
    }
    ;
	
unit
    : var_declaration
    {
        // Global variable declaration
    }
    | func_declaration
    {
        // Function declaration - no code generation needed
    }
    | func_definition
    {
        // Function definition - code already generated in the rule
    }
    ;
     
func_declaration
    : type_specifier ID 
    {
        int hasError = 0;
        SymbolInfo* symbol = new SymbolInfo($ID.text, "FUNCTION"); 
        symbol->setIsFunction(true);
        symbol->setReturnType(getTypeName($type_specifier.ctx));
        symbol->setIsDefined(false);
        if (!symbolTable.insert(*symbol, errorFile)) 
        { 
            hasError = 1;
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
        }
    }
    LPAREN p=parameter_list RPAREN SEMICOLON
    {
        // function declaration with return type and parameter list
        // Function declaration complete
    }
    | type_specifier ID LPAREN RPAREN SEMICOLON
    {
        // Function declaration with return type but without parameters
        int hasError = 0;
        SymbolInfo* symbol = new SymbolInfo($ID.text, "FUNCTION");
        symbol->setIsFunction(true);
        symbol->setReturnType(getTypeName($type_specifier.ctx));
        symbol->setIsDefined(false);
        if (!symbolTable.insert(*symbol, errorFile)) 
        {
            hasError = 1;
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
        }
    }
    ;
		 
func_definition
    : type_specifier ID LPAREN 
    {
        currentFunctionReturnType = getTypeName($type_specifier.ctx);
        currentFunctionName = $ID.text;
        hasReturnStatement = false;

        currentParameterCount = 0;  // Reset parameter count
        
        // Check if function already exists
        SymbolInfo* existingSymbol = symbolTable.lookUp($ID.text, errorFile);
        int hasError = 0;
        
        if (existingSymbol && existingSymbol->getIsFunction()) 
        {
            // Function already declared, see if it has been defined
            if (existingSymbol->getIsDefined()) 
            {
                // now thats an error
                writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
                hasError = 1;
                syntaxErrorCount++;
            } 
            else 
            {
                // function is already declared but not defined, check return type
                if (existingSymbol->getReturnType() != getTypeName($type_specifier.ctx)) 
                {
                    writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Return type mismatch of " + $ID.text);
                    hasError = 1;
                    syntaxErrorCount++;
                }
                existingSymbol->setIsDefined(true);
            }
        } 
        else 
        {
            // on the spot definition of a new function
            SymbolInfo* symbol = new SymbolInfo($ID.text, "FUNCTION");
            symbol->setIsFunction(true);
            symbol->setReturnType(getTypeName($type_specifier.ctx));
            symbol->setIsDefined(true);
            if (!symbolTable.insert(*symbol, errorFile)) 
            {
                writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
                hasError = 1;
                syntaxErrorCount++;
            }
        }
        
        generateFunctionProlog($ID.text);
        
        symbolTable.enterScope(7, errorFile);
    }
    p=parameter_list RPAREN compound_statement
    {
        // function definition with return type, parameter list and body
        lineCount = $compound_statement.stop->getLine();
        
        // Only generate function epilog if no return statement was found
        if (!hasReturnStatement) 
        {
            generateFunctionEpilog($ID.text, currentParameterCount);
        } 
        else 
        {
            // Just generate the ENDP for functions with return statements, because return statement's rule has already generated RET related codes
            if (syntaxErrorCount == 0) 
            {
                writeCode($ID.text + " ENDP"); // ASM: End function definition when return statement handled epilog
            }
        }
        
        currentFunctionReturnType = "";
        currentFunctionName = "";

        currentParameterCount = 0;  // Reset after function
        hasReturnStatement = false; // Reset for next function
    }
    | type_specifier ID LPAREN 
    {
        currentFunctionReturnType = getTypeName($type_specifier.ctx);
        currentFunctionName = $ID.text;
        hasReturnStatement = false;
        currentParameterCount = 0;  // Reset parameter count
        
        SymbolInfo* existingSymbol = symbolTable.lookUp($ID.text, errorFile);
        int hasError = 0;
        
        if (existingSymbol && existingSymbol->getIsFunction()) 
        {
            if (existingSymbol->getIsDefined()) 
            {
                writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
                hasError = 1;
                syntaxErrorCount++;
            } 
            else 
            {
                if (existingSymbol->getReturnType() != getTypeName($type_specifier.ctx)) 
                {
                    writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Return type mismatch of " + $ID.text);
                    hasError = 1;
                    syntaxErrorCount++;
                }
                existingSymbol->setIsDefined(true);
            }
        } 
        else 
        {
            SymbolInfo* symbol = new SymbolInfo($ID.text, "FUNCTION");
            symbol->setIsFunction(true);
            symbol->setReturnType(getTypeName($type_specifier.ctx));
            symbol->setIsDefined(true);
            if (!symbolTable.insert(*symbol, errorFile)) 
            {
                writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
                hasError = 1;
                syntaxErrorCount++;
            }
        }
        
        generateFunctionProlog($ID.text);
        symbolTable.enterScope(7, errorFile);
    }
    RPAREN compound_statement
    {
        // function definition with return type but without parameters
        lineCount = $compound_statement.stop->getLine();
        
        // Only generate function epilog if no return statement was found
        if (!hasReturnStatement) 
        {
            generateFunctionEpilog($ID.text, currentParameterCount);
        } 
        else 
        {
            // Just generate the ENDP for functions with return statements, because return statement's rule has already generated RET related codes
            if (syntaxErrorCount == 0) {
                writeCode($ID.text + " ENDP"); // ASM: End function definition when return statement handled epilog
            }
        }
        
        currentFunctionReturnType = "";
        currentFunctionName = "";
        currentParameterCount = 0;  // Reset after function
        hasReturnStatement = false; // Reset for next function
    }
    ;

parameter_list
    : p=parameter_list COMMA type_specifier ID
    {
        int hasError = 0;
        lineCount = $ID->getLine();

        string functionName = currentFunctionName;
        
        if (!functionName.empty()) 
        {
            SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, errorFile);
            if (functionSymbol && functionSymbol->getIsFunction()) 
            {
                functionSymbol->addParameter(getTypeName($type_specifier.ctx));
            }
        }

        currentParameterNames.push_back($ID->getText());
        
        SymbolInfo* symbol = new SymbolInfo($ID->getText(), getTypeName($type_specifier.ctx));
        symbol->setIsParameter(true);
        symbol->setStackOffset(9999); // Temporary, will be fixed in compound_statement

        
        if (!symbolTable.insert(*symbol, errorFile)) 
        {
            hasError = 1;
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID->getText() + " in parameter");
        }
        // else
        // {
        //     // DEBUG: Check if offset is still correct after insertion
        //     SymbolInfo* inserted = symbolTable.lookUp($ID->getText(), errorFile);
        //     cout << "DEBUG: After insertion, parameter " << $ID->getText() << " has offset " << inserted->getStackOffset() << endl;
        // }
        
        currentParameterCount++;  // Increment after setting offset
    }
    | p=parameter_list COMMA type_specifier
    {
        string functionName = currentFunctionName;
        
        if (!functionName.empty()) 
        {
            SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, errorFile);
            if (functionSymbol && functionSymbol->getIsFunction()) 
            {
                functionSymbol->addParameter(getTypeName($type_specifier.ctx));
            }
        }
        
        currentParameterCount++;  // Increment for unnamed parameter too, this can happen only in function declaration
    }
    | type_specifier ID
    {
        int hasError = 0;
        lineCount = $ID->getLine();

        string functionName = currentFunctionName;
        
        if (!functionName.empty()) 
        {
            SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, errorFile);
            if (functionSymbol && functionSymbol->getIsFunction()) 
            {
                functionSymbol->addParameter(getTypeName($type_specifier.ctx));
            }
        }

        currentParameterNames.push_back($ID->getText());
        
        SymbolInfo* symbol = new SymbolInfo($ID->getText(), getTypeName($type_specifier.ctx));
        symbol->setIsParameter(true);
        symbol->setStackOffset(9999); // Temporary
        
        if (!symbolTable.insert(*symbol, errorFile)) 
        {
            hasError = 1;
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID->getText() + " in parameter");
        }
        
        currentParameterCount++;  // Increment after setting offset
    }
    | type_specifier
    {   
        string functionName = currentFunctionName;
        
        if (!functionName.empty()) 
        {
            SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, errorFile);
            if (functionSymbol && functionSymbol->getIsFunction()) 
            {
                functionSymbol->addParameter(getTypeName($type_specifier.ctx));
            }
        }
        
        currentParameterCount++;  // Increment for unnamed parameter too
    }
    ;

compound_statement
: LCURL 
{
    if (!dynamic_cast<Func_definitionContext*>(_ctx->parent)) 
    {
        // enter a new scope only the LCURL is not part of a function definition, because we have already entered a scope in the function definition rule after getting an LCURL
        symbolTable.enterScope(7, errorFile);
        resetLocalVariables(); // Reset local variable offsets for this scope
    } 
    else 
    {
        // Parameter offsets in reverse order using stored names
        for (int i = 0; i < currentParameterNames.size(); i++) 
        {
            SymbolInfo* param = symbolTable.lookUp(currentParameterNames[i], errorFile);
            if (param && param->getIsParameter()) 
            {
                // First parameter gets highest offset
                int offset = 4 + ((currentParameterNames.size() - 1 - i) * 2);
                param->setStackOffset(offset);
                //cout << "DEBUG: Fixed parameter " << param->getName() << " offset to " << offset << endl;
            }
        }
        // Clear parameter names for next function
        currentParameterNames.clear();
    }
}
statements RCURL
{
    lineCount = $RCURL->getLine();
    symbolTable.exitScope(errorFile, 1);
}
| LCURL RCURL
{
    lineCount = $RCURL->getLine();
    if (!dynamic_cast<Func_definitionContext*>(_ctx->parent)) 
    {
        // Enter a new scope only if this is not part of a function definition
        symbolTable.enterScope(7, errorFile);
        resetLocalVariables();
    } 
    else 
    {
        // Same parameter offset logic for empty function body
        for (int i = 0; i < currentParameterNames.size(); i++) 
        {
            SymbolInfo* param = symbolTable.lookUp(currentParameterNames[i], errorFile);
            if (param && param->getIsParameter()) 
            {
                int offset = 4 + ((currentParameterNames.size() - 1 - i) * 2);
                param->setStackOffset(offset);
            }
        }
        currentParameterNames.clear(); // Clear parameter names for next function
    }
    symbolTable.exitScope(errorFile, 1);
}
;

var_declaration
    : t=type_specifier d=declaration_list sm=SEMICOLON
    {
        lineCount = $sm->getLine();
        
        if (getTypeName($t.ctx) == "VOID") 
        {
            writeIntoErrorFile("Error at line " + to_string($sm->getLine()) + ": Variable type cannot be void");
            syntaxErrorCount++;
        }
    }
    ;

type_specifier
    : INT
    {
        lineCount = $INT->getLine();
    }
    | FLOAT
    {
        lineCount = $FLOAT->getLine();
    }
    | VOID
    {
        lineCount = $VOID->getLine();
    }
    ;

declaration_list
    : d=declaration_list COMMA declaration_item
    {
        // Declaration list processing
    }
    | declaration_item
    {       
        // Single declaration
    }
    ;

declaration_item
    returns [string ruleName]
    : ID
    {
        int hasError = 0;
        lineCount = $ID->getLine();
        
        string varType = getTypeName(dynamic_cast<Var_declarationContext*>(_ctx->parent->parent)->t);
        SymbolInfo* symbol = new SymbolInfo($ID.text, varType);

        //cout << "DEBUG: Declaring variable " << $ID.text << " in scope " << symbolTable.getCurrentScope()->getNestedId() << endl;
        
        if (!symbolTable.insert(*symbol, errorFile)) 
        {
            hasError = 1;
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
        } 
        else 
        {
            // Generate variable declaration
            if (symbolTable.getCurrentScope()->getNestedId() == "1") 
            {
                // Global scope
                generateGlobalVariable($ID.text, varType);
                symbol->setIsGlobal(true);
            } 
            else 
            {
                // Local variable
                symbol->setIsGlobal(false);
                symbol->setStackOffset(currentLocalOffset); // Set stack offset for local variable
                currentLocalOffset -= 2;  // Next local variable at BP-4, BP-6, etc.
                
                if (syntaxErrorCount == 0) 
                {
                    writeCode("\t;Source code line: " + to_string(lineCount) + " allocating 2 bytes for local variable " + $ID.text); // ASMC: Comment for local variable allocation
                    writeCode("\tPUSH BX"); // ASM: Allocate 2 bytes on stack for local variable (BX value irrelevant)
                }
            }
        }
        
        $ruleName = "ID";
    }
    | ID LTHIRD CONST_INT RTHIRD
    {
        int hasError = 0;
        lineCount = $RTHIRD->getLine();
        
        string varType = getTypeName(dynamic_cast<Var_declarationContext*>(_ctx->parent->parent)->t);   // dec_item <- dec_list <- var_declaration
        SymbolInfo* symbol = new SymbolInfo($ID.text, varType);
        symbol->setIsArray(true);
        symbol->setArraySize(stoi($CONST_INT.text));
        
        if (!symbolTable.insert(*symbol, errorFile)) 
        {
            hasError = 1;
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($CONST_INT->getLine()) + ": Multiple declaration of " + $ID.text);
        } 
        else 
        {
            // Generate array declaration
            if (symbolTable.getCurrentScope()->getNestedId() == "1") 
            {
                // Global array - generate global variable
                generateGlobalVariable($ID.text, varType, stoi($CONST_INT.text));
            } 
            else 
            {
                // Local array - reserve stack space
                if (syntaxErrorCount == 0) 
                {
                    int arraySize = stoi($CONST_INT.text);
                    writeCode("\t;Source code line: " + to_string(lineCount) + " allocating " + to_string(arraySize * 2) + " bytes for array " + $ID.text); // ASMC: Comment for array allocation
                    writeCode("\tSUB SP, " + to_string(arraySize * 2)); // Reserve space for array // ASM: Allocate stack space for local array (arraySize * 2 bytes)
                }
            }
        }
        
        $ruleName = "ID LTHIRD CONST_INT RTHIRD";
    }
    ;

statements
    : s=statements statement
    {
        // Multiple statements
    }
    | statement
    {
        // Single statement
    }
    ;

statement
: var_declaration
{
    // Variable declaration statement
}
| expression_statement
{
    // Expression statement
}
| compound_statement
{
    // Compound statement
}
| FOR LPAREN 
{
    inControlStructure = true;
} 
es1=expression_statement 
{
    lineCount = $es1.stop->getLine();
    inControlStructure = false;
    
    // Generate initialization code (es1 already handled)
    string loopStart = generateLabel();
    string loopEnd = generateLabel(); 
    string loopContinue = generateLabel();

    if (syntaxErrorCount == 0) 
    {
        writeCode("\t;Source code line: " + to_string(lineCount) + " initializing FOR loop"); // ASMC: Comment for loop initialization
        writeCode(loopStart + ":"); // ASM: FOR Loop start label for condition checking entry point
    }
} 
{
    inControlStructure = true;
} 
es2=expression_statement 
{
    lineCount = $es2.stop->getLine();
    inControlStructure = false;
    
    // Condition check - value should be on stack from es2
    if (syntaxErrorCount == 0) 
    {
        writeCode("\t;Source code line: " + to_string(lineCount) + " checking loop condition"); // ASMC: Comment for loop condition check
        writeCode("\tPOP AX"); // ASM: Get condition result from stack (1=true, 0=false)
        writeCode("\tCMP AX, 0"); // ASM: Compare condition with 0 to test if false
        writeCode("\tJE " + loopEnd); // ASM: Jump to loop end if condition is false (AX=0)
        writeCode("\tJMP " + loopContinue); // ASM: Jump to loop body if condition is true
        writeCode(loopContinue + ":"); // ASM: Loop body entry label
    }
} 
{
    // Set flag to prevent increment double code generation in 'variable INCOP' rule
    inControlStructure = true;
} 
expression 
{
    // Increment expression ta store kore rakhi but ekhoni code generate korbo na er jonno
    string incrementExpr = $expression.text;
    inControlStructure = false;
} 
RPAREN statement
{
    lineCount = $statement.stop->getLine();
    
    // NOW generate the increment code AFTER the statement (body) has done generating its code
    if (syntaxErrorCount == 0) 
    {
        // Generate the increment code that was suppressed NOW
        if (incrementExpr.find("++") != string::npos) 
        {
            string varName = incrementExpr.substr(0, incrementExpr.find("++"));
            SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
            if (symbol && !symbol->getIsGlobal()) 
            {
                writeCode("\t;Source code line: " + to_string(lineCount) + " incrementing variable " + varName); // ASMC: Comment for increment operation
                writeCode("\tPUSH [BP+" + to_string(symbol->getStackOffset()) + "]"); // ASM: Load current variable value for increment
                writeCode("\tPOP AX"); // ASM: Get current value into register
                writeCode("\tPUSH AX"); // ASM: Save original value (for post-increment concept)
                writeCode("\tINC AX"); // ASM: Increment the value
                writeCode("\tMOV [BP+" + to_string(symbol->getStackOffset()) + "], AX"); // ASM: Store incremented value back to variable
                writeCode("\tPOP AX");  // ASM: Remove original value from stack (cleanup)
            }
        }
        
        writeCode("\t;Source code line: " + to_string(lineCount) + " jumping back to loop condition check"); // ASMC: Comment for loop continuation
        writeCode("\tJMP " + loopStart); // ASM: Jump back to condition check to continue loop
        writeCode(loopEnd + ":"); // ASM: Loop exit label when condition becomes false
    }
}
| IF LPAREN expression RPAREN 
{
    lineCount = $expression.stop->getLine();
    // IF condition check
    string ifTrue = generateLabel();
    string ifEnd = generateLabel();
    
    if (syntaxErrorCount == 0) 
    {
        writeCode("\t;Source code line: " + to_string(lineCount) + " checking IF condition"); // ASMC: Comment for IF condition check
        writeCode("\tPOP AX"); // ASM: Get condition result from stack
        writeCode("\tCMP AX, 0"); // ASM: Compare condition with 0 (false)
        writeCode("\tJE " + ifEnd); // ASM: Jump to end if condition is false
        writeCode("\tJMP " + ifTrue); // ASM: Jump to IF body if condition is true
        writeCode(ifTrue + ":"); // ASM: IF body entry label
    }
} 
statement 
{
    if (syntaxErrorCount == 0) 
    {
        writeCode(ifEnd + ":"); // ASM: IF statement end label
    }
}
| IF LPAREN expression RPAREN 
{
    lineCount = $expression.stop->getLine();
    // IF-ELSE condition check
    string ifTrue = generateLabel();
    string elsePart = generateLabel(); 
    string ifEnd = generateLabel();
    
    if (syntaxErrorCount == 0) 
    {
        writeCode("\t;Source code line: " + to_string(lineCount) + " checking IF condition"); // ASMC: Comment for IF condition check
        writeCode("\tPOP AX"); // ASM: Get condition result from stack
        writeCode("\tCMP AX, 0"); // ASM: Compare condition with 0 (false)
        writeCode("\tJE " + elsePart); // ASM: Jump to ELSE part if condition is false
        writeCode("\tJMP " + ifTrue); // ASM: Jump to IF part if condition is true
        writeCode(ifTrue + ":"); // ASM: IF body entry label
    }
} 
s1=statement ELSE 
{
    // Jump to end after IF part
    if (syntaxErrorCount == 0) 
    {
        writeCode("\tJMP " + ifEnd); // ASM: Jump over ELSE part after IF execution
        writeCode(elsePart + ":"); // ASM: ELSE body entry label
    }
} 
s2=statement
{
    lineCount = $s2.stop->getLine();
    
    // IF-ELSE end
    if (syntaxErrorCount == 0) 
    {
        writeCode(ifEnd + ":"); // ASM: IF-ELSE statement end label
    }
}
| WHILE LPAREN {
    string loopStart = generateLabel();
    string loopEnd = generateLabel();
    string loopBody = generateLabel();
    
    if (syntaxErrorCount == 0) 
    {
        writeCode(loopStart + ":"); // ASM: WHILE loop start label for condition checking //label(begin)
    }
} 
expression 
{
    lineCount = $expression.stop->getLine();
    // WHILE condition check
    if (syntaxErrorCount == 0) 
    {
        writeCode("\t;Source code line: " + to_string(lineCount) + " checking WHILE condition"); // ASMC: Comment for WHILE condition check
        writeCode("\tPOP AX"); // ASM: Get condition result from stack
        writeCode("\tCMP AX, 0"); // ASM: Compare condition with 0 (false)
        writeCode("\tJE " + loopEnd);  // ASM: Exit loop if condition is false // B.false=S.next
        writeCode("\tJMP " + loopBody); // ASM: Enter loop body if condition is true
        writeCode(loopBody + ":");  // ASM: WHILE loop body entry label  //label(B.true)
    }
} 
RPAREN statement
{
    lineCount = $statement.stop->getLine();
    
    // WHILE loop end
    if (syntaxErrorCount == 0) 
    {
        writeCode("\tJMP " + loopStart); // ASM: Jump back to condition check // gen 'goto' begin
        writeCode(loopEnd + ":"); // ASM: WHILE loop exit label
    }
}
| PRINTLN LPAREN ID RPAREN SEMICOLON
{
    lineCount = $SEMICOLON->getLine();
    
    SymbolInfo* symbol = symbolTable.lookUp($ID.text, errorFile);
    if (!symbol) 
    {
        //writeIntoErrorFile("EKHANE DHORA GESE PRINTLN ID");
        writeIntoErrorFile("Error at line " + to_string($SEMICOLON->getLine()) + ": Undeclared variable " + $ID.text);
        syntaxErrorCount++;
    } 
    else 
    {
        if (syntaxErrorCount == 0) 
        {
            if (symbol->getIsGlobal()) 
            {
                writeCode("\t;Source code line: " + to_string(lineCount) + " printing global variable " + $ID.text); // ASMC: Comment for print operation
                writeCode("\tPUSH " + $ID.text); // ASM: Push global variable value as parameter for print
            } 
            else 
            {
                int offset = symbol->getStackOffset();
                writeCode("\t;Source code line: " + to_string(lineCount) + " printing local variable " + $ID.text); // ASMC: Comment for print operation
                writeCode("\tPUSH [BP+" + to_string(offset) + "]"); // Push value for printing // ASM: Push local variable value as parameter for print
            }
            writeCode("\tCALL print_output"); // ASM: Call print procedure to display the value
        }
    }
}
| RETURN expression SEMICOLON
{
    lineCount = $SEMICOLON->getLine();
    hasReturnStatement = true;
    
    if (currentFunctionReturnType == "VOID") 
    {
        writeIntoErrorFile("Error at line " + to_string($SEMICOLON->getLine()) + ": Cannot return value from function " + currentFunctionName + " with void return type");      
        syntaxErrorCount++;
    }
    
    // Generate return code
    if (syntaxErrorCount == 0) 
    {
        // Generate function epilog immediately for non-main functions
        if (currentFunctionName != "main") 
        {
            writeCode("\t;Source code line: " + to_string(lineCount) + " returning from function " + currentFunctionName); // ASMC: Comment for return operation
            writeCode("\tPOP AX"); // ASM: Get return value from stack into AX register
            writeCode("\tMOV SP, BP"); // ASM: Restore stack pointer to caller's frame
            writeCode("\tPOP BP"); // ASM: Restore caller's base pointer
            if (currentParameterCount > 0) 
            {
                writeCode("\tRET " + to_string(currentParameterCount * 2)); // ASM: Return to caller and clean parameter bytes from stack
            } 
            else 
            {
                writeCode("\tRET"); // ASM: Return to caller without parameter cleanup
            }
        }
        else
        {
            writeCode("\t;Source code line: " + to_string(lineCount) + " returning from main function"); // ASMC: Comment for main return operation
            writeCode("\tMOV AX,4CH"); // ASM: Load DOS terminate program function for main
            writeCode("\tINT 21H"); // ASM: Terminate program via DOS interrupt
        }
    }
}
;

expression_statement
    : SEMICOLON
    {
        lineCount = $SEMICOLON->getLine();
    }
    | expression SEMICOLON
    {
        lineCount = $SEMICOLON->getLine();
        // Only pop if NOT in a control structure context
        if (syntaxErrorCount == 0 && !inControlStructure) 
        {
            writeCode("\t;Source code line: " + to_string(lineCount) + " cleaning expression result from stack"); // ASMC: Clean up unused expression value to prevent stack problems
            writeCode("\tPOP AX"); // ASM: Remove unused expression result from stack 
        }
    }
    ;

variable
    returns [string type, bool hasIndexError, string varName]
    : ID
    {
        lineCount = $ID->getLine();
        $varName = $ID.text;  // Set the variable name
        SymbolInfo* symbol = symbolTable.lookUp($ID.text, errorFile);
        if (!symbol) 
        {
            syntaxErrorCount++;
            //writeIntoErrorFile("EKHANE DHORA GESE ID");
            writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Undeclared variable " + $ID.text);
            $type = "UNKNOWN";
        } 
        else 
        {
            $type = symbol->getIsArray() ? "ARRAY" : symbol->getType();
            
            // Generate code based on variable scope
            if (syntaxErrorCount == 0) 
            {
                // Check if this variable is immediately followed by INCOP or DECOP in a control structure
                bool isIncrementInControl = false;
                if (inControlStructure) 
                {
                    // Look ahead to see if next token is INCOP or DECOP
                    antlr4::Token* nextToken = _input->LT(1);
                    if (nextToken && (nextToken->getType() == INCOP || nextToken->getType() == DECOP)) 
                    {
                        isIncrementInControl = true;
                    }
                }
                
                if (!isIncrementInControl) 
                {
                    if (symbol->getIsGlobal()) 
                    {
                        // Global variable - push value directly
                        writeCode("\t;Source code line: " + to_string(lineCount) + " using global variable " + $ID.text); // ASMC: Comment for global variable use
                        writeCode("\tPUSH " + $ID.text); // ASM: Push global variable value onto stack for expression use (i.e. PUSH x)
                    } 
                    else 
                    {
                        // Local variable/parameter - push value
                        int offset = symbol->getStackOffset();
                        cout << "DEBUG: Variable " << $ID.text << " has offset: " << symbol->getStackOffset() << " isParameter: " << symbol->getIsParameter() << endl;
                        writeCode("\t;Source code line: " + to_string(lineCount) + " using local variable " + $ID.text); // ASMC: Comment for local variable use
                        writeCode("\tPUSH [BP+"+ to_string(offset)+"]"); // ASM: Push local variable/parameter value onto stack
                    }
                }
            }
        }
        
        $hasIndexError = false;
    }
    | ID LTHIRD expression RTHIRD
    {
        lineCount = $RTHIRD->getLine();
        SymbolInfo* symbol = symbolTable.lookUp($ID.text, errorFile);
        if (!symbol) 
        {
            writeIntoErrorFile("Error at line " + to_string($RTHIRD->getLine()) + ": Undeclared variable " + $ID.text);
            syntaxErrorCount++;
            $type = "UNKNOWN";
            $hasIndexError = false;
        } 
        else if (!symbol->getIsArray()) 
        {
            writeIntoErrorFile("Error at line " + to_string($RTHIRD->getLine()) + ": " + $ID.text + " not an array");
            syntaxErrorCount++;
            $type = symbol->getType();
            $hasIndexError = true;
        } 
        else if ($expression.type != "CONST_INT") 
        {
            writeIntoErrorFile("Error at line " + to_string($RTHIRD->getLine()) + ": Expression inside third brackets not an integer");
            syntaxErrorCount++;
            $type = symbol->getType();
            $hasIndexError = true;
        } 
        else 
        {
            $type = symbol->getType();
            $hasIndexError = false;
            
            if (syntaxErrorCount == 0) 
            {
                writeCode("\t;Source code line: " + to_string(lineCount) + " accessing array element " + $ID.text); // ASMC: Comment for array access
                writeCode("\tPOP BX"); // ASM: Get array INDEX from stack, this INDEX was pushed from expression rule
                writeCode("\tSHL BX, 1"); // ASM: Convert index to BYTE offset (multiply by 2)
                
                int offset = symbol->getStackOffset();
                cout << "DEBUG: Array " << $ID.text << " has offset: " << symbol->getStackOffset() << endl;
                
                if (offset == -1) 
                {   // Global array
                    writeCode("\t;Source code line: " + to_string(lineCount) + " loading global array element " + $ID.text); // ASMC: Comment for global array access
                    writeCode("\tMOV AX, " + $ID.text + "[BX]"); // ASM: Load global array element using calculated offset(i.e. MOV AX, w[BX]), karon eta shurute DUP DW diyei kora 
                } 
                else 
                {   // Local array
                    writeCode("\t;Source code line: " + to_string(lineCount) + " loading local array element " + $ID.text); // ASMC: Comment for local array access
                    writeCode("\tNEG BX"); // ASM: Make offset negative for local array addressing
                    writeCode("\tADD BX, " + to_string(offset)); // ASM: Add array base offset to element's offset
                    writeCode("\tPUSH BP"); // ASM: Save base pointer
                    writeCode("\tADD BP, BX"); // ASM: Calculate element address
                    writeCode("\tMOV BX, BP"); // ASM: Copy calculated address
                    writeCode("\tMOV AX, [BP]"); // ASM: Load local array element value
                    writeCode("\tPOP BP"); // ASM: Restore base pointer
                }
                
                writeCode("\t;Pushing array element value and address for potential assignment"); // ASMC: Comment for pushing array element and ADDRESS
                writeCode("\tPUSH AX"); // ASM: Push array element value onto stack
                writeCode("\tPUSH BX"); // ASM: Push calculated ADDRESS of that element for potential assignment
            }
        }
    }
    ;

expression
    returns [string type]
    : logic_expression
    {
        $type = $logic_expression.type;
        lineCount = $logic_expression.start->getLine();
    }
    | variable ASSIGNOP logic_expression
    {
        lineCount = $ASSIGNOP->getLine();
        
        if ($variable.type == "ARRAY") 
        {
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($ASSIGNOP->getLine()) + ": Type mismatch, " + $variable.text + " is an array");
        } 
        else if ($variable.type != "UNKNOWN" && $logic_expression.type != "UNKNOWN" && !$variable.hasIndexError) 
        {
            if (!isTypeCompatible($variable.type, $logic_expression.type)) 
            {
                syntaxErrorCount++;
                writeIntoErrorFile("Error at line " + to_string($ASSIGNOP->getLine()) + ": Type Mismatch");
            }
        }
        
        $type = $variable.type;
        
        // Generate assignment code
        if (syntaxErrorCount == 0) 
        {
            writeCode("\t;Source code line: " + to_string(lineCount) + " assigning value to variable " + $variable.text); // ASMC: Comment for assignment
            writeCode("\tPOP AX"); // ASM: Get assignment value (right hand side value) from stack
            
            string varName;
            if ($variable.text.find('[') != string::npos) 
            {
                varName = $variable.text.substr(0, $variable.text.find('['));
            } 
            else 
            {
                varName = $variable.text;
            }
            
            SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
            if (!symbol) 
            {
                writeIntoErrorFile("Error at line " + to_string($ASSIGNOP->getLine()) + ": Undeclared variable " + varName);
                syntaxErrorCount++;
            } 
            else 
            {
                int arraySize = symbol->getIsArray() ? symbol->getArraySize() : 0;
                
                if (arraySize > 0) 
                {
                    writeCode("\t;Source code line: " + to_string(lineCount) + " getting array element address for assignment to " + varName); // ASMC: Comment for array index retrieval
                    writeCode("\tPOP BX"); // ASM: Get array element address from stack for assignment, eta 'variable' unit theke calculate kore push kor ache
                }
                
                // Global
                if (symbol->getStackOffset() == -1) 
                {
                    if (arraySize > 0) 
                    {
                        writeCode("\t;Source code line: " + to_string(lineCount) + " storing value in global array element " + varName + "[BX]"); // ASMC: Comment for global array assignment
                        writeCode("\tMOV " + varName + "[BX], AX"); // ASM: Store value in global array element
                    } 
                    else 
                    {
                        writeCode("\t;Source code line: " + to_string(lineCount) + " storing value in global variable " + varName); // ASMC: Comment for global variable assignment
                        writeCode("\tMOV " + varName + ", AX"); // ASM: Store value in global variable
                    }
                }
                // Local  
                else 
                {
                    if (arraySize > 0) 
                    {
                        writeCode("\t;Source code line: " + to_string(lineCount) + " storing value in local array element " + varName + "[BX]"); // ASMC: Comment for local array assignment
                        writeCode("\tPUSH BP"); // ASM: Save base pointer for local array assignment
                        writeCode("\tMOV BP, BX"); // ASM: Use calculated address as base
                        writeCode("\tMOV [BP], AX"); // ASM: Store value in local array element
                        writeCode("\tPOP BP"); // ASM: Restore base pointer
                    } 
                    else 
                    {
                        writeCode("\t;Source code line: " + to_string(lineCount) + " storing value in local variable " + varName); // ASMC: Comment for local variable assignment
                        writeCode("\tMOV [BP+" + to_string(symbol->getStackOffset()) + "], AX"); // ASM: Store value in local variable
                    }
                }
            }
        }
    }
    ;

logic_expression
    returns [string type, bool hasArithmeticError]
    : rel_expression
    {
        $type = $rel_expression.type;
        $hasArithmeticError = $rel_expression.hasArithmeticError;
        lineCount = $rel_expression.start->getLine();
    }
    | r1=rel_expression LOGICOP r2=rel_expression
    {
        lineCount = $LOGICOP->getLine();
        $type = "INT";
        $hasArithmeticError = $r1.hasArithmeticError || $r2.hasArithmeticError;
        
        // Generate only the labels we actually need
        string logicOp = $LOGICOP.text;
        
        if (logicOp == "&&") 
        {
            string leftFalse = generateLabel();
            string endLabel = generateLabel();
            
            if (syntaxErrorCount == 0) 
            {
                writeCode("\t;Source code line: " + to_string(lineCount) + " evaluating logical AND operation"); // ASMC: Comment for logical AND operation
                writeCode("\tPOP BX"); // Right operand // ASM: Get right operand from stack
                writeCode("\tPOP AX"); // Left operand // ASM: Get left operand from stack
                writeCode("\tCMP AX, 0"); // ASM: Check if left operand is false (short-circuit)
                writeCode("\tJE " + leftFalse); // ASM: Jump to false result if left is false
                writeCode("\tCMP BX, 0"); // ASM: Check if right operand is false
                writeCode("\tJE " + leftFalse); // ASM: Jump to false result if right is false
                writeCode("\tPUSH 1"); // Both true // ASM: Push true result (both operands true)
                writeCode("\tJMP " + endLabel); // ASM: Jump to end of logical operation
                writeCode(leftFalse + ":"); // ASM: False result label
                writeCode("\tPUSH 0"); // At least one false // ASM: Push false result (at least one operand false)
                writeCode(endLabel + ":"); // ASM: End of logical AND operation
            }
        } 
        else if (logicOp == "||") 
        {
            string leftTrue = generateLabel();
            string endLabel = generateLabel();
            
            if (syntaxErrorCount == 0) 
            {
                writeCode("\t;Source code line: " + to_string(lineCount) + " evaluating logical OR operation"); // ASMC: Comment for logical OR operation
                writeCode("\tPOP BX"); // Right operand // ASM: Get right operand from stack
                writeCode("\tPOP AX"); // Left operand // ASM: Get left operand from stack
                writeCode("\tCMP AX, 0"); // ASM: Check if left operand is true (short-circuit)
                writeCode("\tJNE " + leftTrue); // ASM: Jump to true result if left is true
                writeCode("\tCMP BX, 0"); // ASM: Check if right operand is true
                writeCode("\tJNE " + leftTrue); // ASM: Jump to true result if right is true
                writeCode("\tPUSH 0"); // Both false // ASM: Push false result (both operands false)
                writeCode("\tJMP " + endLabel); // ASM: Jump to end of logical operation
                writeCode(leftTrue + ":"); // ASM: True result label
                writeCode("\tPUSH 1"); // At least one true // ASM: Push true result (at least one operand true)
                writeCode(endLabel + ":"); // ASM: End of logical OR operation
            }
        }
    }
    ;
			
rel_expression
    returns [string type, bool hasArithmeticError]
    : simple_expression
    {
        $type = $simple_expression.type;
        $hasArithmeticError = $simple_expression.hasArithmeticError;
        lineCount = $simple_expression.start->getLine();
    }
    | s1=simple_expression RELOP s2=simple_expression
    {
        lineCount = $RELOP->getLine();
        $type = "INT";
        $hasArithmeticError = $s1.hasArithmeticError || $s2.hasArithmeticError;
        
        string trueLabel = generateLabel();
        string endLabel = generateLabel();
        
        if (syntaxErrorCount == 0) 
        {
            string relOp = $RELOP.text;
            
            writeCode("\t;Source code line: " + to_string(lineCount) + " evaluating relational operation " + relOp); // ASMC: Comment for relational operation
            writeCode("\tPOP BX"); // ASM: Get right operand from stack
            writeCode("\tPOP AX"); // ASM: Get left operand from stack
            writeCode("\tCMP AX, BX"); // ASM: Compare left operand with right operand
            
            if (relOp == "<") {
                writeCode("\tJL " + trueLabel); // ASM: Jump to true if left < right
            } else if (relOp == "<=") {
                writeCode("\tJLE " + trueLabel); // ASM: Jump to true if left <= right
            } else if (relOp == ">") {
                writeCode("\tJG " + trueLabel); // ASM: Jump to true if left > right
            } else if (relOp == ">=") {
                writeCode("\tJGE " + trueLabel); // ASM: Jump to true if left >= right
            } else if (relOp == "==") {
                writeCode("\tJE " + trueLabel); // ASM: Jump to true if left == right
            } else if (relOp == "!=") {
                writeCode("\tJNE " + trueLabel); // ASM: Jump to true if left != right
            }

            writeCode("\t;Source code line: " + to_string(lineCount) + " if relational operation result is false"); // ASMC: Comment for false result
            writeCode("\tPUSH 0"); // False // ASM: Push false result (comparison failed)
            writeCode("\tJMP " + endLabel); // ASM: Jump to end of comparison
            writeCode(trueLabel + ":"); // ASM: True result label
            writeCode("\t;Source code line: " + to_string(lineCount) + " if relational operation result is true"); // ASMC: Comment for true result
            writeCode("\tPUSH 1"); // True // ASM: Push true result (comparison succeeded)
            writeCode(endLabel + ":"); // ASM: End of relational operation
        }
    }
    ;
				
simple_expression
    returns [string type, bool hasArithmeticError]
    : term
    {
        $type = $term.type;
        $hasArithmeticError = $term.hasArithmeticError;
        lineCount = $term.start->getLine();
    }
    | s=simple_expression ADDOP t=term
    {
        lineCount = $ADDOP->getLine();
        $type = ($s.type == "FLOAT" || $t.type == "FLOAT") ? "FLOAT" : "INT";
        $hasArithmeticError = $s.hasArithmeticError || $t.hasArithmeticError;
        
        // Generate addition/subtraction
        if (syntaxErrorCount == 0) 
        {
            string addOp = $ADDOP.text;
            
            writeCode("\t;Source code line: " + to_string(lineCount) + " evaluating arithmetic operation " + addOp); // ASMC: Comment for arithmetic operation
            writeCode("\tPOP BX"); // ASM: Get right operand from stack
            writeCode("\tPOP AX"); // ASM: Get left operand from stack
            
            if (addOp == "+") 
            {
                writeCode("\tADD AX, BX"); // ASM: Add right operand to left operand
            } 
            else if (addOp == "-") 
            {
                writeCode("\tSUB AX, BX"); // ASM: Subtract right operand from left operand
            }
            
            writeCode("\tPUSH AX"); // ASM: Push arithmetic result onto stack
        }
    }
    ;
					
term
    returns [string type, bool hasArithmeticError]
    : unary_expression
    {
        $type = $unary_expression.type;
        $hasArithmeticError = false;
        lineCount = $unary_expression.start->getLine();
    }
    | t=term MULOP u=unary_expression
    {
        lineCount = $MULOP->getLine();
        
        string mulOp = $MULOP.text;
        
        if (mulOp == "%" && $u.text == "0") 
        {
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($MULOP->getLine()) + ": Modulus by Zero");
            $hasArithmeticError = true;
        } 
        //else if (mulOp == "%" && ($t.type != "CONST_INT" || $u.type != "CONST_INT")) 
        else if (mulOp == "%" && (!isTypeCompatible("INT", $t.type) || !isTypeCompatible("INT", $u.type)))
        {
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($MULOP->getLine()) + ": Non-Integer operand on modulus operator");
            $hasArithmeticError = true;
        } 
        else 
        {
            $hasArithmeticError = $t.hasArithmeticError;
        }
        
        $type = ($t.type == "FLOAT" || $u.type == "FLOAT") ? "FLOAT" : "INT";
        
        // Generate multiplication/division/modulus
        if (syntaxErrorCount == 0) 
        {
            writeCode("\t;Source code line: " + to_string(lineCount) + " evaluating multiplication/division operation " + mulOp); // ASMC: Comment for multiplication/division operation
            writeCode("\tPOP BX"); // ASM: Get right operand from stack
            writeCode("\tPOP AX"); // ASM: Get left operand from stack
            
            if (mulOp == "*") {
                writeCode("\tIMUL BX"); // ASM: Multiply AX by BX (signed multiplication)
            } else if (mulOp == "/") {
                writeCode("\tXOR DX, DX"); // ASM: Clear DX register for division
                writeCode("\tIDIV BX"); // ASM: Divide AX by BX (quotient in AX, remainder in DX)
            } else if (mulOp == "%") {
                writeCode("\tXOR DX, DX"); // ASM: Clear DX register for division
                writeCode("\tIDIV BX"); // ASM: Divide AX by BX to get remainder
                writeCode("\tMOV AX,DX"); // ASM: Move remainder from DX to AX
            }
            
            writeCode("\t;Pushing multiplication/division result onto stack"); // ASMC: Comment for pushing multiplication/division result
            writeCode("\tPUSH AX"); // ASM: Push multiplication/division result onto stack
        }
    }
    ;

unary_expression
    returns [string type]
    : ADDOP u=unary_expression
    {
        lineCount = $ADDOP->getLine();
        $type = $u.type;
        
        // Generate unary plus/minus
        if (syntaxErrorCount == 0 && $ADDOP.text == "-") 
        {
            writeCode("\t;Source code line: " + to_string(lineCount) + " evaluating unary minus operation"); // ASMC: Comment for unary minus operation
            writeCode("\tPOP AX"); // ASM: Get operand from stack
            writeCode("\tNEG AX"); // ASM: Negate the value (two's complement)
            writeCode("\tPUSH AX"); // ASM: Push negated result onto stack
        }
        // Unary plus does nothing
    }
    | NOT u=unary_expression
    {
        lineCount = $NOT->getLine();
        $type = "INT";
        
        // Generate logical NOT
        if (syntaxErrorCount == 0) 
        {
            string trueLabel = generateLabel();
            string endLabel = generateLabel();
            
            writeCode("\t;Source code line: " + to_string(lineCount) + " evaluating logical NOT operation"); // ASMC: Comment for logical NOT operation
            writeCode("\tPOP AX"); // ASM: Get operand from stack
            writeCode("\tCMP AX, 0"); // ASM: Compare operand with 0 (false)
            writeCode("\tJE " + trueLabel); // ASM: Jump to true if operand was false (0)
            writeCode("\tPUSH 0"); // Was true, now false // ASM: Push false result (operand was true)
            writeCode("\tJMP " + endLabel); // ASM: Jump to end of NOT operation
            writeCode(trueLabel + ":"); // ASM: True result label
            writeCode("\tPUSH 1"); // Was false, now true // ASM: Push true result (operand was false)
            writeCode(endLabel + ":"); // ASM: End of logical NOT operation
        }
    }
    | factor
    {
        $type = $factor.type;
        lineCount = $factor.start->getLine();
    }
    ;
	
factor
    returns [string type]
    : variable
    {
        $type = $variable.type;
        lineCount = $variable.start->getLine();

        string varName;
        if ($variable.text.find('[') != string::npos) 
        {
            varName = $variable.text.substr(0, $variable.text.find('['));
        } 
        else 
        {
            varName = $variable.text;
        }

        SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
        if (!symbol)
        {
            writeIntoErrorFile("Error at line " + to_string($variable.start->getLine()) + ": Undeclared variable " + varName);
            syntaxErrorCount++;
            $type = "UNKNOWN";
        } 
        else
        {
            int arraySize = symbol->getIsArray() ? symbol->getArraySize() : 0;
                
            // if array size is greater than 0 and variable has brackets, then pop bx , bx will catch the address of the array element, which is unnecessary here
            if (arraySize > 0 && $variable.text.find('[') != string::npos) 
            {   
                writeCode("\t;Source code line: " + to_string(lineCount) + " cleanup address from stack for " + $variable.text); // ASMC: Clanup element address
                writeCode("\tPOP BX"); //ASMN: e.g. i=w[0], erokom array element er khetre prothome value then address ta push kori 'variable' rule e.  'factor' rule e ei address r lagbe na tai cleanup korsi
            } 
        }

    }
    | ID LPAREN a=argument_list RPAREN
    {
        lineCount = $RPAREN->getLine();
        SymbolInfo* symbol = symbolTable.lookUp($ID.text, errorFile);
        
        if (!symbol) 
        {
            writeIntoErrorFile("Error at line " + to_string($RPAREN->getLine()) + ": Undefined function " + $ID.text);
            syntaxErrorCount++;
            $type = "UNKNOWN";
        } 
        else if (!symbol->getIsFunction()) 
        {
            writeIntoErrorFile("Error at line " + to_string($RPAREN->getLine()) + ": " + $ID.text + " is not a function");
            syntaxErrorCount++;
            $type = "UNKNOWN";
        } 
        else 
        {
            $type = symbol->getReturnType();
            
            // Check function call validty and generate call
            if (syntaxErrorCount == 0) 
            {
                writeCode("\t;Source code line: " + to_string(lineCount) + " calling function " + $ID.text); // ASMC: Comment for function call
                writeCode("\tCALL " + $ID.text); // ASM: Call function (arguments already on stack)
                writeCode("\tPUSH AX"); // ASM: Push function return value from AX onto stack
            }
        }
    }
    | LPAREN expression RPAREN
    {
        $type = $expression.type;
        lineCount = $LPAREN->getLine();
        // Expression result already on stack
    }
    | CONST_INT
    {
        $type = "CONST_INT";
        lineCount = $CONST_INT->getLine();
        
        // Generate constant loading
        if (syntaxErrorCount == 0) 
        {
            writeCode("\t;Source code line: " + to_string(lineCount) + " loading integer constant " + $CONST_INT.text); // ASMC: Comment for constant loading
            writeCode("\tPUSH " + $CONST_INT.text); // ASM: Push integer constant value onto stack
        }
    }
    | CONST_FLOAT
    {
        $type = "CONST_FLOAT";
        lineCount = $CONST_FLOAT->getLine();
        
        // Generate float constant (no need for it in this offline)
        // if (syntaxErrorCount == 0) 
        // {
        //     writeCode("\tPUSH " + $CONST_FLOAT.text);
        // }
    }
    | variable INCOP
    {
        $type = $variable.type;
        lineCount = $INCOP->getLine();
        
        // Only generate code if NOT in control structure
        if (syntaxErrorCount == 0 && !inControlStructure) 
        {
            // Extract variable name for lookup
            string varName;
            if ($variable.text.find('[') != string::npos) 
            {
                varName = $variable.text.substr(0, $variable.text.find('['));
            } 
            else 
            {
                varName = $variable.text;
            }
            SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
            if (!symbol) 
            {
                writeIntoErrorFile("Error at line " + to_string($INCOP->getLine()) + ": Undeclared variable " + varName);
                syntaxErrorCount++;
            } 
            else
            {
                // Get array size to determine stack operations
                int arraySize = symbol->getIsArray() ? symbol->getArraySize() : 0;
                
                // if array size is greater than 0 and variable has brackets, then pop bx and pop ax, otherwise only pop ax
                if (arraySize > 0 && $variable.text.find('[') != string::npos) 
                {

                    writeCode("\tPOP BX"); //ASMN: Array element's address, lagbe ekhon, unlike just VARIABLE
                    writeCode("\tMOV AX, [BX]"); // ASMN: copy in AX the value of the array element to perfomr INC
                } 
                else 
                {
                    writeCode("\t;Source code line: " + to_string(lineCount) + " getting value in AX and saving original value for post-incrementing variable " + $variable.text); // ASMC: Comment for saving original value
                    writeCode("\tPOP AX"); // ASM: Get variable's current value from stack, push kora chilo 'variable' rule e, ekhon eta AX e anchi
                    writeCode("\tPUSH AX"); // ASM: Save original value in AX to perform INC, i.e. result=b++ erokom er jonno UN-incremented value tai save korchi, eta pop hobe 'expression' rule e, jekhane assignment hobe
                }
                writeCode("\t;Source code line: " + to_string(lineCount) + " actually incrementing value of variable " + $variable.text); // ASMC: Comment for variable increment
                writeCode("\tINC AX"); // ASM: Increment the value

                // Extract variable name for lookup
                string varName;
                if ($variable.text.find('[') != string::npos) 
                {
                    varName = $variable.text.substr(0, $variable.text.find('['));
                } 
                else 
                {
                    varName = $variable.text;
                }

                SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
                if (!symbol) 
                {
                    writeIntoErrorFile("Error at line " + to_string($INCOP->getLine()) + ": Undeclared variable " + varName);
                    syntaxErrorCount++;
                } 
                else if (symbol->getIsGlobal()) 
                {
                    if (symbol->getIsArray()) 
                    {
                        // for Global array element increment: eg. someGlobalArr[0]++
                        writeCode("\t;Source code line: " + to_string(lineCount) + " storing incremented value in global array element " + varName + "[BX]"); // ASMC: Comment for storing global array element increment
                        writeCode("\tMOV " + varName + "[BX], AX"); // ASM: Store incremented value in global array element
                    } 
                    else 
                    {
                        // Global variable
                        writeCode("\t;Source code line: " + to_string(lineCount) + " storing incremented value in global variable " + varName); // ASMC: Comment for storing global variable increment
                        writeCode("\tMOV " + varName + ", AX"); // ASM: Store incremented value in global variable
                    }
                } 
                else 
                {
                    if (symbol->getIsArray()) 
                    {
                        // Local array elemnt increment storing
                        writeCode("\t;Source code line: " + to_string(lineCount) + " storing incremented value in local array element " + varName + "[BX]"); // ASMC: Comment for storing local array element increment
                        writeCode("\tPOP BX"); // ASM: Get calculated array address from stack
                        writeCode("\tSHL BX, 1"); // ASM: Convert to BYTE offset
                        writeCode("\tNEG BX"); // ASM: Make negative for local addressing
                        writeCode("\tPUSH BP"); // ASM: Save base pointer
                        writeCode("\tADD BP, BX"); // ASM: Calculate element address
                        writeCode("\tMOV [BP], AX"); // ASM: Store incremented value in local array element
                        writeCode("\tPOP BP"); // ASM: Restore base pointer
                    } 
                    else 
                    {
                        // Local variable icrement storing
                        writeCode("\t;Source code line: " + to_string(lineCount) + " storing incremented value in local variable " + varName); // ASMC: Comment for storing local variable increment
                        writeCode("\tMOV [BP+" + to_string(symbol->getStackOffset()) + "], AX"); // ASM: Store incremented value in local variable
                    }
                }
            }
        }
    }
    | variable DECOP
    {
        $type = $variable.type;
        lineCount = $DECOP->getLine();
        
        // Only generate code if NOT in control structure
        if (syntaxErrorCount == 0 && !inControlStructure) 
        {
            // Extract variable name for lookup
            string varName;
            if ($variable.text.find('[') != string::npos) 
            {
                varName = $variable.text.substr(0, $variable.text.find('['));
            } 
            else 
            {
                varName = $variable.text;
            }
            SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
            if (!symbol) 
            {
                writeIntoErrorFile("Error at line " + to_string($DECOP->getLine()) + ": Undeclared variable " + varName);
                syntaxErrorCount++;
            } 
            else
            {
                // Get array size to determine stack operations
                int arraySize = symbol->getIsArray() ? symbol->getArraySize() : 0;
                
                // if array size is greater than 0 and variable has brackets, then pop bx and pop ax, otherwise only pop ax
                if (arraySize > 0 && $variable.text.find('[') != string::npos) 
                {
                    writeCode("\tPOP BX"); //ASMN: Array element address
                    writeCode("\tMOV AX, [BX]"); //ASMN: Copy in AX the value of the array element for post-decrement works
                } 
                else 
                {
                    writeCode("\tPOP AX"); /// ASM: Get variable's current value from stack
                    writeCode("\tPUSH AX"); // ASM: Save original value for post-decrement works
                }
                writeCode("\tDEC AX"); // ASM: Decrement the value

                // Extract variable name for lookup
                string varName;
                if ($variable.text.find('[') != string::npos) 
                {
                    varName = $variable.text.substr(0, $variable.text.find('['));
                } 
                else 
                {
                    varName = $variable.text;
                }

                SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
                if (!symbol) 
                {
                    writeIntoErrorFile("Error at line " + to_string($DECOP->getLine()) + ": Undeclared variable " + varName);
                    syntaxErrorCount++;
                } 
                else if (symbol->getIsGlobal()) 
                {
                    if (symbol->getIsArray()) 
                    {
                        // Global array decrement: eg. someGlobalArr[0]--
                        writeCode("\tMOV " + varName + "[BX], AX"); // ASM: Store decremented value in global array element
                    } 
                    else 
                    {
                        // Global variable
                        writeCode("\tMOV " + varName + ", AX"); // ASM: Store decremented value in global variable
                    }
                } 
                else 
                {
                    if (symbol->getIsArray()) 
                    {
                        // Local array decrement
                        writeCode("\tPOP BX"); // Array index // ASM: Get calculated array address from stack
                        writeCode("\tSHL BX, 1"); // ASM: Convert to word offset
                        writeCode("\tNEG BX"); // ASM: Make negative for local addressing
                        writeCode("\tPUSH BP"); // ASM: Save base pointer
                        writeCode("\tADD BP, BX"); // ASM: Calculate element address
                        writeCode("\tMOV [BP], AX"); // ASM: Store decremented value in local array element
                        writeCode("\tPOP BP"); // ASM: Restore base pointer
                    } 
                    else 
                    {
                        // Local variable
                        writeCode("\tMOV [BP+" + to_string(symbol->getStackOffset()) + "], AX"); // ASM: Store decremented value in local variable
                    }
                }
            }
        }
    }
    ;

argument_list
    returns [string argTypes]
    : arguments
    {
        $argTypes = $arguments.argTypes;
    }
    |
    {
        $argTypes = "";
    }
    ;

arguments
    returns [string argTypes]
    : a=arguments COMMA l=logic_expression
    {
        lineCount = $COMMA->getLine();
        $argTypes = $a.argTypes + "," + $l.type;
        
        // Arguments are already pushed by logic_expression
    }
    | logic_expression
    {
        lineCount = $logic_expression.start->getLine();
        $argTypes = $logic_expression.type;
        
        // Argument already pushed by logic_expression
    }
    ;