package org.example.menu.views;

import org.example.loggerobjects.Logger;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.services.CommentService;
import org.example.services.PostService;
import org.example.services.UserService;
import org.example.services.VotingService;
import org.example.userinterface.UIComment;
import org.example.userinterface.UIPost;

import java.util.HashMap;

public class ViewManager {
    private final UserService userService = UserService.getInstance();
    private final PostService postService = PostService.getInstance();
    private final CommentService commentService = CommentService.getInstance();
    private final VotingService votingService = VotingService.getInstance();
    private final UIPost uiPost = new UIPost();
    private final UIComment uiComment = new UIComment();

    private static ViewManager viewManager;
    private HashMap<ViewID, View> views;
    private ViewID currentViewID;
    protected boolean isLoggedIn;
    private User user;
    private Post post;
    private Comment comment;

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

        ViewSetup.linkViews(views);
    }

    public UserService getUserService() {
        return userService;
    }

    public PostService getPostService() {
        return postService;
    }

    public CommentService getCommentService() {
        return commentService;
    }

    public VotingService getVotingService() {
        return votingService;
    }

    public UIPost getUiPost() {
        return uiPost;
    }

    public UIComment getUiComment() {
        return uiComment;
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

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void switchToNextView(ViewID viewID) {
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

    public void cleanUpData() {
        isLoggedIn  = false;
        user = null;
        post = null;
        comment = null;
    }
}
