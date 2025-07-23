package org.example.menu.commandexecution.onpost;

import org.example.DTO.Comment;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.views.ViewManager;
import org.example.menu.commandexecution.IMenuCommand;
import org.example.DTO.User;

public class SelectCommentCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        User user = viewManager.getUser();

        try {
            Comment comment = viewManager.getCommentService().selectComment(viewManager.getPost());
            viewManager.setComment(comment);
            viewManager.getUiComment().showComment(user.getUsername(), comment, 0);

            viewManager.switchToNextView(ViewID.ON_COMMENT);
        } catch (Exception e) {
            view.getViewManager().getUiView().printInvalidInputError(e.getMessage());
        }

        return true;
    }
}
