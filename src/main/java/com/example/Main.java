package com.example;

import javax.swing.SwingUtilities;
import com.example.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            }
        );
    }
}