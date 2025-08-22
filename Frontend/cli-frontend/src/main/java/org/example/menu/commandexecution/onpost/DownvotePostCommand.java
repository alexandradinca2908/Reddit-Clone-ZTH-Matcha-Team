package org.example.menu.commandexecution.onpost;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.models.Post;
import org.example.models.User;

public class DownvotePostCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Post post = view.getViewManager().getPost();

        view.getViewManager().getServiceManager().getPostService().downvote(post);
        view.getViewManager().getUiPost().showPost(true, post, user);

        return true;
    }
}
