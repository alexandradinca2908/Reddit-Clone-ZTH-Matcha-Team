package org.example.menu.commandexecution;

import org.example.menu.views.View;
import org.example.menu.views.ViewID;

public class ShowFeedCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        view.getViewManager().getUiPost().showFeed();
        view.getViewManager().switchToNextView(ViewID.ON_FEED);

        return true;
    }
}
