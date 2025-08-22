package org.example.menu.commandexecution.onfeed;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.views.ViewManager;
import org.example.models.Post;
import org.example.models.Subreddit;
import org.example.models.User;

public class ExpandPostCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        User user = viewManager.getUser();
        Subreddit subreddit = viewManager.getSubreddit();
        Post post = viewManager.getServiceManager().getPostService().openPost(subreddit);

        viewManager.getServiceManager().getCommentService().populatePost(post);
        //TODO la fel ca la comment daca post e null gen s-a saturat ala sa bage id-uri gresite dai back la feed
        viewManager.setPost(post);
        viewManager.getUiPost().showPost(true, post, user);

        viewManager.switchToNextView(ViewID.ON_POST);

        return true;
    }
}
