package ui;

import java.awt.*;
import javax.swing.*;

public class MainMenuPanel extends JPanel {

    private final GameWindow parent;
    private static final String BACKGROUND = "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\splash.png";
    private Image bgImage;

    public MainMenuPanel(GameWindow parent) {
        this.parent = parent;

        setLayout(new GridBagLayout());
        setOpaque(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(screen.width, screen.height, Image.SCALE_SMOOTH);

        JPanel menuBox = new JPanel();
        menuBox.setOpaque(false);
        menuBox.setLayout(new BoxLayout(menuBox, BoxLayout.Y_AXIS));

        JButton play = createButton("Play");
        JButton menu = createButton("Menu");
        JButton exit = createButton("Exit");

        play.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_GAME);
            JComponent game = parent.getScreen(GameWindow.CARD_GAME);
            HelpersUI.fadeInComponent(game, 18, 0.06f, null);
        });

        menu.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "How to Play / Settings / High Scores (TODO)"));

        exit.addActionListener(e -> System.exit(0));

        menuBox.add(play); menuBox.add(Box.createRigidArea(new Dimension(0, 30)));
        menuBox.add(menu); menuBox.add(Box.createRigidArea(new Dimension(0, 30)));
        menuBox.add(exit);

        add(menuBox);
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 28));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(50, 50, 50));
        b.setFocusPainted(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setPreferredSize(new Dimension(300, 70));
        return b;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        else {
            g.setColor(Color.GRAY);
            g.fillRect(0,0,getWidth(),getHeight());
        }
    }
}
