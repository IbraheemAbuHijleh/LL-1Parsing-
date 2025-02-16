package com.example.ll1_parsing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Tokenizer {
    private ArrayList<Token> tokens;
    private String codePath;
    private HashSet<String> reservedWords;
    int lineNumber = 0;

    public Tokenizer(String codePath)
    {
        tokens = new ArrayList<Token>();
        this.codePath = codePath;
    }

    public ArrayList<Token> Tokenize()
    {
        reservedWords = new HashSet<String>();
        for (ReservedWords word : ReservedWords.values()) {
            reservedWords.add(word.toString().toLowerCase());
        }

        try {
            String sourceCode = new String(Files.readAllBytes(Paths.get(codePath)));
            String[] lines = sourceCode.split("\n");
            lineNumber = 1;

            for (String line : lines) {
                tokenizeLine(line, lineNumber);
                lineNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


       updateTokens(tokens);

        for (Token token : tokens) {
            System.out.println(token.tokenValue);

        }
        return tokens;
    }

    private void tokenizeLine(String line, int lineNumber) {
        line = line.replaceAll("\\s+", " ");
        String[] tokensInLine = line.split("\\s+|(?=[()\\-+*/=;,:|])|(?<=[()\\-+*/=;,:|])");

        for (String tokenValue : tokensInLine) {
            try {
                determineTokenType(tokenValue);

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    private void determineTokenType(String tokenValue) {
        if (tokenValue.equals("")) {
            return;
        }
        String identifierPattern = "[a-zA-Z_][a-zA-Z0-9_]*";
        String operatorPattern = ":=|\\+|\\-|\\*|\\/|mod|div|=|<=|<|>=|>|\\|=";
        String specialCharPattern = "\\;|\\,|\\(|\\)|\\[|\\]|:|\\.";
        String realPattern = "[0-9]*\\.[0-9]";

        if (tokenValue.matches("\\d+\\.\\d+")) {
            tokens.add(new Token( tokenValue, lineNumber));
        } else if (tokenValue.matches("\\d+")) {
            tokens.add(new Token( tokenValue, lineNumber));
        } else if (reservedWords.contains(tokenValue)) {
            tokens.add(new Token( tokenValue, lineNumber));
        } else if (tokenValue.matches(operatorPattern)) {
            tokens.add(new Token( tokenValue, lineNumber));
        } else if (tokenValue.matches(specialCharPattern)) {
            tokens.add(new Token( tokenValue, lineNumber));
        }

        else if (Character.isLetter(tokenValue.charAt(0))) {
            if (tokenValue.equals("mod") || tokenValue.equals("div")) {
                tokens.add(new Token( tokenValue, lineNumber));
            } else {
                char[] charArray = tokenValue.toCharArray();
                int indexofspecial = checkspecialchars(charArray);
                if (indexofspecial == -1) {
                    tokens.add(new Token( tokenValue, lineNumber));
                } else {
                    while (indexofspecial != -1) {
                        char[] part1 = Arrays.copyOfRange(charArray, 0, indexofspecial);
                        char[] part2 = Arrays.copyOfRange(charArray, indexofspecial, charArray.length);

                        if (part1.length > 0) {
                            determineTokenType(String.valueOf(part1));
                        }

                        if (part2.length > 0) {
                            determineTokenType(String.valueOf(part2));
                        }

                        break;
                    }
                }
            }
        }
    }



    public int checkspecialchars(char[] chars)
    {
        for (int i = 0 ; i<chars.length ; i++)
        {
            if(Character.isLetterOrDigit(chars[i] )|| chars[i] == '.')
            {
                continue;
            }
            else
            {
                return i;

            }
        }
        return -1;
    }

    private void updateTokens(ArrayList<Token> tokens) {
        ArrayList<Token> updatedTokens = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            String tokenValue = token.tokenValue;

            if (tokenValue.length() > 1 && tokenValue.endsWith(".")) {
                String part1 = tokenValue.substring(0, tokenValue.length() - 1);
                String part2 = tokenValue.substring(tokenValue.length() - 1);
                updatedTokens.add(new Token(part1, token.lineNumber));
                updatedTokens.add(new Token(part2, token.lineNumber));
            }
            else if (tokenValue.equals(":") && i + 1 < tokens.size() && tokens.get(i + 1).tokenValue.equals("=")) {
                updatedTokens.add(new Token(":=", token.lineNumber));
                i++;
            }
            else if (tokenValue.equals("<") && i + 1 < tokens.size() && tokens.get(i + 1).tokenValue.equals("=")) {
                updatedTokens.add(new Token("<=", token.lineNumber));
                i++;
            }
            else if (tokenValue.equals(">") && i + 1 < tokens.size() && tokens.get(i + 1).tokenValue.equals("=")) {
                updatedTokens.add(new Token(">=", token.lineNumber));
                i++;
            }
            else {
                updatedTokens.add(token);
            }
        }
        updatedTokens.add(new Token("$",300));
        tokens.clear();
        tokens.addAll(updatedTokens);
    }

}




