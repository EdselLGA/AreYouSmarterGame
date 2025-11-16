package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SplashScreenPanel extends JPanel {

    private final GameWindow parent;
    private Timer throbTimer;
    private Timer titlePulseTimer;
    private volatile boolean started = false;

    // paths
    private static final String BACKGROUND =
            "assets/splash.png";

    private static final String PRESS_START =
            "assets/press_start.png";

    private static final String TITLE_IMAGE =
            "assets/title.png";

    private static final String CLOUD_IMAGE =
            "assets/Cloud.png";

    private Image bgImage;

    public SplashScreenPanel(GameWindow parent) {
        this.parent = parent;
        setLayout(new BorderLayout());   

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(screen);
        layeredPane.setBounds(0, 0, screen.width, screen.height);

        add(layeredPane, BorderLayout.CENTER);

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(screen.width, screen.height, Image.SCALE_SMOOTH);

        JLabel bgLabel = new JLabel(new ImageIcon(bgImage));
        bgLabel.setBounds(0, 0, screen.width, screen.height);
        layeredPane.add(bgLabel, Integer.valueOf(0));

        ImageIcon cloudIc = new ImageIcon(CLOUD_IMAGE);
        int cloudW = (int) (screen.width * 0.25);
        int cloudH = (int) ((double) cloudIc.getIconHeight() / cloudIc.getIconWidth() * cloudW);
        Image cloudScaled = cloudIc.getImage().getScaledInstance(cloudW, cloudH, Image.SCALE_SMOOTH);

        JLabel cloudLeft = new JLabel(new ImageIcon(cloudScaled));
        cloudLeft.setBounds(30, 20, cloudW, cloudH);

        JLabel cloudRight = new JLabel(new ImageIcon(cloudScaled));
        cloudRight.setBounds(screen.width - cloudW - 30, 20, cloudW, cloudH);

        layeredPane.add(cloudLeft, Integer.valueOf(1));
        layeredPane.add(cloudRight, Integer.valueOf(1));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel();
        ImageIcon titleIc = new ImageIcon(TITLE_IMAGE);

        int titleW = (int) (screen.width * 0.90);
        int titleH = (int) ((double) titleIc.getIconHeight() / titleIc.getIconWidth() * titleW);

        Image titleBaseImage =
                titleIc.getImage().getScaledInstance(titleW, titleH, Image.SCALE_SMOOTH);

        titleLabel.setIcon(new ImageIcon(titleBaseImage));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePulseTimer = new Timer(60, null); 
        final float[] scale = {1.0f};
        final boolean[] shrinking = {true};

        titlePulseTimer.addActionListener(e -> {
            if (shrinking[0]) {
                scale[0] -= 0.004f;
                if (scale[0] <= 0.989f) shrinking[0] = false;
            } else {
                scale[0] += 0.004f;
                if (scale[0] >= 1.015f) shrinking[0] = true;
            }

            int newW = (int) (titleW * scale[0]);
            int newH = (int) (titleH * scale[0]);

            Image scaled =
                    titleBaseImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);

            titleLabel.setIcon(new ImageIcon(scaled));
        });

        titlePulseTimer.start();

        JLabel pressLabel = new JLabel();
        ImageIcon pressIc = new ImageIcon(PRESS_START);

        if (pressIc.getIconWidth() > 0) {
            int w = (int) (screen.width * 0.20);
            int h = (int) ((double) pressIc.getIconHeight() / pressIc.getIconWidth() * w);
            pressLabel.setIcon(new ImageIcon(
                    pressIc.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH)));
        }
        pressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        throbTimer = HelpersUI.createSimpleTextThrob(pressLabel, 60, 0.98f, 1.02f);

        content.add(Box.createVerticalGlue());
        content.add(titleLabel);
        content.add(Box.createRigidArea(new Dimension(0, 45)));
        content.add(pressLabel);
        content.add(Box.createVerticalGlue());

        content.setBounds(0, 0, screen.width, screen.height);
        layeredPane.add(content, Integer.valueOf(2));
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

    private void proceed() {
        if (started) return;
        started = true;

        if (throbTimer != null) throbTimer.stop();
        if (titlePulseTimer != null) titlePulseTimer.stop();

        parent.switchTo(GameWindow.CARD_MAINMENU);

        JComponent menu = parent.getScreen(GameWindow.CARD_MAINMENU);
        HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
