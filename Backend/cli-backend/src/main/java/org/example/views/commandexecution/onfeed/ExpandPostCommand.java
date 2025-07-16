package org.example.views.commandexecution.onfeed;

import org.example.models.Post;
import org.example.services.PostService;
import org.example.views.View;
import org.example.views.ViewID;
import org.example.views.ViewManager;
import org.example.views.commandexecution.IMenuCommand;

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
