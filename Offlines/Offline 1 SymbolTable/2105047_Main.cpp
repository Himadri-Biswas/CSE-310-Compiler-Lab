# include "2105047_SymbolTable.cpp"


int main(int argc, char* argv[])
{
    // take the input file name from the command line argument; if not provided, use the default input file name
    string givenInputFileName;
    string defaultInputFileName = "sample_input.txt"; // Default input file name
    string givenOutputFileName;
    string defaultOutputFileName = "2105047_output.txt"; // Default output file name

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

    int hashFunctionNum;

    if(argc <= 3)
    {
        //cout << "No hash function specified. Using default sdbm_hash." << endl;
        hashFunctionNum = 1; // Default hash function is sdbm_hash
    }
    else if(argv[3] == string("sdbm"))
    {
        //cout << "Using sdbm_hash." << endl;
        hashFunctionNum = 1; // Use sdbm_hash
    }
    else if(argv[3] == string("djb2"))
    {
        //cout << "Using djb2_hash." << endl;
        hashFunctionNum = 2; // Use djb2_hash
    }
    else if(argv[3] == string("bkdr"))
    {
        //cout << "Using bkdr_hash." << endl;
        hashFunctionNum = 3; // Use bkdr_hash
    }
    
    ifstream infile(givenInputFileName); // Open the input file
    ofstream outfile(givenOutputFileName); // Open the output file

    //ifstream infile("sample_input.txt");
    //ofstream outfile("7th refine.txt");
    //ofstream outfile("output_of_sdbm.txt");
    //ofstream outfile("output_of_djb2.txt"); 
    //ofstream outfile("output_of_bkdr.txt");

    if (!infile || !outfile)
    {
        cerr << "Error opening file!" << endl;
        return 1;
    }

    string operation, name, type;
    int bucket_numbers, id = 1, remain = 1, cmp = 0;

    infile >> bucket_numbers;
    SymbolTable st;
    //st.enterScope(id, bucket_numbers, outfile);
    st.enterScope(id, bucket_numbers, outfile, hashFunctionNum); // Pass the hash function to enterScope

    string line;
    getline(infile, line); // consume leftover newline

    while (getline(infile, line)) // Read each line from the input file
    {
        if (!line.empty())
        {
            cmp++;
            // Put the real command when printing; can be unnormalized(have multiple spaces)
            // cout << "Cmd " << cmp << ": " << line << endl;
            // outfile << "Cmd " << cmp << ": " << line << endl;
        }

        stringstream linestream_copy(line);
        string word, normalizedLine;
        while (linestream_copy >> word) // skips multiple spaces
        {
            if (!normalizedLine.empty()) normalizedLine += " ";
            normalizedLine += word;
        }

        // while printing the command, we will print the normalized version of the command
        //cout << "Cmd " << cmp << ": " << normalizedLine << endl;
        outfile << "Cmd " << cmp << ": " << normalizedLine << endl;

        //Now tokenize the normalized version for safe parsing
        stringstream linestream(normalizedLine);
        //vector<string> tokens;
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

        if (operation == "I")
        {
            //if (tokenCount < 2)
            if(tokenCount <3) // We need at least 3 tokens for the command to be valid : operation ,name and type(at least one)
            {
                //cout << "\tNumber of parameters mismatch for the command " << operation << endl;
                outfile << "\tNumber of parameters mismatch for the command " << operation << endl;
            }
            else
            {
                SymbolInfo* symbol = new SymbolInfo(name, type);
                st.insert(*symbol, outfile);
            }
        }
        else if (operation == "L")
        {
            if (tokenCount != 2)
            {
                //cout << "\tNumber of parameters mismatch for the command " << operation << endl;
                outfile << "\tNumber of parameters mismatch for the command " << operation << endl;
            }
            else
            {
                SymbolInfo* sbl = st.lookUp(name, outfile);
                if (sbl == NULL)
                {
                    //cout << "\t'" << name << "' not found in any of the ScopeTables" << endl;
                    outfile << "\t'" << name << "' not found in any of the ScopeTables" << endl;
                }
            }
        }
        else if (operation == "D")
        {
            if (tokenCount != 2)
            {
                //cout << "\tNumber of parameters mismatch for the command " << operation << endl;
                outfile << "\tNumber of parameters mismatch for the command " << operation << endl;
            }
            else
            {
                st.remove(name, outfile);
            }
        }
        else if (operation == "P")
        {
            if (tokenCount != 2)
            {
                //cout << "\tNumber of parameters mismatch for the command " << operation << endl;
                outfile << "\tNumber of parameters mismatch for the command " << operation << endl;
            }
            else
            {
                if (name == "C")
                    st.printCurrentScopeTable(outfile);
                else if (name == "A")
                    st.printAllScopeTable(outfile);
            }
        }
        else if (operation == "S")
        {
            if (tokenCount != 1)
            {
                //cout << "\tNumber of parameters mismatch for the command " << operation << endl;
                outfile << "\tNumber of parameters mismatch for the command " << operation << endl;
            }
            else
            {
                id++;
                //st.enterScope(id, bucket_numbers, outfile);
                st.enterScope(id, bucket_numbers, outfile, hashFunctionNum); // Pass the hash function to enterScope
                remain++;
            }
        }
        else if (operation == "E")
        {
            if (tokenCount != 1)
            {
                //cout << "\tNumber of parameters mismatch for the command " << operation << endl;
                outfile << "\tNumber of parameters mismatch for the command " << operation << endl;
            }
            else
            {
                if (remain > 1)
                {
                    st.exitScope(outfile);
                    remain--;
                }
                else
                {
                    //cout << "\tScopeTable# 1 cannot be removed" << endl;
                    outfile << "\tScopeTable# 1 cannot be removed" << endl;
                }
            }
        }
        else if (operation == "Q")
        {
            break;
        }
    }

    while (remain--)
    {
        st.exitScope(outfile, 1); // forceRemove=1 to remove the global scope table also
    }

    infile.close();
    outfile.close();
    return 0;
}
