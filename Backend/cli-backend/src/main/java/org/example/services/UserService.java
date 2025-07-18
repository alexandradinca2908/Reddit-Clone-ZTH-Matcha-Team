package org.example.services;
import org.example.dbconnection.DatabaseConnection;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.loggerobjects.Logger;
import org.example.repositories.UserRepo;
import org.example.userinterface.UIUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    private static UserService instance;
    public static ArrayList<User> users = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    private static final PasswordService passwordService = new PasswordService();
    public static final UserRepo userRepo = new UserRepo();
    private static final UIUser uiUser = UIUser.getInstance();

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();

            try {
                userRepo.load(users);
            } catch (SQLException e) {
                Logger.error("Failed to load users from the database: " + e.getMessage());
                DatabaseConnection.cannotConnect();
            }
        }
        return instance;
    }

    public User userRegisterCLI() {
        uiUser.welcome("Register");

        String username = readUsername();
        String email = readEmail();
        String password = readPassword();

        try {
            User newUser = new User(username, email, password);
            users.add(newUser);
            userRepo.save(newUser);
            uiUser.registration(true, username);
            return newUser;
        }
        catch (SQLException e) {
            uiUser.registration(false, username);
            return null;
        }
    }

    public User userLoginCLI() {
        uiUser.welcome("Login");

        String username;
        String password;

        do {
            uiUser.pleaseEnter("username");
            username = sc.nextLine();

            uiUser.pleaseEnter("password");
            password = sc.nextLine();

            for (User user : users) {
                if (user.getUsername().equals(username) &&
                        passwordService.checkPassword(password, user.getPassword())) {
                    uiUser.login(true, username);
                    Logger.info("Login successful for user: " + username);
                    return user;
                }
            }

            uiUser.login(false, username);
            String response = sc.nextLine();

            if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("yes")) {
                break;
            }

            Logger.verbose("User tries to login again.");
        } while (true);

        Logger.error("Login didn't work for " + username + ".");
        return null;
    }

    private String readPassword() {
        uiUser.instructions("password");

        uiUser.pleaseEnter("password");
        String password = sc.nextLine();

        String passwordRegex = String.format(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{%d,}$",
                uiUser.getMinPasswordLength()
        );

        while (!password.matches(passwordRegex)) {
            uiUser.invalid("invalid password");
            uiUser.pleaseEnter("password");
            password = sc.nextLine();
        }

        password = PasswordService.hashPassword(password);
        uiUser.accepted("password", null);
        return password;
    }

    private String readEmail() {
        uiUser.pleaseEnter("email");
        String email = sc.nextLine();

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]+$";

        while (email == null || !email.matches(emailRegex)) {
            uiUser.invalid("email");
            email = sc.nextLine();
        }

        return email;
    }

    private String readUsername() {
        uiUser.instructions("username");

        while (true) {
            uiUser.pleaseEnter("username");
            String username = sc.nextLine();

            // Validate username format
            while (!username.matches(String.format(
                    "^[A-Za-z0-9_]{%d,%d}$",
                    uiUser.getMinUsernameLength(),
                    uiUser.getMaxUsernameLength()
            ))) {
                uiUser.invalid("username");
                username = sc.nextLine();
            }

            // At this point, username is valid but not unique

            // Check if username already exists
            if (userAlreadyExists(username)) {
                uiUser.invalid("username exists");
            } else {
                uiUser.accepted("username", username);
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

    public boolean userDeleteCLI(User user) {
        uiUser.areYouSure("delete account");
        String ans = sc.nextLine();

        if (!ans.equalsIgnoreCase("y") && !ans.equalsIgnoreCase("yes")) {
            uiUser.failed("account deletion cancelled", null);
            return false;
        }

        uiUser.pleaseEnter("password");
        String password = sc.nextLine();
        if (passwordService.checkPassword(password, user.getPassword())) {
            uiUser.accepted("account deletion", user.getUsername());
            userRepo.deleteUser(user.getUsername());
            users.remove(user);
            // For every post and comment, you might want to handle deletion logic here
            Logger.info("User " + user.getUsername() + " has been deleted.");
            for (Post post : PostService.posts) {
                if (post.getUsername().equals(user.getUsername())) {
                    post.setUsername("[deleted_user]");
                }
                for (Comment comment : post.getCommentList()) {
                    if (comment.getParentPost().getUsername().equals(user.getUsername())) {
                        comment.getParentPost().setUsername("[deleted_user]");
                    }
                    for (Comment reply : comment.getReplyList()) {
                        if (reply.getParentUser().getUsername().equals(user.getUsername())) {
                            reply.getParentUser().setUsername("[deleted_user]");
                        }
                    }
                }
            }
        } else {
            uiUser.failed("account deletion failed", user.getUsername());
            return false;
        }

        return true;
    }

    public static User findByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }

        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }
}
