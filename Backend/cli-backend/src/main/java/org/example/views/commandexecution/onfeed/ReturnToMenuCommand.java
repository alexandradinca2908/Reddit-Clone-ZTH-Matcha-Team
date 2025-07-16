package org.example.views.commandexecution.onfeed;

import org.example.views.View;
import org.example.views.ViewID;
import org.example.views.commandexecution.IMenuCommand;

public class ReturnToMenuCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        view.getViewManager().setPost(null);
        view.getViewManager().switchToNextView(ViewID.MAIN_MENU);

        return true;
    }
}
