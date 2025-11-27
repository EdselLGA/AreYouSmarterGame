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
 * HighScoresPanel - high scores display screen
 * Button: Back to Menu Options
 * Uses NavigationListener to fire events (MVC compliant)
 */
public class HighScoresPanel extends JPanel {
    private NavigationListener navigationListener;
    private JButton backButton;

    public HighScoresPanel() {
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

        // High Scores Label
        JLabel highScoresLabel = new JLabel("HIGH SCORES");
        highScoresLabel.setFont(new Font("Arial", Font.BOLD, 30));
        highScoresLabel.setForeground(Color.WHITE);
        add(highScoresLabel, gbc);

        // Back Button - Fire navigation event
        gbc.gridy = 1;
        backButton = new JButton("Back to Menu Options");
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setPreferredSize(new java.awt.Dimension(200, 50));
        backButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToMenuOptions();
            }
        });
        add(backButton, gbc);
    }
}
