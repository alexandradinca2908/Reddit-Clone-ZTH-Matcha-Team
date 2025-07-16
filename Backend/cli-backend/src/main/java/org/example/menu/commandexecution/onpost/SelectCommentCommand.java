package org.example.menu.commandexecution.onpost;

import org.example.models.Comment;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.views.ViewManager;
import org.example.menu.commandexecution.IMenuCommand;
import org.example.models.Post;

public class SelectCommentCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        Post post = viewManager.getPost();

        try {
            Comment comment = viewManager.getCommentService().selectComment(viewManager.getPost());
            viewManager.setComment(comment);
            viewManager.getUiComment().showAllCommentsAndReplies(post, viewManager.getUser());

            viewManager.switchToNextView(ViewID.ON_COMMENT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}
