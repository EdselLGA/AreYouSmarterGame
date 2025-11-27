package com.example.model;

/**
 * AnswerResult - Result of checking an answer
 * Contains information about whether answer was correct, if Save was used, and if Save saved the player
 */
public class AnswerResult {
    private final boolean isCorrect;
    private final boolean saveWasUsed;
    private final boolean saveSavedPlayer;
    
    public AnswerResult(boolean isCorrect, boolean saveWasUsed, boolean saveSavedPlayer) {
        this.isCorrect = isCorrect;
        this.saveWasUsed = saveWasUsed;
        this.saveSavedPlayer = saveSavedPlayer;
    }
    
    public boolean isCorrect() {
        return isCorrect;
    }
    
    public boolean saveWasUsed() {
        return saveWasUsed;
    }
    
    public boolean saveSavedPlayer() {
        return saveSavedPlayer;
    }
}

