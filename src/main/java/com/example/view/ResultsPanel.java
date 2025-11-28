package com.example.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
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
    private JButton mainMenuButton;

    private static final String BG = "/QuestionsBG.png";
    private Image bgImage;

    public ResultsPanel() {
        initializePanel();
    }

    public void setGameNavigationListener(GameNavigationListener listener) {
        this.gameNavigationListener = listener;
    }

    private void initializePanel() {
        loadImages();
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across both columns for centered content

        // Add top vertical glue to push text to center
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(new JPanel() {{ setOpaque(false); }}, gbc);

        // Title Label
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        titleLabel = new JLabel("GAME OVER", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, gbc);

        // Score Label
        gbc.gridy = 2;
        scoreLabel = new JLabel("Final Score: $0", JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        scoreLabel.setForeground(Color.YELLOW);
        add(scoreLabel, gbc);

        // Message Label
        gbc.gridy = 3;
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        messageLabel.setForeground(Color.WHITE);
        add(messageLabel, gbc);

        // Add bottom vertical glue to push buttons to bottom
        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(new JPanel() {{ setOpaque(false); }}, gbc);

        // Reset constraints for buttons
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(10, 20, 20, 10);

        // Back to Main Menu Button (Bottom Left)
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 18));
        mainMenuButton.setPreferredSize(new java.awt.Dimension(200, 50));
        mainMenuButton.setBackground(new Color(41, 128, 185)); // Modern blue
        mainMenuButton.setForeground(Color.WHITE);
        mainMenuButton.setFocusPainted(false);
        mainMenuButton.setBorderPainted(false);
        mainMenuButton.setOpaque(true);
        mainMenuButton.addActionListener(e -> {
            if (gameNavigationListener != null) {
                gameNavigationListener.onNavigateToMainMenu();
            }
        });
        add(mainMenuButton, gbc);

        // Restart Button (Bottom Right)
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(10, 10, 20, 20);
        restartButton = new JButton("Play Again");
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.setPreferredSize(new java.awt.Dimension(200, 50));
        restartButton.setBackground(new Color(46, 204, 113)); // Modern green
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.setBorderPainted(false);
        restartButton.setOpaque(true);
        restartButton.addActionListener(e -> {
            if (gameNavigationListener != null) {
                gameNavigationListener.onNavigateToHelper();
            }
        });
        add(restartButton, gbc);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.bgImage != null)
            g.drawImage(this.bgImage, 0, 0, getWidth(), getHeight(), this);
    }
    
    private void loadImages() {
        this.bgImage = new ImageIcon(getClass().getResource(BG)).getImage();
    }
}