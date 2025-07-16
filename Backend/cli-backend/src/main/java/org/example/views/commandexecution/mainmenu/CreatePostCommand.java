package org.example.views.commandexecution.mainmenu;

import org.example.models.User;
import org.example.views.View;
import org.example.views.commandexecution.IMenuCommand;

public class CreatePostCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        view.getViewManager().getPostService().addPost(user.getUsername());

        return true;
    }
}
