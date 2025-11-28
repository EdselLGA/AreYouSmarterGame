package com.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * HowToPlayPanel - how to play instructions screen
 * Button: Back to Menu Options
 * Uses NavigationListener to fire events (MVC compliant)
 */
public class HowToPlayPanel extends JPanel {
    private NavigationListener navigationListener;
    private JButton backButton;

    private static final String BACKGROUND = "/Background_3.png";
    private static final String HOWTOPLAY = "/HowToPlay.png";
    private static final String BTN_BACK = "/BACK.png";

    private Image bgImage;

    public HowToPlayPanel() {
        initializePanel();
    }

    public void setNavigationListener(NavigationListener listener) {
        this.navigationListener = listener;
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(getClass().getResource(BACKGROUND));
        if (bgIc.getIconWidth() > 0) {
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );
        }

        JLabel howToPlayLabel = makeScaledImage(HOWTOPLAY, 0.7);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        centerWrapper.add(howToPlayLabel, gbc);

        add(centerWrapper, BorderLayout.CENTER);

        JButton backBtn = makeImageButton(BTN_BACK);

        //lighten hover
        HelpersUI.addLightenOnHover(backBtn, 1.25f);
        //sfx hover
        HelpersUI.addHoverSFX(backBtn, "/Hover.wav");

        backBtn.addActionListener(e -> {
            // parent.switchTo(GameWindow.CARD_MENUOPTIONS);
            // JComponent menu = parent.getScreen(GameWindow.CARD_MENUOPTIONS);
            // HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
            Sound.playSFX("/Clicked.wav");
            if (navigationListener != null) {
                navigationListener.onNavigateToMenuOptions();
            }
        });

        JPanel bottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        bottomLeft.setOpaque(false);
        bottomLeft.add(backBtn);

        add(bottomLeft, BorderLayout.SOUTH);
    }
    private JButton makeImageButton(String imagePath) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon rawIcon = new ImageIcon(HowToPlayPanel.class.getResource(imagePath));
        int newWidth = (int) (screen.width * 0.15);
        int newHeight = (int) ((double) rawIcon.getIconHeight() /
                rawIcon.getIconWidth() * newWidth);

        Image scaled = rawIcon.getImage().getScaledInstance(
                newWidth, newHeight, Image.SCALE_SMOOTH
        );

        JButton btn = new JButton(new ImageIcon(scaled));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);

        return btn;
    }

    private JLabel makeScaledImage(String path, double widthPercent) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon rawIcon = new ImageIcon(getClass().getResource(path));

        int newWidth = (int) (screen.width * widthPercent);
        int newHeight = (int) ((double) rawIcon.getIconHeight() /
                rawIcon.getIconWidth() * newWidth);

        Image scaled = rawIcon.getImage().getScaledInstance(
                newWidth, newHeight, Image.SCALE_SMOOTH
        );

        return new JLabel(new ImageIcon(scaled));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
/*
        backButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigateToMenuOptions();
            }
        });
*/

