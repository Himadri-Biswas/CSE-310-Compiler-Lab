#include "2105047_SymbolTable.cpp"


int main(int argc, char* argv[])
{
    string givenInputFileName;
    string defaultInputFileName = "sample_input.txt"; // Default input file name

    string givenOutputFileName;
    string defaultOutputFileName = "2105047_report.txt"; // Default output file name

    if(argc > 1)
    {
        givenInputFileName = argv[1]; // Input file name from command line argument
    }
    else
    {
        givenInputFileName = defaultInputFileName; // Default input file name
        cout << "No input file name specified. Trying to use default input file: " << defaultInputFileName << endl;
    }
    if(argc > 2)
    {
        givenOutputFileName = argv[2]; // Output file name from command line argument
    }
    else
    {
        givenOutputFileName = defaultOutputFileName; // Default output file name
        cout << "No output file name specified. Trying to use default output file: " << defaultOutputFileName << endl;
    }

    //string inputFileName = "sample_input.txt"; // Default input file name
    int bucketSize;

    string hashFunctions[] = {"sdbm", "djb2", "bkdr"};
    //ofstream report("2105047_report.txt");
    ofstream report(givenOutputFileName); // Open the report file
    
    int totalCollisionsForEachHashFunction[3] = {0, 0, 0}; // To store total collisions for each hash function
    float ratioOfCollisions[3] = {0.0, 0.0, 0.0}; // To store ratio of collisions for each hash function
    int totalScopeTablesForEachHashFunction[3] = {0, 0, 0}; // To store total scope tables for each hash function
    float meanRatios[3] = {0.0, 0.0, 0.0}; // To store ratio of scope tables for each hash function

    // ifstream infile("sample_input.txt");
    // ofstream outfile("6th refine.txt");

    // if (!infile || !outfile)
    // {
    //     cerr << "Error opening file!" << endl;
    //     return 1;
    // }

    // we wil run the same input for all the hashfuntions; each time just before exiting each scope table , we wil store the total number of collisions in a variable and then print it in the report file

    for (int i = 0; i < 3; i++)
    {
        ifstream infile(givenInputFileName); // Open the input file for each hash function

        if (!infile)
        {
            cerr << "Error opening input file!" << endl;
            return 1;
        }

        report << "Using hash function: " << hashFunctions[i] << endl;

        int hashFunctionNum = i + 1; // 1 for sdbm, 2 for djb2, 3 for bkdr


        string operation, name, type;
        int bucket_numbers, id = 1, remain = 1, cmp = 0;

        infile >> bucket_numbers;

        bucketSize = bucket_numbers; // Store the bucket size for later use

        SymbolTable st;
        st.enterScope(id, bucket_numbers, report, hashFunctionNum); // Pass the hash function to enterScope

        string line;
        getline(infile, line); // consume leftover newline

        //we dont need to print the commands in the report file; we will just print the total number of collisions in the report file
        while(getline(infile, line))
        {
            if(!line.empty())
            {
                cmp++;
            }

            stringstream linestream_copy(line);
            string word, normalizedLine;
            while (linestream_copy >> word) // skips multiple spaces
            {
                if (!normalizedLine.empty()) normalizedLine += " ";
                normalizedLine += word;
            }

            //Now tokenize the normalized version for safe parsing
            stringstream linestream(normalizedLine);
            string tokens[MAX_TOKENS]; // array to store the tokens in the type string
            string item;
            int tokenCount = 0; // index of the array
            while (linestream >> item) // while there are still some tokens in the type string
            {
                if (!item.empty())
                {
                    tokens[tokenCount] = item; // add the token to the array
                    tokenCount++; // increment the index
                }
            }

            if (tokenCount > 0) operation = tokens[0];
            if (tokenCount > 1) name = tokens[1];

            // Reconstruct the full type string from tokens[2..n]
            type = "";
            for (size_t i = 2; i < tokenCount; i++)
            {
                if (!type.empty()) type += " ";
                type += tokens[i];
            }

            if(operation=="I")
            {
                if(tokenCount<2)
                {
                    // cout<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                    // report<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                    // do nothing; as we are not printing the commands in the report file
                }
                else
                {
                    SymbolInfo* symbol = new SymbolInfo(name, type);
                    st.insert(*symbol, report);
                }
            }
            else if(operation=="L")
            {
                if(tokenCount!=2)
                {
                    // cout<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                    // report<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                    // do nothing; as we are not printing the commands in the report file
                }
                else
                {
                    SymbolInfo* sbl=st.lookUp(name, report);
                    if(sbl==NULL)
                    {
                        // cout<<"\t'"<<name<<"' not found in any of the ScopeTables"<<endl;
                        // report<<"\t'"<<name<<"' not found in any of the ScopeTables"<<endl;
                    }
                }
            }
            else if(operation=="D")
            {
                if(tokenCount!=2)
                {
                    // cout<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                    // report<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                }
                else
                {
                    st.remove(name, report);
                }
            }
            else if(operation=="P")
            {
                if(tokenCount!=2)
                {
                    // cout<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                    // report<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                }
                else
                {
                    // if(name=="C")
                    //     st.printCurrentScopeTable(report);
                    // else if(name=="A")
                    //     st.printAllScopeTable(report);
                }
            }
            else if(operation=="S")
            {
                if(tokenCount!=1)
                {
                    // cout<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                    // report<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                }
                else
                {
                    id++;
                    st.enterScope(id, bucket_numbers, report, hashFunctionNum); // Pass the hash function to enterScope
                    remain++;
                }
            }
            else if(operation=="E")
            {
                if(tokenCount!=1)
                {
                    // cout<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                    // report<<"\tNumber of parameters mismatch for the command "<<operation<<endl;
                }
                else
                {
                    if(remain>1)
                    {
                        st.exitScope(report);
                        remain--;
                    }
                    else
                    {
                        // cout<<"\tScopeTable# 1 cannot be removed"<<endl;
                        // report<<"\tScopeTable# 1 cannot be removed"<<endl;
                    }
                }
            }
            else if(operation=="Q")
            {
                break;
            }
        }
        while(remain--)
        {
            st.exitScope(report, 1);
        }

        // Store the total number of collisions for the current hash function
        totalCollisionsForEachHashFunction[i] = st.getTotalCollisions(); // Assuming you have a method to get total collisions
        // Calculate the ratio of collisions for the current hash function
        ratioOfCollisions[i] = (float)totalCollisionsForEachHashFunction[i] / bucketSize; // Assuming bucketSize is the size of the hash table
        // Calculate the total number of scope tables for the current hash function
        totalScopeTablesForEachHashFunction[i] = st.getTotalScopeTables(); // Assuming you have a method to get total scope tables
        // Calculate the mean ratio of collisions for the current hash function
        if (totalScopeTablesForEachHashFunction[i] > 0) {
            meanRatios[i] = ratioOfCollisions[i] / totalScopeTablesForEachHashFunction[i];
        } else {
            meanRatios[i] = 0.0; // Avoid division by zero
        }
        infile.close();
        // //clear the current contest of the report file
        // report.clear(); // Clear the current content of the report file
        // report.seekp(0); // Move the write pointer to the beginning of the file
    }
    
    //clear the current contest of the report file by reopening it
    report.close(); // Close the report file
    //report.open("report.txt", ios::trunc); // Open the report file in truncate mode to clear its content
    report.open(givenOutputFileName, ios::trunc); // Open the report file in truncate mode to clear its content

    report << "ID: 2105047" << endl;
    report << "Report for collision ratio of each hash function used in the symbol table" << endl;
    report << endl;
    report << "----------------------------------------------------------------------------------" << endl;
    report << endl;

    report << "Input file name: " << givenInputFileName << endl;
    report << "Number of buckets: " << bucketSize << endl;
    report << endl;
    report << "----------------------------------------------------------------------------------" << endl;
    report << endl;

    report << "SDBM Hash Function: " << endl;
    report << "Source: https://www.programmingalgorithms.com/algorithm/sdbm-hash/cpp/"<<endl;
    report << "Total Collisions: " << totalCollisionsForEachHashFunction[0] << endl;
    report << "Ratio of Collisions: " << ratioOfCollisions[0] << endl;
    report << "Total Scope Tables: " << totalScopeTablesForEachHashFunction[0] << endl;
    report << "Mean Ratio of collisions: " << meanRatios[0] << endl;
    //report << "----------------------------------------" << endl;
    report << endl;
    report << "----------------------------------------------------------------------------------" << endl;
    report << endl;

    report << "DJB2 Hash Function: " << endl;
    report << "Source: http://www.cse.yorku.ca/~oz/hash.html" <<endl;
    report << "Total Collisions: " << totalCollisionsForEachHashFunction[1] << endl;
    report << "Ratio of Collisions: " << ratioOfCollisions[1] << endl;
    report << "Total Scope Tables: " << totalScopeTablesForEachHashFunction[1] << endl;
    report << "Mean Ratio of collisions: " << meanRatios[1] << endl;
    report << endl;
    report << "----------------------------------------------------------------------------------" << endl;
    report << endl;
    
    report << "BKDR Hash Function: " << endl;
    report << "Source: https://www.programmingalgorithms.com/algorithm/bkdr-hash/cpp/"<<endl;
    report << "Total Collisions: " << totalCollisionsForEachHashFunction[2] << endl;
    report << "Ratio of Collisions: " << ratioOfCollisions[2] << endl;
    report << "Total Scope Tables: " << totalScopeTablesForEachHashFunction[2] << endl;
    report << "Mean Ratio of collisions: " << meanRatios[2] << endl;
    report << endl;
    report << "----------------------------------------------------------------------------------" << endl;
    report << endl;
}

