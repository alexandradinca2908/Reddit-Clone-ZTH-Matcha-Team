package org.example;

import org.example.services.PostService;
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
    private final static PostService postService = new PostService();
    Scanner scan = new Scanner(System.in);

    public boolean executeAction() {
        String option;
        String sanitizedInput;

        switch (currentState) {
            case NOT_LOGGED_IN:
                System.out.println("1. Login\n2. Register\n3. Logout\n4. Quit" );

                option = scan.nextLine();
                sanitizedInput = sanitizeInput(option);

                if (sanitizedInput.equalsIgnoreCase("register")) {
                    userService.userRegisterCLI();
                    changeState(State.LOGGED_IN);
                } else if (sanitizedInput.equalsIgnoreCase("logout")) {
                    changeState(State.LOGOUT);
                } else if (sanitizedInput.equalsIgnoreCase("quit")) {
                    changeState(State.QUIT);
                }

                break;

            case LOGGED_IN:
                System.out.println("1. Show feed\n2. Create post\n3. Add comment\n4. Logout\n5. Quit");

                option = scan.nextLine();
                sanitizedInput = sanitizeInput(option);

                if (sanitizedInput.equalsIgnoreCase("logout")) {
                    changeState(State.LOGOUT);
                } else if (sanitizedInput.equalsIgnoreCase("quit")) {
                    changeState(State.QUIT);
                } else if (sanitizedInput.equalsIgnoreCase("show feed")) {
                    changeState(State.SHOW_FEED);
                } else if (sanitizedInput.equalsIgnoreCase("add comment")) {
                    //to do
                } else if (sanitizedInput.equalsIgnoreCase("add post")) {
                    //to do
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
                        return "logout";

                    case "4":
                        return "quit";
                }

            case LOGGED_IN:
                //  1. Show feed, 2. Create post, 3. Add comment, 4. Logout, 5. Quit
                switch (input) {
                    case "1":
                        return "show feed";

                    case "2":
                        return "create post";

                    case "3":
                        return "add comment";

                    case "4":
                        return "logout";

                    case "5":
                        return "quit";

                }
        }

        return input;
    }

}
