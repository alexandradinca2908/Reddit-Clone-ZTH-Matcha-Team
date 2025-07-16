package org.example.menu.commandexecution.mainmenu;

import org.example.menu.views.View;
import org.example.menu.commandexecution.IMenuCommand;

public class LogoutCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        view.getViewManager().cleanUpData();
        System.out.println("You have been logged out.");

        return true;
    }
}
