package org.example.views.commandexecution.onreply;

import org.example.models.Post;
import org.example.models.State;
import org.example.views.View;
import org.example.views.ViewID;
import org.example.views.commandexecution.IMenuCommand;

public class ReturnToCommentCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        Post post = view.getViewManager().getPost();

        view.getViewManager().getUiComment().showAllCommentsAndReplies(post);
        view.getViewManager().setCommentReply(null);
        view.getViewManager().switchToNextView(ViewID.ON_COMMENT);

        return true;
    }
}
