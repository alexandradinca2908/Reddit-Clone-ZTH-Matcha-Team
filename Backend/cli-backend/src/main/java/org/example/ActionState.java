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
        switch (currentState) {
            case NOT_LOGGED_IN:
                System.out.println("1. Login\n2. Register\n3. Logout\n4. Quit" );

                String option = scan.nextLine();

                if (option.equals("2") || option.equalsIgnoreCase("register")) {
                    userService.userRegisterCLI();
                } else if (option.equals("4") || option.equalsIgnoreCase("quit")){
                    return false;
                }

                break;

            case LOGGED_IN:
                System.out.println("1. Show feed\n2. Logout\n3. Quit");
                break;

            case REGISTER:
                System.out.println("Register User");
                break;

            case LOGIN:
                System.out.println("Login");
                break;

            case LOGOUT:
                System.out.println("You have been logged out.");

            case QUIT:
                System.out.println("See you soon!");

            default:
                break;
        }

        return true;
    }

}
