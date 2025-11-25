package ui;

import Utils.Sound;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NameInputPanel extends JPanel {

    private final GameWindow parent;

    private static final String BACKGROUND = "assets/NameBG.png";
    private static final String BTN_CONTINUE = "assets/CONTINUE.png";

    private Image bgImage;

    public NameInputPanel(GameWindow parent) {
        this.parent = parent;

        setLayout(null); 
        setOpaque(false);

        loadBackground();

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        // --- Title Label ---
        JLabel lblTitle = new JLabel("ENTER PLAYER NAME");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 38));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds((int)(screen.width * 0.28), 120, 600, 50);
        add(lblTitle);

        // --- Text Field ---
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 28));
        nameField.setForeground(Color.WHITE);
        nameField.setOpaque(false);
        nameField.setBackground(new Color(0, 0, 0, 120));
        nameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        nameField.setBounds((int)(screen.width * 0.28), 200, 450, 60);
        add(nameField);

        // --- Continue Button ---
        JButton continueBtn = makeButton(BTN_CONTINUE);

        continueBtn.setBounds((int)(screen.width * 0.35), 300, 300, 120);
        HelpersUI.addLightenOnHover(continueBtn, 1.25f);
        HelpersUI.addHoverSFX(continueBtn, "assets/Hover.wav");
        add(continueBtn);

        continueBtn.addActionListener(e -> {
            Sound.playSFX("assets/Clicked.wav");

            String playerName = nameField.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid name!",
                        "Missing Name",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Store name in Game core object
            parent.getGame().setPlayerName(playerName);

            // Go to next screen (subject select)
            parent.switchTo(GameWindow.CARD_GAME);
            HelpersUI.fadeInComponent(parent.getScreen(GameWindow.CARD_GAME),
                    18, 0.06f, null);
        });
    }

    private JButton makeButton(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaled = icon.getImage().getScaledInstance(
                (int)(icon.getIconWidth() * 1.2),
                (int)(icon.getIconHeight() * 1.2),
                Image.SCALE_SMOOTH);

        JButton btn = new JButton(new ImageIcon(scaled));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);

        return btn;
    }

    private void loadBackground() {
        try {
            ImageIcon raw = new ImageIcon(BACKGROUND);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

            bgImage = raw.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );
        } catch (Exception e) {
            System.out.println("Failed to load background: " + BACKGROUND);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}
