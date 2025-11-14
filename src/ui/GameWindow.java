package ui;

import java.awt.*;
import javax.swing.*;

public class GameWindow extends JFrame {

    private CardLayout layout;
    private JPanel mainPanel;

    public static final String CARD_SPLASH = "splash";
    public static final String CARD_MAINMENU = "mainMenu";
    public static final String CARD_GAME = "gameScreen";

    public GameWindow() {

        super("Are You Smarter Than a 5th Grader?");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        SplashScreenPanel splash = new SplashScreenPanel(this);
        MainMenuPanel mainMenu = new MainMenuPanel(this);
        GameScreenPanel gameScreen = new GameScreenPanel(this);

        mainPanel.add(splash, CARD_SPLASH);
        mainPanel.add(mainMenu, CARD_MAINMENU);
        mainPanel.add(gameScreen, CARD_GAME);

        add(mainPanel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setVisible(true);

        layout.show(mainPanel, CARD_SPLASH);
    }

    public void switchTo(String cardName) {
        layout.show(mainPanel, cardName);
    }

    private Component findCardComponent(String cardName) {
        switch (cardName) {
            case CARD_SPLASH:
                return mainPanel.getComponent(0);
            case CARD_MAINMENU:
                return mainPanel.getComponent(1);
            case CARD_GAME:
                return mainPanel.getComponent(2);
            default:
                return null;
        }
    }

    public JComponent getScreen(String cardName) {
        Component c = findCardComponent(cardName);
        if (c instanceof JComponent) return (JComponent) c;
        return null;
    }
}
