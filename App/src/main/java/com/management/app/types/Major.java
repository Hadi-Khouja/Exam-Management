package com.management.app.types;

/**
 * The Major class represents an academic major, containing information such as major ID , name,
 * and an associated ExamOfficeEmployee.
 */
public class Major {
    private final String name;
    private ExamOfficeEmployee employee;

    /**
     * Constructor for Major with only the name.
     *
     * @param name The name of the major.
     */
    public Major(String name) {
        this.name = name;
    }

    /**
     * Constructor for Major with major ID, name, and an associated ExamOfficeEmployee.
     *
     * @param name     The name of the major.
     * @param employee The ExamOfficeEmployee associated with the major.
     */
    public Major(String name, ExamOfficeEmployee employee) {
        this.name = name;
        this.employee = employee;
    }

    /**
     * Get the name of the major.
     *
     * @return The name of the major as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the associated ExamOfficeEmployee for the major.
     *
     * @return The associated ExamOfficeEmployee object.
     */
    public ExamOfficeEmployee getEmployee() {
        return employee;

    }

    /**
     * Set the ExamOfficeEmployee associated with the major.
     *
     * @param employee The ExamOfficeEmployee to be set.
     */
    public void setEmployee(ExamOfficeEmployee employee) {
        this.employee = employee;
    }
}
