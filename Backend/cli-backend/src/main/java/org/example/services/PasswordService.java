package org.example.services;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.example.loggerobjects.LogLevel;
import org.example.loggerobjects.LogManager;
import org.example.loggerobjects.Logger;

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

            // TODO: Uncomment the line below to enable logging
//            logManager.getLogger("FileLogger1").log(LogLevel.VERBOSE, "Password hashed successfully.");
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
