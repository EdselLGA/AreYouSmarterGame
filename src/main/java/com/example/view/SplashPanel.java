package com.example.view;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * SplashPanel - displays splash screen
 * Navigates to MainMenuPanel on any mouse or keyboard click
 * Uses NavigationListener to fire events (MVC compliant)
 */
public class SplashPanel extends JPanel {
    private NavigationListener navigationListener;

    public SplashPanel() {
        initializePanel();
    }

    public void setNavigationListener(NavigationListener listener) {
        this.navigationListener = listener;
    }

    private void initializePanel() {
        setBackground(Color.BLACK);
        setLayout(null);
        
        // Add label for splash screen
        JLabel splashLabel = new JLabel("GAME SPLASH SCREEN");
        splashLabel.setForeground(Color.WHITE);
        splashLabel.setHorizontalAlignment(JLabel.CENTER);
        splashLabel.setBounds(0, 0, 800, 600);
        add(splashLabel);

        // Mouse click listener - fire navigation event
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (navigationListener != null) {
                    navigationListener.onNavigateToMainMenu();
                }
            }
        });

        // Keyboard listener - fire navigation event on any key press
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (navigationListener != null) {
                    navigationListener.onNavigateToMainMenu();
                }
            }
        });
    }
}
