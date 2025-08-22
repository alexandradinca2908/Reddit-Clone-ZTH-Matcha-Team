package org.example.menu.commandexecution.oncomment;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.menu.views.ViewManager;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;

public class SelectReplyCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        Comment comment = viewManager.getComment();
        viewManager.setParentComment(comment);
        User user = viewManager.getUser();


        Comment reply = viewManager.getServiceManager().getCommentService().openReply(comment);
        viewManager.setComment(reply);

        view.getViewManager().getUiComment().showComment(user.getUsername(), reply, 0);
        //viewManager.getUiComment().showAllCommentsAndReplies(post, viewManager.getUser());
        return true;
    }
}
