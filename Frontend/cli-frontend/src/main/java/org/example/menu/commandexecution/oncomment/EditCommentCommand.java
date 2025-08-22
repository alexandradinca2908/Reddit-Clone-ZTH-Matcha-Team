package org.example.menu.commandexecution.oncomment;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.menu.views.ViewManager;
import org.example.models.Comment;
import org.example.models.Post;

public class EditCommentCommand implements IMenuCommand {

    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        Comment comment = viewManager.getComment();
        Comment parentComment = viewManager.getParentComment();
        Post post = viewManager.getPost();
        if(parentComment == null) {
            viewManager.setComment(post.getComments().get(viewManager.getServiceManager().getCommentService().editComment(post, comment, parentComment)));
        } else {
            viewManager.setComment(parentComment.getReplies().get(viewManager.getServiceManager().getCommentService().editComment(post, comment, parentComment)));
        }

        viewManager.getUiComment().showAllCommentsAndReplies(post, viewManager.getUser());

        return true;

    }
}
