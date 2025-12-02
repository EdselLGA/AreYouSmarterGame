package com.example.view;

import java.awt.CardLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.example.model.Category;
import com.example.model.Helper;
import com.example.model.Question;

/**
 * GamePanel - main game panel with CardLayout (Dumb View)
 * Contains: HelperPanel, CategoryPanel, QuestionPanel, ResultsPanel
 * View is dumb - only displays what Controller tells it to display
 */
public class GamePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel gameContentPanel;

    // Card names for game flow panels
    public static final String CARD_HELPER = "helper";
    public static final String CARD_CATEGORY = "category";
    public static final String CARD_QUESTION = "question";
    public static final String CARD_RESULTS = "results";

    private HelperPanel helperPanel;
    private CategoryPanel categoryPanel;
    private QuestionPanel questionPanel;
    private ResultsPanel resultsPanel;

    public GamePanel() {
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new java.awt.BorderLayout());

        // Create CardLayout for game flow panels
        cardLayout = new CardLayout();
        gameContentPanel = new JPanel(cardLayout);

        // Initialize game flow panels (completely dumb - no controller references)
        helperPanel = new HelperPanel();
        categoryPanel = new CategoryPanel();
        questionPanel = new QuestionPanel();
        resultsPanel = new ResultsPanel();

        // Add panels to CardLayout
        gameContentPanel.add(helperPanel, CARD_HELPER);
        gameContentPanel.add(categoryPanel, CARD_CATEGORY);
        gameContentPanel.add(questionPanel, CARD_QUESTION);
        gameContentPanel.add(resultsPanel, CARD_RESULTS);

        add(gameContentPanel, java.awt.BorderLayout.CENTER);
    }
    
    /**
     * Set GameActionListener - called by Controller
     */
    public void setGameActionListener(GameActionListener listener) {
        helperPanel.setGameActionListener(listener);
        categoryPanel.setGameActionListener(listener);
        questionPanel.setGameActionListener(listener);
    }
    
    /**
     * Set GameNavigationListener - called by Controller
     */
    public void setGameNavigationListener(GameNavigationListener listener) {
        resultsPanel.setGameNavigationListener(listener);
    }

    /**
     * Show a specific panel in the game flow - called by Controller
     */
    public void showPanel(String cardName) {
        cardLayout.show(gameContentPanel, cardName);
    }

    /**
     * Update HelperPanel with available helpers - called by Controller
     */
    public void updateHelperPanel(Helper[] availableHelpers, int[] helperIndices) {
        helperPanel.updateHelpers(availableHelpers, helperIndices);
    }

    /**
     * Update CategoryPanel with available categories - called by Controller
     */
    public void updateCategoryPanel(Category[] availableCategories, int[] categoryIndices) {
        categoryPanel.updateCategories(availableCategories, categoryIndices);
    }

    /**
     * Update QuestionPanel with current question data - called by Controller
     */
    public void updateQuestionPanel(int questionNumber, int totalQuestions, int score,
                                    Question question, Category category, Helper helper,
                                    boolean canUseLifelines, boolean peekUsed,
                                    boolean copyUsed, boolean saveUsed, boolean answerLocked) {
        questionPanel.updateQuestion(questionNumber, totalQuestions, score, question,
                                    category, helper, canUseLifelines, peekUsed,
                                    copyUsed, saveUsed, answerLocked);
    }

    public void disableAnswerButtons() {
        questionPanel.disableAnswerButtons();
    }

    /**
     * Update ResultsPanel with game results - called by Controller
     */
    public void updateResultsPanel(int finalScore, boolean gameWon, boolean droppedOut, int questionNumber) {
        resultsPanel.updateResults(finalScore, gameWon, droppedOut, questionNumber);
    }

    /**
     * Show error message - called by Controller
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Update answer selection in QuestionPanel - called by Controller
     */
    public void updateAnswerSelection(int answerIndex) {
        questionPanel.updateAnswerSelection(answerIndex);
    }

    /**
     * Show peek suggestion in QuestionPanel - called by Controller
     */
    public void showPeekSuggestion(int suggestedIndex) {
        questionPanel.showPeekSuggestion(suggestedIndex);
    }

    /**
     * Show copy selection in QuestionPanel - called by Controller
     */
    public void showCopySelection(int selectedIndex) {
        questionPanel.showCopySelection(selectedIndex);
    }

    /**
     * Update save lifeline in QuestionPanel - called by Controller
     */
    public void updateSaveLifeline(boolean used) {
        questionPanel.updateSaveLifeline(used);
    }

    /**
     * Show answer result in QuestionPanel - called by Controller
     */
    public void showAnswerResult(boolean isCorrect, boolean saveWasUsed, boolean saveSavedPlayer) {
        questionPanel.showAnswerResult(isCorrect, saveWasUsed, saveSavedPlayer);
    }
}
