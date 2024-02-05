package com.management.app.types;

import com.management.app.types.enums.BonusType;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an exam in the educational system.
 */
public class Exam {
    private String examId;
    private String name;
    private Course associatedCourse;
    private LocalDate date;
    private List<GradeLimits> gradeLimits;
    private double passingLimit;
    private List<Task> tasks;
    private double maximumPoints;
    private BonusType recordBonus;
    private List<Student> participatingStudents;

    public Exam(String name) {
        this.name = name;
    }

    /**
     * Constructs an Exam object with the specified name and associated course.
     *
     * @param name             The name of the exam.
     * @param associatedCourse The course associated with the exam.
     */
    public Exam(String name, Course associatedCourse) {
        this.name = name;
        this.associatedCourse = associatedCourse;
    }

    /**
     * Constructs an Exam object with detailed information.
     *
     * @param name             The name of the exam.
     * @param associatedCourse The course associated with the exam.
     * @param date             The date of the exam.
     * @param gradeLimits      The grade limits for the exam.
     * @param passingLimit     The passing limit for the exam.
     * @param maximumPoints    The maximum points achievable in the exam.
     * @param recordBonus      The type of bonus record for the exam.
     */
    public Exam(String name, Course associatedCourse, LocalDate date, List<GradeLimits> gradeLimits, double passingLimit, double maximumPoints, BonusType recordBonus) {
        this.name = name;
        this.associatedCourse = associatedCourse;
        this.date = date;
        this.gradeLimits = gradeLimits;
        this.passingLimit = passingLimit;
        this.maximumPoints = maximumPoints;
        this.recordBonus = recordBonus;
    }

    /**
     * Constructs an Exam object with detailed information.
     *
     * @param examId                The unique identifier for the exam.
     * @param name                  The name of the exam.
     * @param associatedCourse      The course associated with the exam.
     * @param date                  The date of the exam.
     * @param gradeLimits           The grade limits for the exam.
     * @param passingLimit          The passing limit for the exam.
     * @param tasks                 The tasks associated with the exam.
     * @param maximumPoints         The maximum points achievable in the exam.
     * @param recordBonus           The type of bonus record for the exam.
     * @param participatingStudents The students participating in the exam.
     */
    public Exam(String examId, String name, Course associatedCourse, LocalDate date, List<GradeLimits> gradeLimits, double passingLimit, List<Task> tasks, double maximumPoints, BonusType recordBonus, List<Student> participatingStudents) {
        this.examId = examId;
        this.name = name;
        this.associatedCourse = associatedCourse;
        this.date = date;
        this.gradeLimits = gradeLimits;
        this.passingLimit = passingLimit;
        this.tasks = tasks;
        this.maximumPoints = maximumPoints;
        this.recordBonus = recordBonus;
        this.participatingStudents = participatingStudents;
    }

    /**
     * Get the name of the exam.
     *
     * @return the name of the exam.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the unique identifier for the exam.
     *
     * @return the identifier for the exam.
     */
    public String getExamId() {
        return examId;
    }

    /**
     * Get the associated course for the exam.
     *
     * @return the associated course for the exam
     */
    public Course getAssociatedCourse() {
        return associatedCourse;
    }

    /**
     * Get the date of the exam.
     *
     * @return the date of the exam.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get the list of grade limits for the exam.
     *
     * @return the list of grade limits for the exam.
     */
    public List<GradeLimits> getGradeLimits() {
        return gradeLimits;
    }

    /**
     * Get the passing limit for the exam.
     *
     * @return the passing limit for the exam.
     */
    public double getPassingLimit() {
        return passingLimit;
    }

    /**
     * Get the list of tasks associated with the exam.
     *
     * @return the list of tasks associated with the exam.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Get the number of tasks in the exam.
     *
     * @return the number of tasks in the exam.
     */
    public int getNumberOfTasks() {
        return this.tasks.size();
    }

    /**
     * Get the maximum points possible for the exam.
     *
     * @return the maximum points possible for the exam.
     */
    public double getMaximumPoints() {
        return maximumPoints;
    }

    /**
     * Get the bonus type associated with the exam.
     *
     * @return the bonus type associated with the exam.
     */
    public BonusType getRecordBonus() {
        return recordBonus;
    }

    /**
     * Get the count of registered participating students for the exam.
     *
     * @return the count of registered participating students for the exam.
     */
    public int getRegisteredParticipantsCount() {
        return this.participatingStudents.size();
    }

    /**
     * Get the list of participating students in the exam.
     *
     * @return the list of participating students in the exam.
     */
    public List<Student> getParticipatingStudents() {
        return participatingStudents;
    }

    /**
     * Set the name of the exam.
     *
     * @param name the name of the exam
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the associated course for the exam.
     *
     * @param associatedCourse the associated course for the exam
     */
    public void setAssociatedCourse(Course associatedCourse) {
        this.associatedCourse = associatedCourse;
    }

    /**
     * Set the date of the exam.
     *
     * @param date the date of the exam
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Set the list of grade limits for the exam.
     *
     * @param gradeLimits the list of grade limits for the exam.
     */
    public void setGradeLimits(List<GradeLimits> gradeLimits) {
        this.gradeLimits = gradeLimits;
    }

    /**
     * Set the passing limit for the exam.
     *
     * @param passingLimit the passing limit for the exam.
     */
    public void setPassingLimit(double passingLimit) {
        this.passingLimit = passingLimit;
    }

    /**
     * Set the list of tasks associated with the exam.
     *
     * @param tasks the list of tasks associated with the exam.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Set the maximum points possible for the exam.
     *
     * @param maximumPoints the maximum points possible for the exam.
     */
    public void setMaximumPoints(double maximumPoints) {
        this.maximumPoints = maximumPoints;
    }

    /**
     * Set the bonus type associated with the exam
     *
     * @param recordBonus associated with the exam
     */
    public void setRecordBonus(BonusType recordBonus) {
        this.recordBonus = recordBonus;
    }

    /**
     * Set the list of participating students in the exam.
     *
     * @param participatingStudents the list of participating students in the exam
     */
    public void setParticipatingStudents(List<Student> participatingStudents) {
        this.participatingStudents = participatingStudents;
    }

    /**
     * Add a single task to the list of tasks for the exam.
     *
     * @param task a single task to the list for the exam.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Add a list of tasks to the existing tasks for the exam.
     *
     * @param tasks a list of tasks to the existing tasks for the exam
     */
    public void addTasks(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Add a single student to the list of participating students for the exam.
     *
     * @param student the student to the list of participating students for the exam.
     */
    public void addStudent(Student student) {
        this.participatingStudents.add(student);
    }

    /**
     * Add a list of students to the existing participating students for the exam.
     *
     * @param students a list of students to the existing participating students for the exam.
     */
    public void addStudents(List<Student> students) {
        this.participatingStudents.addAll(students);
    }

    /**
     * Add a single grade limit to the list of grade limits for the exam.
     *
     * @param limit thw grade limit for the exam.
     */
    public void addGradeLimit(GradeLimits limit) {
        this.gradeLimits.add(limit);
    }

    /**
     * Add a list of grade limits to the existing grade limits for the exam.
     *
     * @param limits the grade limits for the exam.
     */
    public void addGradeLimits(List<GradeLimits> limits) {
        this.gradeLimits.addAll(limits);
    }
}
