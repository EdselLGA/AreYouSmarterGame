package com.example.view;

/**
 * NavigationListener interface for navigation events
 * Views fire these events, controllers listen to them
 */
public interface NavigationListener {
    void onNavigateToSplash();
    void onNavigateToMainMenu();
    void onNavigateToGame();
    void onNavigateToMenuOptions();
    void onNavigateToSettings();
    void onNavigateToHowToPlay();
    void onNavigateToHighScores();
    void onExitApplication();
}

