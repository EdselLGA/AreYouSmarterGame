package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SplashScreenPanel extends JPanel {

    private final GameWindow parent;
    private Timer textThrob;
    private boolean started = false;

    private static final String BACKGROUND = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\splash.png";
    private static final String LOGO = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\title.png";
    private static final String PRESS_START = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\press_start.png";

    private Image bgImage;

    public SplashScreenPanel(GameWindow parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
        setOpaque(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        bgImage = new ImageIcon(BACKGROUND).getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        ImageIcon logoIcon = new ImageIcon(LOGO);
        JLabel logoLabel = new JLabel();

        int logoW = (int) (screenSize.width * 0.6);
        int logoH = (int) ((double) logoIcon.getIconHeight() / logoIcon.getIconWidth() * logoW);
        logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(logoW, logoH, Image.SCALE_SMOOTH)));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon pressIcon = new ImageIcon(PRESS_START);
        JLabel pressLabel = new JLabel();
        int pressW = (int) (screenSize.width * 0.2);
        int pressH = (int) ((double) pressIcon.getIconHeight() / pressIcon.getIconWidth() * pressW);
        pressLabel.setIcon(new ImageIcon(pressIcon.getImage().getScaledInstance(pressW, pressH, Image.SCALE_SMOOTH)));
        pressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(Box.createVerticalGlue());
        content.add(logoLabel);
        content.add(Box.createRigidArea(new Dimension(0, 60)));
        content.add(pressLabel);
        content.add(Box.createVerticalGlue());

        add(content);

        textThrob = HelpersUI.createScaleThrob(pressLabel, 60, 0.995f, 1.02f);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
        addMouseListener(mouseListener);

        new Timer(100000, e -> {
            proceedToMainMenu();
        }).start();
    }

    private final KeyEventDispatcher keyEventDispatcher = new KeyEventDispatcher() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (!started && e.getID() == KeyEvent.KEY_PRESSED) {
                proceedToMainMenu();
            }
            return false;
        }
    };

    private final MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (!started) proceedToMainMenu();
        }
    };

    private void proceedToMainMenu() {
        if (started) return;
        started = true;

        if (textThrob != null) textThrob.stop();

        HelpersUI.fadePanel(this, false, 20, 0.06f, () -> {
            parent.switchTo(GameWindow.CARD_MAINMENU);

            JComponent mainMenu = parent.getScreen(GameWindow.CARD_MAINMENU);
            if (mainMenu != null) {
                HelpersUI.fadePanel(mainMenu, true, 20, 0.06f, null);
            }
            KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
            removeMouseListener(mouseListener);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
