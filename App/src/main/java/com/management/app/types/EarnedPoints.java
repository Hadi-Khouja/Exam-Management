package com.management.app.types;

import com.management.app.types.enums.ExamListCellType;

/**
 * Represents the points earned by a student for a specific task in an exam.
 */
public class EarnedPoints {
    /**
     * The student associated with the earned points.
     */
    private Student student;
    /**
     * The task for which the points were earned.
     */
    private Task task;
    private Exam exam;
    /**
     * The points earned by the student for the task.
     */
    private double points;
    private ExamListCellType listCellType;

    /**
     * Constructs an EarnedPoints object with the specified student and task.
     *
     * @param student The student associated with the earned points.
     * @param task    The task for which the points were earned.
     */
    public EarnedPoints(Exam exam, Student student, Task task) {
        this.exam = exam;
        this.student = student;
        this.task = task;
    }

    public EarnedPoints(Exam exam, Student student, Task task, ExamListCellType listCellType) {
        this.exam = exam;
        this.student = student;
        this.task = task;
        this.listCellType = listCellType;
    }

    /**
     * Constructs an EarnedPoints object with the specified student, task, and points.
     *
     * @param student The student associated with the earned points.
     * @param task    The task for which the points were earned.
     * @param points  The points earned by the student for the task.
     */
    public EarnedPoints(Exam exam, Student student, Task task, double points) {
        this.exam = exam;
        this.student = student;
        this.task = task;
        setPoints(points);
    }

    /**
     * Gets the student associated with the earned points.
     *
     * @return The student.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Sets the student associated with the earned points.
     *
     * @param student The student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Gets the task for which the points were earned.
     *
     * @return The task.
     */
    public Task getTask() {
        return task;
    }

    public Exam getExam() {
        return exam;
    }

    public ExamListCellType getListCellType() {
        return listCellType;
    }

    public void setListCellType(ExamListCellType listCellType) {
        this.listCellType = listCellType;
    }

    /**
     * Sets the task for which the points were earned.
     *
     * @param task The task to set.
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Gets the points earned by the student for the task.
     *
     * @return The points.
     */
    public double getPoints() {
        return points;
    }

    /**
     * Sets the points earned by the student for the task.
     * If the provided points exceed the maximum points for the task, sets points to 0.
     *
     * @param points The points to set .
     */
    public void setPoints(double points) {
        if (0 < points && points <= this.task.getPoints())
            this.points = points;
        else
            this.points = 0;
    }
}
