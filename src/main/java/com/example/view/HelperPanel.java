package com.example.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.ImageIcon;
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
    private Image bgImage;

    private static final String BACKGROUND = "/splash.png";
    private static final String BTN_H1 = "/helper1.png";
    private static final String BTN_H2 = "/helper2.png";
    private static final String BTN_H3 = "/helper3.png";
    private static final String BTN_H4 = "/helper4.png";
    private static final String BTN_H5 = "/helper5.png";

    private JPanel buttonPanel;

    public HelperPanel() {
        initializePanel();
    }

    public void setGameActionListener(GameActionListener listener) {
        this.gameActionListener = listener;
    }

    private void initializePanel() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(getClass().getResource(BACKGROUND));
        if (bgIc.getIconWidth() > 0) {
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );
        }


        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);
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
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        add(buttonPanel);
    }

    public void updateHelpers(List<Helper> availableHelpers) {
        // Remove existing buttons
        remove(buttonPanel);
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        add(buttonPanel);
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
            StringBuilder sb = new StringBuilder();
            sb.append("/helper").append(i+1).append(".png");
            String result = sb.toString();
            JButton button = makeImageButton(result);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(Color.LIGHT_GRAY);
            button.setFocusPainted(false);
            HelpersUI.addHoverSFX(button, "/Hover.wav");
            HelpersUI.addLightenOnHover(button, 1.25f);

            final int helperIndex = i;
            button.addActionListener(e -> {
                if (gameActionListener != null) {
                    gameActionListener.onHelperSelected(helperIndex);
                }
            });
            
            helperButtons[i] = button;
            buttonPanel.add(button);
        }

        revalidate();
        repaint();
    }

    private JButton makeImageButton(String imagePath) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon rawIcon = new ImageIcon(getClass().getResource(imagePath));

        int newWidth = (int) (screen.width * 0.10);
        int newHeight = (int) ((double) rawIcon.getIconHeight() /
                rawIcon.getIconWidth() * newWidth);

        Image scaledImg = rawIcon.getImage().getScaledInstance(
                newWidth, newHeight, Image.SCALE_SMOOTH
        );

        JButton btn = new JButton(new ImageIcon(scaledImg));

        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);

        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(new Dimension(newWidth, newHeight));

        return btn;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}
