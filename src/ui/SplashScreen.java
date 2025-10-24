package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SplashScreen {
    private JWindow splash;
    private boolean started = false;
    private Timer textThrob; // Only the text throbs now

    public void showSplash() {
        splash = new JWindow();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // --- Load and scale background ---
        Image background = new ImageIcon("c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\splash.png")
                .getImage()
                .getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);

        // --- Load logo ---
        ImageIcon logoIcon = new ImageIcon("c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\title.png");
        ImageIcon Press_Start = new ImageIcon("c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\press_start.png");

        // --- Create main panel ---
        JPanel splashPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        splashPanel.setLayout(new GridBagLayout());
        splashPanel.setOpaque(false);

        // --- Inner content panel ---
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        // --- Logo label (no animation) ---
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setVerticalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder((int)(screenSize.height * 0.10), 0, 0, 0));
        //make the logo scale to 60% of screen width
        int logoWidth = (int)(screenSize.width * .9);
        int logoHeight = (int)((double)logoIcon.getIconHeight() / logoIcon.getIconWidth() * logoWidth);
        logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH)));
        // ---

        // --- "Press Any Key" label (fades softly) ---
        //JLabel pressKeyLabel = new JLabel("Press any key to start", SwingConstants.CENTER);
        //pressKeyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //pressKeyLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 60, 0));
        //pressKeyLabel.setForeground(new Color(1f, 1f, 1f, 1f));
        //pressKeyLabel.setFont(new Font("Arial", Font.BOLD, 28));
        
        // Use image for "Press Any Key"
        JLabel pressKeyLabel = new JLabel(Press_Start);
        pressKeyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pressKeyLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 60, 0));
        // Scale the image to 40% of screen width
        int pressKeyWidth = (int)(screenSize.width * 0.2);
        int pressKeyHeight = (int)((double)Press_Start.getIconHeight() / Press_Start.getIconWidth() * pressKeyWidth);
        pressKeyLabel.setIcon(new ImageIcon(Press_Start.getImage().getScaledInstance(pressKeyWidth, pressKeyHeight, Image.SCALE_SMOOTH)));

        // --- Combine layout ---
        content.add(Box.createVerticalGlue());
        content.add(logoLabel);
        content.add(Box.createRigidArea(new Dimension(0, 60)));
        content.add(pressKeyLabel);
        content.add(Box.createVerticalGlue());

        splashPanel.add(content);
        splash.getContentPane().add(splashPanel);
        splash.setBounds(0, 0, screenSize.width, screenSize.height);
        splash.setOpacity(0f);
        splash.setVisible(true);

        // --- Fade in window ---
        HelpersUI.fadeWindow(splash, true, 30, 0.09f, null);

        // --- Text throb only ---
        textThrob = HelpersUI.createScaleThrob(pressKeyLabel, 500, 0.999f, 1.0f);

       // --- Close splash on any input ---
        Runnable closeSplash = () -> {
            if (!started) {
                started = true;
                stopAnimations();
                HelpersUI.fadeWindow(splash, false, 30, 0.05f, () -> {
                    splash.dispose();
                    new MainMenu().showMenu();
                });
            }
        };

        // --- Keyboard input ---
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                closeSplash.run();
            }
            return false;
        });

        // --- Mouse input ---
        splash.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                closeSplash.run();
            }
        });

        // --- Fallback timeout ---
        new Timer(10000, e -> closeSplash.run()).start();
        
    }

    private void stopAnimations() {
        if (textThrob != null) textThrob.stop();
    }
}

