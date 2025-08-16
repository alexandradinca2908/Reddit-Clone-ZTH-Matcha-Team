package org.example.menu.commandexecution.onfeed;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.menu.views.ViewManager;
import org.example.models.Subreddit;

public class DeletePostCommand implements IMenuCommand {

    public boolean execute(View view) {
        ViewManager viewManager = ViewManager.getInstance();
        Subreddit subreddit = viewManager.getSubreddit();
        //TODO muta in ON_POST si fa-i si logica
        viewManager.getServiceManager().getPostService().deletePost(subreddit);
        view.getViewManager().getUiPost().showFeed(viewManager.getUser(), subreddit.getPosts());
        return true;
    }

}
