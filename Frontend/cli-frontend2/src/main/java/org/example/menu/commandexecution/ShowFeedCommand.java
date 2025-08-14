package org.example.menu.commandexecution;

import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.models.Subreddit;
import org.example.models.User;

public class ShowFeedCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Subreddit subreddit = view.getViewManager().getSubreddit();
        view.getViewManager().getServiceManager().getPostService().populateSubreddit(subreddit);
        view.getViewManager().getUiPost().showFeed(user, subreddit.getPosts());
        view.getViewManager().switchToNextView(ViewID.ON_FEED);

        return true;
    }
}
