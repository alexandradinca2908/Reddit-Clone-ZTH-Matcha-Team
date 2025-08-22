package org.example.menu.commandexecution.oncomment;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;

public class ReplyCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Comment comment = view.getViewManager().getComment();
        Post post = view.getViewManager().getPost();

        view.getViewManager().getServiceManager().getCommentService().createReply(post, comment, user);

        view.getViewManager().getUiComment().showComment(user.getUsername(), comment, 0);
        //view.getViewManager().getUiComment().showAllCommentsAndReplies(post, user);

        return true;
    }
}
