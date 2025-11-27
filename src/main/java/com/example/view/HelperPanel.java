package com.example.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.example.model.Helper;

/**
 * HelperPanel - helper selection panel
 * Displays 5 helper buttons with accuracy percentages
 * Uses GameActionListener to fire events (MVC compliant)
 */
public class HelperPanel extends JPanel {
    private GameActionListener gameActionListener;
    private JLabel titleLabel;
    private JButton[] helperButtons;
    private static final int NUM_HELPERS = 5;

    public HelperPanel() {
        initializePanel();
    }

    public void setGameActionListener(GameActionListener listener) {
        this.gameActionListener = listener;
    }

    private void initializePanel() {
        setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Title Label
        titleLabel = new JLabel("SELECT A HELPER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, gbc);

        // Initialize helper buttons array
        helperButtons = new JButton[NUM_HELPERS];
    }

    public void updateHelpers(List<Helper> availableHelpers) {
        // Remove existing buttons
        if (helperButtons != null) {
            for (JButton button : helperButtons) {
                if (button != null) {
                    remove(button);
                }
            }
        }

        // Reinitialize array if needed
        if (helperButtons == null || helperButtons.length < availableHelpers.size()) {
            helperButtons = new JButton[availableHelpers.size()];
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;

        // Create buttons for available helpers
        for (int i = 0; i < availableHelpers.size(); i++) {
            Helper helper = availableHelpers.get(i);
            JButton button = new JButton(
                String.format("%s (%d%%)", helper.getName(), helper.getAccuracyPercentage())
            );
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setPreferredSize(new java.awt.Dimension(300, 60));
            button.setBackground(Color.LIGHT_GRAY);
            button.setFocusPainted(false);
            
            final int helperIndex = i;
            button.addActionListener(e -> {
                if (gameActionListener != null) {
                    gameActionListener.onHelperSelected(helperIndex);
                }
            });
            
            helperButtons[i] = button;
            add(button, gbc);
            gbc.gridy++;
        }

        revalidate();
        repaint();
    }

}
