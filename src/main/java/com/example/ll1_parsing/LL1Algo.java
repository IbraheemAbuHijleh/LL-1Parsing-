package com.example.ll1_parsing;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LL1Algo {
    private LL1Table parser;
    private Stack<String> stack;

    public LL1Algo() {
        parser = new LL1Table();
        stack = new Stack<>();
        stack.push("$"); // Bottom of stack marker
        stack.push("module-decl"); // Starting symbol
    }


    //Algorithm for LL(1)
    public String parseInput(ArrayList<Token> inputTokens) {
        ArrayList<Token> realInput = new ArrayList<>(inputTokens);
        int inputIndex = 0;
        String topOfStack = "";
        String result = "";
        while (!stack.isEmpty()) {
            Token currentToken = inputTokens.get(inputIndex);
            String tokenValue = currentToken.tokenValue;

            if (!isTerminal(tokenValue) && isName(tokenValue)) {
                tokenValue = "name";
            } else if (!isTerminal(tokenValue) && isIntegerValue(tokenValue)) {
                tokenValue = "integer-value";
            } else if (!isTerminal(tokenValue) && isRealNumber(tokenValue)) {
                tokenValue = "real-value";
            }

            topOfStack = stack.pop();

            if (tokenValue.equals("$") && topOfStack.equals("$")) {
                System.out.println("Input parsed successfully.");
                result = "Input parsed successfully.";
                return result;
            } else if (isTerminal(topOfStack)) {
                if (inputIndex < inputTokens.size() && topOfStack.equals(tokenValue)) {
                    inputIndex++;
                } else {
                    if (realInput.get(inputIndex).tokenValue.equals("$")) {
                        System.out.println("Syntax error: code does not complete");
                        result="Syntax error: code does not complete";
                    } else {
                        System.out.println("Syntax error -- > Line "+currentToken.lineNumber+" : Unexpected token " + realInput.get(inputIndex).tokenValue);
                        result="Syntax error -- > Line "+currentToken.lineNumber+" : Unexpected token " + realInput.get(inputIndex).tokenValue;
                    }
                    return result;
                }
            } else if (isNonTerminal(topOfStack)) {
                String production = parser.getRule(topOfStack, tokenValue);
                if (production != null) {
                    if (!production.equals("ε")) {
                        String[] productionSymbols = production.split("\\s+");
                        for (int i = productionSymbols.length - 1; i >= 0; i--) {
                            stack.push(productionSymbols[i]);
                        }
                    }
                } else {
                    if (realInput.get(inputIndex).tokenValue.equals("$")) {
                        System.out.println("Syntax error: code does not complete");
                        result="Syntax error: code does not complete";
                    } else {
                        System.out.println("Syntax error -- > Line "+currentToken.lineNumber+": Unexpected token " + realInput.get(inputIndex).tokenValue);
                        result="Syntax error -- > Line "+currentToken.lineNumber+": Unexpected token " + realInput.get(inputIndex).tokenValue;
                    }
                    return result;
                }
            } else {
                System.out.println("Syntax error: Unexpected symbol '" + topOfStack + "'");
                result="Syntax error: Unexpected symbol '" + topOfStack + "'";
                return result;
            }
        }
        return result;
    }


    private boolean isNonTerminal(String symbol) {
        for (String nonTerminal : LL1Table.nonTerminals) {
            if (nonTerminal.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTerminal(String symbol) {
        for (String terminal : LL1Table.terminals) {
            if (terminal.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    public  boolean isName(String input) {
        String namePattern = "[a-zA-Z][a-zA-Z0-9]*";
        Pattern pattern = Pattern.compile(namePattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public  boolean isIntegerValue(String input) {
        String intValuePattern = "\\d+";
        Pattern pattern = Pattern.compile(intValuePattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public  boolean isRealNumber(String input) {
        String realNumberPattern = "\\d+\\.\\d*|\\d*\\.\\d+";
        Pattern pattern = Pattern.compile(realNumberPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
