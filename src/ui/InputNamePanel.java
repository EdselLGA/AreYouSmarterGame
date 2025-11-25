package ui;

import Utils.Sound;
import java.awt.*;
import javax.swing.*;
import player.Player;

public class InputNamePanel extends JPanel {

    private final GameWindow parent;

    private static final String BACKGROUND = "assets/splash.png";
    private static final String INPUTBG = "assets/NameInputFieldBG.png";
    private static final String BTN_BACK = "assets/BACK.png";
    private static final String BTN_CONTINUE = "assets/Continue.png";

    private Image bgImage;
    private boolean errorGlow = false;
    private JTextField nameField; // class-level field

    public InputNamePanel(GameWindow parent) {
        this.parent = parent;

        // background settings
        setLayout(new BorderLayout());
        setOpaque(false);

        // load background image
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0) {
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );
        }

        // main input background
        JLabel inputBgLabel = makeScaledImage(INPUTBG, 1.0);

        JPanel layered = new JPanel(null);
        layered.setOpaque(false);

        int bgWidth = inputBgLabel.getPreferredSize().width;
        int bgHeight = inputBgLabel.getPreferredSize().height;

        inputBgLabel.setBounds(0, 0, bgWidth, bgHeight);
        layered.add(inputBgLabel);

        // use class field (fix invisible bug)
        nameField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (errorGlow) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(new Color(255, 0, 0, 140));
                    g2.setStroke(new BasicStroke(10));
                    g2.drawRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 20, 20);
                }
            }
        };

        nameField.setBackground(Color.WHITE);
        nameField.setForeground(Color.BLACK);
        nameField.setFont(new Font("Arial", Font.BOLD, 48));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

        int fieldWidth = (int) (bgWidth * 0.70);
        int fieldHeight = 90;

        nameField.setBounds(
                (bgWidth - fieldWidth) / 2,
                (bgHeight / 2) - (fieldHeight / 2),
                fieldWidth,
                fieldHeight
        );

        layered.add(nameField);
        layered.setPreferredSize(new Dimension(bgWidth, bgHeight));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(100, 0, 0, 0);

        centerPanel.add(layered, gbc);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setOpaque(false);

        JButton backButton = makeImageButton(BTN_BACK);
        HelpersUI.addLightenOnHover(backButton, 1.25f);
        HelpersUI.addHoverSFX(backButton, "assets/Hover.wav");

        backButton.addActionListener(e -> {
            Sound.comingFromGame = true;
            parent.switchTo(GameWindow.CARD_MAINMENU);
            JComponent menu = parent.getScreen(GameWindow.CARD_MAINMENU);
            HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
            Sound.playSFX("assets/Clicked.wav");
        });

        JPanel bottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        bottomLeft.setOpaque(false);
        bottomLeft.add(backButton);

        JButton continueButton = makeImageButton(BTN_CONTINUE);
        HelpersUI.addLightenOnHover(continueButton, 1.25f);
        HelpersUI.addHoverSFX(continueButton, "assets/Hover.wav");

        continueButton.addActionListener(e -> {

            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                errorGlow = true;
                nameField.repaint();
                HelpersUI.showErrorAutoClose(this); // image-only popup
                return;
            }

            //save name to file 
            core.PlayerStorage.saveName(name);

            //create player instance
            Player p = new Player(name);
            parent.getGameLogic().setPlayer(p);

            //clear
            nameField.setText("");

            //reset error glow
            errorGlow = false;
            nameField.repaint();

            Sound.playSFX("assets/Clicked.wav");
            parent.switchTo(GameWindow.CARD_CURRENTWINNINGS);
        });

        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 30));
        bottomRight.setOpaque(false);
        bottomRight.add(continueButton);

        bottomBar.add(bottomLeft, BorderLayout.WEST);
        bottomBar.add(bottomRight, BorderLayout.EAST);

        add(bottomBar, BorderLayout.SOUTH);
    }

    private JButton makeImageButton(String imagePath) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon rawIcon = new ImageIcon(imagePath);
        int newWidth = (int) (screen.width * 0.14);
        int newHeight = rawIcon.getIconHeight() * newWidth / rawIcon.getIconWidth();

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

    private JLabel makeScaledImage(String path, double widthPercent) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon raw = new ImageIcon(path);

        int newWidth = (int) (screen.width * widthPercent);
        int newHeight = raw.getIconHeight() * newWidth / raw.getIconWidth();

        Image scaled = raw.getImage().getScaledInstance(
                newWidth, newHeight, Image.SCALE_SMOOTH
        );

        return new JLabel(new ImageIcon(scaled));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

