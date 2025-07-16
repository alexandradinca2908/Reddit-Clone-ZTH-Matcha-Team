package org.example.views.commandexecution;

import org.example.models.User;
import org.example.views.View;

public class CreatePostCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        view.getViewManager().getPostService().addPost(user.getUsername());

        return true;
    }
}
