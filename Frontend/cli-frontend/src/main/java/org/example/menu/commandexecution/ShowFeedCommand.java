package org.example.menu.commandexecution;

import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.DTO.User;

public class ShowFeedCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        view.getViewManager().getUiPost().showFeed(user);
        view.getViewManager().switchToNextView(ViewID.ON_FEED);

        return true;
    }
}
