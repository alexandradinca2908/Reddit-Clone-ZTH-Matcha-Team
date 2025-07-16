package org.example.views.commandexecution.onreply;

import org.example.models.Comment;
import org.example.models.User;
import org.example.views.View;
import org.example.views.commandexecution.IMenuCommand;

public class UpvoteReplyCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Comment commentReply = view.getViewManager().getCommentReply();

        view.getViewManager().getVotingService().voteReply(user, commentReply, true);

        return true;
    }
}
