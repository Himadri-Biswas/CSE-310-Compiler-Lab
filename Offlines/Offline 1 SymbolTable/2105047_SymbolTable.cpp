#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstring>

const int MAX_TOKENS = 100;

using namespace std;

class SymbolInfo
{
    private:

    string name;
    string type; // type of the symbol; can be "FUNCTION", "STRUCT", "UNION" ; or can be just a normal type like "int", "float", "char" etc; but cannot be empty

    SymbolInfo* next; // Pointer to the next symbol in the same hash bucket (for chaining in case of collision)
    
    public:

    SymbolInfo(){} // Default constructor

    SymbolInfo(string name, string type)
    {
        this->name=name;
        this->type=type;
    }

    SymbolInfo(string name, string type, SymbolInfo* next)
    {
        this->name=name;
        this->type=type;
        this->next=next;
    }
    
    // getters and setters for the attributes

    void setName(string name)
    {
        this->name=name;
    }

    string getName()
    {
        return name;
    }

    void setType(string type)
    {
        this->type=type;
    }

    string getType()
    {
        return type;
    }

    void setNext(SymbolInfo* next)
    {
        this->next=next;
    }

    SymbolInfo* getNext()
    {
        return next;
    }


    // overloading the << operator to print the symbol information in the specified format
    // friend function to allow non-member function to access the private members of SymbolInfo class
    
    friend ostream& operator<<(ostream& out, SymbolInfo& symbol)
    {
        out<<"<"<<symbol.name<<","; //First, print the name 
        
        string typeString = symbol.type;
        // Now we will slice the type string by space and keep the tokens in a string array
        stringstream ss(typeString);
        string token;
        string tokens[MAX_TOKENS]; // array to store the tokens in the type string
        int tokenCount = 0; 
        while (ss >> token) // while there are still some tokens in the type string
        {
            tokens[tokenCount] = token; 
            tokenCount++; 
        }
        
        if(tokenCount == 0) // this will never happen from now because we won't allow empty type
        {
            out<<">";
            return out;
        }

        // if the array is not empty, print the first token and then print the rest of the tokens in the specified format
        string keyword=tokens[0]; // the first token is the keyword

        if(keyword=="FUNCTION")
        {
            out<<keyword<<","; // print the keyword and a comma
            out<<tokens[1]<<"<==("; // print the return type and the opening parenthesis
            for(int i=2;i<tokenCount;i++) // print all the parameter types
            {
                out<<tokens[i];
                if(i!=tokenCount-1) 
                {
                    out<<","; // print comma between parameter types
                }
            }
            out << ")"; // closing parenthesis
        }
        else if(keyword=="STRUCT"||keyword=="UNION")
        {
            out<<keyword<<",{"; // print the keyword and the opening curly brace
            for(int i=1;i<tokenCount;i+=2) // printing all the members of the struct or union
            {
                out<<"("<<tokens[i]<<","<< tokens[i+1]<<")"; // print the member type and name in parentheses
                if(i != tokenCount - 2) out << ","; // print comma between members
            }
            out << "}"; // closing curly brace
        }
        else // if the keyword is not FUNCTION, STRUCT or UNION, then it is a normal type
        {
            out<<keyword; // print the keyword
        }
        out << ">"; // closing angle bracket
        return out;
    }

    ~SymbolInfo() 
    { 
        next = NULL;  
    }

};

class ScopeTable
{
    int uniqId; // Unique ID of the scope table 
    int position; // position of the symbol in the hash bucket (in the pointer to SymbolInfo objects)
    int bucketNum; // Index of the bucket in the hash table (in the array of pointers to SymbolInfo objects)
    int totalBuckets; // Total number of buckets in the hash table (size of the array of pointers to SymbolInfo objects)
    int hashFunctionNum; // Number of the hash function used to create the hash table (1 for sdbm(default), 2 for djb2, 3 for bkdr)


    SymbolInfo** symbolList; // Array of pointers to SymbolInfo objects (i.e. hash table)
    ScopeTable* parentScope; // Pointer to the parent scope (for nested scopes)

    int totalCollisions; 

    public :

    ScopeTable(){} // Default constructor

    ScopeTable(int uniqId, int totalBuckets, ScopeTable* parentScope, int hashFunctionNum=1)
    {
        this->uniqId=uniqId;
        this->totalBuckets=totalBuckets;
        this->parentScope=parentScope;
        this->hashFunctionNum=hashFunctionNum; // 1 for sdbm(default), 2 for djb2, 3 for bkdr

        this->totalCollisions=0; 

        symbolList=new SymbolInfo*[totalBuckets];

        // Initialize all buckets to NULL
        for(int i=0;i<totalBuckets;i++)
        {
            symbolList[i]=NULL;
        }
    }

    // SDBM (default)
    // Source : https://www.programmingalgorithms.com/algorithm/sdbm-hash/cpp/
    unsigned int sdbm_hash(const string &str)
    {
        unsigned int hash = 0;
        unsigned int i = 0;
        unsigned int len = str.length();

        for (i = 0; i < len; i++)
        {
            hash =( ((str[i]) + (hash << 6) + (hash << 16) - hash) ) % totalBuckets;
        }

        return hash;
    }

    // DJB2
    // Source: http://www.cse.yorku.ca/~oz/hash.html
    int djb2_hash(const string &str)
    {
        unsigned int hash = 5381;
        for (char ch : str)
        {
            hash =( ((hash << 5) + hash) + ch) % totalBuckets; // hash * 33 + ch
        }
        return hash;
    }

    // BKDR
    // Source: https://www.programmingalgorithms.com/algorithm/bkdr-hash/cpp/
    int bkdr_hash(const string &str)
    {
        unsigned int seed = 131;
        unsigned int hash = 0;
        for (char c : str)
            hash = (hash * seed + c) % totalBuckets;
        return hash;
    }


    void setUniqId(int uniqId)
    {
        this->uniqId=uniqId;
    }

    int getUniqId()
    {
        return uniqId;
    }

    void setTotalBuckets(int totalBuckets)
    {
        this->totalBuckets=totalBuckets;
    }

    int getTotalBuckets()
    {
        return totalBuckets;
    }

    void setParentScope(ScopeTable* parentScope)
    {
        this->parentScope=parentScope;
    }

    ScopeTable* getParentScope()
    {
        return parentScope;
    }

    int getTotalCollisions()
    {
        return totalCollisions;
    }

    // methods

    // function to look up a symbol in the hash table
    // check=1 for outputting the message to the console and file, check=0 for not outputting the message(internal use)
    SymbolInfo* lookUp(string name, ofstream& outfile, int check=1)
    {
        //int bucketNum=hashFunction(name); // First finding the bucket number in the hash table in which the symbol is stored
        
        if(hashFunctionNum==1)
            bucketNum=sdbm_hash(name);
        else if(hashFunctionNum==2)
            bucketNum=djb2_hash(name);
        else if(hashFunctionNum==3)
            bucketNum=bkdr_hash(name);
        SymbolInfo* temp=symbolList[bucketNum]; // The first symbol in the bucket
        position=1; // Position of the first symbol in the bucket(starts from 1)

        while(temp!=NULL)
        {
            if(temp->getName()==name)
            {
                if(check==1) 
                {
                    //cout<<"\t"<<"'"<<name<<"' found in ScopeTable# "<<uniqId<<" at position "<<bucketNum+1<<", "<<position<<"\n";
                    outfile<<"\t"<<"'"<<name<<"' found in ScopeTable# "<<uniqId<<" at position " <<bucketNum+1<<", "<<position<<"\n";
                }
                return temp;
            }
            position++;
            temp=temp->getNext();
        }
        return NULL;
    }

    // function to insert a symbol in the hash table
    bool insert(SymbolInfo& symbol, ofstream& outfile)
    {
        if(lookUp(symbol.getName(),outfile,0)!=NULL) // we won't print the lookup meessage in this case, so check=0
        {
            //cout<<"\t"<<"'"<<symbol.getName()<<"' already exists in the current ScopeTable"<<endl;
            outfile<<"\t"<<"'"<<symbol.getName()<<"' already exists in the current ScopeTable"<<endl;
            return false;
        }

        //int bucketNum=hashFunction(symbol.getName()); // First finding the bucket number in the hash table in which the symbol is to be inserted

        if(hashFunctionNum==1)
            bucketNum=sdbm_hash(symbol.getName());
        else if(hashFunctionNum==2)
            bucketNum=djb2_hash(symbol.getName());
        else if(hashFunctionNum==3)
            bucketNum=bkdr_hash(symbol.getName());
        position=1; // Position of the first symbol in the bucket(starts from 1)
        SymbolInfo* temp=symbolList[bucketNum]; // The first symbol in the bucket

        if(temp==NULL) // if the bucket is empty, insert the symbol in the bucket as the first symbol
        {
            symbolList[bucketNum]=&symbol;
            symbol.setNext(NULL);

            //cout<<"\t"<<"Inserted in ScopeTable# "<<uniqId<<" at position " <<bucketNum+1<<", "<<position<<endl;
            outfile<<"\t"<<"Inserted in ScopeTable# "<<uniqId<<" at position " <<bucketNum+1<<", "<<position<<endl;
            return true;
        }
        else
        {
            totalCollisions++; // if the bucket is not empty, then there is a collision
        }

        while(temp->getNext()!=NULL)
        {
            temp=temp->getNext();
            position++;
        }

        temp->setNext(&symbol); // Putting the address of the new symbol in the next pointer of the last symbol in the bucket
        symbol.setNext(NULL);
        position++;

        //cout<<"\t"<<"Inserted in ScopeTable# "<<uniqId<<" at position " <<bucketNum+1<<", "<<position<<endl;
        outfile<<"\t"<<"Inserted in ScopeTable# "<<uniqId<<" at position " <<bucketNum+1<<", "<<position<<endl;
        return true;
    }

    // function to delete a symbol from the hash table
    bool deleteSymbol(string name, ofstream& outfile)
    {
        if(lookUp(name, outfile, 0) == NULL)
        {
            outfile << "\t" << "Not found in the current ScopeTable" << endl;
            return false;
        }

        if(hashFunctionNum == 1)
            bucketNum = sdbm_hash(name);
        else if(hashFunctionNum == 2)
            bucketNum = djb2_hash(name);
        else if(hashFunctionNum == 3)
            bucketNum = bkdr_hash(name);
        
        position = 1; // Position of the first symbol in the bucket(starts from 1)
        SymbolInfo* temp = symbolList[bucketNum]; // The first symbol in the bucket

        if(temp->getName() == name) // if the symbol to be deleted is the first symbol in the bucket
        {
            SymbolInfo* toDelete = temp; // Store the pointer to be deleted
            symbolList[bucketNum] = temp->getNext(); // Update the bucket pointer
            
            delete toDelete; // Free the memory
            
            outfile << "\t" << "Deleted '" << name << "' from ScopeTable# " << uniqId << " at position " << bucketNum+1 << ", " << position << endl;
            return true;
        }

        // if not the first symbol
        while(temp->getNext() != NULL)
        {
            if(temp->getNext()->getName() == name)
            {
                SymbolInfo* toDelete = temp->getNext(); // Store the pointer to be deleted
                temp->setNext(toDelete->getNext()); // Update the linked list
                
                delete toDelete; // Free the memory
                
                outfile << "\t" << "Deleted '" << name << "' from ScopeTable# " << uniqId << " at position " << bucketNum+1 << ", " << position+1 << endl;
                return true;
            }
            position++;
            temp = temp->getNext(); // Moving to the next symbol in the bucket for further searching
        }
        return false; // Should never reach here but still returning false for avoiding warning
    }

    //function to print the scope table in the specified format
    // indent_level is the level of indentation for printing the scope table
    // it is by default 1 for the first scope table and increases by 1 for each nested scope table
    void print(ofstream& outfile, int indent_level = 1)
    {
        string indent(indent_level, '\t'); // create same number of tabs as the indent_level
        //cout << indent << "ScopeTable# " << uniqId << endl;
        outfile << indent << "ScopeTable# " << uniqId << endl;

        // i is the index of the bucket in the hash table(actual index; starts from 0)
        for(int i = 0; i < totalBuckets; i++)
        {
            // j is also the index of the bucket in the hash table but it starts from 1 for printing purpose
            int j = i + 1;
            //cout << indent << j << "-->";
            outfile << indent << j << "-->";

            SymbolInfo* temp_symbol = symbolList[i];

            while(temp_symbol != NULL)
            {
                //cout << " " << *temp_symbol;
                outfile << " " << *temp_symbol;

                temp_symbol = temp_symbol->getNext();
            }

            //cout << " " << endl;
            outfile << " " << endl;
        }
    }


    ~ScopeTable() // Destructor
    {
        for(int i=0;i<totalBuckets;i++)
        {
            SymbolInfo* temp=symbolList[i]; // The first symbol in the bucket
            while(temp!=NULL)
            {
                SymbolInfo* temp2=temp; // Storing the address of the current symbol to be deleted
                temp=temp->getNext(); // Moving to the next symbol in the bucket for further deleting
                delete temp2; // Deleting the current symbol
            }
        }
        
        delete[] symbolList; // Deleting the array of pointers to SymbolInfo objects(i.e. hash table)
    }

};


class SymbolTable
{
    ScopeTable* currentScope; // Pointer to the current scope table

    int totalCollisions; // Total number of collisions in the whole symbol table
    int totalScopeTables; // Total number of scope tables in the symbol table

    public:

    //Default constructor
    SymbolTable()
    {
        currentScope=NULL; // Initializing the current scope table to NULL
        totalCollisions=0; // Initializing the total number of collisions to 0
        totalScopeTables=0; // Initializing the total number of scope tables to 0
    }
    
    ~SymbolTable()
    {
        // Delete all remaining scope tables
        while (currentScope != NULL) {
            ScopeTable* toDelete = currentScope;
            currentScope = currentScope->getParentScope();
            delete toDelete;
        }
    }


    int getTotalCollisions()
    {
        return totalCollisions; 
    }

    void enterScope(int uniqId, int totalBuckets, ofstream& outfile, int hashFunctionNum)
    {
        ScopeTable* newScope=new ScopeTable(uniqId,totalBuckets,currentScope,hashFunctionNum); //sent the currentScope as the parent scope table of the newScope !
        currentScope=newScope; // Setting the newSCope as the current scope table

        totalScopeTables++; // Incrementing the total number of scope tables in the symbol table

        //cout<<"\t"<<"ScopeTable# "<<uniqId<<" created"<<endl;
        outfile<<"\t"<<"ScopeTable# "<<uniqId<<" created"<<endl;
    }


    void exitScope(ofstream& outfile, int forceRemove=0)
    {
        if (currentScope == NULL) {
            outfile << "\t" << "No scope table to remove" << endl;
            return;
        }

        int currentScopeUniqId = currentScope->getUniqId(); 

        if (currentScopeUniqId == 1 && forceRemove == 0) {
            outfile << "\t" << "ScopeTable# 1 cannot be removed" << endl;
            return;
        }

        outfile << "\t" << "ScopeTable# " << currentScope->getUniqId() << " removed" << endl;

        totalCollisions += currentScope->getTotalCollisions();
        
        // Save pointer to current scope to delete it
        ScopeTable* toDelete = currentScope;
        currentScope = currentScope->getParentScope();
        
        // delete the scope
        delete toDelete;

        return;
    }


    SymbolInfo* lookUp(string name, ofstream& outfile)
    {
        SymbolInfo* temp=NULL;
        ScopeTable* current=currentScope; 

        while(current!=NULL)
        {
            temp=current->lookUp(name,outfile); // Looking up the symbol in the current scope table
            if(temp!=NULL)
            {
                break; // whenever we find the symbol in the current recursion, we break the loop and return the symbol information
            }
            current=current->getParentScope(); // Moving to the parent scope table for further searching    
        }
        return temp; // Returning the symbol information if found, else NULL
    }

    bool insert(SymbolInfo& symbol, ofstream& outfile)
    {
        return currentScope->insert(symbol,outfile); 
    }

    bool remove(string name, ofstream& outfile)
    {
        if(currentScope==NULL)
        {
            //cout<<"\t"<<"no ScopeTable in the SymbolTable"<<endl;
            outfile<<"\t"<<"no ScopeTable in the SymbolTable"<<endl;
            return false;
        }
        return currentScope->deleteSymbol(name,outfile); // Deleting the symbol from the current scope table
    }

    void printCurrentScopeTable(ofstream& outfile)
    {
        if(currentScope==NULL)
        {
            //cout<<"\t"<<"no ScopeTable in the SymbolTable"<<endl;
            outfile<<"\t"<<"no ScopeTable in the SymbolTable"<<endl;
            return ;
        }
        currentScope->print(outfile);  // Printing the current scope table in the specified format
        return;
    }


    void printAllScopeTable(ofstream& outfile)
    {
        ScopeTable* temp = currentScope;
        int indent = 1;

        while (temp != NULL)
        {
            temp->print(outfile, indent);
            temp = temp->getParentScope();
            indent++;
        }
    }


    ScopeTable* getCurrentScope()
    {
        return currentScope; 
    }

    int getTotalScopeTables()
    {
        return totalScopeTables; // Returning the total number of scope tables in the symbol table
    }

};


