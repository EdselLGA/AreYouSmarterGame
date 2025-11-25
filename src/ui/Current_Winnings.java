package ui;

import Utils.Sound;
import core.GameLogic;
import java.awt.*;
import javax.swing.*;

public class Current_Winnings extends JPanel {

    private final GameWindow parent;
    private static final String BACKGROUND = "assets/Current_Winnings1.png";
    private static final String BTN_CHEST = "assets/Chestbutton.png";

    private Image bgImage;
    private JLabel amountLabel;

    public Current_Winnings(GameWindow parent) {
        this.parent = parent;

        setLayout(null); // absolute layout for FULL control
        setOpaque(false);

        // load background
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon bgIcon = new ImageIcon(BACKGROUND);
        if (bgIcon.getIconWidth() > 0) {
            bgImage = bgIcon.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );
        }

        // ==== WINNINGS LABEL (ABSOLUTE POSITION) ====

        amountLabel = new JLabel("$0", JLabel.CENTER);
        amountLabel.setFont(new Font("Arial", Font.BOLD, 150));
        amountLabel.setForeground(Color.WHITE);

        // Set exact X, Y, width, height (change these to move it)
        int labelWidth = 500;
        int labelHeight = 200;

        int posX = (screen.width / 2) - (labelWidth / 2); // center horizontally
        int posY = (int)(screen.height * 0.48); // move UP or DOWN by changing 0.48

        amountLabel.setBounds(posX, posY, labelWidth, labelHeight);

        add(amountLabel);

        // ==== CHEST BUTTON (BOTTOM RIGHT) ====

        JButton chestButton = new JButton();
        chestButton.setBorderPainted(false);
        chestButton.setContentAreaFilled(false);
        chestButton.setFocusPainted(false);
        chestButton.setOpaque(false);

        ImageIcon chestIcon = new ImageIcon(BTN_CHEST);
        int chestWidth = (int)(screen.width * 0.22);
        int chestHeight = chestIcon.getIconHeight() * chestWidth / chestIcon.getIconWidth();

        Image scaledChest = chestIcon.getImage().getScaledInstance(
                chestWidth, chestHeight, Image.SCALE_SMOOTH
        );
        chestButton.setIcon(new ImageIcon(scaledChest));

        HelpersUI.addLightenOnHover(chestButton, 1.25f);
        HelpersUI.addHoverSFX(chestButton, "assets/Hover.wav");

        chestButton.addActionListener(e -> {
            Sound.playSFX("assets/Clicked.wav");
            parent.switchTo(GameWindow.CARD_GAME);
        });

        // chest button position (bottom-right)
        chestButton.setBounds(
                screen.width - chestWidth - 40,  // X
                screen.height - chestHeight + 30, // Y
                chestWidth, chestHeight
        );

        add(chestButton);
    }

    // update winnings display
    public void updateWinnings() {
        GameLogic gl = parent.getGameLogic();
        amountLabel.setText("$" + gl.getTotalWinnings());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
