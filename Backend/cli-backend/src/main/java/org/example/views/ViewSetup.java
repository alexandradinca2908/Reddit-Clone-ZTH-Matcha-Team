package org.example.views;

import org.example.views.commandexecution.*;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ViewSetup {
    protected static View initMainMenu() {
        View mainMenu = new View();

        //  Set id
        mainMenu.setViewID(ViewID.MAIN_MENU);

        //  Set menu
        LinkedHashMap<MenuOption, Availability> menu = new LinkedHashMap<>();
        menu.put(MenuOption.LOGIN, Availability.LOGGED_OUT);
        menu.put(MenuOption.REGISTER, Availability.LOGGED_OUT);
        menu.put(MenuOption.SHOW_FEED, Availability.ANYTIME);
        menu.put(MenuOption.CREATE_POST, Availability.LOGGED_IN);
        menu.put(MenuOption.DELETE_ACCOUNT, Availability.LOGGED_IN);
        menu.put(MenuOption.LOGOUT, Availability.LOGGED_IN);
        menu.put(MenuOption.QUIT, Availability.ANYTIME);

        mainMenu.setMenu(menu);

        //  Set commands
        HashMap<MenuOption, IMenuCommand> commands = new HashMap<>(Map.of(
                MenuOption.LOGIN, new LoginCommand(),
                MenuOption.REGISTER, new RegisterCommand(),
                MenuOption.SHOW_FEED, new ShowFeedCommand(),
                MenuOption.CREATE_POST, new CreatePostCommand(),
                MenuOption.LOGOUT, new LogoutCommand(),
                MenuOption.DELETE_ACCOUNT, new DeleteAccountCommand(),
                MenuOption.QUIT, new QuitCommand()
        ));

        mainMenu.setCommands(commands);

        return mainMenu;
    }

    protected static View initOnFeed() {
        View onFeed = new View();

        //  Set id
        onFeed.setViewID(ViewID.ON_FEED);

        //  Set menu
        LinkedHashMap<MenuOption, Availability> menu = new LinkedHashMap<>();
        menu.put(MenuOption.EXPAND_POST, Availability.ANYTIME);
        menu.put(MenuOption.RETURN_TO_MENU, Availability.ANYTIME);
        menu.put(MenuOption.QUIT, Availability.ANYTIME);

        onFeed.setMenu(menu);

        //  Set commands
        HashMap<MenuOption, IMenuCommand> commands = new HashMap<>(Map.of(
                MenuOption.EXPAND_POST, new ,
                MenuOption.RETURN_TO_MENU, Availability.ANYTIME,
                MenuOption.QUIT, Availability.ANYTIME
        ));

        onFeed.setCommands(commands);

        return onFeed;
    }

    protected static View initOnPost() {
        View onPost = new View();

        //  Set id
        onPost.setViewID(ViewID.ON_POST);

        //  Set menu
        LinkedHashMap<MenuOption, Availability> menu = new LinkedHashMap<>(Map.of(
                MenuOption.COMMENT, Availability.LOGGED_IN,
                MenuOption.UPVOTE, Availability.LOGGED_IN,
                MenuOption.DOWNVOTE, Availability.LOGGED_IN,
                MenuOption.SELECT_COMMENT, Availability.LOGGED_IN,
                MenuOption.RETURN_TO_FEED, Availability.ANYTIME,
                MenuOption.QUIT, Availability.ANYTIME
        ));

        onPost.setMenu(menu);

        return onPost;
    }

    protected static View initOnComment() {
        View onComment = new View();

        //  Set id
        onComment.setViewID(ViewID.ON_COMMENT);

        //  Set menu
        LinkedHashMap<MenuOption, Availability> menu = new LinkedHashMap<>(Map.of(
                MenuOption.REPLY, Availability.LOGGED_IN,
                MenuOption.UPVOTE, Availability.LOGGED_IN,
                MenuOption.DOWNVOTE, Availability.LOGGED_IN,
                MenuOption.SELECT_REPLY, Availability.LOGGED_IN,
                MenuOption.RETURN_TO_POST, Availability.ANYTIME,
                MenuOption.QUIT, Availability.ANYTIME
        ));

        onComment.setMenu(menu);

        return onComment;
    }

    protected static View initOnReply() {
        View onReply = new View();

        //  Set id
        onReply.setViewID(ViewID.ON_REPLY);

        //  Set menu
        LinkedHashMap<MenuOption, Availability> menu = new LinkedHashMap<>(Map.of(
                MenuOption.UPVOTE, Availability.LOGGED_IN,
                MenuOption.DOWNVOTE, Availability.LOGGED_IN,
                MenuOption.RETURN_TO_COMMENT, Availability.ANYTIME,
                MenuOption.QUIT, Availability.ANYTIME
        ));

        onReply.setMenu(menu);

        return onReply;
    }

    protected static void linkViews(HashMap<ViewID, View> views) {
        linkMainMenu(views);
        linkOnFeed(views);
        linkOnPost(views);
        linkOnComment(views);
        linkOnReply(views);
    }

    private static void linkMainMenu(HashMap<ViewID, View> views) {
        HashMap<ViewID, View> nextViewsMainMenu = new HashMap<>();
        nextViewsMainMenu.put(ViewID.ON_FEED, views.get(ViewID.ON_FEED));

        views.get(ViewID.MAIN_MENU).setNextViews(nextViewsMainMenu);
    }

    private static void linkOnFeed(HashMap<ViewID, View> views) {
        HashMap<ViewID, View> nextViewsOnFeed = new HashMap<>();
        nextViewsOnFeed.put(ViewID.MAIN_MENU, views.get(ViewID.MAIN_MENU));
        nextViewsOnFeed.put(ViewID.ON_POST, views.get(ViewID.ON_FEED));

        views.get(ViewID.ON_FEED).setNextViews(nextViewsOnFeed);
    }

    private static void linkOnPost(HashMap<ViewID, View> views) {
        HashMap<ViewID, View> nextViewsOnPost = new HashMap<>();
        nextViewsOnPost.put(ViewID.ON_FEED, views.get(ViewID.ON_FEED));
        nextViewsOnPost.put(ViewID.ON_COMMENT, views.get(ViewID.ON_COMMENT));

        views.get(ViewID.ON_POST).setNextViews(nextViewsOnPost);
    }

    private static void linkOnComment(HashMap<ViewID, View> views) {
        HashMap<ViewID, View> nextViewsOnComment = new HashMap<>();
        nextViewsOnComment.put(ViewID.ON_POST, views.get(ViewID.ON_POST));
        nextViewsOnComment.put(ViewID.ON_REPLY, views.get(ViewID.ON_REPLY));

        views.get(ViewID.ON_COMMENT).setNextViews(nextViewsOnComment);
    }

    private static void linkOnReply(HashMap<ViewID, View> views) {
        HashMap<ViewID, View> nextViewsOnReply = new HashMap<>();
        nextViewsOnReply.put(ViewID.ON_COMMENT, views.get(ViewID.ON_COMMENT));

        views.get(ViewID.ON_REPLY).setNextViews(nextViewsOnReply);
    }
}
