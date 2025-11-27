package ui;

import core.HighScoreRepository;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

//Fullscreen borderless Game Window with CardLayout.
public class GameWindow extends JFrame {

    private CardLayout layout;
    private JPanel mainPanel;
    private final Map<String, JComponent> screens = new HashMap<>();
    public static Font customFont;
    private HighScoreRepository highScoreRepository;

    public static final String CARD_SPLASH = "splash";
    public static final String CARD_MAINMENU = "mainMenu";
    public static final String CARD_GAME = "gameScreen";
    public static final String CARD_MENUOPTIONS = "menuOptions";
    public static final String CARD_HOWTOPLAY = "howToPlay";
    public static final String CARD_SETTINGS = "settings";
    public static final String CARD_HIGHSCORES = "highScores";


    public GameWindow() {
        super("Are You Smarter Than a 5th Grader?");

        // === FULLSCREEN MODE (Safe Borderless Fullscreen) ===
        setUndecorated(true);           // remove title bar
        setResizable(false);            // keep fixed
        setExtendedState(JFrame.MAXIMIZED_BOTH); // fullscreen on display
        loadCustomFont();
        layout = new CardLayout();
        mainPanel = new JPanel(layout);
        
        highScoreRepository = new HighScoreRepository();

        // create and register screens
        addCard(CARD_SPLASH, new SplashScreenPanel(this));
        addCard(CARD_MAINMENU, new MainMenuPanel(this));
        addCard(CARD_GAME, new GameScreenPanel(this));
        addCard(CARD_MENUOPTIONS, new MenuOptionsPanel(this));
        addCard(CARD_HOWTOPLAY, new HowToPlayPanel(this));
        addCard(CARD_SETTINGS, new SettingPanel(this));
        addCard(CARD_HIGHSCORES, new HighScorePanel(this));

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
    private void loadCustomFont() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("assets/Pixelette Regular.ttf"))
                                  .deriveFont(12f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
