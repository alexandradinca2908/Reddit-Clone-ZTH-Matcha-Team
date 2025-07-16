package org.example.menu.commandexecution.onfeed;

import org.example.models.Post;
import org.example.services.PostService;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.views.ViewManager;
import org.example.menu.commandexecution.IMenuCommand;

public class ExpandPostCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();

        PostService postService = viewManager.getPostService();
        Post post = postService.getPost(postService.getPostIDUser());

        viewManager.setPost(post);
        viewManager.getUiPost().showPost(true, post);

        viewManager.switchToNextView(ViewID.ON_POST);

        return true;
    }
}
