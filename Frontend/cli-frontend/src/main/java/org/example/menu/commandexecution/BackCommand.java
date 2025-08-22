package org.example.menu.commandexecution;

import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.Subreddit;
import org.example.models.User;

public class BackCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        Post post = view.getViewManager().getPost();
        User user = view.getViewManager().getUser();
        Comment comment = view.getViewManager().getComment();
        Subreddit subreddit = view.getViewManager().getSubreddit();

        //  Return to main menu
        if (view.getViewID() == ViewID.ON_FEED) {
            view.getViewManager().switchToNextView(ViewID.MAIN_MENU);

            return true;
        }

        //  Return to feed
        if (view.getViewID() == ViewID.ON_POST) {
            view.getViewManager().getUiPost().showFeed(user, subreddit.getPosts());
            view.getViewManager().setPost(null);
            view.getViewManager().switchToNextView(ViewID.ON_FEED);

            return true;
        }

        //  Return to post
            view.getViewManager().getUiPost().showPost(true, post, user);
            view.getViewManager().setComment(null);
            view.getViewManager().switchToNextView(ViewID.ON_POST);

        return true;
    }
}
