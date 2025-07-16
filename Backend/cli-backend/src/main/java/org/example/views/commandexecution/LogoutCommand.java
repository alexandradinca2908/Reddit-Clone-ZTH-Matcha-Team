package org.example.views.commandexecution;

import org.example.views.View;

public class LogoutCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        view.getViewManager().cleanUpData();
        System.out.println("You have been logged out.");

        return true;
    }
}
