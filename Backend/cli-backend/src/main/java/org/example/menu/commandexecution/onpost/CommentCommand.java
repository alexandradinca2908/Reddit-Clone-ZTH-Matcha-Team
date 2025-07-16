package org.example.menu.commandexecution.onpost;

import org.example.models.Post;
import org.example.models.User;
import org.example.menu.views.View;
import org.example.menu.commandexecution.IMenuCommand;

public class CommentCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Post post = view.getViewManager().getPost();

        view.getViewManager().getCommentService().addComment(user, post);
        view.getViewManager().getUiPost().showPost(true, post, user);

        return true;
    }
}
