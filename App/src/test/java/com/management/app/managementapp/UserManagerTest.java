package com.management.app.managementapp;

import com.management.app.managemenetapp.UserManager;
import com.management.app.types.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    @Test
    void testLogin() {
        User user = new User("john", "reed", "", "", "", "", "john", "test");
        UserManager.getInstance().login(user);
        assertEquals(user, UserManager.getInstance().getCurrentUser(), "Log in success");
    }

    @Test
    void testLogout() {
        User user = new User("john", "reed", "", "", "", "", "john", "test");
        UserManager.getInstance().login(user);

        UserManager.getInstance().logout();
        assertNull(UserManager.getInstance().getCurrentUser(), "Log out successful");
    }

    @Test
    void testSingletonPattern() {
        UserManager userManager1 = UserManager.getInstance();
        UserManager userManager2 = UserManager.getInstance();
        assertEquals(userManager1, userManager2, "Singleton pattern maintained");
    }
}
