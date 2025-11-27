package com.example.model;

public enum Category {
    HISTORY("History"),
    GUESS_THE_PARADIGM("Guess the Paradigm"),
    TRUE_OR_FALSE("True or False"),
    CODE_SNIPPETS("Code Snippets");

    private final String displayName;
    Category(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}