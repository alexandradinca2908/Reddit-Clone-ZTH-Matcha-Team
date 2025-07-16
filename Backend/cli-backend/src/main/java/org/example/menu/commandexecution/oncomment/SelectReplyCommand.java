package org.example.menu.commandexecution.oncomment;

import org.example.models.Comment;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.views.ViewManager;
import org.example.menu.commandexecution.IMenuCommand;

public class SelectReplyCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        Comment comment = viewManager.getComment();

        try {
            Comment commentReply = viewManager.getCommentService().selectReply(comment);
            viewManager.setCommentReply(commentReply);

            viewManager.switchToNextView(ViewID.ON_REPLY);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}
