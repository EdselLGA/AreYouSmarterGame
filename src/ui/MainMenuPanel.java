package ui;

import java.awt.*;
import javax.swing.*;

public class MainMenuPanel extends JPanel {

    private final GameWindow parent;

    private static final String BACKGROUND =
            "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\splash.png";

    private static final String BTN_START =
            "c:\\Users\\edsel\\AreYouSmarterGame-1\\assets\\START.png";
    private static final String BTN_MENU =
            "c:\\Users\\edsel\\AreYouSmarterGame-1\\assets\\MENU.png";
    private static final String BTN_EXIT =
            "c:\\Users\\edsel\\AreYouSmarterGame-1\\assets\\EXIT.png";

    private Image bgImage;

    public MainMenuPanel(GameWindow parent) {
        this.parent = parent;

        setLayout(new GridBagLayout());
        setOpaque(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);

        JPanel menuBox = new JPanel();
        menuBox.setOpaque(false);
        menuBox.setLayout(new BoxLayout(menuBox, BoxLayout.Y_AXIS));

        JButton playBtn = makeImageButton(BTN_START);
        JButton menuBtn = makeImageButton(BTN_MENU);
        JButton exitBtn = makeImageButton(BTN_EXIT);

        playBtn.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_GAME);
            JComponent game = parent.getScreen(GameWindow.CARD_GAME);
            HelpersUI.fadeInComponent(game, 18, 0.06f, null);
        });

        menuBtn.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_MENUOPTIONS);
            JComponent menu = parent.getScreen(GameWindow.CARD_MENUOPTIONS);
            HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
        });


        exitBtn.addActionListener(e -> System.exit(0));

        menuBox.add(playBtn);
        menuBox.add(Box.createRigidArea(new Dimension(0, 20)));
        menuBox.add(menuBtn);
        menuBox.add(Box.createRigidArea(new Dimension(0, 20)));
        menuBox.add(exitBtn);

        add(menuBox);
    }

    private JButton makeImageButton(String imagePath) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon rawIcon = new ImageIcon(imagePath);

        int newWidth = (int) (screen.width * 0.20);
        int newHeight = (int) ((double) rawIcon.getIconHeight() / rawIcon.getIconWidth() * newWidth);

        Image scaledImg = rawIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

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
        else {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

}
