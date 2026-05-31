# C to x86 Assembly Compiler

A complete, hand-built compiler that translates a **C-like language into runnable 8086 assembly**, implemented from scratch across four progressive phases — symbol table, lexical analysis, syntactic & semantic analysis, and intermediate code generation.

Built as part of the **CSE 310: Compiler Sessional** course. Every phase was designed, debugged, and integrated individually before being layered into the full pipeline.

---

## What This Compiler Does

You write a C program. The compiler:
1. Tokenizes it
2. Parses it and validates semantics
3. Generates optimized x86 assembly

The output `.asm` file runs directly in **emu8086** — a real 8086 emulator — producing correct output.

```
int i, j;
int main() {
    i = 1;
    j = 5 + 8;
    int k;
    k = i + 2 * j;
    println(k);
    return 0;
}
```

**↓ Compiles to ↓**

```asm
.MODEL SMALL
.STACK 1000H
.DATA
    i DW 1 DUP (0000H)
    j DW 1 DUP (0000H)
.Code
main PROC
    MOV AX, @DATA
    MOV DS, AX
    PUSH BP
    MOV BP, SP
    ...
    MOV AX, 1
    MOV i, AX
    PUSH 5
    MOV BX, 8
    POP AX
    ADD AX, BX
    MOV j, AX
    ...
    CALL print_output
```

---

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                    C Source Code (.c)                           │
└────────────────────────┬────────────────────────────────────────┘
                         │
              ┌──────────▼──────────┐
              │  Lexical Analysis   │  (FLEX)
              │  Tokenizer          │
              └──────────┬──────────┘
                         │  Token stream
              ┌──────────▼──────────┐
              │  Syntax Analysis    │  (ANTLR4)
              │  CFG Parser         │
              └──────────┬──────────┘
                         │  Parse tree
              ┌──────────▼──────────┐
              │  Semantic Analysis  │  (ANTLR4 + C++)
              │  Type checking      │
              │  Scope resolution   │
              └──────────┬──────────┘
                         │  Validated AST
              ┌──────────▼──────────┐
              │  Code Generation    │  (ANTLR4 + C++)
              │  x86 Assembly       │
              │  Optimizer          │
              └──────────┬──────────┘
                         │
              ┌──────────▼──────────┐
              │   .asm file         │  → Runs in emu8086
              └─────────────────────┘
```

---

## Phase Breakdown

### Phase 1 — Symbol Table

A hash-table based symbol table with **separate chaining** for collision handling and full **nested scope** management.

- Insert, lookup, and delete symbols
- Enter and exit scopes (scope stack)
- Nested scope IDs (e.g., `1`, `1.1`, `1.1.2`)
- Three configurable hash functions: **SDBM**, **DJB2**, **BKDR** — with a collision report generator to compare them

**Input format (command-driven):**
```
7         ← bucket count
I foo FUNCTION
I i VAR
L i
P C       ← Print current scope
S         ← Enter new scope
I x VAR
P A       ← Print all scopes
E         ← Exit scope
Q         ← Quit
```

**The challenge:** Getting scope enter/exit to mirror C's `{}`  block scoping exactly, and designing a hash function comparison tool that generates statistically meaningful collision ratios across different inputs.

---

### Phase 2 — Lexical Analysis

A **FLEX-based** lexer that tokenizes C source code and integrates with the symbol table from Phase 1.

**Recognized tokens:**
- Keywords: `int`, `float`, `char`, `void`, `if`, `else`, `while`, `for`, `do`, `return`, `switch`, `break`, `continue`, `goto`, `static`, `long`, `short`, `unsigned`
- Operators: all arithmetic, relational, logical, and bitwise operators including `++`, `--`, `<<`, `>>`
- Literals: integers, floats (including scientific notation like `3.14e-5`), character literals, string literals
- Identifiers, comments (single-line and multi-line)

**Error detection:**
- Malformed numbers (multiple decimal points, invalid suffixes)
- Unterminated strings and character constants
- Unclosed block comments

**Output files:**
- `2105047_token.txt` — token stream in `<TYPE, lexeme>` format
- `2105047_log.txt` — line-by-line log with symbol table state

**The challenge:** Handling the full complexity of C's number literal syntax (e.g., `1.2e+3f` vs. `1.2e+3`) and multi-state lexer rules for nested comments — all while keeping the symbol table synchronized across scope changes triggered by `{` and `}`.

---

### Phase 3 — Syntactic & Semantic Analysis (SSA)

An **ANTLR4-based** parser implementing a context-free grammar for a substantial subset of C, with semantic actions that perform full type and scope checking.

**Grammar covers:**
- Global variable and function declarations/definitions
- Nested compound statements and all major control flow: `if/else`, `while`, `for`
- Full expression hierarchy: assignment, logical, relational, additive, multiplicative, unary
- Array declarations and indexing
- Function calls with argument passing
- `return` statements

**Semantic checks performed:**
| Error | Example |
|---|---|
| Multiple declaration | `int x; int x;` in same scope |
| Undeclared variable | `y = 5;` without declaring `y` |
| Type mismatch in assignment | `float f = func_returning_int()` |
| Array index not integer | `a[3.5]` |
| Non-array indexed | `int x; x[0]` |
| Modulus with non-integer | `3.5 % 2` |
| Function parameter count mismatch | `foo(1, 2)` when `foo` takes 1 arg |
| Void return used in expression | `int x = void_func()` |
| Function called before defined | `foo()` before `int foo() {...}` |

**Output files:**
- `parserLog.txt` — formatted parse tree and reconstructed source
- `errorLog.txt` — all semantic/syntax errors with line numbers
- `lexerLog.txt` — token-level details

**The challenge:** ANTLR4's visitor/listener pattern requires manually threading the symbol table through every grammar rule. Tracking scope depth, managing function signatures (parameter types, return type, definition vs. declaration), and correctly propagating expression types up the parse tree — all simultaneously — was the most intellectually demanding part of the project.

---

### Phase 4 — Intermediate Code Generation (ICG)

The final phase extends the parser with **x86 8086 assembly generation**, producing `.asm` files that run in emu8086.

**What gets generated:**

| Construct | Assembly Strategy |
|---|---|
| Global variables | `.DATA` section as `DW` words |
| Local variables | Stack-allocated via `PUSH BX`, accessed as `[BP-n]` |
| Function parameters | Passed on stack, accessed as `[BP+n]` |
| Function call/return | `PUSH BP` / `MOV BP, SP` prologue, `MOV SP, BP` / `POP BP` epilogue |
| Arithmetic `+`, `-` | `ADD`, `SUB` on AX/BX |
| Multiplication `*` | `IMUL BX` |
| Division `/`, modulus `%` | `IDIV BX`, with `XOR DX, DX` for zero-extension |
| Relational `<`, `>`, `<=`, `>=`, `==`, `!=` | `CMP` + conditional jumps + push 0/1 |
| Logical `&&`, `\|\|` | Short-circuit via conditional jumps |
| Unary `-` | `NEG AX` |
| Post-increment/decrement | Save original value, then `INC`/`DEC` |
| `println(x)` | `PUSH x` + `CALL print_output` |
| `return` | Restore stack frame, `MOV AX, 4CH` / `INT 21H` for `main` |

**Built-in print routine:** A `PRINT_OUTPUT` procedure is appended to every generated file. It handles positive and negative integers using digit-extraction via repeated `DIV 10` and DOS interrupt `INT 21H`.

**Optimizer (peephole, multi-pass):**

The optimizer runs repeatedly until no more changes occur, applying these transforms:
- `PUSH X` followed immediately by `POP X` → eliminated
- `MOV A, B` followed by `MOV B, A` → second instruction removed
- `ADD reg, 0` → removed
- `IMUL reg` where reg is 1 → removed
- `PUSH X` / `POP Y` where result is immediately used → converted to `MOV Y, X`

**Output files:**
- `code.asm` — raw generated assembly
- `optimized_code.asm` — after peephole optimization passes

**The challenge:** The 8086 architecture is severely register-constrained (AX, BX, CX, DX only for general use, with IMUL/IDIV clobbering DX). Managing the implicit stack used during expression evaluation — keeping track of what is on the stack at every point in the expression grammar — required very careful alignment between the grammar rules and the code generation logic. Getting function call conventions right (caller saves, callee cleanup, `RET n`) took multiple full redesigns.

---

## Supported Language Features

```c
// Data types
int x;
float y;          // declaration only; arithmetic is integer
char c;

// Arrays
int arr[10];
arr[i] = 5;

// Arithmetic
x = a + b * c - d / e;
x = a % b;
x = -a;
x++;
x--;

// Relational & logical
if (a <= b && c != d) { ... }
while (x > 0 || flag) { ... }

// Control flow
if (cond) { ... } else { ... }
while (cond) { ... }
for (init; cond; update) { ... }

// Functions (integer return, multiple parameters)
int add(int a, int b) {
    return a + b;
}

// Built-in output
println(x);    // prints integer value followed by newline

// Scoping
{
    int local;   // inner scope, shadows outer
}
```

---

## Prerequisites

You need the following tools installed and available on your PATH:

| Tool | Version | Purpose |
|---|---|---|
| [ANTLR4](https://www.antlr.org/) | 4.13.2 | Parser & lexer generation (Phases 3 & 4) |
| ANTLR4 C++ Runtime | 4.13.2 | Runtime library for generated parsers |
| [FLEX](https://github.com/westes/flex) | any recent | Lexer for Phase 2 |
| g++ / GCC | C++17 compatible | Compiling all C++ sources |
| [emu8086](https://emu8086-microprocessor-emulator.en.softonic.com/) | any | Running generated `.asm` output |
| WSL / Linux / Git Bash | — | For running the `.sh` build scripts on Windows |

---

## Step-by-Step Setup (Windows + VSCode)

### 1. Install ANTLR4

**Download the ANTLR4 JAR:**

Go to https://www.antlr.org/download.html and download `antlr-4.13.2-complete.jar`.

Place it somewhere permanent, e.g., `C:\tools\antlr\antlr-4.13.2-complete.jar`.

**Create a wrapper script so you can call `antlr4` from the terminal:**

Create a file `C:\tools\antlr\antlr4.bat` with the following content:
```bat
@echo off
java -jar C:\tools\antlr\antlr-4.13.2-complete.jar %*
```

Add `C:\tools\antlr` to your system PATH (System Properties → Environment Variables → Path).

**Verify:**
```bash
antlr4
```
You should see ANTLR version and usage info.

### 2. Install the ANTLR4 C++ Runtime

The C++ runtime is needed to compile the generated parser code.

**On WSL (Ubuntu/Debian) — recommended for this project:**
```bash
sudo apt-get update
sudo apt-get install -y cmake pkg-config uuid-dev
git clone https://github.com/antlr/antlr4.git
cd antlr4/runtime/Cpp
mkdir build && cd build
cmake ..
make -j4
sudo make install
```

This installs headers to `/usr/local/include/antlr4-runtime` and the library to `/usr/local/lib/libantlr4-runtime.a`.

### 3. Install FLEX

```bash
# WSL / Ubuntu
sudo apt-get install flex
```

### 4. Install g++ with C++17 support

```bash
sudo apt-get install g++
```

Verify: `g++ --version` should show g++ 7 or later.

### 5. Install emu8086

Download from https://emu8086-microprocessor-emulator.en.softonic.com/ and install on Windows. This is a Windows-only tool, so run it natively (not in WSL).

### 6. Open the Project in VSCode

Open the repo root in VSCode. Install the **WSL extension** if you want to build from within VSCode's integrated terminal using WSL.

---

## Running the Compiler (Phase by Phase)

### Phase 1 — Symbol Table

```bash
cd "Offlines/Offline 1 SymbolTable"

# Compile
g++ -o symbol_table 2105047_Main.cpp

# Run (provide bucket count and commands via stdin or file)
./symbol_table < "Sample IO/input.txt"
```

Output is printed to stdout showing the symbol table state, scope hierarchy, and lookup results.

To compare hash functions:
```bash
g++ -o report 2105047_report_generator.cpp
./report
```

---

### Phase 2 — Lexical Analysis

```bash
cd "Offlines/Offline 2 Lexical Analysis"

# Generate lexer binary from FLEX specification
flex 2105047.l
g++ lex.yy.c 2105047_SymbolTable.cpp -o lexer -lfl

# Run on a C source file
./lexer < "Sample IO/input1.txt"
```

**Outputs written automatically:**
- `2105047_token.txt` — token stream
- `2105047_log.txt` — detailed log

---

### Phase 3 — Syntax & Semantic Analysis

```bash
cd "Offlines/Offline 3 SSA"

# Build and run (uses the provided shell script)
bash run-script.sh
./Ctester.out input/your_input.c
```

What `run-script.sh` does:
1. Runs `antlr4 -Dlanguage=Cpp C2105047Lexer.g4` to generate `C2105047Lexer.cpp/.h`
2. Runs `antlr4 -Dlanguage=Cpp C2105047Parser.g4` to generate `C2105047Parser.cpp/.h`
3. Compiles everything together with g++

**Outputs written automatically:**
- `parserLog.txt` — parse tree and reconstructed source
- `errorLog.txt` — all errors with line numbers
- `lexerLog.txt` — token details

---

### Phase 4 — Code Generation (Full Pipeline)

This is the final compiler. Given a `.c` input, it produces runnable x86 assembly.

```bash
cd "Offlines/Offline 4 ICG"

# Build
bash run-script.sh

# Compile your C program
./ICG_Compiler.out input/test1_i.c
```

**Output files produced:**
- `code.asm` — raw generated 8086 assembly
- `optimized_code.asm` — peephole-optimized version
- `errorLog.txt` — any compilation errors

**If there are no errors, `optimized_code.asm` is ready to run in emu8086.**

---

## Running the Output in emu8086

1. Open **emu8086** on Windows.
2. Click **Open** and navigate to `optimized_code.asm` inside the ICG folder.
3. Click **Emulate** to assemble and load the program.
4. Click **Run** (or step through with F8).
5. The output window shows the printed integers, one per line.

**What to expect for `test1_i.c`:**
```
1
13
27
0
0
1
1
0
1
-1
```

---

## Sample Input Programs

All test inputs are in `Offlines/Offline 4 ICG/input/`. Pre-generated assembly for all tests is in `Offlines/Offline 4 ICG/GenCodes/`.

| Input file | Tests |
|---|---|
| `test1_i.c` | Arithmetic, modulus, relational, logical operators, post-increment |
| `test2_i.c` | Nested if/else, while loops |
| `test3_i.c` | for loops, arrays |
| `test4_i.c` | Function definitions and calls |
| `test5_i.c` | Recursive functions |
| `test6_i.c` | Multiple functions, parameter passing |
| `test7_i.c` | Complex expressions, nested calls |
| `bonustest1_i.c` | Array operations |
| `bonustest2_i.c` | Mixed arrays and functions |
| `loop.c` | Loop patterns |
| `func.c` | Function call conventions |
| `exp.c` | Expression evaluation order |

---

## Project Structure

```
CSE 310 repo/
├── readme.md
└── Offlines/
    ├── Offline 1 SymbolTable/
    │   ├── 2105047_Main.cpp           ← Command-driven symbol table driver
    │   ├── 2105047_SymbolTable.cpp    ← Hash table + scope stack
    │   ├── 2105047_report_generator.cpp ← Hash function comparison
    │   └── Sample IO/
    │       ├── input.txt
    │       └── output.txt
    │
    ├── Offline 2 Lexical Analysis/
    │   ├── 2105047.l                  ← FLEX lexer specification
    │   ├── 2105047_SymbolTable.cpp
    │   └── Sample IO/
    │       ├── input*.txt
    │       ├── *_token.txt
    │       └── *_log.txt
    │
    ├── Offline 3 SSA/
    │   ├── C2105047Lexer.g4           ← ANTLR4 lexer grammar
    │   ├── C2105047Parser.g4          ← ANTLR4 parser grammar + semantic actions
    │   ├── 2105047_SymbolTable.h      ← Extended symbol table (function metadata)
    │   ├── 2105047_SymbolTable.cpp
    │   ├── Ctester.cpp                ← Main driver
    │   ├── run-script.sh              ← Build script
    │   └── input/                     ← Test C programs
    │
    └── Offline 4 ICG/
        ├── C2105047Lexer.g4
        ├── C2105047Parser.g4          ← Grammar + assembly generation actions
        ├── 2105047_SymbolTable.h      ← Extended with stack offsets
        ├── 2105047_SymbolTable.cpp
        ├── Ctester.cpp                ← Compiler driver + optimizer
        ├── run-script.sh
        ├── input/                     ← C source inputs
        └── GenCodes/                  ← Pre-generated .asm outputs
```

---

## Challenges & Design Decisions

**Symbol table scoping:** Making the scope stack mirror C's exact block-scoping rules — including that a variable declared inside a nested `{}` is invisible after the `}` closes — required careful coordination between the lexer (which detects `{` and `}`) and the symbol table (which owns scope entry/exit).

**FLEX edge cases:** C's number literal syntax is surprisingly complex. Scientific notation, optional sign in exponent, optional `f`/`F` suffix for floats — writing correct FLEX rules that catch all valid forms while catching all invalid forms (like `1.2.3`) without false positives took many iterations.

**ANTLR4 semantic threading:** ANTLR4 generates a visitor/listener framework, but type information must be manually propagated up through expression rules. Every grammar rule that can produce a value needs to carry a type annotation, and every rule that consumes a value needs to validate types. Doing this cleanly without global state required designing a custom return-value mechanism through ANTLR4's rule contexts.

**x86 stack discipline:** The 8086's expression evaluation model — where every intermediate result lives on the stack — is elegant but extremely easy to corrupt. A single mismatched push/pop anywhere in a complex expression leaves the stack pointer wrong for the rest of the function. Debugging these issues required reading generated assembly line by line and tracing the stack pointer by hand.

**Register pressure:** 8086 has only four general-purpose registers. `IMUL` and `IDIV` implicitly use `DX:AX`, which means any multiplication or division mid-expression clobbers `DX`. Designing the code generator to work around this constraint — using the stack as the primary expression register — was a deliberate architectural choice that avoided a full register allocator.

**Peephole optimizer convergence:** The optimizer needed to be run in a loop because one pass could expose new opportunities (e.g., eliminating a `PUSH`/`POP` pair might reveal a redundant `MOV` that was previously separated by those instructions). The loop runs until the assembly stops changing.

<!-- ```markdown
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)
``` -->
