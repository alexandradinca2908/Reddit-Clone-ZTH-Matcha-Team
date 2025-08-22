package org.example.menu.commandexecution.oncomment;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;

public class DownvoteCommentCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Comment comment = view.getViewManager().getComment();

        view.getViewManager().getServiceManager().getCommentService().downvote(comment);

        view.getViewManager().getUiComment().showComment(user.getUsername(), comment, 0);
        //view.getViewManager().getUiComment().showAllCommentsAndReplies(post, user);

        return true;
    }
}
