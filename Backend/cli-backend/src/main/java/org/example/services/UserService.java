package org.example.services;
import org.example.entities.User;

import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private static UserService instance;
    private static final ArrayList<User> users = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static final PasswordService passwordService = new PasswordService();

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();

            // Pre-populate with a default user for testing purposes
            String defaultUsername = "TestUser1";
            String defaultEmail = "test1@user.com";
            String defaultPassword = PasswordService.hashPassword("TestUser1!");
            users.add(new User(defaultUsername, defaultEmail, defaultPassword));

            defaultUsername = "TestUser2";
            defaultEmail = "test2@user.com";
            defaultPassword = PasswordService.hashPassword("TestUser2!");
            users.add(new User(defaultUsername, defaultEmail, defaultPassword));
        }
        return instance;
    }

    public User userRegisterCLI() {
        System.out.println("Welcome to Register Page\n");
        Scanner sc = new Scanner(System.in);

        String username = readUsername();
        String email = readEmail();
        String password = readPassword();

        users.add(new User(username, email, password));
        System.out.println("Registration successful! Welcome, " + username + "!");
        return users.getLast(); // Return the registered user for instant login
    }

    public User userLoginCLI() {
        System.out.println("Welcome to Login Page\n");
        System.out.println("Please enter your username:");
        String username = sc.nextLine();

        System.out.println("Please enter your password:");
        String password = sc.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) &&
                passwordService.checkPassword(password, user.getPassword())) {
                System.out.println("Login successful! Welcome back, " + username + "!");
                return user;
            }
        }
        System.out.println("Invalid username or password. Do you want to try again? (y/n)");
        String response = sc.nextLine();
        if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) {
            return userLoginCLI();
        } else {
            System.out.println("Login cancelled.");
        }
        return null;
    }

    private String readPassword() {
        System.out.println("Password must be at least 8 characters long " +
                "and contain a mix of upper and lower case letters, numbers, and special characters.");
        System.out.println("Please enter your password:");
        String password = sc.nextLine();

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$";
        while (!password.matches(passwordRegex)
    ) {
            System.out.println("Invalid password format. Please try again.");
            password = sc.nextLine();
        }

        password = PasswordService.hashPassword(password);
        return password;
    }

    private String readEmail() {
        System.out.println("Please enter your email:");
        String email = sc.nextLine();

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!email.matches(emailRegex)) {
            System.out.println("Invalid email format. Please try again.");
            email = sc.nextLine();
        }

        return email;
    }

    private String readUsername() {
        System.out.println("Please enter your username:");

        String username = sc.nextLine();

        // Validate username format
        while (!username.matches("^[A-Za-z0-9_]{3,20}$")) {
            System.out.println("Invalid username format. Please use only letters, numbers, and underscores.");
            System.out.println("Username must be between 3 and 20 characters long.");
            System.out.println("Please try again:");
            username = sc.nextLine();
        }

        // Check if username already exists
        while (userAlreadyExists(username)) {
            System.out.println("username already exists. Please try a different one.");
            username = sc.nextLine();
        }

        System.out.println("username accepted: " + username);
        return username;
    }

    private boolean userAlreadyExists(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public void showAllUsers() {
        System.out.println("Registered Users:");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserID() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail() +
                    ", Password: " + user.getPassword());
        }
    }

    public void userDeleteCLI(User user) {
        if (users.remove(user)) {
            System.out.println("User " + user.getUsername() + " deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }
}
