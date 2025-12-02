package com.example.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * GameState - Manages all game logic and state
 * Handles questions, helpers, categories, scoring, and lifelines
 */
public class GameState {
    // Game constants
    private static final int TOTAL_QUESTIONS = 11;
    private static final int[] PRIZE_LADDER = {
        100, 200, 300, 500, 1000, 2000, 4000, 8000, 16000, 32000, 1000000
    };
    private static final int HELPER_DURATION = 2; // Helper lasts 2 questions
    private static final int CATEGORY_DURATION = 3; // Category lasts 3 questions (Q1-3: Cat A, Q4-6: Cat B, etc.)
    
    // Available helpers with accuracy percentages
    private static final Helper[] ALL_HELPERS = {
        new Helper("Helper 1", 20),
        new Helper("Helper 2", 40),
        new Helper("Helper 3", 60),
        new Helper("Helper 4", 80),
        new Helper("Helper 5", 100)
    };
    
    // Available categories
    private static final Category[] ALL_CATEGORIES = Category.values();
    
    // Game state
    private int currentQuestionNumber;
    private int currentScore;
    private Question currentQuestion;
    private Helper currentHelper;
    private Category currentCategory;
    //private List<Helper> availableHelpers;
    //private List<Category> availableCategories;
    private boolean[] categoriesUsed;
    private boolean[] helpersUsed;

    //
    private int questionsWithCurrentHelper;
    private int questionsWithCurrentCategory;
    private QuestionBank questionBank;
    
    // Lifeline states
    private boolean peekUsed;
    private boolean copyUsed;
    private boolean saveUsed;
    private int peekSuggestedIndex;
    private int copySelectedIndex;
    private int playerSelectedIndex;
    private boolean answerLocked;
    
    // Game status
    private boolean gameOver;
    private boolean gameWon;
    private boolean droppedOut;
    
    public GameState() {
        reset();
    }
    
    public void reset() {
        currentQuestionNumber = 0;
        currentScore = 0;
        currentQuestion = null;
        currentHelper = null;
        currentCategory = null;
        //availableHelpers = new ArrayList<>(Arrays.asList(ALL_HELPERS));
        //availableCategories = new ArrayList<>(Arrays.asList(ALL_CATEGORIES));
        categoriesUsed = new boolean[ALL_CATEGORIES.length];
        helpersUsed = new boolean[ALL_HELPERS.length];
        //
        questionsWithCurrentHelper = 0;
        questionsWithCurrentCategory = 0;
        questionBank = new QuestionBank();
        peekUsed = false;
        copyUsed = false;
        saveUsed = false;
        peekSuggestedIndex = -1;
        copySelectedIndex = -1;
        playerSelectedIndex = -1;
        answerLocked = false;
        gameOver = false;
        gameWon = false;
        droppedOut = false;
    }
    
    // Helper selection
    public void selectHelper(int helperIndex) {
        if (helperIndex < 0 || helperIndex >= ALL_HELPERS.length) {
            throw new IllegalArgumentException("Invalid helper index");
        }
        if (helpersUsed[helperIndex]) {
            throw new IllegalStateException("Helper already used");
        }
        currentHelper = ALL_HELPERS[helperIndex];
        helpersUsed[helperIndex] = true;
        questionsWithCurrentHelper = 0;
        resetLifelines();
    }

    
    // Category selection
    // public void selectCategory(int categoryIndex) {
    //     if (categoryIndex < 0 || categoryIndex >= availableCategories.size()) {
    //         throw new IllegalArgumentException("Invalid category index");
    //     }
    //     currentCategory = availableCategories.remove(categoryIndex);
    //     questionsWithCurrentCategory = 0;
    // }
    public void selectCategory(int categoryIndex) {
        if (categoryIndex < 0 || categoryIndex >= ALL_CATEGORIES.length) {
            throw new IllegalArgumentException("Invalid category index");
        }
        if (categoriesUsed[categoryIndex]) {
            throw new IllegalStateException("Category already used");
        }
        currentCategory = ALL_CATEGORIES[categoryIndex];
        categoriesUsed[categoryIndex] = true;
        questionsWithCurrentCategory = 0;
    }

    
    // Get next question - called to get the current question to display
    // Question number is incremented in checkAnswer() after correct answer
    public Question getNextQuestion() {
        if (currentQuestionNumber >= TOTAL_QUESTIONS) {
            throw new IllegalStateException("All questions completed");
        }
        
        // Note: Category and helper switching checks happen AFTER answering questions
        // in checkAnswer(), not here. This method just gets the question to display.
        
        // Get question based on question number
        // Note: currentQuestionNumber will be 0 for first question, 1 for second, etc.
        // We need to get question for currentQuestionNumber + 1
        int questionToGet = currentQuestionNumber + 1;
        
        if (questionToGet == TOTAL_QUESTIONS) {
            // Final question - random category, no helper
            currentQuestion = questionBank.getFinalQuestion();
            currentCategory = currentQuestion.getCategory();
            currentHelper = null; // No helper for final question
        } else {
            // Regular question - use current category
            // Note: Category might be null if we need to select a new one
            // This should be checked by the controller before calling getNextQuestion()
            if (currentCategory == null) {
                throw new IllegalStateException("Category must be selected before getting question. Call needsCategorySelection() first.");
            }
            currentQuestion = questionBank.getQuestion(currentCategory);
        }
        
        // Reset question-specific state
        resetQuestionState();
        
        return currentQuestion;
    }
    
    private void resetQuestionState() {
        peekSuggestedIndex = -1;
        copySelectedIndex = -1;
        playerSelectedIndex = -1;
        answerLocked = false;
    }
    
    private void resetLifelines() {
        peekUsed = false;
        copyUsed = false;
        saveUsed = false;
    }
    
    // Lifeline: Peek
    public int usePeek() {
        if (peekUsed || currentHelper == null || currentQuestionNumber == TOTAL_QUESTIONS) {
            throw new IllegalStateException("Peek cannot be used");
        }
        peekUsed = true;
        peekSuggestedIndex = currentHelper.suggestAnswerIndex(
            currentQuestion.getOptions(),
            currentQuestion.getCorrectIndex()
        );
        return peekSuggestedIndex;
    }
    
    // Lifeline: Copy
    public int useCopy() {
        if (copyUsed || currentHelper == null || currentQuestionNumber == TOTAL_QUESTIONS) {
            throw new IllegalStateException("Copy cannot be used");
        }
        copyUsed = true;
        // Generate helper's answer once and store it
        copySelectedIndex = currentHelper.suggestAnswerIndex(
            currentQuestion.getOptions(),
            currentQuestion.getCorrectIndex()
        );
        answerLocked = true;
        playerSelectedIndex = copySelectedIndex;
        return copySelectedIndex;
    }
    
    // Lifeline: Save
    public void useSave() {
        if (saveUsed || currentHelper == null || currentQuestionNumber == TOTAL_QUESTIONS) {
            throw new IllegalStateException("Save cannot be used");
        }
        saveUsed = true;
    }
    
    // Player selects answer
    public void selectAnswer(int index) {
        if (answerLocked) {
            throw new IllegalStateException("Answer is locked (Copy lifeline used)");
        }
        playerSelectedIndex = index;
    }
    
    // Lock answer
    public boolean lockAnswer() {
        if (playerSelectedIndex == -1) {
            return false; // No answer selected
        }
        answerLocked = true;
        return true;
    }
    
    // Legacy method for backward compatibility - returns just boolean
    // Use checkAnswer() directly for full AnswerResult
    @Deprecated
    public boolean checkAnswerBoolean() {
        return checkAnswer().isCorrect();
    }
    
    // Check answer and process result
    // Returns: true if correct (or saved), false if wrong
    // Also returns whether Save was used and if it saved the player
    public AnswerResult checkAnswer() {
        if (!answerLocked || playerSelectedIndex == -1) {
            return new AnswerResult(false, false, false);
        }
        
        // Get the question number we're answering (currentQuestionNumber is 0-based for tracking)
        int questionNumber = currentQuestionNumber + 1;
        
        boolean isCorrect = currentQuestion.isCorrect(playerSelectedIndex);
        boolean saveWasUsed = saveUsed;
        boolean saveSavedPlayer = false;
        
        if (isCorrect) {
            // Correct answer - increment question number and update score
            currentQuestionNumber++;
            questionNumber = currentQuestionNumber; // Now it's 1-based for display
            
            // Update score based on question number (1-based)
            currentScore = PRIZE_LADDER[questionNumber - 1];
            
            // Increment helper/category counters (not for final question)
            if (questionNumber < TOTAL_QUESTIONS) {
                if (currentHelper != null) {
                    questionsWithCurrentHelper++;
                }
                if (currentCategory != null) {
                    questionsWithCurrentCategory++;
                }
            }
            
            // Check if we need to switch category AFTER answering (after 3 questions with current category)
            // Categories: Q1-3: Cat A, Q4-6: Cat B, Q7-9: Cat C, Q10: Cat D, Q11: Random
            if (questionsWithCurrentCategory >= CATEGORY_DURATION && questionNumber < TOTAL_QUESTIONS) {
                // Force category selection - will be handled by controller
                currentCategory = null;
                questionsWithCurrentCategory = 0;
            }
            
            // Check if we need to switch helper AFTER answering (after 2 questions with current helper)
            // Helpers switch after 2 questions, except Q11
            if (questionsWithCurrentHelper >= HELPER_DURATION && questionNumber < TOTAL_QUESTIONS) {
                currentHelper = null;
                questionsWithCurrentHelper = 0;
                resetLifelines();
            }
            
            // Check if game is won (answered all questions correctly)
            if (questionNumber == TOTAL_QUESTIONS) {
                gameWon = true;
                gameOver = true;
            }
            return new AnswerResult(true, saveWasUsed, false);
        } else {
            // Wrong answer - check Save lifeline
            if (saveUsed && currentHelper != null) {
                // Generate helper's answer to check if they were correct
                int helperAnswer = currentHelper.suggestAnswerIndex(
                    currentQuestion.getOptions(),
                    currentQuestion.getCorrectIndex()
                );
                if (currentQuestion.isCorrect(helperAnswer)) {
                    // Helper saved the player - treat as correct
                    saveSavedPlayer = true;
                    currentQuestionNumber++;
                    questionNumber = currentQuestionNumber;
                    currentScore = PRIZE_LADDER[questionNumber - 1];
                    
                    // Increment counters
                    if (questionNumber < TOTAL_QUESTIONS) {
                        if (currentHelper != null) {
                            questionsWithCurrentHelper++;
                        }
                        if (currentCategory != null) {
                            questionsWithCurrentCategory++;
                        }
                    }
                    
                    // Check if we need to switch category AFTER answering (after 3 questions)
                    if (questionsWithCurrentCategory >= CATEGORY_DURATION && questionNumber < TOTAL_QUESTIONS) {
                        currentCategory = null;
                        questionsWithCurrentCategory = 0;
                    }
                    
                    // Check if we need to switch helper AFTER answering (after 2 questions)
                    if (questionsWithCurrentHelper >= HELPER_DURATION && questionNumber < TOTAL_QUESTIONS) {
                        currentHelper = null;
                        questionsWithCurrentHelper = 0;
                        resetLifelines();
                    }
                    
                    if (questionNumber == TOTAL_QUESTIONS) {
                        gameWon = true;
                    }
                    return new AnswerResult(true, saveWasUsed, saveSavedPlayer);
                } else {
                    // Save was used but helper was wrong - game over
                    gameOver = true;
                    return new AnswerResult(false, saveWasUsed, false);
                }
            }
            // Game over - wrong answer and Save not used or Save failed
            // Question number stays the same (don't increment)
            gameOver = true;
            return new AnswerResult(false, saveWasUsed, false);
        }
    }
    
    // Drop out
    public void dropOut() {
        droppedOut = true;
        gameOver = true;
    }
    
    // Getters
    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }
    
    public int getTotalQuestions() {
        return TOTAL_QUESTIONS;
    }
    
    public int getCurrentScore() {
        return currentScore;
    }
    
    public Question getCurrentQuestion() {
        return currentQuestion;
    }
    
    public Helper getCurrentHelper() {
        return currentHelper;
    }
    
    public Category getCurrentCategory() {
        return currentCategory;
    }
    
    public Helper[] getAvailableHelpers() {
        List<Helper> available = new ArrayList<>();
        for (int i = 0; i < ALL_HELPERS.length; i++) {
            if (!helpersUsed[i]) {
                available.add(ALL_HELPERS[i]);
            }
        }
        return available.toArray(new Helper[0]);
    }

    
    public Category[] getAvailableCategories() {
        List<Category> available = new ArrayList<>();
        for (int i = 0; i < ALL_CATEGORIES.length; i++) {
            if (!categoriesUsed[i]) {
                available.add(ALL_CATEGORIES[i]);
            }
        }
        return available.toArray(new Category[0]);
    }
    public int[] getAvailableHelperIndices() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < ALL_HELPERS.length; i++) {
            if (!helpersUsed[i]) {
                indices.add(i);
            }
        }
        int[] result = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            result[i] = indices.get(i);
        }
        return result;
        }

    public int[] getAvailableCategoryIndices() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < ALL_CATEGORIES.length; i++) {
            if (!categoriesUsed[i]) {
                indices.add(i);
            }
        }
        int[] result = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            result[i] = indices.get(i);
        }
        return result;
    }


    
    public boolean needsHelperSelection() {
        return currentHelper == null && currentQuestionNumber < TOTAL_QUESTIONS;
    }
    
    public boolean needsCategorySelection() {
        return currentCategory == null && currentQuestionNumber < TOTAL_QUESTIONS;
    }
    
    public boolean isPeekUsed() {
        return peekUsed;
    }
    
    public boolean isCopyUsed() {
        return copyUsed;
    }
    
    public boolean isSaveUsed() {
        return saveUsed;
    }
    
    public int getPeekSuggestedIndex() {
        return peekSuggestedIndex;
    }
    
    public int getCopySelectedIndex() {
        return copySelectedIndex;
    }
    
    public int getPlayerSelectedIndex() {
        return playerSelectedIndex;
    }
    
    public boolean isAnswerLocked() {
        return answerLocked;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public boolean isGameWon() {
        return gameWon;
    }
    
    public boolean isDroppedOut() {
        return droppedOut;
    }
    
    public boolean canUseLifelines() {
        return currentHelper != null && currentQuestionNumber < TOTAL_QUESTIONS;
    }
}
