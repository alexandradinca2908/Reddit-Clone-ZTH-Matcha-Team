package org.example.views;

import org.example.loggerobjects.Logger;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.services.CommentService;
import org.example.services.PostService;
import org.example.services.UserService;
import org.example.userinterface.UIComment;
import org.example.userinterface.UIPost;

import java.util.HashMap;

public class ViewManager {
    private final static UserService userService = UserService.getInstance();
    private final static PostService postService = PostService.getInstance();
    private final static CommentService commentService = CommentService.getInstance();
    private static ViewManager viewManager;
    private HashMap<ViewID, View> views;
    private ViewID currentViewID;
    protected boolean isLoggedIn;
    private User user;
    private Post post;
    private Comment comment;
    private Comment commentReply;
    private final UIPost UIPost = new UIPost();
    private final UIComment UIComment = new UIComment();

    private ViewManager() {
        this.views = new HashMap<>();
        currentViewID = ViewID.MAIN_MENU;
        isLoggedIn = false;
    }

    public static ViewManager getInstance() {
        if (viewManager == null) {
            viewManager = new ViewManager();
            viewManager.initAllViews();
        }

        return viewManager;
    }

    private void initAllViews() {
        views.put(ViewID.MAIN_MENU, ViewSetup.initMainMenu());
        views.put(ViewID.ON_FEED, ViewSetup.initOnFeed());
        views.put(ViewID.ON_POST, ViewSetup.initOnPost());
        views.put(ViewID.ON_COMMENT, ViewSetup.initOnComment());
        views.put(ViewID.ON_REPLY, ViewSetup.initOnReply());

        ViewSetup.linkViews(views);
    }

    public View getCurrentViewObject() {
        return views.get(currentViewID);
    }

    public ViewID getCurrentViewID() {
        return currentViewID;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    void switchToNextView(ViewID viewID) {
        View currentViewObject = views.get(currentViewID);

        //  Improbable error: manager doesn't have a reference to current view
        if (currentViewObject == null) {
            Logger.fatal("No view found for current view " + currentViewID);
            return;
        }

        //  Likely error: current view doesn't have access to the requested next view
        if (currentViewObject.getNextViews().get(viewID) == null) {
            Logger.error("Current view" + currentViewID + " doesn't have access to next view " + viewID);
            return;
        }

        //  Switch to next view
        currentViewID = viewID;
    }
}
