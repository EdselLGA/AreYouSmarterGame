package ui;

import Utils.Sound;
import java.awt.*;
import javax.swing.*;

public class SettingPanel extends JPanel {

    private final GameWindow parent;

    private static final String BACKGROUND = "assets/SettingBG.png";
    private static final String ICON_MUSIC = "assets/Music.png";
    private static final String ICON_SFX   = "assets/SDFX.png";
    private static final String BTN_BACK   = "assets/BACK.png";

    private Image bgImage;

    public SettingPanel(GameWindow parent) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setOpaque(false);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH
            );

        JPanel centerCol = new JPanel();
        centerCol.setLayout(new BoxLayout(centerCol, BoxLayout.Y_AXIS));
        centerCol.setOpaque(false);

        JLabel musicIcon = makeScaledIcon(ICON_MUSIC, 0.09);  
        JLabel sfxIcon   = makeScaledIcon(ICON_SFX,   0.09);

        JSlider musicSlider = makeLargeSlider(70);
        JSlider sfxSlider   = makeLargeSlider(70);

        centerCol.add(makeSettingRow(musicIcon, musicSlider));
        centerCol.add(Box.createRigidArea(new Dimension(0, 40)));
        centerCol.add(makeSettingRow(sfxIcon, sfxSlider));

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(250, 0, 0, 0));
        centerWrapper.add(centerCol, BorderLayout.NORTH);

        add(centerWrapper, BorderLayout.CENTER);

        JButton backBtn = makeImageButton(BTN_BACK);
        //lighten hover
        HelpersUI.addLightenOnHover(backBtn, 1.25f);
        //sfx hover
        HelpersUI.addHoverSFX(backBtn, "assets/Hover.wav");

        backBtn.addActionListener(e -> {
            parent.switchTo(GameWindow.CARD_MENUOPTIONS);
            HelpersUI.fadeInComponent(
                    parent.getScreen(GameWindow.CARD_MENUOPTIONS),
                    18, 0.06f, null
            );
            Sound.playSFX("assets/clicked.wav");
        });

        JPanel bottomLeftWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 20));
        bottomLeftWrapper.setOpaque(false);
        bottomLeftWrapper.add(backBtn);

        add(bottomLeftWrapper, BorderLayout.SOUTH);
    }


    private JSlider makeLargeSlider(int initialValue) {
        JSlider slider = new JSlider(0, 100, initialValue);

        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setFont(new Font("Arial", Font.BOLD, 18));
        slider.setForeground(Color.WHITE);
        slider.setOpaque(false);

        slider.setMaximumSize(new Dimension(600, 90));
        slider.setPreferredSize(new Dimension(600, 90));

        return slider;
    }

    private JPanel makeSettingRow(JLabel icon, JSlider slider) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);

        row.add(icon);
        row.add(Box.createRigidArea(new Dimension(55, 0)));
        row.add(slider);

        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        return row;
    }

    private JLabel makeScaledIcon(String path, double widthPercent) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon raw = new ImageIcon(path);
        int newWidth = (int) (screen.width * widthPercent);
        int newHeight = (int) ((double) raw.getIconHeight() / raw.getIconWidth() * newWidth);

        Image scaled = raw.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(scaled));
    }

    private JButton makeImageButton(String imagePath) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon rawIcon = new ImageIcon(imagePath);

        // 20% smaller than original
        int newWidth = (int) (screen.width * 0.16);
        int newHeight = (int) ((double) rawIcon.getIconHeight() /
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

        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}
