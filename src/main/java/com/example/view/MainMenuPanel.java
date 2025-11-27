package com.example.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * MainMenuPanel - main menu with navigation options
 * Buttons: Game, Menu Options, Exit
 * Uses NavigationListener to fire events (MVC compliant)
 */
public class MainMenuPanel extends JPanel {
    private NavigationListener navigationListener;
    private JButton gameButton;
    private JButton menuOptionsButton;
    private JButton exitButton;

    public MainMenuPanel() {
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

        // Game Button - Fire navigation event
        gameButton = new JButton("Start Game");
        gameButton.setFont(new Font("Arial", Font.BOLD, 20));
        gameButton.setPreferredSize(new java.awt.Dimension(200, 50));
        gameButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToGame();
            }
        });
        add(gameButton, gbc);

        // Menu Options Button - Fire navigation event
        gbc.gridy = 1;
        menuOptionsButton = new JButton("Menu Options");
        menuOptionsButton.setFont(new Font("Arial", Font.BOLD, 20));
        menuOptionsButton.setPreferredSize(new java.awt.Dimension(200, 50));
        menuOptionsButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToMenuOptions();
            }
        });
        add(menuOptionsButton, gbc);

        // Exit Button - Fire exit event
        gbc.gridy = 2;
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setPreferredSize(new java.awt.Dimension(200, 50));
        exitButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onExitApplication();
            }
        });
        add(exitButton, gbc);
    }
}
