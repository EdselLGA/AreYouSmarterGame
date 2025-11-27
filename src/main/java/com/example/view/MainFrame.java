package com.example.view;

import java.awt.CardLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.example.controller.NavigationController;
import com.example.controller.GameController;
import com.example.model.GameState;

/**
 * MainFrame - main application frame with CardLayout
 * Contains: SplashPanel, MainMenuPanel, GamePanel, MenuOptionsPanel,
 *           SettingsPanel, HowToPlayPanel, HighScoresPanel
 * Creates Controllers with Model and View (proper MVC)
 */
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Card names for main navigation panels
    public static final String CARD_SPLASH = "splash";
    public static final String CARD_MAINMENU = "mainMenu";
    public static final String CARD_GAME = "game";
    public static final String CARD_MENUOPTIONS = "menuOptions";
    public static final String CARD_HOWTOPLAY = "howToPlay";
    public static final String CARD_SETTINGS = "settings";
    public static final String CARD_HIGHSCORES = "highScores";

    // Controllers
    private NavigationController navigationController;
    private GameController gameController;

    // Models
    private GameState gameState;
    
    // Getter for GameController (used by NavigationController)
    public GameController getGameController() {
        return gameController;
    }
    
    // Getter for NavigationController (used by GameController)
    public NavigationController getNavigationController() {
        return navigationController;
    }

    // Main navigation panels
    private SplashPanel splashPanel;
    private MainMenuPanel mainMenuPanel;
    private GamePanel gamePanel;
    private MenuOptionsPanel menuOptionsPanel;
    private SettingsPanel settingsPanel;
    private HowToPlayPanel howToPlayPanel;
    private HighScoresPanel highScoresPanel;

    public MainFrame() {
        initializeFrame();
        initializeControllers();
        initializePanels();
        addPanelsToCardLayout();
        setInitialPanel();
    }

    private void initializeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
    }

    private void initializeControllers() {
        // Create NavigationController (implements NavigationListener)
        navigationController = new NavigationController(this);
        
        // Create Model
        gameState = new GameState();
        
        // Create View
        gamePanel = new GamePanel();
        
        // Create GameController with Model and View (proper MVC)
        gameController = new GameController(gameState, gamePanel);
        
        // Give GameController access to NavigationController for main menu navigation
        gameController.setNavigationController(navigationController);
    }

    private void initializePanels() {
        // Initialize main navigation panels (no controller references)
        splashPanel = new SplashPanel();
        mainMenuPanel = new MainMenuPanel();
        menuOptionsPanel = new MenuOptionsPanel();
        settingsPanel = new SettingsPanel();
        howToPlayPanel = new HowToPlayPanel();
        highScoresPanel = new HighScoresPanel();

        // Set listeners on panels (controllers subscribe to view events)
        splashPanel.setNavigationListener(navigationController);
        mainMenuPanel.setNavigationListener(navigationController);
        menuOptionsPanel.setNavigationListener(navigationController);
        settingsPanel.setNavigationListener(navigationController);
        howToPlayPanel.setNavigationListener(navigationController);
        highScoresPanel.setNavigationListener(navigationController);
        
        // GamePanel is already created in initializeControllers()
        // Controller is already wired up
    }

    private void addPanelsToCardLayout() {
        // Add all main navigation panels to CardLayout
        mainPanel.add(splashPanel, CARD_SPLASH);
        mainPanel.add(mainMenuPanel, CARD_MAINMENU);
        mainPanel.add(gamePanel, CARD_GAME);
        mainPanel.add(menuOptionsPanel, CARD_MENUOPTIONS);
        mainPanel.add(settingsPanel, CARD_SETTINGS);
        mainPanel.add(howToPlayPanel, CARD_HOWTOPLAY);
        mainPanel.add(highScoresPanel, CARD_HIGHSCORES);
    }

    private void setInitialPanel() {
        // Start with SplashPanel
        showPanel(CARD_SPLASH);
    }

    /**
     * Show a specific panel in the main CardLayout
     */
    public void showPanel(String screenName) {
        cardLayout.show(mainPanel, screenName);
    }

    /**
     * Add a panel to the main CardLayout (for future use if needed)
     */
    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }
}
