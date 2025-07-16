package org.example.userinterface;

import org.example.views.Availability;
import org.example.views.MenuOption;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class UIView {
    private static UIView instance;
    private static final String UNKNOWN_COMMAND = "Unknown command";

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
}
