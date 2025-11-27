package com.example.view;

/**
 * GameActionListener interface for game action events
 * Views fire these events, GameController listens to them
 */
public interface GameActionListener {
    // Helper selection
    void onHelperSelected(int helperIndex);
    
    // Category selection
    void onCategorySelected(int categoryIndex);
    
    // Answer selection
    void onAnswerSelected(int answerIndex);
    
    // Lifelines
    void onPeekRequested();
    void onCopyRequested();
    void onSaveRequested();
    
    // Game actions
    void onLockAnswer();
    void onDropOut();
    
    // Navigation
    void onContinueAfterQuestion();
}

