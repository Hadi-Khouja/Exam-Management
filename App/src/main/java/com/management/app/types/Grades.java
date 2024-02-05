package com.management.app.types;

/**
 * The Grades class represents information about a course, including the course name,
 * points obtained, grade received, and whether the course was passed.
 */
public class Grades {
    private String courseName;
    private double points;
    private String grade;
    private Student student;
    private Boolean passed;

    /**
     * Constructor to initialize a Grades object with course information.
     *
     * @param courseName The name of the course.
     * @param points     The points obtained in the course.
     * @param grade      The grade received in the course.
     * @param passed     A Boolean indicating whether the course was passed.
     */
    public Grades(Student student, String courseName, double points, String grade, Boolean passed) {
        this.student = student;
        this.courseName = courseName;
        this.points = points;
        this.grade = grade;
        this.passed = passed;
    }

    /**
     * Constructor to initialize a Grades object with course information.
     *
     * @param courseName The name of the course.
     * @param points     The points obtained in the course.
     * @param grade      The grade received in the course.
     * @param passed     A Boolean indicating whether the course was passed.
     */
    public Grades(String courseName, double points, String grade, Boolean passed) {
        this.courseName = courseName;
        this.points = points;
        this.grade = grade;
        this.passed = passed;
    }

    /**
     * Set the name of the course.
     *
     * @param courseName The name of the course to be set.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Set the points obtained in the course.
     *
     * @param points The points obtained to be set.
     */
    public void setPoints(double points) {
        this.points = points;
    }

    /**
     * Set the grade received in the course.
     *
     * @param grade The grade to be set.
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Set whether the course was passed or not.
     *
     * @param passed A Boolean indicating whether the course was passed.
     */
    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    /**
     * Get the name of the course.
     *
     * @return The name of the course as a String.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Get the points obtained in the course.
     *
     * @return The points obtained as a double.
     */
    public double getPoints() {
        return points;
    }

    /**
     * Get the grade received in the course.
     *
     * @return The grade as a String.
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Check whether the course was passed or not.
     *
     * @return true if the course was passed, false otherwise.
     */
    public Boolean getPassed() {
        return passed;
    }

    public Student getStudent() {
        return student;
    }
}

