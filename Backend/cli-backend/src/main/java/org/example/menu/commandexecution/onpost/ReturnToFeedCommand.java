package org.example.menu.commandexecution.onpost;

import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.commandexecution.IMenuCommand;
import org.example.models.User;

public class ReturnToFeedCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        view.getViewManager().getUiPost().showFeed(user);
        view.getViewManager().setPost(null);
        view.getViewManager().switchToNextView(ViewID.ON_FEED);

        return true;
    }
}
