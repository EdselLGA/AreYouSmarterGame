package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

    private static final String CHARLIE = "/charlie.png";
    private static final String BACKGROUND = "/ChooseSub_BG.png";
    
    private JPanel centerPanel;
    private JPanel middleContainer;
    Image bgImage;
    Image charlieImage;

    public CategoryPanel() {
        initializePanel();
    }

    public void setGameActionListener(GameActionListener listener) {
        this.gameActionListener = listener;
    }

    private void initializePanel() {
        setOpaque(false);
        setLayout(new BorderLayout());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(getClass().getResource(BACKGROUND));
        System.out.println(bgIc.getIconHeight());
        System.out.println("ARE YOU HERE");
        if (bgIc.getIconWidth() > 0) {
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);
        }

        // CHARLIE at 1.6x size
        ImageIcon charlieIcon = new ImageIcon(getClass().getResource(CHARLIE));
        charlieImage = charlieIcon.getImage().getScaledInstance(
                (int)(charlieIcon.getIconWidth() * 2),
                (int)(charlieIcon.getIconHeight() * 2),
                Image.SCALE_SMOOTH
        );

        // This panel centers the subject grid on the screen
        middleContainer = new JPanel(new GridBagLayout());
        middleContainer.setOpaque(false);

        middleContainer.setBorder(BorderFactory.createEmptyBorder(0, -160, -80, 0));

        // This panel contains the buttons (2×2)
        centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        categoryButtons = new JButton[4];
        
        

        // Add centerPanel into the middleContainer
        middleContainer.add(centerPanel);

        // Add middleContainer to the screen CENTER
        add(middleContainer, BorderLayout.CENTER);

        
        // BOTTOM — BACK BUTTON
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        bottom.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        add(bottom, BorderLayout.SOUTH);
    }

    public void updateCategories(List<Category> availableCategories) {
        // Remove existing buttons
        middleContainer.remove(centerPanel);
        centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        middleContainer.add(centerPanel);
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
            
            StringBuilder sb = new StringBuilder();
            sb.append("/category").append(i+1).append(".png");
            String result = sb.toString();
            JButton button = makeLargeButton(result);            

            
            final int categoryIndex = i;
            button.addActionListener(e -> {
                if (gameActionListener != null) {
                    gameActionListener.onCategorySelected(categoryIndex);
                }
            });
            
            categoryButtons[i] = button;
            centerPanel.add(button);
        }

        revalidate();
        repaint();
    }
    private JButton makeLargeButton(String path) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaled = icon.getImage().getScaledInstance(
                (int)(icon.getIconWidth() * 1.4),
                (int)(icon.getIconHeight() * 1.4),
                Image.SCALE_SMOOTH);

        JButton btn = new JButton(new ImageIcon(scaled));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);

        HelpersUI.addLightenOnHover(btn, 1.25f);
        return btn;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

        if (charlieImage != null) {
            int w = charlieImage.getWidth(null);
            int h = charlieImage.getHeight(null);

            g.drawImage(charlieImage,
                    getWidth() - w - 40,
                    getHeight() - h - 0,
                    this
            );
        }
    }
}
