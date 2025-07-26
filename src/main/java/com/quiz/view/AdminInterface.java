package com.quiz.view;

import com.quiz.service.AuthenticationService;
import com.quiz.service.QuizService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Admin interface for managing quiz questions and starting quizzes
 * This class provides the main admin panel with CRUD operations for questions
 */
public class AdminInterface extends JFrame {
    private QuizService quizService;
    private AuthenticationService authService;
    
    /**
     * Constructor to initialize the admin interface
     */
    public AdminInterface() {
        this.quizService = QuizService.getInstance();
        this.authService = AuthenticationService.getInstance();
        initializeComponents();
    }
    
    /**
     * Initializes all UI components and sets up the interface
     */
    private void initializeComponents() {
        setTitle("Quiz Application - Admin Panel");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        JLabel titleLabel = new JLabel("Quiz Admin Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Create main button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton addButton = createStyledButton("Add Question", new Color(46, 204, 113));
        JButton editButton = createStyledButton("Edit Question", new Color(241, 196, 15));
        JButton deleteButton = createStyledButton("Delete Question", new Color(231, 76, 60));
        JButton startQuizButton = createStyledButton("Start Quiz", new Color(155, 89, 182));
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(startQuizButton);
        
        add(buttonPanel, BorderLayout.CENTER);
        
        // Create footer panel with question count
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(236, 240, 241));
        JLabel questionCountLabel = new JLabel("Questions in database: " + quizService.getQuestionCount());
        questionCountLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        footerPanel.add(questionCountLabel);
        add(footerPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        setupActionListeners(addButton, editButton, deleteButton, startQuizButton, questionCountLabel);
    }
    
    /**
     * Creates a styled button with the specified text and color
     * @param text Button text
     * @param color Button background color
     * @return Styled JButton
     */
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 50));
        return button;
    }
    
    /**
     * Sets up action listeners for all buttons
     */
    private void setupActionListeners(JButton addButton, JButton editButton, 
                                    JButton deleteButton, JButton startQuizButton, 
                                    JLabel questionCountLabel) {
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (promptForPassword()) {
                    QuestionEditor editor = new QuestionEditor(AdminInterface.this);
                    editor.setVisible(true);
                    // Update question count after editor is closed
                    updateQuestionCount(questionCountLabel);
                }
            }
        });
        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (promptForPassword()) {
                    if (quizService.hasQuestions()) {
                        // Show question selection dialog
                        int questionIndex = showQuestionSelectionDialog();
                        if (questionIndex >= 0) {
                            QuestionEditor editor = new QuestionEditor(
                                AdminInterface.this, 
                                quizService.getQuestion(questionIndex), 
                                questionIndex
                            );
                            editor.setVisible(true);
                            updateQuestionCount(questionCountLabel);
                        }
                    } else {
                        showMessage("No questions to edit!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (promptForPassword()) {
                    if (quizService.hasQuestions()) {
                        int questionIndex = showQuestionSelectionDialog();
                        if (questionIndex >= 0) {
                            int confirm = JOptionPane.showConfirmDialog(
                                AdminInterface.this,
                                "Are you sure you want to delete this question?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION
                            );
                            if (confirm == JOptionPane.YES_OPTION) {
                                quizService.removeQuestion(questionIndex);
                                showMessage("Question deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                updateQuestionCount(questionCountLabel);
                            }
                        }
                    } else {
                        showMessage("No questions to delete!", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        
        startQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quizService.hasQuestions()) {
                    QuizInterface quiz = new QuizInterface();
                    quiz.setVisible(true);
                } else {
                    showMessage("Please add some questions before starting the quiz!", 
                              "No Questions", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    
    /**
     * Prompts user for admin password
     * @return true if correct password is entered
     */
    private boolean promptForPassword() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        
        int option = JOptionPane.showConfirmDialog(
            this, 
            passwordField,
            "Enter Admin Password", 
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (option == JOptionPane.OK_OPTION) {
            String enteredPassword = new String(passwordField.getPassword());
            boolean isValid = authService.verifyAdminPassword(enteredPassword);
            if (!isValid) {
                showMessage("Incorrect password!", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
            }
            return isValid;
        }
        return false;
    }
    
    /**
     * Shows a dialog to select which question to edit/delete
     * @return Index of selected question, or -1 if cancelled
     */
    private int showQuestionSelectionDialog() {
        String[] questionTitles = new String[quizService.getQuestionCount()];
        for (int i = 0; i < questionTitles.length; i++) {
            String questionText = quizService.getQuestion(i).getQuestion();
            questionTitles[i] = (i + 1) + ". " + (questionText.length() > 50 ? 
                questionText.substring(0, 50) + "..." : questionText);
        }
        
        String selectedQuestion = (String) JOptionPane.showInputDialog(
            this,
            "Select a question:",
            "Question Selection",
            JOptionPane.QUESTION_MESSAGE,
            null,
            questionTitles,
            questionTitles[0]
        );
        
        if (selectedQuestion != null) {
            for (int i = 0; i < questionTitles.length; i++) {
                if (questionTitles[i].equals(selectedQuestion)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    /**
     * Updates the question count label
     * @param label The label to update
     */
    public void updateQuestionCount(JLabel label) {
        SwingUtilities.invokeLater(() -> {
            label.setText("Questions in database: " + quizService.getQuestionCount());
        });
    }
    
    /**
     * Shows a message dialog
     * @param message Message to display
     * @param title Dialog title
     * @param messageType Type of message (INFO, WARNING, ERROR)
     */
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
