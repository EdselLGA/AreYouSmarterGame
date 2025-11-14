package ui;

import java.awt.*;
import javax.swing.*;

public class MainMenuPanel extends JPanel {

    private final GameWindow parent;

    private static final String BACKGROUND = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\splash.png";
    private static final String BTN_PLAY = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\btn_play.png";
    private static final String BTN_PLAY_HOVER = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\btn_play_hover.png";
    private static final String BTN_MENU = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\btn_menu.png";
    private static final String BTN_MENU_HOVER = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\btn_menu_hover.png";
    private static final String BTN_EXIT = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\btn_exit.png";
    private static final String BTN_EXIT_HOVER = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\btn_exit_hover.png";

    private Image bgImage;

    public MainMenuPanel(GameWindow parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
        setOpaque(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        bgImage = new ImageIcon(BACKGROUND).getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JButton playBtn = makeButton(BTN_PLAY, BTN_PLAY_HOVER, screenSize);
        JButton menuBtn = makeButton(BTN_MENU, BTN_MENU_HOVER, screenSize);
        JButton exitBtn = makeButton(BTN_EXIT, BTN_EXIT_HOVER, screenSize);

        playBtn.addActionListener(e -> {
            HelpersUI.fadePanel(this, false, 18, 0.06f, () -> {
                parent.switchTo(GameWindow.CARD_GAME);
                JComponent game = parent.getScreen(GameWindow.CARD_GAME);
                if (game != null) HelpersUI.fadePanel(game, true, 18, 0.06f, null);
            });
        });

        menuBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Menu Options (How to Play / Settings / High Scores) - TODO");
        });

        exitBtn.addActionListener(e -> System.exit(0));

        menuPanel.add(playBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        menuPanel.add(menuBtn);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        menuPanel.add(exitBtn);

        add(menuPanel);
    }

    private JButton makeButton(String normalPath, String hoverPath, Dimension screenSize) {
        ImageIcon normalIc = new ImageIcon(normalPath);
        ImageIcon hoverIc = new ImageIcon(hoverPath);

        int w = (int) (screenSize.width * 0.20);
        int h = (int) ((double) normalIc.getIconHeight() / normalIc.getIconWidth() * w);

        Image normalImg = normalIc.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        Image hoverImg = hoverIc.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);

        JButton btn = new JButton(new ImageIcon(normalImg));
        btn.setRolloverIcon(new ImageIcon(hoverImg));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
