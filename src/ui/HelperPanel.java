package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Utils.Sound;


public class HelperPanel extends JPanel{
    private static final String BACKGROUND = "assets/splash.png";
    private Image bgImage;
    private GameScreenPanel parent;
    private static final String BTN_H1 = "assets/helper1.png";
    private static final String BTN_H2 = "assets/helper2.png";
    private static final String BTN_H3 = "assets/helper3.png";
    private static final String BTN_H4 = "assets/helper4.png";
    private static final String BTN_H5 = "assets/helper5.png";
    private JButton helper1Button;
    private JButton helper2Button;
    private JButton helper3Button;
    private JButton helper4Button;
    private JButton helper5Button;

    //private GameLogic gameLogic;

    public HelperPanel(GameScreenPanel parent /*  GameLogic gameLogic*/){

        this.parent = parent;
       // this.gameLogic = gameLogic;

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon bgIc = new ImageIcon(BACKGROUND);

        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);

        helper1Button = makeImageButton(BTN_H1);
        HelpersUI.addHoverSFX(helper1Button, "assets/Hover.wav");
        HelpersUI.addLightenOnHover(helper1Button, 1.25f);
        helper2Button = makeImageButton(BTN_H2);
        HelpersUI.addHoverSFX(helper2Button, "assets/Hover.wav");
        HelpersUI.addLightenOnHover(helper2Button, 1.25f);
        helper3Button = makeImageButton(BTN_H3);
        HelpersUI.addHoverSFX(helper3Button, "assets/Hover.wav");
        HelpersUI.addLightenOnHover(helper3Button, 1.25f);
        helper4Button = makeImageButton(BTN_H4);
        HelpersUI.addHoverSFX(helper4Button, "assets/Hover.wav");
        HelpersUI.addLightenOnHover(helper4Button, 1.25f);
        helper5Button = makeImageButton(BTN_H5);
        HelpersUI.addHoverSFX(helper5Button, "assets/Hover.wav");
        HelpersUI.addLightenOnHover(helper5Button, 1.25f);
            
        JPanel btnContainer = new JPanel();
        btnContainer.setOpaque(false);
        btnContainer.add(helper1Button);
        btnContainer.add(helper2Button);
        btnContainer.add(helper3Button);
        btnContainer.add(helper4Button);
        btnContainer.add(helper5Button);
        setLayout(new BorderLayout());
        add(btnContainer,BorderLayout.SOUTH);
        initializeButtonListeners();
    }

    public void refresh(){
        int usageCounters[] = parent.getModel().getHelperUsage();
        helper1Button.setEnabled(usageCounters[0]<2);
        helper2Button.setEnabled(usageCounters[1]<2);
        helper3Button.setEnabled(usageCounters[2]<2);
        helper4Button.setEnabled(usageCounters[3]<2);
        helper5Button.setEnabled(usageCounters[4]<2);
    }

    private void initializeButtonListeners(){
        helper1Button.addActionListener(e -> {
            parent.getGameLogic().selectHelper(0);
            parent.showCard("category");
        });
        helper2Button.addActionListener(e -> {
            parent.getGameLogic().selectHelper(1);
            parent.showCard("category");
        });
        helper3Button.addActionListener(e -> {
            parent.getGameLogic().selectHelper(2);
            parent.showCard("category");
        });
        helper4Button.addActionListener(e -> {
            parent.getGameLogic().selectHelper(3);
            parent.showCard("category");
        });
        helper5Button.addActionListener(e -> {
            parent.getGameLogic().selectHelper(4);
            parent.showCard("category");
        });
    }

    private JButton makeImageButton(String imagePath) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon rawIcon = new ImageIcon(imagePath);

        int newWidth = (int) (screen.width * 0.10);
        int newHeight = (int) ((double) rawIcon.getIconHeight() /
                rawIcon.getIconWidth() * newWidth);

        Image scaledImg = rawIcon.getImage().getScaledInstance(
                newWidth, newHeight, Image.SCALE_SMOOTH
        );

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
    }
}
