package com.example.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;

import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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


    private static final String BACKGROUND = "/splash.png";
    private static final String BTN_START = "/START.png";
    private static final String BTN_MENU = "/MENU.png";
    private static final String BTN_EXIT = "/EXIT.png";
    private static final String CLOUD = "/Cloud.png";

    private Image bgImage;
    private Image cloudImage;


    public MainMenuPanel() {
        initializePanel();
    }

    public void setNavigationListener(NavigationListener listener) {
        this.navigationListener = listener;
    }

    private void initializePanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(getClass().getResource(BACKGROUND));
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );

        ImageIcon cloudIc = new ImageIcon(getClass().getResource(CLOUD));
        if (cloudIc.getIconWidth() > 0) {
            int cloudWidth = (int) (screen.width * 0.30);
            int cloudHeight = (int) ((double) cloudIc.getIconHeight() /
                    cloudIc.getIconWidth() * cloudWidth);

            cloudImage = cloudIc.getImage().getScaledInstance(
                    cloudWidth, cloudHeight, Image.SCALE_SMOOTH
            );
        }

        JPanel menuBox = new JPanel();
        menuBox.setOpaque(false);
        menuBox.setLayout(new BoxLayout(menuBox, BoxLayout.Y_AXIS));

        JButton gameButton = makeImageButton(BTN_START);
        HelpersUI.addHoverSFX(gameButton, "/Hover.wav");
        HelpersUI.addLightenOnHover(gameButton, 1.25f); //lighten hover

        JButton menuOptionsButton = makeImageButton(BTN_MENU);
        HelpersUI.addHoverSFX(menuOptionsButton, "/Hover.wav");
        HelpersUI.addLightenOnHover(menuOptionsButton, 1.25f); //lighten hover

        JButton exitButton = makeImageButton(BTN_EXIT);
        HelpersUI.addHoverSFX(exitButton, "/Hover.wav");
        HelpersUI.addLightenOnHover(exitButton, 1.25f); //lighten hover

        gameButton.addActionListener(e -> {
            Sound.playSFX("/Clicked.wav");

            Sound.stopBGM();
            Sound.isMenuMusicPlaying = false;

            Sound.playBGM("/GameBg.wav");
            Sound.setBGMVolume(300);

            //parent.switchTo(GameWindow.CARD_GAME);
            // GAME ENTRY
            if (navigationListener != null) {
                navigationListener.onNavigateToGame();
            }

            // HelpersUI.fadeInComponent(parent.getScreen(GameWindow.CARD_GAME),
            //         18, 0.06f, null);
        });

        menuOptionsButton.addActionListener(e -> {
            
            Sound.playSFX("/Clicked.wav");
            if (navigationListener != null) {
                navigationListener.onNavigateToMenuOptions();
            }
            // parent.switchTo(GameWindow.CARD_MENUOPTIONS);
            // HelpersUI.fadeInComponent(parent.getScreen(GameWindow.CARD_MENUOPTIONS),
            //         18, 0.06f, null);
            //Sound.playSFX("Clicked.wav");
            
        });

        exitButton.addActionListener(e -> {
            Sound.playSFX("/Clicked.wav");
            if (navigationListener != null) {
                navigationListener.onExitApplication();
            }
            // System.exit(0);
            //Sound.playSFX("Clicked.wav");
        });

        menuBox.add(gameButton);
        menuBox.add(Box.createRigidArea(new Dimension(0, 20)));
        menuBox.add(menuOptionsButton);
        menuBox.add(Box.createRigidArea(new Dimension(0, 20)));
        menuBox.add(exitButton);

        add(menuBox);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {

                if (!Sound.comingFromGame)
                    return;

                Sound.comingFromGame = false;

                Sound.stopBGM();
                Sound.isMenuMusicPlaying = false;
                Sound.playBGM("/BgMusic.wav");
            }
        });
    }

    private JButton makeImageButton(String imagePath) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon rawIcon = new ImageIcon(MainMenuPanel.class.getResource(imagePath));

        int newWidth = (int) (screen.width * 0.20);
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

        if (cloudImage != null) {
            int cloudW = cloudImage.getWidth(this);
            int cloudH = cloudImage.getHeight(this);

            g.drawImage(cloudImage, (int) (getWidth() * 0.03),
                    (int) (getHeight() * 0.05), this);

            g.drawImage(cloudImage,
                    getWidth() - cloudW - (int) (getWidth() * 0.03),
                    (int) (getHeight() * 0.09), this);
        }
    }
}
/*
        gameButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToGame();
            }
        });
        menuOptionsButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToMenuOptions();
            }
        });
        exitButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onExitApplication();
            }
        });
*/