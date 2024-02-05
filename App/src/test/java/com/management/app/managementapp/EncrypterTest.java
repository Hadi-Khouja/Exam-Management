package com.management.app.managementapp;

import com.management.app.managemenetapp.Encrypter;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class EncrypterTest {
    @Test
    void testEmptyPasswordHash() {
        try {
            String hashEmptyPassword = Encrypter.getInstance().hashPassword("");
            assertFalse(hashEmptyPassword.isEmpty(), "Hash of password not empty");
        } catch (NoSuchAlgorithmException e) {
            fail("NoSuchAlgorithmException should not be thrown for SHA-256");
        }
    }

    @Test
    void testExamplePasswordHash() {
        try {
            String hashEmptyPassword = Encrypter.getInstance().hashPassword("test");
            assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", hashEmptyPassword, "Password is hashed correctly");
        } catch (NoSuchAlgorithmException e) {
            fail("NoSuchAlgorithmException should not be thrown for SHA-256");
        }
    }
}
