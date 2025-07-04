package org.example.services;
import org.example.User;

import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private static UserService instance;
    private static ArrayList<User> users = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static final PasswordService passwordService = new PasswordService();

    public UserService() {}

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void userRegisterCLI() {
        System.out.println("Welcome to Register Page\n");
        Scanner sc = new Scanner(System.in);

        String username = readUsername();
        String email = readEmail();
        String password = readPassword();

        users.add(new User(username, email, password));
        System.out.println("Registration successful! Welcome, " + username + "!");
//        showAllUsers();
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

        password = passwordService.hashPassword(password);
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
}
