package ui;

import Utils.Sound;
import java.awt.*;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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
                    screen.width, screen.height, Image.SCALE_SMOOTH);

        JPanel centerCol = new JPanel();
        centerCol.setLayout(new BoxLayout(centerCol, BoxLayout.Y_AXIS));
        centerCol.setOpaque(false);

        JLabel musicIcon = makeScaledIcon(ICON_MUSIC, 0.09);
        JLabel sfxIcon   = makeScaledIcon(ICON_SFX,   0.09);

        // -------------------------------------------
        // SLIDERS (start at correct tick values)
        // -------------------------------------------
        JSlider musicSlider = makeVolumeSlider(getBGMTick());
        JSlider sfxSlider   = makeVolumeSlider(getSFXTick());

        // -------------------------------------------
        // BGM SLIDER LOGIC
        // -------------------------------------------
        musicSlider.addChangeListener(e -> {
            int tick = musicSlider.getValue();
            Sound.MenusVolume = mapBGMdB(tick);

            Clip clip = Sound.getBGMClip();
            if (clip != null) {
                try {
                    FloatControl gain =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gain.setValue(Sound.MenusVolume);
                } catch (Exception ignore) {}
            }
        });

        // -------------------------------------------
        // SFX SLIDER LOGIC
        // -------------------------------------------
        sfxSlider.addChangeListener(e -> {
            int tick = sfxSlider.getValue();
            Sound.SFXVolume = mapSFXdB(tick);

            Sound.playSFX("assets/Hover.wav"); // preview
        });

        // Layout rows
        centerCol.add(makeSettingRow(musicIcon, musicSlider));
        centerCol.add(Box.createRigidArea(new Dimension(0, 40)));
        centerCol.add(makeSettingRow(sfxIcon, sfxSlider));

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(250, 0, 0, 0));
        centerWrapper.add(centerCol, BorderLayout.NORTH);

        add(centerWrapper, BorderLayout.CENTER);

        // BACK BUTTON
        JButton backBtn = makeImageButton(BTN_BACK);
        HelpersUI.addLightenOnHover(backBtn, 1.25f);
        HelpersUI.addHoverSFX(backBtn, "assets/Hover.wav");

        backBtn.addActionListener(e -> {
            Sound.playSFX("assets/clicked.wav");

            parent.switchTo(GameWindow.CARD_MENUOPTIONS);

            HelpersUI.fadeInComponent(
                    parent.getScreen(GameWindow.CARD_MENUOPTIONS),
                    18, 0.06f,
                    null
            );
        });

        JPanel bottomLeftWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 20));
        bottomLeftWrapper.setOpaque(false);
        bottomLeftWrapper.add(backBtn);

        add(bottomLeftWrapper, BorderLayout.SOUTH);
    }


    /* ============================================================
                      SLIDER + dB MAPPING LOGIC
       ============================================================ */

    private JSlider makeVolumeSlider(int initialTick) {
        return new JSlider(0, 5, initialTick) {{
            setMajorTickSpacing(1);
            setPaintTicks(true);
            setPaintLabels(true);

            setFont(new Font("Arial", Font.BOLD, 22));
            setForeground(Color.WHITE);
            setOpaque(false);

            setMaximumSize(new Dimension(500, 90));
            setPreferredSize(new Dimension(500, 90));
        }};
    }

    // Map stored volume to slider tick
    private int getBGMTick() {
        float v = Sound.MenusVolume;

        if (v <= -60f) return 0;   // mute
        if (v <= -25f) return 1;   // default = TICK 1
        if (v <= -10f) return 2;
        if (v <= -5f)  return 3;
        if (v <= -2f)  return 4;
        return 5;
    }

    private int getSFXTick() {
        float v = Sound.SFXVolume;

        if (v <= -60f) return 0;
        if (v <= -20f) return 1;
        if (v <= -10f) return 2;
        if (v <= -6f)  return 3;
        if (v <= -3f)  return 4;
        return 5;
    }

    // Tick → dB
    private float mapBGMdB(int tick) {
        switch (tick) {
            case 0: return -80f;   // mute
            case 1: return -25f;   // DEFAULT START (TICK 1)
            case 2: return -10f;
            case 3: return -5f;
            case 4: return -2f;
            case 5: return 0f;
        }
        return -10f;
    }

    private float mapSFXdB(int tick) {
        switch (tick) {
            case 0: return -80f;
            case 1: return -20f;
            case 2: return -10f;
            case 3: return -6f;
            case 4: return -3f;
            case 5: return 0f;
        }
        return -10f;
    }

    /* ============================================================
                            UI HELPERS
       ============================================================ */

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
        int newHeight = (int) ((double) raw.getIconHeight() /
                raw.getIconWidth() * newWidth);

        Image scaled = raw.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(scaled));
    }

    private JButton makeImageButton(String imagePath) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon rawIcon = new ImageIcon(imagePath);

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
