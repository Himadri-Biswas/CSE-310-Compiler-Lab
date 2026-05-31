// Generated from e:/academics repo/CSE 310 repo/Offlines/Offline 4 ICG/C2105047Parser.g4 by ANTLR 4.13.1

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

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class C2105047Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LINE_COMMENT=1, BLOCK_COMMENT=2, STRING=3, WS=4, IF=5, ELSE=6, FOR=7, 
		WHILE=8, PRINTLN=9, RETURN=10, INT=11, FLOAT=12, VOID=13, LPAREN=14, RPAREN=15, 
		LCURL=16, RCURL=17, LTHIRD=18, RTHIRD=19, SEMICOLON=20, COMMA=21, ADDOP=22, 
		MULOP=23, INCOP=24, DECOP=25, NOT=26, RELOP=27, LOGICOP=28, ASSIGNOP=29, 
		ID=30, CONST_INT=31, CONST_FLOAT=32, UNRECOGNIZED_CHAR=33;
	public static final int
		RULE_start = 0, RULE_program = 1, RULE_unit = 2, RULE_func_declaration = 3, 
		RULE_func_definition = 4, RULE_parameter_list = 5, RULE_compound_statement = 6, 
		RULE_var_declaration = 7, RULE_type_specifier = 8, RULE_declaration_list = 9, 
		RULE_declaration_item = 10, RULE_statements = 11, RULE_statement = 12, 
		RULE_expression_statement = 13, RULE_variable = 14, RULE_expression = 15, 
		RULE_logic_expression = 16, RULE_rel_expression = 17, RULE_simple_expression = 18, 
		RULE_term = 19, RULE_unary_expression = 20, RULE_factor = 21, RULE_argument_list = 22, 
		RULE_arguments = 23;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "program", "unit", "func_declaration", "func_definition", "parameter_list", 
			"compound_statement", "var_declaration", "type_specifier", "declaration_list", 
			"declaration_item", "statements", "statement", "expression_statement", 
			"variable", "expression", "logic_expression", "rel_expression", "simple_expression", 
			"term", "unary_expression", "factor", "argument_list", "arguments"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'if'", "'else'", "'for'", "'while'", "'println'", 
			"'return'", "'int'", "'float'", "'void'", "'('", "')'", "'{'", "'}'", 
			"'['", "']'", "';'", "','", null, null, "'++'", "'--'", "'!'", null, 
			null, "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LINE_COMMENT", "BLOCK_COMMENT", "STRING", "WS", "IF", "ELSE", 
			"FOR", "WHILE", "PRINTLN", "RETURN", "INT", "FLOAT", "VOID", "LPAREN", 
			"RPAREN", "LCURL", "RCURL", "LTHIRD", "RTHIRD", "SEMICOLON", "COMMA", 
			"ADDOP", "MULOP", "INCOP", "DECOP", "NOT", "RELOP", "LOGICOP", "ASSIGNOP", 
			"ID", "CONST_INT", "CONST_FLOAT", "UNRECOGNIZED_CHAR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "C2105047Parser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	    
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

	public C2105047Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartContext extends ParserRuleContext {
		public ProgramContext program;
		public ProgramContext program() {
			return getRuleContext(ProgramContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			((StartContext)_localctx).program = program(0);

			        lineCount = (((StartContext)_localctx).program!=null?(((StartContext)_localctx).program.stop):null)->getLine();
			        if (syntaxErrorCount == 0) 
			        {
			            symbolTable.printAllScopeTable(errorFile);
			        }
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public ProgramContext p;
		public UnitContext unit;
		public UnitContext u;
		public UnitContext unit() {
			return getRuleContext(UnitContext.class,0);
		}
		public ProgramContext program() {
			return getRuleContext(ProgramContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		return program(0);
	}

	private ProgramContext program(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ProgramContext _localctx = new ProgramContext(_ctx, _parentState);
		ProgramContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_program, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(52);
			((ProgramContext)_localctx).unit = unit();

			        lineCount = (((ProgramContext)_localctx).unit!=null?(((ProgramContext)_localctx).unit.stop):null)->getLine();
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(61);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ProgramContext(_parentctx, _parentState);
					_localctx.p = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_program);
					setState(55);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(56);
					((ProgramContext)_localctx).u = ((ProgramContext)_localctx).unit = unit();

					                  lineCount = (((ProgramContext)_localctx).u!=null?(((ProgramContext)_localctx).u.stop):null)->getLine();
					              
					}
					} 
				}
				setState(63);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UnitContext extends ParserRuleContext {
		public Var_declarationContext var_declaration() {
			return getRuleContext(Var_declarationContext.class,0);
		}
		public Func_declarationContext func_declaration() {
			return getRuleContext(Func_declarationContext.class,0);
		}
		public Func_definitionContext func_definition() {
			return getRuleContext(Func_definitionContext.class,0);
		}
		public UnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unit; }
	}

	public final UnitContext unit() throws RecognitionException {
		UnitContext _localctx = new UnitContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_unit);
		try {
			setState(73);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				var_declaration();

				        // Global variable declaration
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(67);
				func_declaration();

				        // Function declaration - no code generation needed
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(70);
				func_definition();

				        // Function definition - code already generated in the rule
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Func_declarationContext extends ParserRuleContext {
		public Type_specifierContext type_specifier;
		public Token ID;
		public Parameter_listContext p;
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public TerminalNode ID() { return getToken(C2105047Parser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(C2105047Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(C2105047Parser.RPAREN, 0); }
		public TerminalNode SEMICOLON() { return getToken(C2105047Parser.SEMICOLON, 0); }
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public Func_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_declaration; }
	}

	public final Func_declarationContext func_declaration() throws RecognitionException {
		Func_declarationContext _localctx = new Func_declarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_func_declaration);
		try {
			setState(91);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				((Func_declarationContext)_localctx).type_specifier = type_specifier();
				setState(76);
				((Func_declarationContext)_localctx).ID = match(ID);

				        int hasError = 0;
				        SymbolInfo* symbol = new SymbolInfo((((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null), "FUNCTION"); 
				        symbol->setIsFunction(true);
				        symbol->setReturnType(getTypeName(((Func_declarationContext)_localctx).type_specifier));
				        symbol->setIsDefined(false);
				        if (!symbolTable.insert(*symbol, errorFile)) 
				        { 
				            hasError = 1;
				            syntaxErrorCount++;
				            writeIntoErrorFile("Error at line " + to_string(((Func_declarationContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null));
				        }
				    
				setState(78);
				match(LPAREN);
				setState(79);
				((Func_declarationContext)_localctx).p = parameter_list(0);
				setState(80);
				match(RPAREN);
				setState(81);
				match(SEMICOLON);

				        // function declaration with return type and parameter list
				        // Function declaration complete
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(84);
				((Func_declarationContext)_localctx).type_specifier = type_specifier();
				setState(85);
				((Func_declarationContext)_localctx).ID = match(ID);
				setState(86);
				match(LPAREN);
				setState(87);
				match(RPAREN);
				setState(88);
				match(SEMICOLON);

				        // Function declaration with return type but without parameters
				        int hasError = 0;
				        SymbolInfo* symbol = new SymbolInfo((((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null), "FUNCTION");
				        symbol->setIsFunction(true);
				        symbol->setReturnType(getTypeName(((Func_declarationContext)_localctx).type_specifier));
				        symbol->setIsDefined(false);
				        if (!symbolTable.insert(*symbol, errorFile)) 
				        {
				            hasError = 1;
				            syntaxErrorCount++;
				            writeIntoErrorFile("Error at line " + to_string(((Func_declarationContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null));
				        }
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Func_definitionContext extends ParserRuleContext {
		public Type_specifierContext type_specifier;
		public Token ID;
		public Parameter_listContext p;
		public Compound_statementContext compound_statement;
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public TerminalNode ID() { return getToken(C2105047Parser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(C2105047Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(C2105047Parser.RPAREN, 0); }
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public Func_definitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_definition; }
	}

	public final Func_definitionContext func_definition() throws RecognitionException {
		Func_definitionContext _localctx = new Func_definitionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_func_definition);
		try {
			setState(110);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(93);
				((Func_definitionContext)_localctx).type_specifier = type_specifier();
				setState(94);
				((Func_definitionContext)_localctx).ID = match(ID);
				setState(95);
				match(LPAREN);

				        currentFunctionReturnType = getTypeName(((Func_definitionContext)_localctx).type_specifier);
				        currentFunctionName = (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null);
				        hasReturnStatement = false;

				        currentParameterCount = 0;  // Reset parameter count
				        
				        // Check if function already exists
				        SymbolInfo* existingSymbol = symbolTable.lookUp((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), errorFile);
				        int hasError = 0;
				        
				        if (existingSymbol && existingSymbol->getIsFunction()) 
				        {
				            // Function already declared, see if it has been defined
				            if (existingSymbol->getIsDefined()) 
				            {
				                // now thats an error
				                writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                hasError = 1;
				                syntaxErrorCount++;
				            } 
				            else 
				            {
				                // function is already declared but not defined, check return type
				                if (existingSymbol->getReturnType() != getTypeName(((Func_definitionContext)_localctx).type_specifier)) 
				                {
				                    writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Return type mismatch of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                    hasError = 1;
				                    syntaxErrorCount++;
				                }
				                existingSymbol->setIsDefined(true);
				            }
				        } 
				        else 
				        {
				            // on the spot definition of a new function
				            SymbolInfo* symbol = new SymbolInfo((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), "FUNCTION");
				            symbol->setIsFunction(true);
				            symbol->setReturnType(getTypeName(((Func_definitionContext)_localctx).type_specifier));
				            symbol->setIsDefined(true);
				            if (!symbolTable.insert(*symbol, errorFile)) 
				            {
				                writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                hasError = 1;
				                syntaxErrorCount++;
				            }
				        }
				        
				        generateFunctionProlog((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				        
				        symbolTable.enterScope(7, errorFile);
				    
				setState(97);
				((Func_definitionContext)_localctx).p = parameter_list(0);
				setState(98);
				match(RPAREN);
				setState(99);
				((Func_definitionContext)_localctx).compound_statement = compound_statement();

				        // function definition with return type, parameter list and body
				        lineCount = (((Func_definitionContext)_localctx).compound_statement!=null?(((Func_definitionContext)_localctx).compound_statement.stop):null)->getLine();
				        
				        // Only generate function epilog if no return statement was found
				        if (!hasReturnStatement) 
				        {
				            generateFunctionEpilog((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), currentParameterCount);
				        } 
				        else 
				        {
				            // Just generate the ENDP for functions with return statements, because return statement's rule has already generated RET related codes
				            if (syntaxErrorCount == 0) 
				            {
				                writeCode((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null) + " ENDP"); // ASM: End function definition when return statement handled epilog
				            }
				        }
				        
				        currentFunctionReturnType = "";
				        currentFunctionName = "";

				        currentParameterCount = 0;  // Reset after function
				        hasReturnStatement = false; // Reset for next function
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				((Func_definitionContext)_localctx).type_specifier = type_specifier();
				setState(103);
				((Func_definitionContext)_localctx).ID = match(ID);
				setState(104);
				match(LPAREN);

				        currentFunctionReturnType = getTypeName(((Func_definitionContext)_localctx).type_specifier);
				        currentFunctionName = (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null);
				        hasReturnStatement = false;
				        currentParameterCount = 0;  // Reset parameter count
				        
				        SymbolInfo* existingSymbol = symbolTable.lookUp((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), errorFile);
				        int hasError = 0;
				        
				        if (existingSymbol && existingSymbol->getIsFunction()) 
				        {
				            if (existingSymbol->getIsDefined()) 
				            {
				                writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                hasError = 1;
				                syntaxErrorCount++;
				            } 
				            else 
				            {
				                if (existingSymbol->getReturnType() != getTypeName(((Func_definitionContext)_localctx).type_specifier)) 
				                {
				                    writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Return type mismatch of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                    hasError = 1;
				                    syntaxErrorCount++;
				                }
				                existingSymbol->setIsDefined(true);
				            }
				        } 
				        else 
				        {
				            SymbolInfo* symbol = new SymbolInfo((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), "FUNCTION");
				            symbol->setIsFunction(true);
				            symbol->setReturnType(getTypeName(((Func_definitionContext)_localctx).type_specifier));
				            symbol->setIsDefined(true);
				            if (!symbolTable.insert(*symbol, errorFile)) 
				            {
				                writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                hasError = 1;
				                syntaxErrorCount++;
				            }
				        }
				        
				        generateFunctionProlog((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				        symbolTable.enterScope(7, errorFile);
				    
				setState(106);
				match(RPAREN);
				setState(107);
				((Func_definitionContext)_localctx).compound_statement = compound_statement();

				        // function definition with return type but without parameters
				        lineCount = (((Func_definitionContext)_localctx).compound_statement!=null?(((Func_definitionContext)_localctx).compound_statement.stop):null)->getLine();
				        
				        // Only generate function epilog if no return statement was found
				        if (!hasReturnStatement) 
				        {
				            generateFunctionEpilog((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), currentParameterCount);
				        } 
				        else 
				        {
				            // Just generate the ENDP for functions with return statements, because return statement's rule has already generated RET related codes
				            if (syntaxErrorCount == 0) {
				                writeCode((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null) + " ENDP"); // ASM: End function definition when return statement handled epilog
				            }
				        }
				        
				        currentFunctionReturnType = "";
				        currentFunctionName = "";
				        currentParameterCount = 0;  // Reset after function
				        hasReturnStatement = false; // Reset for next function
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Parameter_listContext extends ParserRuleContext {
		public Parameter_listContext p;
		public Type_specifierContext type_specifier;
		public Token ID;
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public TerminalNode ID() { return getToken(C2105047Parser.ID, 0); }
		public TerminalNode COMMA() { return getToken(C2105047Parser.COMMA, 0); }
		public Parameter_listContext parameter_list() {
			return getRuleContext(Parameter_listContext.class,0);
		}
		public Parameter_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter_list; }
	}

	public final Parameter_listContext parameter_list() throws RecognitionException {
		return parameter_list(0);
	}

	private Parameter_listContext parameter_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Parameter_listContext _localctx = new Parameter_listContext(_ctx, _parentState);
		Parameter_listContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_parameter_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(113);
				((Parameter_listContext)_localctx).type_specifier = type_specifier();
				setState(114);
				((Parameter_listContext)_localctx).ID = match(ID);

				        int hasError = 0;
				        lineCount = ((Parameter_listContext)_localctx).ID->getLine();

				        string functionName = currentFunctionName;
				        
				        if (!functionName.empty()) 
				        {
				            SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, errorFile);
				            if (functionSymbol && functionSymbol->getIsFunction()) 
				            {
				                functionSymbol->addParameter(getTypeName(((Parameter_listContext)_localctx).type_specifier));
				            }
				        }

				        currentParameterNames.push_back(((Parameter_listContext)_localctx).ID->getText());
				        
				        SymbolInfo* symbol = new SymbolInfo(((Parameter_listContext)_localctx).ID->getText(), getTypeName(((Parameter_listContext)_localctx).type_specifier));
				        symbol->setIsParameter(true);
				        symbol->setStackOffset(9999); // Temporary
				        
				        if (!symbolTable.insert(*symbol, errorFile)) 
				        {
				            hasError = 1;
				            syntaxErrorCount++;
				            writeIntoErrorFile("Error at line " + to_string(((Parameter_listContext)_localctx).ID->getLine()) + ": Multiple declaration of " + ((Parameter_listContext)_localctx).ID->getText() + " in parameter");
				        }
				        
				        currentParameterCount++;  // Increment after setting offset
				    
				}
				break;
			case 2:
				{
				setState(117);
				((Parameter_listContext)_localctx).type_specifier = type_specifier();
				   
				        string functionName = currentFunctionName;
				        
				        if (!functionName.empty()) 
				        {
				            SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, errorFile);
				            if (functionSymbol && functionSymbol->getIsFunction()) 
				            {
				                functionSymbol->addParameter(getTypeName(((Parameter_listContext)_localctx).type_specifier));
				            }
				        }
				        
				        currentParameterCount++;  // Increment for unnamed parameter too
				    
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(135);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(133);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new Parameter_listContext(_parentctx, _parentState);
						_localctx.p = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_parameter_list);
						setState(122);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(123);
						match(COMMA);
						setState(124);
						((Parameter_listContext)_localctx).type_specifier = type_specifier();
						setState(125);
						((Parameter_listContext)_localctx).ID = match(ID);

						                  int hasError = 0;
						                  lineCount = ((Parameter_listContext)_localctx).ID->getLine();

						                  string functionName = currentFunctionName;
						                  
						                  if (!functionName.empty()) 
						                  {
						                      SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, errorFile);
						                      if (functionSymbol && functionSymbol->getIsFunction()) 
						                      {
						                          functionSymbol->addParameter(getTypeName(((Parameter_listContext)_localctx).type_specifier));
						                      }
						                  }

						                  currentParameterNames.push_back(((Parameter_listContext)_localctx).ID->getText());
						                  
						                  SymbolInfo* symbol = new SymbolInfo(((Parameter_listContext)_localctx).ID->getText(), getTypeName(((Parameter_listContext)_localctx).type_specifier));
						                  symbol->setIsParameter(true);
						                  symbol->setStackOffset(9999); // Temporary, will be fixed in compound_statement

						                  
						                  if (!symbolTable.insert(*symbol, errorFile)) 
						                  {
						                      hasError = 1;
						                      syntaxErrorCount++;
						                      writeIntoErrorFile("Error at line " + to_string(((Parameter_listContext)_localctx).ID->getLine()) + ": Multiple declaration of " + ((Parameter_listContext)_localctx).ID->getText() + " in parameter");
						                  }
						                  // else
						                  // {
						                  //     // DEBUG: Check if offset is still correct after insertion
						                  //     SymbolInfo* inserted = symbolTable.lookUp(((Parameter_listContext)_localctx).ID->getText(), errorFile);
						                  //     cout << "DEBUG: After insertion, parameter " << ((Parameter_listContext)_localctx).ID->getText() << " has offset " << inserted->getStackOffset() << endl;
						                  // }
						                  
						                  currentParameterCount++;  // Increment after setting offset
						              
						}
						break;
					case 2:
						{
						_localctx = new Parameter_listContext(_parentctx, _parentState);
						_localctx.p = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_parameter_list);
						setState(128);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(129);
						match(COMMA);
						setState(130);
						((Parameter_listContext)_localctx).type_specifier = type_specifier();

						                  string functionName = currentFunctionName;
						                  
						                  if (!functionName.empty()) 
						                  {
						                      SymbolInfo* functionSymbol = symbolTable.lookUp(functionName, errorFile);
						                      if (functionSymbol && functionSymbol->getIsFunction()) 
						                      {
						                          functionSymbol->addParameter(getTypeName(((Parameter_listContext)_localctx).type_specifier));
						                      }
						                  }
						                  
						                  currentParameterCount++;  // Increment for unnamed parameter too, this can happen only in function declaration
						              
						}
						break;
					}
					} 
				}
				setState(137);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Compound_statementContext extends ParserRuleContext {
		public Token RCURL;
		public TerminalNode LCURL() { return getToken(C2105047Parser.LCURL, 0); }
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public TerminalNode RCURL() { return getToken(C2105047Parser.RCURL, 0); }
		public Compound_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compound_statement; }
	}

	public final Compound_statementContext compound_statement() throws RecognitionException {
		Compound_statementContext _localctx = new Compound_statementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_compound_statement);
		try {
			setState(147);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(138);
				match(LCURL);

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

				setState(140);
				statements(0);
				setState(141);
				((Compound_statementContext)_localctx).RCURL = match(RCURL);

				    lineCount = ((Compound_statementContext)_localctx).RCURL->getLine();
				    symbolTable.exitScope(errorFile, 1);

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				match(LCURL);
				setState(145);
				((Compound_statementContext)_localctx).RCURL = match(RCURL);

				    lineCount = ((Compound_statementContext)_localctx).RCURL->getLine();
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
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Var_declarationContext extends ParserRuleContext {
		public Type_specifierContext t;
		public Declaration_listContext d;
		public Token sm;
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public Declaration_listContext declaration_list() {
			return getRuleContext(Declaration_listContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(C2105047Parser.SEMICOLON, 0); }
		public Var_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_declaration; }
	}

	public final Var_declarationContext var_declaration() throws RecognitionException {
		Var_declarationContext _localctx = new Var_declarationContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_var_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			((Var_declarationContext)_localctx).t = type_specifier();
			setState(150);
			((Var_declarationContext)_localctx).d = declaration_list(0);
			setState(151);
			((Var_declarationContext)_localctx).sm = match(SEMICOLON);

			        lineCount = ((Var_declarationContext)_localctx).sm->getLine();
			        
			        if (getTypeName(((Var_declarationContext)_localctx).t) == "VOID") 
			        {
			            writeIntoErrorFile("Error at line " + to_string(((Var_declarationContext)_localctx).sm->getLine()) + ": Variable type cannot be void");
			            syntaxErrorCount++;
			        }
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Type_specifierContext extends ParserRuleContext {
		public Token INT;
		public Token FLOAT;
		public Token VOID;
		public TerminalNode INT() { return getToken(C2105047Parser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(C2105047Parser.FLOAT, 0); }
		public TerminalNode VOID() { return getToken(C2105047Parser.VOID, 0); }
		public Type_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier; }
	}

	public final Type_specifierContext type_specifier() throws RecognitionException {
		Type_specifierContext _localctx = new Type_specifierContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_type_specifier);
		try {
			setState(160);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(154);
				((Type_specifierContext)_localctx).INT = match(INT);

				        lineCount = ((Type_specifierContext)_localctx).INT->getLine();
				    
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(156);
				((Type_specifierContext)_localctx).FLOAT = match(FLOAT);

				        lineCount = ((Type_specifierContext)_localctx).FLOAT->getLine();
				    
				}
				break;
			case VOID:
				enterOuterAlt(_localctx, 3);
				{
				setState(158);
				((Type_specifierContext)_localctx).VOID = match(VOID);

				        lineCount = ((Type_specifierContext)_localctx).VOID->getLine();
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Declaration_listContext extends ParserRuleContext {
		public Declaration_listContext d;
		public Declaration_itemContext declaration_item() {
			return getRuleContext(Declaration_itemContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(C2105047Parser.COMMA, 0); }
		public Declaration_listContext declaration_list() {
			return getRuleContext(Declaration_listContext.class,0);
		}
		public Declaration_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_list; }
	}

	public final Declaration_listContext declaration_list() throws RecognitionException {
		return declaration_list(0);
	}

	private Declaration_listContext declaration_list(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Declaration_listContext _localctx = new Declaration_listContext(_ctx, _parentState);
		Declaration_listContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_declaration_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(163);
			declaration_item();
			       
			        // Single declaration
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(173);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Declaration_listContext(_parentctx, _parentState);
					_localctx.d = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_declaration_list);
					setState(166);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(167);
					match(COMMA);
					setState(168);
					declaration_item();

					                  // Declaration list processing
					              
					}
					} 
				}
				setState(175);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Declaration_itemContext extends ParserRuleContext {
		public string ruleName;
		public Token ID;
		public Token CONST_INT;
		public Token RTHIRD;
		public TerminalNode ID() { return getToken(C2105047Parser.ID, 0); }
		public TerminalNode LTHIRD() { return getToken(C2105047Parser.LTHIRD, 0); }
		public TerminalNode CONST_INT() { return getToken(C2105047Parser.CONST_INT, 0); }
		public TerminalNode RTHIRD() { return getToken(C2105047Parser.RTHIRD, 0); }
		public Declaration_itemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_item; }
	}

	public final Declaration_itemContext declaration_item() throws RecognitionException {
		Declaration_itemContext _localctx = new Declaration_itemContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_declaration_item);
		try {
			setState(183);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(176);
				((Declaration_itemContext)_localctx).ID = match(ID);

				        int hasError = 0;
				        lineCount = ((Declaration_itemContext)_localctx).ID->getLine();
				        
				        string varType = getTypeName(dynamic_cast<Var_declarationContext*>(_ctx->parent->parent)->t);
				        SymbolInfo* symbol = new SymbolInfo((((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null), varType);

				        //cout << "DEBUG: Declaring variable " << (((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null) << " in scope " << symbolTable.getCurrentScope()->getNestedId() << endl;
				        
				        if (!symbolTable.insert(*symbol, errorFile)) 
				        {
				            hasError = 1;
				            syntaxErrorCount++;
				            writeIntoErrorFile("Error at line " + to_string(((Declaration_itemContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null));
				        } 
				        else 
				        {
				            // Generate variable declaration
				            if (symbolTable.getCurrentScope()->getNestedId() == "1") 
				            {
				                // Global scope
				                generateGlobalVariable((((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null), varType);
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
				                    writeCode("\t;Source code line: " + to_string(lineCount) + " allocating 2 bytes for local variable " + (((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null)); // ASMC: Comment for local variable allocation
				                    writeCode("\tPUSH BX"); // ASM: Allocate 2 bytes on stack for local variable (BX value irrelevant)
				                }
				            }
				        }
				        
				        ((Declaration_itemContext)_localctx).ruleName =  "ID";
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(178);
				((Declaration_itemContext)_localctx).ID = match(ID);
				setState(179);
				match(LTHIRD);
				setState(180);
				((Declaration_itemContext)_localctx).CONST_INT = match(CONST_INT);
				setState(181);
				((Declaration_itemContext)_localctx).RTHIRD = match(RTHIRD);

				        int hasError = 0;
				        lineCount = ((Declaration_itemContext)_localctx).RTHIRD->getLine();
				        
				        string varType = getTypeName(dynamic_cast<Var_declarationContext*>(_ctx->parent->parent)->t);   // dec_item <- dec_list <- var_declaration
				        SymbolInfo* symbol = new SymbolInfo((((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null), varType);
				        symbol->setIsArray(true);
				        symbol->setArraySize(stoi((((Declaration_itemContext)_localctx).CONST_INT!=null?((Declaration_itemContext)_localctx).CONST_INT.getText():null)));
				        
				        if (!symbolTable.insert(*symbol, errorFile)) 
				        {
				            hasError = 1;
				            syntaxErrorCount++;
				            writeIntoErrorFile("Error at line " + to_string(((Declaration_itemContext)_localctx).CONST_INT->getLine()) + ": Multiple declaration of " + (((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null));
				        } 
				        else 
				        {
				            // Generate array declaration
				            if (symbolTable.getCurrentScope()->getNestedId() == "1") 
				            {
				                // Global array - generate global variable
				                generateGlobalVariable((((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null), varType, stoi((((Declaration_itemContext)_localctx).CONST_INT!=null?((Declaration_itemContext)_localctx).CONST_INT.getText():null)));
				            } 
				            else 
				            {
				                // Local array - reserve stack space
				                if (syntaxErrorCount == 0) 
				                {
				                    int arraySize = stoi((((Declaration_itemContext)_localctx).CONST_INT!=null?((Declaration_itemContext)_localctx).CONST_INT.getText():null));
				                    writeCode("\t;Source code line: " + to_string(lineCount) + " allocating " + to_string(arraySize * 2) + " bytes for array " + (((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null)); // ASMC: Comment for array allocation
				                    writeCode("\tSUB SP, " + to_string(arraySize * 2)); // Reserve space for array // ASM: Allocate stack space for local array (arraySize * 2 bytes)
				                }
				            }
				        }
				        
				        ((Declaration_itemContext)_localctx).ruleName =  "ID LTHIRD CONST_INT RTHIRD";
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementsContext extends ParserRuleContext {
		public StatementsContext s;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
	}

	public final StatementsContext statements() throws RecognitionException {
		return statements(0);
	}

	private StatementsContext statements(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StatementsContext _localctx = new StatementsContext(_ctx, _parentState);
		StatementsContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_statements, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(186);
			statement();

			        // Single statement
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(195);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new StatementsContext(_parentctx, _parentState);
					_localctx.s = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_statements);
					setState(189);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(190);
					statement();

					                  // Multiple statements
					              
					}
					} 
				}
				setState(197);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public Expression_statementContext es1;
		public Expression_statementContext es2;
		public ExpressionContext expression;
		public StatementContext statement;
		public StatementContext s1;
		public StatementContext s2;
		public Token ID;
		public Token SEMICOLON;
		public Var_declarationContext var_declaration() {
			return getRuleContext(Var_declarationContext.class,0);
		}
		public List<Expression_statementContext> expression_statement() {
			return getRuleContexts(Expression_statementContext.class);
		}
		public Expression_statementContext expression_statement(int i) {
			return getRuleContext(Expression_statementContext.class,i);
		}
		public Compound_statementContext compound_statement() {
			return getRuleContext(Compound_statementContext.class,0);
		}
		public TerminalNode FOR() { return getToken(C2105047Parser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(C2105047Parser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(C2105047Parser.RPAREN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode IF() { return getToken(C2105047Parser.IF, 0); }
		public TerminalNode ELSE() { return getToken(C2105047Parser.ELSE, 0); }
		public TerminalNode WHILE() { return getToken(C2105047Parser.WHILE, 0); }
		public TerminalNode PRINTLN() { return getToken(C2105047Parser.PRINTLN, 0); }
		public TerminalNode ID() { return getToken(C2105047Parser.ID, 0); }
		public TerminalNode SEMICOLON() { return getToken(C2105047Parser.SEMICOLON, 0); }
		public TerminalNode RETURN() { return getToken(C2105047Parser.RETURN, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_statement);
		try {
			setState(261);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(198);
				var_declaration();

				    // Variable declaration statement

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(201);
				expression_statement();

				    // Expression statement

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(204);
				compound_statement();

				    // Compound statement

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(207);
				match(FOR);
				setState(208);
				match(LPAREN);

				    inControlStructure = true;

				setState(210);
				((StatementContext)_localctx).es1 = expression_statement();

				    lineCount = (((StatementContext)_localctx).es1!=null?(((StatementContext)_localctx).es1.stop):null)->getLine();
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


				    inControlStructure = true;

				setState(213);
				((StatementContext)_localctx).es2 = expression_statement();

				    lineCount = (((StatementContext)_localctx).es2!=null?(((StatementContext)_localctx).es2.stop):null)->getLine();
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


				    // Set flag to prevent increment double code generation in 'variable INCOP' rule
				    inControlStructure = true;

				setState(216);
				((StatementContext)_localctx).expression = expression();

				    // Increment expression ta store kore rakhi but ekhoni code generate korbo na er jonno
				    string incrementExpr = (((StatementContext)_localctx).expression!=null?_input.getText(((StatementContext)_localctx).expression.start,((StatementContext)_localctx).expression.stop):null);
				    inControlStructure = false;

				setState(218);
				match(RPAREN);
				setState(219);
				((StatementContext)_localctx).statement = statement();

				    lineCount = (((StatementContext)_localctx).statement!=null?(((StatementContext)_localctx).statement.stop):null)->getLine();
				    
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
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(222);
				match(IF);
				setState(223);
				match(LPAREN);
				setState(224);
				((StatementContext)_localctx).expression = expression();
				setState(225);
				match(RPAREN);

				    lineCount = (((StatementContext)_localctx).expression!=null?(((StatementContext)_localctx).expression.stop):null)->getLine();
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

				setState(227);
				statement();

				    if (syntaxErrorCount == 0) 
				    {
				        writeCode(ifEnd + ":"); // ASM: IF statement end label
				    }

				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(230);
				match(IF);
				setState(231);
				match(LPAREN);
				setState(232);
				((StatementContext)_localctx).expression = expression();
				setState(233);
				match(RPAREN);

				    lineCount = (((StatementContext)_localctx).expression!=null?(((StatementContext)_localctx).expression.stop):null)->getLine();
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

				setState(235);
				((StatementContext)_localctx).s1 = statement();
				setState(236);
				match(ELSE);

				    // Jump to end after IF part
				    if (syntaxErrorCount == 0) 
				    {
				        writeCode("\tJMP " + ifEnd); // ASM: Jump over ELSE part after IF execution
				        writeCode(elsePart + ":"); // ASM: ELSE body entry label
				    }

				setState(238);
				((StatementContext)_localctx).s2 = statement();

				    lineCount = (((StatementContext)_localctx).s2!=null?(((StatementContext)_localctx).s2.stop):null)->getLine();
				    
				    // IF-ELSE end
				    if (syntaxErrorCount == 0) 
				    {
				        writeCode(ifEnd + ":"); // ASM: IF-ELSE statement end label
				    }

				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(241);
				match(WHILE);
				setState(242);
				match(LPAREN);

				    string loopStart = generateLabel();
				    string loopEnd = generateLabel();
				    string loopBody = generateLabel();
				    
				    if (syntaxErrorCount == 0) 
				    {
				        writeCode(loopStart + ":"); // ASM: WHILE loop start label for condition checking //label(begin)
				    }

				setState(244);
				((StatementContext)_localctx).expression = expression();

				    lineCount = (((StatementContext)_localctx).expression!=null?(((StatementContext)_localctx).expression.stop):null)->getLine();
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

				setState(246);
				match(RPAREN);
				setState(247);
				((StatementContext)_localctx).statement = statement();

				    lineCount = (((StatementContext)_localctx).statement!=null?(((StatementContext)_localctx).statement.stop):null)->getLine();
				    
				    // WHILE loop end
				    if (syntaxErrorCount == 0) 
				    {
				        writeCode("\tJMP " + loopStart); // ASM: Jump back to condition check // gen 'goto' begin
				        writeCode(loopEnd + ":"); // ASM: WHILE loop exit label
				    }

				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(250);
				match(PRINTLN);
				setState(251);
				match(LPAREN);
				setState(252);
				((StatementContext)_localctx).ID = match(ID);
				setState(253);
				match(RPAREN);
				setState(254);
				((StatementContext)_localctx).SEMICOLON = match(SEMICOLON);

				    lineCount = ((StatementContext)_localctx).SEMICOLON->getLine();
				    
				    SymbolInfo* symbol = symbolTable.lookUp((((StatementContext)_localctx).ID!=null?((StatementContext)_localctx).ID.getText():null), errorFile);
				    if (!symbol) 
				    {
				        //writeIntoErrorFile("EKHANE DHORA GESE PRINTLN ID");
				        writeIntoErrorFile("Error at line " + to_string(((StatementContext)_localctx).SEMICOLON->getLine()) + ": Undeclared variable " + (((StatementContext)_localctx).ID!=null?((StatementContext)_localctx).ID.getText():null));
				        syntaxErrorCount++;
				    } 
				    else 
				    {
				        if (syntaxErrorCount == 0) 
				        {
				            if (symbol->getIsGlobal()) 
				            {
				                writeCode("\t;Source code line: " + to_string(lineCount) + " printing global variable " + (((StatementContext)_localctx).ID!=null?((StatementContext)_localctx).ID.getText():null)); // ASMC: Comment for print operation
				                writeCode("\tPUSH " + (((StatementContext)_localctx).ID!=null?((StatementContext)_localctx).ID.getText():null)); // ASM: Push global variable value as parameter for print
				            } 
				            else 
				            {
				                int offset = symbol->getStackOffset();
				                writeCode("\t;Source code line: " + to_string(lineCount) + " printing local variable " + (((StatementContext)_localctx).ID!=null?((StatementContext)_localctx).ID.getText():null)); // ASMC: Comment for print operation
				                writeCode("\tPUSH [BP+" + to_string(offset) + "]"); // Push value for printing // ASM: Push local variable value as parameter for print
				            }
				            writeCode("\tCALL print_output"); // ASM: Call print procedure to display the value
				        }
				    }

				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(256);
				match(RETURN);
				setState(257);
				expression();
				setState(258);
				((StatementContext)_localctx).SEMICOLON = match(SEMICOLON);

				    lineCount = ((StatementContext)_localctx).SEMICOLON->getLine();
				    hasReturnStatement = true;
				    
				    if (currentFunctionReturnType == "VOID") 
				    {
				        writeIntoErrorFile("Error at line " + to_string(((StatementContext)_localctx).SEMICOLON->getLine()) + ": Cannot return value from function " + currentFunctionName + " with void return type");      
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
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression_statementContext extends ParserRuleContext {
		public Token SEMICOLON;
		public TerminalNode SEMICOLON() { return getToken(C2105047Parser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Expression_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_statement; }
	}

	public final Expression_statementContext expression_statement() throws RecognitionException {
		Expression_statementContext _localctx = new Expression_statementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expression_statement);
		try {
			setState(269);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SEMICOLON:
				enterOuterAlt(_localctx, 1);
				{
				setState(263);
				((Expression_statementContext)_localctx).SEMICOLON = match(SEMICOLON);

				        lineCount = ((Expression_statementContext)_localctx).SEMICOLON->getLine();
				    
				}
				break;
			case LPAREN:
			case ADDOP:
			case NOT:
			case ID:
			case CONST_INT:
			case CONST_FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(265);
				expression();
				setState(266);
				((Expression_statementContext)_localctx).SEMICOLON = match(SEMICOLON);

				        lineCount = ((Expression_statementContext)_localctx).SEMICOLON->getLine();
				        // Only pop if NOT in a control structure context
				        if (syntaxErrorCount == 0 && !inControlStructure) 
				        {
				            writeCode("\t;Source code line: " + to_string(lineCount) + " cleaning expression result from stack"); // ASMC: Clean up unused expression value to prevent stack problems
				            writeCode("\tPOP AX"); // ASM: Remove unused expression result from stack 
				        }
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableContext extends ParserRuleContext {
		public string type;
		public bool hasIndexError;
		public string varName;
		public Token ID;
		public ExpressionContext expression;
		public Token RTHIRD;
		public TerminalNode ID() { return getToken(C2105047Parser.ID, 0); }
		public TerminalNode LTHIRD() { return getToken(C2105047Parser.LTHIRD, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RTHIRD() { return getToken(C2105047Parser.RTHIRD, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_variable);
		try {
			setState(279);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(271);
				((VariableContext)_localctx).ID = match(ID);

				        lineCount = ((VariableContext)_localctx).ID->getLine();
				        ((VariableContext)_localctx).varName =  (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null);  // Set the variable name
				        SymbolInfo* symbol = symbolTable.lookUp((((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null), errorFile);
				        if (!symbol) 
				        {
				            syntaxErrorCount++;
				            //writeIntoErrorFile("EKHANE DHORA GESE ID");
				            writeIntoErrorFile("Error at line " + to_string(((VariableContext)_localctx).ID->getLine()) + ": Undeclared variable " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null));
				            ((VariableContext)_localctx).type =  "UNKNOWN";
				        } 
				        else 
				        {
				            ((VariableContext)_localctx).type =  symbol->getIsArray() ? "ARRAY" : symbol->getType();
				            
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
				                        writeCode("\t;Source code line: " + to_string(lineCount) + " using global variable " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null)); // ASMC: Comment for global variable use
				                        writeCode("\tPUSH " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null)); // ASM: Push global variable value onto stack for expression use (i.e. PUSH x)
				                    } 
				                    else 
				                    {
				                        // Local variable/parameter - push value
				                        int offset = symbol->getStackOffset();
				                        cout << "DEBUG: Variable " << (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null) << " has offset: " << symbol->getStackOffset() << " isParameter: " << symbol->getIsParameter() << endl;
				                        writeCode("\t;Source code line: " + to_string(lineCount) + " using local variable " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null)); // ASMC: Comment for local variable use
				                        writeCode("\tPUSH [BP+"+ to_string(offset)+"]"); // ASM: Push local variable/parameter value onto stack
				                    }
				                }
				            }
				        }
				        
				        ((VariableContext)_localctx).hasIndexError =  false;
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(273);
				((VariableContext)_localctx).ID = match(ID);
				setState(274);
				match(LTHIRD);
				setState(275);
				((VariableContext)_localctx).expression = expression();
				setState(276);
				((VariableContext)_localctx).RTHIRD = match(RTHIRD);

				        lineCount = ((VariableContext)_localctx).RTHIRD->getLine();
				        SymbolInfo* symbol = symbolTable.lookUp((((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null), errorFile);
				        if (!symbol) 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((VariableContext)_localctx).RTHIRD->getLine()) + ": Undeclared variable " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null));
				            syntaxErrorCount++;
				            ((VariableContext)_localctx).type =  "UNKNOWN";
				            ((VariableContext)_localctx).hasIndexError =  false;
				        } 
				        else if (!symbol->getIsArray()) 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((VariableContext)_localctx).RTHIRD->getLine()) + ": " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null) + " not an array");
				            syntaxErrorCount++;
				            ((VariableContext)_localctx).type =  symbol->getType();
				            ((VariableContext)_localctx).hasIndexError =  true;
				        } 
				        else if (((VariableContext)_localctx).expression.type != "CONST_INT") 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((VariableContext)_localctx).RTHIRD->getLine()) + ": Expression inside third brackets not an integer");
				            syntaxErrorCount++;
				            ((VariableContext)_localctx).type =  symbol->getType();
				            ((VariableContext)_localctx).hasIndexError =  true;
				        } 
				        else 
				        {
				            ((VariableContext)_localctx).type =  symbol->getType();
				            ((VariableContext)_localctx).hasIndexError =  false;
				            
				            if (syntaxErrorCount == 0) 
				            {
				                writeCode("\t;Source code line: " + to_string(lineCount) + " accessing array element " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null)); // ASMC: Comment for array access
				                writeCode("\tPOP BX"); // ASM: Get array INDEX from stack, this INDEX was pushed from expression rule
				                writeCode("\tSHL BX, 1"); // ASM: Convert index to BYTE offset (multiply by 2)
				                
				                int offset = symbol->getStackOffset();
				                cout << "DEBUG: Array " << (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null) << " has offset: " << symbol->getStackOffset() << endl;
				                
				                if (offset == -1) 
				                {   // Global array
				                    writeCode("\t;Source code line: " + to_string(lineCount) + " loading global array element " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null)); // ASMC: Comment for global array access
				                    writeCode("\tMOV AX, " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null) + "[BX]"); // ASM: Load global array element using calculated offset(i.e. MOV AX, w[BX]), karon eta shurute DUP DW diyei kora 
				                } 
				                else 
				                {   // Local array
				                    writeCode("\t;Source code line: " + to_string(lineCount) + " loading local array element " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null)); // ASMC: Comment for local array access
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
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public string type;
		public Logic_expressionContext logic_expression;
		public VariableContext variable;
		public Token ASSIGNOP;
		public Logic_expressionContext logic_expression() {
			return getRuleContext(Logic_expressionContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode ASSIGNOP() { return getToken(C2105047Parser.ASSIGNOP, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_expression);
		try {
			setState(289);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(281);
				((ExpressionContext)_localctx).logic_expression = logic_expression();

				        ((ExpressionContext)_localctx).type =  ((ExpressionContext)_localctx).logic_expression.type;
				        lineCount = (((ExpressionContext)_localctx).logic_expression!=null?(((ExpressionContext)_localctx).logic_expression.start):null)->getLine();
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(284);
				((ExpressionContext)_localctx).variable = variable();
				setState(285);
				((ExpressionContext)_localctx).ASSIGNOP = match(ASSIGNOP);
				setState(286);
				((ExpressionContext)_localctx).logic_expression = logic_expression();

				        lineCount = ((ExpressionContext)_localctx).ASSIGNOP->getLine();
				        
				        if (((ExpressionContext)_localctx).variable.type == "ARRAY") 
				        {
				            syntaxErrorCount++;
				            writeIntoErrorFile("Error at line " + to_string(((ExpressionContext)_localctx).ASSIGNOP->getLine()) + ": Type mismatch, " + (((ExpressionContext)_localctx).variable!=null?_input.getText(((ExpressionContext)_localctx).variable.start,((ExpressionContext)_localctx).variable.stop):null) + " is an array");
				        } 
				        else if (((ExpressionContext)_localctx).variable.type != "UNKNOWN" && ((ExpressionContext)_localctx).logic_expression.type != "UNKNOWN" && !((ExpressionContext)_localctx).variable.hasIndexError) 
				        {
				            if (!isTypeCompatible(((ExpressionContext)_localctx).variable.type, ((ExpressionContext)_localctx).logic_expression.type)) 
				            {
				                syntaxErrorCount++;
				                writeIntoErrorFile("Error at line " + to_string(((ExpressionContext)_localctx).ASSIGNOP->getLine()) + ": Type Mismatch");
				            }
				        }
				        
				        ((ExpressionContext)_localctx).type =  ((ExpressionContext)_localctx).variable.type;
				        
				        // Generate assignment code
				        if (syntaxErrorCount == 0) 
				        {
				            writeCode("\t;Source code line: " + to_string(lineCount) + " assigning value to variable " + (((ExpressionContext)_localctx).variable!=null?_input.getText(((ExpressionContext)_localctx).variable.start,((ExpressionContext)_localctx).variable.stop):null)); // ASMC: Comment for assignment
				            writeCode("\tPOP AX"); // ASM: Get assignment value (right hand side value) from stack
				            
				            string varName;
				            if ((((ExpressionContext)_localctx).variable!=null?_input.getText(((ExpressionContext)_localctx).variable.start,((ExpressionContext)_localctx).variable.stop):null).find('[') != string::npos) 
				            {
				                varName = (((ExpressionContext)_localctx).variable!=null?_input.getText(((ExpressionContext)_localctx).variable.start,((ExpressionContext)_localctx).variable.stop):null).substr(0, (((ExpressionContext)_localctx).variable!=null?_input.getText(((ExpressionContext)_localctx).variable.start,((ExpressionContext)_localctx).variable.stop):null).find('['));
				            } 
				            else 
				            {
				                varName = (((ExpressionContext)_localctx).variable!=null?_input.getText(((ExpressionContext)_localctx).variable.start,((ExpressionContext)_localctx).variable.stop):null);
				            }
				            
				            SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
				            if (!symbol) 
				            {
				                writeIntoErrorFile("Error at line " + to_string(((ExpressionContext)_localctx).ASSIGNOP->getLine()) + ": Undeclared variable " + varName);
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
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Logic_expressionContext extends ParserRuleContext {
		public string type;
		public bool hasArithmeticError;
		public Rel_expressionContext rel_expression;
		public Rel_expressionContext r1;
		public Token LOGICOP;
		public Rel_expressionContext r2;
		public List<Rel_expressionContext> rel_expression() {
			return getRuleContexts(Rel_expressionContext.class);
		}
		public Rel_expressionContext rel_expression(int i) {
			return getRuleContext(Rel_expressionContext.class,i);
		}
		public TerminalNode LOGICOP() { return getToken(C2105047Parser.LOGICOP, 0); }
		public Logic_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic_expression; }
	}

	public final Logic_expressionContext logic_expression() throws RecognitionException {
		Logic_expressionContext _localctx = new Logic_expressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_logic_expression);
		try {
			setState(299);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(291);
				((Logic_expressionContext)_localctx).rel_expression = rel_expression();

				        ((Logic_expressionContext)_localctx).type =  ((Logic_expressionContext)_localctx).rel_expression.type;
				        ((Logic_expressionContext)_localctx).hasArithmeticError =  ((Logic_expressionContext)_localctx).rel_expression.hasArithmeticError;
				        lineCount = (((Logic_expressionContext)_localctx).rel_expression!=null?(((Logic_expressionContext)_localctx).rel_expression.start):null)->getLine();
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(294);
				((Logic_expressionContext)_localctx).r1 = rel_expression();
				setState(295);
				((Logic_expressionContext)_localctx).LOGICOP = match(LOGICOP);
				setState(296);
				((Logic_expressionContext)_localctx).r2 = rel_expression();

				        lineCount = ((Logic_expressionContext)_localctx).LOGICOP->getLine();
				        ((Logic_expressionContext)_localctx).type =  "INT";
				        ((Logic_expressionContext)_localctx).hasArithmeticError =  ((Logic_expressionContext)_localctx).r1.hasArithmeticError || ((Logic_expressionContext)_localctx).r2.hasArithmeticError;
				        
				        // Generate only the labels we actually need
				        string logicOp = (((Logic_expressionContext)_localctx).LOGICOP!=null?((Logic_expressionContext)_localctx).LOGICOP.getText():null);
				        
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
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Rel_expressionContext extends ParserRuleContext {
		public string type;
		public bool hasArithmeticError;
		public Simple_expressionContext simple_expression;
		public Simple_expressionContext s1;
		public Token RELOP;
		public Simple_expressionContext s2;
		public List<Simple_expressionContext> simple_expression() {
			return getRuleContexts(Simple_expressionContext.class);
		}
		public Simple_expressionContext simple_expression(int i) {
			return getRuleContext(Simple_expressionContext.class,i);
		}
		public TerminalNode RELOP() { return getToken(C2105047Parser.RELOP, 0); }
		public Rel_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rel_expression; }
	}

	public final Rel_expressionContext rel_expression() throws RecognitionException {
		Rel_expressionContext _localctx = new Rel_expressionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_rel_expression);
		try {
			setState(309);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(301);
				((Rel_expressionContext)_localctx).simple_expression = simple_expression(0);

				        ((Rel_expressionContext)_localctx).type =  ((Rel_expressionContext)_localctx).simple_expression.type;
				        ((Rel_expressionContext)_localctx).hasArithmeticError =  ((Rel_expressionContext)_localctx).simple_expression.hasArithmeticError;
				        lineCount = (((Rel_expressionContext)_localctx).simple_expression!=null?(((Rel_expressionContext)_localctx).simple_expression.start):null)->getLine();
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(304);
				((Rel_expressionContext)_localctx).s1 = simple_expression(0);
				setState(305);
				((Rel_expressionContext)_localctx).RELOP = match(RELOP);
				setState(306);
				((Rel_expressionContext)_localctx).s2 = simple_expression(0);

				        lineCount = ((Rel_expressionContext)_localctx).RELOP->getLine();
				        ((Rel_expressionContext)_localctx).type =  "INT";
				        ((Rel_expressionContext)_localctx).hasArithmeticError =  ((Rel_expressionContext)_localctx).s1.hasArithmeticError || ((Rel_expressionContext)_localctx).s2.hasArithmeticError;
				        
				        string trueLabel = generateLabel();
				        string endLabel = generateLabel();
				        
				        if (syntaxErrorCount == 0) 
				        {
				            string relOp = (((Rel_expressionContext)_localctx).RELOP!=null?((Rel_expressionContext)_localctx).RELOP.getText():null);
				            
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
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Simple_expressionContext extends ParserRuleContext {
		public string type;
		public bool hasArithmeticError;
		public Simple_expressionContext s;
		public TermContext term;
		public Token ADDOP;
		public TermContext t;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TerminalNode ADDOP() { return getToken(C2105047Parser.ADDOP, 0); }
		public Simple_expressionContext simple_expression() {
			return getRuleContext(Simple_expressionContext.class,0);
		}
		public Simple_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_expression; }
	}

	public final Simple_expressionContext simple_expression() throws RecognitionException {
		return simple_expression(0);
	}

	private Simple_expressionContext simple_expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Simple_expressionContext _localctx = new Simple_expressionContext(_ctx, _parentState);
		Simple_expressionContext _prevctx = _localctx;
		int _startState = 36;
		enterRecursionRule(_localctx, 36, RULE_simple_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(312);
			((Simple_expressionContext)_localctx).term = term(0);

			        ((Simple_expressionContext)_localctx).type =  ((Simple_expressionContext)_localctx).term.type;
			        ((Simple_expressionContext)_localctx).hasArithmeticError =  ((Simple_expressionContext)_localctx).term.hasArithmeticError;
			        lineCount = (((Simple_expressionContext)_localctx).term!=null?(((Simple_expressionContext)_localctx).term.start):null)->getLine();
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(322);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Simple_expressionContext(_parentctx, _parentState);
					_localctx.s = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_simple_expression);
					setState(315);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(316);
					((Simple_expressionContext)_localctx).ADDOP = match(ADDOP);
					setState(317);
					((Simple_expressionContext)_localctx).t = ((Simple_expressionContext)_localctx).term = term(0);

					                  lineCount = ((Simple_expressionContext)_localctx).ADDOP->getLine();
					                  ((Simple_expressionContext)_localctx).type =  (((Simple_expressionContext)_localctx).s.type == "FLOAT" || ((Simple_expressionContext)_localctx).t.type == "FLOAT") ? "FLOAT" : "INT";
					                  ((Simple_expressionContext)_localctx).hasArithmeticError =  ((Simple_expressionContext)_localctx).s.hasArithmeticError || ((Simple_expressionContext)_localctx).t.hasArithmeticError;
					                  
					                  // Generate addition/subtraction
					                  if (syntaxErrorCount == 0) 
					                  {
					                      string addOp = (((Simple_expressionContext)_localctx).ADDOP!=null?((Simple_expressionContext)_localctx).ADDOP.getText():null);
					                      
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
					} 
				}
				setState(324);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public string type;
		public bool hasArithmeticError;
		public TermContext t;
		public Unary_expressionContext unary_expression;
		public Token MULOP;
		public Unary_expressionContext u;
		public Unary_expressionContext unary_expression() {
			return getRuleContext(Unary_expressionContext.class,0);
		}
		public TerminalNode MULOP() { return getToken(C2105047Parser.MULOP, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	}

	public final TermContext term() throws RecognitionException {
		return term(0);
	}

	private TermContext term(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TermContext _localctx = new TermContext(_ctx, _parentState);
		TermContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_term, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(326);
			((TermContext)_localctx).unary_expression = unary_expression();

			        ((TermContext)_localctx).type =  ((TermContext)_localctx).unary_expression.type;
			        ((TermContext)_localctx).hasArithmeticError =  false;
			        lineCount = (((TermContext)_localctx).unary_expression!=null?(((TermContext)_localctx).unary_expression.start):null)->getLine();
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(336);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TermContext(_parentctx, _parentState);
					_localctx.t = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_term);
					setState(329);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(330);
					((TermContext)_localctx).MULOP = match(MULOP);
					setState(331);
					((TermContext)_localctx).u = ((TermContext)_localctx).unary_expression = unary_expression();

					                  lineCount = ((TermContext)_localctx).MULOP->getLine();
					                  
					                  string mulOp = (((TermContext)_localctx).MULOP!=null?((TermContext)_localctx).MULOP.getText():null);
					                  
					                  if (mulOp == "%" && (((TermContext)_localctx).u!=null?_input.getText(((TermContext)_localctx).u.start,((TermContext)_localctx).u.stop):null) == "0") 
					                  {
					                      syntaxErrorCount++;
					                      writeIntoErrorFile("Error at line " + to_string(((TermContext)_localctx).MULOP->getLine()) + ": Modulus by Zero");
					                      ((TermContext)_localctx).hasArithmeticError =  true;
					                  } 
					                  //else if (mulOp == "%" && (((TermContext)_localctx).t.type != "CONST_INT" || ((TermContext)_localctx).u.type != "CONST_INT")) 
					                  else if (mulOp == "%" && (!isTypeCompatible("INT", ((TermContext)_localctx).t.type) || !isTypeCompatible("INT", ((TermContext)_localctx).u.type)))
					                  {
					                      syntaxErrorCount++;
					                      writeIntoErrorFile("Error at line " + to_string(((TermContext)_localctx).MULOP->getLine()) + ": Non-Integer operand on modulus operator");
					                      ((TermContext)_localctx).hasArithmeticError =  true;
					                  } 
					                  else 
					                  {
					                      ((TermContext)_localctx).hasArithmeticError =  ((TermContext)_localctx).t.hasArithmeticError;
					                  }
					                  
					                  ((TermContext)_localctx).type =  (((TermContext)_localctx).t.type == "FLOAT" || ((TermContext)_localctx).u.type == "FLOAT") ? "FLOAT" : "INT";
					                  
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
					} 
				}
				setState(338);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Unary_expressionContext extends ParserRuleContext {
		public string type;
		public Token ADDOP;
		public Unary_expressionContext u;
		public Token NOT;
		public FactorContext factor;
		public TerminalNode ADDOP() { return getToken(C2105047Parser.ADDOP, 0); }
		public Unary_expressionContext unary_expression() {
			return getRuleContext(Unary_expressionContext.class,0);
		}
		public TerminalNode NOT() { return getToken(C2105047Parser.NOT, 0); }
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public Unary_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unary_expression; }
	}

	public final Unary_expressionContext unary_expression() throws RecognitionException {
		Unary_expressionContext _localctx = new Unary_expressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_unary_expression);
		try {
			setState(350);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ADDOP:
				enterOuterAlt(_localctx, 1);
				{
				setState(339);
				((Unary_expressionContext)_localctx).ADDOP = match(ADDOP);
				setState(340);
				((Unary_expressionContext)_localctx).u = unary_expression();

				        lineCount = ((Unary_expressionContext)_localctx).ADDOP->getLine();
				        ((Unary_expressionContext)_localctx).type =  ((Unary_expressionContext)_localctx).u.type;
				        
				        // Generate unary plus/minus
				        if (syntaxErrorCount == 0 && (((Unary_expressionContext)_localctx).ADDOP!=null?((Unary_expressionContext)_localctx).ADDOP.getText():null) == "-") 
				        {
				            writeCode("\t;Source code line: " + to_string(lineCount) + " evaluating unary minus operation"); // ASMC: Comment for unary minus operation
				            writeCode("\tPOP AX"); // ASM: Get operand from stack
				            writeCode("\tNEG AX"); // ASM: Negate the value (two's complement)
				            writeCode("\tPUSH AX"); // ASM: Push negated result onto stack
				        }
				        // Unary plus does nothing
				    
				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(343);
				((Unary_expressionContext)_localctx).NOT = match(NOT);
				setState(344);
				((Unary_expressionContext)_localctx).u = unary_expression();

				        lineCount = ((Unary_expressionContext)_localctx).NOT->getLine();
				        ((Unary_expressionContext)_localctx).type =  "INT";
				        
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
				break;
			case LPAREN:
			case ID:
			case CONST_INT:
			case CONST_FLOAT:
				enterOuterAlt(_localctx, 3);
				{
				setState(347);
				((Unary_expressionContext)_localctx).factor = factor();

				        ((Unary_expressionContext)_localctx).type =  ((Unary_expressionContext)_localctx).factor.type;
				        lineCount = (((Unary_expressionContext)_localctx).factor!=null?(((Unary_expressionContext)_localctx).factor.start):null)->getLine();
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FactorContext extends ParserRuleContext {
		public string type;
		public VariableContext variable;
		public Token ID;
		public Argument_listContext a;
		public Token RPAREN;
		public Token LPAREN;
		public ExpressionContext expression;
		public Token CONST_INT;
		public Token CONST_FLOAT;
		public Token INCOP;
		public Token DECOP;
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode ID() { return getToken(C2105047Parser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(C2105047Parser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(C2105047Parser.RPAREN, 0); }
		public Argument_listContext argument_list() {
			return getRuleContext(Argument_listContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CONST_INT() { return getToken(C2105047Parser.CONST_INT, 0); }
		public TerminalNode CONST_FLOAT() { return getToken(C2105047Parser.CONST_FLOAT, 0); }
		public TerminalNode INCOP() { return getToken(C2105047Parser.INCOP, 0); }
		public TerminalNode DECOP() { return getToken(C2105047Parser.DECOP, 0); }
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_factor);
		try {
			setState(378);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(352);
				((FactorContext)_localctx).variable = variable();

				        ((FactorContext)_localctx).type =  ((FactorContext)_localctx).variable.type;
				        lineCount = (((FactorContext)_localctx).variable!=null?(((FactorContext)_localctx).variable.start):null)->getLine();

				        string varName;
				        if ((((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('[') != string::npos) 
				        {
				            varName = (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).substr(0, (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('['));
				        } 
				        else 
				        {
				            varName = (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null);
				        }

				        SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
				        if (!symbol)
				        {
				            writeIntoErrorFile("Error at line " + to_string((((FactorContext)_localctx).variable!=null?(((FactorContext)_localctx).variable.start):null)->getLine()) + ": Undeclared variable " + varName);
				            syntaxErrorCount++;
				            ((FactorContext)_localctx).type =  "UNKNOWN";
				        } 
				        else
				        {
				            int arraySize = symbol->getIsArray() ? symbol->getArraySize() : 0;
				                
				            // if array size is greater than 0 and variable has brackets, then pop bx , bx will catch the address of the array element, which is unnecessary here
				            if (arraySize > 0 && (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('[') != string::npos) 
				            {   
				                writeCode("\t;Source code line: " + to_string(lineCount) + " cleanup address from stack for " + (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null)); // ASMC: Clanup element address
				                writeCode("\tPOP BX"); //ASMN: e.g. i=w[0], erokom array element er khetre prothome value then address ta push kori 'variable' rule e.  'factor' rule e ei address r lagbe na tai cleanup korsi
				            } 
				        }

				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(355);
				((FactorContext)_localctx).ID = match(ID);
				setState(356);
				match(LPAREN);
				setState(357);
				((FactorContext)_localctx).a = argument_list();
				setState(358);
				((FactorContext)_localctx).RPAREN = match(RPAREN);

				        lineCount = ((FactorContext)_localctx).RPAREN->getLine();
				        SymbolInfo* symbol = symbolTable.lookUp((((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null), errorFile);
				        
				        if (!symbol) 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": Undefined function " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null));
				            syntaxErrorCount++;
				            ((FactorContext)_localctx).type =  "UNKNOWN";
				        } 
				        else if (!symbol->getIsFunction()) 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null) + " is not a function");
				            syntaxErrorCount++;
				            ((FactorContext)_localctx).type =  "UNKNOWN";
				        } 
				        else 
				        {
				            ((FactorContext)_localctx).type =  symbol->getReturnType();
				            
				            // Check function call validty and generate call
				            if (syntaxErrorCount == 0) 
				            {
				                writeCode("\t;Source code line: " + to_string(lineCount) + " calling function " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null)); // ASMC: Comment for function call
				                writeCode("\tCALL " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null)); // ASM: Call function (arguments already on stack)
				                writeCode("\tPUSH AX"); // ASM: Push function return value from AX onto stack
				            }
				        }
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(361);
				((FactorContext)_localctx).LPAREN = match(LPAREN);
				setState(362);
				((FactorContext)_localctx).expression = expression();
				setState(363);
				match(RPAREN);

				        ((FactorContext)_localctx).type =  ((FactorContext)_localctx).expression.type;
				        lineCount = ((FactorContext)_localctx).LPAREN->getLine();
				        // Expression result already on stack
				    
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(366);
				((FactorContext)_localctx).CONST_INT = match(CONST_INT);

				        ((FactorContext)_localctx).type =  "CONST_INT";
				        lineCount = ((FactorContext)_localctx).CONST_INT->getLine();
				        
				        // Generate constant loading
				        if (syntaxErrorCount == 0) 
				        {
				            writeCode("\t;Source code line: " + to_string(lineCount) + " loading integer constant " + (((FactorContext)_localctx).CONST_INT!=null?((FactorContext)_localctx).CONST_INT.getText():null)); // ASMC: Comment for constant loading
				            writeCode("\tPUSH " + (((FactorContext)_localctx).CONST_INT!=null?((FactorContext)_localctx).CONST_INT.getText():null)); // ASM: Push integer constant value onto stack
				        }
				    
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(368);
				((FactorContext)_localctx).CONST_FLOAT = match(CONST_FLOAT);

				        ((FactorContext)_localctx).type =  "CONST_FLOAT";
				        lineCount = ((FactorContext)_localctx).CONST_FLOAT->getLine();
				        
				        // Generate float constant (no need for it in this offline)
				        // if (syntaxErrorCount == 0) 
				        // {
				        //     writeCode("\tPUSH " + (((FactorContext)_localctx).CONST_FLOAT!=null?((FactorContext)_localctx).CONST_FLOAT.getText():null));
				        // }
				    
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(370);
				((FactorContext)_localctx).variable = variable();
				setState(371);
				((FactorContext)_localctx).INCOP = match(INCOP);

				        ((FactorContext)_localctx).type =  ((FactorContext)_localctx).variable.type;
				        lineCount = ((FactorContext)_localctx).INCOP->getLine();
				        
				        // Only generate code if NOT in control structure
				        if (syntaxErrorCount == 0 && !inControlStructure) 
				        {
				            // Extract variable name for lookup
				            string varName;
				            if ((((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('[') != string::npos) 
				            {
				                varName = (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).substr(0, (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('['));
				            } 
				            else 
				            {
				                varName = (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null);
				            }
				            SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
				            if (!symbol) 
				            {
				                writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).INCOP->getLine()) + ": Undeclared variable " + varName);
				                syntaxErrorCount++;
				            } 
				            else
				            {
				                // Get array size to determine stack operations
				                int arraySize = symbol->getIsArray() ? symbol->getArraySize() : 0;
				                
				                // if array size is greater than 0 and variable has brackets, then pop bx and pop ax, otherwise only pop ax
				                if (arraySize > 0 && (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('[') != string::npos) 
				                {

				                    writeCode("\tPOP BX"); //ASMN: Array element's address, lagbe ekhon, unlike just VARIABLE
				                    writeCode("\tMOV AX, [BX]"); // ASMN: copy in AX the value of the array element to perfomr INC
				                } 
				                else 
				                {
				                    writeCode("\t;Source code line: " + to_string(lineCount) + " getting value in AX and saving original value for post-incrementing variable " + (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null)); // ASMC: Comment for saving original value
				                    writeCode("\tPOP AX"); // ASM: Get variable's current value from stack, push kora chilo 'variable' rule e, ekhon eta AX e anchi
				                    writeCode("\tPUSH AX"); // ASM: Save original value in AX to perform INC, i.e. result=b++ erokom er jonno UN-incremented value tai save korchi, eta pop hobe 'expression' rule e, jekhane assignment hobe
				                }
				                writeCode("\t;Source code line: " + to_string(lineCount) + " actually incrementing value of variable " + (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null)); // ASMC: Comment for variable increment
				                writeCode("\tINC AX"); // ASM: Increment the value

				                // Extract variable name for lookup
				                string varName;
				                if ((((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('[') != string::npos) 
				                {
				                    varName = (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).substr(0, (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('['));
				                } 
				                else 
				                {
				                    varName = (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null);
				                }

				                SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
				                if (!symbol) 
				                {
				                    writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).INCOP->getLine()) + ": Undeclared variable " + varName);
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
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(374);
				((FactorContext)_localctx).variable = variable();
				setState(375);
				((FactorContext)_localctx).DECOP = match(DECOP);

				        ((FactorContext)_localctx).type =  ((FactorContext)_localctx).variable.type;
				        lineCount = ((FactorContext)_localctx).DECOP->getLine();
				        
				        // Only generate code if NOT in control structure
				        if (syntaxErrorCount == 0 && !inControlStructure) 
				        {
				            // Extract variable name for lookup
				            string varName;
				            if ((((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('[') != string::npos) 
				            {
				                varName = (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).substr(0, (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('['));
				            } 
				            else 
				            {
				                varName = (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null);
				            }
				            SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
				            if (!symbol) 
				            {
				                writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).DECOP->getLine()) + ": Undeclared variable " + varName);
				                syntaxErrorCount++;
				            } 
				            else
				            {
				                // Get array size to determine stack operations
				                int arraySize = symbol->getIsArray() ? symbol->getArraySize() : 0;
				                
				                // if array size is greater than 0 and variable has brackets, then pop bx and pop ax, otherwise only pop ax
				                if (arraySize > 0 && (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('[') != string::npos) 
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
				                if ((((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('[') != string::npos) 
				                {
				                    varName = (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).substr(0, (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null).find('['));
				                } 
				                else 
				                {
				                    varName = (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null);
				                }

				                SymbolInfo* symbol = symbolTable.lookUp(varName, errorFile);
				                if (!symbol) 
				                {
				                    writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).DECOP->getLine()) + ": Undeclared variable " + varName);
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
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Argument_listContext extends ParserRuleContext {
		public string argTypes;
		public ArgumentsContext arguments;
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public Argument_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument_list; }
	}

	public final Argument_listContext argument_list() throws RecognitionException {
		Argument_listContext _localctx = new Argument_listContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_argument_list);
		try {
			setState(384);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
			case ADDOP:
			case NOT:
			case ID:
			case CONST_INT:
			case CONST_FLOAT:
				enterOuterAlt(_localctx, 1);
				{
				setState(380);
				((Argument_listContext)_localctx).arguments = arguments(0);

				        ((Argument_listContext)_localctx).argTypes =  ((Argument_listContext)_localctx).arguments.argTypes;
				    
				}
				break;
			case RPAREN:
				enterOuterAlt(_localctx, 2);
				{

				        ((Argument_listContext)_localctx).argTypes =  "";
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentsContext extends ParserRuleContext {
		public string argTypes;
		public ArgumentsContext a;
		public Logic_expressionContext logic_expression;
		public Token COMMA;
		public Logic_expressionContext l;
		public Logic_expressionContext logic_expression() {
			return getRuleContext(Logic_expressionContext.class,0);
		}
		public TerminalNode COMMA() { return getToken(C2105047Parser.COMMA, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		return arguments(0);
	}

	private ArgumentsContext arguments(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, _parentState);
		ArgumentsContext _prevctx = _localctx;
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_arguments, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(387);
			((ArgumentsContext)_localctx).logic_expression = logic_expression();

			        lineCount = (((ArgumentsContext)_localctx).logic_expression!=null?(((ArgumentsContext)_localctx).logic_expression.start):null)->getLine();
			        ((ArgumentsContext)_localctx).argTypes =  ((ArgumentsContext)_localctx).logic_expression.type;
			        
			        // Argument already pushed by logic_expression
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(397);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArgumentsContext(_parentctx, _parentState);
					_localctx.a = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_arguments);
					setState(390);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(391);
					((ArgumentsContext)_localctx).COMMA = match(COMMA);
					setState(392);
					((ArgumentsContext)_localctx).l = ((ArgumentsContext)_localctx).logic_expression = logic_expression();

					                  lineCount = ((ArgumentsContext)_localctx).COMMA->getLine();
					                  ((ArgumentsContext)_localctx).argTypes =  ((ArgumentsContext)_localctx).a.argTypes + "," + ((ArgumentsContext)_localctx).l.type;
					                  
					                  // Arguments are already pushed by logic_expression
					              
					}
					} 
				}
				setState(399);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return program_sempred((ProgramContext)_localctx, predIndex);
		case 5:
			return parameter_list_sempred((Parameter_listContext)_localctx, predIndex);
		case 9:
			return declaration_list_sempred((Declaration_listContext)_localctx, predIndex);
		case 11:
			return statements_sempred((StatementsContext)_localctx, predIndex);
		case 18:
			return simple_expression_sempred((Simple_expressionContext)_localctx, predIndex);
		case 19:
			return term_sempred((TermContext)_localctx, predIndex);
		case 23:
			return arguments_sempred((ArgumentsContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean program_sempred(ProgramContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean parameter_list_sempred(Parameter_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean declaration_list_sempred(Declaration_listContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean statements_sempred(StatementsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean simple_expression_sempred(Simple_expressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean term_sempred(TermContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean arguments_sempred(ArgumentsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001!\u0191\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001<\b\u0001\n\u0001\f\u0001"+
		"?\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002J\b\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003\\\b\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		"o\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005y\b\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005\u0086\b\u0005"+
		"\n\u0005\f\u0005\u0089\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003"+
		"\u0006\u0094\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u00a1\b"+
		"\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0005\t\u00ac\b\t\n\t\f\t\u00af\t\t\u0001\n\u0001\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0003\n\u00b8\b\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005"+
		"\u000b\u00c2\b\u000b\n\u000b\f\u000b\u00c5\t\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u0106\b\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u010e\b\r\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0003\u000e\u0118\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f"+
		"\u0122\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u012c\b\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0003\u0011\u0136\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0005\u0012\u0141\b\u0012\n\u0012\f\u0012\u0144\t\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0005\u0013\u014f\b\u0013\n\u0013\f\u0013\u0152\t\u0013"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014"+
		"\u015f\b\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0003\u0015\u017b\b\u0015\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0003\u0016\u0181\b\u0016\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0005\u0017\u018c\b\u0017\n\u0017\f\u0017\u018f"+
		"\t\u0017\u0001\u0017\u0000\u0007\u0002\n\u0012\u0016$&.\u0018\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"+
		" \"$&(*,.\u0000\u0000\u019f\u00000\u0001\u0000\u0000\u0000\u00023\u0001"+
		"\u0000\u0000\u0000\u0004I\u0001\u0000\u0000\u0000\u0006[\u0001\u0000\u0000"+
		"\u0000\bn\u0001\u0000\u0000\u0000\nx\u0001\u0000\u0000\u0000\f\u0093\u0001"+
		"\u0000\u0000\u0000\u000e\u0095\u0001\u0000\u0000\u0000\u0010\u00a0\u0001"+
		"\u0000\u0000\u0000\u0012\u00a2\u0001\u0000\u0000\u0000\u0014\u00b7\u0001"+
		"\u0000\u0000\u0000\u0016\u00b9\u0001\u0000\u0000\u0000\u0018\u0105\u0001"+
		"\u0000\u0000\u0000\u001a\u010d\u0001\u0000\u0000\u0000\u001c\u0117\u0001"+
		"\u0000\u0000\u0000\u001e\u0121\u0001\u0000\u0000\u0000 \u012b\u0001\u0000"+
		"\u0000\u0000\"\u0135\u0001\u0000\u0000\u0000$\u0137\u0001\u0000\u0000"+
		"\u0000&\u0145\u0001\u0000\u0000\u0000(\u015e\u0001\u0000\u0000\u0000*"+
		"\u017a\u0001\u0000\u0000\u0000,\u0180\u0001\u0000\u0000\u0000.\u0182\u0001"+
		"\u0000\u0000\u000001\u0003\u0002\u0001\u000012\u0006\u0000\uffff\uffff"+
		"\u00002\u0001\u0001\u0000\u0000\u000034\u0006\u0001\uffff\uffff\u0000"+
		"45\u0003\u0004\u0002\u000056\u0006\u0001\uffff\uffff\u00006=\u0001\u0000"+
		"\u0000\u000078\n\u0002\u0000\u000089\u0003\u0004\u0002\u00009:\u0006\u0001"+
		"\uffff\uffff\u0000:<\u0001\u0000\u0000\u0000;7\u0001\u0000\u0000\u0000"+
		"<?\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000"+
		"\u0000>\u0003\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000@A\u0003"+
		"\u000e\u0007\u0000AB\u0006\u0002\uffff\uffff\u0000BJ\u0001\u0000\u0000"+
		"\u0000CD\u0003\u0006\u0003\u0000DE\u0006\u0002\uffff\uffff\u0000EJ\u0001"+
		"\u0000\u0000\u0000FG\u0003\b\u0004\u0000GH\u0006\u0002\uffff\uffff\u0000"+
		"HJ\u0001\u0000\u0000\u0000I@\u0001\u0000\u0000\u0000IC\u0001\u0000\u0000"+
		"\u0000IF\u0001\u0000\u0000\u0000J\u0005\u0001\u0000\u0000\u0000KL\u0003"+
		"\u0010\b\u0000LM\u0005\u001e\u0000\u0000MN\u0006\u0003\uffff\uffff\u0000"+
		"NO\u0005\u000e\u0000\u0000OP\u0003\n\u0005\u0000PQ\u0005\u000f\u0000\u0000"+
		"QR\u0005\u0014\u0000\u0000RS\u0006\u0003\uffff\uffff\u0000S\\\u0001\u0000"+
		"\u0000\u0000TU\u0003\u0010\b\u0000UV\u0005\u001e\u0000\u0000VW\u0005\u000e"+
		"\u0000\u0000WX\u0005\u000f\u0000\u0000XY\u0005\u0014\u0000\u0000YZ\u0006"+
		"\u0003\uffff\uffff\u0000Z\\\u0001\u0000\u0000\u0000[K\u0001\u0000\u0000"+
		"\u0000[T\u0001\u0000\u0000\u0000\\\u0007\u0001\u0000\u0000\u0000]^\u0003"+
		"\u0010\b\u0000^_\u0005\u001e\u0000\u0000_`\u0005\u000e\u0000\u0000`a\u0006"+
		"\u0004\uffff\uffff\u0000ab\u0003\n\u0005\u0000bc\u0005\u000f\u0000\u0000"+
		"cd\u0003\f\u0006\u0000de\u0006\u0004\uffff\uffff\u0000eo\u0001\u0000\u0000"+
		"\u0000fg\u0003\u0010\b\u0000gh\u0005\u001e\u0000\u0000hi\u0005\u000e\u0000"+
		"\u0000ij\u0006\u0004\uffff\uffff\u0000jk\u0005\u000f\u0000\u0000kl\u0003"+
		"\f\u0006\u0000lm\u0006\u0004\uffff\uffff\u0000mo\u0001\u0000\u0000\u0000"+
		"n]\u0001\u0000\u0000\u0000nf\u0001\u0000\u0000\u0000o\t\u0001\u0000\u0000"+
		"\u0000pq\u0006\u0005\uffff\uffff\u0000qr\u0003\u0010\b\u0000rs\u0005\u001e"+
		"\u0000\u0000st\u0006\u0005\uffff\uffff\u0000ty\u0001\u0000\u0000\u0000"+
		"uv\u0003\u0010\b\u0000vw\u0006\u0005\uffff\uffff\u0000wy\u0001\u0000\u0000"+
		"\u0000xp\u0001\u0000\u0000\u0000xu\u0001\u0000\u0000\u0000y\u0087\u0001"+
		"\u0000\u0000\u0000z{\n\u0004\u0000\u0000{|\u0005\u0015\u0000\u0000|}\u0003"+
		"\u0010\b\u0000}~\u0005\u001e\u0000\u0000~\u007f\u0006\u0005\uffff\uffff"+
		"\u0000\u007f\u0086\u0001\u0000\u0000\u0000\u0080\u0081\n\u0003\u0000\u0000"+
		"\u0081\u0082\u0005\u0015\u0000\u0000\u0082\u0083\u0003\u0010\b\u0000\u0083"+
		"\u0084\u0006\u0005\uffff\uffff\u0000\u0084\u0086\u0001\u0000\u0000\u0000"+
		"\u0085z\u0001\u0000\u0000\u0000\u0085\u0080\u0001\u0000\u0000\u0000\u0086"+
		"\u0089\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000\u0000\u0087"+
		"\u0088\u0001\u0000\u0000\u0000\u0088\u000b\u0001\u0000\u0000\u0000\u0089"+
		"\u0087\u0001\u0000\u0000\u0000\u008a\u008b\u0005\u0010\u0000\u0000\u008b"+
		"\u008c\u0006\u0006\uffff\uffff\u0000\u008c\u008d\u0003\u0016\u000b\u0000"+
		"\u008d\u008e\u0005\u0011\u0000\u0000\u008e\u008f\u0006\u0006\uffff\uffff"+
		"\u0000\u008f\u0094\u0001\u0000\u0000\u0000\u0090\u0091\u0005\u0010\u0000"+
		"\u0000\u0091\u0092\u0005\u0011\u0000\u0000\u0092\u0094\u0006\u0006\uffff"+
		"\uffff\u0000\u0093\u008a\u0001\u0000\u0000\u0000\u0093\u0090\u0001\u0000"+
		"\u0000\u0000\u0094\r\u0001\u0000\u0000\u0000\u0095\u0096\u0003\u0010\b"+
		"\u0000\u0096\u0097\u0003\u0012\t\u0000\u0097\u0098\u0005\u0014\u0000\u0000"+
		"\u0098\u0099\u0006\u0007\uffff\uffff\u0000\u0099\u000f\u0001\u0000\u0000"+
		"\u0000\u009a\u009b\u0005\u000b\u0000\u0000\u009b\u00a1\u0006\b\uffff\uffff"+
		"\u0000\u009c\u009d\u0005\f\u0000\u0000\u009d\u00a1\u0006\b\uffff\uffff"+
		"\u0000\u009e\u009f\u0005\r\u0000\u0000\u009f\u00a1\u0006\b\uffff\uffff"+
		"\u0000\u00a0\u009a\u0001\u0000\u0000\u0000\u00a0\u009c\u0001\u0000\u0000"+
		"\u0000\u00a0\u009e\u0001\u0000\u0000\u0000\u00a1\u0011\u0001\u0000\u0000"+
		"\u0000\u00a2\u00a3\u0006\t\uffff\uffff\u0000\u00a3\u00a4\u0003\u0014\n"+
		"\u0000\u00a4\u00a5\u0006\t\uffff\uffff\u0000\u00a5\u00ad\u0001\u0000\u0000"+
		"\u0000\u00a6\u00a7\n\u0002\u0000\u0000\u00a7\u00a8\u0005\u0015\u0000\u0000"+
		"\u00a8\u00a9\u0003\u0014\n\u0000\u00a9\u00aa\u0006\t\uffff\uffff\u0000"+
		"\u00aa\u00ac\u0001\u0000\u0000\u0000\u00ab\u00a6\u0001\u0000\u0000\u0000"+
		"\u00ac\u00af\u0001\u0000\u0000\u0000\u00ad\u00ab\u0001\u0000\u0000\u0000"+
		"\u00ad\u00ae\u0001\u0000\u0000\u0000\u00ae\u0013\u0001\u0000\u0000\u0000"+
		"\u00af\u00ad\u0001\u0000\u0000\u0000\u00b0\u00b1\u0005\u001e\u0000\u0000"+
		"\u00b1\u00b8\u0006\n\uffff\uffff\u0000\u00b2\u00b3\u0005\u001e\u0000\u0000"+
		"\u00b3\u00b4\u0005\u0012\u0000\u0000\u00b4\u00b5\u0005\u001f\u0000\u0000"+
		"\u00b5\u00b6\u0005\u0013\u0000\u0000\u00b6\u00b8\u0006\n\uffff\uffff\u0000"+
		"\u00b7\u00b0\u0001\u0000\u0000\u0000\u00b7\u00b2\u0001\u0000\u0000\u0000"+
		"\u00b8\u0015\u0001\u0000\u0000\u0000\u00b9\u00ba\u0006\u000b\uffff\uffff"+
		"\u0000\u00ba\u00bb\u0003\u0018\f\u0000\u00bb\u00bc\u0006\u000b\uffff\uffff"+
		"\u0000\u00bc\u00c3\u0001\u0000\u0000\u0000\u00bd\u00be\n\u0002\u0000\u0000"+
		"\u00be\u00bf\u0003\u0018\f\u0000\u00bf\u00c0\u0006\u000b\uffff\uffff\u0000"+
		"\u00c0\u00c2\u0001\u0000\u0000\u0000\u00c1\u00bd\u0001\u0000\u0000\u0000"+
		"\u00c2\u00c5\u0001\u0000\u0000\u0000\u00c3\u00c1\u0001\u0000\u0000\u0000"+
		"\u00c3\u00c4\u0001\u0000\u0000\u0000\u00c4\u0017\u0001\u0000\u0000\u0000"+
		"\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c6\u00c7\u0003\u000e\u0007\u0000"+
		"\u00c7\u00c8\u0006\f\uffff\uffff\u0000\u00c8\u0106\u0001\u0000\u0000\u0000"+
		"\u00c9\u00ca\u0003\u001a\r\u0000\u00ca\u00cb\u0006\f\uffff\uffff\u0000"+
		"\u00cb\u0106\u0001\u0000\u0000\u0000\u00cc\u00cd\u0003\f\u0006\u0000\u00cd"+
		"\u00ce\u0006\f\uffff\uffff\u0000\u00ce\u0106\u0001\u0000\u0000\u0000\u00cf"+
		"\u00d0\u0005\u0007\u0000\u0000\u00d0\u00d1\u0005\u000e\u0000\u0000\u00d1"+
		"\u00d2\u0006\f\uffff\uffff\u0000\u00d2\u00d3\u0003\u001a\r\u0000\u00d3"+
		"\u00d4\u0006\f\uffff\uffff\u0000\u00d4\u00d5\u0006\f\uffff\uffff\u0000"+
		"\u00d5\u00d6\u0003\u001a\r\u0000\u00d6\u00d7\u0006\f\uffff\uffff\u0000"+
		"\u00d7\u00d8\u0006\f\uffff\uffff\u0000\u00d8\u00d9\u0003\u001e\u000f\u0000"+
		"\u00d9\u00da\u0006\f\uffff\uffff\u0000\u00da\u00db\u0005\u000f\u0000\u0000"+
		"\u00db\u00dc\u0003\u0018\f\u0000\u00dc\u00dd\u0006\f\uffff\uffff\u0000"+
		"\u00dd\u0106\u0001\u0000\u0000\u0000\u00de\u00df\u0005\u0005\u0000\u0000"+
		"\u00df\u00e0\u0005\u000e\u0000\u0000\u00e0\u00e1\u0003\u001e\u000f\u0000"+
		"\u00e1\u00e2\u0005\u000f\u0000\u0000\u00e2\u00e3\u0006\f\uffff\uffff\u0000"+
		"\u00e3\u00e4\u0003\u0018\f\u0000\u00e4\u00e5\u0006\f\uffff\uffff\u0000"+
		"\u00e5\u0106\u0001\u0000\u0000\u0000\u00e6\u00e7\u0005\u0005\u0000\u0000"+
		"\u00e7\u00e8\u0005\u000e\u0000\u0000\u00e8\u00e9\u0003\u001e\u000f\u0000"+
		"\u00e9\u00ea\u0005\u000f\u0000\u0000\u00ea\u00eb\u0006\f\uffff\uffff\u0000"+
		"\u00eb\u00ec\u0003\u0018\f\u0000\u00ec\u00ed\u0005\u0006\u0000\u0000\u00ed"+
		"\u00ee\u0006\f\uffff\uffff\u0000\u00ee\u00ef\u0003\u0018\f\u0000\u00ef"+
		"\u00f0\u0006\f\uffff\uffff\u0000\u00f0\u0106\u0001\u0000\u0000\u0000\u00f1"+
		"\u00f2\u0005\b\u0000\u0000\u00f2\u00f3\u0005\u000e\u0000\u0000\u00f3\u00f4"+
		"\u0006\f\uffff\uffff\u0000\u00f4\u00f5\u0003\u001e\u000f\u0000\u00f5\u00f6"+
		"\u0006\f\uffff\uffff\u0000\u00f6\u00f7\u0005\u000f\u0000\u0000\u00f7\u00f8"+
		"\u0003\u0018\f\u0000\u00f8\u00f9\u0006\f\uffff\uffff\u0000\u00f9\u0106"+
		"\u0001\u0000\u0000\u0000\u00fa\u00fb\u0005\t\u0000\u0000\u00fb\u00fc\u0005"+
		"\u000e\u0000\u0000\u00fc\u00fd\u0005\u001e\u0000\u0000\u00fd\u00fe\u0005"+
		"\u000f\u0000\u0000\u00fe\u00ff\u0005\u0014\u0000\u0000\u00ff\u0106\u0006"+
		"\f\uffff\uffff\u0000\u0100\u0101\u0005\n\u0000\u0000\u0101\u0102\u0003"+
		"\u001e\u000f\u0000\u0102\u0103\u0005\u0014\u0000\u0000\u0103\u0104\u0006"+
		"\f\uffff\uffff\u0000\u0104\u0106\u0001\u0000\u0000\u0000\u0105\u00c6\u0001"+
		"\u0000\u0000\u0000\u0105\u00c9\u0001\u0000\u0000\u0000\u0105\u00cc\u0001"+
		"\u0000\u0000\u0000\u0105\u00cf\u0001\u0000\u0000\u0000\u0105\u00de\u0001"+
		"\u0000\u0000\u0000\u0105\u00e6\u0001\u0000\u0000\u0000\u0105\u00f1\u0001"+
		"\u0000\u0000\u0000\u0105\u00fa\u0001\u0000\u0000\u0000\u0105\u0100\u0001"+
		"\u0000\u0000\u0000\u0106\u0019\u0001\u0000\u0000\u0000\u0107\u0108\u0005"+
		"\u0014\u0000\u0000\u0108\u010e\u0006\r\uffff\uffff\u0000\u0109\u010a\u0003"+
		"\u001e\u000f\u0000\u010a\u010b\u0005\u0014\u0000\u0000\u010b\u010c\u0006"+
		"\r\uffff\uffff\u0000\u010c\u010e\u0001\u0000\u0000\u0000\u010d\u0107\u0001"+
		"\u0000\u0000\u0000\u010d\u0109\u0001\u0000\u0000\u0000\u010e\u001b\u0001"+
		"\u0000\u0000\u0000\u010f\u0110\u0005\u001e\u0000\u0000\u0110\u0118\u0006"+
		"\u000e\uffff\uffff\u0000\u0111\u0112\u0005\u001e\u0000\u0000\u0112\u0113"+
		"\u0005\u0012\u0000\u0000\u0113\u0114\u0003\u001e\u000f\u0000\u0114\u0115"+
		"\u0005\u0013\u0000\u0000\u0115\u0116\u0006\u000e\uffff\uffff\u0000\u0116"+
		"\u0118\u0001\u0000\u0000\u0000\u0117\u010f\u0001\u0000\u0000\u0000\u0117"+
		"\u0111\u0001\u0000\u0000\u0000\u0118\u001d\u0001\u0000\u0000\u0000\u0119"+
		"\u011a\u0003 \u0010\u0000\u011a\u011b\u0006\u000f\uffff\uffff\u0000\u011b"+
		"\u0122\u0001\u0000\u0000\u0000\u011c\u011d\u0003\u001c\u000e\u0000\u011d"+
		"\u011e\u0005\u001d\u0000\u0000\u011e\u011f\u0003 \u0010\u0000\u011f\u0120"+
		"\u0006\u000f\uffff\uffff\u0000\u0120\u0122\u0001\u0000\u0000\u0000\u0121"+
		"\u0119\u0001\u0000\u0000\u0000\u0121\u011c\u0001\u0000\u0000\u0000\u0122"+
		"\u001f\u0001\u0000\u0000\u0000\u0123\u0124\u0003\"\u0011\u0000\u0124\u0125"+
		"\u0006\u0010\uffff\uffff\u0000\u0125\u012c\u0001\u0000\u0000\u0000\u0126"+
		"\u0127\u0003\"\u0011\u0000\u0127\u0128\u0005\u001c\u0000\u0000\u0128\u0129"+
		"\u0003\"\u0011\u0000\u0129\u012a\u0006\u0010\uffff\uffff\u0000\u012a\u012c"+
		"\u0001\u0000\u0000\u0000\u012b\u0123\u0001\u0000\u0000\u0000\u012b\u0126"+
		"\u0001\u0000\u0000\u0000\u012c!\u0001\u0000\u0000\u0000\u012d\u012e\u0003"+
		"$\u0012\u0000\u012e\u012f\u0006\u0011\uffff\uffff\u0000\u012f\u0136\u0001"+
		"\u0000\u0000\u0000\u0130\u0131\u0003$\u0012\u0000\u0131\u0132\u0005\u001b"+
		"\u0000\u0000\u0132\u0133\u0003$\u0012\u0000\u0133\u0134\u0006\u0011\uffff"+
		"\uffff\u0000\u0134\u0136\u0001\u0000\u0000\u0000\u0135\u012d\u0001\u0000"+
		"\u0000\u0000\u0135\u0130\u0001\u0000\u0000\u0000\u0136#\u0001\u0000\u0000"+
		"\u0000\u0137\u0138\u0006\u0012\uffff\uffff\u0000\u0138\u0139\u0003&\u0013"+
		"\u0000\u0139\u013a\u0006\u0012\uffff\uffff\u0000\u013a\u0142\u0001\u0000"+
		"\u0000\u0000\u013b\u013c\n\u0001\u0000\u0000\u013c\u013d\u0005\u0016\u0000"+
		"\u0000\u013d\u013e\u0003&\u0013\u0000\u013e\u013f\u0006\u0012\uffff\uffff"+
		"\u0000\u013f\u0141\u0001\u0000\u0000\u0000\u0140\u013b\u0001\u0000\u0000"+
		"\u0000\u0141\u0144\u0001\u0000\u0000\u0000\u0142\u0140\u0001\u0000\u0000"+
		"\u0000\u0142\u0143\u0001\u0000\u0000\u0000\u0143%\u0001\u0000\u0000\u0000"+
		"\u0144\u0142\u0001\u0000\u0000\u0000\u0145\u0146\u0006\u0013\uffff\uffff"+
		"\u0000\u0146\u0147\u0003(\u0014\u0000\u0147\u0148\u0006\u0013\uffff\uffff"+
		"\u0000\u0148\u0150\u0001\u0000\u0000\u0000\u0149\u014a\n\u0001\u0000\u0000"+
		"\u014a\u014b\u0005\u0017\u0000\u0000\u014b\u014c\u0003(\u0014\u0000\u014c"+
		"\u014d\u0006\u0013\uffff\uffff\u0000\u014d\u014f\u0001\u0000\u0000\u0000"+
		"\u014e\u0149\u0001\u0000\u0000\u0000\u014f\u0152\u0001\u0000\u0000\u0000"+
		"\u0150\u014e\u0001\u0000\u0000\u0000\u0150\u0151\u0001\u0000\u0000\u0000"+
		"\u0151\'\u0001\u0000\u0000\u0000\u0152\u0150\u0001\u0000\u0000\u0000\u0153"+
		"\u0154\u0005\u0016\u0000\u0000\u0154\u0155\u0003(\u0014\u0000\u0155\u0156"+
		"\u0006\u0014\uffff\uffff\u0000\u0156\u015f\u0001\u0000\u0000\u0000\u0157"+
		"\u0158\u0005\u001a\u0000\u0000\u0158\u0159\u0003(\u0014\u0000\u0159\u015a"+
		"\u0006\u0014\uffff\uffff\u0000\u015a\u015f\u0001\u0000\u0000\u0000\u015b"+
		"\u015c\u0003*\u0015\u0000\u015c\u015d\u0006\u0014\uffff\uffff\u0000\u015d"+
		"\u015f\u0001\u0000\u0000\u0000\u015e\u0153\u0001\u0000\u0000\u0000\u015e"+
		"\u0157\u0001\u0000\u0000\u0000\u015e\u015b\u0001\u0000\u0000\u0000\u015f"+
		")\u0001\u0000\u0000\u0000\u0160\u0161\u0003\u001c\u000e\u0000\u0161\u0162"+
		"\u0006\u0015\uffff\uffff\u0000\u0162\u017b\u0001\u0000\u0000\u0000\u0163"+
		"\u0164\u0005\u001e\u0000\u0000\u0164\u0165\u0005\u000e\u0000\u0000\u0165"+
		"\u0166\u0003,\u0016\u0000\u0166\u0167\u0005\u000f\u0000\u0000\u0167\u0168"+
		"\u0006\u0015\uffff\uffff\u0000\u0168\u017b\u0001\u0000\u0000\u0000\u0169"+
		"\u016a\u0005\u000e\u0000\u0000\u016a\u016b\u0003\u001e\u000f\u0000\u016b"+
		"\u016c\u0005\u000f\u0000\u0000\u016c\u016d\u0006\u0015\uffff\uffff\u0000"+
		"\u016d\u017b\u0001\u0000\u0000\u0000\u016e\u016f\u0005\u001f\u0000\u0000"+
		"\u016f\u017b\u0006\u0015\uffff\uffff\u0000\u0170\u0171\u0005 \u0000\u0000"+
		"\u0171\u017b\u0006\u0015\uffff\uffff\u0000\u0172\u0173\u0003\u001c\u000e"+
		"\u0000\u0173\u0174\u0005\u0018\u0000\u0000\u0174\u0175\u0006\u0015\uffff"+
		"\uffff\u0000\u0175\u017b\u0001\u0000\u0000\u0000\u0176\u0177\u0003\u001c"+
		"\u000e\u0000\u0177\u0178\u0005\u0019\u0000\u0000\u0178\u0179\u0006\u0015"+
		"\uffff\uffff\u0000\u0179\u017b\u0001\u0000\u0000\u0000\u017a\u0160\u0001"+
		"\u0000\u0000\u0000\u017a\u0163\u0001\u0000\u0000\u0000\u017a\u0169\u0001"+
		"\u0000\u0000\u0000\u017a\u016e\u0001\u0000\u0000\u0000\u017a\u0170\u0001"+
		"\u0000\u0000\u0000\u017a\u0172\u0001\u0000\u0000\u0000\u017a\u0176\u0001"+
		"\u0000\u0000\u0000\u017b+\u0001\u0000\u0000\u0000\u017c\u017d\u0003.\u0017"+
		"\u0000\u017d\u017e\u0006\u0016\uffff\uffff\u0000\u017e\u0181\u0001\u0000"+
		"\u0000\u0000\u017f\u0181\u0006\u0016\uffff\uffff\u0000\u0180\u017c\u0001"+
		"\u0000\u0000\u0000\u0180\u017f\u0001\u0000\u0000\u0000\u0181-\u0001\u0000"+
		"\u0000\u0000\u0182\u0183\u0006\u0017\uffff\uffff\u0000\u0183\u0184\u0003"+
		" \u0010\u0000\u0184\u0185\u0006\u0017\uffff\uffff\u0000\u0185\u018d\u0001"+
		"\u0000\u0000\u0000\u0186\u0187\n\u0002\u0000\u0000\u0187\u0188\u0005\u0015"+
		"\u0000\u0000\u0188\u0189\u0003 \u0010\u0000\u0189\u018a\u0006\u0017\uffff"+
		"\uffff\u0000\u018a\u018c\u0001\u0000\u0000\u0000\u018b\u0186\u0001\u0000"+
		"\u0000\u0000\u018c\u018f\u0001\u0000\u0000\u0000\u018d\u018b\u0001\u0000"+
		"\u0000\u0000\u018d\u018e\u0001\u0000\u0000\u0000\u018e/\u0001\u0000\u0000"+
		"\u0000\u018f\u018d\u0001\u0000\u0000\u0000\u0018=I[nx\u0085\u0087\u0093"+
		"\u00a0\u00ad\u00b7\u00c3\u0105\u010d\u0117\u0121\u012b\u0135\u0142\u0150"+
		"\u015e\u017a\u0180\u018d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}