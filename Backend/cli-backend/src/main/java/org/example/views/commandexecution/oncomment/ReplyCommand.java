package org.example.views.commandexecution.oncomment;

import org.example.models.Comment;
import org.example.models.User;
import org.example.views.View;
import org.example.views.commandexecution.IMenuCommand;

public class ReplyCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Comment comment = view.getViewManager().getComment();

        view.getViewManager().getCommentService().addReply(user, comment);

        return true;
    }
}
