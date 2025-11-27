package com.example.controller;

import com.example.view.MainFrame;
import com.example.view.NavigationListener;

/**
 * NavigationController handles navigation between main panels
 * Implements NavigationListener to respond to view events (MVC compliant)
 */
public class NavigationController implements NavigationListener {
    private final MainFrame mainFrame;

    public NavigationController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void onNavigateToSplash() {
        mainFrame.showPanel(MainFrame.CARD_SPLASH);
    }

    @Override
    public void onNavigateToMainMenu() {
        mainFrame.showPanel(MainFrame.CARD_MAINMENU);
    }

    @Override
    public void onNavigateToGame() {
        mainFrame.showPanel(MainFrame.CARD_GAME);
        // Start the game by navigating to HelperPanel
        GameController gameController = mainFrame.getGameController();
        if (gameController != null) {
            gameController.onNavigateToHelper();
        }
    }

    @Override
    public void onNavigateToMenuOptions() {
        mainFrame.showPanel(MainFrame.CARD_MENUOPTIONS);
    }

    @Override
    public void onNavigateToSettings() {
        mainFrame.showPanel(MainFrame.CARD_SETTINGS);
    }

    @Override
    public void onNavigateToHowToPlay() {
        mainFrame.showPanel(MainFrame.CARD_HOWTOPLAY);
    }

    @Override
    public void onNavigateToHighScores() {
        mainFrame.showPanel(MainFrame.CARD_HIGHSCORES);
    }

    @Override
    public void onExitApplication() {
        System.exit(0);
    }
}

