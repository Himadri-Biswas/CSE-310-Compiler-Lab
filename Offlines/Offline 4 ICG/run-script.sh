#!/bin/bash

echo "========================================="
echo "     ICG Compiler Build Script"
echo "========================================="

# Clean previous builds
# echo "Cleaning previous builds..."
# rm -f *.o ICG_Compiler.out code.asm optimized_code.asm errorLog.txt
# rm -f C2105047Lexer.cpp C2105047Parser.cpp
# rm -f C2105047Lexer.h C2105047Parser.h
# rm -f *.tokens *.interp

# Generate ANTLR4 files
echo "Generating ANTLR4 lexer..."
antlr4 -v 4.13.2 -Dlanguage=Cpp C2105047Lexer.g4 || { 
    echo "ANTLR Lexer generation failed"; 
    exit 1; 
}

echo "Generating ANTLR4 parser..."
antlr4 -v 4.13.2 -Dlanguage=Cpp C2105047Parser.g4 || { 
    echo "ANTLR Parser generation failed"; 
    exit 1; 
}

# Compile with ICG support
echo "Compiling ICG compiler..."
g++ -w -I /usr/local/include/antlr4-runtime -I . \
    C2105047Lexer.cpp \
    C2105047Parser.cpp \
    Ctester.cpp \
    2105047_SymbolTable.cpp \
    -o ICG_Compiler.out \
    -lantlr4-runtime \
    -std=c++17 \
    -no-pie || { 
    echo "Compilation failed"; 
    exit 1; 
}

echo "ICG Compiler built successfully!"
echo ""
echo "========================================="
echo "           Usage Instructions"
echo "========================================="
echo "To compile a C program and generate assembly:"
echo "  ./ICG_Compiler.out <input.c>"
echo ""
echo "Example:"
echo "  ./ICG_Compiler.out test_input.c"
echo ""
# echo "Output files (if no errors):"
# echo "  code.asm          - Original assembly code"
# echo "  optimized_code.asm - Optimized assembly code"
# echo "  errorLog.txt      - Error log (if errors found)"
# echo ""
echo "========================================="