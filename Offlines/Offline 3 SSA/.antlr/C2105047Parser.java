// Generated from e:/academics repo/CSE 310 repo/Offlines/Offline 3 SSA/C2105047Parser.g4 by ANTLR 4.13.1

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
		SUBOP=23, MULOP=24, INCOP=25, DECOP=26, NOT=27, RELOP=28, LOGICOP=29, 
		ASSIGNOP=30, ID=31, CONST_INT=32, CONST_FLOAT=33, UNRECOGNIZED_CHAR=34;
	public static final int
		RULE_start = 0, RULE_program = 1, RULE_unit = 2, RULE_func_declaration = 3, 
		RULE_func_definition = 4, RULE_parameter_list = 5, RULE_error_in_parameter = 6, 
		RULE_compound_statement = 7, RULE_var_declaration = 8, RULE_type_specifier = 9, 
		RULE_declaration_list = 10, RULE_declaration_item = 11, RULE_statements = 12, 
		RULE_statement = 13, RULE_expression_statement = 14, RULE_variable = 15, 
		RULE_expression = 16, RULE_logic_expression = 17, RULE_rel_expression = 18, 
		RULE_simple_expression = 19, RULE_term = 20, RULE_unary_expression = 21, 
		RULE_factor = 22, RULE_argument_list = 23, RULE_arguments = 24;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "program", "unit", "func_declaration", "func_definition", "parameter_list", 
			"error_in_parameter", "compound_statement", "var_declaration", "type_specifier", 
			"declaration_list", "declaration_item", "statements", "statement", "expression_statement", 
			"variable", "expression", "logic_expression", "rel_expression", "simple_expression", 
			"term", "unary_expression", "factor", "argument_list", "arguments"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'if'", "'else'", "'for'", "'while'", "'printf'", 
			"'return'", "'int'", "'float'", "'void'", "'('", "')'", "'{'", "'}'", 
			"'['", "']'", "';'", "','", null, null, null, "'++'", "'--'", "'!'", 
			null, null, "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LINE_COMMENT", "BLOCK_COMMENT", "STRING", "WS", "IF", "ELSE", 
			"FOR", "WHILE", "PRINTLN", "RETURN", "INT", "FLOAT", "VOID", "LPAREN", 
			"RPAREN", "LCURL", "RCURL", "LTHIRD", "RTHIRD", "SEMICOLON", "COMMA", 
			"ADDOP", "SUBOP", "MULOP", "INCOP", "DECOP", "NOT", "RELOP", "LOGICOP", 
			"ASSIGNOP", "ID", "CONST_INT", "CONST_FLOAT", "UNRECOGNIZED_CHAR"
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
			setState(50);
			((StartContext)_localctx).program = program(0);

			        lineCount = (((StartContext)_localctx).program!=null?(((StartContext)_localctx).program.stop):null)->getLine(); // get the last line number
			        writeIntoparserLogFile("Line " + to_string(lineCount) + ": start : program");
			        writeIntoparserLogFile("");
			        symbolTable.printAllScopeTable(parserLogFile);
			        writeIntoparserLogFile("Total number of lines: " + to_string(lineCount));
			        writeIntoparserLogFile("Total number of errors: " + to_string(syntaxErrorCount));
			    
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
			setState(54);
			((ProgramContext)_localctx).unit = unit();

			        lineCount = (((ProgramContext)_localctx).unit!=null?(((ProgramContext)_localctx).unit.stop):null)->getLine();
			        string formattedCode = removeEmptyLines(formatFunctionBlock((((ProgramContext)_localctx).unit!=null?_input.getText(((ProgramContext)_localctx).unit.start,((ProgramContext)_localctx).unit.stop):null)));
			        logRule("program : unit", formattedCode);
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(63);
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
					setState(57);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(58);
					((ProgramContext)_localctx).u = ((ProgramContext)_localctx).unit = unit();

					                  lineCount = (((ProgramContext)_localctx).u!=null?(((ProgramContext)_localctx).u.stop):null)->getLine();
					                  string code = (((ProgramContext)_localctx).p!=null?_input.getText(((ProgramContext)_localctx).p.start,((ProgramContext)_localctx).p.stop):null) + "\n\n" + (((ProgramContext)_localctx).u!=null?_input.getText(((ProgramContext)_localctx).u.start,((ProgramContext)_localctx).u.stop):null);
					                  string formattedCode = removeEmptyLines(formatFunctionBlock(code));
					                  logRule("program : program unit", formattedCode);
					              
					}
					} 
				}
				setState(65);
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
		public Var_declarationContext var_declaration;
		public Func_declarationContext func_declaration;
		public Func_definitionContext func_definition;
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
			setState(75);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				((UnitContext)_localctx).var_declaration = var_declaration();

				        logRule("unit : var_declaration", (((UnitContext)_localctx).var_declaration!=null?_input.getText(((UnitContext)_localctx).var_declaration.start,((UnitContext)_localctx).var_declaration.stop):null));
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				((UnitContext)_localctx).func_declaration = func_declaration();

				        logRule("unit : func_declaration", formatDeclaration((((UnitContext)_localctx).func_declaration!=null?_input.getText(((UnitContext)_localctx).func_declaration.start,((UnitContext)_localctx).func_declaration.stop):null)));
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(72);
				((UnitContext)_localctx).func_definition = func_definition();

				        logRule("unit : func_definition", removeEmptyLines(formatFunctionBlock((((UnitContext)_localctx).func_definition!=null?_input.getText(((UnitContext)_localctx).func_definition.start,((UnitContext)_localctx).func_definition.stop):null))));
				    
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
		public Token SEMICOLON;
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
			setState(93);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				((Func_declarationContext)_localctx).type_specifier = type_specifier();
				setState(78);
				((Func_declarationContext)_localctx).ID = match(ID);

				        // writeIntoparserLogFile("DEBUG: func_declaration matched for " + (((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null));

				        int hasError=0;
				        SymbolInfo* symbol = new SymbolInfo((((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null), "FUNCTION"); 
				        symbol->setIsFunction(true);
				        symbol->setReturnType(getTypeName(((Func_declarationContext)_localctx).type_specifier)); // will need later to check if return type macthes in definition
				        symbol->setIsDefined(false); // still not defined, just declared
				        if (!symbolTable.insert(*symbol, parserLogFile)) // just insert the function name, need not enter scope for the parametrs here
				        { 
				            hasError = 1;
				            syntaxErrorCount++;
				        }
				    
				setState(80);
				match(LPAREN);
				setState(81);
				((Func_declarationContext)_localctx).p = parameter_list(0);
				setState(82);
				match(RPAREN);
				setState(83);
				((Func_declarationContext)_localctx).SEMICOLON = match(SEMICOLON);

				        // writeIntoparserLogFile("DEBUG: func_declaration matched for " + (((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null));

				        lineCount = ((Func_declarationContext)_localctx).SEMICOLON->getLine(); // the semicolon is surely the ending of a declaration
				        
				        writeIntoparserLogFile("Line " + to_string(lineCount) + ": func_declaration : type_specifier ID LPAREN parameter_list RPAREN SEMICOLON");
				        writeIntoparserLogFile("");
				        
				        if(hasError) 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((Func_declarationContext)_localctx).SEMICOLON->getLine()) + ": Multiple declaration of " + (((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("Error at line " + to_string(((Func_declarationContext)_localctx).SEMICOLON->getLine()) + ": Multiple declaration of " + (((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        }
				        
				        writeIntoparserLogFile(formatDeclaration((((Func_declarationContext)_localctx).type_specifier!=null?_input.getText(((Func_declarationContext)_localctx).type_specifier.start,((Func_declarationContext)_localctx).type_specifier.stop):null) + " " + (((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null) + "(" + (((Func_declarationContext)_localctx).p!=null?_input.getText(((Func_declarationContext)_localctx).p.start,((Func_declarationContext)_localctx).p.stop):null) + ");"));
				        writeIntoparserLogFile("");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(86);
				((Func_declarationContext)_localctx).type_specifier = type_specifier();
				setState(87);
				((Func_declarationContext)_localctx).ID = match(ID);
				setState(88);
				match(LPAREN);
				setState(89);
				match(RPAREN);
				setState(90);
				((Func_declarationContext)_localctx).SEMICOLON = match(SEMICOLON);

				        int hasError=0;
				        lineCount = ((Func_declarationContext)_localctx).SEMICOLON->getLine();
				        SymbolInfo* symbol = new SymbolInfo((((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null), "FUNCTION");
				        symbol->setIsFunction(true);
				        symbol->setReturnType(getTypeName(((Func_declarationContext)_localctx).type_specifier));
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
				            writeIntoErrorFile("Error at line " + to_string(((Func_declarationContext)_localctx).SEMICOLON->getLine()) + ": Multiple declaration of " + (((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("Error at line " + to_string(((Func_declarationContext)_localctx).SEMICOLON->getLine()) + ": Multiple declaration of " + (((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        }
				        
				        writeIntoparserLogFile(formatDeclaration((((Func_declarationContext)_localctx).type_specifier!=null?_input.getText(((Func_declarationContext)_localctx).type_specifier.start,((Func_declarationContext)_localctx).type_specifier.stop):null) + " " + (((Func_declarationContext)_localctx).ID!=null?((Func_declarationContext)_localctx).ID.getText():null) + "();"));
				        writeIntoparserLogFile("");
				    
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
		public Token RPAREN;
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
			setState(113);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(95);
				((Func_definitionContext)_localctx).type_specifier = type_specifier();
				setState(96);
				((Func_definitionContext)_localctx).ID = match(ID);
				setState(97);
				match(LPAREN);

				        currentFunctionReturnType = getTypeName(((Func_definitionContext)_localctx).type_specifier); //get the return type of the function for later use
				        currentFunctionName = (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null); // store the function name for later use
				        
				        // Check if the function , at least declaration , already exists
				        SymbolInfo* existingSymbol = symbolTable.lookUp((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), parserLogFile);
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
				                writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                multipleDeclarationInDeclarationError = 1;
				                syntaxErrorCount++;
				            } 
				            else 
				            {
				                // it was not defined before , it was just declared before
				                //check if the return type matches with declaration
				                if (existingSymbol->getReturnType() != getTypeName(((Func_definitionContext)_localctx).type_specifier)) 
				                {
				                    writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Return type mismatch of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                    returnTypeMismatchError = 1;
				                    syntaxErrorCount++;
				                }

				                // Store declaration parameter count and types before clearing
				                declaredParamCount = existingSymbol->getParameterCount();
				                declaredParamTypes = existingSymbol->getParameterTypes();
				                //writeIntoErrorFile("DEBUG: Function " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null) + " was declared before with " + to_string(declaredParamCount) + " parameters.");
				                existingSymbol->clearParameters(); // Clear to rebuild from definition
				                
				                //mark it as defined now
				                existingSymbol->setIsDefined(true);
				            }
				        } 
				        else 
				        {
				            // New function - insert it in symbol table
				            SymbolInfo* symbol = new SymbolInfo((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), "FUNCTION");
				            symbol->setIsFunction(true);
				            symbol->setReturnType(getTypeName(((Func_definitionContext)_localctx).type_specifier));
				            symbol->setIsDefined(true);  // This is an on-the-spot definition
				            if (!symbolTable.insert(*symbol, parserLogFile)) 
				            {
				                writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                multipleDeclationInDefinitionError = 1;
				                syntaxErrorCount++;
				            }
				        }
				        
				        // we have got LPAREN, Create scope for the parameters immediately because we have to remember them throughout the function definition
				        // if we waited for RCURL, we ccould not save the parameters info
				        symbolTable.enterScope(7, parserLogFile);
				    
				setState(99);
				((Func_definitionContext)_localctx).p = parameter_list(0);
				setState(100);
				((Func_definitionContext)_localctx).RPAREN = match(RPAREN);

				        // Check parameter count and type mismatch after parameter_list is processed
				        if (declaredParamCount >= 0) // Function was previously declared
				        { 
				            SymbolInfo* functionSymbol = symbolTable.lookUp((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), parserLogFile);
				            if (functionSymbol && functionSymbol->getIsFunction()) 
				            {
				                int definitionParamCount = functionSymbol->getParameterCount();
				                vector<string> definitionParamTypes = functionSymbol->getParameterTypes();
				                
				                // Check argument count mismatch
				                if (definitionParamCount != declaredParamCount) 
				                {
				                    writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).RPAREN->getLine()) + ": Total number of arguments mismatch with declaration in function " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
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
				                            writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).RPAREN->getLine()) + ": " + to_string(i + 1) + "th argument type mismatch with declaration in function " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                            writeIntoparserLogFile("Error at line " + to_string(((Func_definitionContext)_localctx).RPAREN->getLine()) + ": " + to_string(i + 1) + "th argument type mismatch with declaration in function " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                            writeIntoparserLogFile("");
				                            syntaxErrorCount++;
				                            typeOfParametersMismatchError = 1;
				                            break;
				                        }
				                    }
				                }
				            }
				        }
				    
				setState(102);
				((Func_definitionContext)_localctx).compound_statement = compound_statement();

				        lineCount = (((Func_definitionContext)_localctx).compound_statement!=null?(((Func_definitionContext)_localctx).compound_statement.stop):null)->getLine();
				        
				        // Reset current function return type when exiting function because definition will be complete here
				        currentFunctionReturnType = "";
				        currentFunctionName = "";

				            //print rule header first
				            writeIntoparserLogFile("Line " + to_string(lineCount) + ": func_definition : type_specifier ID LPAREN parameter_list RPAREN compound_statement");
				            writeIntoparserLogFile("");
				                
				                // Check for any errors that occurred during definition
				                if (multipleDeclarationInDeclarationError) {
				                    writeIntoparserLogFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                    writeIntoparserLogFile("");
				                } else if (returnTypeMismatchError) {
				                    writeIntoparserLogFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Return type mismatch of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                    writeIntoparserLogFile("");
				                } else if (multipleDeclationInDefinitionError) {
				                    writeIntoparserLogFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                    writeIntoparserLogFile("");
				                } else if (numberOfParametersMismatchError) {
				                    writeIntoparserLogFile("Error at line " + to_string(((Func_definitionContext)_localctx).RPAREN->getLine()) + ": Total number of arguments mismatch with declaration in function " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                    writeIntoparserLogFile("");
				                } else if (typeOfParametersMismatchError) {
				                    // Error already logged inside the loop
				                }
				            //then print the function definition code, only the (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null)
				            //writeIntoparserLogFile(removeUnnecessarySpaces((((Func_definitionContext)_localctx).type_specifier!=null?_input.getText(((Func_definitionContext)_localctx).type_specifier.start,((Func_definitionContext)_localctx).type_specifier.stop):null) + " " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null) + "(" + (((Func_definitionContext)_localctx).p!=null?_input.getText(((Func_definitionContext)_localctx).p.start,((Func_definitionContext)_localctx).p.stop):null) + ")") + "\n" + formatFunctionBlock((((Func_definitionContext)_localctx).compound_statement!=null?_input.getText(((Func_definitionContext)_localctx).compound_statement.start,((Func_definitionContext)_localctx).compound_statement.stop):null)));
				            writeIntoparserLogFile(removeEmptyLines(removeUnnecessarySpaces((((Func_definitionContext)_localctx).type_specifier!=null?_input.getText(((Func_definitionContext)_localctx).type_specifier.start,((Func_definitionContext)_localctx).type_specifier.stop):null) + " " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null) + "(" + (((Func_definitionContext)_localctx).p!=null?_input.getText(((Func_definitionContext)_localctx).p.start,((Func_definitionContext)_localctx).p.stop):null) + ")") + formatFunctionBlock((((Func_definitionContext)_localctx).compound_statement!=null?_input.getText(((Func_definitionContext)_localctx).compound_statement.start,((Func_definitionContext)_localctx).compound_statement.stop):null))));
				            writeIntoparserLogFile("");    
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				((Func_definitionContext)_localctx).type_specifier = type_specifier();
				setState(106);
				((Func_definitionContext)_localctx).ID = match(ID);
				setState(107);
				match(LPAREN);

				        // this will be the rule for funcction definition without parameters
				        // these functions can have type_specifier too, save that
				        currentFunctionReturnType = getTypeName(((Func_definitionContext)_localctx).type_specifier);
				        currentFunctionName = (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null);
				        
				        // Check if function already exists
				        SymbolInfo* existingSymbol = symbolTable.lookUp((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), parserLogFile);
				        int multipleDeclarationInDeclarationError=0;
				        int returnTypeMismatchError=0;
				        int numberOfParametersMismatchError=0;
				        int multipleDeclationInDefinitionError=0;
				        lineCount = ((Func_definitionContext)_localctx).ID->getLine();
				        
				        if (existingSymbol && existingSymbol->getIsFunction()) 
				        {
				            // Function was declared before - check if it's already defined
				            if (existingSymbol->getIsDefined()) 
				            {
				                // Already defined - this is an error
				                writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                multipleDeclarationInDeclarationError = 1;
				                syntaxErrorCount++;
				            } 
				            else 
				            {
				                // it was not defined before , it was just declared before
				                //check if the return type matches with declaration
				                if (existingSymbol->getReturnType() != getTypeName(((Func_definitionContext)_localctx).type_specifier)) 
				                {
				                    writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Return type mismatch of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                    returnTypeMismatchError = 1;
				                    syntaxErrorCount++;
				                }
				                
				                // Check parameter count mismatch - definition has 0 parameters but declaration had more
				                if (existingSymbol->getParameterCount() > 0) 
				                {
				                    writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Total number of arguments mismatch with declaration in function " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                    syntaxErrorCount++;
				                    numberOfParametersMismatchError = 1;
				                }
				                existingSymbol->setIsDefined(true);
				            }
				        } 
				        else 
				        {
				            // New function - insert it in the symbol table
				            SymbolInfo* symbol = new SymbolInfo((((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null), "FUNCTION");
				            symbol->setIsFunction(true);
				            symbol->setReturnType(getTypeName(((Func_definitionContext)_localctx).type_specifier));
				            symbol->setIsDefined(true);  // This is an on-the-spot definition
				            if (!symbolTable.insert(*symbol, parserLogFile)) 
				            {
				                writeIntoErrorFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				                multipleDeclationInDefinitionError = 1;
				                syntaxErrorCount++;
				            }
				        }
				        
				        symbolTable.enterScope(7, parserLogFile);
				    
				setState(109);
				match(RPAREN);
				setState(110);
				((Func_definitionContext)_localctx).compound_statement = compound_statement();

				        lineCount = (((Func_definitionContext)_localctx).compound_statement!=null?(((Func_definitionContext)_localctx).compound_statement.stop):null)->getLine();
				        
				        // func definition is complete, reset current function return type
				        currentFunctionReturnType = "";
				        currentFunctionName = "";
				        
				        //print rule header first
				        writeIntoparserLogFile("Line " + to_string(lineCount) + ": func_definition : type_specifier ID LPAREN RPAREN compound_statement");
				        writeIntoparserLogFile("");

				        // Check for any errors that occurred during definition
				        if (multipleDeclarationInDeclarationError) {
				            writeIntoparserLogFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        } else if (returnTypeMismatchError) {
				            writeIntoparserLogFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Return type mismatch of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        } else if (multipleDeclationInDefinitionError) {
				            writeIntoparserLogFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        } else if (numberOfParametersMismatchError) {
				            writeIntoparserLogFile("Error at line " + to_string(((Func_definitionContext)_localctx).ID->getLine()) + ": Total number of arguments mismatch with declaration in function " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        }
				        // Print the formatted function definition
				        writeIntoparserLogFile(removeUnnecessarySpaces((((Func_definitionContext)_localctx).type_specifier!=null?_input.getText(((Func_definitionContext)_localctx).type_specifier.start,((Func_definitionContext)_localctx).type_specifier.stop):null) + " " + (((Func_definitionContext)_localctx).ID!=null?((Func_definitionContext)_localctx).ID.getText():null) + "()") + removeEmptyLines(formatFunctionBlock((((Func_definitionContext)_localctx).compound_statement!=null?_input.getText(((Func_definitionContext)_localctx).compound_statement.start,((Func_definitionContext)_localctx).compound_statement.stop):null))));
				        writeIntoparserLogFile("");
				    
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
		public Error_in_parameterContext error_in_parameter() {
			return getRuleContext(Error_in_parameterContext.class,0);
		}
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
			setState(127);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(116);
				((Parameter_listContext)_localctx).type_specifier = type_specifier();
				setState(117);
				((Parameter_listContext)_localctx).ID = match(ID);

				        // base case for list of parameters
				        int multipleDeclarationInParameterError = 0;
				        lineCount = ((Parameter_listContext)_localctx).ID->getLine();

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
				                functionSymbol->addParameter(getTypeName(((Parameter_listContext)_localctx).type_specifier));
				            }
				        }

				        // ONLY insert parameter variables IN SYMBOLTABLE for function definitions (not declarations)
				        if (dynamic_cast<Func_definitionContext*>(_ctx->parent)) 
				        {
				            // We're in func_definition - insert parameter symbols in symbol table
				            SymbolInfo* symbol = new SymbolInfo(((Parameter_listContext)_localctx).ID->getText(), getTypeName(((Parameter_listContext)_localctx).type_specifier));
				            if (!symbolTable.insert(*symbol, parserLogFile))
				            {
				                multipleDeclarationInParameterError = 1;
				                syntaxErrorCount++;
				                writeIntoErrorFile("Error at line " + to_string(((Parameter_listContext)_localctx).ID->getLine()) + ": Multiple declaration of " + ((Parameter_listContext)_localctx).ID->getText() + " in parameter");
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
				            writeIntoparserLogFile("Error at line " + to_string(((Parameter_listContext)_localctx).ID->getLine()) + ": Multiple declaration of " + ((Parameter_listContext)_localctx).ID->getText() + " in parameter");
				            writeIntoparserLogFile("");
				        }

				        writeIntoparserLogFile(formatDeclaration((((Parameter_listContext)_localctx).type_specifier!=null?_input.getText(((Parameter_listContext)_localctx).type_specifier.start,((Parameter_listContext)_localctx).type_specifier.stop):null) + " " + ((Parameter_listContext)_localctx).ID->getText()));
				        writeIntoparserLogFile("");
				    
				}
				break;
			case 2:
				{
				setState(120);
				((Parameter_listContext)_localctx).type_specifier = type_specifier();
				   
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
				                functionSymbol->addParameter(getTypeName(((Parameter_listContext)_localctx).type_specifier));
				            }
				        }
				        
				        logRule("parameter_list : type_specifier", removeUnnecessarySpaces(_localctx->getText()));
				    
				}
				break;
			case 3:
				{
				setState(123);
				((Parameter_listContext)_localctx).type_specifier = type_specifier();
				setState(124);
				error_in_parameter();

				        // Error handling rule for invalid parameter syntax like "int-"
				        lineCount = (((Parameter_listContext)_localctx).type_specifier!=null?(((Parameter_listContext)_localctx).type_specifier.stop):null)->getLine();
				        syntaxErrorCount++;
				        writeIntoErrorFile("Error at line " + to_string(lineCount) + ": syntax error, unexpected ADDOP, expecting RPAREN or COMMA");
				        
				        writeIntoparserLogFile("Line " + to_string(lineCount) + ": parameter_list : type_specifier");
				        writeIntoparserLogFile("");
				        //print the error message
				        writeIntoparserLogFile("Error at line " + to_string(lineCount) + ": syntax error, unexpected ADDOP, expecting RPAREN or COMMA");
				        writeIntoparserLogFile("");
				        //then print code
				        writeIntoparserLogFile(removeUnnecessarySpaces((((Parameter_listContext)_localctx).type_specifier!=null?_input.getText(((Parameter_listContext)_localctx).type_specifier.start,((Parameter_listContext)_localctx).type_specifier.stop):null)));
				        writeIntoparserLogFile("");
				    
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(142);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(140);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new Parameter_listContext(_parentctx, _parentState);
						_localctx.p = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_parameter_list);
						setState(129);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(130);
						match(COMMA);
						setState(131);
						((Parameter_listContext)_localctx).type_specifier = type_specifier();
						setState(132);
						((Parameter_listContext)_localctx).ID = match(ID);

						                  int multipleDeclarationInParameterError = 0;

						                  lineCount = ((Parameter_listContext)_localctx).ID->getLine();

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
						                          functionSymbol->addParameter(getTypeName(((Parameter_listContext)_localctx).type_specifier));
						                      }
						                  }

						                  // ONLY insert parameter variables IN SYMBOLTABLE for function definitions (not declarations)
						                  if (dynamic_cast<Func_definitionContext*>(_ctx->parent)) 
						                  {
						                      // We're in func_definition - insert parameter symbols in symbol table
						                      // they need to be inserted in symbol table immediately when they are found in parameter_list, cant wait for LCURL to arrive
						                      SymbolInfo* symbol = new SymbolInfo(((Parameter_listContext)_localctx).ID->getText(), getTypeName(((Parameter_listContext)_localctx).type_specifier));
						                      if (!symbolTable.insert(*symbol, parserLogFile)) 
						                      {
						                          multipleDeclarationInParameterError = 1;
						                          syntaxErrorCount++;
						                          writeIntoErrorFile("Error at line " + to_string(((Parameter_listContext)_localctx).ID->getLine()) + ": Multiple declaration of " + ((Parameter_listContext)_localctx).ID->getText() + " in parameter");
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
						                      writeIntoparserLogFile("Error at line " + to_string(((Parameter_listContext)_localctx).ID->getLine()) + ": Multiple declaration of " + ((Parameter_listContext)_localctx).ID->getText() + " in parameter");
						                      writeIntoparserLogFile("");
						                  }
						                  
						                  writeIntoparserLogFile(formatDeclaration((((Parameter_listContext)_localctx).p!=null?_input.getText(((Parameter_listContext)_localctx).p.start,((Parameter_listContext)_localctx).p.stop):null) + "," + (((Parameter_listContext)_localctx).type_specifier!=null?_input.getText(((Parameter_listContext)_localctx).type_specifier.start,((Parameter_listContext)_localctx).type_specifier.stop):null) + " " + ((Parameter_listContext)_localctx).ID->getText()));
						                  writeIntoparserLogFile("");
						              
						}
						break;
					case 2:
						{
						_localctx = new Parameter_listContext(_parentctx, _parentState);
						_localctx.p = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_parameter_list);
						setState(135);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(136);
						match(COMMA);
						setState(137);
						((Parameter_listContext)_localctx).type_specifier = type_specifier();

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
						                          functionSymbol->addParameter(getTypeName(((Parameter_listContext)_localctx).type_specifier));
						                      }
						                  }
						                  
						                  logRule("parameter_list : parameter_list COMMA type_specifier", removeUnnecessarySpaces(_localctx->getText()));
						              
						}
						break;
					}
					} 
				}
				setState(144);
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
	public static class Error_in_parameterContext extends ParserRuleContext {
		public TerminalNode ADDOP() { return getToken(C2105047Parser.ADDOP, 0); }
		public TerminalNode MULOP() { return getToken(C2105047Parser.MULOP, 0); }
		public TerminalNode ASSIGNOP() { return getToken(C2105047Parser.ASSIGNOP, 0); }
		public TerminalNode RELOP() { return getToken(C2105047Parser.RELOP, 0); }
		public TerminalNode LOGICOP() { return getToken(C2105047Parser.LOGICOP, 0); }
		public TerminalNode INCOP() { return getToken(C2105047Parser.INCOP, 0); }
		public TerminalNode DECOP() { return getToken(C2105047Parser.DECOP, 0); }
		public TerminalNode NOT() { return getToken(C2105047Parser.NOT, 0); }
		public Error_in_parameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_error_in_parameter; }
	}

	public final Error_in_parameterContext error_in_parameter() throws RecognitionException {
		Error_in_parameterContext _localctx = new Error_in_parameterContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_error_in_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2134900736L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
	public static class Compound_statementContext extends ParserRuleContext {
		public StatementsContext statements;
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
		enterRule(_localctx, 14, RULE_compound_statement);
		try {
			setState(156);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(147);
				match(LCURL);

				    // writeIntoparserLogFile("DEBUG: compound_statement - entering new scope");
				    // Only create new scope if NOT already in function scope , this is normal scoping with braces without function definition
				    // as said before, function definition already creates scope for parameters
				    if (!dynamic_cast<Func_definitionContext*>(_ctx->parent)) 
				    {
				        symbolTable.enterScope(7, parserLogFile);
				    }

				setState(149);
				((Compound_statementContext)_localctx).statements = statements(0);
				setState(150);
				((Compound_statementContext)_localctx).RCURL = match(RCURL);

				    lineCount = ((Compound_statementContext)_localctx).RCURL->getLine();
				    //writeIntoparserLogFile("DEBUG: compound_statement - exiting scope");
				    logRule("compound_statement : LCURL statements RCURL", "{\n" + removeEmptyLines(formatFunctionBlock((((Compound_statementContext)_localctx).statements!=null?_input.getText(((Compound_statementContext)_localctx).statements.start,((Compound_statementContext)_localctx).statements.stop):null))) + "\n}");
				    
				    // Always exit scope after getting right brace, either function scope or block scope, both is handled here
				    symbolTable.exitScope(parserLogFile, 1);

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(153);
				match(LCURL);
				setState(154);
				((Compound_statementContext)_localctx).RCURL = match(RCURL);

				    //nothing between the braces, still we have to enter and exit scope
				    lineCount = ((Compound_statementContext)_localctx).RCURL->getLine();
				    //writeIntoparserLogFile("DEBUG: compound_statement (empty) - exiting scope");
				    logRule("compound_statement : LCURL RCURL", "{\n}");
				    
				    // Only creat scope if NOT in function definition
				    // as said before, function definition already creates scope for parameters
				    if (!dynamic_cast<Func_definitionContext*>(_ctx->parent)) {
				        symbolTable.enterScope(7, parserLogFile);
				    }
				    symbolTable.exitScope(parserLogFile, 1);

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
		enterRule(_localctx, 16, RULE_var_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			((Var_declarationContext)_localctx).t = type_specifier();
			setState(159);
			((Var_declarationContext)_localctx).d = declaration_list(0);
			setState(160);
			((Var_declarationContext)_localctx).sm = match(SEMICOLON);

			        int varTypeIsVoid = 0;
			        lineCount = ((Var_declarationContext)_localctx).sm->getLine();
			        
			        if (getTypeName(((Var_declarationContext)_localctx).t) == "VOID") 
			        {
			            writeIntoErrorFile("Error at line " + to_string(((Var_declarationContext)_localctx).sm->getLine()) + ": Variable type cannot be void");
			            syntaxErrorCount++;
			            varTypeIsVoid = 1;
			        }
			        
			        writeIntoparserLogFile("Line " + to_string(lineCount) + ": var_declaration : type_specifier declaration_list SEMICOLON");
			        writeIntoparserLogFile("");
			        if (varTypeIsVoid) 
			        {
			            writeIntoparserLogFile("Error at line " + to_string(((Var_declarationContext)_localctx).sm->getLine()) + ": Variable type cannot be void");
			            writeIntoparserLogFile("");
			        }
			        writeIntoparserLogFile(formatDeclaration((((Var_declarationContext)_localctx).t!=null?_input.getText(((Var_declarationContext)_localctx).t.start,((Var_declarationContext)_localctx).t.stop):null) + " " + (((Var_declarationContext)_localctx).d!=null?_input.getText(((Var_declarationContext)_localctx).d.start,((Var_declarationContext)_localctx).d.stop):null) + ";"));
			        writeIntoparserLogFile("");
			    
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
		enterRule(_localctx, 18, RULE_type_specifier);
		try {
			setState(169);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(163);
				((Type_specifierContext)_localctx).INT = match(INT);

				        lineCount = ((Type_specifierContext)_localctx).INT->getLine();
				        logRule("type_specifier : INT", (((Type_specifierContext)_localctx).INT!=null?((Type_specifierContext)_localctx).INT.getText():null));
				    
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(165);
				((Type_specifierContext)_localctx).FLOAT = match(FLOAT);

				        lineCount = ((Type_specifierContext)_localctx).FLOAT->getLine();
				        logRule("type_specifier : FLOAT", (((Type_specifierContext)_localctx).FLOAT!=null?((Type_specifierContext)_localctx).FLOAT.getText():null));
				    
				}
				break;
			case VOID:
				enterOuterAlt(_localctx, 3);
				{
				setState(167);
				((Type_specifierContext)_localctx).VOID = match(VOID);

				        lineCount = ((Type_specifierContext)_localctx).VOID->getLine();
				        logRule("type_specifier : VOID", (((Type_specifierContext)_localctx).VOID!=null?((Type_specifierContext)_localctx).VOID.getText():null));
				    
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
		public Declaration_itemContext declaration_item;
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
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_declaration_list, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(172);
			((Declaration_listContext)_localctx).declaration_item = declaration_item();
			       
			        writeIntoparserLogFile("Line " + to_string(lineCount) + ": declaration_list : " + ((Declaration_listContext)_localctx).declaration_item.ruleName);
			        writeIntoparserLogFile("");
			        writeIntoparserLogFile((((Declaration_listContext)_localctx).declaration_item!=null?_input.getText(((Declaration_listContext)_localctx).declaration_item.start,((Declaration_listContext)_localctx).declaration_item.stop):null));
			        writeIntoparserLogFile("");
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(182);
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
					setState(175);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(176);
					match(COMMA);
					setState(177);
					((Declaration_listContext)_localctx).declaration_item = declaration_item();

					                  // getting the actual rulename of the declaration_item
					                  writeIntoparserLogFile("Line " + to_string(lineCount) + ": declaration_list : declaration_list COMMA " + ((Declaration_listContext)_localctx).declaration_item.ruleName);
					                  writeIntoparserLogFile("");
					                  // getting the ID name of the item
					                  writeIntoparserLogFile((((Declaration_listContext)_localctx).d!=null?_input.getText(((Declaration_listContext)_localctx).d.start,((Declaration_listContext)_localctx).d.stop):null) + "," + (((Declaration_listContext)_localctx).declaration_item!=null?_input.getText(((Declaration_listContext)_localctx).declaration_item.start,((Declaration_listContext)_localctx).declaration_item.stop):null));
					                  writeIntoparserLogFile("");
					              
					}
					} 
				}
				setState(184);
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
		public Token ADDOP;
		public List<TerminalNode> ID() { return getTokens(C2105047Parser.ID); }
		public TerminalNode ID(int i) {
			return getToken(C2105047Parser.ID, i);
		}
		public TerminalNode LTHIRD() { return getToken(C2105047Parser.LTHIRD, 0); }
		public TerminalNode CONST_INT() { return getToken(C2105047Parser.CONST_INT, 0); }
		public TerminalNode RTHIRD() { return getToken(C2105047Parser.RTHIRD, 0); }
		public TerminalNode ADDOP() { return getToken(C2105047Parser.ADDOP, 0); }
		public Declaration_itemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration_item; }
	}

	public final Declaration_itemContext declaration_item() throws RecognitionException {
		Declaration_itemContext _localctx = new Declaration_itemContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_declaration_item);
		try {
			setState(196);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(185);
				((Declaration_itemContext)_localctx).ID = match(ID);

				        // this is simple ID declaration 
				        int hasMultipleDeclarationError = 0;
				        lineCount = ((Declaration_itemContext)_localctx).ID->getLine();
				        // declaration_item's parent is declaration_list, which is parent of var_declaration
				        // we are getting the type specifier a.k.a.   t    of the var_declaration rule  
				        SymbolInfo* symbol = new SymbolInfo((((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null), getTypeName(dynamic_cast<Var_declarationContext*>(_ctx->parent->parent)->t));
				        if (!symbolTable.insert(*symbol, parserLogFile)) 
				        {
				            hasMultipleDeclarationError = 1;
				            syntaxErrorCount++;
				            writeIntoErrorFile("Error at line " + to_string(((Declaration_itemContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null));
				        }
				    
				        ((Declaration_itemContext)_localctx).ruleName =  "ID";

				        if (hasMultipleDeclarationError) 
				        {
				            writeIntoparserLogFile("Error at line " + to_string(((Declaration_itemContext)_localctx).ID->getLine()) + ": Multiple declaration of " + (((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        }
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(187);
				((Declaration_itemContext)_localctx).ID = match(ID);
				setState(188);
				match(LTHIRD);
				setState(189);
				((Declaration_itemContext)_localctx).CONST_INT = match(CONST_INT);
				setState(190);
				((Declaration_itemContext)_localctx).RTHIRD = match(RTHIRD);

				        // this is an array declaration with size
				        int hasMultipleDeclarationError = 0;
				        lineCount = ((Declaration_itemContext)_localctx).RTHIRD->getLine();
				        SymbolInfo* symbol = new SymbolInfo((((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null), getTypeName(dynamic_cast<Var_declarationContext*>(_ctx->parent->parent)->t));
				        symbol->setIsArray(true);
				        symbol->setArraySize(stoi((((Declaration_itemContext)_localctx).CONST_INT!=null?((Declaration_itemContext)_localctx).CONST_INT.getText():null)));
				        if (!symbolTable.insert(*symbol, parserLogFile)) 
				        {
				            hasMultipleDeclarationError = 1;
				            syntaxErrorCount++;
				            writeIntoErrorFile("Error at line " + to_string(((Declaration_itemContext)_localctx).CONST_INT->getLine()) + ": Multiple declaration of " + (((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null));
				        }
				        ((Declaration_itemContext)_localctx).ruleName =  "ID LTHIRD CONST_INT RTHIRD";

				        if (hasMultipleDeclarationError) 
				        {
				            writeIntoparserLogFile("Error at line " + to_string(((Declaration_itemContext)_localctx).CONST_INT->getLine()) + ": Multiple declaration of " + (((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        }
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(192);
				((Declaration_itemContext)_localctx).ID = match(ID);
				setState(193);
				((Declaration_itemContext)_localctx).ADDOP = match(ADDOP);
				setState(194);
				((Declaration_itemContext)_localctx).ID = match(ID);

				        // Error case: x-y
				        lineCount = ((Declaration_itemContext)_localctx).ADDOP->getLine();
				        syntaxErrorCount++;
				        writeIntoErrorFile("Error at line " + to_string(((Declaration_itemContext)_localctx).ADDOP->getLine()) + ": syntax error, unexpected ADDOP, expecting COMMA or SEMICOLON");
				        
				        ((Declaration_itemContext)_localctx).ruleName =  "ID"; // For error case, just send ID since that's what was expected

				        // print rule header first
				        // writeIntoparserLogFile("Line " + to_string(lineCount) + ": declaration_item : ID ADDOP ID");
				        // writeIntoparserLogFile("");
				        // print the error message
				        writeIntoparserLogFile("Error at line " + to_string(((Declaration_itemContext)_localctx).ADDOP->getLine()) + ": syntax error, unexpected ADDOP, expecting COMMA or SEMICOLON");
				        writeIntoparserLogFile("");
				        // then print the code
				        // writeIntoparserLogFile(removeUnnecessarySpaces((((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null) + " " + (((Declaration_itemContext)_localctx).ADDOP!=null?((Declaration_itemContext)_localctx).ADDOP.getText():null) + " " + (((Declaration_itemContext)_localctx).ID!=null?((Declaration_itemContext)_localctx).ID.getText():null)));
				        // writeIntoparserLogFile("");
				    
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
		public StatementContext statement;
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
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_statements, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(199);
			((StatementsContext)_localctx).statement = statement();

			        logRule("statements : statement",removeEmptyLines(formatFunctionBlock((((StatementsContext)_localctx).statement!=null?_input.getText(((StatementsContext)_localctx).statement.start,((StatementsContext)_localctx).statement.stop):null))));
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(208);
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
					setState(202);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(203);
					((StatementsContext)_localctx).statement = statement();

					                  logRule("statements : statements statement", removeEmptyLines(formatFunctionBlock((((StatementsContext)_localctx).s!=null?_input.getText(((StatementsContext)_localctx).s.start,((StatementsContext)_localctx).s.stop):null) + "\n" + (((StatementsContext)_localctx).statement!=null?_input.getText(((StatementsContext)_localctx).statement.start,((StatementsContext)_localctx).statement.stop):null))));
					              
					}
					} 
				}
				setState(210);
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
		public Var_declarationContext var_declaration;
		public Expression_statementContext expression_statement;
		public Compound_statementContext compound_statement;
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
		enterRule(_localctx, 26, RULE_statement);
		try {
			setState(263);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(211);
				((StatementContext)_localctx).var_declaration = var_declaration();

				    logRule("statement : var_declaration", removeEmptyLines(formatFunctionBlock((((StatementContext)_localctx).var_declaration!=null?_input.getText(((StatementContext)_localctx).var_declaration.start,((StatementContext)_localctx).var_declaration.stop):null))));

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(214);
				((StatementContext)_localctx).expression_statement = expression_statement();

				    logRule("statement : expression_statement", removeUnnecessarySpaces((((StatementContext)_localctx).expression_statement!=null?_input.getText(((StatementContext)_localctx).expression_statement.start,((StatementContext)_localctx).expression_statement.stop):null)));

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(217);
				((StatementContext)_localctx).compound_statement = compound_statement();

				    logRule("statement : compound_statement", removeEmptyLines(formatFunctionBlock((((StatementContext)_localctx).compound_statement!=null?_input.getText(((StatementContext)_localctx).compound_statement.start,((StatementContext)_localctx).compound_statement.stop):null))));

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(220);
				match(FOR);
				setState(221);
				match(LPAREN);
				setState(222);
				((StatementContext)_localctx).es1 = expression_statement();
				setState(223);
				((StatementContext)_localctx).es2 = expression_statement();
				setState(224);
				((StatementContext)_localctx).expression = expression();
				setState(225);
				match(RPAREN);
				setState(226);
				((StatementContext)_localctx).statement = statement();

				    //this is a FOR loop
				    // because es1 and es2 has a semicolon at the end, and expression has no semicolon
				    // after that there could be a simple statement or a compound statement or any kind of loop statement i.e. nested for, while, if etc
				    lineCount= (((StatementContext)_localctx).statement!=null?(((StatementContext)_localctx).statement.stop):null)->getLine();
				    string forBody = removeEmptyLines(formatFunctionBlock((((StatementContext)_localctx).statement!=null?_input.getText(((StatementContext)_localctx).statement.start,((StatementContext)_localctx).statement.stop):null)));

				    logRule("statement : FOR LPAREN expression_statement expression_statement expression RPAREN statement", "for(" + removeUnnecessarySpaces((((StatementContext)_localctx).es1!=null?_input.getText(((StatementContext)_localctx).es1.start,((StatementContext)_localctx).es1.stop):null)) + removeUnnecessarySpaces((((StatementContext)_localctx).es2!=null?_input.getText(((StatementContext)_localctx).es2.start,((StatementContext)_localctx).es2.stop):null)) + removeUnnecessarySpaces((((StatementContext)_localctx).expression!=null?_input.getText(((StatementContext)_localctx).expression.start,((StatementContext)_localctx).expression.stop):null)) + ")" + forBody);

				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(229);
				match(IF);
				setState(230);
				match(LPAREN);
				setState(231);
				((StatementContext)_localctx).expression = expression();
				setState(232);
				match(RPAREN);
				setState(233);
				((StatementContext)_localctx).statement = statement();

				    // this is an IF statement , could  be a simple IF statement or an IF with compound statement i.e. IF block
				    lineCount = (((StatementContext)_localctx).statement!=null?(((StatementContext)_localctx).statement.stop):null)->getLine();
				    string ifBody = removeEmptyLines(formatFunctionBlock((((StatementContext)_localctx).statement!=null?_input.getText(((StatementContext)_localctx).statement.start,((StatementContext)_localctx).statement.stop):null)));

				    logRule("statement : IF LPAREN expression RPAREN statement", "if(" + removeUnnecessarySpaces((((StatementContext)_localctx).expression!=null?_input.getText(((StatementContext)_localctx).expression.start,((StatementContext)_localctx).expression.stop):null)) + ")" + ifBody);

				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(236);
				match(IF);
				setState(237);
				match(LPAREN);
				setState(238);
				((StatementContext)_localctx).expression = expression();
				setState(239);
				match(RPAREN);
				setState(240);
				((StatementContext)_localctx).s1 = statement();
				setState(241);
				match(ELSE);
				setState(242);
				((StatementContext)_localctx).s2 = statement();

				    //IF-ELSE, each can have simple statements or compound statements
				    lineCount= (((StatementContext)_localctx).s2!=null?(((StatementContext)_localctx).s2.stop):null)->getLine();
				    string ifBody = removeEmptyLines(formatFunctionBlock((((StatementContext)_localctx).s1!=null?_input.getText(((StatementContext)_localctx).s1.start,((StatementContext)_localctx).s1.stop):null)));
				    string elseBody = removeEmptyLines(formatFunctionBlock((((StatementContext)_localctx).s2!=null?_input.getText(((StatementContext)_localctx).s2.start,((StatementContext)_localctx).s2.stop):null)));

				    logRule("statement : IF LPAREN expression RPAREN statement ELSE statement", "if(" + removeUnnecessarySpaces((((StatementContext)_localctx).expression!=null?_input.getText(((StatementContext)_localctx).expression.start,((StatementContext)_localctx).expression.stop):null)) + ")" + ifBody + "\nelse " + elseBody);

				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(245);
				match(WHILE);
				setState(246);
				match(LPAREN);
				setState(247);
				((StatementContext)_localctx).expression = expression();
				setState(248);
				match(RPAREN);
				setState(249);
				((StatementContext)_localctx).statement = statement();

				    // this is a WHILE loop, because expression does not have a semicolon at the end
				    // and statement can be a simple statement or a compound statement i.e. WHILE block
				    lineCount= (((StatementContext)_localctx).statement!=null?(((StatementContext)_localctx).statement.stop):null)->getLine();
				    string whileBody = removeEmptyLines(formatFunctionBlock((((StatementContext)_localctx).statement!=null?_input.getText(((StatementContext)_localctx).statement.start,((StatementContext)_localctx).statement.stop):null)));
				    
				    logRule("statement : WHILE LPAREN expression RPAREN statement", "while(" + removeUnnecessarySpaces((((StatementContext)_localctx).expression!=null?_input.getText(((StatementContext)_localctx).expression.start,((StatementContext)_localctx).expression.stop):null)) + ")" + whileBody);

				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(252);
				match(PRINTLN);
				setState(253);
				match(LPAREN);
				setState(254);
				((StatementContext)_localctx).ID = match(ID);
				setState(255);
				match(RPAREN);
				setState(256);
				((StatementContext)_localctx).SEMICOLON = match(SEMICOLON);

				    // this is allowing printf statements 
				    lineCount = ((StatementContext)_localctx).SEMICOLON->getLine();
				    
				    int undeclaredVariableError = 0;
				    // Check if variable is declared for printf
				    SymbolInfo* symbol = symbolTable.lookUp((((StatementContext)_localctx).ID!=null?((StatementContext)_localctx).ID.getText():null), parserLogFile);
				    if (!symbol) 
				    {
				        writeIntoErrorFile("Error at line " + to_string(((StatementContext)_localctx).SEMICOLON->getLine()) + ": Undeclared variable " + (((StatementContext)_localctx).ID!=null?((StatementContext)_localctx).ID.getText():null));
				        syntaxErrorCount++;
				        undeclaredVariableError = 1;
				    }
				    
				    //logRule("statement : PRINTLN LPAREN ID RPAREN SEMICOLON", "printf(" + (((StatementContext)_localctx).ID!=null?((StatementContext)_localctx).ID.getText():null) + ");");

				    // Print the rule header
				    writeIntoparserLogFile("Line " + to_string(lineCount) + ": statement : PRINTLN LPAREN ID RPAREN SEMICOLON");
				    writeIntoparserLogFile("");
				    // print the error if any
				    if (undeclaredVariableError) 
				    {
				        writeIntoparserLogFile("Error at line " + to_string(((StatementContext)_localctx).SEMICOLON->getLine()) + ": Undeclared variable " + (((StatementContext)_localctx).ID!=null?((StatementContext)_localctx).ID.getText():null));
				        writeIntoparserLogFile("");
				    }
				    // then print the code
				    writeIntoparserLogFile("printf(" + (((StatementContext)_localctx).ID!=null?((StatementContext)_localctx).ID.getText():null) + ");");
				    writeIntoparserLogFile("");

				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(258);
				match(RETURN);
				setState(259);
				((StatementContext)_localctx).expression = expression();
				setState(260);
				((StatementContext)_localctx).SEMICOLON = match(SEMICOLON);

				    // these are return statements, which are used in functions
				    lineCount = ((StatementContext)_localctx).SEMICOLON->getLine();
				    int voidReturnTypeError = 0;
				    
				    // Check if a function whose return type is void is trying to return a value
				    if (currentFunctionReturnType == "VOID") 
				    {
				        writeIntoErrorFile("Error at line " + to_string(((StatementContext)_localctx).SEMICOLON->getLine()) + ": Cannot return value from function " + currentFunctionName + " with void return type ");      
				        syntaxErrorCount++;
				        voidReturnTypeError = 1;
				    }
				    
				    // Print the rule header
				    writeIntoparserLogFile("Line " + to_string(lineCount) + ": statement : RETURN expression SEMICOLON");
				    writeIntoparserLogFile("");
				    // print the error if any
				    if (voidReturnTypeError) 
				    {
				        writeIntoparserLogFile("Error at line " + to_string(((StatementContext)_localctx).SEMICOLON->getLine()) + ": Cannot return value from function " + currentFunctionName + " with void return type ");
				        writeIntoparserLogFile("");
				    }
				    // then print the code
				    writeIntoparserLogFile("return " + removeUnnecessarySpaces((((StatementContext)_localctx).expression!=null?_input.getText(((StatementContext)_localctx).expression.start,((StatementContext)_localctx).expression.stop):null)) + ";");
				    writeIntoparserLogFile("");

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
		public ExpressionContext expression;
		public Token CONST_INT;
		public TerminalNode SEMICOLON() { return getToken(C2105047Parser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CONST_INT() { return getToken(C2105047Parser.CONST_INT, 0); }
		public Expression_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression_statement; }
	}

	public final Expression_statementContext expression_statement() throws RecognitionException {
		Expression_statementContext _localctx = new Expression_statementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_expression_statement);
		try {
			setState(275);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(265);
				((Expression_statementContext)_localctx).SEMICOLON = match(SEMICOLON);

				        lineCount = ((Expression_statementContext)_localctx).SEMICOLON->getLine();
				        logRule("expression_statement : SEMICOLON", ";");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(267);
				((Expression_statementContext)_localctx).expression = expression();
				setState(268);
				((Expression_statementContext)_localctx).SEMICOLON = match(SEMICOLON);

				        lineCount = ((Expression_statementContext)_localctx).SEMICOLON->getLine();
				        logRule("expression_statement : expression SEMICOLON", removeUnnecessarySpaces((((Expression_statementContext)_localctx).expression!=null?_input.getText(((Expression_statementContext)_localctx).expression.start,((Expression_statementContext)_localctx).expression.stop):null)) + ";");
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(271);
				((Expression_statementContext)_localctx).expression = expression();
				setState(272);
				((Expression_statementContext)_localctx).CONST_INT = match(CONST_INT);

				        // Error case: expression followed by number without semicolon like "2 + = 6"
				        lineCount = ((Expression_statementContext)_localctx).CONST_INT->getLine();
				        syntaxErrorCount++;
				        // was not in sample errors but need to  catch it so that antlr4 lets us run input5
				        writeIntoErrorFile("Error at line " + to_string(((Expression_statementContext)_localctx).CONST_INT->getLine()) + ": missing ';' at '" + (((Expression_statementContext)_localctx).CONST_INT!=null?((Expression_statementContext)_localctx).CONST_INT.getText():null) + "'"); 
				        logRule("expression_statement : expression", removeUnnecessarySpaces((((Expression_statementContext)_localctx).expression!=null?_input.getText(((Expression_statementContext)_localctx).expression.start,((Expression_statementContext)_localctx).expression.stop):null)));
				    
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
	public static class VariableContext extends ParserRuleContext {
		public string type;
		public bool hasIndexError;
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
		enterRule(_localctx, 30, RULE_variable);
		try {
			setState(285);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(277);
				((VariableContext)_localctx).ID = match(ID);

				        int undeclaredVariableError = 0;
				        lineCount = ((VariableContext)_localctx).ID->getLine();
				        SymbolInfo* symbol = symbolTable.lookUp((((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null), parserLogFile);
				        if (!symbol) 
				        {
				            undeclaredVariableError = 1;
				            syntaxErrorCount++;
				            ((VariableContext)_localctx).type =  "UNKNOWN";
				        } 
				        else 
				        {
				            ((VariableContext)_localctx).type =  symbol->getIsArray() ? "ARRAY" : symbol->getType(); //if it is an array, set type to ARRAY, else set type to its type
				        }
				        
				        ((VariableContext)_localctx).hasIndexError =  false; // No index error for simple ID
				        
				        writeIntoparserLogFile("Line " + to_string(lineCount) + ": variable : ID");
				        writeIntoparserLogFile("");
				        
				        if (undeclaredVariableError) 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((VariableContext)_localctx).ID->getLine()) + ": Undeclared variable " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("Error at line " + to_string(((VariableContext)_localctx).ID->getLine()) + ": Undeclared variable " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        }
				        
				        writeIntoparserLogFile((((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null));
				        writeIntoparserLogFile("");
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(279);
				((VariableContext)_localctx).ID = match(ID);
				setState(280);
				match(LTHIRD);
				setState(281);
				((VariableContext)_localctx).expression = expression();
				setState(282);
				((VariableContext)_localctx).RTHIRD = match(RTHIRD);

				        // like a[0]
				        int undeclaredVariableError = 0;
				        int notAnArrayError = 0;
				        int arrayIndexNotIntegerError = 0;
				        lineCount = ((VariableContext)_localctx).RTHIRD->getLine();
				        SymbolInfo* symbol = symbolTable.lookUp((((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null), parserLogFile);
				        if (!symbol) 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((VariableContext)_localctx).RTHIRD->getLine()) + ": Undeclared variable " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null));
				            undeclaredVariableError = 1;
				            syntaxErrorCount++;
				            ((VariableContext)_localctx).type =  "UNKNOWN";
				            ((VariableContext)_localctx).hasIndexError =  false;
				        } 
				        else if (!symbol->getIsArray()) 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((VariableContext)_localctx).RTHIRD->getLine()) + ": " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null) + " not an array");
				            notAnArrayError = 1;
				            syntaxErrorCount++;
				            ((VariableContext)_localctx).type =  symbol->getType();
				            ((VariableContext)_localctx).hasIndexError =  true;
				        } 
				        else if (((VariableContext)_localctx).expression.type != "CONST_INT") 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((VariableContext)_localctx).RTHIRD->getLine()) + ": Expression inside third brackets not an integer");
				            arrayIndexNotIntegerError = 1;
				            syntaxErrorCount++;
				            ((VariableContext)_localctx).type =  symbol->getType();
				            ((VariableContext)_localctx).hasIndexError =  true; // FLAG: We have an index error
				        } 
				        else 
				        {
				            ((VariableContext)_localctx).type =  symbol->getType();
				            ((VariableContext)_localctx).hasIndexError =  false;
				        }
				        
				        // Print rule header first
				        writeIntoparserLogFile("Line " + to_string(lineCount) + ": variable : ID LTHIRD expression RTHIRD");
				        writeIntoparserLogFile("");
				        
				        if (undeclaredVariableError) {
				            writeIntoparserLogFile("Error at line " + to_string(((VariableContext)_localctx).RTHIRD->getLine()) + ": Undeclared variable " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        } else if (notAnArrayError) {
				            writeIntoparserLogFile("Error at line " + to_string(((VariableContext)_localctx).RTHIRD->getLine()) + ": " + (((VariableContext)_localctx).ID!=null?((VariableContext)_localctx).ID.getText():null) + " not an array");
				            writeIntoparserLogFile("");
				        } else if (arrayIndexNotIntegerError) {
				            writeIntoparserLogFile("Error at line " + to_string(((VariableContext)_localctx).RTHIRD->getLine()) + ": Expression inside third brackets not an integer");
				            writeIntoparserLogFile("");
				        }
				        
				        // Print code last
				        writeIntoparserLogFile(_localctx->getText());
				        writeIntoparserLogFile("");
				    
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
		public Logic_expressionContext l;
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
		enterRule(_localctx, 32, RULE_expression);
		try {
			setState(296);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(287);
				((ExpressionContext)_localctx).logic_expression = logic_expression();

				        ((ExpressionContext)_localctx).type =  ((ExpressionContext)_localctx).logic_expression.type;
				        lineCount = (((ExpressionContext)_localctx).logic_expression!=null?(((ExpressionContext)_localctx).logic_expression.start):null)->getLine();
				        logRule("expression : logic_expression", removeUnnecessarySpaces((((ExpressionContext)_localctx).logic_expression!=null?_input.getText(((ExpressionContext)_localctx).logic_expression.start,((ExpressionContext)_localctx).logic_expression.stop):null)));
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(290);
				((ExpressionContext)_localctx).variable = variable();
				setState(291);
				((ExpressionContext)_localctx).ASSIGNOP = match(ASSIGNOP);

				        // Check for array assignment error immediately after ASSIGNOP
				        int hasError = 0;
				        lineCount = ((ExpressionContext)_localctx).ASSIGNOP->getLine();
				        if (((ExpressionContext)_localctx).variable.type == "ARRAY") 
				        {
				            hasError = 1;
				            syntaxErrorCount++;
				            
				            // Print error immediately (no rule header yet)
				            writeIntoErrorFile("Error at line " + to_string(((ExpressionContext)_localctx).ASSIGNOP->getLine()) + ": Type mismatch, " + (((ExpressionContext)_localctx).variable!=null?_input.getText(((ExpressionContext)_localctx).variable.start,((ExpressionContext)_localctx).variable.stop):null) + " is an array");
				            writeIntoparserLogFile("Error at line " + to_string(((ExpressionContext)_localctx).ASSIGNOP->getLine()) + ": Type mismatch, " + (((ExpressionContext)_localctx).variable!=null?_input.getText(((ExpressionContext)_localctx).variable.start,((ExpressionContext)_localctx).variable.stop):null) + " is an array");
				            writeIntoparserLogFile("");
				        }
				    
				setState(293);
				((ExpressionContext)_localctx).l = logic_expression();

				        int hasTypeMismatchError = 0;
				        //like a=a+y*2
				        // Handle other type compatibility errors ONLY if no array index error AND no arithmetic error AND no void function error
				        // because they have been reported in their respective rules
				        if (((ExpressionContext)_localctx).variable.type != "ARRAY" && ((ExpressionContext)_localctx).variable.type != "UNKNOWN" && ((ExpressionContext)_localctx).l.type != "UNKNOWN" && !((ExpressionContext)_localctx).variable.hasIndexError && !((ExpressionContext)_localctx).l.hasArithmeticError) 
				        {
				            // Don't trigger type mismatch if we already have void function used in expression error
				            bool hasVoidFunctionError = (((ExpressionContext)_localctx).l.type == "VOID");
				            
				            if (!hasVoidFunctionError && !isTypeCompatible(((ExpressionContext)_localctx).variable.type, ((ExpressionContext)_localctx).l.type)) 
				            {
				                syntaxErrorCount++;
				                hasTypeMismatchError = 1;
				            }
				        }
				        
				        ((ExpressionContext)_localctx).type =  ((ExpressionContext)_localctx).variable.type;
				        
				        // Print rule header and code AFTER all error checking
				        writeIntoparserLogFile("Line " + to_string(lineCount) + ": expression : variable ASSIGNOP logic_expression");
				        writeIntoparserLogFile("");
				        if(hasTypeMismatchError) 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((ExpressionContext)_localctx).ASSIGNOP->getLine()) + ": Type Mismatch");
				            writeIntoparserLogFile("Error at line " + to_string(((ExpressionContext)_localctx).ASSIGNOP->getLine()) + ": Type Mismatch");
				            writeIntoparserLogFile("");
				        }
				        writeIntoparserLogFile(removeUnnecessarySpaces((((ExpressionContext)_localctx).variable!=null?_input.getText(((ExpressionContext)_localctx).variable.start,((ExpressionContext)_localctx).variable.stop):null) + "=" + (((ExpressionContext)_localctx).l!=null?_input.getText(((ExpressionContext)_localctx).l.start,((ExpressionContext)_localctx).l.stop):null)));
				        writeIntoparserLogFile("");
				    
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
		enterRule(_localctx, 34, RULE_logic_expression);
		try {
			setState(306);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(298);
				((Logic_expressionContext)_localctx).rel_expression = rel_expression();

				        ((Logic_expressionContext)_localctx).type =  ((Logic_expressionContext)_localctx).rel_expression.type;
				        ((Logic_expressionContext)_localctx).hasArithmeticError =  ((Logic_expressionContext)_localctx).rel_expression.hasArithmeticError;
				        lineCount = (((Logic_expressionContext)_localctx).rel_expression!=null?(((Logic_expressionContext)_localctx).rel_expression.start):null)->getLine();
				        logRule("logic_expression : rel_expression", removeUnnecessarySpaces((((Logic_expressionContext)_localctx).rel_expression!=null?_input.getText(((Logic_expressionContext)_localctx).rel_expression.start,((Logic_expressionContext)_localctx).rel_expression.stop):null)));
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(301);
				((Logic_expressionContext)_localctx).r1 = rel_expression();
				setState(302);
				((Logic_expressionContext)_localctx).LOGICOP = match(LOGICOP);
				setState(303);
				((Logic_expressionContext)_localctx).r2 = rel_expression();

				        // like 5%3<4&&8 in j= 2*3+(5%3 < 4 && 8) || 2 ;
				        lineCount = ((Logic_expressionContext)_localctx).LOGICOP->getLine();
				        ((Logic_expressionContext)_localctx).type =  "INT";
				        ((Logic_expressionContext)_localctx).hasArithmeticError =  ((Logic_expressionContext)_localctx).r1.hasArithmeticError || ((Logic_expressionContext)_localctx).r2.hasArithmeticError;
				        logRule("logic_expression : rel_expression LOGICOP rel_expression", removeUnnecessarySpaces((((Logic_expressionContext)_localctx).r1!=null?_input.getText(((Logic_expressionContext)_localctx).r1.start,((Logic_expressionContext)_localctx).r1.stop):null) + (((Logic_expressionContext)_localctx).LOGICOP!=null?((Logic_expressionContext)_localctx).LOGICOP.getText():null) + (((Logic_expressionContext)_localctx).r2!=null?_input.getText(((Logic_expressionContext)_localctx).r2.start,((Logic_expressionContext)_localctx).r2.stop):null)));
				    
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
		enterRule(_localctx, 36, RULE_rel_expression);
		try {
			setState(316);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(308);
				((Rel_expressionContext)_localctx).simple_expression = simple_expression(0);

				        ((Rel_expressionContext)_localctx).type =  ((Rel_expressionContext)_localctx).simple_expression.type;
				        ((Rel_expressionContext)_localctx).hasArithmeticError =  ((Rel_expressionContext)_localctx).simple_expression.hasArithmeticError;
				        lineCount = (((Rel_expressionContext)_localctx).simple_expression!=null?(((Rel_expressionContext)_localctx).simple_expression.start):null)->getLine();
				        logRule("rel_expression : simple_expression", removeUnnecessarySpaces((((Rel_expressionContext)_localctx).simple_expression!=null?_input.getText(((Rel_expressionContext)_localctx).simple_expression.start,((Rel_expressionContext)_localctx).simple_expression.stop):null)));
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(311);
				((Rel_expressionContext)_localctx).s1 = simple_expression(0);
				setState(312);
				((Rel_expressionContext)_localctx).RELOP = match(RELOP);
				setState(313);
				((Rel_expressionContext)_localctx).s2 = simple_expression(0);

				        // like c<a[0] in if(c<a[0])
				        lineCount = ((Rel_expressionContext)_localctx).RELOP->getLine();
				        ((Rel_expressionContext)_localctx).type =  "INT";
				        ((Rel_expressionContext)_localctx).hasArithmeticError =  ((Rel_expressionContext)_localctx).s1.hasArithmeticError || ((Rel_expressionContext)_localctx).s2.hasArithmeticError;
				        logRule("rel_expression : simple_expression RELOP simple_expression", removeUnnecessarySpaces((((Rel_expressionContext)_localctx).s1!=null?_input.getText(((Rel_expressionContext)_localctx).s1.start,((Rel_expressionContext)_localctx).s1.stop):null) + (((Rel_expressionContext)_localctx).RELOP!=null?((Rel_expressionContext)_localctx).RELOP.getText():null) + (((Rel_expressionContext)_localctx).s2!=null?_input.getText(((Rel_expressionContext)_localctx).s2.start,((Rel_expressionContext)_localctx).s2.stop):null)));
				    
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
		public Token ASSIGNOP;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TerminalNode ADDOP() { return getToken(C2105047Parser.ADDOP, 0); }
		public Simple_expressionContext simple_expression() {
			return getRuleContext(Simple_expressionContext.class,0);
		}
		public TerminalNode ASSIGNOP() { return getToken(C2105047Parser.ASSIGNOP, 0); }
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
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_simple_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(319);
			((Simple_expressionContext)_localctx).term = term(0);

			        ((Simple_expressionContext)_localctx).type =  ((Simple_expressionContext)_localctx).term.type;
			        ((Simple_expressionContext)_localctx).hasArithmeticError =  ((Simple_expressionContext)_localctx).term.hasArithmeticError;
			        lineCount = (((Simple_expressionContext)_localctx).term!=null?(((Simple_expressionContext)_localctx).term.start):null)->getLine();
			        logRule("simple_expression : term", removeUnnecessarySpaces((((Simple_expressionContext)_localctx).term!=null?_input.getText(((Simple_expressionContext)_localctx).term.start,((Simple_expressionContext)_localctx).term.stop):null)));
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(333);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(331);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new Simple_expressionContext(_parentctx, _parentState);
						_localctx.s = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_simple_expression);
						setState(322);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(323);
						((Simple_expressionContext)_localctx).ADDOP = match(ADDOP);
						setState(324);
						((Simple_expressionContext)_localctx).t = ((Simple_expressionContext)_localctx).term = term(0);

						                  // like a+y*2 in a = a + y * 2;
						                  lineCount = ((Simple_expressionContext)_localctx).ADDOP->getLine();
						                  ((Simple_expressionContext)_localctx).type =  (((Simple_expressionContext)_localctx).s.type == "FLOAT" || ((Simple_expressionContext)_localctx).t.type == "FLOAT") ? "FLOAT" : "INT"; // if at least one side is float, result is float, else int
						                  ((Simple_expressionContext)_localctx).hasArithmeticError =  ((Simple_expressionContext)_localctx).s.hasArithmeticError || ((Simple_expressionContext)_localctx).t.hasArithmeticError;
						                  logRule("simple_expression : simple_expression ADDOP term", removeUnnecessarySpaces((((Simple_expressionContext)_localctx).s!=null?_input.getText(((Simple_expressionContext)_localctx).s.start,((Simple_expressionContext)_localctx).s.stop):null) + (((Simple_expressionContext)_localctx).ADDOP!=null?((Simple_expressionContext)_localctx).ADDOP.getText():null) + (((Simple_expressionContext)_localctx).t!=null?_input.getText(((Simple_expressionContext)_localctx).t.start,((Simple_expressionContext)_localctx).t.stop):null)));
						              
						}
						break;
					case 2:
						{
						_localctx = new Simple_expressionContext(_parentctx, _parentState);
						_localctx.s = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_simple_expression);
						setState(327);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(328);
						((Simple_expressionContext)_localctx).ADDOP = match(ADDOP);
						setState(329);
						((Simple_expressionContext)_localctx).ASSIGNOP = match(ASSIGNOP);

						                  // Error case: 2 + =
						                  lineCount = ((Simple_expressionContext)_localctx).ASSIGNOP->getLine();
						                  syntaxErrorCount++;
						                  writeIntoErrorFile("Error at line " + to_string(((Simple_expressionContext)_localctx).ASSIGNOP->getLine()) + ": syntax error, unexpected ASSIGNOP");
						                  ((Simple_expressionContext)_localctx).type =  "INT";
						                  ((Simple_expressionContext)_localctx).hasArithmeticError =  true;
						                  //logRule("simple_expression : term", removeUnnecessarySpaces((((Simple_expressionContext)_localctx).s!=null?_input.getText(((Simple_expressionContext)_localctx).s.start,((Simple_expressionContext)_localctx).s.stop):null)));
						                  writeIntoparserLogFile("Line " + to_string(lineCount) + ": simple_expression : term");
						                  writeIntoparserLogFile("");
						                  writeIntoparserLogFile("Error at line " + to_string(((Simple_expressionContext)_localctx).ASSIGNOP->getLine()) + ": syntax error, unexpected ASSIGNOP");
						                  writeIntoparserLogFile("");
						                  //writeIntoparserLogFile(removeUnnecessarySpaces((((Simple_expressionContext)_localctx).s!=null?_input.getText(((Simple_expressionContext)_localctx).s.start,((Simple_expressionContext)_localctx).s.stop):null) + " " + (((Simple_expressionContext)_localctx).ADDOP!=null?((Simple_expressionContext)_localctx).ADDOP.getText():null) + " " + (((Simple_expressionContext)_localctx).ASSIGNOP!=null?((Simple_expressionContext)_localctx).ASSIGNOP.getText():null)));
						                  writeIntoparserLogFile(removeUnnecessarySpaces((((Simple_expressionContext)_localctx).s!=null?_input.getText(((Simple_expressionContext)_localctx).s.start,((Simple_expressionContext)_localctx).s.stop):null)));
						                  writeIntoparserLogFile("");
						              
						}
						break;
					}
					} 
				}
				setState(335);
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
		int _startState = 40;
		enterRecursionRule(_localctx, 40, RULE_term, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(337);
			((TermContext)_localctx).unary_expression = unary_expression();

			        ((TermContext)_localctx).type =  ((TermContext)_localctx).unary_expression.type;
			        ((TermContext)_localctx).hasArithmeticError =  false;
			        lineCount = (((TermContext)_localctx).unary_expression!=null?(((TermContext)_localctx).unary_expression.start):null)->getLine();
			        logRule("term : unary_expression", removeUnnecessarySpaces((((TermContext)_localctx).unary_expression!=null?_input.getText(((TermContext)_localctx).unary_expression.start,((TermContext)_localctx).unary_expression.stop):null)));
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(347);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TermContext(_parentctx, _parentState);
					_localctx.t = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_term);
					setState(340);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(341);
					((TermContext)_localctx).MULOP = match(MULOP);
					setState(342);
					((TermContext)_localctx).u = ((TermContext)_localctx).unary_expression = unary_expression();

					                  // like a[0]*4 in a[0] = a[0] * 4;
					                  int modulusByZeroError = 0;
					                  int nonIntegerOperandError = 0;
					                  lineCount = ((TermContext)_localctx).MULOP->getLine();
					                  
					                  // Check for modulus by zero
					                  if ((((TermContext)_localctx).MULOP!=null?((TermContext)_localctx).MULOP.getText():null) == "%" && (((TermContext)_localctx).u!=null?_input.getText(((TermContext)_localctx).u.start,((TermContext)_localctx).u.stop):null) == "0") 
					                  {
					                      modulusByZeroError = 1;
					                      syntaxErrorCount++;
					                      ((TermContext)_localctx).hasArithmeticError =  true;
					                  } 
					                  else if ((((TermContext)_localctx).MULOP!=null?((TermContext)_localctx).MULOP.getText():null) == "%" && (((TermContext)_localctx).t.type != "CONST_INT" || ((TermContext)_localctx).u.type != "CONST_INT")) 
					                  {
					                      nonIntegerOperandError = 1;
					                      syntaxErrorCount++;
					                      ((TermContext)_localctx).hasArithmeticError =  true;
					                  } 
					                  else 
					                  {
					                      ((TermContext)_localctx).hasArithmeticError =  ((TermContext)_localctx).t.hasArithmeticError;
					                  }
					                  ((TermContext)_localctx).type =  (((TermContext)_localctx).t.type == "FLOAT" || ((TermContext)_localctx).u.type == "FLOAT") ? "FLOAT" : "INT";
					                  
					                  // Print rule header first
					                  writeIntoparserLogFile("Line " + to_string(lineCount) + ": term : term MULOP unary_expression");
					                  writeIntoparserLogFile("");
					                  
					                  if (modulusByZeroError) 
					                  {
					                      writeIntoErrorFile("Error at line " + to_string(((TermContext)_localctx).MULOP->getLine()) + ": Modulus by Zero");
					                      writeIntoparserLogFile("Error at line " + to_string(((TermContext)_localctx).MULOP->getLine()) + ": Modulus by Zero");
					                      writeIntoparserLogFile("");
					                  } 
					                  else if (nonIntegerOperandError) 
					                  {
					                      writeIntoErrorFile("Error at line " + to_string(((TermContext)_localctx).MULOP->getLine()) + ": Non-Integer operand on modulus operator");
					                      writeIntoparserLogFile("Error at line " + to_string(((TermContext)_localctx).MULOP->getLine()) + ": Non-Integer operand on modulus operator");
					                      writeIntoparserLogFile("");
					                  }
					                  
					                  // Print code last with formatting
					                  writeIntoparserLogFile(removeUnnecessarySpaces((((TermContext)_localctx).t!=null?_input.getText(((TermContext)_localctx).t.start,((TermContext)_localctx).t.stop):null) + (((TermContext)_localctx).MULOP!=null?((TermContext)_localctx).MULOP.getText():null) + (((TermContext)_localctx).u!=null?_input.getText(((TermContext)_localctx).u.start,((TermContext)_localctx).u.stop):null)));
					                  writeIntoparserLogFile("");
					              
					}
					} 
				}
				setState(349);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
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
		enterRule(_localctx, 42, RULE_unary_expression);
		try {
			setState(361);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ADDOP:
				enterOuterAlt(_localctx, 1);
				{
				setState(350);
				((Unary_expressionContext)_localctx).ADDOP = match(ADDOP);
				setState(351);
				((Unary_expressionContext)_localctx).u = unary_expression();

				        // like -a[1] in a[1] = -a[1];;
				        lineCount = ((Unary_expressionContext)_localctx).ADDOP->getLine();
				        ((Unary_expressionContext)_localctx).type =  ((Unary_expressionContext)_localctx).u.type;
				        logRule("unary_expression : ADDOP unary_expression", removeUnnecessarySpaces((((Unary_expressionContext)_localctx).ADDOP!=null?((Unary_expressionContext)_localctx).ADDOP.getText():null) + (((Unary_expressionContext)_localctx).u!=null?_input.getText(((Unary_expressionContext)_localctx).u.start,((Unary_expressionContext)_localctx).u.stop):null)));
				    
				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(354);
				((Unary_expressionContext)_localctx).NOT = match(NOT);
				setState(355);
				((Unary_expressionContext)_localctx).u = unary_expression();

				        // like !(7<5) in a[0] = !(7<5);
				        lineCount = ((Unary_expressionContext)_localctx).NOT->getLine();
				        ((Unary_expressionContext)_localctx).type =  "INT";
				        logRule("unary_expression : NOT unary_expression", removeUnnecessarySpaces((((Unary_expressionContext)_localctx).NOT!=null?((Unary_expressionContext)_localctx).NOT.getText():null) + (((Unary_expressionContext)_localctx).u!=null?_input.getText(((Unary_expressionContext)_localctx).u.start,((Unary_expressionContext)_localctx).u.stop):null)));
				    
				}
				break;
			case LPAREN:
			case ID:
			case CONST_INT:
			case CONST_FLOAT:
				enterOuterAlt(_localctx, 3);
				{
				setState(358);
				((Unary_expressionContext)_localctx).factor = factor();

				        // like (7<5) in a[0] = !(7<5);
				        ((Unary_expressionContext)_localctx).type =  ((Unary_expressionContext)_localctx).factor.type;
				        lineCount = (((Unary_expressionContext)_localctx).factor!=null?(((Unary_expressionContext)_localctx).factor.start):null)->getLine();
				        logRule("unary_expression : factor", removeUnnecessarySpaces((((Unary_expressionContext)_localctx).factor!=null?_input.getText(((Unary_expressionContext)_localctx).factor.start,((Unary_expressionContext)_localctx).factor.stop):null)));
				    
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
		enterRule(_localctx, 44, RULE_factor);
		try {
			setState(389);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(363);
				((FactorContext)_localctx).variable = variable();

				        ((FactorContext)_localctx).type =  ((FactorContext)_localctx).variable.type;
				        lineCount = (((FactorContext)_localctx).variable!=null?(((FactorContext)_localctx).variable.start):null)->getLine();
				        logRule("factor : variable", (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null));
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(366);
				((FactorContext)_localctx).ID = match(ID);
				setState(367);
				match(LPAREN);
				setState(368);
				((FactorContext)_localctx).a = argument_list();
				setState(369);
				((FactorContext)_localctx).RPAREN = match(RPAREN);

				        // like var(c,j) in a[1]=var(c,j);

				        int undefinedFunctionError = 0;
				        int notAFunctionError = 0;
				        int numberOfArgumentsMismatchError = 0;
				        int typeMismatchArrayError = 0;
				        int typeOfParametersMismatchError = 0;
				        int isInAssignmentExpression = 0; // Flag to check if we're in an assignment expression context, I used the same flag to check void func in arithmetic expression also

				        lineCount = ((FactorContext)_localctx).RPAREN->getLine();
				        SymbolInfo* symbol = symbolTable.lookUp((((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null), parserLogFile);
				        if (!symbol) 
				        {
				            undefinedFunctionError = 1;
				            writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": Undefined function " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null));
				            syntaxErrorCount++;
				            ((FactorContext)_localctx).type =  "UNKNOWN";
				        } 
				        else if (!symbol->getIsFunction()) 
				        {
				            writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null) + " is not a function");
				            notAFunctionError = 1;
				            syntaxErrorCount++;
				            ((FactorContext)_localctx).type =  "UNKNOWN";
				        } 
				        else 
				        {
				            ((FactorContext)_localctx).type =  symbol->getReturnType();
				            
				            // Check for void function used in expression
				            if (_localctx.type == "VOID") 
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
				                    writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": Void function used in expression");
				                    syntaxErrorCount++;
				                    // log this error here
				                    writeIntoparserLogFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": Void function used in expression");
				                    writeIntoparserLogFile("");
				                }
				            }
				            
				            // function argument validation
				            vector<string> expectedParamTypes = symbol->getParameterTypes();
				            int expectedParamCount = symbol->getParameterCount();
				            
				            // Parse actual arguments
				            vector<string> actualArgTypes;
				            if (!((FactorContext)_localctx).a.argTypes.empty()) 
				            {
				                stringstream ss(((FactorContext)_localctx).a.argTypes);
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
				                writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": Total number of arguments mismatch with declaration in function " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null));
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
				                        writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": Type mismatch, argument " + to_string(i + 1) + " is an array");
				                        // log this error here
				                        writeIntoparserLogFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": Type mismatch, argument " + to_string(i + 1) + " is an array");
				                        writeIntoparserLogFile("");
				                        typeMismatchArrayError = 1;
				                        syntaxErrorCount++;
				                        break;
				                    }
				                    
				                    // Check for type compatibility
				                    else if (!isTypeCompatibleForFunction(expectedType, actualType)) 
				                    {
				                        writeIntoErrorFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": " + to_string(i + 1) + "th argument mismatch in function " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null));
				                        //log this error here
				                        writeIntoparserLogFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": " + to_string(i + 1) + "th argument mismatch in function " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null));
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
				            writeIntoparserLogFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": Undefined function " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        } else if (notAFunctionError) {
				            writeIntoparserLogFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null) + " is not a function");
				            writeIntoparserLogFile("");
				        } else if (numberOfArgumentsMismatchError) {
				            writeIntoparserLogFile("Error at line " + to_string(((FactorContext)_localctx).RPAREN->getLine()) + ": Total number of arguments mismatch with declaration in function " + (((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null));
				            writeIntoparserLogFile("");
				        } else if (typeMismatchArrayError) {
				            // already logged above
				        } else if (typeOfParametersMismatchError) {
				            // already logged above
				        } else if (isInAssignmentExpression) {
				            // had more complex logic here, so already logged above
				        }
				        
				        writeIntoparserLogFile(removeUnnecessarySpaces((((FactorContext)_localctx).ID!=null?((FactorContext)_localctx).ID.getText():null) + "(" + (((FactorContext)_localctx).a!=null?_input.getText(((FactorContext)_localctx).a.start,((FactorContext)_localctx).a.stop):null) + ")"));
				        writeIntoparserLogFile("");
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(372);
				((FactorContext)_localctx).LPAREN = match(LPAREN);
				setState(373);
				((FactorContext)_localctx).expression = expression();
				setState(374);
				match(RPAREN);

				        //like (7<5) in a[0] = !(7<5);
				        ((FactorContext)_localctx).type =  ((FactorContext)_localctx).expression.type;
				        lineCount = ((FactorContext)_localctx).LPAREN->getLine();
				        logRule("factor : LPAREN expression RPAREN", "(" + removeUnnecessarySpaces((((FactorContext)_localctx).expression!=null?_input.getText(((FactorContext)_localctx).expression.start,((FactorContext)_localctx).expression.stop):null)) + ")");
				    
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(377);
				((FactorContext)_localctx).CONST_INT = match(CONST_INT);

				        // liek 2 in a = a + y * 2;
				        ((FactorContext)_localctx).type =  "CONST_INT";
				        lineCount = ((FactorContext)_localctx).CONST_INT->getLine();
				        logRule("factor : CONST_INT", (((FactorContext)_localctx).CONST_INT!=null?((FactorContext)_localctx).CONST_INT.getText():null));
				    
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(379);
				((FactorContext)_localctx).CONST_FLOAT = match(CONST_FLOAT);

				        // like 9.5 in d = 9.5;
				        ((FactorContext)_localctx).type =  "CONST_FLOAT";
				        lineCount = ((FactorContext)_localctx).CONST_FLOAT->getLine();
				        logRule("factor : CONST_FLOAT", (((FactorContext)_localctx).CONST_FLOAT!=null?((FactorContext)_localctx).CONST_FLOAT.getText():null));
				    
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(381);
				((FactorContext)_localctx).variable = variable();
				setState(382);
				((FactorContext)_localctx).INCOP = match(INCOP);

				        // like c++ in for(c=0;c<2*d+3;c++)
				        ((FactorContext)_localctx).type =  ((FactorContext)_localctx).variable.type;
				        lineCount = ((FactorContext)_localctx).INCOP->getLine();
				        logRule("factor : variable INCOP", (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null) + (((FactorContext)_localctx).INCOP!=null?((FactorContext)_localctx).INCOP.getText():null));
				    
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(385);
				((FactorContext)_localctx).variable = variable();
				setState(386);
				((FactorContext)_localctx).DECOP = match(DECOP);

				        // like a[0]-- in while(a[0]--)
				        ((FactorContext)_localctx).type =  ((FactorContext)_localctx).variable.type;
				        lineCount = ((FactorContext)_localctx).DECOP->getLine();
				        logRule("factor : variable DECOP", (((FactorContext)_localctx).variable!=null?_input.getText(((FactorContext)_localctx).variable.start,((FactorContext)_localctx).variable.stop):null) + (((FactorContext)_localctx).DECOP!=null?((FactorContext)_localctx).DECOP.getText():null));
				    
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
		enterRule(_localctx, 46, RULE_argument_list);
		try {
			setState(395);
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
				setState(391);
				((Argument_listContext)_localctx).arguments = arguments(0);

				        // like 1,2*3 in var(1,2*3)
				        ((Argument_listContext)_localctx).argTypes =  ((Argument_listContext)_localctx).arguments.argTypes;
				        logRule("argument_list : arguments", removeUnnecessarySpaces((((Argument_listContext)_localctx).arguments!=null?_input.getText(((Argument_listContext)_localctx).arguments.start,((Argument_listContext)_localctx).arguments.stop):null)));
				    
				}
				break;
			case RPAREN:
				enterOuterAlt(_localctx, 2);
				{

				        // empty argument list, like var()
				        ((Argument_listContext)_localctx).argTypes =  "";
				        logRule("argument_list : ", "");
				    
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
		int _startState = 48;
		enterRecursionRule(_localctx, 48, RULE_arguments, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(398);
			((ArgumentsContext)_localctx).logic_expression = logic_expression();

			        // like c in var(c)
			        lineCount = (((ArgumentsContext)_localctx).logic_expression!=null?(((ArgumentsContext)_localctx).logic_expression.start):null)->getLine();
			        ((ArgumentsContext)_localctx).argTypes =  ((ArgumentsContext)_localctx).logic_expression.type;
			        logRule("arguments : logic_expression", removeUnnecessarySpaces((((ArgumentsContext)_localctx).logic_expression!=null?_input.getText(((ArgumentsContext)_localctx).logic_expression.start,((ArgumentsContext)_localctx).logic_expression.stop):null)));
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(408);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArgumentsContext(_parentctx, _parentState);
					_localctx.a = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_arguments);
					setState(401);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(402);
					((ArgumentsContext)_localctx).COMMA = match(COMMA);
					setState(403);
					((ArgumentsContext)_localctx).l = ((ArgumentsContext)_localctx).logic_expression = logic_expression();

					                  // like c,j in var(c,j)
					                  lineCount = ((ArgumentsContext)_localctx).COMMA->getLine();
					                  ((ArgumentsContext)_localctx).argTypes =  ((ArgumentsContext)_localctx).a.argTypes + "," + ((ArgumentsContext)_localctx).l.type;
					                  logRule("arguments : arguments COMMA logic_expression", removeUnnecessarySpaces((((ArgumentsContext)_localctx).a!=null?_input.getText(((ArgumentsContext)_localctx).a.start,((ArgumentsContext)_localctx).a.stop):null) + "," + (((ArgumentsContext)_localctx).l!=null?_input.getText(((ArgumentsContext)_localctx).l.start,((ArgumentsContext)_localctx).l.stop):null)));
					              
					}
					} 
				}
				setState(410);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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
		case 10:
			return declaration_list_sempred((Declaration_listContext)_localctx, predIndex);
		case 12:
			return statements_sempred((StatementsContext)_localctx, predIndex);
		case 19:
			return simple_expression_sempred((Simple_expressionContext)_localctx, predIndex);
		case 20:
			return term_sempred((TermContext)_localctx, predIndex);
		case 24:
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
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 4);
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
			return precpred(_ctx, 2);
		case 6:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean term_sempred(TermContext _localctx, int predIndex) {
		switch (predIndex) {
		case 7:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean arguments_sempred(ArgumentsContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\"\u019c\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001"+
		">\b\u0001\n\u0001\f\u0001A\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002L\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0003\u0003^\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0003\u0004r\b\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005\u0080\b\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005"+
		"\u008d\b\u0005\n\u0005\f\u0005\u0090\t\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u009d\b\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0003\t\u00aa\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0005\n\u00b5\b\n\n\n\f\n\u00b8\t\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00c5\b\u000b\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0005\f\u00cf"+
		"\b\f\n\f\f\f\u00d2\t\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0003\r\u0108\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0003\u000e\u0114\b\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u011e"+
		"\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u0129\b\u0010\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0011\u0003\u0011\u0133\b\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003"+
		"\u0012\u013d\b\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u014c\b\u0013\n\u0013\f\u0013"+
		"\u014f\t\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u015a\b\u0014"+
		"\n\u0014\f\u0014\u015d\t\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0003\u0015\u016a\b\u0015\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0003"+
		"\u0016\u0186\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0003"+
		"\u0017\u018c\b\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0005\u0018\u0197"+
		"\b\u0018\n\u0018\f\u0018\u019a\t\u0018\u0001\u0018\u0000\u0007\u0002\n"+
		"\u0014\u0018&(0\u0019\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.0\u0000\u0001\u0002\u0000"+
		"\u0016\u0016\u0018\u001e\u01ad\u00002\u0001\u0000\u0000\u0000\u00025\u0001"+
		"\u0000\u0000\u0000\u0004K\u0001\u0000\u0000\u0000\u0006]\u0001\u0000\u0000"+
		"\u0000\bq\u0001\u0000\u0000\u0000\n\u007f\u0001\u0000\u0000\u0000\f\u0091"+
		"\u0001\u0000\u0000\u0000\u000e\u009c\u0001\u0000\u0000\u0000\u0010\u009e"+
		"\u0001\u0000\u0000\u0000\u0012\u00a9\u0001\u0000\u0000\u0000\u0014\u00ab"+
		"\u0001\u0000\u0000\u0000\u0016\u00c4\u0001\u0000\u0000\u0000\u0018\u00c6"+
		"\u0001\u0000\u0000\u0000\u001a\u0107\u0001\u0000\u0000\u0000\u001c\u0113"+
		"\u0001\u0000\u0000\u0000\u001e\u011d\u0001\u0000\u0000\u0000 \u0128\u0001"+
		"\u0000\u0000\u0000\"\u0132\u0001\u0000\u0000\u0000$\u013c\u0001\u0000"+
		"\u0000\u0000&\u013e\u0001\u0000\u0000\u0000(\u0150\u0001\u0000\u0000\u0000"+
		"*\u0169\u0001\u0000\u0000\u0000,\u0185\u0001\u0000\u0000\u0000.\u018b"+
		"\u0001\u0000\u0000\u00000\u018d\u0001\u0000\u0000\u000023\u0003\u0002"+
		"\u0001\u000034\u0006\u0000\uffff\uffff\u00004\u0001\u0001\u0000\u0000"+
		"\u000056\u0006\u0001\uffff\uffff\u000067\u0003\u0004\u0002\u000078\u0006"+
		"\u0001\uffff\uffff\u00008?\u0001\u0000\u0000\u00009:\n\u0002\u0000\u0000"+
		":;\u0003\u0004\u0002\u0000;<\u0006\u0001\uffff\uffff\u0000<>\u0001\u0000"+
		"\u0000\u0000=9\u0001\u0000\u0000\u0000>A\u0001\u0000\u0000\u0000?=\u0001"+
		"\u0000\u0000\u0000?@\u0001\u0000\u0000\u0000@\u0003\u0001\u0000\u0000"+
		"\u0000A?\u0001\u0000\u0000\u0000BC\u0003\u0010\b\u0000CD\u0006\u0002\uffff"+
		"\uffff\u0000DL\u0001\u0000\u0000\u0000EF\u0003\u0006\u0003\u0000FG\u0006"+
		"\u0002\uffff\uffff\u0000GL\u0001\u0000\u0000\u0000HI\u0003\b\u0004\u0000"+
		"IJ\u0006\u0002\uffff\uffff\u0000JL\u0001\u0000\u0000\u0000KB\u0001\u0000"+
		"\u0000\u0000KE\u0001\u0000\u0000\u0000KH\u0001\u0000\u0000\u0000L\u0005"+
		"\u0001\u0000\u0000\u0000MN\u0003\u0012\t\u0000NO\u0005\u001f\u0000\u0000"+
		"OP\u0006\u0003\uffff\uffff\u0000PQ\u0005\u000e\u0000\u0000QR\u0003\n\u0005"+
		"\u0000RS\u0005\u000f\u0000\u0000ST\u0005\u0014\u0000\u0000TU\u0006\u0003"+
		"\uffff\uffff\u0000U^\u0001\u0000\u0000\u0000VW\u0003\u0012\t\u0000WX\u0005"+
		"\u001f\u0000\u0000XY\u0005\u000e\u0000\u0000YZ\u0005\u000f\u0000\u0000"+
		"Z[\u0005\u0014\u0000\u0000[\\\u0006\u0003\uffff\uffff\u0000\\^\u0001\u0000"+
		"\u0000\u0000]M\u0001\u0000\u0000\u0000]V\u0001\u0000\u0000\u0000^\u0007"+
		"\u0001\u0000\u0000\u0000_`\u0003\u0012\t\u0000`a\u0005\u001f\u0000\u0000"+
		"ab\u0005\u000e\u0000\u0000bc\u0006\u0004\uffff\uffff\u0000cd\u0003\n\u0005"+
		"\u0000de\u0005\u000f\u0000\u0000ef\u0006\u0004\uffff\uffff\u0000fg\u0003"+
		"\u000e\u0007\u0000gh\u0006\u0004\uffff\uffff\u0000hr\u0001\u0000\u0000"+
		"\u0000ij\u0003\u0012\t\u0000jk\u0005\u001f\u0000\u0000kl\u0005\u000e\u0000"+
		"\u0000lm\u0006\u0004\uffff\uffff\u0000mn\u0005\u000f\u0000\u0000no\u0003"+
		"\u000e\u0007\u0000op\u0006\u0004\uffff\uffff\u0000pr\u0001\u0000\u0000"+
		"\u0000q_\u0001\u0000\u0000\u0000qi\u0001\u0000\u0000\u0000r\t\u0001\u0000"+
		"\u0000\u0000st\u0006\u0005\uffff\uffff\u0000tu\u0003\u0012\t\u0000uv\u0005"+
		"\u001f\u0000\u0000vw\u0006\u0005\uffff\uffff\u0000w\u0080\u0001\u0000"+
		"\u0000\u0000xy\u0003\u0012\t\u0000yz\u0006\u0005\uffff\uffff\u0000z\u0080"+
		"\u0001\u0000\u0000\u0000{|\u0003\u0012\t\u0000|}\u0003\f\u0006\u0000}"+
		"~\u0006\u0005\uffff\uffff\u0000~\u0080\u0001\u0000\u0000\u0000\u007fs"+
		"\u0001\u0000\u0000\u0000\u007fx\u0001\u0000\u0000\u0000\u007f{\u0001\u0000"+
		"\u0000\u0000\u0080\u008e\u0001\u0000\u0000\u0000\u0081\u0082\n\u0005\u0000"+
		"\u0000\u0082\u0083\u0005\u0015\u0000\u0000\u0083\u0084\u0003\u0012\t\u0000"+
		"\u0084\u0085\u0005\u001f\u0000\u0000\u0085\u0086\u0006\u0005\uffff\uffff"+
		"\u0000\u0086\u008d\u0001\u0000\u0000\u0000\u0087\u0088\n\u0004\u0000\u0000"+
		"\u0088\u0089\u0005\u0015\u0000\u0000\u0089\u008a\u0003\u0012\t\u0000\u008a"+
		"\u008b\u0006\u0005\uffff\uffff\u0000\u008b\u008d\u0001\u0000\u0000\u0000"+
		"\u008c\u0081\u0001\u0000\u0000\u0000\u008c\u0087\u0001\u0000\u0000\u0000"+
		"\u008d\u0090\u0001\u0000\u0000\u0000\u008e\u008c\u0001\u0000\u0000\u0000"+
		"\u008e\u008f\u0001\u0000\u0000\u0000\u008f\u000b\u0001\u0000\u0000\u0000"+
		"\u0090\u008e\u0001\u0000\u0000\u0000\u0091\u0092\u0007\u0000\u0000\u0000"+
		"\u0092\r\u0001\u0000\u0000\u0000\u0093\u0094\u0005\u0010\u0000\u0000\u0094"+
		"\u0095\u0006\u0007\uffff\uffff\u0000\u0095\u0096\u0003\u0018\f\u0000\u0096"+
		"\u0097\u0005\u0011\u0000\u0000\u0097\u0098\u0006\u0007\uffff\uffff\u0000"+
		"\u0098\u009d\u0001\u0000\u0000\u0000\u0099\u009a\u0005\u0010\u0000\u0000"+
		"\u009a\u009b\u0005\u0011\u0000\u0000\u009b\u009d\u0006\u0007\uffff\uffff"+
		"\u0000\u009c\u0093\u0001\u0000\u0000\u0000\u009c\u0099\u0001\u0000\u0000"+
		"\u0000\u009d\u000f\u0001\u0000\u0000\u0000\u009e\u009f\u0003\u0012\t\u0000"+
		"\u009f\u00a0\u0003\u0014\n\u0000\u00a0\u00a1\u0005\u0014\u0000\u0000\u00a1"+
		"\u00a2\u0006\b\uffff\uffff\u0000\u00a2\u0011\u0001\u0000\u0000\u0000\u00a3"+
		"\u00a4\u0005\u000b\u0000\u0000\u00a4\u00aa\u0006\t\uffff\uffff\u0000\u00a5"+
		"\u00a6\u0005\f\u0000\u0000\u00a6\u00aa\u0006\t\uffff\uffff\u0000\u00a7"+
		"\u00a8\u0005\r\u0000\u0000\u00a8\u00aa\u0006\t\uffff\uffff\u0000\u00a9"+
		"\u00a3\u0001\u0000\u0000\u0000\u00a9\u00a5\u0001\u0000\u0000\u0000\u00a9"+
		"\u00a7\u0001\u0000\u0000\u0000\u00aa\u0013\u0001\u0000\u0000\u0000\u00ab"+
		"\u00ac\u0006\n\uffff\uffff\u0000\u00ac\u00ad\u0003\u0016\u000b\u0000\u00ad"+
		"\u00ae\u0006\n\uffff\uffff\u0000\u00ae\u00b6\u0001\u0000\u0000\u0000\u00af"+
		"\u00b0\n\u0002\u0000\u0000\u00b0\u00b1\u0005\u0015\u0000\u0000\u00b1\u00b2"+
		"\u0003\u0016\u000b\u0000\u00b2\u00b3\u0006\n\uffff\uffff\u0000\u00b3\u00b5"+
		"\u0001\u0000\u0000\u0000\u00b4\u00af\u0001\u0000\u0000\u0000\u00b5\u00b8"+
		"\u0001\u0000\u0000\u0000\u00b6\u00b4\u0001\u0000\u0000\u0000\u00b6\u00b7"+
		"\u0001\u0000\u0000\u0000\u00b7\u0015\u0001\u0000\u0000\u0000\u00b8\u00b6"+
		"\u0001\u0000\u0000\u0000\u00b9\u00ba\u0005\u001f\u0000\u0000\u00ba\u00c5"+
		"\u0006\u000b\uffff\uffff\u0000\u00bb\u00bc\u0005\u001f\u0000\u0000\u00bc"+
		"\u00bd\u0005\u0012\u0000\u0000\u00bd\u00be\u0005 \u0000\u0000\u00be\u00bf"+
		"\u0005\u0013\u0000\u0000\u00bf\u00c5\u0006\u000b\uffff\uffff\u0000\u00c0"+
		"\u00c1\u0005\u001f\u0000\u0000\u00c1\u00c2\u0005\u0016\u0000\u0000\u00c2"+
		"\u00c3\u0005\u001f\u0000\u0000\u00c3\u00c5\u0006\u000b\uffff\uffff\u0000"+
		"\u00c4\u00b9\u0001\u0000\u0000\u0000\u00c4\u00bb\u0001\u0000\u0000\u0000"+
		"\u00c4\u00c0\u0001\u0000\u0000\u0000\u00c5\u0017\u0001\u0000\u0000\u0000"+
		"\u00c6\u00c7\u0006\f\uffff\uffff\u0000\u00c7\u00c8\u0003\u001a\r\u0000"+
		"\u00c8\u00c9\u0006\f\uffff\uffff\u0000\u00c9\u00d0\u0001\u0000\u0000\u0000"+
		"\u00ca\u00cb\n\u0002\u0000\u0000\u00cb\u00cc\u0003\u001a\r\u0000\u00cc"+
		"\u00cd\u0006\f\uffff\uffff\u0000\u00cd\u00cf\u0001\u0000\u0000\u0000\u00ce"+
		"\u00ca\u0001\u0000\u0000\u0000\u00cf\u00d2\u0001\u0000\u0000\u0000\u00d0"+
		"\u00ce\u0001\u0000\u0000\u0000\u00d0\u00d1\u0001\u0000\u0000\u0000\u00d1"+
		"\u0019\u0001\u0000\u0000\u0000\u00d2\u00d0\u0001\u0000\u0000\u0000\u00d3"+
		"\u00d4\u0003\u0010\b\u0000\u00d4\u00d5\u0006\r\uffff\uffff\u0000\u00d5"+
		"\u0108\u0001\u0000\u0000\u0000\u00d6\u00d7\u0003\u001c\u000e\u0000\u00d7"+
		"\u00d8\u0006\r\uffff\uffff\u0000\u00d8\u0108\u0001\u0000\u0000\u0000\u00d9"+
		"\u00da\u0003\u000e\u0007\u0000\u00da\u00db\u0006\r\uffff\uffff\u0000\u00db"+
		"\u0108\u0001\u0000\u0000\u0000\u00dc\u00dd\u0005\u0007\u0000\u0000\u00dd"+
		"\u00de\u0005\u000e\u0000\u0000\u00de\u00df\u0003\u001c\u000e\u0000\u00df"+
		"\u00e0\u0003\u001c\u000e\u0000\u00e0\u00e1\u0003 \u0010\u0000\u00e1\u00e2"+
		"\u0005\u000f\u0000\u0000\u00e2\u00e3\u0003\u001a\r\u0000\u00e3\u00e4\u0006"+
		"\r\uffff\uffff\u0000\u00e4\u0108\u0001\u0000\u0000\u0000\u00e5\u00e6\u0005"+
		"\u0005\u0000\u0000\u00e6\u00e7\u0005\u000e\u0000\u0000\u00e7\u00e8\u0003"+
		" \u0010\u0000\u00e8\u00e9\u0005\u000f\u0000\u0000\u00e9\u00ea\u0003\u001a"+
		"\r\u0000\u00ea\u00eb\u0006\r\uffff\uffff\u0000\u00eb\u0108\u0001\u0000"+
		"\u0000\u0000\u00ec\u00ed\u0005\u0005\u0000\u0000\u00ed\u00ee\u0005\u000e"+
		"\u0000\u0000\u00ee\u00ef\u0003 \u0010\u0000\u00ef\u00f0\u0005\u000f\u0000"+
		"\u0000\u00f0\u00f1\u0003\u001a\r\u0000\u00f1\u00f2\u0005\u0006\u0000\u0000"+
		"\u00f2\u00f3\u0003\u001a\r\u0000\u00f3\u00f4\u0006\r\uffff\uffff\u0000"+
		"\u00f4\u0108\u0001\u0000\u0000\u0000\u00f5\u00f6\u0005\b\u0000\u0000\u00f6"+
		"\u00f7\u0005\u000e\u0000\u0000\u00f7\u00f8\u0003 \u0010\u0000\u00f8\u00f9"+
		"\u0005\u000f\u0000\u0000\u00f9\u00fa\u0003\u001a\r\u0000\u00fa\u00fb\u0006"+
		"\r\uffff\uffff\u0000\u00fb\u0108\u0001\u0000\u0000\u0000\u00fc\u00fd\u0005"+
		"\t\u0000\u0000\u00fd\u00fe\u0005\u000e\u0000\u0000\u00fe\u00ff\u0005\u001f"+
		"\u0000\u0000\u00ff\u0100\u0005\u000f\u0000\u0000\u0100\u0101\u0005\u0014"+
		"\u0000\u0000\u0101\u0108\u0006\r\uffff\uffff\u0000\u0102\u0103\u0005\n"+
		"\u0000\u0000\u0103\u0104\u0003 \u0010\u0000\u0104\u0105\u0005\u0014\u0000"+
		"\u0000\u0105\u0106\u0006\r\uffff\uffff\u0000\u0106\u0108\u0001\u0000\u0000"+
		"\u0000\u0107\u00d3\u0001\u0000\u0000\u0000\u0107\u00d6\u0001\u0000\u0000"+
		"\u0000\u0107\u00d9\u0001\u0000\u0000\u0000\u0107\u00dc\u0001\u0000\u0000"+
		"\u0000\u0107\u00e5\u0001\u0000\u0000\u0000\u0107\u00ec\u0001\u0000\u0000"+
		"\u0000\u0107\u00f5\u0001\u0000\u0000\u0000\u0107\u00fc\u0001\u0000\u0000"+
		"\u0000\u0107\u0102\u0001\u0000\u0000\u0000\u0108\u001b\u0001\u0000\u0000"+
		"\u0000\u0109\u010a\u0005\u0014\u0000\u0000\u010a\u0114\u0006\u000e\uffff"+
		"\uffff\u0000\u010b\u010c\u0003 \u0010\u0000\u010c\u010d\u0005\u0014\u0000"+
		"\u0000\u010d\u010e\u0006\u000e\uffff\uffff\u0000\u010e\u0114\u0001\u0000"+
		"\u0000\u0000\u010f\u0110\u0003 \u0010\u0000\u0110\u0111\u0005 \u0000\u0000"+
		"\u0111\u0112\u0006\u000e\uffff\uffff\u0000\u0112\u0114\u0001\u0000\u0000"+
		"\u0000\u0113\u0109\u0001\u0000\u0000\u0000\u0113\u010b\u0001\u0000\u0000"+
		"\u0000\u0113\u010f\u0001\u0000\u0000\u0000\u0114\u001d\u0001\u0000\u0000"+
		"\u0000\u0115\u0116\u0005\u001f\u0000\u0000\u0116\u011e\u0006\u000f\uffff"+
		"\uffff\u0000\u0117\u0118\u0005\u001f\u0000\u0000\u0118\u0119\u0005\u0012"+
		"\u0000\u0000\u0119\u011a\u0003 \u0010\u0000\u011a\u011b\u0005\u0013\u0000"+
		"\u0000\u011b\u011c\u0006\u000f\uffff\uffff\u0000\u011c\u011e\u0001\u0000"+
		"\u0000\u0000\u011d\u0115\u0001\u0000\u0000\u0000\u011d\u0117\u0001\u0000"+
		"\u0000\u0000\u011e\u001f\u0001\u0000\u0000\u0000\u011f\u0120\u0003\"\u0011"+
		"\u0000\u0120\u0121\u0006\u0010\uffff\uffff\u0000\u0121\u0129\u0001\u0000"+
		"\u0000\u0000\u0122\u0123\u0003\u001e\u000f\u0000\u0123\u0124\u0005\u001e"+
		"\u0000\u0000\u0124\u0125\u0006\u0010\uffff\uffff\u0000\u0125\u0126\u0003"+
		"\"\u0011\u0000\u0126\u0127\u0006\u0010\uffff\uffff\u0000\u0127\u0129\u0001"+
		"\u0000\u0000\u0000\u0128\u011f\u0001\u0000\u0000\u0000\u0128\u0122\u0001"+
		"\u0000\u0000\u0000\u0129!\u0001\u0000\u0000\u0000\u012a\u012b\u0003$\u0012"+
		"\u0000\u012b\u012c\u0006\u0011\uffff\uffff\u0000\u012c\u0133\u0001\u0000"+
		"\u0000\u0000\u012d\u012e\u0003$\u0012\u0000\u012e\u012f\u0005\u001d\u0000"+
		"\u0000\u012f\u0130\u0003$\u0012\u0000\u0130\u0131\u0006\u0011\uffff\uffff"+
		"\u0000\u0131\u0133\u0001\u0000\u0000\u0000\u0132\u012a\u0001\u0000\u0000"+
		"\u0000\u0132\u012d\u0001\u0000\u0000\u0000\u0133#\u0001\u0000\u0000\u0000"+
		"\u0134\u0135\u0003&\u0013\u0000\u0135\u0136\u0006\u0012\uffff\uffff\u0000"+
		"\u0136\u013d\u0001\u0000\u0000\u0000\u0137\u0138\u0003&\u0013\u0000\u0138"+
		"\u0139\u0005\u001c\u0000\u0000\u0139\u013a\u0003&\u0013\u0000\u013a\u013b"+
		"\u0006\u0012\uffff\uffff\u0000\u013b\u013d\u0001\u0000\u0000\u0000\u013c"+
		"\u0134\u0001\u0000\u0000\u0000\u013c\u0137\u0001\u0000\u0000\u0000\u013d"+
		"%\u0001\u0000\u0000\u0000\u013e\u013f\u0006\u0013\uffff\uffff\u0000\u013f"+
		"\u0140\u0003(\u0014\u0000\u0140\u0141\u0006\u0013\uffff\uffff\u0000\u0141"+
		"\u014d\u0001\u0000\u0000\u0000\u0142\u0143\n\u0002\u0000\u0000\u0143\u0144"+
		"\u0005\u0016\u0000\u0000\u0144\u0145\u0003(\u0014\u0000\u0145\u0146\u0006"+
		"\u0013\uffff\uffff\u0000\u0146\u014c\u0001\u0000\u0000\u0000\u0147\u0148"+
		"\n\u0001\u0000\u0000\u0148\u0149\u0005\u0016\u0000\u0000\u0149\u014a\u0005"+
		"\u001e\u0000\u0000\u014a\u014c\u0006\u0013\uffff\uffff\u0000\u014b\u0142"+
		"\u0001\u0000\u0000\u0000\u014b\u0147\u0001\u0000\u0000\u0000\u014c\u014f"+
		"\u0001\u0000\u0000\u0000\u014d\u014b\u0001\u0000\u0000\u0000\u014d\u014e"+
		"\u0001\u0000\u0000\u0000\u014e\'\u0001\u0000\u0000\u0000\u014f\u014d\u0001"+
		"\u0000\u0000\u0000\u0150\u0151\u0006\u0014\uffff\uffff\u0000\u0151\u0152"+
		"\u0003*\u0015\u0000\u0152\u0153\u0006\u0014\uffff\uffff\u0000\u0153\u015b"+
		"\u0001\u0000\u0000\u0000\u0154\u0155\n\u0001\u0000\u0000\u0155\u0156\u0005"+
		"\u0018\u0000\u0000\u0156\u0157\u0003*\u0015\u0000\u0157\u0158\u0006\u0014"+
		"\uffff\uffff\u0000\u0158\u015a\u0001\u0000\u0000\u0000\u0159\u0154\u0001"+
		"\u0000\u0000\u0000\u015a\u015d\u0001\u0000\u0000\u0000\u015b\u0159\u0001"+
		"\u0000\u0000\u0000\u015b\u015c\u0001\u0000\u0000\u0000\u015c)\u0001\u0000"+
		"\u0000\u0000\u015d\u015b\u0001\u0000\u0000\u0000\u015e\u015f\u0005\u0016"+
		"\u0000\u0000\u015f\u0160\u0003*\u0015\u0000\u0160\u0161\u0006\u0015\uffff"+
		"\uffff\u0000\u0161\u016a\u0001\u0000\u0000\u0000\u0162\u0163\u0005\u001b"+
		"\u0000\u0000\u0163\u0164\u0003*\u0015\u0000\u0164\u0165\u0006\u0015\uffff"+
		"\uffff\u0000\u0165\u016a\u0001\u0000\u0000\u0000\u0166\u0167\u0003,\u0016"+
		"\u0000\u0167\u0168\u0006\u0015\uffff\uffff\u0000\u0168\u016a\u0001\u0000"+
		"\u0000\u0000\u0169\u015e\u0001\u0000\u0000\u0000\u0169\u0162\u0001\u0000"+
		"\u0000\u0000\u0169\u0166\u0001\u0000\u0000\u0000\u016a+\u0001\u0000\u0000"+
		"\u0000\u016b\u016c\u0003\u001e\u000f\u0000\u016c\u016d\u0006\u0016\uffff"+
		"\uffff\u0000\u016d\u0186\u0001\u0000\u0000\u0000\u016e\u016f\u0005\u001f"+
		"\u0000\u0000\u016f\u0170\u0005\u000e\u0000\u0000\u0170\u0171\u0003.\u0017"+
		"\u0000\u0171\u0172\u0005\u000f\u0000\u0000\u0172\u0173\u0006\u0016\uffff"+
		"\uffff\u0000\u0173\u0186\u0001\u0000\u0000\u0000\u0174\u0175\u0005\u000e"+
		"\u0000\u0000\u0175\u0176\u0003 \u0010\u0000\u0176\u0177\u0005\u000f\u0000"+
		"\u0000\u0177\u0178\u0006\u0016\uffff\uffff\u0000\u0178\u0186\u0001\u0000"+
		"\u0000\u0000\u0179\u017a\u0005 \u0000\u0000\u017a\u0186\u0006\u0016\uffff"+
		"\uffff\u0000\u017b\u017c\u0005!\u0000\u0000\u017c\u0186\u0006\u0016\uffff"+
		"\uffff\u0000\u017d\u017e\u0003\u001e\u000f\u0000\u017e\u017f\u0005\u0019"+
		"\u0000\u0000\u017f\u0180\u0006\u0016\uffff\uffff\u0000\u0180\u0186\u0001"+
		"\u0000\u0000\u0000\u0181\u0182\u0003\u001e\u000f\u0000\u0182\u0183\u0005"+
		"\u001a\u0000\u0000\u0183\u0184\u0006\u0016\uffff\uffff\u0000\u0184\u0186"+
		"\u0001\u0000\u0000\u0000\u0185\u016b\u0001\u0000\u0000\u0000\u0185\u016e"+
		"\u0001\u0000\u0000\u0000\u0185\u0174\u0001\u0000\u0000\u0000\u0185\u0179"+
		"\u0001\u0000\u0000\u0000\u0185\u017b\u0001\u0000\u0000\u0000\u0185\u017d"+
		"\u0001\u0000\u0000\u0000\u0185\u0181\u0001\u0000\u0000\u0000\u0186-\u0001"+
		"\u0000\u0000\u0000\u0187\u0188\u00030\u0018\u0000\u0188\u0189\u0006\u0017"+
		"\uffff\uffff\u0000\u0189\u018c\u0001\u0000\u0000\u0000\u018a\u018c\u0006"+
		"\u0017\uffff\uffff\u0000\u018b\u0187\u0001\u0000\u0000\u0000\u018b\u018a"+
		"\u0001\u0000\u0000\u0000\u018c/\u0001\u0000\u0000\u0000\u018d\u018e\u0006"+
		"\u0018\uffff\uffff\u0000\u018e\u018f\u0003\"\u0011\u0000\u018f\u0190\u0006"+
		"\u0018\uffff\uffff\u0000\u0190\u0198\u0001\u0000\u0000\u0000\u0191\u0192"+
		"\n\u0002\u0000\u0000\u0192\u0193\u0005\u0015\u0000\u0000\u0193\u0194\u0003"+
		"\"\u0011\u0000\u0194\u0195\u0006\u0018\uffff\uffff\u0000\u0195\u0197\u0001"+
		"\u0000\u0000\u0000\u0196\u0191\u0001\u0000\u0000\u0000\u0197\u019a\u0001"+
		"\u0000\u0000\u0000\u0198\u0196\u0001\u0000\u0000\u0000\u0198\u0199\u0001"+
		"\u0000\u0000\u0000\u01991\u0001\u0000\u0000\u0000\u019a\u0198\u0001\u0000"+
		"\u0000\u0000\u0019?K]q\u007f\u008c\u008e\u009c\u00a9\u00b6\u00c4\u00d0"+
		"\u0107\u0113\u011d\u0128\u0132\u013c\u014b\u014d\u015b\u0169\u0185\u018b"+
		"\u0198";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}