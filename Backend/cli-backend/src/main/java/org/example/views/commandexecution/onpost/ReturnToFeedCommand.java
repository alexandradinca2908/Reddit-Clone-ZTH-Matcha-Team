package org.example.views.commandexecution.onpost;

import org.example.views.View;
import org.example.views.ViewID;
import org.example.views.commandexecution.IMenuCommand;

public class ReturnToFeedCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        view.getViewManager().getUiPost().showFeed();
        view.getViewManager().setPost(null);
        view.getViewManager().switchToNextView(ViewID.ON_FEED);

        return true;
    }
}
