package org.example.menu.commandexecution.oncomment;

import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.menu.views.View;
import org.example.menu.commandexecution.IMenuCommand;

public class UpvoteCommentCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Comment comment = view.getViewManager().getComment();
        Post post = view.getViewManager().getPost();

        view.getViewManager().getVotingApi().voteComment(user, comment, true);
        view.getViewManager().getUiComment().showAllCommentsAndReplies(post, user);

        return true;
    }
}
