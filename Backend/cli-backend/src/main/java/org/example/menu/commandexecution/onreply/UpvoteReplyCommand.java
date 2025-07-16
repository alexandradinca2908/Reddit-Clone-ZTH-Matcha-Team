package org.example.menu.commandexecution.onreply;

import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.menu.views.View;
import org.example.menu.commandexecution.IMenuCommand;

public class UpvoteReplyCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Comment commentReply = view.getViewManager().getCommentReply();
        Post post = view.getViewManager().getPost();

        view.getViewManager().getVotingService().voteReply(user, commentReply, true);
        view.getViewManager().getUiComment().showAllCommentsAndReplies(post, user);

        return true;
    }
}
