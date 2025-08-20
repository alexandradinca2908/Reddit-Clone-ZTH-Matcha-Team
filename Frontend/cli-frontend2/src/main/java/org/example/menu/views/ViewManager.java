package org.example.menu.views;

import com.google.gson.Gson;
import org.example.loggerobjects.Logger;
import org.example.models.*;
import org.example.services.ServiceManager;
import org.example.textprocessors.AnsiColors;
import org.example.userinterface.*;

import java.net.http.HttpClient;
import java.util.HashMap;

public class ViewManager {
    private static final String ACCOUNTS_DISABLED = "Accounts have been disabled! Logging in as TEST USER";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    private final ServiceManager serviceManager = ServiceManager.getInstance(client, gson);

    private final UIPost uiPost = UIPost.getInstance();
    private final UIComment uiComment = UIComment.getInstance();
    private final UIView uiView = UIView.getInstance();

    private static ViewManager viewManager;
    private HashMap<ViewID, View> views;
    private ViewID currentViewID;
    protected boolean isLoggedIn;
    private User user;
    private Post post;
    private Comment parentComment;
    private Comment comment;
    private Subreddit subreddit;

    private ViewManager() {
        this.views = new HashMap<>();
        currentViewID = ViewID.MAIN_MENU;
        subreddit = new Subreddit("echipa1_general", "echipa1_general");
        isLoggedIn = false;
    }

    public static ViewManager getInstance() {
        if (viewManager == null) {
            viewManager = new ViewManager();
            viewManager.initAllViews();
            viewManager.initFeed(viewManager.getSubreddit());
        }

        return viewManager;
    }

    private void initFeed(Subreddit subreddit) {
        serviceManager.getPostService().populateSubreddit(subreddit);
        if (subreddit.getPosts().isEmpty()) {
            System.err.println("Failed to load feed");
        }
    }

    private void initAllViews() {
        views.put(ViewID.MAIN_MENU, ViewSetup.initMainMenu());
        views.put(ViewID.ON_FEED, ViewSetup.initOnFeed());
        views.put(ViewID.ON_POST, ViewSetup.initOnPost());
        views.put(ViewID.ON_COMMENT, ViewSetup.initOnComment());

        ViewSetup.linkViews(views);
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
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

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
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

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
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
            User dummyuser = new User("current_user", "test@gmail.com", "12345678aA!");
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
