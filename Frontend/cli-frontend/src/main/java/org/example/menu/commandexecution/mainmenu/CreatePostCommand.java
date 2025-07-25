package org.example.menu.commandexecution.mainmenu;

import org.example.models.User;
import org.example.menu.views.View;
import org.example.menu.commandexecution.IMenuCommand;

public class CreatePostCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        User user = view.getViewManager().getUser();
        view.getViewManager().getPostApi().addPost(user.getUsername(), "MatchaSubreddit");

        return true;
    }
}
