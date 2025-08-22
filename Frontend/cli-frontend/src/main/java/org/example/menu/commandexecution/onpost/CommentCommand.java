package org.example.menu.commandexecution.onpost;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.menu.views.ViewManager;
import org.example.models.Post;
import org.example.models.User;

public class CommentCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        User user = view.getViewManager().getUser();
        Post post = view.getViewManager().getPost();

        viewManager.getServiceManager().getCommentService().createComment(post, user);
        view.getViewManager().getUiPost().showPost(true, post, user);

        return true;
    }
}
