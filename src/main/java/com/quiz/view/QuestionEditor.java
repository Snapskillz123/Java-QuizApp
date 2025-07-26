package com.quiz.view;

import com.quiz.model.Question;
import com.quiz.service.QuizService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Question editor interface for adding and editing quiz questions
 * This class provides a form for creating/editing questions with multiple choice answers
 */
public class QuestionEditor extends JFrame {
    private JTextField questionField;
    private JTextField[] answerFields = new JTextField[4];
    private JRadioButton[] correctAnswerButtons = new JRadioButton[4];
    private ButtonGroup group = new ButtonGroup();
    private QuizService quizService;
    private AdminInterface parentInterface;
    private Question editingQuestion;
    private int editingIndex = -1;
    
    /**
     * Constructor for adding a new question
     * @param parent The parent AdminInterface
     */
    public QuestionEditor(AdminInterface parent) {
        this.parentInterface = parent;
        this.quizService = QuizService.getInstance();
        setupEditor("Add New Question");
    }
    
    /**
     * Constructor for editing an existing question
     * @param parent The parent AdminInterface
     * @param question The question to edit
     * @param index The index of the question being edited
     */
    public QuestionEditor(AdminInterface parent, Question question, int index) {
        this.parentInterface = parent;
        this.quizService = QuizService.getInstance();
        this.editingQuestion = question;
        this.editingIndex = index;
        setupEditor("Edit Question");
        populateFields();
    }
    
    /**
     * Sets up the editor interface
     * @param title The window title
     */
    private void setupEditor(String title) {
        setTitle(title);
        setSize(500, 400);
        setLocationRelativeTo(parentInterface);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 152, 219));
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Question field
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 0);
        
        JLabel questionLabel = new JLabel("Question:");\n        questionLabel.setFont(new Font("Arial", Font.BOLD, 12));
        contentPanel.add(questionLabel, gbc);
        
        gbc.gridy = 1;
        questionField = new JTextField("Enter question here...");
        questionField.setFont(new Font("Arial", Font.PLAIN, 12));
        questionField.setPreferredSize(new Dimension(400, 30));
        questionField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (questionField.getText().equals("Enter question here...")) {
                    questionField.setText("");
                }
            }
        });
        contentPanel.add(questionField, gbc);
        
        // Answer fields
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 0, 5, 5);
        
        for (int i = 0; i < 4; i++) {
            // Answer label
            gbc.gridx = 0; gbc.gridy = 2 + i;
            gbc.fill = GridBagConstraints.NONE;
            JLabel answerLabel = new JLabel("Answer " + (i + 1) + ":");
            answerLabel.setFont(new Font("Arial", Font.BOLD, 12));
            contentPanel.add(answerLabel, gbc);
            
            // Answer field
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            
            JPanel answerPanel = new JPanel(new BorderLayout(5, 0));
            answerFields[i] = new JTextField("Answer " + (i + 1));
            answerFields[i].setFont(new Font("Arial", Font.PLAIN, 12));
            answerFields[i].setPreferredSize(new Dimension(250, 25));
            
            final int index = i;
            answerFields[i].addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (answerFields[index].getText().equals("Answer " + (index + 1))) {
                        answerFields[index].setText("");
                    }
                }
            });
            
            correctAnswerButtons[i] = new JRadioButton("Correct");
            correctAnswerButtons[i].setFont(new Font("Arial", Font.PLAIN, 11));
            group.add(correctAnswerButtons[i]);
            
            answerPanel.add(answerFields[i], BorderLayout.CENTER);
            answerPanel.add(correctAnswerButtons[i], BorderLayout.EAST);
            contentPanel.add(answerPanel, gbc);
        }
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton saveButton = new JButton(editingQuestion != null ? "Update Question" : "Save Question");
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.setPreferredSize(new Dimension(150, 35));
        saveButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 12));
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setFocusPainted(false);
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        saveButton.addActionListener(e -> saveQuestion());
        cancelButton.addActionListener(e -> dispose());
        
        // Set first correct answer button as default
        if (editingQuestion == null) {
            correctAnswerButtons[0].setSelected(true);
        }
    }
    
    /**
     * Populates the fields when editing an existing question
     */
    private void populateFields() {
        if (editingQuestion != null) {
            questionField.setText(editingQuestion.getQuestion());
            String[] answers = editingQuestion.getAnswers();
            for (int i = 0; i < 4; i++) {
                answerFields[i].setText(answers[i]);
                if (i == editingQuestion.getCorrectAnswerIndex()) {
                    correctAnswerButtons[i].setSelected(true);
                }
            }
        }
    }
    
    /**
     * Saves the question (either adds new or updates existing)
     */
    private void saveQuestion() {
        try {
            String questionText = questionField.getText().trim();
            String[] answers = new String[4];
            int correctAnswerIndex = -1;
            
            // Validate and collect data
            if (questionText.isEmpty() || questionText.equals("Enter question here...")) {
                showError("Please enter a valid question.");
                return;
            }
            
            for (int i = 0; i < 4; i++) {
                answers[i] = answerFields[i].getText().trim();
                if (answers[i].isEmpty() || answers[i].equals("Answer " + (i + 1))) {
                    showError("Please enter a valid answer for option " + (i + 1) + ".");
                    return;
                }
                if (correctAnswerButtons[i].isSelected()) {
                    correctAnswerIndex = i;
                }
            }
            
            if (correctAnswerIndex == -1) {
                showError("Please select the correct answer.");
                return;
            }
            
            // Validate using service
            if (!quizService.validateQuestion(questionText, answers, correctAnswerIndex)) {
                showError("Question validation failed. Please check all fields.");
                return;
            }
            
            // Save or update question
            if (editingQuestion != null && editingIndex >= 0) {
                // Update existing question
                editingQuestion.setQuestion(questionText);
                editingQuestion.setAnswers(answers);
                editingQuestion.setCorrectAnswerIndex(correctAnswerIndex);
                showSuccess("Question updated successfully!");
            } else {
                // Add new question
                Question question = new Question(questionText, answers, correctAnswerIndex);
                quizService.addQuestion(question);
                showSuccess("Question saved successfully!");
            }
            
            // Update parent interface and close
            if (parentInterface != null) {
                parentInterface.updateQuestionCount(null);
            }
            dispose();
            
        } catch (Exception e) {
            showError("An error occurred while saving the question: " + e.getMessage());
        }
    }
    
    /**
     * Shows an error message
     * @param message The error message
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Shows a success message
     * @param message The success message
     */
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
