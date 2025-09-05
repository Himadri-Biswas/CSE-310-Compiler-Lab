#!/bin/bash

# antlr4 -v 4.13.2 -Dlanguage=Cpp C8086Lexer.g4
# antlr4 -v 4.13.2 -Dlanguage=Cpp C8086Parser.g4
# g++ -std=c++17 -w -I/usr/local/include/antlr4-runtime -c C8086Lexer.cpp C8086Parser.cpp Ctester.cpp 2105047_SymbolTable.cpp
# g++ -std=c++17 -w C8086Lexer.o C8086Parser.o Ctester.o -L/usr/local/lib/ -lantlr4-runtime -o Ctester.out -pthread
# LD_LIBRARY_PATH=/usr/local/lib ./Ctester.out $1




# Generate lexer and parser
antlr4 -v 4.13.2 -Dlanguage=Cpp C2105047Lexer.g4 || { echo "ANTLR Lexer generation failed"; exit 1; }
antlr4 -v 4.13.2 -Dlanguage=Cpp C2105047Parser.g4 || { echo "ANTLR Parser generation failed"; exit 1; }

# Compile with verbose output and error checking
g++ -v -I /usr/local/include/antlr4-runtime -I . C2105047Lexer.cpp C2105047Parser.cpp Ctester.cpp 2105047_SymbolTable.cpp -o Ctester.out -lantlr4-runtime -std=c++17 -no-pie || { echo "Compilation failed"; exit 1; }