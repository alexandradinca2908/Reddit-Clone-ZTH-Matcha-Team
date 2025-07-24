package org.example.menu.commandexecution.oncomment;

import org.example.DTO.Comment;
import org.example.DTO.Post;
import org.example.DTO.User;
import org.example.menu.views.View;
import org.example.menu.commandexecution.IMenuCommand;

public class ReplyCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Comment comment = view.getViewManager().getComment();
        Post post = view.getViewManager().getPost();

        view.getViewManager().getCommentApi().addReply(user, comment);
        view.getViewManager().getUiComment().showAllCommentsAndReplies(post, user);

        return true;
    }
}
