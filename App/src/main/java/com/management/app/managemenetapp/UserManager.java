package com.management.app.managemenetapp;

import com.management.app.types.User;

/**
 * The UserManager class is responsible for managing user-related operations in the application.
 * It follows the Singleton pattern to ensure only one instance is created.
 */
public class UserManager {
    private static UserManager instance;
    private User currentUser;

    /**
     * Private constructor to prevent external instantiation.
     */
    private UserManager() {
    }

    /**
     * Returns the singleton instance of the UserManager.
     *
     * @return The UserManager instance
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return The current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Logs in the provided user, setting them as the current user.
     *
     * @param user The user to be logged in
     */
    public void login(User user) {
        currentUser = user;
    }

    /**
     * Logs out the current user, setting the current user to null.
     */
    public void logout() {
        currentUser = null;
    }
}
