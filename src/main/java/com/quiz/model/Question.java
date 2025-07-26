package com.quiz.model;

/**
 * Represents a quiz question with multiple choice answers
 * This class stores the question text, possible answers, and the correct answer index
 */
public class Question {
    private String question;
    private String[] answers;
    private int correctAnswerIndex;
    
    /**
     * Constructor to create a new question
     * @param question The question text
     * @param answers Array of possible answers (should be 4 options)
     * @param correctAnswerIndex Index of the correct answer (0-3)
     */
    public Question(String question, String[] answers, int correctAnswerIndex) {
        this.question = question;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }
    
    /**
     * Gets the question text
     * @return The question as a string
     */
    public String getQuestion() {
        return question;
    }
    
    /**
     * Gets all possible answers
     * @return Array of answer options
     */
    public String[] getAnswers() {
        return answers;
    }
    
    /**
     * Gets the index of the correct answer
     * @return Index of correct answer (0-3)
     */
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
    
    /**
     * Sets the question text
     * @param question New question text
     */
    public void setQuestion(String question) {
        this.question = question;
    }
    
    /**
     * Sets the answer options
     * @param answers New array of answers
     */
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
    
    /**
     * Sets the correct answer index
     * @param correctAnswerIndex New correct answer index
     */
    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
    
    /**
     * Checks if the given answer index is correct
     * @param answerIndex The index to check
     * @return true if the answer is correct, false otherwise
     */
    public boolean isCorrectAnswer(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }
    
    @Override
    public String toString() {
        return "Question: " + question;
    }
}
