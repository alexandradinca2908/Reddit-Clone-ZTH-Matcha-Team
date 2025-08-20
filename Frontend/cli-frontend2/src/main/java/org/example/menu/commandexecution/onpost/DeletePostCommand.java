package org.example.menu.commandexecution.onpost;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.views.ViewManager;
import org.example.models.Post;
import org.example.models.Subreddit;

public class DeletePostCommand implements IMenuCommand {

    public boolean execute(View view) {
        ViewManager viewManager = ViewManager.getInstance();
        Subreddit subreddit = viewManager.getSubreddit();
        Post post = viewManager.getPost();

        viewManager.getServiceManager().getPostService().deletePost(subreddit, post);

        viewManager.switchToNextView(ViewID.ON_FEED);
        return true;
    }

}
