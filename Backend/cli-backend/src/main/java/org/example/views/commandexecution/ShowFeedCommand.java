package org.example.views.commandexecution;

import org.example.views.View;
import org.example.views.ViewID;

public class ShowFeedCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        view.getViewManager().getUiPost().showFeed();
        view.getViewManager().switchToNextView(ViewID.ON_FEED);

        return true;
    }
}
