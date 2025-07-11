package org.example.services;
import org.example.entities.User;
import org.example.loggerobjects.LogLevel;
import org.example.loggerobjects.Logger;
import org.example.loggerobjects.LoggerType;
import org.example.repositories.UserRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private static UserService instance;
    public static ArrayList<User> users = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static final PasswordService passwordService = new PasswordService();
    public static final UserRepo userRepo = new UserRepo();
    private static final UIService uiService = UIService.getInstance();

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();

            userRepo.load(users);
        }
        return instance;
    }

    public User userRegisterCLI() {
        uiService.welcome("Register");

        String username = readUsername();
        String email = readEmail();
        String password = readPassword();

        try {
            User newUser = new User(username, email, password);
            userRepo.save(newUser);
            uiService.registration(true, username);
            return newUser;
        }
        catch (SQLException e) {
            uiService.registration(false, username);
            return null;
        }
    }

    public User userLoginCLI() {
        uiService.welcome("Login");

        String username;
        String password;

        do {
            uiService.pleaseEnter("username");
            username = sc.nextLine();

            uiService.pleaseEnter("password");
            password = sc.nextLine();

            for (User user : users) {
                if (user.getUsername().equals(username) &&
                        passwordService.checkPassword(password, user.getPassword())) {
                    uiService.login(true, username);
                    return user;
                }
            }

            uiService.login(false, username);
            String response = sc.nextLine();

            if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes"))
                break;

        } while (true);

        System.out.println("Invalid username or password. Do you want to try again? (y/n)");
        String response = sc.nextLine();
        if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) {
            Logger.log(LogLevel.VERBOSE, "User tries to login again.");
            return userLoginCLI();
        } else {
            Logger.log(LogLevel.ERROR, "Login didn't work for " + username + ".");
            System.out.println("Login cancelled.");
        }
        return null;
    }

    private String readPassword() {
        uiService.instructions("password");

        uiService.pleaseEnter("password");
        String password = sc.nextLine();

        String passwordRegex = String.format(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{%d,}$",
                uiService.getMinPasswordLength()
        );
        while (!password.matches(passwordRegex)) {
            uiService.invalid("password");
            uiService.pleaseEnter("password");
            password = sc.nextLine();
        }

        password = PasswordService.hashPassword(password);
        uiService.accepted("password", null);
        return password;
    }

    private String readEmail() {
        uiService.pleaseEnter("email");
        String email = sc.nextLine();

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        while (email == null || !email.matches(emailRegex)) {
            uiService.invalid("email");
            email = sc.nextLine();
        }

        return email;
    }

    private String readUsername() {
        uiService.instructions("username");

        while (true) {
            uiService.pleaseEnter("username");
            String username = sc.nextLine();

            // Validate username format
            while (!username.matches(String.format(
                    "^[A-Za-z0-9_]{%d,%d}$",
                    uiService.getMinUsernameLength(),
                    uiService.getMaxUsernameLength()
            ))) {
                uiService.invalid("username");
                username = sc.nextLine();
            }

            // At this point, username is valid but not unique

            // Check if username already exists
            if (userAlreadyExists(username)) {
                uiService.invalid("username exists");
            } else {
                uiService.accepted("username", username);
                return username;
            }
        }
    }

    private boolean userAlreadyExists(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    // Only for debugging purposes
    public void showAllUsers() {
        System.out.println("Registered Users:");
        for (User user : users) {
            System.out.println(
                    "User ID: " + user.getUserID() +
                    ", Username: " + user.getUsername() +
                    ", Email: " + user.getEmail() +
                    ", Password: " + user.getPassword()
            );
        }
    }

    public void userDeleteCLI(User user) {
        uiService.areYouSure("delete account");
        String ans = sc.nextLine();

        if (!ans.equalsIgnoreCase("y") && !ans.equalsIgnoreCase("yes")) {
            uiService.failed("account deletion cancelled", null);
            return;
        }

        uiService.pleaseEnter("password");
        String password = sc.nextLine();
        if (passwordService.checkPassword(password, user.getPassword())) {
            uiService.accepted("account deletion", user.getUsername());
            // TODO: Implement the actual deletion logic
        } else {
            uiService.failed("account deletion failed", user.getUsername());
        }
    }
}
