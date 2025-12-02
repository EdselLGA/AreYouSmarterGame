package com.example.model;

public enum Category {
    HISTORY("History"),
    GUESS_THE_PARADIGM("Guess the Paradigm"),
    GENERAL_CONCEPTS("General Concepts"),
    CODE_SNIPPETS("Code Snippets");

    private final String displayName;
    Category(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}