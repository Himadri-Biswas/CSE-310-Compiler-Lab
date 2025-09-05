#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <vector>
#include <regex>

#include "antlr4-runtime.h"
#include "C2105047Lexer.h"
#include "C2105047Parser.h"
#include "2105047_SymbolTable.h"

using namespace antlr4;
using namespace std;

// Define global variables
ofstream errorFile("errorLog.txt");
int syntaxErrorCount = 0;

// ICG Global Variables
ofstream codeFile("code.asm");
int labelCount = 1;
int tempCount = 0;
int scopeCount = 1;
int stackOffset = 0;
string currentFunctionName = "";
string currentFunctionReturnType = "";
bool hasReturnStatement = false;
bool inControlStructure = false;

// File line tracking
int asm_LineCount = 0;
int asm_CS_endLine = 0;
int asm_DS_endLine = 0;
bool codeSecWritten = false;

int currentParameterCount = 0;
int currentLocalOffset = -2; // Local variables start at BP-2, BP-4, etc.

vector<string> currentParameterNames;

SymbolTable symbolTable(7); // Initialize with size 7
int lineCount = 1;

// Optimization functions
vector<string> splitInstruction(const string &s, char delim)
{
    vector<string> elems;
    string item = "";
    for (int i = 0; i < s.size(); i++)
    {
        if (s[i] == delim || s[i] == '\t')
        {
            if (item != "")
            {
                elems.push_back(item);
                item = "";
            }
        }
        else
        {
            item += s[i];
        }
    }
    if (item != "")
    {
        elems.push_back(item);
    }
    return elems;
}

bool optimizeAssembly(const string &inputFile, const string &outputFile)
{
    ifstream input(inputFile);
    ofstream output(outputFile);
    string currentLine;
    string previousLine = "";
    vector<string> prevInstruction;
    bool gotOptimized = false;

    while (getline(input, currentLine))
    {
        vector<string> currentInstruction = splitInstruction(currentLine, ' ');

        if (currentInstruction.size() == 0)
        {
            output << currentLine << endl;
            continue;
        }

        // Skip comments
        if (currentInstruction[0][0] == ';')
        {
            output << currentLine << endl;
            continue;
        }

        if (prevInstruction.size() == 0)
        {
            previousLine = currentLine;
            prevInstruction = currentInstruction;
            continue;
        }

        // Optimization 1: Redundant MOV elimination
        if (prevInstruction[0] == "MOV" && currentInstruction[0] == "MOV")
        {
            // Extract operands
            string prevOp1 = prevInstruction[1].substr(0, prevInstruction[1].find(','));
            string prevOp2 = prevInstruction[2];
            string currOp1 = currentInstruction[1].substr(0, currentInstruction[1].find(','));
            string currOp2 = currentInstruction[2];

            // MOV A,B followed by MOV B,A
            if (prevOp1 == currOp2 && prevOp2 == currOp1)
            {
                output << previousLine << endl;
                output << "; " << currentLine << " ; Redundant MOV eliminated" << endl;
                gotOptimized = true;
                previousLine = "";
                prevInstruction.clear();
                continue;
            }

            // MOV A,A (same operands)
            if (prevOp1 == prevOp2)
            {
                output << "; " << previousLine << " ; Same operand MOV eliminated" << endl;
                previousLine = currentLine;
                prevInstruction = currentInstruction;
                gotOptimized = true;
                continue;
            }
        }

        // Optimization 2: PUSH/POP elimination
        if (currentInstruction[0] == "POP" && prevInstruction[0] == "PUSH")
        {
            if (currentInstruction[1] == prevInstruction[1])
            {
                // Same register - eliminate both
                output << "; " << previousLine << " ; PUSH/POP pair eliminated" << endl;
                output << "; " << currentLine << endl;
            }
            else
            {
                // Different registers - convert to MOV
                output << "; " << previousLine << " ; PUSH/POP converted to MOV" << endl;
                output << "; " << currentLine << endl;
                output << "\tMOV " << currentInstruction[1] << ", " << prevInstruction[1] << endl;
            }
            gotOptimized = true;
            previousLine = "";
            prevInstruction.clear();
            continue;
        }

        // Optimization 3: Arithmetic with 0/1
        if ((currentInstruction[0] == "ADD" || currentInstruction[0] == "SUB") && currentInstruction[2] == "0")
        {
            output << "; " << currentLine << " ; Arithmetic with 0 eliminated" << endl;
            gotOptimized = true;
            continue;
        }

        if (currentInstruction[0] == "IMUL" && currentInstruction[1] == "1")
        {
            output << "; " << currentLine << " ; Multiplication by 1 eliminated" << endl;
            gotOptimized = true;
            continue;
        }

        // No optimization - output previous line
        output << previousLine << endl;
        previousLine = currentLine;
        prevInstruction = currentInstruction;
    }

    // Output last line
    if (!previousLine.empty())
    {
        output << previousLine << endl;
    }

    input.close();
    output.close();
    return gotOptimized;
}

void performOptimization()
{
    int passCount = 0;
    bool gotOptimized = false;

    // First pass: code.asm -> optimized_code.asm
    gotOptimized = optimizeAssembly("code.asm", "optimized_code.asm");
    passCount++;

    // Continue optimization until no more improvements
    while (gotOptimized)
    {
        gotOptimized = optimizeAssembly("optimized_code.asm", "temp_optimized.asm");
        if (gotOptimized)
        {
            remove("optimized_code.asm");
            rename("temp_optimized.asm", "optimized_code.asm");
            passCount++;
        }
        else
        {
            remove("temp_optimized.asm");
        }
    }

    cout << "Optimization completed in " << passCount << " passes." << endl;
}

void addLibraryProcedures()
{
    string str = "";
    str += "\nNEW_LINE PROC\r\n";
    str += "\tPUSH AX\r\n";
    str += "\tPUSH DX\r\n";
    str += "\tMOV AH, 2\r\n";
    str += "\tMOV DL, cr\r\n";
    str += "\tINT 21H\r\n";
    str += "\tMOV AH, 2\r\n";
    str += "\tMOV DL, lf\r\n";
    str += "\tINT 21H\r\n";
    str += "\tPOP DX\r\n";
    str += "\tPOP AX\r\n";
    str += "\tRET\r\n";
    str += "NEW_LINE ENDP\r\n";

    codeFile << str << endl;

    str = "";
    str += "PRINT_OUTPUT PROC NEAR\n\
    PUSH BP   ;Save BP\n\
    MOV BP, SP   ;BP points to the top of the stack\n\
    MOV BX, [BP+4]   ;The number to be printed\n\
    ;if(BX >= 0) then the number is positive\n\
    CMP BX, 0\n\
    JGE POSITIVE\n\
    ;else, the number is negative\n\
    MOV AH, 2\n\
    MOV DL, '-'   ;Print a '-' sign\n\
    INT 21H\n\
    NEG BX    ;make BX positive by applying 2s complement\n\
    POSITIVE:\n\
    MOV AX, BX\n\
    MOV CX, 0     ;Initialize character count\n\
    PUSH_WHILE:\n\
        XOR DX, DX  ;clear DX\n\
        MOV BX, 10  ;BX has the divisor, AX has the dividend\n\
        DIV BX\n\
        ;quotient is in AX and remainder is in DX\n\
        PUSH DX   ;Push the remainder\n\
        INC CX  ;CX++\n\
        ;if(AX == 0) then break the loop\n\
        CMP AX, 0\n\
        JE END_PUSH_WHILE\n\
        ;else continue\n\
        JMP PUSH_WHILE\n\
    END_PUSH_WHILE:\n\
    MOV AH, 2\n\
    POP_WHILE:\n\
        POP DX  ;Pop the remainder\n\
        ADD DL, '0'\n\
        INT 21H  ; DL has the desired character now\n\
        DEC CX  ;CX--\n\
        ;if(CX <= 0) then end loop\n\
        CMP CX, 0\n\
        JLE END_POP_WHILE\n\
        ;else continue\n\
        JMP POP_WHILE\n\
    END_POP_WHILE:\n\
    ;Print newline\n\
    MOV DL, 0DH\n\
    INT 21H\n\
    MOV DL, 0AH\n\
    INT 21H\n\
    POP BP  ; Restore BP\n\
    RET 2\n\
PRINT_OUTPUT ENDP";

    codeFile << str << endl;
}

int main(int argc, const char *argv[])
{
    if (argc != 2)
    {
        cerr << "Usage: " << argv[0] << " <input_file>" << endl;
        return 1;
    }

    ifstream inputFile(argv[1]);
    if (!inputFile)
    {
        cerr << "Error opening input file: " << argv[1] << endl;
        return 1;
    }

    if (!errorFile || !codeFile)
    {
        cerr << "Error opening output files" << endl;
        return 1;
    }

    cout << "Starting compilation..." << endl;

    // Initialize assembly file structure
    codeFile << ".MODEL SMALL" << endl;
    codeFile << ".STACK 1000H" << endl;
    codeFile << ".DATA" << endl;
    codeFile << "\tCR EQU 0DH" << endl;
    codeFile << "\tLF EQU 0AH" << endl;
    
    codeFile.close(); 

    // Set initial line counts
    asm_LineCount = 5;
    asm_DS_endLine = 5;

    // Parse the input
    ANTLRInputStream input(inputFile);
    C2105047Lexer lexer(&input);
    CommonTokenStream tokens(&lexer);
    C2105047Parser parser(&tokens);

    // Parse and generate code
    tree::ParseTree *tree = parser.start();

    cout << "Parsing completed." << endl;
    cout << "Syntax errors found: " << syntaxErrorCount << endl;

    if (syntaxErrorCount == 0)
    {
        cout << "No syntax errors found. Code generation successful!" << endl;

        codeFile.open("code.asm", ios::app);
        
        addLibraryProcedures();
        
        // End assembly file
        codeFile << "END main" << endl;
        codeFile.close();

        cout << "Assembly code generated in code.asm" << endl;

        performOptimization();
        cout << "Optimized assembly code generated in optimized_code.asm" << endl;
    }
    else
    {
        cout << "Syntax errors found. Code generation skipped." << endl;
        codeFile.close();
        // Remove incomplete assembly file
        remove("code.asm");
    }

    errorFile.close();
    inputFile.close();

    return syntaxErrorCount > 0 ? 1 : 0;
}