package org.example.menu.commandexecution;

import org.example.menu.views.View;

public class QuitCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        if (view.getViewManager().isLoggedIn()) {
            view.getViewManager().cleanUpData();
            System.out.println("You have been automatically logged out.");
        }

        System.out.println("See you soon!");

        return false;
    }
}
