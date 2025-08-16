package org.example.menu.commandexecution.onpost;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.menu.views.ViewManager;
import org.example.models.Post;
import org.example.models.Subreddit;
import org.example.models.User;

public class EditPostCommand implements IMenuCommand {

    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        User user = viewManager.getUser();
        Subreddit subreddit = viewManager.getSubreddit();
        Post post = viewManager.getPost();

        viewManager.setPost(subreddit.getPosts().get(viewManager.getServiceManager().getPostService().editPost(subreddit, post)));
        post = viewManager.getPost();
        view.getViewManager().getUiPost().showPost(true, post, user);
        return true;
    }
}
