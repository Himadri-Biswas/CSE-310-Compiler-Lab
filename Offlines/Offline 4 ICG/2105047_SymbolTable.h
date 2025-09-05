#ifndef SYMBOL_TABLE_H
#define SYMBOL_TABLE_H

#include <string>
#include <vector>
#include <fstream>

using namespace std;

class ScopeTable;

class SymbolInfo {
private:
    string name;
    string type;
    bool isFunction;
    bool isDefined;
    string returnType;
    SymbolInfo* next; 
    bool isArray;
    int arraySize;
    std::vector<std::string> parameterTypes;
    int parameterCount;
    
    // ICG additions
    int stackOffset;    // Stack offset for local variables
    bool isGlobal;      // Whether variable is global
    bool isParameter;   // Whether this is a function parameter

public:
    SymbolInfo(string name, string type);
    ~SymbolInfo();
    
    // Existing methods
    string getName();
    string getType();
    bool getIsFunction();
    bool getIsDefined();
    string getReturnType();
    void setIsFunction(bool value);
    void setIsDefined(bool value);
    void setReturnType(string type);
    SymbolInfo* getNext();
    void setNext(SymbolInfo* nextPtr);
    bool getIsArray();
    void setIsArray(bool value);
    int getArraySize();
    void setArraySize(int size);
    void addParameter(const std::string& type);
    std::vector<std::string> getParameterTypes() const;
    int getParameterCount() const;
    void clearParameters();
    void setParameterCount(int count);
    
    // ICG additions
    int getStackOffset() const;
    void setStackOffset(int offset);
    bool getIsGlobal() const;
    void setIsGlobal(bool global);
    bool getIsParameter() const;
    void setIsParameter(bool param);
};

class ScopeTable {
private:
    SymbolInfo** table;
    int size;
    int totalCollisions;
    ScopeTable* parentScope;
    string nestedId;
    int childCounter;
    
    // ICG additions
    int currentStackOffset;  // Current stack offset for this scope
    int variableCount;       // Number of variables in this scope

public:
    ScopeTable(int size);
    ~ScopeTable();
    bool insert(SymbolInfo& symbol, ofstream& outfile);
    SymbolInfo* lookUp(string name, ofstream& outfile);
    bool deleteSymbol(string name, ofstream& outfile);
    void print(ofstream& outfile);
    string getNestedId();
    ScopeTable* getParentScope();
    void setParentScope(ScopeTable* parent);
    int getTotalCollisions();
    void incrementChildCounter();
    void setNestedId(string id); 
    int getChildCounter();
    
    // ICG additions
    int getCurrentStackOffset() const;
    void incrementStackOffset(int size);
    int getVariableCount() const;
    void incrementVariableCount();
};

unsigned int sdbm_hash(string str);

class SymbolTable {
private:
    ScopeTable* currentScope;
    int totalCollisions;
    int scopeCount;

public:
    SymbolTable(int size);
    ~SymbolTable();
    void enterScope(int size, ofstream& outfile);
    void exitScope(ofstream& outfile, int forceRemove);
    bool insert(SymbolInfo& symbol, ofstream& outfile);
    SymbolInfo* lookUp(string name, ofstream& outfile);
    void printCurrentScopeTable(ofstream& outfile);
    void printAllScopeTable(ofstream& outfile);
    ScopeTable* getCurrentScope();
};

#endif