package com.example.controller;

import com.example.model.GameState;
import com.example.model.Question;
import com.example.view.GamePanel;
import com.example.view.GameNavigationListener;
import com.example.view.GameActionListener;
import com.example.view.EscapeKeyHandler;


/**
 * GameController manages game flow and navigation within GamePanel
 * Takes Model (GameState) and View (GamePanel) in constructor - proper MVC
 * Implements GameNavigationListener and GameActionListener to respond to view events
 */
public class GameController implements GameNavigationListener, GameActionListener {
    private final GamePanel gamePanel;
    private final GameState gameState;
    private EscapeKeyHandler escapeKeyHandler;
    private NavigationController navigationController;

    /**
     * Constructor - Controller takes Model and View (proper MVC)
     */
    public GameController(GameState gameState, GamePanel gamePanel) {
        this.gameState = gameState;
        this.gamePanel = gamePanel;
        
        // Wire up listeners - Controller subscribes to View events
        gamePanel.setGameActionListener(this);
        gamePanel.setGameNavigationListener(this);
        escapeKeyHandler = new EscapeKeyHandler(this, true);

    }
    
    /**
     * Set NavigationController - called by MainFrame to enable navigation to main menu
     */
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    public void dispose() {
        if (escapeKeyHandler != null) {
            escapeKeyHandler.unregister();
        }
    }

    public void onGamePanelShown() {
        if (escapeKeyHandler != null) {
            escapeKeyHandler.register();
        }
    }

    public void onGamePanelHidden() {
        if (escapeKeyHandler != null) {
            escapeKeyHandler.unregister();
        }
    }

    // Navigation methods
    @Override
    public void onNavigateToHelper() {
        // Reset game if starting fresh or if game was over
        if (gameState.getCurrentQuestionNumber() == 0 || gameState.isGameOver()) {
            gameState.reset();
        }
        
        // Always show helper panel and update with available helpers
        gamePanel.showPanel(GamePanel.CARD_HELPER); 
        gamePanel.updateHelperPanel(gameState.getAvailableHelpers(),gameState.getAvailableHelperIndices());
        
        // If helper selection not needed, automatically proceed
        if (!gameState.needsHelperSelection()) {
            // Skip helper selection, go to category or question
            if (gameState.needsCategorySelection()) {
                onNavigateToCategory();
            } else {
                onNavigateToQuestion();
            }
        }
    }

    @Override
    public void onNavigateToCategory() {
        // Check if category selection is needed
        if (gameState.needsCategorySelection()) {
            gamePanel.showPanel(GamePanel.CARD_CATEGORY);
            gamePanel.updateCategoryPanel(gameState.getAvailableCategories(),gameState.getAvailableCategoryIndices());
        } else {
            // Skip category selection, go to question
            onNavigateToQuestion();
        }
    }

    @Override
    public void onNavigateToQuestion() {
        try {
            // Ensure category is selected before getting question
            if (gameState.needsCategorySelection()) {
                // Category not selected - navigate to category selection instead
                onNavigateToCategory();
                return;
            }
            
            Question question = gameState.getNextQuestion();
            gamePanel.showPanel(GamePanel.CARD_QUESTION);
            // Display question number as currentQuestionNumber + 1 (1-based for display)
            int displayQuestionNumber = gameState.getCurrentQuestionNumber() + 1;
            gamePanel.updateQuestionPanel(
                displayQuestionNumber,
                gameState.getTotalQuestions(),
                gameState.getCurrentScore(),
                question,
                gameState.getCurrentCategory(),
                gameState.getCurrentHelper(),
                gameState.canUseLifelines(),
                gameState.isPeekUsed(),
                gameState.isCopyUsed(),
                gameState.isSaveUsed(),
                gameState.isAnswerLocked()
            );
        } catch (IllegalStateException e) {
            // Game over or error - show error message and navigate appropriately
            String errorMsg = e.getMessage();
            gamePanel.showError("Error: " + errorMsg);
            
            // If it's a category error, try to navigate to category selection
            if (errorMsg != null && errorMsg.contains("Category")) {
                if (gameState.needsCategorySelection()) {
                    onNavigateToCategory();
                } else {
                    onNavigateToResults();
                }
            } else {
                // Other errors - go to results
                onNavigateToResults();
            }
        } catch (Exception e) {
            // Unexpected error
            gamePanel.showError("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            onNavigateToResults();
        }
    }

    @Override
    public void onNavigateToResults() {
        gamePanel.showPanel(GamePanel.CARD_RESULTS);
        // Display question number as currentQuestionNumber + 1 (1-based for display)
        // If game over due to wrong answer, this is the question they got wrong
        int displayQuestionNumber = gameState.getCurrentQuestionNumber() + 1;
        gamePanel.updateResultsPanel(
            gameState.getCurrentScore(),
            gameState.isGameWon(),
            gameState.isDroppedOut(),
            displayQuestionNumber
        );
    }

    @Override
    public void onNavigateToMainMenu() {
        // Navigate to main menu through NavigationController
        if (navigationController != null) {
            navigationController.onNavigateToMainMenu();
        }
    }

    // Game action methods
    @Override
    public void onHelperSelected(int helperIndex) {
        try {
            gameState.selectHelper(helperIndex);
            // After helper selection, ALWAYS check if category is needed first
            // At game start, category will always be null, so we need to select it
            if (gameState.needsCategorySelection()) {
                onNavigateToCategory();
            } else {
                // Category exists, go to question
                onNavigateToQuestion();
            }
        } catch (IllegalArgumentException e) {
            // Invalid selection - stay on helper panel
            gamePanel.showError("Invalid helper selection: " + e.getMessage());
        } catch (Exception e) {
            // Any other error
            gamePanel.showError("Error selecting helper: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onCategorySelected(int categoryIndex) {
        try {
            gameState.selectCategory(categoryIndex);
            // After category selection, go to question
            onNavigateToQuestion();
        } catch (IllegalArgumentException e) {
            // Invalid selection - stay on category panel
            gamePanel.showError("Invalid category selection");
        }
    }

    @Override
    public void onAnswerSelected(int answerIndex) {
        if (!gameState.isAnswerLocked()) {
            gameState.selectAnswer(answerIndex);
            gamePanel.updateAnswerSelection(answerIndex);
        }
    }

    @Override
    public void onPeekRequested() {
        try {
            int suggestedIndex = gameState.usePeek();
            gamePanel.showPeekSuggestion(suggestedIndex);
        } catch (IllegalStateException e) {
            gamePanel.showError("Peek cannot be used");
        }
    }

    @Override
    public void onCopyRequested() {
        try {
            int selectedIndex = gameState.useCopy();
            gamePanel.showCopySelection(selectedIndex);
            gamePanel.updateAnswerSelection(selectedIndex);
        } catch (IllegalStateException e) {
            gamePanel.showError("Copy cannot be used");
        }
    }

    @Override
    public void onSaveRequested() {
        try {
            gameState.useSave();
            gamePanel.updateSaveLifeline(true);
        } catch (IllegalStateException e) {
            gamePanel.showError("Save cannot be used");
        }
    }

    @Override
    public void onLockAnswer() {
        if (gameState.lockAnswer()) {
            gamePanel.disableAnswerButtons();
            com.example.model.AnswerResult result = gameState.checkAnswer();
            gamePanel.showAnswerResult(result.isCorrect(), result.saveWasUsed(), result.saveSavedPlayer());
            
            if (gameState.isGameOver()) {
                // Wrong answer or game won - show results after delay
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    onNavigateToResults();
                }).start();
            } else {
                // Correct answer (or saved) - continue to next question after delay
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    // Check if helper or category selection is needed before next question
                    if (gameState.needsHelperSelection()) {
                        onNavigateToHelper();
                    } else if (gameState.needsCategorySelection()) {
                        onNavigateToCategory();
                    } else {
                        onNavigateToQuestion();
                    }
                }).start();
            }
        } else {
            gamePanel.showError("Please select an answer first");
        }
    }

    @Override
    public void onDropOut() {
        gameState.dropOut();
        onNavigateToResults();
    }

    @Override
    public void onContinueAfterQuestion() {
        // Check if helper or category selection is needed
        if (gameState.needsHelperSelection()) {
            onNavigateToHelper();
        } else if (gameState.needsCategorySelection()) {
            onNavigateToCategory();
        } else if (gameState.getCurrentQuestionNumber() < gameState.getTotalQuestions()) {
            onNavigateToQuestion();
        } else {
            onNavigateToResults();
        }
    }

    public GameState getGameState() {
        return gameState;
    }
}
