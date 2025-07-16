package org.example.views.commandexecution.oncomment;

import org.example.models.Comment;
import org.example.models.State;
import org.example.views.View;
import org.example.views.ViewID;
import org.example.views.ViewManager;
import org.example.views.commandexecution.IMenuCommand;

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
