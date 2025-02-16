package com.example.ll1_parsing;

public class LL1Table {

    //non-terminals
    static String[] nonTerminals = {
            "module-decl", "module-heading", "block", "declarations", "const-decl",
            "const-list", "var-decl", "var-list", "var-item", "name-list", "more-names",
            "data-type", "procedure-decl", "procedure-heading", "stmt-list", "statement",
            "ass-stmt", "exp", "exp-prime", "term", "term-prime", "factor", "add-oper",
            "mul-oper", "read-stmt", "write-stmt", "write-list", "more-write-value",
            "write-item", "if-stmt", "else-part", "while-stmt", "loop-stmt", "exit-stmt",
            "call-stmt", "condition", "relational-oper", "name-value", "value","$"
    };

    //terminals
    static String[] terminals = {
            "|=", "(", ")", "*", "+", ",", "-", "/", ":", ";", "<", "<=", "=", ">",
            ">=", "begin", "call", "char", "const", "div", "else", "end", "exit",
            "if", "integer", "integer-value", "loop", "mod", "module", "name",
            "procedure", "readchar", "readint", "readln", "readreal", "real",
            "real-value", "until", "var", "while", "writechar", "writeint",
            "writeln", "writereal", "ε",".","then",":=","do"
    };

    //  LL(1) parsing table
    static String[][] parsingTable = new String[nonTerminals.length][terminals.length];

    // Initialize the table
    static {
        parsingTable[0][28] = "module-heading declarations block name .";

        parsingTable[1][28] = "module name ;";

        parsingTable[2][15] = "begin stmt-list end";

        parsingTable[3][15] = "const-decl var-decl procedure-decl";
        parsingTable[3][18] = "const-decl var-decl procedure-decl";
        parsingTable[3][30] = "const-decl var-decl procedure-decl";
        parsingTable[3][38] = "const-decl var-decl procedure-decl";

        parsingTable[4][15] = "ε";
        parsingTable[4][18] = "const const-list";
        parsingTable[4][30] = "ε";
        parsingTable[4][38] = "ε";
        parsingTable[4][44] = "ε";

        parsingTable[5][15] = "ε";
        parsingTable[5][29] = "name = value ; const-list";
        parsingTable[5][30] = "ε";
        parsingTable[5][38] = "ε";
        parsingTable[5][44] = "ε";

        parsingTable[6][15] = "ε";
        parsingTable[6][30] = "ε";
        parsingTable[6][38] = "var var-list";
        parsingTable[6][44] = "ε";


        parsingTable[7][15] = "ε";
        parsingTable[7][29] = "var-item ; var-list";
        parsingTable[7][30] = "ε";
        parsingTable[7][44] = "ε";

        parsingTable[8][29] = "name-list : data-type";

        parsingTable[9][29] = "name more-names";

        parsingTable[10][2] = "ε";
        parsingTable[10][5] = ", name-list";
        parsingTable[10][8] = "ε";

        parsingTable[11][17] = "char";
        parsingTable[11][24] = "integer";
        parsingTable[11][35] = "real";

        parsingTable[12][15] = "ε";
        parsingTable[12][30] = "procedure-heading declarations block name ; procedure-decl";


        parsingTable[13][30] = "procedure name ;";

        parsingTable[14][9] = "statement ; stmt-list";
        parsingTable[14][15] = "statement ; stmt-list";
        parsingTable[14][16] = "statement ; stmt-list";

        parsingTable[14][22] = "statement ; stmt-list";
        parsingTable[14][23] = "statement ; stmt-list";
        parsingTable[14][26] = "statement ; stmt-list";
        parsingTable[14][29] = "statement ; stmt-list";
        parsingTable[14][31] = "statement ; stmt-list";
        parsingTable[14][32] = "statement ; stmt-list";
        parsingTable[14][33] = "statement ; stmt-list";
        parsingTable[14][34] = "statement ; stmt-list";
        parsingTable[14][37] = "ε";
        parsingTable[14][39] = "statement ; stmt-list";
        parsingTable[14][40] = "statement ; stmt-list";
        parsingTable[14][41] = "statement ; stmt-list";
        parsingTable[14][42] = "statement ; stmt-list";
        parsingTable[14][43] = "statement ; stmt-list";
        parsingTable[14][44] = "ε";
        parsingTable[14][20] = "ε";
        parsingTable[14][21] = "ε";

        parsingTable[15][9] = "ε";
        parsingTable[15][15] = "block";
        parsingTable[15][16] = "call-stmt";
        parsingTable[15][22] = "exit-stmt";
        parsingTable[15][23] = "if-stmt";
        parsingTable[15][26] = "loop-stmt";
        parsingTable[15][29] = "ass-stmt";
        parsingTable[15][31] = "read-stmt";
        parsingTable[15][32] = "read-stmt";
        parsingTable[15][33] = "read-stmt";
        parsingTable[15][34] = "read-stmt";
        parsingTable[15][39] = "while-stmt";
        parsingTable[15][40] = "write-stmt";
        parsingTable[15][41] = "write-stmt";
        parsingTable[15][42] = "write-stmt";
        parsingTable[15][43] = "write-stmt";

        parsingTable[16][29] = "name := exp";

        parsingTable[17][1] = "term exp-prime";
        parsingTable[17][25] = "term exp-prime";
        parsingTable[17][29] = "term exp-prime";
        parsingTable[17][36] = "term exp-prime";


        parsingTable[18][4] = "add-oper term exp-prime";
        parsingTable[18][6] = "add-oper term exp-prime";
        parsingTable[18][9] = "ε";
        parsingTable[18][2] = "ε";

        parsingTable[19][1] = "factor term-prime";
        parsingTable[19][25] = "factor term-prime";
        parsingTable[19][29] = "factor term-prime";
        parsingTable[19][36] = "factor term-prime";


        parsingTable[20][2] = "ε";
        parsingTable[20][3] = "mul-oper factor term-prime";
        parsingTable[20][4] = "ε";
        parsingTable[20][6] = "ε";
        parsingTable[20][9] = "ε";
        parsingTable[20][7] = "mul-oper factor term-prime";

        parsingTable[20][19] = "mul-oper factor term-prime";
        parsingTable[20][27] = "mul-oper factor term-prime";
        parsingTable[20][44] = "ε";

        parsingTable[21][1] = "( exp )";
        parsingTable[21][25] = "name-value";
        parsingTable[21][29] = "name-value";
        parsingTable[21][36] = "name-value";


        parsingTable[22][4] = "+";
        parsingTable[22][6] = "-";

        parsingTable[23][3] = "*";
        parsingTable[23][7] = "/";
        parsingTable[23][19] = "div";
        parsingTable[23][27] = "mod";

        parsingTable[24][31] = "readchar ( name-list )";
        parsingTable[24][32] = "readint ( name-list )";
        parsingTable[24][33] = "readln";
        parsingTable[24][34] = "readreal ( name-list )";

        parsingTable[25][39] = "ε";
        parsingTable[25][40] = "writechar ( write-list )";
        parsingTable[25][41] = "writeint ( write-list )";
        parsingTable[25][42] = "writeln";
        parsingTable[25][43] = "writereal ( write-list )";

        parsingTable[26][25] = "write-item more-write-value";
        parsingTable[26][29] = "write-item more-write-value";
        parsingTable[26][36] = "write-item more-write-value";

        parsingTable[27][5] = ", write-list";
        parsingTable[27][2] = "ε";

        parsingTable[28][25] = "value";
        parsingTable[28][29] = "name";
        parsingTable[28][36] = "value";

        parsingTable[29][23] = "if condition then stmt-list else-part end";

        parsingTable[30][20] = "else stmt-list";
        parsingTable[30][21] = "ε";

        parsingTable[31][39] = "while condition do stmt-list end";

        parsingTable[32][26] = "loop stmt-list until condition";

        parsingTable[33][22] = "exit";

        parsingTable[34][16] = "call name";

        parsingTable[35][25] = "name-value relational-oper name-value";
        parsingTable[35][29] = "name-value relational-oper name-value";
        parsingTable[35][36] = "name-value relational-oper name-value";


        parsingTable[36][0] = "|=";
        parsingTable[36][10] = "<";
        parsingTable[36][11] = "<=";
        parsingTable[36][12] = "=";
        parsingTable[36][13] = ">";
        parsingTable[36][14] = ">=";

        parsingTable[37][25] = "value";
        parsingTable[37][29] = "name";
        parsingTable[37][36] = "value";

        parsingTable[38][25] = "integer-value";
        parsingTable[38][36] = "real-value";
    }

    // get rule from LL1 table
    public static String getRule(String nonTerminal, String terminal) {
        int nonTerminalIndex = -1;
        int terminalIndex = -1;
        for (int i = 0; i < nonTerminals.length; i++) {
            if (nonTerminals[i].equals(nonTerminal)) {
                nonTerminalIndex = i;
                break;
            }
        }
        for (int i = 0; i < terminals.length; i++) {
            if (terminals[i].equals(terminal)) {
                terminalIndex = i;
                break;
            }
        }
        if (nonTerminalIndex != -1 && terminalIndex != -1) {
            return parsingTable[nonTerminalIndex][terminalIndex];
        }
        return null;
    }
}
