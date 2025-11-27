package ui;

import Utils.Sound;
import core.Category;
import java.awt.*;
import javax.swing.*;

public class CategoryPanel extends JPanel {

    private final GameScreenPanel parent;

    private static final String BACKGROUND = "assets/ChooseSub_BG.png";

    private static final String BTN_HISTORY = "assets/History.png";
    private static final String BTN_GUESS = "assets/GuessthePara.png";
    private static final String BTN_TRUEFALSE = "assets/TrueFalse.png";
    private static final String BTN_SNIPPETS = "assets/CodeSnip.png";

    private static final String BTN_BACK = "assets/BACK.png";
    private static final String CHARLIE = "assets/charlie.png";

    private Image bgImage;
    private Image charlieImage;
    private JButton[] categoryButtons;

    public CategoryPanel(GameScreenPanel parent) {
        this.parent = parent;

        setOpaque(false);
        setLayout(new BorderLayout());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0) {
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);
        }

        // CHARLIE at 1.6x size
        ImageIcon charlieIcon = new ImageIcon(CHARLIE);
        charlieImage = charlieIcon.getImage().getScaledInstance(
                (int)(charlieIcon.getIconWidth() * 2),
                (int)(charlieIcon.getIconHeight() * 2),
                Image.SCALE_SMOOTH
        );

        // This panel centers the subject grid on the screen
        JPanel middleContainer = new JPanel(new GridBagLayout());
        middleContainer.setOpaque(false);

        middleContainer.setBorder(BorderFactory.createEmptyBorder(0, -160, -80, 0));

        // This panel contains the buttons (2×2)
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        categoryButtons = new JButton[4];
        categoryButtons[0] = makeLargeButton(BTN_HISTORY, "History");
        categoryButtons[1] = makeLargeButton(BTN_GUESS, "GuessTheParadigm");
        categoryButtons[2] = makeLargeButton(BTN_TRUEFALSE, "TrueOrFalse");
        categoryButtons[3] = makeLargeButton(BTN_SNIPPETS, "CodeSnippets");
        centerPanel.add(categoryButtons[0]);
        centerPanel.add(categoryButtons[1]);
        centerPanel.add(categoryButtons[2]);
        centerPanel.add(categoryButtons[3]);

        // Add centerPanel into the middleContainer
        middleContainer.add(centerPanel);

        // Add middleContainer to the screen CENTER
        add(middleContainer, BorderLayout.CENTER);

        
        // BOTTOM — BACK BUTTON
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        JButton backBtn = makeSmallButton(BTN_BACK, "BACK");
        HelpersUI.addLightenOnHover(backBtn, 1.25f);
        HelpersUI.addHoverSFX(backBtn, "assets/Hover.wav");
        
        backBtn.addActionListener(e -> {
            Sound.comingFromGame = true;
            parent.backToMenu();

            Sound.playSFX("assets/Clicked.wav");
            
        });

        bottom.add(backBtn, BorderLayout.WEST);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        add(bottom, BorderLayout.SOUTH);
    }

    private JButton makeLargeButton(String path, String actionName) {
        ImageIcon icon = new ImageIcon(path);
        Image scaled = icon.getImage().getScaledInstance(
                (int)(icon.getIconWidth() * 1.4),
                (int)(icon.getIconHeight() * 1.4),
                Image.SCALE_SMOOTH);

        JButton btn = new JButton(new ImageIcon(scaled));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);

        HelpersUI.addLightenOnHover(btn, 1.25f);

        btn.addActionListener(e -> {
            Sound.playSFX("assets/Clicked.wav");
            System.out.println("Pressed: " + actionName);
            switch (actionName) {
            case "History":
                parent.getGameLogic().selectCategory(0);
                break;
            case "GuessTheParadigm":
                parent.getGameLogic().selectCategory(1);
                break;
            case "TrueOrFalse":
                parent.getGameLogic().selectCategory(2);
                break;
            case "CodeSnippets":
                parent.getGameLogic().selectCategory(3);
                break;
            default:
                throw new IllegalArgumentException("Unknown category: " + actionName);
            }
            refresh();
            parent.onCategorySelected();
        });

        return btn;
    }

    private JButton makeSmallButton(String path, String actionName) {
        ImageIcon icon = new ImageIcon(path);
        Image scaled = icon.getImage().getScaledInstance(
                (int)(icon.getIconWidth() * 0.55),
                (int)(icon.getIconHeight() * 0.55),
                Image.SCALE_SMOOTH);

        JButton btn = new JButton(new ImageIcon(scaled));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);

        HelpersUI.addLightenOnHover(btn, 1.25f);

        btn.addActionListener(e ->
                Sound.playSFX("assets/Clicked.wav")
        );

        return btn;
    }

    public void refresh(){
        categoryButtons[0].setEnabled(parent.getModel().canUseCategory(Category.HISTORY));
        categoryButtons[1].setEnabled(parent.getModel().canUseCategory(Category.GUESS_THE_PARADIGM));
        categoryButtons[2].setEnabled(parent.getModel().canUseCategory(Category.TRUE_OR_FALSE));
        categoryButtons[3].setEnabled(parent.getModel().canUseCategory(Category.CODE_SNIPPETS));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);

        if (charlieImage != null) {
            int w = charlieImage.getWidth(null);
            int h = charlieImage.getHeight(null);

            g.drawImage(charlieImage,
                    getWidth() - w - 40,
                    getHeight() - h - 0,
                    this
            );
        }
    }
}
