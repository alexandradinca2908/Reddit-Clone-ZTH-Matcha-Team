package org.example.views.commandexecution.mainmenu;

import org.example.models.User;
import org.example.views.View;
import org.example.views.ViewManager;
import org.example.views.commandexecution.IMenuCommand;

public class RegisterCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();

        User user = viewManager.getUserService().userRegisterCLI();

        //  Display login menu only if action was successful
        if (user != null) {
            viewManager.setLoggedIn(true);
            viewManager.setUser(user);
        }

        return true;
    }
}
