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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewManager {
    private final static UserService userService = UserService.getInstance();
    private final static PostService postService = PostService.getInstance();
    private final static CommentService commentService = CommentService.getInstance();
    private static ViewManager viewManager;
    private HashMap<ViewID, View> views;
    private ViewID currentViewID;
    private boolean isLoggedIn;
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
        if (viewManager == null)
            viewManager = new ViewManager();

        return viewManager;
    }

    private void addView(View view) {
        this.views.put(view.getViewID(), view);
    }

    private void addMultipleViews(View... views) {
        for (View view : views) {
            this.views.put(view.getViewID(), view);
        }
    }

    private void initAllViews() {
        views.put(ViewID.MAIN_MENU, initMainMenu());
        views.put(ViewID.ON_FEED, initOnFeed());
        views.put(ViewID.ON_POST, initOnPost());
        views.put(ViewID.ON_COMMENT, initOnComment());
        views.put(ViewID.ON_REPLY, initOnReply());

    }

    private View initMainMenu() {
        View mainMenu = new View();

        //  Set id
        mainMenu.setViewID(ViewID.MAIN_MENU);

        //  Set menu
        ArrayList<MenuOption> menu = new ArrayList<>(List.of(
                MenuOption.LOGIN,
                MenuOption.REGISTER,
                MenuOption.SHOW_FEED,
                MenuOption.CREATE_POST,
                MenuOption.LOGOUT,
                MenuOption.DELETE_ACCOUNT,
                MenuOption.QUIT
        ));

        mainMenu.setMenu(menu);

        return mainMenu;
    }

    private View initOnFeed() {
        View onFeed = new View();

        //  Set id
        onFeed.setViewID(ViewID.ON_FEED);

        //  Set menu
        ArrayList<MenuOption> menu = new ArrayList<>(List.of(
                MenuOption.EXPAND_POST,
                MenuOption.RETURN_TO_MENU,
                MenuOption.QUIT
        ));

        onFeed.setMenu(menu);

        return onFeed;
    }

    private View initOnPost() {
        View onPost = new View();

        //  Set id
        onPost.setViewID(ViewID.ON_POST);

        //  Set menu
        ArrayList<MenuOption> menu = new ArrayList<>(List.of(
                MenuOption.COMMENT,
                MenuOption.UPVOTE,
                MenuOption.DOWNVOTE,
                MenuOption.SELECT_COMMENT,
                MenuOption.RETURN_TO_FEED,
                MenuOption.QUIT
        ));

        onPost.setMenu(menu);

        return onPost;
    }

    private View initOnComment() {
        View onComment = new View();

        //  Set id
        onComment.setViewID(ViewID.ON_COMMENT);

        //  Set menu
        ArrayList<MenuOption> menu = new ArrayList<>(List.of(
                MenuOption.REPLY,
                MenuOption.UPVOTE,
                MenuOption.DOWNVOTE,
                MenuOption.SELECT_REPLY,
                MenuOption.RETURN_TO_POST,
                MenuOption.QUIT
        ));

        onComment.setMenu(menu);

        return onComment;
    }

    private View initOnReply() {
        View onReply = new View();

        //  Set id
        onReply.setViewID(ViewID.ON_REPLY);

        //  Set menu
        ArrayList<MenuOption> menu = new ArrayList<>(List.of(
                MenuOption.UPVOTE,
                MenuOption.DOWNVOTE,
                MenuOption.RETURN_TO_COMMENT,
                MenuOption.QUIT
        ));

        onReply.setMenu(menu);

        return onReply;
    }

    private void linkViews() {
        linkMainMenu();
        linkOnFeed();
        linkOnPost();
        linkOnComment();
        linkOnReply();
    }

    private void linkMainMenu() {
        HashMap<ViewID, View> nextViewsMainMenu = new HashMap<>();
        nextViewsMainMenu.put(ViewID.ON_FEED, views.get(ViewID.ON_FEED));

        views.get(ViewID.MAIN_MENU).setNextViews(nextViewsMainMenu);
    }

    private void linkOnFeed() {
        HashMap<ViewID, View> nextViewsOnFeed = new HashMap<>();
        nextViewsOnFeed.put(ViewID.MAIN_MENU, views.get(ViewID.MAIN_MENU));
        nextViewsOnFeed.put(ViewID.ON_POST, views.get(ViewID.ON_FEED));

        views.get(ViewID.ON_FEED).setNextViews(nextViewsOnFeed);
    }

    private void linkOnPost() {
        HashMap<ViewID, View> nextViewsOnPost = new HashMap<>();
        nextViewsOnPost.put(ViewID.ON_FEED, views.get(ViewID.ON_FEED));
        nextViewsOnPost.put(ViewID.ON_COMMENT, views.get(ViewID.ON_COMMENT));

        views.get(ViewID.ON_POST).setNextViews(nextViewsOnPost);
    }

    private void linkOnComment() {
        HashMap<ViewID, View> nextViewsOnComment = new HashMap<>();
        nextViewsOnComment.put(ViewID.ON_POST, views.get(ViewID.ON_POST));
        nextViewsOnComment.put(ViewID.ON_REPLY, views.get(ViewID.ON_REPLY));

        views.get(ViewID.ON_COMMENT).setNextViews(nextViewsOnComment);
    }

    private void linkOnReply() {
        HashMap<ViewID, View> nextViewsOnReply = new HashMap<>();
        nextViewsOnReply.put(ViewID.ON_COMMENT, views.get(ViewID.ON_COMMENT));

        views.get(ViewID.ON_REPLY).setNextViews(nextViewsOnReply);
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
