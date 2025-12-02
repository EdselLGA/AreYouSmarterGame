package com.example.view;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 * EscapeKeyHandler - Handles ESC key press globally
 * Dispatches ESC key events to navigate back to main menu
 */
public class EscapeKeyHandler implements KeyEventDispatcher {
    private GameNavigationListener navigationListener;
    private boolean confirmBeforeExit;
    
    public EscapeKeyHandler(GameNavigationListener navigationListener) {
        this(navigationListener, true);
    }
    
    public EscapeKeyHandler(GameNavigationListener navigationListener, boolean confirmBeforeExit) {
        this.navigationListener = navigationListener;
        this.confirmBeforeExit = confirmBeforeExit;
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        // Only handle ESC key press (not release or typed)
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (navigationListener != null) {
                if (confirmBeforeExit) {
                    // Show confirmation dialog
                    int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to return to the main menu?\nYour current progress will be lost.",
                        "Exit to Main Menu",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                    
                    if (choice == JOptionPane.YES_OPTION) {
                        navigationListener.onNavigateToMainMenu();
                    }
                } else {
                    // Exit immediately without confirmation
                    navigationListener.onNavigateToMainMenu();
                }
            }
            return true; // Consume the event
        }
        return false; // Allow other key events to pass through
    }
    
    /**
     * Set whether to show confirmation dialog before exiting
     */
    public void setConfirmBeforeExit(boolean confirm) {
        this.confirmBeforeExit = confirm;
    }
    
    /**
     * Register this handler with the keyboard focus manager
     */
    public void register() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(this);
    }
    
    /**
     * Unregister this handler from the keyboard focus manager
     */
    public void unregister() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .removeKeyEventDispatcher(this);
    }
}