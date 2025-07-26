package com.quiz.view;

import com.quiz.model.Question;
import com.quiz.service.QuizService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Quiz interface for users to take the quiz
 * This class provides the quiz-taking experience with questions, timer, and scoring
 */
public class QuizInterface extends JFrame {
    private QuizService quizService;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel questionLabel;
    private JRadioButton[] answerButtons = new JRadioButton[4];
    private ButtonGroup answerGroup = new ButtonGroup();
    private JLabel timerLabel;
    private JLabel progressLabel;
    private Timer quizTimer;
    private int timeLeft;
    private JButton nextButton;
    private JButton previousButton;
    private int[] userAnswers;
    
    /**
     * Constructor to initialize the quiz interface
     */
    public QuizInterface() {
        this.quizService = QuizService.getInstance();
        this.questions = quizService.getQuestions();
        this.userAnswers = new int[questions.size()];
        this.timeLeft = quizService.getCurrentQuiz().getTimeLimit();
        
        // Initialize user answers to -1 (no answer selected)
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }
        
        initializeComponents();
        startTimer();
        displayQuestion();
    }
    
    /**
     * Initializes all UI components
     */
    private void initializeComponents() {
        setTitle("Quiz Application - Take Quiz");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Create header panel with timer and progress
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        progressLabel = new JLabel("Question 1 of " + questions.size());
        progressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        progressLabel.setForeground(Color.WHITE);
        
        timerLabel = new JLabel("Time left: " + formatTime(timeLeft));
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setForeground(Color.WHITE);
        
        headerPanel.add(progressLabel, BorderLayout.WEST);
        headerPanel.add(timerLabel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Question panel
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Question"));
        
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        questionLabel.setVerticalAlignment(SwingConstants.TOP);
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        
        contentPanel.add(questionPanel, BorderLayout.NORTH);
        
        // Answer panel
        JPanel answerPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        answerPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Select your answer"));
        
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JRadioButton();
            answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            answerButtons[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            answerGroup.add(answerButtons[i]);
            answerPanel.add(answerButtons[i]);
            
            // Add action listener to save answer when selected
            final int answerIndex = i;
            answerButtons[i].addActionListener(e -> {
                userAnswers[currentQuestionIndex] = answerIndex;
            });
        }
        
        contentPanel.add(answerPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
        
        // Create navigation panel
        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        previousButton = new JButton("Previous");
        previousButton.setBackground(new Color(149, 165, 166));
        previousButton.setForeground(Color.WHITE);
        previousButton.setFont(new Font("Arial", Font.BOLD, 12));
        previousButton.setPreferredSize(new Dimension(100, 35));
        previousButton.setEnabled(false);
        
        nextButton = new JButton("Next");
        nextButton.setBackground(new Color(52, 152, 219));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Arial", Font.BOLD, 12));
        nextButton.setPreferredSize(new Dimension(100, 35));
        
        JButton finishButton = new JButton("Finish Quiz");
        finishButton.setBackground(new Color(231, 76, 60));
        finishButton.setForeground(Color.WHITE);
        finishButton.setFont(new Font("Arial", Font.BOLD, 12));
        finishButton.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(finishButton);
        
        navigationPanel.add(buttonPanel, BorderLayout.CENTER);
        add(navigationPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        setupActionListeners(previousButton, nextButton, finishButton);
    }
    
    /**
     * Sets up action listeners for navigation buttons
     */
    private void setupActionListeners(JButton previousButton, JButton nextButton, JButton finishButton) {
        previousButton.addActionListener(e -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion();
                updateNavigationButtons();
            }
        });
        
        nextButton.addActionListener(e -> {
            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                displayQuestion();
                updateNavigationButtons();
            }
        });
        
        finishButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to finish the quiz?\\nUnanswered questions will be marked as incorrect.",
                "Finish Quiz",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                finishQuiz();
            }
        });
    }
    
    /**
     * Updates the navigation buttons based on current question
     */
    private void updateNavigationButtons() {
        previousButton.setEnabled(currentQuestionIndex > 0);
        
        if (currentQuestionIndex == questions.size() - 1) {
            nextButton.setText("Last Question");
            nextButton.setEnabled(false);
        } else {
            nextButton.setText("Next");
            nextButton.setEnabled(true);
        }
    }
    
    /**
     * Displays the current question and its answers
     */
    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            
            // Update question text
            questionLabel.setText("<html><body style='width: 500px'>" + 
                                 question.getQuestion() + "</body></html>");
            
            // Update answer options
            String[] answers = question.getAnswers();
            for (int i = 0; i < 4; i++) {
                answerButtons[i].setText((char)('A' + i) + ". " + answers[i]);
            }
            
            // Restore previous answer if any
            answerGroup.clearSelection();
            if (userAnswers[currentQuestionIndex] != -1) {
                answerButtons[userAnswers[currentQuestionIndex]].setSelected(true);
            }
            
            // Update progress
            progressLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
        }
    }
    
    /**
     * Starts the quiz timer
     */
    private void startTimer() {
        quizTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + formatTime(timeLeft));
                
                // Change color when time is running low
                if (timeLeft <= 60) { // Last minute
                    timerLabel.setForeground(new Color(231, 76, 60)); // Red
                } else if (timeLeft <= 300) { // Last 5 minutes
                    timerLabel.setForeground(new Color(241, 196, 15)); // Yellow
                }
                
                if (timeLeft <= 0) {
                    quizTimer.stop();
                    JOptionPane.showMessageDialog(QuizInterface.this, 
                        "Time's up! The quiz will now be submitted.", 
                        "Time Up", 
                        JOptionPane.WARNING_MESSAGE);
                    finishQuiz();
                }
            }
        });
        quizTimer.start();
    }
    
    /**
     * Formats time in MM:SS format
     * @param seconds Time in seconds
     * @return Formatted time string
     */
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
    
    /**
     * Calculates the score and finishes the quiz
     */
    private void finishQuiz() {
        quizTimer.stop();
        
        // Calculate score
        score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i] != -1 && 
                questions.get(i).isCorrectAnswer(userAnswers[i])) {
                score++;
            }
        }
        
        // Show results
        showResults();
        dispose();
    }
    
    /**
     * Shows the quiz results
     */
    private void showResults() {
        String message = String.format(
            "Quiz Complete!\\n\\n" +
            "Your Score: %d out of %d\\n" +
            "Percentage: %.1f%%\\n\\n" +
            "Time taken: %s",
            score, 
            questions.size(),
            (score * 100.0 / questions.size()),
            formatTime(quizService.getCurrentQuiz().getTimeLimit() - timeLeft)
        );
        
        String title = "Quiz Results";
        int messageType = JOptionPane.INFORMATION_MESSAGE;
        
        // Determine message type based on score
        double percentage = (score * 100.0 / questions.size());
        if (percentage >= 80) {
            title = "Excellent!";
        } else if (percentage >= 60) {
            title = "Good Job!";
        } else {
            title = "Keep Practicing!";
            messageType = JOptionPane.WARNING_MESSAGE;
        }
        
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}
