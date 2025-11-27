package com.example.view;

/**
 * GameNavigationListener interface for game flow navigation events
 * Game flow views fire these events, GameController listens to them
 */
public interface GameNavigationListener {
    void onNavigateToHelper();
    void onNavigateToCategory();
    void onNavigateToQuestion();
    void onNavigateToResults();
    void onNavigateToMainMenu();
}

