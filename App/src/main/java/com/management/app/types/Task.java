package com.management.app.types;

import com.management.app.managemenetapp.Translator;

/**
 * The Task class represents a task with a number, points, and a question.
 * It includes methods to get and set points, number, and question.
 */
public class Task {
    private int number;
    private double points;
    private String question;

    /**
     * Constructor for Task with number, points, and question.
     *
     * @param number   The number of the task.
     * @param points   The points associated with the task.
     * @param question The question related to the task.
     */
    public Task(int number, double points, String question) {
        this.number = number;
        this.points = points;
        this.question = question;
    }

    /**
     * Constructor for Task with points and question.
     *
     * @param points   The points associated with the task.
     * @param question The question related to the task.
     */
    public Task(double points, String question) {
        this.points = points;
        this.question = question;
    }

    /**
     * Constructor for Task with points.
     *
     * @param points The points associated with the task.
     */
    public Task(double points) {
        this.points = points;
    }

    /**
     * Get the points associated with the task.
     *
     * @return The points as a double.
     */
    public double getPoints() {
        return points;
    }

    /**
     * Set the points associated with the task.
     *
     * @param points The points to be set.
     */
    public void setPoints(double points) {
        this.points = points;
    }

    /**
     * Get the number of the task.
     *
     * @return The number as an int.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set the number of the task.
     *
     * @param number The number to be set.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Get the question related to the task.
     *
     * @return The question as a String.
     */
    public String getQuestion() {
        return Translator.getInstance().translate("Task") + " " + number + " " + question;
    }

    public String getQuestionWithoutNumber() {
        return question;
    }

    /**
     * Set the question related to the task.
     *
     * @param question The question to be set.
     */
    public void setQuestion(String question) {
        this.question = question;
    }
}