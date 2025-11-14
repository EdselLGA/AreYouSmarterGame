package ui;

import java.awt.*;
import javax.swing.*;

public class GameScreenPanel extends JPanel {

    private final GameWindow parent;
    private Image bgImage;

    private static final String BACKGROUND = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\splash.png";

    public GameScreenPanel(GameWindow parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        bgImage = new ImageIcon(BACKGROUND).getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);

        JLabel label = new JLabel("GAME SCREEN (Placeholder)", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setForeground(Color.WHITE);

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> {
            HelpersUI.fadePanel(this, false, 18, 0.06f, () -> {
                parent.switchTo(GameWindow.CARD_MAINMENU);
                JComponent mainMenu = parent.getScreen(GameWindow.CARD_MAINMENU);
                if (mainMenu != null) HelpersUI.fadePanel(mainMenu, true, 18, 0.06f, null);
            });
        });

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(back);

        add(label, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
