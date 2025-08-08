package org.example.menu.commandexecution;

import org.example.menu.views.View;

public class QuitCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        if (view.getViewManager().isLoggedIn()) {
            view.getViewManager().cleanUpData();
            view.getViewManager().getUiView().printLogoutMessageForQuit();
        }

        view.getViewManager().getUiView().printGoodbyeMessage();

        return false;
    }
}
