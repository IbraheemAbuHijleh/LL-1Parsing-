package com.example.ll1_parsing;


public class Token {
    String tokenValue;
    int lineNumber;
    public Token(String tokenValue, int lineNumber) {
        this.tokenValue = tokenValue;
        this.lineNumber = lineNumber;
    }
}