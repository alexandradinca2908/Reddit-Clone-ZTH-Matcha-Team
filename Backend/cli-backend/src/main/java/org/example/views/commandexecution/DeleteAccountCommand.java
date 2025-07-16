package org.example.views.commandexecution;

import org.example.models.User;
import org.example.views.View;
import org.example.views.ViewManager;

public class DeleteAccountCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();

        User user = viewManager.getUser();
        viewManager.getUserService().userDeleteCLI(user);
        viewManager.setLoggedIn(false);

        return true;
    }
}
