package com.management.app.types;

/**
 * The User class represents a user, extending the Person class.
 * It includes additional properties such as ID, username, and password.
 */
public class User extends Person {
    private String id;
    private String userName;
    private String password;

    /**
     * Default constructor for User.
     */
    public User() {

    }

    /**
     * Constructor for User with details about the user and person.
     *
     * @param firstName    The first name of the user.
     * @param lastName     The last name of the user.
     * @param emailAddress The email address of the user.
     * @param phoneNumber  The phone number of the user.
     * @param room         The room number of the user.
     * @param id           The ID of the user.
     * @param userName     The username of the user.
     * @param password     The password of the user.
     */
    public User(String firstName, String lastName, String emailAddress, String phoneNumber, String room, String id, String userName, String password) {
        super(firstName, lastName, emailAddress, phoneNumber, room);
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Get the ID of the user.
     *
     * @return The ID as a String.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the username of the user.
     *
     * @return The username as a String.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Get the password of the user.
     *
     * @return The password as a String.
     */
    public String getPassword() {
        return password;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}

