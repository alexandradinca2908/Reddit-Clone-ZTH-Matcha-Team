package org.example.services;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordService {
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

    public boolean checkPassword(String password, String password1) {
        String hashedPassword = hashPassword(password);
        return hashedPassword.equals(password1);
    }
}
