package org.example.views.commandexecution.mainmenu;

import org.example.views.View;
import org.example.views.commandexecution.IMenuCommand;

public class LogoutCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        view.getViewManager().cleanUpData();
        System.out.println("You have been logged out.");

        return true;
    }
}
