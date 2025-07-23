package org.example.menu.commandexecution.oncomment;

import org.example.DTO.Comment;
import org.example.DTO.Post;
import org.example.DTO.User;
import org.example.menu.views.View;
import org.example.menu.commandexecution.IMenuCommand;

public class DownvoteCommentCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        Comment comment = view.getViewManager().getComment();
        Post post = view.getViewManager().getPost();

        view.getViewManager().getVotingService().voteComment(user, comment, false);
        view.getViewManager().getUiComment().showAllCommentsAndReplies(post, user);

        return true;
    }
}
