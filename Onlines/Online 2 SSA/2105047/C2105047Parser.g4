parser grammar C2105047Parser;

options {
    tokenVocab = C2105047Lexer;
}

@parser::header {
    #include <iostream>
    #include <fstream>
    #include <string>
    #include <cstdlib>
    #include <regex>
    #include "2105047_SymbolTable.h"
    #include "C2105047Lexer.h"

    extern ofstream parserLogFile;
    extern ofstream errorFile;
    extern int syntaxErrorCount;

    extern SymbolTable symbolTable;
    extern int lineCount;

    using namespace std;
}

@parser::members {
    
    string currentFunctionReturnType = ""; // Track current function return type to check void function returns
    string currentFunctionName = ""; // Track current function name for error reporting in void returns

    void writeIntoparserLogFile(const string message) 
    {
        if (!parserLogFile)
        {
            cout << "Error opening parserLogFile.txt" << endl;
            return;
        }
        parserLogFile << message << endl;
        parserLogFile.flush();
    }

    void writeIntoErrorFile(const string message) 
    {
        if (!errorFile) 
        {
            cout << "Error opening errorFile.txt" << endl;
            return;
        }
        errorFile << message << endl << endl;
        errorFile.flush();
    }

    // Helper to get type from type_specifier
    string getTypeName(antlr4::ParserRuleContext* ctx) 
    {
        if (ctx->getText() == "int") return "INT";
        if (ctx->getText() == "float") return "FLOAT";
        if (ctx->getText() == "void") return "VOID";
        return "";
    }

    // Helper to log rule and code
    void logRule(const string& ruleName, const string& code) 
    {
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": " + ruleName + "\n\n" + code + "\n");
    }

    // Helper to check type compatibility
    bool isTypeCompatible(const string& leftType, const string& rightType, bool isArray = false) 
    {
        if (isArray && rightType != "ARRAY") return false;
        if (leftType == "INT" && rightType == "FLOAT") return false;
        if (leftType == "FLOAT" && (rightType == "INT" || rightType == "CONST_INT" || rightType == "CONST_FLOAT")) return true;

        // Allow assignment of constants to variables
        if (leftType == "INT" && (rightType == "CONST_INT" || rightType == "INT")) return true;
        if (leftType == "FLOAT" && (rightType == "CONST_FLOAT" || rightType == "FLOAT")) return true;
        return leftType == rightType;
    }

    // Helper function for type compatibility in function arguments
    bool isTypeCompatibleForFunction(const string& expectedType, const string& actualType) 
    {
        // Exact match
        if (expectedType == actualType) return true;
        
        // Allow constants to match their base types
        if (expectedType == "INT" && actualType == "CONST_INT") return true;
        if (expectedType == "FLOAT" && actualType == "CONST_FLOAT") return true;
        
        // Allow int to float promotion  
        if (expectedType == "FLOAT" && (actualType == "INT" || actualType == "CONST_INT")) return true;
        
        // Arrays must match exactly
        if (expectedType == "ARRAY" && actualType != "ARRAY") return false;
        if (expectedType != "ARRAY" && actualType == "ARRAY") return false;
        
        return false;
    }

    // Helper to remove unnecessary spaces but keep essential ones
    string removeUnnecessarySpaces(const string& input) 
    {
        string result = input;
        
        // Remove spaces around operators but keep spaces between keywords and identifiers
        regex spaceAroundOps(R"(\s*([+\-*/%=<>!&|,;()])\s*)");
        result = regex_replace(result, spaceAroundOps, "$1"); // replace the spaces with the operator itself
        
        // Keep single spaces between words (like "int a" or "float d")
        regex multipleSpaces(R"(\s+)");
        result = regex_replace(result, multipleSpaces, " "); //replace multiple spaces with a single space
        
        // Trim leading and trailing spaces
        result.erase(0, result.find_first_not_of(' ')); // removes everything from the start to the first non-space character
        result.erase(result.find_last_not_of(' ') + 1); // removes everything from the last non-space character to the end
        
        return result;
    }

    string trim(const string& s) 
    {
        size_t start = s.find_first_not_of(" \t");
        size_t end = s.find_last_not_of(" \t");
        return (start == string::npos) ? "" : s.substr(start, end - start + 1); // if no non-space character is found, return empty string, otherwise return the substring from start to end
    }
    
    string formatFunctionBlock(const string &input)
    {
        stringstream ss(input);
        string line, output;
        bool firstLine = true;

        while (getline(ss, line))
        {
            line = trim(line);
            if (line.empty())
                continue;

            // Special handling for else statements
            bool isElseStatement = (line.find("else") == 0); // Check if line starts with "else"

            if (!firstLine && !isElseStatement)
            {
                output += "\n";
            }
            firstLine = false; //not a first line anymore

            if (line == "{" || line == "}")
            {
                output += line; // No extra newlines after braces
            }
            else if (isElseStatement)
            {
                // Remove the last newline if it exists to put else on same line as RCURL
                if (!output.empty() && output.back() == '\n')
                {
                    output.pop_back();
                }

                // Ensure space after else keyword
                string processedElse = removeUnnecessarySpaces(line);
                if (processedElse.find("else{") != string::npos)
                {
                    processedElse.replace(processedElse.find("else{"), 5, "else {"); // add space after else
                }
                output += processedElse;
            }
            else if (line.find("(") != string::npos && line.find(")") != string::npos && line.find("{") != string::npos)
            {
                // Function header WITH opening brace on same line
                output += removeUnnecessarySpaces(line);
            }
            else if (line.find("(") != string::npos && line.find(")") != string::npos && line.find("{") == string::npos)
            {
                // Function header WITHOUT opening brace
                output += removeUnnecessarySpaces(line);
            }
            else
            {
                // Regular statement 
                string processedLine = removeUnnecessarySpaces(line);

                // Check if line contains semicolons , if yes, split by semicolons
                if (processedLine.find(';') != string::npos)
                {
                    // after every semicolon, we need to add a newline, except for the first segment
                    bool firstSegment = true;
                    string currentSegment = "";

                    for (size_t i = 0; i < processedLine.length(); i++)
                    {
                        if (processedLine[i] == ';')
                        {
                            // Found a semicolon
                            if (!firstSegment)
                            {
                                output += "\n";
                            }

                            if (!currentSegment.empty())
                            {
                                // Non-empty segment before semicolon
                                output += trim(currentSegment) + ";";
                            }
                            else
                            {
                                // Empty segment = an alone semicolon
                                output += ";";
                            }

                            currentSegment = "";
                            firstSegment = false; // not the first segment anymore
                        }
                        else
                        {
                            currentSegment += processedLine[i]; // add other characters normally
                        }
                    }

                    // Handle any remaining content after the last semicolon
                    if (!currentSegment.empty())
                    {
                        if (!firstSegment)
                        {
                            output += "\n";
                        }
                        output += trim(currentSegment);
                    }
                }
                else
                {
                    // No semicolons, just add the processed line
                    output += processedLine;
                }
            }
        }

        return output;
    }

    string formatDeclaration(const string &input)
    {
        string result;
        bool lastWasSpace = false;
        bool skipNextSpace = false; // Flag to skip space after comma

        for (size_t i = 0; i < input.length(); i++)
        {
            char c = input[i];

            if (c == ' ' || c == '\t')
            {
                // Skip space if we just added a comma
                if (skipNextSpace)
                {
                    skipNextSpace = false;
                    continue;
                }

                // Only add one space if the last character wasn't already a space
                if (!lastWasSpace && !result.empty())
                {
                    result += ' ';
                    lastWasSpace = true;
                }
            }
            else if (c == ',')
            {
                // Remove any trailing space before comma, add comma without space after
                if (!result.empty() && result.back() == ' ')
                {
                    result.pop_back();
                }
                result += ',';
                lastWasSpace = false;
                skipNextSpace = true; // Set the flag that we eill have to skip the next space
            }
            else
            {
                // Regular character - just add it
                result += c;
                lastWasSpace = false;
                skipNextSpace = false; // Reset this flag on non-space character
            }
        }

        // Trim trailing spaces
        while (!result.empty() && result.back() == ' ')
        {
            result.pop_back();
        }

        return result;
    }

    string removeEmptyLines(const string& input) 
    {
        stringstream ss(input);
        string line, output;
        bool firstLine = true; // we wont add a newlne before the first line
        
        while (getline(ss, line)) {
            // Skip truly empty lines OR lines with only whitespace
            if (line.empty() || line.find_first_not_of(" \t\r") == string::npos) {
                continue;
            }
            
            // Add newline before each line (except the first)
            if (!firstLine) {
                output += "\n";
            }
            output += line;
            firstLine = false;
        }
        return output;
    }

}

start
    : program
    {
        lineCount = $program.stop->getLine(); // get the last line number
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": start : program");
        writeIntoparserLogFile("");
        symbolTable.printAllScopeTable(parserLogFile);
        writeIntoparserLogFile("Total number of lines: " + to_string(lineCount));
        writeIntoparserLogFile("Total number of errors: " + to_string(syntaxErrorCount));
    }
    ;

program
    : p=program u=unit
    {
        lineCount = $u.stop->getLine();
        string code = $p.text + "\n\n" + $u.text;
        string formattedCode = removeEmptyLines(formatFunctionBlock(code));
        logRule("program : program unit", formattedCode);
    }
    | unit
    {
        lineCount = $unit.stop->getLine();
        string formattedCode = removeEmptyLines(formatFunctionBlock($unit.text));
        logRule("program : unit", formattedCode);
    }
    ;
	
unit
    : var_declaration
    {
        logRule("unit : var_declaration", $var_declaration.text);
    }
    | func_declaration
    {
        logRule("unit : func_declaration", formatDeclaration($func_declaration.text));
    }
    | func_definition
    {
        logRule("unit : func_definition", removeEmptyLines(formatFunctionBlock($func_definition.text)));
    }
    ;
     
func_declaration
    : type_specifier ID 
    {
        // writeIntoparserLogFile("DEBUG: func_declaration matched for " + $ID.text);

        int hasError=0;
        SymbolInfo* symbol = new SymbolInfo($ID.text, "FUNCTION"); 
        symbol->setIsFunction(true);
        symbol->setReturnType(getTypeName($type_specifier.ctx)); // will need later to check if return type macthes in definition
        symbol->setIsDefined(false); // still not defined, just declared
        if (!symbolTable.insert(*symbol, parserLogFile)) // just insert the function name, need not enter scope for the parametrs here
        { 
            hasError = 1;
            syntaxErrorCount++;
        }
    }
    LPAREN p=parameter_list RPAREN SEMICOLON
    {
        // writeIntoparserLogFile("DEBUG: func_declaration matched for " + $ID.text);

        lineCount = $SEMICOLON->getLine(); // the semicolon is surely the ending of a declaration
        
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": func_declaration : type_specifier ID LPAREN parameter_list RPAREN SEMICOLON");
        writeIntoparserLogFile("");
        
        if(hasError) 
        {
            writeIntoErrorFile("Error at line " + to_string($SEMICOLON->getLine()) + ": Multiple declaration of " + $ID.text);
            writeIntoparserLogFile("Error at line " + to_string($SEMICOLON->getLine()) + ": Multiple declaration of " + $ID.text);
            writeIntoparserLogFile("");
        }
        
        writeIntoparserLogFile(formatDeclaration($type_specifier.text + " " + $ID.text + "(" + $p.text + ");"));
        writeIntoparserLogFile("");
    }
    | type_specifier ID LPAREN RPAREN SEMICOLON
    {
        int hasError=0;
        lineCount = $SEMICOLON->getLine();
        SymbolInfo* symbol = new SymbolInfo($ID.text, "FUNCTION");
        symbol->setIsFunction(true);
        symbol->setReturnType(getTypeName($type_specifier.ctx));
        symbol->setIsDefined(false);
        if (!symbolTable.insert(*symbol, parserLogFile)) 
        {
            hasError = 1;
            syntaxErrorCount++;
        }
        
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": func_declaration : type_specifier ID LPAREN RPAREN SEMICOLON");
        writeIntoparserLogFile("");
        
        if(hasError) 
        {
            writeIntoErrorFile("Error at line " + to_string($SEMICOLON->getLine()) + ": Multiple declaration of " + $ID.text);
            writeIntoparserLogFile("Error at line " + to_string($SEMICOLON->getLine()) + ": Multiple declaration of " + $ID.text);
            writeIntoparserLogFile("");
        }
        
        writeIntoparserLogFile(formatDeclaration($type_specifier.text + " " + $ID.text + "();"));
        writeIntoparserLogFile("");
    }
    ;
		 
func_definition
    : type_specifier ID LPAREN 
    {
        currentFunctionReturnType = getTypeName($type_specifier.ctx); //get the return type of the function for later use
        currentFunctionName = $ID.text; // store the function name for later use
        
        // Check if the function , at least declaration , already exists
        SymbolInfo* existingSymbol = symbolTable.lookUp($ID.text, parserLogFile);
        int multipleDeclarationInDeclarationError = 0;
        int returnTypeMismatchError = 0;
        int multipleDeclationInDefinitionError = 0;
        int numberOfParametersMismatchError = 0;
        int typeOfParametersMismatchError = 0;
        int declaredParamCount = -1; // -1 means no previous declaration
        vector<string> declaredParamTypes; // Store declared parameter types
        
        if (existingSymbol && existingSymbol->getIsFunction()) 
        {
            // Function was declared before - check if it's already defined
            if (existingSymbol->getIsDefined()) 
            {
                // Already defined - this is an error
                writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
                multipleDeclarationInDeclarationError = 1;
                syntaxErrorCount++;
            } 
            else 
            {
                // it was not defined before , it was just declared before
                //check if the return type matches with declaration
                if (existingSymbol->getReturnType() != getTypeName($type_specifier.ctx)) 
                {
                    writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Return type mismatch of " + $ID.text);
                    returnTypeMismatchError = 1;
                    syntaxErrorCount++;
                }

                // Store declaration parameter count and types before clearing
                declaredParamCount = existingSymbol->getParameterCount();
                declaredParamTypes = existingSymbol->getParameterTypes();
                //writeIntoErrorFile("DEBUG: Function " + $ID.text + " was declared before with " + to_string(declaredParamCount) + " parameters.");
                existingSymbol->clearParameters(); // Clear to rebuild from definition
                
                //mark it as defined now
                existingSymbol->setIsDefined(true);
            }
        } 
        else 
        {
            // New function - insert it in symbol table
            SymbolInfo* symbol = new SymbolInfo($ID.text, "FUNCTION");
            symbol->setIsFunction(true);
            symbol->setReturnType(getTypeName($type_specifier.ctx));
            symbol->setIsDefined(true);  // This is an on-the-spot definition
            if (!symbolTable.insert(*symbol, parserLogFile)) 
            {
                writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
                multipleDeclationInDefinitionError = 1;
                syntaxErrorCount++;
            }
        }
        
        // we have got LPAREN, Create scope for the parameters immediately because we have to remember them throughout the function definition
        // if we waited for RCURL, we ccould not save the parameters info
        symbolTable.enterScope(7, parserLogFile);
    }
    p=parameter_list RPAREN 
    {
        // Check parameter count and type mismatch after parameter_list is processed
        if (declaredParamCount >= 0) // Function was previously declared
        { 
            SymbolInfo* functionSymbol = symbolTable.lookUp($ID.text, parserLogFile);
            if (functionSymbol && functionSymbol->getIsFunction()) 
            {
                int definitionParamCount = functionSymbol->getParameterCount();
                vector<string> definitionParamTypes = functionSymbol->getParameterTypes();
                
                // Check argument count mismatch
                if (definitionParamCount != declaredParamCount) 
                {
                    writeIntoErrorFile("Error at line " + to_string($RPAREN->getLine()) + ": Total number of arguments mismatch with declaration in function " + $ID.text);
                    syntaxErrorCount++;
                    numberOfParametersMismatchError = 1;
                }
                // Check argument types (only if counts match)
                else 
                {
                    for (int i = 0; i < definitionParamCount; i++) 
                    {
                        string declaredType = declaredParamTypes[i];
                        string definitionType = definitionParamTypes[i];
                        
                        // Check for type compatibility using the same logic as factor rule
                        if (!isTypeCompatibleForFunction(declaredType, definitionType)) 
                        {
                            writeIntoErrorFile("Error at line " + to_string($RPAREN->getLine()) + ": " + to_string(i + 1) + "th argument type mismatch with declaration in function " + $ID.text);
                            writeIntoparserLogFile("Error at line " + to_string($RPAREN->getLine()) + ": " + to_string(i + 1) + "th argument type mismatch with declaration in function " + $ID.text);
                            writeIntoparserLogFile("");
                            syntaxErrorCount++;
                            typeOfParametersMismatchError = 1;
                            break;
                        }
                    }
                }
            }
        }
    }
    compound_statement
    {
        lineCount = $compound_statement.stop->getLine();
        
        // Reset current function return type when exiting function because definition will be complete here
        currentFunctionReturnType = "";
        currentFunctionName = "";

            //print rule header first
            writeIntoparserLogFile("Line " + to_string(lineCount) + ": func_definition : type_specifier ID LPAREN parameter_list RPAREN compound_statement");
            writeIntoparserLogFile("");
                
                // Check for any errors that occurred during definition
                if (multipleDeclarationInDeclarationError) {
                    writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
                    writeIntoparserLogFile("");
                } else if (returnTypeMismatchError) {
                    writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Return type mismatch of " + $ID.text);
                    writeIntoparserLogFile("");
                } else if (multipleDeclationInDefinitionError) {
                    writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
                    writeIntoparserLogFile("");
                } else if (numberOfParametersMismatchError) {
                    writeIntoparserLogFile("Error at line " + to_string($RPAREN->getLine()) + ": Total number of arguments mismatch with declaration in function " + $ID.text);
                    writeIntoparserLogFile("");
                } else if (typeOfParametersMismatchError) {
                    // Error already logged inside the loop
                }
            //then print the function definition code, only the $ID.text
            //writeIntoparserLogFile(removeUnnecessarySpaces($type_specifier.text + " " + $ID.text + "(" + $p.text + ")") + "\n" + formatFunctionBlock($compound_statement.text));
            writeIntoparserLogFile(removeEmptyLines(removeUnnecessarySpaces($type_specifier.text + " " + $ID.text + "(" + $p.text + ")") + formatFunctionBlock($compound_statement.text)));
            writeIntoparserLogFile("");    
    }
    | type_specifier ID LPAREN 
    {
        // this will be the rule for funcction definition without parameters
        // these functions can have type_specifier too, save that
        currentFunctionReturnType = getTypeName($type_specifier.ctx);
        currentFunctionName = $ID.text;
        
        // Check if function already exists
        SymbolInfo* existingSymbol = symbolTable.lookUp($ID.text, parserLogFile);
        int multipleDeclarationInDeclarationError=0;
        int returnTypeMismatchError=0;
        int numberOfParametersMismatchError=0;
        int multipleDeclationInDefinitionError=0;
        lineCount = $ID->getLine();
        
        if (existingSymbol && existingSymbol->getIsFunction()) 
        {
            // Function was declared before - check if it's already defined
            if (existingSymbol->getIsDefined()) 
            {
                // Already defined - this is an error
                writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
                multipleDeclarationInDeclarationError = 1;
                syntaxErrorCount++;
            } 
            else 
            {
                // it was not defined before , it was just declared before
                //check if the return type matches with declaration
                if (existingSymbol->getReturnType() != getTypeName($type_specifier.ctx)) 
                {
                    writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Return type mismatch of " + $ID.text);
                    returnTypeMismatchError = 1;
                    syntaxErrorCount++;
                }
                
                // Check parameter count mismatch - definition has 0 parameters but declaration had more
                if (existingSymbol->getParameterCount() > 0) 
                {
                    writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Total number of arguments mismatch with declaration in function " + $ID.text);
                    syntaxErrorCount++;
                    numberOfParametersMismatchError = 1;
                }
                existingSymbol->setIsDefined(true);
            }
        } 
        else 
        {
            // New function - insert it in the symbol table
            SymbolInfo* symbol = new SymbolInfo($ID.text, "FUNCTION");
            symbol->setIsFunction(true);
            symbol->setReturnType(getTypeName($type_specifier.ctx));
            symbol->setIsDefined(true);  // This is an on-the-spot definition
            if (!symbolTable.insert(*symbol, parserLogFile)) 
            {
                writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
                multipleDeclationInDefinitionError = 1;
                syntaxErrorCount++;
            }
        }
        
        symbolTable.enterScope(7, parserLogFile);
    }
    RPAREN compound_statement
    {
        lineCount = $compound_statement.stop->getLine();
        
        // func definition is complete, reset current function return type
        currentFunctionReturnType = "";
        currentFunctionName = "";
        
        //print rule header first
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": func_definition : type_specifier ID LPAREN RPAREN compound_statement");
        writeIntoparserLogFile("");

        // Check for any errors that occurred during definition
        if (multipleDeclarationInDeclarationError) {
            writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
            writeIntoparserLogFile("");
        } else if (returnTypeMismatchError) {
            writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Return type mismatch of " + $ID.text);
            writeIntoparserLogFile("");
        } else if (multipleDeclationInDefinitionError) {
            writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
            writeIntoparserLogFile("");
        } else if (numberOfParametersMismatchError) {
            writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Total number of arguments mismatch with declaration in function " + $ID.text);
            writeIntoparserLogFile("");
        }
        // Print the formatted function definition
        writeIntoparserLogFile(removeUnnecessarySpaces($type_specifier.text + " " + $ID.text + "()") + removeEmptyLines(formatFunctionBlock($compound_statement.text)));
        writeIntoparserLogFile("");
    }
    ;

parameter_list
    : p=parameter_list COMMA type_specifier ID
    {
        int multipleDeclarationInParameterError = 0;

        lineCount = $ID->getLine();

        // ALWAYS add parameter to function symbol (for both declaration and definition)
        string functionName = "";
        if (auto funcDefCtx = dynamic_cast<Func_definitionContext*>(_ctx->parent)) 
        {
            functionName = funcDefCtx->ID()->getText();
        } 
        else if (auto funcDeclCtx = dynamic_cast<Func_declarationContext*>(_ctx->parent)) 
        {
            functionName = funcDeclCtx->ID()->getText();
        }
        
        // Add parameter to the function symbol
        if (!functionName.empty()) 
        {
            SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, parserLogFile);
            if (functionSymbol && functionSymbol->getIsFunction()) 
            {
                functionSymbol->addParameter(getTypeName($type_specifier.ctx));
            }
        }

        // ONLY insert parameter variables IN SYMBOLTABLE for function definitions (not declarations)
        if (dynamic_cast<Func_definitionContext*>(_ctx->parent)) 
        {
            // We're in func_definition - insert parameter symbols in symbol table
            // they need to be inserted in symbol table immediately when they are found in parameter_list, cant wait for LCURL to arrive
            SymbolInfo* symbol = new SymbolInfo($ID->getText(), getTypeName($type_specifier.ctx));
            if (!symbolTable.insert(*symbol, parserLogFile)) 
            {
                multipleDeclarationInParameterError = 1;
                syntaxErrorCount++;
                writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID->getText() + " in parameter");
            }
        } 
        else 
        {
            // We're in func_declaration - we dont need to insert the parameters in symbol table because they are dummy
            // writeIntoparserLogFile("DEBUG: Skipping parameter symbol insertion in declaration");
        }

        writeIntoparserLogFile("Line " + to_string(lineCount) + ": parameter_list : parameter_list COMMA type_specifier ID");
        writeIntoparserLogFile("");

        if (multipleDeclarationInParameterError) 
        {
            writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID->getText() + " in parameter");
            writeIntoparserLogFile("");
        }
        
        writeIntoparserLogFile(formatDeclaration($p.text + "," + $type_specifier.text + " " + $ID->getText()));
        writeIntoparserLogFile("");
    }
    | p=parameter_list COMMA type_specifier
    {
        // This case handles the situation where only type_specifier is declared but ID name is not given, can only happen in func_declaration
        
        // add parameter to function symbol, this can only happen in function declaration, ID er name nai
        string functionName = "";
        if (auto funcDeclCtx = dynamic_cast<Func_declarationContext*>(_ctx->parent)) 
        {
            functionName = funcDeclCtx->ID()->getText();
        }
        
        // Add parameter to the function symbol (even without ID name)
        if (!functionName.empty()) 
        {
            SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, parserLogFile);
            if (functionSymbol && functionSymbol->getIsFunction()) 
            {
                functionSymbol->addParameter(getTypeName($type_specifier.ctx));
            }
        }
        
        logRule("parameter_list : parameter_list COMMA type_specifier", removeUnnecessarySpaces($ctx->getText()));
    }
    | type_specifier ID
    {
        // base case for list of parameters
        int multipleDeclarationInParameterError = 0;
        lineCount = $ID->getLine();

        // ALWAYS add parameter to function symbol (for both declaration and definition)
        string functionName = "";
        if (auto funcDefCtx = dynamic_cast<Func_definitionContext*>(_ctx->parent)) 
        {
            functionName = funcDefCtx->ID()->getText();
        } 
        else if (auto funcDeclCtx = dynamic_cast<Func_declarationContext*>(_ctx->parent)) 
        {
            functionName = funcDeclCtx->ID()->getText();
        }
        
        // Add parameter to the function symbol
        if (!functionName.empty()) 
        {
            SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, parserLogFile);
            if (functionSymbol && functionSymbol->getIsFunction()) 
            {
                functionSymbol->addParameter(getTypeName($type_specifier.ctx));
            }
        }

        // ONLY insert parameter variables IN SYMBOLTABLE for function definitions (not declarations)
        if (dynamic_cast<Func_definitionContext*>(_ctx->parent)) 
        {
            // We're in func_definition - insert parameter symbols in symbol table
            SymbolInfo* symbol = new SymbolInfo($ID->getText(), getTypeName($type_specifier.ctx));
            if (!symbolTable.insert(*symbol, parserLogFile))
            {
                multipleDeclarationInParameterError = 1;
                syntaxErrorCount++;
                writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID->getText() + " in parameter");
            }
        } 
        else
        {
            // We're in func_declaration - skip symbol insertion
            // writeIntoparserLogFile("DEBUG: Skipping parameter symbol insertion in declaration");
        }
        
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": parameter_list : type_specifier ID");
        writeIntoparserLogFile("");

        if (multipleDeclarationInParameterError) 
        {
            writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID->getText() + " in parameter");
            writeIntoparserLogFile("");
        }

        writeIntoparserLogFile(formatDeclaration($type_specifier.text + " " + $ID->getText()));
        writeIntoparserLogFile("");
    }
    | type_specifier
    {   
        // base case for parameter list with just type_specifier, without ID dummy name
        
        // ALWAYS add parameter to function symbol
        string functionName = "";
        if (auto funcDeclCtx = dynamic_cast<Func_declarationContext*>(_ctx->parent)) 
        {
            functionName = funcDeclCtx->ID()->getText();
        }
        
        // Add parameter to the function symbol (even without ID name)
        if (!functionName.empty()) 
        {
            SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, parserLogFile);
            if (functionSymbol && functionSymbol->getIsFunction()) 
            {
                functionSymbol->addParameter(getTypeName($type_specifier.ctx));
            }
        }
        
        logRule("parameter_list : type_specifier", removeUnnecessarySpaces($ctx->getText()));
    }
    | type_specifier error_in_parameter
    {
        // Error handling rule for invalid parameter syntax like "int-"
        lineCount = $type_specifier.stop->getLine();
        syntaxErrorCount++;
        writeIntoErrorFile("Error at line " + to_string(lineCount) + ": syntax error, unexpected ADDOP, expecting RPAREN or COMMA");
        
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": parameter_list : type_specifier");
        writeIntoparserLogFile("");
        //print the error message
        writeIntoparserLogFile("Error at line " + to_string(lineCount) + ": syntax error, unexpected ADDOP, expecting RPAREN or COMMA");
        writeIntoparserLogFile("");
        //then print code
        writeIntoparserLogFile(removeUnnecessarySpaces($type_specifier.text));
        writeIntoparserLogFile("");
    }
    ;

error_in_parameter
    : ADDOP
    | MULOP
    | ASSIGNOP
    | RELOP
    | LOGICOP
    | INCOP
    | DECOP
    | NOT
    ;

compound_statement
: LCURL 
{
    // writeIntoparserLogFile("DEBUG: compound_statement - entering new scope");
    // Only create new scope if NOT already in function scope , this is normal scoping with braces without function definition
    // as said before, function definition already creates scope for parameters
    if (!dynamic_cast<Func_definitionContext*>(_ctx->parent)) 
    {
        symbolTable.enterScope(7, parserLogFile);
    }
}
statements RCURL
{
    lineCount = $RCURL->getLine();
    //writeIntoparserLogFile("DEBUG: compound_statement - exiting scope");
    logRule("compound_statement : LCURL statements RCURL", "{\n" + removeEmptyLines(formatFunctionBlock($statements.text)) + "\n}");
    
    // Always exit scope after getting right brace, either function scope or block scope, both is handled here
    symbolTable.exitScope(parserLogFile, 1);
}
| LCURL RCURL
{
    //nothing between the braces, still we have to enter and exit scope
    lineCount = $RCURL->getLine();
    //writeIntoparserLogFile("DEBUG: compound_statement (empty) - exiting scope");
    logRule("compound_statement : LCURL RCURL", "{\n}");
    
    // Only creat scope if NOT in function definition
    // as said before, function definition already creates scope for parameters
    if (!dynamic_cast<Func_definitionContext*>(_ctx->parent)) {
        symbolTable.enterScope(7, parserLogFile);
    }
    symbolTable.exitScope(parserLogFile, 1);
}
;

var_declaration
    : t=type_specifier d=declaration_list sm=SEMICOLON
    {
        int varTypeIsVoid = 0;
        lineCount = $sm->getLine();
        
        if (getTypeName($t.ctx) == "VOID") 
        {
            writeIntoErrorFile("Error at line " + to_string($sm->getLine()) + ": Variable type cannot be void");
            syntaxErrorCount++;
            varTypeIsVoid = 1;
        }
        
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": var_declaration : type_specifier declaration_list SEMICOLON");
        writeIntoparserLogFile("");
        if (varTypeIsVoid) 
        {
            writeIntoparserLogFile("Error at line " + to_string($sm->getLine()) + ": Variable type cannot be void");
            writeIntoparserLogFile("");
        }
        writeIntoparserLogFile(formatDeclaration($t.text + " " + $d.text + ";"));
        writeIntoparserLogFile("");
    }
    ;

type_specifier
    : INT
    {
        lineCount = $INT->getLine();
        logRule("type_specifier : INT", $INT.text);
    }
    | FLOAT
    {
        lineCount = $FLOAT->getLine();
        logRule("type_specifier : FLOAT", $FLOAT.text);
    }
    | VOID
    {
        lineCount = $VOID->getLine();
        logRule("type_specifier : VOID", $VOID.text);
    }
    ;

declaration_list
    : d=declaration_list COMMA declaration_item
    {
        // getting the actual rulename of the declaration_item
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": declaration_list : declaration_list COMMA " + $declaration_item.ruleName);
        writeIntoparserLogFile("");
        // getting the ID name of the item
        writeIntoparserLogFile($d.text + "," + $declaration_item.text);
        writeIntoparserLogFile("");
    }
    | declaration_item
    {       
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": declaration_list : " + $declaration_item.ruleName);
        writeIntoparserLogFile("");
        writeIntoparserLogFile($declaration_item.text);
        writeIntoparserLogFile("");
    }
    ;

declaration_item
    returns [string ruleName]
    : ID
    {
        // this is simple ID declaration 
        int hasMultipleDeclarationError = 0;
        lineCount = $ID->getLine();
        // declaration_item's parent is declaration_list, which is parent of var_declaration
        // we are getting the type specifier a.k.a.   t    of the var_declaration rule  
        SymbolInfo* symbol = new SymbolInfo($ID.text, getTypeName(dynamic_cast<Var_declarationContext*>(_ctx->parent->parent)->t));
        if (!symbolTable.insert(*symbol, parserLogFile)) 
        {
            hasMultipleDeclarationError = 1;
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
        }
    
        $ruleName = "ID";

        if (hasMultipleDeclarationError) 
        {
            writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Multiple declaration of " + $ID.text);
            writeIntoparserLogFile("");
        }
    }
    | ID LTHIRD CONST_INT RTHIRD
    {
        // this is an array declaration with size
        int hasMultipleDeclarationError = 0;
        lineCount = $RTHIRD->getLine();
        SymbolInfo* symbol = new SymbolInfo($ID.text, getTypeName(dynamic_cast<Var_declarationContext*>(_ctx->parent->parent)->t));
        symbol->setIsArray(true);
        symbol->setArraySize(stoi($CONST_INT.text));
        if (!symbolTable.insert(*symbol, parserLogFile)) 
        {
            hasMultipleDeclarationError = 1;
            syntaxErrorCount++;
            writeIntoErrorFile("Error at line " + to_string($CONST_INT->getLine()) + ": Multiple declaration of " + $ID.text);
        }
        $ruleName = "ID LTHIRD CONST_INT RTHIRD";

        if (hasMultipleDeclarationError) 
        {
            writeIntoparserLogFile("Error at line " + to_string($CONST_INT->getLine()) + ": Multiple declaration of " + $ID.text);
            writeIntoparserLogFile("");
        }
    }
    | ID ADDOP ID
    {
        // Error case: x-y
        lineCount = $ADDOP->getLine();
        syntaxErrorCount++;
        writeIntoErrorFile("Error at line " + to_string($ADDOP->getLine()) + ": syntax error, unexpected ADDOP, expecting COMMA or SEMICOLON");
        
        $ruleName = "ID"; // For error case, just send ID since that's what was expected

        // print rule header first
        // writeIntoparserLogFile("Line " + to_string(lineCount) + ": declaration_item : ID ADDOP ID");
        // writeIntoparserLogFile("");
        // print the error message
        writeIntoparserLogFile("Error at line " + to_string($ADDOP->getLine()) + ": syntax error, unexpected ADDOP, expecting COMMA or SEMICOLON");
        writeIntoparserLogFile("");
        // then print the code
        // writeIntoparserLogFile(removeUnnecessarySpaces($ID.text + " " + $ADDOP.text + " " + $ID.text));
        // writeIntoparserLogFile("");
    }
    ;

statements
    : s=statements statement
    {
        logRule("statements : statements statement", removeEmptyLines(formatFunctionBlock($s.text + "\n" + $statement.text)));
    }
    | statement
    {
        logRule("statements : statement",removeEmptyLines(formatFunctionBlock($statement.text)));
    }
    ;

statement
: var_declaration
{
    logRule("statement : var_declaration", removeEmptyLines(formatFunctionBlock($var_declaration.text)));
}
| expression_statement
{
    logRule("statement : expression_statement", removeUnnecessarySpaces($expression_statement.text));
}
| compound_statement
{
    logRule("statement : compound_statement", removeEmptyLines(formatFunctionBlock($compound_statement.text)));
}
| FOR LPAREN es1=expression_statement es2=expression_statement expression RPAREN statement
{
    //this is a FOR loop
    // because es1 and es2 has a semicolon at the end, and expression has no semicolon
    // after that there could be a simple statement or a compound statement or any kind of loop statement i.e. nested for, while, if etc
    lineCount= $statement.stop->getLine();
    string forBody = removeEmptyLines(formatFunctionBlock($statement.text));

    logRule("statement : FOR LPAREN expression_statement expression_statement expression RPAREN statement", "for(" + removeUnnecessarySpaces($es1.text) + removeUnnecessarySpaces($es2.text) + removeUnnecessarySpaces($expression.text) + ")" + forBody);
}
| IF LPAREN expression RPAREN statement
{
    // this is an IF statement , could  be a simple IF statement or an IF with compound statement i.e. IF block
    lineCount = $statement.stop->getLine();
    string ifBody = removeEmptyLines(formatFunctionBlock($statement.text));

    logRule("statement : IF LPAREN expression RPAREN statement", "if(" + removeUnnecessarySpaces($expression.text) + ")" + ifBody);
}
| IF LPAREN expression RPAREN s1=statement ELSE s2=statement
{
    //IF-ELSE, each can have simple statements or compound statements
    lineCount= $s2.stop->getLine();
    string ifBody = removeEmptyLines(formatFunctionBlock($s1.text));
    string elseBody = removeEmptyLines(formatFunctionBlock($s2.text));

    logRule("statement : IF LPAREN expression RPAREN statement ELSE statement", "if(" + removeUnnecessarySpaces($expression.text) + ")" + ifBody + "\nelse " + elseBody);
}
| WHILE LPAREN expression RPAREN statement
{
    // this is a WHILE loop, because expression does not have a semicolon at the end
    // and statement can be a simple statement or a compound statement i.e. WHILE block
    lineCount= $statement.stop->getLine();
    string whileBody = removeEmptyLines(formatFunctionBlock($statement.text));
    
    logRule("statement : WHILE LPAREN expression RPAREN statement", "while(" + removeUnnecessarySpaces($expression.text) + ")" + whileBody);
}
| PRINTLN LPAREN ID RPAREN SEMICOLON
{
    // this is allowing printf statements 
    lineCount = $SEMICOLON->getLine();
    
    int undeclaredVariableError = 0;
    // Check if variable is declared for printf
    SymbolInfo* symbol = symbolTable.lookUp($ID.text, parserLogFile);
    if (!symbol) 
    {
        writeIntoErrorFile("Error at line " + to_string($SEMICOLON->getLine()) + ": Undeclared variable " + $ID.text);
        syntaxErrorCount++;
        undeclaredVariableError = 1;
    }
    
    //logRule("statement : PRINTLN LPAREN ID RPAREN SEMICOLON", "printf(" + $ID.text + ");");

    // Print the rule header
    writeIntoparserLogFile("Line " + to_string(lineCount) + ": statement : PRINTLN LPAREN ID RPAREN SEMICOLON");
    writeIntoparserLogFile("");
    // print the error if any
    if (undeclaredVariableError) 
    {
        writeIntoparserLogFile("Error at line " + to_string($SEMICOLON->getLine()) + ": Undeclared variable " + $ID.text);
        writeIntoparserLogFile("");
    }
    // then print the code
    writeIntoparserLogFile("printf(" + $ID.text + ");");
    writeIntoparserLogFile("");
}
| RETURN expression SEMICOLON
{
    // these are return statements, which are used in functions
    lineCount = $SEMICOLON->getLine();
    int voidReturnTypeError = 0;
    
    // Check if a function whose return type is void is trying to return a value
    if (currentFunctionReturnType == "VOID") 
    {
        writeIntoErrorFile("Error at line " + to_string($SEMICOLON->getLine()) + ": Cannot return value from function " + currentFunctionName + " with void return type ");      
        syntaxErrorCount++;
        voidReturnTypeError = 1;
    }
    
    // Print the rule header
    writeIntoparserLogFile("Line " + to_string(lineCount) + ": statement : RETURN expression SEMICOLON");
    writeIntoparserLogFile("");
    // print the error if any
    if (voidReturnTypeError) 
    {
        writeIntoparserLogFile("Error at line " + to_string($SEMICOLON->getLine()) + ": Cannot return value from function " + currentFunctionName + " with void return type ");
        writeIntoparserLogFile("");
    }
    // then print the code
    writeIntoparserLogFile("return " + removeUnnecessarySpaces($expression.text) + ";");
    writeIntoparserLogFile("");
}
;

expression_statement
    : SEMICOLON
    {
        lineCount = $SEMICOLON->getLine();
        logRule("expression_statement : SEMICOLON", ";");
    }
    | expression SEMICOLON
    {
        lineCount = $SEMICOLON->getLine();
        logRule("expression_statement : expression SEMICOLON", removeUnnecessarySpaces($expression.text) + ";");
    }
    | expression CONST_INT
    {
        // Error case: expression followed by number without semicolon like "2 + = 6"
        lineCount = $CONST_INT->getLine();
        syntaxErrorCount++;
        // was not in sample errors but need to  catch it so that antlr4 lets us run input5
        writeIntoErrorFile("Error at line " + to_string($CONST_INT->getLine()) + ": missing ';' at '" + $CONST_INT.text + "'"); 
        logRule("expression_statement : expression", removeUnnecessarySpaces($expression.text));
    }
    ;

variable
    returns [string type, bool hasIndexError]
    : ID
    {
        int undeclaredVariableError = 0;
        lineCount = $ID->getLine();
        SymbolInfo* symbol = symbolTable.lookUp($ID.text, parserLogFile);
        if (!symbol) 
        {
            undeclaredVariableError = 1;
            syntaxErrorCount++;
            $type = "UNKNOWN";
        } 
        else 
        {
            $type = symbol->getIsArray() ? "ARRAY" : symbol->getType(); //if it is an array, set type to ARRAY, else set type to its type
        }
        
        $hasIndexError = false; // No index error for simple ID
        
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": variable : ID");
        writeIntoparserLogFile("");
        
        if (undeclaredVariableError) 
        {
            writeIntoErrorFile("Error at line " + to_string($ID->getLine()) + ": Undeclared variable " + $ID.text);
            writeIntoparserLogFile("Error at line " + to_string($ID->getLine()) + ": Undeclared variable " + $ID.text);
            writeIntoparserLogFile("");
        }
        
        writeIntoparserLogFile($ID.text);
        writeIntoparserLogFile("");
    }
    | ID LTHIRD expression RTHIRD
    {
        // like a[0]
        int undeclaredVariableError = 0;
        int notAnArrayError = 0;
        int arrayIndexNotIntegerError = 0;
        lineCount = $RTHIRD->getLine();
        SymbolInfo* symbol = symbolTable.lookUp($ID.text, parserLogFile);
        if (!symbol) 
        {
            writeIntoErrorFile("Error at line " + to_string($RTHIRD->getLine()) + ": Undeclared variable " + $ID.text);
            undeclaredVariableError = 1;
            syntaxErrorCount++;
            $type = "UNKNOWN";
            $hasIndexError = false;
        } 
        else if (!symbol->getIsArray()) 
        {
            writeIntoErrorFile("Error at line " + to_string($RTHIRD->getLine()) + ": " + $ID.text + " not an array");
            notAnArrayError = 1;
            syntaxErrorCount++;
            $type = symbol->getType();
            $hasIndexError = true;
        } 
        else if ($expression.type != "CONST_INT") 
        {
            writeIntoErrorFile("Error at line " + to_string($RTHIRD->getLine()) + ": Expression inside third brackets not an integer");
            arrayIndexNotIntegerError = 1;
            syntaxErrorCount++;
            $type = symbol->getType();
            $hasIndexError = true; // FLAG: We have an index error
        } 
        else 
        {
            $type = symbol->getType();
            $hasIndexError = false;
        }
        
        // Print rule header first
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": variable : ID LTHIRD expression RTHIRD");
        writeIntoparserLogFile("");
        
        if (undeclaredVariableError) {
            writeIntoparserLogFile("Error at line " + to_string($RTHIRD->getLine()) + ": Undeclared variable " + $ID.text);
            writeIntoparserLogFile("");
        } else if (notAnArrayError) {
            writeIntoparserLogFile("Error at line " + to_string($RTHIRD->getLine()) + ": " + $ID.text + " not an array");
            writeIntoparserLogFile("");
        } else if (arrayIndexNotIntegerError) {
            writeIntoparserLogFile("Error at line " + to_string($RTHIRD->getLine()) + ": Expression inside third brackets not an integer");
            writeIntoparserLogFile("");
        }
        
        // Print code last
        writeIntoparserLogFile($ctx->getText());
        writeIntoparserLogFile("");
    }
    ;

expression
    returns [string type]
    : logic_expression
    {
        $type = $logic_expression.type;
        lineCount = $logic_expression.start->getLine();
        logRule("expression : logic_expression", removeUnnecessarySpaces($logic_expression.text));
    }
    | variable ASSIGNOP 
    {
        // Check for array assignment error immediately after ASSIGNOP
        int hasError = 0;
        lineCount = $ASSIGNOP->getLine();
        if ($variable.type == "ARRAY") 
        {
            hasError = 1;
            syntaxErrorCount++;
            
            // Print error immediately (no rule header yet)
            writeIntoErrorFile("Error at line " + to_string($ASSIGNOP->getLine()) + ": Type mismatch, " + $variable.text + " is an array");
            writeIntoparserLogFile("Error at line " + to_string($ASSIGNOP->getLine()) + ": Type mismatch, " + $variable.text + " is an array");
            writeIntoparserLogFile("");
        }
    }
    l=logic_expression
    {
        int hasTypeMismatchError = 0;
        //like a=a+y*2
        // Handle other type compatibility errors ONLY if no array index error AND no arithmetic error AND no void function error
        // because they have been reported in their respective rules
        if ($variable.type != "ARRAY" && $variable.type != "UNKNOWN" && $l.type != "UNKNOWN" && !$variable.hasIndexError && !$l.hasArithmeticError) 
        {
            // Don't trigger type mismatch if we already have void function used in expression error
            bool hasVoidFunctionError = ($l.type == "VOID");
            
            if (!hasVoidFunctionError && !isTypeCompatible($variable.type, $l.type)) 
            {
                syntaxErrorCount++;
                hasTypeMismatchError = 1;
            }
        }
        
        $type = $variable.type;
        
        // Print rule header and code AFTER all error checking
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": expression : variable ASSIGNOP logic_expression");
        writeIntoparserLogFile("");
        if(hasTypeMismatchError) 
        {
            writeIntoErrorFile("Error at line " + to_string($ASSIGNOP->getLine()) + ": Type Mismatch");
            writeIntoparserLogFile("Error at line " + to_string($ASSIGNOP->getLine()) + ": Type Mismatch");
            writeIntoparserLogFile("");
        }
        writeIntoparserLogFile(removeUnnecessarySpaces($variable.text + "=" + $l.text));
        writeIntoparserLogFile("");
    }
    ;

logic_expression
    returns [string type, bool hasArithmeticError]
    : rel_expression
    {
        $type = $rel_expression.type;
        $hasArithmeticError = $rel_expression.hasArithmeticError;
        lineCount = $rel_expression.start->getLine();
        logRule("logic_expression : rel_expression", removeUnnecessarySpaces($rel_expression.text));
    }
    | r1=rel_expression LOGICOP r2=rel_expression
    {
        // like 5%3<4&&8 in j= 2*3+(5%3 < 4 && 8) || 2 ;
        lineCount = $LOGICOP->getLine();
        $type = "INT";
        $hasArithmeticError = $r1.hasArithmeticError || $r2.hasArithmeticError;
        logRule("logic_expression : rel_expression LOGICOP rel_expression", removeUnnecessarySpaces($r1.text + $LOGICOP.text + $r2.text));
    }
    ;

rel_expression
    returns [string type, bool hasArithmeticError]
    : simple_expression
    {
        $type = $simple_expression.type;
        $hasArithmeticError = $simple_expression.hasArithmeticError;
        lineCount = $simple_expression.start->getLine();
        logRule("rel_expression : simple_expression", removeUnnecessarySpaces($simple_expression.text));
    }
    | s1=simple_expression RELOP s2=simple_expression
    {
        // like c<a[0] in if(c<a[0])
        lineCount = $RELOP->getLine();
        $type = "INT";
        $hasArithmeticError = $s1.hasArithmeticError || $s2.hasArithmeticError;
        logRule("rel_expression : simple_expression RELOP simple_expression", removeUnnecessarySpaces($s1.text + $RELOP.text + $s2.text));
    }
    ;

simple_expression
    returns [string type, bool hasArithmeticError]
    : term
    {
        $type = $term.type;
        $hasArithmeticError = $term.hasArithmeticError;
        lineCount = $term.start->getLine();
        logRule("simple_expression : term", removeUnnecessarySpaces($term.text));
    }
    | s=simple_expression ADDOP t=term
    {
        // like a+y*2 in a = a + y * 2;
        lineCount = $ADDOP->getLine();
        $type = ($s.type == "FLOAT" || $t.type == "FLOAT") ? "FLOAT" : "INT"; // if at least one side is float, result is float, else int
        $hasArithmeticError = $s.hasArithmeticError || $t.hasArithmeticError;
        logRule("simple_expression : simple_expression ADDOP term", removeUnnecessarySpaces($s.text + $ADDOP.text + $t.text));
    }
    | s=simple_expression ADDOP ASSIGNOP
    {
        // Error case: 2 + =
        lineCount = $ASSIGNOP->getLine();
        syntaxErrorCount++;
        writeIntoErrorFile("Error at line " + to_string($ASSIGNOP->getLine()) + ": syntax error, unexpected ASSIGNOP");
        $type = "INT";
        $hasArithmeticError = true;
        //logRule("simple_expression : term", removeUnnecessarySpaces($s.text));
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": simple_expression : term");
        writeIntoparserLogFile("");
        writeIntoparserLogFile("Error at line " + to_string($ASSIGNOP->getLine()) + ": syntax error, unexpected ASSIGNOP");
        writeIntoparserLogFile("");
        //writeIntoparserLogFile(removeUnnecessarySpaces($s.text + " " + $ADDOP.text + " " + $ASSIGNOP.text));
        writeIntoparserLogFile(removeUnnecessarySpaces($s.text));
        writeIntoparserLogFile("");
    }
    ;

term
    returns [string type, bool hasArithmeticError]
    : unary_expression
    {
        $type = $unary_expression.type;
        $hasArithmeticError = false;
        lineCount = $unary_expression.start->getLine();
        logRule("term : unary_expression", removeUnnecessarySpaces($unary_expression.text));
    }
    | t=term MULOP u=unary_expression
    {
        // like a[0]*4 in a[0] = a[0] * 4;
        int modulusByZeroError = 0;
        int nonIntegerOperandError = 0;
        lineCount = $MULOP->getLine();
        
        // Check for modulus by zero
        if ($MULOP.text == "%" && $u.text == "0") 
        {
            modulusByZeroError = 1;
            syntaxErrorCount++;
            $hasArithmeticError = true;
        } 
        else if ($MULOP.text == "%" && ($t.type != "CONST_INT" || $u.type != "CONST_INT")) 
        {
            nonIntegerOperandError = 1;
            syntaxErrorCount++;
            $hasArithmeticError = true;
        } 
        else 
        {
            $hasArithmeticError = $t.hasArithmeticError;
        }
        $type = ($t.type == "FLOAT" || $u.type == "FLOAT") ? "FLOAT" : "INT";
        
        // Print rule header first
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": term : term MULOP unary_expression");
        writeIntoparserLogFile("");
        
        if (modulusByZeroError) 
        {
            writeIntoErrorFile("Error at line " + to_string($MULOP->getLine()) + ": Modulus by Zero");
            writeIntoparserLogFile("Error at line " + to_string($MULOP->getLine()) + ": Modulus by Zero");
            writeIntoparserLogFile("");
        } 
        else if (nonIntegerOperandError) 
        {
            writeIntoErrorFile("Error at line " + to_string($MULOP->getLine()) + ": Non-Integer operand on modulus operator");
            writeIntoparserLogFile("Error at line " + to_string($MULOP->getLine()) + ": Non-Integer operand on modulus operator");
            writeIntoparserLogFile("");
        }
        
        // Print code last with formatting
        writeIntoparserLogFile(removeUnnecessarySpaces($t.text + $MULOP.text + $u.text));
        writeIntoparserLogFile("");
    }
    ;

unary_expression
    returns [string type]
    : ADDOP u=unary_expression
    {
        // like -a[1] in a[1] = -a[1];;
        lineCount = $ADDOP->getLine();
        $type = $u.type;
        logRule("unary_expression : ADDOP unary_expression", removeUnnecessarySpaces($ADDOP.text + $u.text));
    }
    | NOT u=unary_expression
    {
        // like !(7<5) in a[0] = !(7<5);
        lineCount = $NOT->getLine();
        $type = "INT";
        logRule("unary_expression : NOT unary_expression", removeUnnecessarySpaces($NOT.text + $u.text));
    }
    | factor
    {
        // like (7<5) in a[0] = !(7<5);
        $type = $factor.type;
        lineCount = $factor.start->getLine();
        logRule("unary_expression : factor", removeUnnecessarySpaces($factor.text));
    }
    ;

factor
    returns [string type]
    : variable
    {
        $type = $variable.type;
        lineCount = $variable.start->getLine();
        logRule("factor : variable", $variable.text);
    }
    | ID LPAREN a=argument_list RPAREN
    {
        // like var(c,j) in a[1]=var(c,j);

        int undefinedFunctionError = 0;
        int notAFunctionError = 0;
        int numberOfArgumentsMismatchError = 0;
        int typeMismatchArrayError = 0;
        int typeOfParametersMismatchError = 0;
        int isInAssignmentExpression = 0; // Flag to check if we're in an assignment expression context, I used the same flag to check void func in arithmetic expression also

        lineCount = $RPAREN->getLine();
        SymbolInfo* symbol = symbolTable.lookUp($ID.text, parserLogFile);
        if (!symbol) 
        {
            undefinedFunctionError = 1;
            writeIntoErrorFile("Error at line " + to_string($RPAREN->getLine()) + ": Undefined function " + $ID.text);
            syntaxErrorCount++;
            $type = "UNKNOWN";
        } 
        else if (!symbol->getIsFunction()) 
        {
            writeIntoErrorFile("Error at line " + to_string($RPAREN->getLine()) + ": " + $ID.text + " is not a function");
            notAFunctionError = 1;
            syntaxErrorCount++;
            $type = "UNKNOWN";
        } 
        else 
        {
            $type = symbol->getReturnType();
            
            // Check for void function used in expression
            if ($type == "VOID") 
            {
                // Check if we're in an assignment expression by looking at parent context
                antlr4::tree::ParseTree* parent = _ctx->parent;
                
                while (parent != nullptr) 
                {
                    antlr4::ParserRuleContext* parentCtx = dynamic_cast<antlr4::ParserRuleContext*>(parent);
                    if (parentCtx != nullptr) 
                    {
                        if (dynamic_cast<ExpressionContext*>(parentCtx)) 
                        {
                            // if the context is an ExpressionContext, check if it contains an assignment operator is USED ALONE, not with other operators 
                            ExpressionContext* exprCtx = dynamic_cast<ExpressionContext*>(parentCtx);
                            if (exprCtx->getText().find("=") != string::npos && exprCtx->getText().find("==") == string::npos && exprCtx->getText().find("!=") == string::npos && exprCtx->getText().find("<=") == string::npos && exprCtx->getText().find(">=") == string::npos) 
                            {
                                isInAssignmentExpression = 1;
                                break;
                            }
                        }
                        // Also check if we are in a term thats part of arithmetic, void value cannot be used in arithmetic expressions
                        if (dynamic_cast<TermContext*>(parentCtx)) 
                        {
                            TermContext* termCtx = dynamic_cast<TermContext*>(parentCtx);
                            if (termCtx->getText().find("*") != string::npos || termCtx->getText().find("/") != string::npos || termCtx->getText().find("%") != string::npos) 
                            {
                                isInAssignmentExpression = 1;
                                break;
                            }
                        }
                    }
                    parent = parent->parent;
                }
                
                if (isInAssignmentExpression) 
                {
                    writeIntoErrorFile("Error at line " + to_string($RPAREN->getLine()) + ": Void function used in expression");
                    syntaxErrorCount++;
                    // log this error here
                    writeIntoparserLogFile("Error at line " + to_string($RPAREN->getLine()) + ": Void function used in expression");
                    writeIntoparserLogFile("");
                }
            }
            
            // function argument validation
            vector<string> expectedParamTypes = symbol->getParameterTypes();
            int expectedParamCount = symbol->getParameterCount();
            
            // Parse actual arguments
            vector<string> actualArgTypes;
            if (!$a.argTypes.empty()) 
            {
                stringstream ss($a.argTypes);
                string argType;
                while (getline(ss, argType, ',')) 
                {
                    actualArgTypes.push_back(argType);
                }
            }
            
            int actualArgCount = actualArgTypes.size();
            
            // Check argument count
            if (actualArgCount != expectedParamCount) 
            {
                writeIntoErrorFile("Error at line " + to_string($RPAREN->getLine()) + ": Total number of arguments mismatch with declaration in function " + $ID.text);
                numberOfArgumentsMismatchError = 1;
                syntaxErrorCount++;
            }
            // Check argument types (only if counts match)
            else 
            {
                for (int i = 0; i < actualArgCount; i++) 
                {
                    string expectedType = expectedParamTypes[i];
                    string actualType = actualArgTypes[i];
                    
                    // Check for array mismatch
                    if (actualType == "ARRAY" && expectedType != "ARRAY") 
                    {
                        writeIntoErrorFile("Error at line " + to_string($RPAREN->getLine()) + ": Type mismatch, argument " + to_string(i + 1) + " is an array");
                        // log this error here
                        writeIntoparserLogFile("Error at line " + to_string($RPAREN->getLine()) + ": Type mismatch, argument " + to_string(i + 1) + " is an array");
                        writeIntoparserLogFile("");
                        typeMismatchArrayError = 1;
                        syntaxErrorCount++;
                        break;
                    }
                    
                    // Check for type compatibility
                    else if (!isTypeCompatibleForFunction(expectedType, actualType)) 
                    {
                        writeIntoErrorFile("Error at line " + to_string($RPAREN->getLine()) + ": " + to_string(i + 1) + "th argument mismatch in function " + $ID.text);
                        //log this error here
                        writeIntoparserLogFile("Error at line " + to_string($RPAREN->getLine()) + ": " + to_string(i + 1) + "th argument mismatch in function " + $ID.text);
                        writeIntoparserLogFile("");
                        typeOfParametersMismatchError = 1;
                        syntaxErrorCount++;
                        break;
                    }
                }
            }
        }
        
        // Print rule header
        writeIntoparserLogFile("Line " + to_string(lineCount) + ": factor : ID LPAREN argument_list RPAREN");
        writeIntoparserLogFile("");
        
        // Print errors
        if (undefinedFunctionError) {
            writeIntoparserLogFile("Error at line " + to_string($RPAREN->getLine()) + ": Undefined function " + $ID.text);
            writeIntoparserLogFile("");
        } else if (notAFunctionError) {
            writeIntoparserLogFile("Error at line " + to_string($RPAREN->getLine()) + ": " + $ID.text + " is not a function");
            writeIntoparserLogFile("");
        } else if (numberOfArgumentsMismatchError) {
            writeIntoparserLogFile("Error at line " + to_string($RPAREN->getLine()) + ": Total number of arguments mismatch with declaration in function " + $ID.text);
            writeIntoparserLogFile("");
        } else if (typeMismatchArrayError) {
            // already logged above
        } else if (typeOfParametersMismatchError) {
            // already logged above
        } else if (isInAssignmentExpression) {
            // had more complex logic here, so already logged above
        }
        
        writeIntoparserLogFile(removeUnnecessarySpaces($ID.text + "(" + $a.text + ")"));
        writeIntoparserLogFile("");
    }
    | LPAREN expression RPAREN
    {
        //like (7<5) in a[0] = !(7<5);
        $type = $expression.type;
        lineCount = $LPAREN->getLine();
        logRule("factor : LPAREN expression RPAREN", "(" + removeUnnecessarySpaces($expression.text) + ")");
    }
    | CONST_INT
    {
        // liek 2 in a = a + y * 2;
        $type = "CONST_INT";
        lineCount = $CONST_INT->getLine();
        logRule("factor : CONST_INT", $CONST_INT.text);
    }
    | CONST_FLOAT
    {
        // like 9.5 in d = 9.5;
        $type = "CONST_FLOAT";
        lineCount = $CONST_FLOAT->getLine();
        logRule("factor : CONST_FLOAT", $CONST_FLOAT.text);
    }
    | variable INCOP
    {
        // like c++ in for(c=0;c<2*d+3;c++)
        $type = $variable.type;
        lineCount = $INCOP->getLine();
        logRule("factor : variable INCOP", $variable.text + $INCOP.text);
    }
    | variable DECOP
    {
        // like a[0]-- in while(a[0]--)
        $type = $variable.type;
        lineCount = $DECOP->getLine();
        logRule("factor : variable DECOP", $variable.text + $DECOP.text);
    }
    ;

argument_list
    returns [string argTypes]
    : arguments
    {
        // like 1,2*3 in var(1,2*3)
        $argTypes = $arguments.argTypes;
        logRule("argument_list : arguments", removeUnnecessarySpaces($arguments.text));
    }
    |
    {
        // empty argument list, like var()
        $argTypes = "";
        logRule("argument_list : ", "");
    }
    ;

arguments
    returns [string argTypes]
    : a=arguments COMMA l=logic_expression
    {
        // like c,j in var(c,j)
        lineCount = $COMMA->getLine();
        $argTypes = $a.argTypes + "," + $l.type;
        logRule("arguments : arguments COMMA logic_expression", removeUnnecessarySpaces($a.text + "," + $l.text));
    }
    | logic_expression
    {
        // like c in var(c)
        lineCount = $logic_expression.start->getLine();
        $argTypes = $logic_expression.type;
        logRule("arguments : logic_expression", removeUnnecessarySpaces($logic_expression.text));
    }
    ;