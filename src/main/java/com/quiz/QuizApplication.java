package com.quiz;

import com.quiz.view.AdminInterface;
import javax.swing.*;

/**
 * Main entry point for the Quiz Application
 * This class initializes and starts the application
 */
public class QuizApplication {
    
    public static void main(String[] args) {
        // Set look and feel for better UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            // Use default look and feel if system look and feel is not available
        }
        
        // Start the application with admin interface
        SwingUtilities.invokeLater(() -> {
            AdminInterface adminPanel = new AdminInterface();
            adminPanel.setVisible(true);
        });
    }
}
