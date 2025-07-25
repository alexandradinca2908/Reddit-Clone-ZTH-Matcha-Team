package org.example.menu.commandexecution.mainmenu;

import org.example.models.User;
import org.example.menu.views.View;
import org.example.menu.views.ViewManager;
import org.example.menu.commandexecution.IMenuCommand;

public class RegisterCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();

        User user = viewManager.getUserApi().userRegisterCLI();

        //  Display login menu only if action was successful
        if (user != null) {
            viewManager.setLoggedIn(true);
            viewManager.setUser(user);
        }

        return true;
    }
}
