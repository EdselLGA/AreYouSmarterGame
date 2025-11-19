package ui;

import java.awt.*;
import javax.swing.*;

public class MenuOptionsPanel extends JPanel {

        private final GameWindow parent;

    private static final String BACKGROUND =
            "assets/background_2.png";
    private static final String BTN_HOWTOPLAY =
            "assets/HOW TO PLAY.png";
    private static final String BTN_HIGHSCORES =
            "assets/HIGHSCORES.png";
    private static final String BTN_SETTINGS =
            "assets/SETTINGS.png";
    private static final String BTN_BACK =
            "assets\\BACK.png";

        private Image bgImage;


        public MenuOptionsPanel(GameWindow parent) {
                this.parent = parent;
                setLayout(new GridBagLayout());
                setOpaque(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );

        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JButton howToPlayBtn = makeImageButton(BTN_HOWTOPLAY);
        JButton highScoresBtn = makeImageButton(BTN_HIGHSCORES);
        JButton settingsBtn = makeImageButton(BTN_SETTINGS);
        JButton backBtn = makeImageButton(BTN_BACK);

        howToPlayBtn.addActionListener(e -> {
                parent.switchTo(GameWindow.CARD_HOWTOPLAY);
                JComponent howToPlay = parent.getScreen(GameWindow.CARD_HOWTOPLAY);
                HelpersUI.fadeInComponent(howToPlay, 18, 0.06f, null);
        });

        highScoresBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "HIGH SCORES feature coming soon!")
                
        );

        settingsBtn.addActionListener(e ->
                parent.switchTo(GameWindow.CARD_SETTINGS)
        );

        backBtn.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_MAINMENU);
            JComponent main = parent.getScreen(GameWindow.CARD_MAINMENU);
            HelpersUI.fadeInComponent(main, 18, 0.06f, null);
        });

        box.add(howToPlayBtn);
        box.add(Box.createRigidArea(new Dimension(0, 20)));

        box.add(highScoresBtn);
        box.add(Box.createRigidArea(new Dimension(0, 20)));

        box.add(settingsBtn);
        box.add(Box.createRigidArea(new Dimension(0, 20)));

        box.add(backBtn);
        box.add(Box.createRigidArea(new Dimension(0, 20)));

        add(box);
    }

    private JButton makeImageButton(String imagePath) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon rawIcon = new ImageIcon(imagePath);

        int newWidth = (int) (screen.width * 0.20);
        int newHeight = (int) ((double) rawIcon.getIconHeight() /
                rawIcon.getIconWidth() * newWidth);

        Image scaled = rawIcon.getImage().getScaledInstance(
                newWidth, newHeight, Image.SCALE_SMOOTH
        );

        ImageIcon scaledIcon = new ImageIcon(scaled);

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
    }
}
