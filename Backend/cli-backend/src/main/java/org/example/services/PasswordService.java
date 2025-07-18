package org.example.services;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordService {
    private static PasswordService instance;

    private PasswordService() {}

    public static PasswordService getInstance() {
        if (instance == null) {
            instance = new PasswordService();
        }
        return instance;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convert bytes to hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public boolean checkPassword(String inputPassword, String storedHashedPassword) {
        String hashedInputPassword = hashPassword(inputPassword);
        return hashedInputPassword.equals(storedHashedPassword);
    }
}
