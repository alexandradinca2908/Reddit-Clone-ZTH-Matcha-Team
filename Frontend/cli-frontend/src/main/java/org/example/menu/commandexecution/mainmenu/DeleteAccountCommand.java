package org.example.menu.commandexecution.mainmenu;

import org.example.DTO.User;
import org.example.menu.views.View;
import org.example.menu.views.ViewManager;
import org.example.menu.commandexecution.IMenuCommand;

public class DeleteAccountCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();

        User user = viewManager.getUser();
        boolean isDeleted = viewManager.getUserApi().userDeleteCLI(user);

        if (isDeleted) {
            viewManager.setLoggedIn(false);
        }

        return true;
    }
}
