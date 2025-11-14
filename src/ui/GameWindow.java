package ui;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * Fullscreen borderless Game Window with CardLayout.
 */
public class GameWindow extends JFrame {

    private CardLayout layout;
    private JPanel mainPanel;
    private final Map<String, JComponent> screens = new HashMap<>();

    public static final String CARD_SPLASH = "splash";
    public static final String CARD_MAINMENU = "mainMenu";
    public static final String CARD_GAME = "gameScreen";

    public GameWindow() {
        super("Are You Smarter Than a 5th Grader?");

        // === FULLSCREEN MODE (Safe Borderless Fullscreen) ===
        setUndecorated(true);           // remove title bar
        setResizable(false);            // keep fixed
        setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen on display

        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        // create and register screens
        addCard(CARD_SPLASH, new SplashScreenPanel(this));
        addCard(CARD_MAINMENU, new MainMenuPanel(this));
        addCard(CARD_GAME, new GameScreenPanel(this));

        getContentPane().add(mainPanel);

        // ESC closes game (optional)
        getRootPane().registerKeyboardAction(
                e -> System.exit(0),
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        setVisible(true);

        showCard(CARD_SPLASH);
    }

    private void addCard(String name, JComponent comp) {
        screens.put(name, comp);
        mainPanel.add(comp, name);
    }

    public void showCard(String name) {
        layout.show(mainPanel, name);
    }

    public void switchTo(String name) {
        showCard(name);
    }

    public JComponent getScreen(String name) {
        return screens.get(name);
    }
}
