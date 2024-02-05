package com.management.app.types;

/**
 * The Person class is an abstract class representing a generic person with basic information.
 * It includes properties such as first name, last name, email address, phone number, and room.
 */
public abstract class Person {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String room;

    /**
     * Default constructor for Person.
     */
    public Person() {

    }

    /**
     *Parameterized constructor for Person with details about the person.
     *
     * @param firstName The first name of the person.
     * @param lastName The last name of the person.
     * @param emailAddress The email address of the person.
     * @param phoneNumber The phone number of the person.
     * @param room The room number of the person.
     */
    public Person(String firstName, String lastName, String emailAddress, String phoneNumber, String room) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.room = room;
    }

    /**
     * Get the first name of the person.
     *
     * @return The first name as a String.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the last name of the person.
     *
     * @return The last name as a String.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the full name of the person.
     *
     * @return The full name as a String.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Get the email address of the person.
     *
     * @return The email address as a String.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Get the phone number of the person.
     *
     * @return The phone number as a String.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Get the room number of the person.
     *
     * @return The room number as a String.
     */
    public String getRoom() {
        return room;
    }

    /**
     *  Set the first name of the person.
     *
     * @param firstName The first name to be set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Set the last name of the person.
     *
     * @param lastName The last name to be set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Set the email address of the person.
     *
     * @param emailAddress The email address to be set.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Set the phone number of the person.
     *
     * @param phoneNumber The phone number to be set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Set the room number of the person.
     *
     * @param room The room number to be set.
     */
    public void setRoom(String room) {
        this.room = room;
    }
}
