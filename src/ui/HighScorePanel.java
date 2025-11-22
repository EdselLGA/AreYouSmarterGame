package ui;

import Utils.Sound;
import java.awt.*;
import javax.swing.*;

public class HighScorePanel extends JPanel {
    
    private final GameWindow parent;

    private static final String BACKGROUND =
        "assets/background_2.png";
    private static final String BTN_BACK =
        "assets/BACK.png";

    private Image bgImage;

    public HighScorePanel(GameWindow parent) {
        this.parent = parent;
        
        setLayout(new BorderLayout());
        setOpaque(false);
        

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0) {
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );
        }

        JButton backBtn = makeImageButton(BTN_BACK);
        //lighten hover
        HelpersUI.addLightenOnHover(backBtn, 1.25f);
        //sfx hover
        HelpersUI.addHoverSFX(backBtn, "assets/Hover.wav");

        backBtn.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_MENUOPTIONS);
            JComponent menu = parent.getScreen(GameWindow.CARD_MENUOPTIONS);
            HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
            Sound.playSFX("assets/clicked.wav");
        });

        JPanel bottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        bottomLeft.setOpaque(false);
        bottomLeft.add(backBtn);
        
        add(bottomLeft, BorderLayout.SOUTH);
    }

    private JButton makeImageButton(String imagePath) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon rawIcon = new ImageIcon(imagePath);
        int newWidth = (int) (screen.width * 0.15);
        int newHeight = (int)((double) rawIcon.getIconHeight() /
                rawIcon.getIconWidth() * newWidth);

        Image scaled = rawIcon.getImage().getScaledInstance(
                newWidth, newHeight, Image.SCALE_SMOOTH
        );

        JButton btn = new JButton(new ImageIcon(scaled));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);

        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }  
}
