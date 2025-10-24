package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SplashScreen {
    private JWindow splash;
    private boolean started = false;
    private Timer logoThrob, pressKeyThrob;

    public void showSplash() {
        splash = new JWindow();

        // Get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Load and scale background
        Image background = new ImageIcon("c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\splash.png")
                .getImage()
                .getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);

        // Load assets (check these paths exist!)
        ImageIcon logoIcon = new ImageIcon("c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\logo.png");
        ImageIcon pressKeyIcon = new ImageIcon("c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\press_any_key.png");

        // Custom panel for the background image
        JPanel splashPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Use vertical BoxLayout (lets both logo and text show clearly)
        splashPanel.setLayout(new BoxLayout(splashPanel, BoxLayout.Y_AXIS));
        splashPanel.setOpaque(false);

        // --- Center Logo ---
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder((int)(screenSize.height * 0.25), 0, 0, 0));

        // --- Press Any Key (image or text fallback) ---
        JLabel pressKeyLabel;
        if (pressKeyIcon.getIconWidth() > 0)
            pressKeyLabel = new JLabel(pressKeyIcon);
        else
            pressKeyLabel = new JLabel("Press any key to start", SwingConstants.CENTER);

        pressKeyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pressKeyLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 60, 0));
        pressKeyLabel.setForeground(Color.WHITE);
        pressKeyLabel.setFont(new Font("Arial", Font.BOLD, 28));

        // Add components vertically
        splashPanel.add(Box.createVerticalGlue());
        splashPanel.add(logoLabel);
        splashPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        splashPanel.add(pressKeyLabel);
        splashPanel.add(Box.createVerticalGlue());

        splash.getContentPane().add(splashPanel);
        splash.setBounds(0, 0, screenSize.width, screenSize.height);
        splash.setVisible(true);

        // --- Apply animations ---
        logoThrob = HelpersUI.createScaleThrob(logoLabel, 50, 0.9f, 1.1f);
        pressKeyThrob = HelpersUI.createFadeThrob(pressKeyLabel, 60, 0.3f, 1.0f);

        // --- Detect key press globally ---
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (!started && e.getID() == KeyEvent.KEY_PRESSED) {
                started = true;
                stopAnimations();
                splash.dispose();
                new MainMenu().showMenu(); // Launch main menu
            }
            return false;
        });

        // --- Optional fallback ---
        new Timer(10000, e -> {
            if (!started) {
                started = true;
                stopAnimations();
                splash.dispose();
                new MainMenu().showMenu();
            }
        }).start();
    }

    private void stopAnimations() {
        if (logoThrob != null) logoThrob.stop();
        if (pressKeyThrob != null) pressKeyThrob.stop();
    }
}
