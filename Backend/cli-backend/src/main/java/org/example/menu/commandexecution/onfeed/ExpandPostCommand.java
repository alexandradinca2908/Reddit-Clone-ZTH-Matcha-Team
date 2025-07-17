package org.example.menu.commandexecution.onfeed;

import org.example.models.Post;
import org.example.models.User;
import org.example.services.PostService;
import org.example.menu.views.View;
import org.example.menu.views.ViewID;
import org.example.menu.views.ViewManager;
import org.example.menu.commandexecution.IMenuCommand;

public class ExpandPostCommand implements IMenuCommand {
    @Override
    public boolean execute(View view) {
        ViewManager viewManager = view.getViewManager();
        User user = view.getViewManager().getUser();

        try {
            PostService postService = viewManager.getPostService();
            Post post = PostService.getPost(postService.getPostIDUser());

            viewManager.setPost(post);
            viewManager.getUiPost().showPost(true, post, user);

            viewManager.switchToNextView(ViewID.ON_POST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return true;
    }
}
