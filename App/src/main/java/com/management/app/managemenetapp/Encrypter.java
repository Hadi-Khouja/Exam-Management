package com.management.app.managemenetapp;

import com.management.app.types.RecoveryCode;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

/**
 * Singleton class for providing an instance of the Encrypter.
 */
public class Encrypter {

    private RecoveryCode currentCode;
    private static Encrypter instance;

    /**
     * Private constructor to prevent external instantiation.
     */
    private Encrypter() {
    }

    /**
     * Returns the singleton instance of Encrypter. If the instance is null, a new instance is created.
     *
     * @return The singleton instance of Encrypter.
     */
    public static Encrypter getInstance() {
        if (instance == null) {
            instance = new Encrypter();
        }
        return instance;
    }

    /**
     * Method for hashing a password using the SHA-256 algorithm.
     *
     * @param input The input password to be hashed.
     * @return A hexadecimal string representation of the hashed password.
     * @throws NoSuchAlgorithmException If the specified algorithm (SHA-256) is not available.
     */
    public String hashPassword(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(input.getBytes());
        BigInteger bigInt = new BigInteger(1, messageDigest);
        return bigInt.toString(16);
    }

    /**
     * Randomly Generates a 6-digit Code for Password Recovery
     *
     * @return the 6-digit Code as a String
     */
    public void generateResetCode() {
        currentCode = new RecoveryCode();
    }

    public RecoveryCode getCurrentCode() {
        return currentCode;
    }

    public boolean checkCode(String code) {
        if (currentCode == null) {
            return false; // No code to check
        }

        // Check if the provided code matches the current code
        boolean isCodeCorrect = currentCode.getCode().equals(code);

        // Check if it's within one hour of the timestamp
        LocalDateTime timeStampLimit = currentCode.getTimeStamp().plusHours(1);
        boolean timeStampValid = LocalDateTime.now().isBefore(timeStampLimit) && LocalDateTime.now().isAfter(currentCode.getTimeStamp());

        return isCodeCorrect && timeStampValid;
    }
}

