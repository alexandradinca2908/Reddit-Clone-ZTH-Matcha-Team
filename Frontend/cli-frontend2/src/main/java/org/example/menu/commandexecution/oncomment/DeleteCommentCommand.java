package org.example.menu.commandexecution.oncomment;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.menu.views.ViewManager;
import org.example.models.Comment;
import org.example.models.Post;

public class DeleteCommentCommand implements IMenuCommand {

    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        Post post = viewManager.getPost();
        Comment comment = viewManager.getComment();
        Comment parentComment  = viewManager.getParentComment();

        viewManager.getServiceManager().getCommentService().deleteComment(post, comment, parentComment);
        viewManager.getUiComment().showAllCommentsAndReplies(post, viewManager.getUser());

        return true;
    }
}
