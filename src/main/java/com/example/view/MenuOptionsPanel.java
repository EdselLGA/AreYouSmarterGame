package com.example.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * MenuOptionsPanel - menu options with navigation
 * Buttons: Settings, How To Play, High Scores, Back to Main Menu
 * Uses NavigationListener to fire events (MVC compliant)
 */
public class MenuOptionsPanel extends JPanel {
    private NavigationListener navigationListener;
    private JButton settingsButton;
    private JButton howToPlayButton;
    private JButton highScoresButton;
    private JButton backButton;

    public MenuOptionsPanel() {
        initializePanel();
    }

    public void setNavigationListener(NavigationListener listener) {
        this.navigationListener = listener;
    }

    private void initializePanel() {
        setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Settings Button - Fire navigation event
        settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Arial", Font.BOLD, 20));
        settingsButton.setPreferredSize(new java.awt.Dimension(200, 50));
        settingsButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToSettings();
            }
        });
        add(settingsButton, gbc);

        // How To Play Button - Fire navigation event
        gbc.gridy = 1;
        howToPlayButton = new JButton("How To Play");
        howToPlayButton.setFont(new Font("Arial", Font.BOLD, 20));
        howToPlayButton.setPreferredSize(new java.awt.Dimension(200, 50));
        howToPlayButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToHowToPlay();
            }
        });
        add(howToPlayButton, gbc);

        // High Scores Button - Fire navigation event
        gbc.gridy = 2;
        highScoresButton = new JButton("High Scores");
        highScoresButton.setFont(new Font("Arial", Font.BOLD, 20));
        highScoresButton.setPreferredSize(new java.awt.Dimension(200, 50));
        highScoresButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToHighScores();
            }
        });
        add(highScoresButton, gbc);

        // Back Button - Fire navigation event
        gbc.gridy = 3;
        backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setPreferredSize(new java.awt.Dimension(200, 50));
        backButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToMainMenu();
            }
        });
        add(backButton, gbc);
    }
}
