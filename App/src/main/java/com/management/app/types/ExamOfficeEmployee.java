package com.management.app.types;

/**
 * This class represents an Exam Office Employee, extending the Person class.
 */
public class ExamOfficeEmployee extends Person {
    private String employeeId;

    /**
     * Parameterized constructor for ExamOfficeEmployee.
     *
     * @param firstName    The first name of the employee.
     * @param lastName     The last name of the employee.
     * @param emailAddress The email address of the employee.
     * @param phoneNumber  The phone number of the employee.
     * @param room         The room location of the employee.
     */
    public ExamOfficeEmployee(String firstName, String lastName, String emailAddress, String phoneNumber, String room) {
        super(firstName, lastName, emailAddress, phoneNumber, room);
    }

    /**
     * Parameterized constructor for ExamOfficeEmployee.
     *
     * @param firstName    The first name of the employee.
     * @param lastName     The last name of the employee.
     * @param emailAddress The email address of the employee.
     * @param phoneNumber  The phone number of the employee.
     * @param room         The room location of the employee.
     * @param employeeId   The unique identifier for the employee.
     */
    public ExamOfficeEmployee(String firstName, String lastName, String emailAddress, String phoneNumber, String room, String employeeId) {
        super(firstName, lastName, emailAddress, phoneNumber, room);
        this.employeeId = employeeId;
    }

    /**
     * Get the unique identifier for the Exam Office Employee.
     *
     * @return The employeeId of the Exam Office Employee.
     */
    public String getEmployeeId() {
        return employeeId;
    }
}
