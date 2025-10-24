// Main entry point
package main;
import javax.swing.SwingUtilities;
import ui.*;


public class Game {
    public static void main(String[] args) {
        // Initialize the game components
        SwingUtilities.invokeLater(() -> new SplashScreen().showSplash());

    }
}

