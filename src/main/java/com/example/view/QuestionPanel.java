package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.example.model.Category;
import com.example.model.Helper;
import com.example.model.Question;

/**
 * QuestionPanel - question display panel with full game UI
 * Displays question, options, score, category, helper, lifelines, and action buttons
 * Uses GameActionListener to fire events (MVC compliant)
 */
public class QuestionPanel extends JPanel {
    private GameActionListener gameActionListener;
    
    // UI Components
    private JLabel questionNumberLabel;
    private JLabel questionTextLabel;
    private JButton[] optionButtons;
    private JLabel scoreLabel;
    private JLabel categoryLabel;
    private JLabel helperLabel;
    private JButton peekButton;
    private JButton copyButton;
    private JButton saveButton;
    private JButton dropOutButton;
    private JButton lockAnswerButton;
    private JLabel resultLabel;
    
    private int selectedAnswerIndex = -1;
    private static final int NUM_OPTIONS = 4;

    public QuestionPanel() {
        initializePanel();
    }

    public void setGameActionListener(GameActionListener listener) {
        this.gameActionListener = listener;
    }

    private void initializePanel() {
        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());
        
        // Top panel - Question info
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel - Question and options
        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel - Lifelines and actions
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        
        // Question number
        gbc.gridx = 0;
        gbc.gridy = 0;
        questionNumberLabel = new JLabel("Question 1 of 11");
        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionNumberLabel.setForeground(Color.WHITE);
        panel.add(questionNumberLabel, gbc);
        
        // Score
        gbc.gridx = 1;
        scoreLabel = new JLabel("Score: $0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(Color.YELLOW);
        panel.add(scoreLabel, gbc);
        
        // Category
        gbc.gridx = 0;
        gbc.gridy = 1;
        categoryLabel = new JLabel("Category: -");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryLabel.setForeground(Color.WHITE);
        panel.add(categoryLabel, gbc);
        
        // Helper
        gbc.gridx = 1;
        helperLabel = new JLabel("Helper: None");
        helperLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        helperLabel.setForeground(Color.WHITE);
        panel.add(helperLabel, gbc);
        
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Question text
        questionTextLabel = new JLabel("<html><div style='text-align: center; width: 600px;'>Question text will appear here</div></html>");
        questionTextLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        questionTextLabel.setForeground(Color.WHITE);
        questionTextLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(questionTextLabel, gbc);
        
        // Option buttons
        optionButtons = new JButton[NUM_OPTIONS];
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        
        for (int i = 0; i < NUM_OPTIONS; i++) {
            JButton button = new JButton("Option " + (i + 1));
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setPreferredSize(new java.awt.Dimension(400, 60));
            button.setBackground(Color.LIGHT_GRAY);
            button.setFocusPainted(false);
            
            final int optionIndex = i;
            button.addActionListener(e -> {
                // Deselect previous
                if (selectedAnswerIndex >= 0 && selectedAnswerIndex < optionButtons.length) {
                    optionButtons[selectedAnswerIndex].setBackground(Color.LIGHT_GRAY);
                }
                // Select new
                selectedAnswerIndex = optionIndex;
                button.setBackground(Color.CYAN);
                if (gameActionListener != null) {
                    gameActionListener.onAnswerSelected(optionIndex);
                }
            });
            
            optionButtons[i] = button;
            panel.add(button, gbc);
            gbc.gridy++;
        }
        
        // Result label (hidden initially)
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        resultLabel.setVisible(false);
        panel.add(resultLabel, gbc);
        
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Lifeline buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        peekButton = new JButton("Peek");
        peekButton.setFont(new Font("Arial", Font.BOLD, 14));
        peekButton.setPreferredSize(new java.awt.Dimension(100, 40));
        peekButton.addActionListener(e -> {
            if (gameActionListener != null) {
                gameActionListener.onPeekRequested();
            }
        });
        panel.add(peekButton, gbc);
        
        gbc.gridx = 1;
        copyButton = new JButton("Copy");
        copyButton.setFont(new Font("Arial", Font.BOLD, 14));
        copyButton.setPreferredSize(new java.awt.Dimension(100, 40));
        copyButton.addActionListener(e -> {
            if (gameActionListener != null) {
                gameActionListener.onCopyRequested();
            }
        });
        panel.add(copyButton, gbc);
        
        gbc.gridx = 2;
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setPreferredSize(new java.awt.Dimension(100, 40));
        saveButton.addActionListener(e -> {
            if (gameActionListener != null) {
                gameActionListener.onSaveRequested();
            }
        });
        panel.add(saveButton, gbc);
        
        // Action buttons
        gbc.gridx = 3;
        dropOutButton = new JButton("Drop Out");
        dropOutButton.setFont(new Font("Arial", Font.BOLD, 14));
        dropOutButton.setPreferredSize(new java.awt.Dimension(120, 40));
        dropOutButton.setBackground(Color.ORANGE);
        dropOutButton.addActionListener(e -> {
            if (gameActionListener != null) {
                gameActionListener.onDropOut();
            }
        });
        panel.add(dropOutButton, gbc);
        
        gbc.gridx = 4;
        lockAnswerButton = new JButton("Lock Answer");
        lockAnswerButton.setFont(new Font("Arial", Font.BOLD, 16));
        lockAnswerButton.setPreferredSize(new java.awt.Dimension(150, 50));
        lockAnswerButton.setBackground(Color.GREEN);
        lockAnswerButton.addActionListener(e -> {
            if (gameActionListener != null) {
                gameActionListener.onLockAnswer();
            }
        });
        panel.add(lockAnswerButton, gbc);
        
        return panel;
    }

    public void updateQuestion(int questionNumber, int totalQuestions, int score, 
                              Question question, Category category, Helper helper,
                              boolean canUseLifelines, boolean peekUsed, 
                              boolean copyUsed, boolean saveUsed, boolean answerLocked) {
        // Update question number and score
        questionNumberLabel.setText(String.format("Question %d of %d", questionNumber, totalQuestions));
        scoreLabel.setText(String.format("Score: $%,d", score));
        
        // Update category and helper
        if (category != null) {
            categoryLabel.setText("Category: " + category.getDisplayName());
        } else {
            categoryLabel.setText("Category: -");
        }
        
        if (helper != null && questionNumber < totalQuestions) {
            helperLabel.setText(String.format("Helper: %s (%d%%)", helper.getName(), getHelperAccuracy(helper)));
        } else {
            helperLabel.setText("Helper: None");
        }
        
        // Update question text
        questionTextLabel.setText("<html><div style='text-align: center; width: 600px;'>" + 
                                 question.getQuestionText() + "</div></html>");
        
        // Update option buttons
        String[] options = question.getOptions();
        for (int i = 0; i < Math.min(options.length, optionButtons.length); i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setEnabled(!answerLocked);
            if (answerLocked && i == selectedAnswerIndex) {
                optionButtons[i].setBackground(Color.CYAN);
            } else if (!answerLocked) {
                optionButtons[i].setBackground(Color.LIGHT_GRAY);
            }
        }
        
        // Update lifeline buttons
        boolean lifelinesEnabled = canUseLifelines && !answerLocked;
        peekButton.setEnabled(lifelinesEnabled && !peekUsed);
        copyButton.setEnabled(lifelinesEnabled && !copyUsed);
        saveButton.setEnabled(lifelinesEnabled && !saveUsed);
        
        // Update lock answer button
        lockAnswerButton.setEnabled(!answerLocked && selectedAnswerIndex >= 0);
        
        // Hide result label
        resultLabel.setVisible(false);
        selectedAnswerIndex = -1;
        
        revalidate();
        repaint();
    }

    public void updateAnswerSelection(int index) {
        // Deselect previous
        if (selectedAnswerIndex >= 0 && selectedAnswerIndex < optionButtons.length) {
            optionButtons[selectedAnswerIndex].setBackground(Color.LIGHT_GRAY);
        }
        // Select new
        selectedAnswerIndex = index;
        if (index >= 0 && index < optionButtons.length) {
            optionButtons[index].setBackground(Color.CYAN);
            lockAnswerButton.setEnabled(true);
        }
    }

    public void showPeekSuggestion(int suggestedIndex) {
        if (suggestedIndex >= 0 && suggestedIndex < optionButtons.length) {
            optionButtons[suggestedIndex].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            // Remove border after 3 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if (suggestedIndex < optionButtons.length) {
                    optionButtons[suggestedIndex].setBorder(null);
                }
            }).start();
        }
    }

    public void showCopySelection(int selectedIndex) {
        updateAnswerSelection(selectedIndex);
        // Disable all option buttons
        for (JButton button : optionButtons) {
            button.setEnabled(false);
        }
    }

    public void updateSaveLifeline(boolean used) {
        saveButton.setEnabled(false);
        if (used) {
            saveButton.setText("Save (Used)");
        }
    }

    public void showAnswerResult(boolean isCorrect, boolean saveWasUsed, boolean saveSavedPlayer) {
        resultLabel.setVisible(true);
        if (isCorrect) {
            if (saveWasUsed && saveSavedPlayer) {
                resultLabel.setText("WRONG ANSWER - SAVED BY HELPER!");
                resultLabel.setForeground(Color.ORANGE);
            } else {
                resultLabel.setText("CORRECT!");
                resultLabel.setForeground(Color.GREEN);
            }
        } else {
            if (saveWasUsed && !saveSavedPlayer) {
                resultLabel.setText("WRONG ANSWER - HELPER WAS WRONG - GAME OVER");
                resultLabel.setForeground(Color.RED);
            } else {
                resultLabel.setText("WRONG ANSWER - GAME OVER");
                resultLabel.setForeground(Color.RED);
            }
        }
        revalidate();
        repaint();
    }

    private int getHelperAccuracy(Helper helper) {
        return helper.getAccuracyPercentage();
    }
}
