package org.example.menu.commandexecution.onfeed;

import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.commandexecution.IMenuCommand;

public class ReturnToMenuCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        view.getViewManager().setPost(null);
        view.getViewManager().switchToNextView(ViewID.MAIN_MENU);

        return true;
    }
}
