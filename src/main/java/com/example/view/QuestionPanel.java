package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

    private Image bgImage;
    private Image containerImg;
    
    private static final String BG = "/QuestionsBG.png";
    private static final String CONTAINER = "/QuestionsbuttonContainer.png";

    private int selectedAnswerIndex = -1;
    private static final int NUM_OPTIONS = 4;

    private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    public QuestionPanel() {
        initializePanel();
    }

    public void setGameActionListener(GameActionListener listener) {
        this.gameActionListener = listener;
    }

    private void initializePanel() {
        setBackground(Color.DARK_GRAY);
        setOpaque(false);
        loadImages();
        revalidate();
        repaint();
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
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0)); // More top padding to push into white box
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); // More spacing
        
        // Question number
        gbc.gridx = 0;
        gbc.gridy = 0;
        questionNumberLabel = new JLabel("Question 1 of 11");
        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 26));
        questionNumberLabel.setForeground(Color.YELLOW);
        questionNumberLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black outline
        questionNumberLabel.setOpaque(true);
        questionNumberLabel.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent black background
        panel.add(questionNumberLabel, gbc);
        
        // Score
        gbc.gridx = 1;
        scoreLabel = new JLabel("Score: $0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 26));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black outline
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent black background
        panel.add(scoreLabel, gbc);
        
        // Category
        gbc.gridx = 0;
        gbc.gridy = 1;
        categoryLabel = new JLabel("Category: -");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 22));
        categoryLabel.setForeground(Color.YELLOW);
        categoryLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black outline
        categoryLabel.setOpaque(true);
        categoryLabel.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent black background
        panel.add(categoryLabel, gbc);
        
        // Helper
        gbc.gridx = 1;
        helperLabel = new JLabel("Helper: None");
        helperLabel.setFont(new Font("Arial", Font.BOLD, 22));
        helperLabel.setForeground(Color.YELLOW);
        helperLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black outline
        helperLabel.setOpaque(true);
        helperLabel.setBackground(new Color(0, 0, 0, 100)); // Semi-transparent black background
        panel.add(helperLabel, gbc);
        
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 20); // No vertical spacing
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Question text - LARGER, BLACK, and CENTERED - VERY HIGH
        questionTextLabel = new JLabel("<html><div style='text-align: center; width: 900px;'>Question text will appear here</div></html>");
        questionTextLabel.setFont(new Font("Arial", Font.BOLD, 28));
        questionTextLabel.setForeground(Color.BLACK);
        questionTextLabel.setHorizontalAlignment(JLabel.CENTER);
        questionTextLabel.setBorder(BorderFactory.createEmptyBorder(5, 20, 25, 20)); // Minimal top padding
        panel.add(questionTextLabel, gbc);
        
        // Option buttons in 2x2 grid - PROPERLY SIZED and CENTERED
        optionButtons = new JButton[NUM_OPTIONS];
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(8, 25, 8, 25); // Tight spacing
        
        for (int i = 0; i < NUM_OPTIONS; i++) {
            JButton button = new JButton("Option " + (i + 1));
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setPreferredSize(new java.awt.Dimension(350, 55)); // Better size for text
            button.setMinimumSize(new java.awt.Dimension(350, 55));
            button.setOpaque(true); // Make opaque so background shows
            button.setContentAreaFilled(true);
            button.setBorderPainted(true);
            button.setBackground(new Color(240, 240, 240)); // Light gray background
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Clear border
            
            final int optionIndex = i;
            button.addActionListener(e -> {
                // Deselect previous
                if (selectedAnswerIndex >= 0 && selectedAnswerIndex < optionButtons.length) {
                    optionButtons[selectedAnswerIndex].setBackground(new Color(240, 240, 240));
                }
                // Select new
                selectedAnswerIndex = optionIndex;
                button.setBackground(new Color(100, 200, 255)); // Nice blue selection
                if (gameActionListener != null) {
                    gameActionListener.onAnswerSelected(optionIndex);
                }
            });
            
            optionButtons[i] = button;
            
            // Arrange in 2x2 grid
            gbc.gridx = i % 2; // Column 0 or 1
            gbc.gridy = 1 + (i / 2); // Row 1 or 2
            
            panel.add(button, gbc);
        }
        
        // Result label (hidden initially)
        gbc.gridx = 0;
        gbc.gridy = 3; // After the 2x2 button grid
        gbc.gridwidth = 2; // Span across both columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
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
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Lifeline buttons - BIGGER and PRETTIER
        gbc.gridx = 0;
        gbc.gridy = 0;
        peekButton = new JButton("Peek");
        peekButton.setFont(new Font("Arial", Font.BOLD, 18));
        peekButton.setPreferredSize(new java.awt.Dimension(140, 60));
        peekButton.setBackground(new Color(173, 216, 230)); // Light blue
        peekButton.setForeground(Color.BLACK);
        peekButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        peekButton.setFocusPainted(false);
        peekButton.addActionListener(e -> {
            if (gameActionListener != null) {
                gameActionListener.onPeekRequested();
            }
        });
        panel.add(peekButton, gbc);
        
        gbc.gridx = 1;
        copyButton = new JButton("Copy");
        copyButton.setFont(new Font("Arial", Font.BOLD, 18));
        copyButton.setPreferredSize(new java.awt.Dimension(140, 60));
        copyButton.setBackground(new Color(173, 216, 230)); // Light blue
        copyButton.setForeground(Color.BLACK);
        copyButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        copyButton.setFocusPainted(false);
        copyButton.addActionListener(e -> {
            if (gameActionListener != null) {
                gameActionListener.onCopyRequested();
            }
        });
        panel.add(copyButton, gbc);
        
        gbc.gridx = 2;
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.setPreferredSize(new java.awt.Dimension(140, 60));
        saveButton.setBackground(new Color(173, 216, 230)); // Light blue
        saveButton.setForeground(Color.BLACK);
        saveButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> {
            if (gameActionListener != null) {
                gameActionListener.onSaveRequested();
            }
        });
        panel.add(saveButton, gbc);
        
        // Action buttons - BIGGER and PRETTIER
        gbc.gridx = 3;
        dropOutButton = new JButton("Drop Out");
        dropOutButton.setFont(new Font("Arial", Font.BOLD, 18));
        dropOutButton.setPreferredSize(new java.awt.Dimension(160, 60));
        dropOutButton.setBackground(new Color(255, 165, 0)); // Orange
        dropOutButton.setForeground(Color.BLACK);
        dropOutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        dropOutButton.setFocusPainted(false);
        dropOutButton.addActionListener(e -> {
            if (gameActionListener != null) {
                gameActionListener.onDropOut();
            }
        });
        panel.add(dropOutButton, gbc);
        
        gbc.gridx = 4;
        lockAnswerButton = new JButton("Lock Answer");
        lockAnswerButton.setFont(new Font("Arial", Font.BOLD, 20));
        lockAnswerButton.setPreferredSize(new java.awt.Dimension(200, 65));
        lockAnswerButton.setBackground(new Color(0, 255, 0)); // Bright green
        lockAnswerButton.setForeground(Color.BLACK);
        lockAnswerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        lockAnswerButton.setFocusPainted(false);
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
        questionTextLabel.setText("<html><div style='text-align: center; width: 900px;'>" + 
                                 question.getQuestionText() + "</div></html>");
        
        // Update option buttons
        String[] options = question.getOptions();
        for (int i = 0; i < Math.min(options.length, optionButtons.length); i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            optionButtons[i].setEnabled(!answerLocked);
            if (answerLocked && i == selectedAnswerIndex) {
                optionButtons[i].setBackground(new Color(100, 200, 255));
            } else if (!answerLocked) {
                optionButtons[i].setBackground(new Color(240, 240, 240));
            }
        }
        if(options[0]=="True" && options[1]=="False"){
            optionButtons[2].setText("");
            optionButtons[3].setText("");
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
            optionButtons[selectedAnswerIndex].setBackground(new Color(240, 240, 240));
        }
        // Select new
        selectedAnswerIndex = index;
        if (index >= 0 && index < optionButtons.length) {
            optionButtons[index].setBackground(new Color(100, 200, 255));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.bgImage != null)
            g.drawImage(this.bgImage, 0, 0, getWidth(), getHeight(), this);

        g.drawImage(this.containerImg, 40, 20, 1350, 700, this);
    }
    private void loadImages() {
        this.bgImage = new ImageIcon(getClass().getResource(BG)).getImage();
        this.containerImg = new ImageIcon(getClass().getResource(CONTAINER)).getImage();
    }
}