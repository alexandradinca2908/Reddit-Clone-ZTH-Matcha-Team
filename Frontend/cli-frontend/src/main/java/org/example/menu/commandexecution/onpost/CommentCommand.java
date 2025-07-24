package org.example.menu.commandexecution.onpost;

import org.example.DTO.Post;
import org.example.DTO.User;
import org.example.menu.views.View;
import org.example.menu.commandexecution.IMenuCommand;

public class CommentCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Post post = view.getViewManager().getPost();

        view.getViewManager().getCommentApi().addComment(user, post);
        view.getViewManager().getUiPost().showPost(true, post, user);

        return true;
    }
}
