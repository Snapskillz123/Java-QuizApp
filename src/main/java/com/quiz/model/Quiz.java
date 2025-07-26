package com.quiz.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a complete quiz with multiple questions
 * This class manages a collection of questions and quiz metadata
 */
public class Quiz {
    private String title;
    private List<Question> questions;
    private int timeLimit; // in seconds
    
    /**
     * Default constructor creates an empty quiz
     */
    public Quiz() {
        this.questions = new ArrayList<>();
        this.title = "Untitled Quiz";
        this.timeLimit = 60; // Default 1 minute per quiz
    }
    
    /**
     * Constructor with title
     * @param title The title of the quiz
     */
    public Quiz(String title) {
        this();
        this.title = title;
    }
    
    /**
     * Constructor with title and time limit
     * @param title The title of the quiz
     * @param timeLimit Time limit in seconds
     */
    public Quiz(String title, int timeLimit) {
        this(title);
        this.timeLimit = timeLimit;
    }
    
    /**
     * Adds a question to the quiz
     * @param question The question to add
     */
    public void addQuestion(Question question) {
        questions.add(question);
    }
    
    /**
     * Removes a question from the quiz
     * @param index The index of the question to remove
     * @return true if question was removed, false if index is invalid
     */
    public boolean removeQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            questions.remove(index);
            return true;
        }
        return false;
    }
    
    /**
     * Gets a question by index
     * @param index The index of the question
     * @return The question at the specified index, or null if invalid index
     */
    public Question getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }
    
    /**
     * Gets all questions in the quiz
     * @return List of all questions
     */
    public List<Question> getQuestions() {
        return new ArrayList<>(questions); // Return a copy to prevent external modification
    }
    
    /**
     * Gets the number of questions in the quiz
     * @return Number of questions
     */
    public int getQuestionCount() {
        return questions.size();
    }
    
    /**
     * Checks if the quiz has any questions
     * @return true if quiz is empty, false otherwise
     */
    public boolean isEmpty() {
        return questions.isEmpty();
    }
    
    /**
     * Gets the quiz title
     * @return The title of the quiz
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the quiz title
     * @param title New title for the quiz
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Gets the time limit for the quiz
     * @return Time limit in seconds
     */
    public int getTimeLimit() {
        return timeLimit;
    }
    
    /**
     * Sets the time limit for the quiz
     * @param timeLimit New time limit in seconds
     */
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
}
