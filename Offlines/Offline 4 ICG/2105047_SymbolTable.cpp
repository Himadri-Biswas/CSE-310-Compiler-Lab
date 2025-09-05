#include "2105047_SymbolTable.h"
#include <iostream>
#include <iomanip>

SymbolInfo::SymbolInfo(string name, string type) {
    this->name = name;
    this->type = type;
    this->isFunction = false;
    this->isDefined = false;
    this->returnType = "";
    this->next = NULL;
    this->isArray = false;
    this->arraySize = 0;
    this->parameterCount = 0;
    this->parameterTypes.clear();
    
    // ICG initializations
    this->stackOffset = 0;
    this->isGlobal = false;
    this->isParameter = false;
}

SymbolInfo::~SymbolInfo() {}

string SymbolInfo::getName() { return name; }
string SymbolInfo::getType() { return type; }
bool SymbolInfo::getIsFunction() { return isFunction; }
bool SymbolInfo::getIsDefined() { return isDefined; }
string SymbolInfo::getReturnType() { return returnType; }
void SymbolInfo::setIsFunction(bool value) { isFunction = value; }
void SymbolInfo::setIsDefined(bool value) { isDefined = value; }
void SymbolInfo::setReturnType(string type) { returnType = type; }
SymbolInfo* SymbolInfo::getNext() { return next; }
void SymbolInfo::setNext(SymbolInfo* nextPtr) { next = nextPtr; }
bool SymbolInfo::getIsArray() { return isArray; }
void SymbolInfo::setIsArray(bool value) { isArray = value; }
int SymbolInfo::getArraySize() { return arraySize; }
void SymbolInfo::setArraySize(int size) { arraySize = size; }

void SymbolInfo::addParameter(const std::string& type) {
    parameterTypes.push_back(type);
    parameterCount++;
}

std::vector<std::string> SymbolInfo::getParameterTypes() const { 
    return parameterTypes; 
}

int SymbolInfo::getParameterCount() const { 
    return parameterCount; 
}

void SymbolInfo::clearParameters() {
    parameterTypes.clear();
    parameterCount = 0;
}

void SymbolInfo::setParameterCount(int count) {
    parameterCount = count;
}

// ICG methods
int SymbolInfo::getStackOffset() const {
    return stackOffset;
}

void SymbolInfo::setStackOffset(int offset) {
    stackOffset = offset;
}

bool SymbolInfo::getIsGlobal() const {
    return isGlobal;
}

void SymbolInfo::setIsGlobal(bool global) {
    isGlobal = global;
}

bool SymbolInfo::getIsParameter() const {
    return isParameter;
}

void SymbolInfo::setIsParameter(bool param) {
    isParameter = param;
}

ScopeTable::ScopeTable(int size) {
    this->size = size;
    table = new SymbolInfo*[size];
    for (int i = 0; i < size; i++) table[i] = NULL;
    totalCollisions = 0;
    parentScope = NULL;
    nestedId = "1";
    childCounter = 1;
    
    // ICG initializations
    currentStackOffset = 0;
    variableCount = 0;
}

ScopeTable::~ScopeTable() {
    for (int i = 0; i < size; i++) {
        SymbolInfo* current = table[i];
        while (current != NULL) {
            SymbolInfo* next = current->getNext();
            delete current;
            current = next;
        }
    }
    delete[] table;
}

bool ScopeTable::insert(SymbolInfo& symbol, ofstream& outfile) {
    int index = sdbm_hash(symbol.getName());
    SymbolInfo* current = table[index];
    int collisions = 0;

    while (current != NULL) {
        if (current->getName() == symbol.getName()) {
            return false;
        }
        current = current->getNext();
        collisions++;
    }

    SymbolInfo* newSymbol = new SymbolInfo(symbol.getName(), symbol.getType());
    newSymbol->setIsFunction(symbol.getIsFunction());
    newSymbol->setIsDefined(symbol.getIsDefined());
    newSymbol->setReturnType(symbol.getReturnType());
    newSymbol->setIsArray(symbol.getIsArray());
    newSymbol->setArraySize(symbol.getArraySize());
    newSymbol->setNext(NULL);
    
    // ICG: other properties
    newSymbol->setStackOffset(symbol.getStackOffset());
    newSymbol->setIsParameter(symbol.getIsParameter());
    newSymbol->setIsGlobal(symbol.getIsGlobal());
    
    // ICG: Set stack offset for variables ONLY if not already set by caller
    if (!symbol.getIsFunction())
    {
        // Check if this is a parameter . if so, don't overwrite the offset
        if (symbol.getIsParameter())
        {
            // Parameter . keep the offset that was already set
            // Do nothing, offset already copied above
        }
        else if (nestedId == "1")
        {
            // Global scope
            newSymbol->setIsGlobal(true);
            newSymbol->setStackOffset(-1); // Special value for global
        }
        else
        {
            // Local scope variable
            newSymbol->setIsGlobal(false);
            if (symbol.getIsArray())
            {
                // currentStackOffset -= (symbol.getArraySize() * 2);
                currentStackOffset -= 2;
                newSymbol->setStackOffset(currentStackOffset);
            }
            else
            {
                currentStackOffset -= 2;
                newSymbol->setStackOffset(currentStackOffset);
            }
        }
        variableCount++;
    }

    // Insert at end of chain
    if (table[index] == NULL) {
        table[index] = newSymbol;
    } else {
        current = table[index];
        while (current->getNext() != NULL) {
            current = current->getNext();
        }
        current->setNext(newSymbol);
    }

    totalCollisions += collisions;
    return true;
}

SymbolInfo* ScopeTable::lookUp(string name, ofstream& outfile) {
    int index = sdbm_hash(name);
    SymbolInfo* current = table[index];

    while (current != NULL) {
        if (current->getName() == name) {
            return current;
        }
        current = current->getNext();
    }

    return NULL;
}

bool ScopeTable::deleteSymbol(string name, ofstream& outfile) {
    int index = sdbm_hash(name);
    SymbolInfo* current = table[index];
    SymbolInfo* prev = NULL;

    while (current != NULL) {
        if (current->getName() == name) {
            if (prev == NULL) {
                table[index] = current->getNext();
            } else {
                prev->setNext(current->getNext());
            }
            delete current;
            return true;
        }
        prev = current;
        current = current->getNext();
    }

    return false;
}

void ScopeTable::print(ofstream& outfile) {
    outfile << "ScopeTable # " << nestedId << endl;
    for (int i = 0; i < size; i++) {
        SymbolInfo* current = table[i];
        if(current != NULL) {
            outfile << i << " --> " ;
            while(current != NULL) {
                outfile << "< " << current->getName() << " : " << "ID" << " >";
                current = current->getNext();
            }
            outfile << endl;
        }
    }
}

string ScopeTable::getNestedId() { return nestedId; }
ScopeTable* ScopeTable::getParentScope() { return parentScope; }
void ScopeTable::setParentScope(ScopeTable* parent) { parentScope = parent; }
int ScopeTable::getTotalCollisions() { return totalCollisions; }
void ScopeTable::incrementChildCounter() { childCounter++; }
void ScopeTable::setNestedId(string id) { nestedId = id; }
int ScopeTable::getChildCounter() { return childCounter; }

// ICG methods
int ScopeTable::getCurrentStackOffset() const {
    return currentStackOffset;
}

void ScopeTable::incrementStackOffset(int size) {
    currentStackOffset -= size;
}

int ScopeTable::getVariableCount() const {
    return variableCount;
}

void ScopeTable::incrementVariableCount() {
    variableCount++;
}

unsigned int sdbm_hash(string str) {
    unsigned int hash = 0;
    for (char c : str) {
        hash = c + (hash << 6) + (hash << 16) - hash;
    }
    return hash % 7;
}

SymbolTable::SymbolTable(int size) {
    currentScope = new ScopeTable(size);
    totalCollisions = 0;
    scopeCount = 1;
}

SymbolTable::~SymbolTable() {
    while (currentScope != NULL) {
        ScopeTable* temp = currentScope;
        currentScope = currentScope->getParentScope();
        delete temp;
    }
}

void SymbolTable::enterScope(int size, ofstream& outfile) {
    ScopeTable* newScope = new ScopeTable(size);
    newScope->setParentScope(currentScope);

    string parentId = (currentScope == NULL) ? "1" : currentScope->getNestedId();
    int depth = 1;
    ScopeTable* temp = currentScope;
    int maxDepth = 100;
    
    while (temp != NULL && depth < maxDepth) {
        depth++;
        temp = temp->getParentScope();
        
        if (temp == currentScope) {
            outfile << "ERROR: Circular reference detected in scope chain!" << endl;
            break;
        }
    }
    
    if (depth >= maxDepth) {
        outfile << "ERROR: Scope depth exceeded maximum (" << maxDepth << ")" << endl;
    }
    
    string newId = parentId;
    if (depth > 1) {
        newId += "." + to_string(currentScope->getChildCounter());
        currentScope->incrementChildCounter();
    }
    newScope->setNestedId(newId);

    currentScope = newScope;
}

void SymbolTable::exitScope(ofstream& outfile, int forceRemove) {
    if (currentScope == NULL) {
        outfile << "\t" << "No scope table to remove" << endl;
        return;
    }

    string currentId = currentScope->getNestedId();
    if (currentScope->getParentScope() == NULL && forceRemove == 0) {
        return;
    }

    totalCollisions += currentScope->getTotalCollisions();
    ScopeTable* toDelete = currentScope;
    if (currentScope != NULL) {
        printAllScopeTable(outfile);
    }
    currentScope = currentScope->getParentScope();

    delete toDelete;
}

bool SymbolTable::insert(SymbolInfo& symbol, ofstream& outfile) {
    if (currentScope == NULL) return false;
    return currentScope->insert(symbol, outfile);
}

SymbolInfo* SymbolTable::lookUp(string name, ofstream& outfile) {
    ScopeTable* temp = currentScope;
    while (temp != NULL) {
        SymbolInfo* found = temp->lookUp(name, outfile);
        if (found != NULL) return found;
        temp = temp->getParentScope();
    }
    return NULL;
}

void SymbolTable::printCurrentScopeTable(ofstream& outfile) {
    if (currentScope == NULL) {
        outfile << "\t" << "No current scope to print" << endl;
        return;
    }
    currentScope->print(outfile);
}

void SymbolTable::printAllScopeTable(ofstream& outfile) {
    ScopeTable* temp = currentScope;
    while (temp != NULL) {
        temp->print(outfile);
        temp = temp->getParentScope();
    }
    outfile << endl;
}

ScopeTable* SymbolTable::getCurrentScope() {
    return currentScope;
}