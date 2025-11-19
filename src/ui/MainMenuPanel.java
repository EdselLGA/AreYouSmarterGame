package ui;

import java.awt.*;
import javax.swing.*;

public class MainMenuPanel extends JPanel {

    private final GameWindow parent;

    private static final String BACKGROUND =
            "assets/splash.png";
    private static final String BTN_START =
            "assets/START.png";
    private static final String BTN_MENU =
            "assets/MENU.png";
    private static final String BTN_EXIT =
            "assets/EXIT.png";
    private static final String CLOUD =
            "assets/Cloud.png";

    private Image bgImage;
    private Image cloudImage;

    public MainMenuPanel(GameWindow parent) {
        this.parent = parent;

        setLayout(new GridBagLayout());
        setOpaque(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);

        ImageIcon cloudIc = new ImageIcon(CLOUD);
        if (cloudIc.getIconWidth() > 0) {
            int cloudWidth = (int) (screen.width * 0.30);    
            int cloudHeight = (int)((double) cloudIc.getIconHeight()/ 
                    cloudIc.getIconWidth() * cloudWidth);

            cloudImage = cloudIc.getImage().getScaledInstance(
                    cloudWidth, cloudHeight, Image.SCALE_SMOOTH
            );
        }
        
        JPanel menuBox = new JPanel();
        menuBox.setOpaque(false);
        menuBox.setLayout(new BoxLayout(menuBox, BoxLayout.Y_AXIS));

        JButton playBtn = makeImageButton(BTN_START);
        JButton menuBtn = makeImageButton(BTN_MENU);
        JButton exitBtn = makeImageButton(BTN_EXIT);

        playBtn.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_GAME);
            JComponent game = parent.getScreen(GameWindow.CARD_GAME);
            HelpersUI.fadeInComponent(game, 18, 0.06f, null);
        });

        menuBtn.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_MENUOPTIONS);
            JComponent menu = parent.getScreen(GameWindow.CARD_MENUOPTIONS);
            HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
        });

        exitBtn.addActionListener(e -> System.exit(0));

        menuBox.add(playBtn);
        menuBox.add(Box.createRigidArea(new Dimension(0, 20)));
        menuBox.add(menuBtn);
        menuBox.add(Box.createRigidArea(new Dimension(0, 20)));
        menuBox.add(exitBtn);

        add(menuBox);
    }

    private JButton makeImageButton(String imagePath) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon rawIcon = new ImageIcon(imagePath);

        int newWidth = (int) (screen.width * 0.20);
        int newHeight = (int) ((double) rawIcon.getIconHeight() /
                rawIcon.getIconWidth() * newWidth);

        Image scaledImg = rawIcon.getImage().getScaledInstance(
                newWidth, newHeight, Image.SCALE_SMOOTH);

        JButton btn = new JButton(new ImageIcon(scaledImg));

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
        else {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (cloudImage != null) {
            int cloudW = cloudImage.getWidth(this);
            int cloudH = cloudImage.getHeight(this);


            int leftX = (int) (getWidth() * 0.03);   
            int leftY = (int) (getHeight() * 0.05);  

            int rightX = getWidth() - cloudW - (int)(getWidth() * 0.03);
            int rightY = (int) (getHeight() * 0.09); 

            g.drawImage(cloudImage, leftX, leftY, this);
            g.drawImage(cloudImage, rightX, rightY, this);
        }
    }
}

