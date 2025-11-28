package com.example.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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

    private static final String BACKGROUND = "assets/background_2.png";
    private static final String BTN_HOWTOPLAY = "assets/HOW TO PLAY.png";
    private static final String BTN_HIGHSCORES = "assets/HIGHSCORES.png";
    private static final String BTN_SETTINGS = "assets/SETTINGS.png";
    private static final String BTN_BACK = "assets/BACK.png";

    private Image bgImage;

    public MenuOptionsPanel() {
        initializePanel();
    }

    public void setNavigationListener(NavigationListener listener) {
        this.navigationListener = listener;
    }

    private void initializePanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );

        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JButton settingsButton = makeImageButton(BTN_HOWTOPLAY);
        JButton highScoresButton = makeImageButton(BTN_HIGHSCORES);
        JButton howToPlayButton = makeImageButton(BTN_SETTINGS);
        JButton backButton = makeImageButton(BTN_BACK);

        //lighten hover
        HelpersUI.addLightenOnHover(settingsButton, 1.25f);
        HelpersUI.addLightenOnHover(highScoresButton, 1.25f);
        HelpersUI.addLightenOnHover(settingsButton, 1.25f);
        HelpersUI.addLightenOnHover(backButton, 1.25f);

        //hover sfx (delayed)
        HelpersUI.addHoverSFX(settingsButton, "assets/Hover.wav");
        HelpersUI.addHoverSFX(highScoresButton, "assets/Hover.wav");
        HelpersUI.addHoverSFX(settingsButton, "assets/Hover.wav");
        HelpersUI.addHoverSFX(backButton, "assets/Hover.wav");

        settingsButton.addActionListener(e -> {
            Sound.playSFX("assets/clicked.wav");
            if (navigationListener != null) {
                navigationListener.onNavigateToSettings();
            }
            // HelpersUI.fadeInComponent(howToPlay, 18, 0.06f, null);
        });

        highScoresButton.addActionListener(e -> {
            Sound.playSFX("assets/clicked.wav");
            if (navigationListener != null) {
                navigationListener.onNavigateToHighScores();
            }
            // HelpersUI.fadeInComponent(highScores, 18, 0.06f, null);
        });

        settingsButton.addActionListener(e -> {
            Sound.playSFX("assets/clicked.wav");
            if (navigationListener != null) {
                navigationListener.onNavigateToSettings();
            }
        });

        backButton.addActionListener(e -> {
            //HelpersUI.fadeInComponent(main, 18, 0.06f, null);
            Sound.playSFX("assets/clicked.wav");
            if (navigationListener != null) {
                navigationListener.onNavigateToMainMenu();
            }
        });

        box.add(settingsButton);
        box.add(Box.createRigidArea(new Dimension(0, 20)));
        box.add(highScoresButton);
        box.add(Box.createRigidArea(new Dimension(0, 20)));
        box.add(settingsButton);
        box.add(Box.createRigidArea(new Dimension(0, 20)));
        box.add(backButton);
        box.add(Box.createRigidArea(new Dimension(0, 20)));

        add(box);
    }
    
    private JButton makeImageButton(String imagePath) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon rawIcon = new ImageIcon(imagePath);

        int newWidth = (int) (screen.width * 0.20);
        int newHeight = (int) ((double) rawIcon.getIconHeight() /
                rawIcon.getIconWidth() * newWidth);

        Image scaled = rawIcon.getImage().getScaledInstance(
                newWidth, newHeight, Image.SCALE_SMOOTH
        );

        ImageIcon scaledIcon = new ImageIcon(scaled);

        JButton btn = new JButton(scaledIcon);
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

/*
        settingsButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToSettings();
            }
        });
        howToPlayButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToHowToPlay();
            }
        });
        highScoresButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToHighScores();
            }
        });
        backButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToMainMenu();
            }
        });
*/