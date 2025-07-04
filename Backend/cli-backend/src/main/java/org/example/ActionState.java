package org.example;

import org.example.services.UserService;

import java.util.Scanner;

public class ActionState {
    public enum State {
        NOT_LOGGED_IN,
        LOGGED_IN,
        SHOW_FEED,
        REGISTER,
        LOGIN,
        LOGOUT,
        QUIT
    }

    State currentState = State.NOT_LOGGED_IN;
    private final static UserService userService = new UserService();
    Scanner scan = new Scanner(System.in);

    public boolean executeAction() {
        String option;
        String sanitizedInput;

        switch (currentState) {
            case NOT_LOGGED_IN:
                System.out.println("1. Login\n2. Register\n3. Show feed\n4. Quit" );

                option = scan.nextLine();
                sanitizedInput = sanitizeInput(option);

                if (sanitizedInput.equalsIgnoreCase("login")) {
                    //  TODO LOGIN
                    changeState(State.LOGGED_IN);
                } else if (sanitizedInput.equalsIgnoreCase("register")) {
                    userService.userRegisterCLI();
                    changeState(State.LOGGED_IN);
                } else if (sanitizedInput.equalsIgnoreCase("show feed")) {
                    //  TODO SHOW FEED
                    changeState(State.LOGOUT);
                } else if (sanitizedInput.equalsIgnoreCase("quit")) {
                    changeState(State.QUIT);
                }

                break;

            case LOGGED_IN:
                System.out.println("1. Show feed\n2. Logout\n3. Quit");

                option = scan.nextLine();
                sanitizedInput = sanitizeInput(option);

                if (sanitizedInput.equalsIgnoreCase("show feed")) {
                    changeState(State.LOGOUT);
                } else if (sanitizedInput.equalsIgnoreCase("logout")) {
                    changeState(State.LOGOUT);
                } else if (sanitizedInput.equalsIgnoreCase("quit")) {
                    changeState(State.QUIT);
                }

                break;

            case LOGOUT:
                System.out.println("You have been logged out.");
                changeState(State.NOT_LOGGED_IN);
                break;

            case QUIT:
                System.out.println("See you soon!");
                return false;

            default:
                break;
        }

        return true;
    }

    private void changeState(State state) {
        currentState = state;
    }

    private String sanitizeInput(String input) {
        switch (currentState) {
            case NOT_LOGGED_IN:
                // 1. Login, 2. Register, 3. Logout, 4. Quit
                switch (input) {
                    case "1":
                        return "login";

                    case "2":
                        return "register";

                    case "3":
                        return "show feed";

                    case "4":
                        return "quit";
                }

            case LOGGED_IN:
                //  1. Show feed, 2. Logout, 3. Quit
                switch (input) {
                    case "1":
                        return "show feed";

                    case "2":
                        return "logout";

                    case "3":
                        return "quit";
                }
        }

        return input;
    }

}
