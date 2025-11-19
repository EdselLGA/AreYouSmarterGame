package ui;
import java.awt.*;
import javax.swing.*;

public class HowToPlayPanel extends JPanel {

    private final GameWindow parent;

    private static final String BACKGROUND =
            "assets/Background_3.png";
    private static final String HOWTOPLAY =
            "assets/HowToPlay.png";
    private static final String BTN_BACK =
            "assets/BACK.png";
    private Image bgImage;

    public HowToPlayPanel(GameWindow parent) {
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

        JLabel howToPlayLabel = makeScaledImage(HOWTOPLAY, 0.7);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        centerWrapper.add(howToPlayLabel, gbc);

        add(centerWrapper, BorderLayout.CENTER);

        JButton backBtn = makeImageButton(BTN_BACK);

        backBtn.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_MENUOPTIONS);
            JComponent menu = parent.getScreen(GameWindow.CARD_MENUOPTIONS);
            HelpersUI.fadeInComponent(menu, 18, 0.06f, null);
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

    private JLabel makeScaledImage(String path, double widthPercent) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon rawIcon = new ImageIcon(path);

        int newWidth = (int) (screen.width * widthPercent);
        int newHeight = (int) ((double) rawIcon.getIconHeight() /
            rawIcon.getIconWidth() * newWidth);

        Image scaled = rawIcon.getImage().getScaledInstance(
            newWidth, newHeight, Image.SCALE_SMOOTH
        );

        return new JLabel(new ImageIcon(scaled));
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
