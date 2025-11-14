package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SplashScreenPanel extends JPanel {

    private final GameWindow parent;
    private Timer throbTimer;
    private Timer titlePulseTimer;
    private volatile boolean started = false;

    private static final String BACKGROUND =
            "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\splash.png";

    private static final String PRESS_START =
            "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\press_start.png";

    private static final String TITLE_IMAGE =
            "c:\\Users\\edsel\\AreYouSmarterGame-1\\assets\\title.png";

    private Image bgImage;

    public SplashScreenPanel(GameWindow parent) {
        this.parent = parent;

        setOpaque(false);
        setLayout(new GridBagLayout());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        // -------------------------
        // Load background
        // -------------------------
        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);

        // -------------------------
        // TITLE IMAGE + ANIMATION
        // -------------------------
        JLabel titleLabel = new JLabel();
        ImageIcon titleIc = new ImageIcon(TITLE_IMAGE);

        int titleW = (int) (screen.width * 0.90);
        int titleH = (int) ((double) titleIc.getIconHeight() / titleIc.getIconWidth() * titleW);

        Image titleBaseImage = titleIc.getImage()
                .getScaledInstance(titleW, titleH, Image.SCALE_SMOOTH);

        titleLabel.setIcon(new ImageIcon(titleBaseImage));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // TITLE ANIMATION (Pulse)
        titlePulseTimer = new Timer(60, null);

        final float[] scale = {1.0f};
        final boolean[] shrinking = {true};

        titlePulseTimer.addActionListener(e -> {
            if (shrinking[0]) {
                scale[0] -= 0.005f;
                if (scale[0] <= 0.98f) shrinking[0] = false;
            } else {
                scale[0] += 0.005f;
                if (scale[0] >= 1.02f) shrinking[0] = true;
            }

            int newW = Math.max(1, (int) (titleW * scale[0]));
            int newH = Math.max(1, (int) (titleH * scale[0]));

            Image scaled = titleBaseImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            titleLabel.setIcon(new ImageIcon(scaled));
        });

        titlePulseTimer.start();

        // -------------------------
        // PRESS START
        // -------------------------
        JLabel pressLabel = new JLabel();
        ImageIcon pressIc = new ImageIcon(PRESS_START);

        if (pressIc.getIconWidth() > 0) {
            int w = (int) (screen.width * 0.20);
            int h = (int) ((double) pressIc.getIconHeight() / pressIc.getIconWidth() * w);
            pressLabel.setIcon(new ImageIcon(
                    pressIc.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH)));
        } else {
            pressLabel.setText("Click or Press Any Key");
            pressLabel.setFont(new Font("Arial", Font.BOLD, 36));
            pressLabel.setForeground(Color.WHITE);
        }

        pressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // PRESS START throbbing (already safe)
        throbTimer = HelpersUI.createSimpleTextThrob(pressLabel, 60, 0.98f, 1.02f);

        // -------------------------
        // Layout container
        // -------------------------
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createVerticalGlue());
        content.add(titleLabel);
        content.add(Box.createRigidArea(new Dimension(0, 40)));
        content.add(pressLabel);
        content.add(Box.createVerticalGlue());

        add(content);

        // -------------------------
        // Input Listeners
        // -------------------------
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                proceed();
            }
        });

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                proceed();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> requestFocusInWindow());
            }
        });

        new Timer(8000, e -> proceed()).start();
    }

    // -------------------------
    // Transition to main menu
    // -------------------------
    private void proceed() {
        if (started) return;
        started = true;

        if (throbTimer != null) throbTimer.stop();
        if (titlePulseTimer != null) titlePulseTimer.stop();

        parent.switchTo(GameWindow.CARD_MAINMENU);

        JComponent menu = parent.getScreen(GameWindow.CARD_MAINMENU);
        HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
    }

    // -------------------------
    // Paint background
    // -------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        else {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }
}
