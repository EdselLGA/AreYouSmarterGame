package ui;

import java.awt.*;
import javax.swing.*;

public class MenuOptionsPanel extends JPanel {

    private final GameWindow parent;
    private static final String BACKGROUND =
            "c:\\Users\\edsel\\AreYouSmarterGame-5\\assets\\splash.png";

    private Image bgImage;

    public MenuOptionsPanel(GameWindow parent) {
        this.parent = parent;

        setLayout(new GridBagLayout());
        setOpaque(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);

        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        // Create simple text buttons for now
        JButton howToPlay = createButton("How to Play");
        JButton highScores = createButton("High Scores");
        JButton settings = createButton("Settings");
        JButton back = createButton("Back");

        howToPlay.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Show HOW TO PLAY screen here"));

        highScores.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Show HIGH SCORES here"));

        settings.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Show SETTINGS here"));

        back.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_MAINMENU);
            JComponent menu = parent.getScreen(GameWindow.CARD_MAINMENU);
            HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
        });

        box.add(howToPlay);
        box.add(Box.createRigidArea(new Dimension(0, 25)));
        box.add(highScores);
        box.add(Box.createRigidArea(new Dimension(0, 25)));
        box.add(settings);
        box.add(Box.createRigidArea(new Dimension(0, 25)));
        box.add(back);

        add(box);
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 36));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(50, 50, 50));
        b.setFocusPainted(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setPreferredSize(new Dimension(350, 70));
        return b;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}
