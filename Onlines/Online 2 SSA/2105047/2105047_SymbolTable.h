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
    bool isArray;     // New member to track array status
    int arraySize;    // New member to store array size
    std::vector<std::string> parameterTypes;  // NEW
    int parameterCount;    // NEW

public:
    SymbolInfo(string name, string type);
    ~SymbolInfo();
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
    bool getIsArray();          // New getter
    void setIsArray(bool value); // New setter
    int getArraySize();         // New getter
    void setArraySize(int size); // New setter
    void addParameter(const std::string& type); // NEW
    std::vector<std::string> getParameterTypes() const; // NEW
    int getParameterCount() const; // NEW
    void clearParameters(); // NEW
    void setParameterCount(int count); // NEW
};

class ScopeTable {
private:
    SymbolInfo** table;
    int size;
    int totalCollisions;
    ScopeTable* parentScope;
    string nestedId;
    int childCounter;

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
};

// int sdbm_hash(string str);
// unsigned int sdbm_hash(const char* p);
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