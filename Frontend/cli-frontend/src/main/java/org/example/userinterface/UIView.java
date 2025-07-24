package org.example.userinterface;

import org.example.menu.Availability;
import org.example.menu.MenuOption;

import java.util.LinkedHashMap;

public class UIView {
    private static UIView instance;
    private static final String UNKNOWN_COMMAND = "Unknown command";
    private static final String SIMPLE_LOGOUT = "You have been logged out.";
    private static final String LOGOUT_AFTER_QUIT = "You have been automatically logged out.";
    private static final String GOODBYE_MESSAGE = "See you soon!";
    private static final String RESTART_APP = "\nPlease restart the application";

    private UIView() {
    }

    public static UIView getInstance() {
        if (instance == null) {
            instance = new UIView();
        }

        return instance;
    }

    public void renderMenu(LinkedHashMap<MenuOption, Availability> menu, boolean isLoggedIn) {
        int counter = 1;
        Availability availability;

        if (isLoggedIn) {
            availability = Availability.LOGGED_IN;
        } else {
            availability = Availability.LOGGED_OUT;
        }

        //  Render logged in/logged out options and always available options
        for (MenuOption option :  menu.keySet()) {
            if (menu.get(option) == availability ||
                    menu.get(option) == Availability.ANYTIME) {
                System.out.println(counter++ + ". " + option);
            }
        }
    }

    public void unknownCommand() {
        System.out.println(UNKNOWN_COMMAND);
    }

    public void printLogoutMessage() {
        System.out.println(SIMPLE_LOGOUT);
    }

    public void printLogoutMessageForQuit() {
        System.out.println(LOGOUT_AFTER_QUIT);
    }

    public void printGoodbyeMessage() {
        System.out.println(GOODBYE_MESSAGE);
    }

    public void printInvalidInputError(String message) {
        System.out.println(message);
    }

    public void printRestartAppMessage() {
        System.out.println(RESTART_APP);
    }
}
