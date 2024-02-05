package com.management.app.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an academic course.
 */
public class Course {
    /**
     * The unique identifier for the course.
     */
    private String id;
    /**
     * The name of the course.
     */
    private String name;
    /**
     * The list of exams associated with the course.
     */
    private List<Exam> exams;
    private Lecturer owner;

    /**
     * Constructs a Course object with the specified name.
     *
     * @param name The name of the course.
     */
    public Course(String name) {
        this.name = name;
    }

    /**
     * Constructs a Course object with the specified id, name and Lecturer owner.
     *
     * @param id the id of the course
     * @param name The name of the course.
     * @param owner the Lecturer of this Course
     */
    public Course(String id, String name, Lecturer owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        exams = new ArrayList<>();
    }

    /**
     * Constructs a Course object with the specified ID and name.
     *
     * @param id The unique identifier for the course.
     * @param name The name of the course.
     */
    public Course(String id, String name) {
        this.id = id;
        this.name = name;
        exams = new ArrayList<>();
    }

    /**
     * Constructs a Course object with the specified ID, name, and list of exams.
     *
     * @param id The unique identifier for the course.
     * @param name The name of the course.
     * @param exams The list of exams associated with the course.
     */
    public Course(String id, String name, List<Exam> exams) {
        this.id = id;
        this.name = name;
        this.exams = exams;
    }

    /**
     * Gets the unique identifier for the course.
     *
     * @return The ID of the course.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the name of the course.
     *
     * @return The name of the course.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the list of exams associated with the course.
     *
     * @return The list of exams.
     */
    public List<Exam> getExams() {
        return exams;
    }

    /**
     * Sets the name of the course.
     *
     * @param name The name to set for the course.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the list of exams associated with the course.
     *
     * @param exams The list of exams to set.
     */
    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    /**
     * Adds a single exam to the list of exams associated with the course.
     *
     * @param exam The exam to add.
     */
    public void addExam(Exam exam) {
        this.exams.add(exam);
    }

    /**
     * Adds a list of exams to the existing list of exams associated with the course.
     *
     * @param exams The list of exams to add.
     */
    public void addExams(List<Exam> exams) {
        this.exams.addAll(exams);
    }

    public Lecturer getOwner() {
        return owner;
    }

    public void setOwner(Lecturer owner) {
        this.owner = owner;
    }
}
