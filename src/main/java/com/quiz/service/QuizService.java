package com.quiz.service;

import com.quiz.model.Question;
import com.quiz.model.Quiz;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing quiz operations
 * This class handles business logic for quiz management
 */
public class QuizService {
    private static QuizService instance;
    private Quiz currentQuiz;
    
    /**
     * Private constructor for singleton pattern
     */
    private QuizService() {
        this.currentQuiz = new Quiz("Default Quiz");
    }
    
    /**
     * Gets the singleton instance of QuizService
     * @return The QuizService instance
     */
    public static QuizService getInstance() {
        if (instance == null) {
            instance = new QuizService();
        }
        return instance;
    }
    
    /**
     * Gets the current quiz
     * @return The current quiz
     */
    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }
    
    /**
     * Sets the current quiz
     * @param quiz The quiz to set as current
     */
    public void setCurrentQuiz(Quiz quiz) {
        this.currentQuiz = quiz;
    }
    
    /**
     * Adds a question to the current quiz
     * @param question The question to add
     */
    public void addQuestion(Question question) {
        currentQuiz.addQuestion(question);
    }
    
    /**
     * Removes a question from the current quiz
     * @param index The index of the question to remove
     * @return true if question was removed successfully
     */
    public boolean removeQuestion(int index) {
        return currentQuiz.removeQuestion(index);
    }
    
    /**
     * Gets all questions from the current quiz
     * @return List of questions
     */
    public List<Question> getQuestions() {
        return currentQuiz.getQuestions();
    }
    
    /**
     * Gets a specific question by index
     * @param index The index of the question
     * @return The question at the specified index
     */
    public Question getQuestion(int index) {
        return currentQuiz.getQuestion(index);
    }
    
    /**
     * Gets the number of questions in the current quiz
     * @return Number of questions
     */
    public int getQuestionCount() {
        return currentQuiz.getQuestionCount();
    }
    
    /**
     * Checks if the current quiz has any questions
     * @return true if quiz is empty
     */
    public boolean hasQuestions() {
        return !currentQuiz.isEmpty();
    }
    
    /**
     * Creates a new empty quiz
     * @param title The title for the new quiz
     */
    public void createNewQuiz(String title) {
        this.currentQuiz = new Quiz(title);
    }
    
    /**
     * Validates a question before adding it to the quiz
     * @param question The question text
     * @param answers Array of answers
     * @param correctAnswerIndex Index of correct answer
     * @return true if question is valid
     */
    public boolean validateQuestion(String question, String[] answers, int correctAnswerIndex) {
        if (question == null || question.trim().isEmpty()) {
            return false;
        }
        
        if (answers == null || answers.length != 4) {
            return false;
        }
        
        for (String answer : answers) {
            if (answer == null || answer.trim().isEmpty()) {
                return false;
            }
        }
        
        return correctAnswerIndex >= 0 && correctAnswerIndex < 4;
    }
}
