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

import com.example.model.Category;

/**
 * CategoryPanel - category selection panel
 * Displays 4 category buttons
 * Uses GameActionListener to fire events (MVC compliant)
 */
public class CategoryPanel extends JPanel {
    private GameActionListener gameActionListener;
    private JLabel titleLabel;
    private JButton[] categoryButtons;

    public CategoryPanel() {
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
        titleLabel = new JLabel("SELECT A CATEGORY");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, gbc);
    }

    public void updateCategories(List<Category> availableCategories) {
        // Remove existing buttons
        if (categoryButtons != null) {
            for (JButton button : categoryButtons) {
                if (button != null) {
                    remove(button);
                }
            }
        }

        categoryButtons = new JButton[availableCategories.size()];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;

        // Create buttons for available categories
        for (int i = 0; i < availableCategories.size(); i++) {
            Category category = availableCategories.get(i);
            JButton button = new JButton(category.getDisplayName());
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setPreferredSize(new java.awt.Dimension(300, 60));
            button.setBackground(Color.LIGHT_GRAY);
            
            final int categoryIndex = i;
            button.addActionListener(e -> {
                if (gameActionListener != null) {
                    gameActionListener.onCategorySelected(categoryIndex);
                }
            });
            
            categoryButtons[i] = button;
            add(button, gbc);
            gbc.gridy++;
        }

        revalidate();
        repaint();
    }
}
