package ui;

import core.GameLogic;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

// Fullscreen borderless Game Window with CardLayout.
public class GameWindow extends JFrame {

    private CardLayout layout;
    private JPanel mainPanel;
    private final Map<String, JComponent> screens = new HashMap<>();

    private GameLogic gameLogic = new GameLogic();
    public static final String CARD_SPLASH = "splash";
    public static final String CARD_MAINMENU = "mainMenu";
    public static final String CARD_GAME = "gameScreen";        // Subject Selection Panel
    public static final String CARD_MENUOPTIONS = "menuOptions";
    public static final String CARD_HOWTOPLAY = "howToPlay";
    public static final String CARD_SETTINGS = "settings";
    public static final String CARD_HIGHSCORES = "highScores";
    public static final String CARD_NAMEINPUT = "nameInput";    // <-- fixed proper naming

    public GameWindow() {
        super("Are You Smarter Than a 5th Grader?");

        // === FULLSCREEN MODE ===
        setUndecorated(true);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        // Register all screens
        addCard(CARD_SPLASH, new SplashScreenPanel(this));
        addCard(CARD_MAINMENU, new MainMenuPanel(this));
        addCard(CARD_GAME, new GameScreenPanel(this));
        addCard(CARD_MENUOPTIONS, new MenuOptionsPanel(this));
        addCard(CARD_HOWTOPLAY, new HowToPlayPanel(this));
        addCard(CARD_SETTINGS, new SettingPanel(this));
        addCard(CARD_HIGHSCORES, new HighScorePanel(this));
        addCard(CARD_NAMEINPUT, new NameInputPanel(this));   // <-- new panel added here

        getContentPane().add(mainPanel);

        // ESC to exit
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

    public GameLogic getGameLogic() {
        return gameLogic;
    }
}

