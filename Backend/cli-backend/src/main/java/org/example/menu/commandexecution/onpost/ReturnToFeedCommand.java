package org.example.menu.commandexecution.onpost;

import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.commandexecution.IMenuCommand;

public class ReturnToFeedCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        view.getViewManager().getUiPost().showFeed();
        view.getViewManager().setPost(null);
        view.getViewManager().switchToNextView(ViewID.ON_FEED);

        return true;
    }
}
