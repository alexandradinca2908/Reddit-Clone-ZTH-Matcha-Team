package org.example.views.commandexecution.oncomment;

import org.example.models.Post;
import org.example.models.State;
import org.example.views.View;
import org.example.views.ViewID;
import org.example.views.commandexecution.IMenuCommand;

public class ReturnToPostCommand implements IMenuCommand {

    @Override
    public boolean execute(View view) {
        Post post = view.getViewManager().getPost();
        view.getViewManager().getUiPost().showPost(true, post);
        view.getViewManager().setComment(null);
        view.getViewManager().switchToNextView(ViewID.ON_POST);

        return true;
    }
}
