package com.management.app.types;

import java.util.ArrayList;
import java.util.List;

/**
 * The Student class represents a student, extending the Person class.
 * It includes additional properties such as matriculation number and a list of majors.
 */
public class Student extends Person {
    private String matriculationNumber;
    private List<Major> majors;

    /**
     * Default constructor for Student.
     */
    public Student() {
    }

    /**
     * Constructor for Student with first name, last name, and matriculation number.
     *
     * @param firstName The first name of the student.
     * @param lastName The last name of the student.
     * @param matriculationNumber The matriculation number of the student.
     */
    public Student(String firstName, String lastName, String matriculationNumber) {
        super(firstName, lastName, "", "", "");
        this.matriculationNumber = matriculationNumber;
        this.majors = new ArrayList<>();
    }

    /**
     * Constructor for Student with first name, last name, matriculation number, and a single major.
     *
     * @param firstName The first name of the student.
     * @param lastName The last name of the student.
     * @param matriculationNumber The matriculation number of the student.
     * @param major The major associated with the student.
     */
    public Student(String firstName, String lastName, String matriculationNumber, Major major) {
        super(firstName, lastName, "", "", "");
        this.matriculationNumber = matriculationNumber;
        this.majors = new ArrayList<>();
        this.majors.add(major);
    }

    /**
     * Constructor for Student with detailed information, including email, phone, room, matriculation number, and a single major.
     *
     * @param firstName The first name of the student.
     * @param lastName The last name of the student.
     * @param emailAddress The email address of the student.
     * @param phoneNumber The phone number of the student.
     * @param room The room number of the student.
     * @param matriculationNumber The matriculation number of the student.
     * @param major The major associated with the student.
     */
    public Student(String firstName, String lastName, String emailAddress, String phoneNumber, String room, String matriculationNumber, Major major) {
        super(firstName, lastName, emailAddress, phoneNumber, room);
        this.matriculationNumber = matriculationNumber;
        this.majors = new ArrayList<>();
        this.majors.add(major);
    }

    /**
     * Get the matriculation number of the student.
     *
     * @return The matriculation number as a String
     */
    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    /**
     *  Get the list of majors associated with the student.
     *
     * @return The list of majors as a List<Major>.
     */
    public List<Major> getMajors() {
        return majors;
    }

    /**
     * Add a major to the list of majors associated with the student.
     *
     * @param major The major to be added.
     */
    public void addMajor(Major major) {
        majors.add(major);
    }

    /**
     * Add a list of majors to the existing list of majors associated with the student.
     *
     * @param majorList The list of majors to be added.
     */
    public void addMajors(List<Major> majorList) {
        majors.addAll(majorList);
    }
}
