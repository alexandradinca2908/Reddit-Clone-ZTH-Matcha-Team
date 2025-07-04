package org.example.services;
import java.util.ArrayList;
import java.util.Scanner;
import main.java.org.example.User;

public class UserService {
    private static UserService instance;
    private static ArrayList<User> users = new ArrayList<>();

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

        System.out.println("Please enter your username:");
        String username = sc.nextLine();

        // Check if username already exists
        if (userAlreadyExists(username)) {
            System.out.println("Username already exists. Please try a different one.");
            return;
        }

        System.out.println("Please enter your email:");
        String email = sc.nextLine();

        System.out.println("Please enter your password:");
        String password = sc.nextLine();

        users.add(new User(username, email, password));
    }
}
