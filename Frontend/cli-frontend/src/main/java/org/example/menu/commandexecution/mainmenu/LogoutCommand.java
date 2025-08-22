package org.example.menu.commandexecution.mainmenu;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;

public class LogoutCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        view.getViewManager().cleanUpData();
        view.getViewManager().getUiView().printLogoutMessage();

        return true;
    }
}
