package org.example.menu.commandexecution.onreply;

import org.example.models.Post;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.commandexecution.IMenuCommand;
import org.example.models.User;

public class ReturnToCommentCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        Post post = view.getViewManager().getPost();
        User user = view.getViewManager().getUser();

        view.getViewManager().getUiComment().showAllCommentsAndReplies(post, user);
        view.getViewManager().setCommentReply(null);
        view.getViewManager().switchToNextView(ViewID.ON_COMMENT);

        return true;
    }
}
