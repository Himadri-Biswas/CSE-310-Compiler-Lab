#include <iostream>
#include <string>

#include "antlr4-runtime.h"
#include "C2105047Lexer.h"
#include "C2105047Parser.h"
#include "2105047_SymbolTable.h"

using namespace antlr4;
using namespace std;

// Define global variables
ofstream parserLogFile("parserLog.txt");
ofstream errorFile("errorLog.txt");
ofstream lexLogFile("lexerLog.txt");
int syntaxErrorCount = 0;

SymbolTable symbolTable(7); // Initialize with size 7
int lineCount = 1;

int main(int argc, const char* argv[]) {
    if (argc != 2) {
        cerr << "Usage: " << argv[0] << " <input_file>" << endl;
        return 1;
    }

    ifstream inputFile(argv[1]);
    if (!inputFile) {
        cerr << "Error opening input file: " << argv[1] << endl;
        return 1;
    }

    if (!parserLogFile || !errorFile || !lexLogFile) {
        cerr << "Error opening output log files" << endl;
        return 1;
    }

    ANTLRInputStream input(inputFile);
    C2105047Lexer lexer(&input);
    CommonTokenStream tokens(&lexer);

    C2105047Parser parser(&tokens);
    tree::ParseTree* tree = parser.start();

    lexLogFile.close();
    parserLogFile.close();
    errorFile.close();
    inputFile.close();

    return 0;
}