package ui;

import Utils.Sound;
import java.awt.*;
import javax.swing.*;

public class GameScreenPanel extends JPanel {

    private final GameWindow parent;
    private static final String BACKGROUND = "assets/splash.png";
    private Image bgImage;

    public GameScreenPanel(GameWindow parent) {
        this.parent = parent;

        setOpaque(false);
        setLayout(new BorderLayout());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon bgIc = new ImageIcon(BACKGROUND);

        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);

        JLabel center = new JLabel("GAME SCREEN (PLACEHOLDER)", SwingConstants.CENTER);
        center.setFont(new Font("Arial", Font.BOLD, 40));
        center.setForeground(Color.WHITE);

        JButton back = new JButton("Back to Menu");
        back.setFont(new Font("Arial", Font.BOLD, 28));

        back.addActionListener(e -> {

            Sound.playSFX("assets/Clicked.wav");

            Sound.comingFromGame = true;

            parent.switchTo(GameWindow.CARD_MAINMENU);
            HelpersUI.fadeInComponent(parent.getScreen(GameWindow.CARD_MAINMENU),
                    18, 0.06f, null);
        });

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(back);

        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}
