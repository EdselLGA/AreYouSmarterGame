package ui;

import java.awt.*;
import javax.swing.*;

public class GameScreenPanel extends JPanel {

    private final GameWindow parent;
    private static final String BACKGROUND = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\splash.png";
    private Image bgImage;

    public GameScreenPanel(GameWindow parent) {
        this.parent = parent;
        setOpaque(false);
        setLayout(new BorderLayout());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(screen.width, screen.height, Image.SCALE_SMOOTH);

        JLabel center = new JLabel("GAME SCREEN (PLACEHOLDER)", SwingConstants.CENTER);
        center.setFont(new Font("Arial", Font.BOLD, 40));
        center.setForeground(Color.WHITE);

        JButton back = new JButton("Back to Menu");
        back.setFont(new Font("Arial", Font.BOLD, 28));
        back.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_MAINMENU);
            JComponent menu = parent.getScreen(GameWindow.CARD_MAINMENU);
            HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
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
        else {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }
}
