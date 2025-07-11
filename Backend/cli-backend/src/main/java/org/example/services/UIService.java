package org.example.services;


public class UIService {
    private static UIService instance;
    private static final String WELCOME_TO_REGISTER = "Welcome to Register Page\n";
    private static final String WELCOME_TO_LOGIN = "Welcome to Login Page\n";
    private static final String WELCOME_TO_USER = "Welcome, ";

    private static final String REGISTRATION_FAILED = "\"Registration failed. Check internet connection and try again.\"";
    private static final String REGISTRATION_SUCCESSFUL = "Registration successful.";

    private static final String PLEASE_ENTER_USERNAME = "Please enter your username:";
    private static final String PLEASE_ENTER_EMAIL = "Please enter your email:";
    private static final String PLEASE_ENTER_PASSWORD = "Please enter your password:";
    private static final String PLEASE_ENTER_PASSWORD_AGAIN = "Please enter your password again:";

    private static final String LOGIN_SUCCESSFUL = "Login successful! Welcome back, ";
    private static final String LOGIN_FAILED = "Login failed.\nDo you want to try again? (y/n)";
    private static final String LOGIN_CANCELLED = "Login cancelled.";

    private static final String USERNAME_INSTRUCTIONS = "Usernames use only letters, numbers and underscores.\nIt must be between %d and %d characters long.\n";
    private static final String PASSWORD_INSTRUCTIONS = "Password must be at least %d characters long and contain at least\n\t- one uppercase letter\n\t- one lowercase letter\n\t- one digit\n\t- one special character";

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 20;

    private static final String INCORRECT_PASSWORD = "Invalid password. Please enter a valid password.";
    private static final String INVALID_EMAIL = "Invalid email format. Please enter a valid email address.";
    private static final String INVALID_PASSWORD = "Invalid password. Please enter a valid password.";
    private static final String INVALID_USERNAME = "Invalid username format. Please enter a valid username.";
    private static final String USERNAME_ALREADY_EXISTS = "Username already exists. Please choose a different username.";

    private static final String ACCEPTED_USERNAME = "Username accepted: %s";
    private static final String ACCEPTED_PASSWORD = "Password accepted.";

    private static final String DELETE_ACCOUNT_CONFIRMATION = "Are you sure you want to delete your account? (y/n)";
    private static final String ACCOUNT_DELETION_CANCELLED = "Account deletion cancelled.";
    private static final String ACCOUNT_DELETION_SUCCESS = "Account with username: %s was deleted successfully.";

    private UIService() {}

    public static UIService getInstance() {
        if (instance == null) {
            instance = new UIService();
        }
        return instance;
    }

    public void welcome(String where) {
        if (where.equalsIgnoreCase("register")) {
            System.out.println(WELCOME_TO_REGISTER);
        } else if (where.equalsIgnoreCase("login")) {
            System.out.println(WELCOME_TO_LOGIN);
        }
    }

    public void registration(boolean success, String username) {
        if (success) {
            System.out.println(REGISTRATION_SUCCESSFUL);
            System.out.println(WELCOME_TO_USER + username + "!");
        } else {
            System.out.println(REGISTRATION_FAILED);
        }
    }

    public void login(boolean success, String username) {
        if (success) {
            System.out.println(LOGIN_SUCCESSFUL + username + "!");
        } else if (username != null) {
            System.out.println(LOGIN_FAILED);
        } else {
            System.out.println(LOGIN_CANCELLED);
        }
    }

    public void pleaseEnter(String what) {
        if (what.equalsIgnoreCase("username")) {
            System.out.println(PLEASE_ENTER_USERNAME);
        } else if (what.equalsIgnoreCase("email")) {
            System.out.println(PLEASE_ENTER_EMAIL);
        } else if (what.equalsIgnoreCase("password")) {
            System.out.println(PLEASE_ENTER_PASSWORD);
        } else if (what.equalsIgnoreCase("password again")) {
            System.out.println(PLEASE_ENTER_PASSWORD_AGAIN);
        }
    }

    public void instructions(String what) {
        if (what.equalsIgnoreCase("password")) {
            System.out.printf((PASSWORD_INSTRUCTIONS) + "%n", MIN_PASSWORD_LENGTH);
        } else if (what.equalsIgnoreCase("username")) {
            System.out.printf((USERNAME_INSTRUCTIONS) + "%n", MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH);
        }
    }

    public int getMinPasswordLength() {
        return MIN_PASSWORD_LENGTH;
    }

    public void invalid(String what) {
        if (what.equalsIgnoreCase("incorrect password")) {
            System.out.println(INCORRECT_PASSWORD);
        } else if (what.equalsIgnoreCase("invalid password")) {
            System.out.println(INVALID_PASSWORD);
        } else if (what.equalsIgnoreCase("email")) {
            System.out.println(INVALID_EMAIL);
        } else if (what.equalsIgnoreCase("username")) {
            System.out.println(INVALID_USERNAME);
        } else if (what.equalsIgnoreCase("username exists")) {
            System.out.println(USERNAME_ALREADY_EXISTS);
        }
    }

    public int getMinUsernameLength() {
        return MIN_USERNAME_LENGTH;
    }

    public int getMaxUsernameLength() {
        return MAX_USERNAME_LENGTH;
    }

    public void accepted(String what, String username) {
        if (what.equalsIgnoreCase("username")) {
            System.out.printf(ACCEPTED_USERNAME + "%n", username);
        } else if (what.equalsIgnoreCase("password")) {
            System.out.println(ACCEPTED_PASSWORD);
        } else if (what.equalsIgnoreCase("account deletion")) {
            System.out.printf((ACCOUNT_DELETION_SUCCESS) + "%n", username);
        }
    }

    public void failed(String what, String str) {
        if (what.equalsIgnoreCase("account deletion cancelled")) {
            System.out.println(ACCOUNT_DELETION_CANCELLED);
        } else if (what.equalsIgnoreCase("account deletion failed")) {}
            System.out.println("Failed to delete account: " + str);
    }

    public void areYouSure(String what) {
        if (what.equalsIgnoreCase("delete account")) {
            System.out.println(DELETE_ACCOUNT_CONFIRMATION);
        }
    }
}