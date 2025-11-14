package main;

import javax.swing.SwingUtilities;
import ui.GameWindow;

public class Game {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow());
    }
}

