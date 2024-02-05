package com.management.app.types;

import com.management.app.types.enums.AcademicGrade;

/**
 * The Lecturer class represents a user with a lecturer role, extending the User class.
 * It includes additional properties specific to lecturers, such as academic degree and research group.
 */
public class Lecturer extends User {
    private AcademicGrade academicDegree;
    private String researchGroup;

    /**
     * Default constructor for Lecturer.
     */
    public Lecturer() {
        super();
    }

    /**
     * Parameterized constructor for Lecturer with details about the lecturer and user.
     *
     * @param firstName The first name of the lecturer.
     * @param lastName The last name of the lecturer.
     * @param emailAddress The email address of the lecturer.
     * @param phoneNumber The phone number of the lecturer.
     * @param room The room number of the lecturer.
     * @param id  The ID of the lecturer.
     * @param userName The username of the lecturer.
     * @param password The password of the lecturer.
     * @param academicDegree The academic degree of the lecturer.
     * @param researchGroup The research group associated with the lecturer.
     */
    public Lecturer(String firstName, String lastName, String emailAddress, String phoneNumber, String room, String id, String userName, String password, AcademicGrade academicDegree, String researchGroup) {
        super(firstName, lastName, emailAddress, phoneNumber, room, id, userName, password);
        this.academicDegree = academicDegree;
        this.researchGroup = researchGroup;
    }

    /**
     * Get the full name with Academic Title of the lecturer.
     *
     * @return The Full Name with Academic Title as a String.
     */
    @Override
    public String getFullName() {
        String academicTitle = academicDegree == AcademicGrade.None
                ? ""
                : academicDegree.toString() + ". ";

        return academicTitle + super.getFullName();
    }

    /**
     * Get the academic degree of the lecturer.
     *
     * @return The academic degree as an AcademicGrade object.
     */
    public AcademicGrade getAcademicDegree() {
        return academicDegree;
    }

    /**
     * Get the research group associated with the lecturer.
     *
     * @return The research group as a String.
     */
    public String getResearchGroup() {
        return researchGroup;
    }
}