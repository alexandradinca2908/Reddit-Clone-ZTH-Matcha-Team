package org.example.menu.commandexecution.onpost;

import org.example.menu.commandexecution.IMenuCommand;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.views.ViewManager;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;

public class SelectCommentCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        User user = viewManager.getUser();
        Post post = viewManager.getPost();
        Comment comment = viewManager.getServiceManager().getCommentService().openComment(post);
        //TODO daca comment e null da i back command
        viewManager.setComment(comment);
        viewManager.getUiComment().showComment(user.getUsername(), comment, 0);

        viewManager.switchToNextView(ViewID.ON_COMMENT);

        return true;
    }
}
