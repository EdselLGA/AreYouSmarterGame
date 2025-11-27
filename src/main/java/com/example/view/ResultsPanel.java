package com.example.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ResultsPanel - results display panel
 * Shows final score and game outcome
 * Uses GameNavigationListener to fire events (MVC compliant)
 */
public class ResultsPanel extends JPanel {
    private GameNavigationListener gameNavigationListener;
    private JLabel titleLabel;
    private JLabel scoreLabel;
    private JLabel messageLabel;
    private JButton restartButton;

    public ResultsPanel() {
        initializePanel();
    }

    public void setGameNavigationListener(GameNavigationListener listener) {
        this.gameNavigationListener = listener;
    }

    private void initializePanel() {
        setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Title Label
        titleLabel = new JLabel("GAME OVER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, gbc);

        // Score Label
        gbc.gridy = 1;
        scoreLabel = new JLabel("Final Score: $0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.YELLOW);
        add(scoreLabel, gbc);

        // Message Label
        gbc.gridy = 2;
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        messageLabel.setForeground(Color.WHITE);
        add(messageLabel, gbc);

        // Restart Button
        gbc.gridy = 3;
        restartButton = new JButton("Play Again");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setPreferredSize(new java.awt.Dimension(200, 50));
        restartButton.addActionListener(e -> {
            if (gameNavigationListener != null) {
                gameNavigationListener.onNavigateToHelper();
            }
        });
        add(restartButton, gbc);
        
        // Back to Main Menu Button
        gbc.gridy = 4;
        JButton mainMenuButton = new JButton("Back to Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 20));
        mainMenuButton.setPreferredSize(new java.awt.Dimension(200, 50));
        mainMenuButton.setBackground(Color.BLUE);
        mainMenuButton.setForeground(Color.WHITE);
        mainMenuButton.addActionListener(e -> {
            if (gameNavigationListener != null) {
                gameNavigationListener.onNavigateToMainMenu();
            }
        });
        add(mainMenuButton, gbc);
    }

    public void updateResults(int finalScore, boolean gameWon, boolean droppedOut, int questionNumber) {
        scoreLabel.setText(String.format("Final Score: $%,d", finalScore));
        
        if (gameWon) {
            titleLabel.setText("YOU WIN!");
            titleLabel.setForeground(Color.GREEN);
            messageLabel.setText("Congratulations! You answered all questions correctly!");
        } else if (droppedOut) {
            titleLabel.setText("YOU DROPPED OUT");
            titleLabel.setForeground(Color.ORANGE);
            messageLabel.setText(String.format("You dropped out at question %d and took home $%,d!", 
                                             questionNumber, finalScore));
        } else {
            titleLabel.setText("GAME OVER");
            titleLabel.setForeground(Color.RED);
            messageLabel.setText(String.format("You answered incorrectly at question %d.", questionNumber));
        }
        
        revalidate();
        repaint();
    }
}
