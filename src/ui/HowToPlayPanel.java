package ui;
import java.awt.*;
import javax.swing.*;

public class HowToPlayPanel extends JPanel {

    private final GameWindow parent;

    private static final String BACKGROUND =
            "C:\\Users\\edsel\\AreYouSmarterGame-3\\assets\\Background_3.png";
    private static final String HOWTOPLAY =
           "C:\\Users\\edsel\\AreYouSmarterGame-3\\assets\\HowToPlay.png";
    private static final String BTN_BACK =
            "C:\\Users\\edsel\\AreYouSmarterGame-3\\assets\\BACK.png";
    private Image bgImage; 

    public HowToPlayPanel(GameWindow parent) {
        this.parent = parent;

        setLayout(new GridBagLayout());
        setOpaque(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        JButton backBtn = makeImageButton(BTN_BACK);



        backBtn.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_MENUOPTIONS);
            JComponent menu = parent.getScreen(GameWindow.CARD_MENUOPTIONS);
            HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
        });

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );

        
        
        JLabel howToPlayLabel = new JLabel(new ImageIcon(HOWTOPLAY));
        add(howToPlayLabel);
        add(backBtn);

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
        else {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

}
