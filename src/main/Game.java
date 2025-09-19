// Main entry point
import javax.swing.*;

public class Game {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Game().start();
        
        });

    }

    private JFrame mainFrame;
    //private Player player;
    //private QuestionManager questionManager;

    public void Game() {
        //player = new Player ("Player1");
        //questionManager = new QuestionManager();
    }

    public void start() {
        ShowMainmenu();
    }

    private void ShowMainmenu() {
        mainFrame = new JFrame("A million bytes");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainFrame.setSize(800, 600);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //mainFrame.setUndecorated(true);
        
        // MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        //mainFrame.setContentPane(mainMenuPanel);
        mainFrame.setVisible(true);
    }



    
   
}

