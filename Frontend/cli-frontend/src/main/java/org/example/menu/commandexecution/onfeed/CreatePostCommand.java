package org.example.menu.commandexecution.onfeed;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.menu.views.ViewManager;
import org.example.models.Subreddit;
import org.example.models.User;

public class CreatePostCommand implements IMenuCommand {

    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        Subreddit subreddit = viewManager.getSubreddit();
        User user = viewManager.getUser();

        viewManager.getServiceManager().getPostService().createPost(subreddit, user);

        return true;
    }
}
