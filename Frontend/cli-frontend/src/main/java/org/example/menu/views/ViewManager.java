package org.example.menu.views;

import org.example.api.*;
import org.example.loggerobjects.Logger;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.textprocessors.AnsiColors;
import org.example.userinterface.UIComment;
import org.example.userinterface.UIPost;
import org.example.userinterface.UIView;

import java.util.HashMap;

public class ViewManager {
    //public static final String BACKEND_API_URL = "http://13.48.209.206:8080";  //to be moved soon
    public static final String BACKEND_API_URL = "http://localhost:8080"; //to be moved soon
    private static final String ACCOUNTS_DISABLED = "Accounts have been disabled! Logging in as TEST USER";
    private final ApiManager apiManager = ApiManager.getInstance(BACKEND_API_URL);
    private final UIPost uiPost = UIPost.getInstance();
    private final UIComment uiComment = UIComment.getInstance();
    private final UIView uiView = UIView.getInstance();

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

    public UserApiClient getUserApi() {
        return apiManager.getUserApiClient();
    }

    public PostApiClient getPostApi() {
        return apiManager.getPostApiClient();
    }

    public CommentApiClient getCommentApi() {
        return apiManager.getCommentApiClient();
    }

    public VotingApiClient getVotingApi() {
        return apiManager.getVotingApiClient();
    }

    public UIPost getUiPost() {
        return uiPost;
    }

    public UIComment getUiComment() {
        return uiComment;
    }

    public UIView getUiView() {
        return uiView;
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

    public void disableAccounts(boolean disable) {
        UIView.accountsDisabled = disable;
        if (disable) {
            System.out.println(AnsiColors.toBlue(ACCOUNTS_DISABLED));
            User dummyuser = new User("TEST_USER_MATCHA", "test@gmail.com", "12345678aA!");
            this.setUser(dummyuser);
            this.setLoggedIn(true);
        }
    }

    public void cleanUpData() {
        if (UIView.accountsDisabled) {
            post = null;
            comment = null;
        } else {
            isLoggedIn  = false;
            user = null;
            post = null;
            comment = null;
        }


    }
}
