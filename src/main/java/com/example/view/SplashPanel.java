package com.example.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * SplashPanel - displays splash screen
 * Navigates to MainMenuPanel on any mouse or keyboard click
 * Uses NavigationListener to fire events (MVC compliant)
 */


public class SplashPanel extends JPanel {
    private NavigationListener navigationListener;
    private Timer throbTimer;
    private Timer titlePulseTimer;
    private volatile boolean started = false;

    private static final String BACKGROUND = "/splash.png";
    private static final String PRESS_START = "/press_start.png";
    private static final String TITLE_IMAGE = "/title.png";
    private static final String CLOUD_IMAGE = "/Cloud.png";

    private Image bgImage;

    public SplashPanel() {
        initializePanel();
    }

    public void setNavigationListener(NavigationListener listener) {
        this.navigationListener = listener;
    }

    private void initializePanel() {
        setLayout(new BorderLayout());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("HELLO WORLD");
        // Play menu BGM
        Sound.playBGM("/BgMusic.wav");

        // LAYERED PANEL
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(screen);
        layeredPane.setBounds(0, 0, screen.width, screen.height);
        add(layeredPane, BorderLayout.CENTER);

        // BACKGROUND
        ImageIcon bgIc = new ImageIcon(getClass().getResource(BACKGROUND));
        bgImage = bgIc.getImage().getScaledInstance(screen.width, screen.height, Image.SCALE_SMOOTH);

        JLabel bgLabel = new JLabel(new ImageIcon(bgImage));
        bgLabel.setBounds(0, 0, screen.width, screen.height);
        layeredPane.add(bgLabel, Integer.valueOf(0));

        // CLOUDS
        ImageIcon cloudIc = new ImageIcon(getClass().getResource(CLOUD_IMAGE));
        int cloudW = (int) (screen.width * 0.25);
        int cloudH = cloudIc.getIconHeight() * cloudW / cloudIc.getIconWidth();

        Image cloudScaled = cloudIc.getImage().getScaledInstance(cloudW, cloudH, Image.SCALE_SMOOTH);

        JLabel cloudLeft = new JLabel(new ImageIcon(cloudScaled));
        cloudLeft.setBounds(30, 20, cloudW, cloudH);

        JLabel cloudRight = new JLabel(new ImageIcon(cloudScaled));
        cloudRight.setBounds(screen.width - cloudW - 30, 20, cloudW, cloudH);

        layeredPane.add(cloudLeft, Integer.valueOf(1));
        layeredPane.add(cloudRight, Integer.valueOf(1));

        // CONTENT CENTER
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        // TITLE
        ImageIcon titleIc = new ImageIcon(getClass().getResource(TITLE_IMAGE));
        JLabel titleLabel = new JLabel();

        int titleW = (int) (screen.width * 0.90);
        int titleH = titleIc.getIconHeight() * titleW / titleIc.getIconWidth();

        Image titleBase = titleIc.getImage().getScaledInstance(titleW, titleH, Image.SCALE_SMOOTH);
        titleLabel.setIcon(new ImageIcon(titleBase));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // TITLE PULSE ANIMATION
        titlePulseTimer = new Timer(60, null);
        final float[] scale = {1.0f};
        final boolean[] shrinking = {true};

        titlePulseTimer.addActionListener(e -> {
            scale[0] += shrinking[0] ? -0.004f : 0.004f;

            if (scale[0] <= 0.989f) shrinking[0] = false;
            else if (scale[0] >= 1.015f) shrinking[0] = true;

            int newW = (int) (titleW * scale[0]);
            int newH = (int) (titleH * scale[0]);
            Image img = titleBase.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);

            titleLabel.setIcon(new ImageIcon(img));
        });

        titlePulseTimer.start();

        // PRESS START LABEL
        ImageIcon pressIc = new ImageIcon(getClass().getResource(PRESS_START));
        JLabel pressLabel = new JLabel();

        int w = (int) (screen.width * 0.20);
        int h = pressIc.getIconHeight() * w / pressIc.getIconWidth();
        pressLabel.setIcon(new ImageIcon(pressIc.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH)));
        pressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // THROB EFFECT
        throbTimer = HelpersUI.createFadeInOut(pressLabel, 35, 0.03f);

        // ADD COMPONENTS
        content.add(Box.createVerticalGlue());
        content.add(titleLabel);
        content.add(Box.createRigidArea(new Dimension(0, 45)));
        content.add(pressLabel);
        content.add(Box.createVerticalGlue());

        content.setBounds(0, 0, screen.width, screen.height);
        layeredPane.add(content, Integer.valueOf(2));

        // INPUT HANDLING
        addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                Sound.playSFX("/Clicked.wav");
                proceed();
                navigationListener.onNavigateToMainMenu();
            }
        });

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                Sound.playSFX("/Clicked.wav");
                proceed();
                navigationListener.onNavigateToMainMenu();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override public void componentShown(ComponentEvent e) {
                requestFocusInWindow();
            }
        });

        // AUTO CONTINUE AFTER 80 SECONDS
        new Timer(80000, e -> proceed()).start();

    }
    private void proceed() {
        if (started) return;
        started = true;

        if (throbTimer != null) throbTimer.stop();
        if (titlePulseTimer != null) titlePulseTimer.stop();
        
    }

}
