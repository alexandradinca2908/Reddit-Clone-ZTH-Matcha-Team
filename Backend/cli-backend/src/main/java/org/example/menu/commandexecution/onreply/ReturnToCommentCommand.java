package org.example.menu.commandexecution.onreply;

import org.example.models.Post;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.commandexecution.IMenuCommand;

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
