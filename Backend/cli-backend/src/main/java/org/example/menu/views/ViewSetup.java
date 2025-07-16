package org.example.menu.views;

import org.example.menu.Availability;
import org.example.menu.MenuOption;
import org.example.menu.commandexecution.*;
import org.example.menu.commandexecution.mainmenu.*;
import org.example.menu.commandexecution.oncomment.*;
import org.example.menu.commandexecution.onfeed.ExpandPostCommand;
import org.example.menu.commandexecution.onfeed.ReturnToMenuCommand;
import org.example.menu.commandexecution.onpost.*;
import org.example.menu.commandexecution.onreply.DownvoteReplyCommand;
import org.example.menu.commandexecution.onreply.ReturnToCommentCommand;
import org.example.menu.commandexecution.onreply.UpvoteReplyCommand;

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
                MenuOption.EXPAND_POST, new ExpandPostCommand(),
                MenuOption.RETURN_TO_MENU, new ReturnToMenuCommand(),
                MenuOption.QUIT, new QuitCommand()
        ));

        onFeed.setCommands(commands);

        return onFeed;
    }

    protected static View initOnPost() {
        View onPost = new View();

        //  Set id
        onPost.setViewID(ViewID.ON_POST);

        //  Set menu
        LinkedHashMap<MenuOption, Availability> menu = new LinkedHashMap<>();
        menu.put(MenuOption.COMMENT, Availability.LOGGED_IN);
        menu.put(MenuOption.UPVOTE, Availability.LOGGED_IN);
        menu.put(MenuOption.DOWNVOTE, Availability.LOGGED_IN);
        menu.put(MenuOption.SELECT_COMMENT, Availability.LOGGED_IN);
        menu.put(MenuOption.RETURN_TO_FEED, Availability.ANYTIME);
        menu.put(MenuOption.QUIT, Availability.ANYTIME);

        onPost.setMenu(menu);

        HashMap<MenuOption, IMenuCommand> commands = new HashMap<>(Map.of(
                MenuOption.COMMENT, new CommentCommand(),
                MenuOption.UPVOTE, new UpvotePostCommand(),
                MenuOption.DOWNVOTE, new DownvotePostCommand(),
                MenuOption.SELECT_COMMENT, new SelectCommentCommand(),
                MenuOption.RETURN_TO_FEED, new ReturnToFeedCommand(),
                MenuOption.QUIT, new QuitCommand()
        ));

        onPost.setCommands(commands);

        return onPost;
    }

    protected static View initOnComment() {
        View onComment = new View();

        //  Set id
        onComment.setViewID(ViewID.ON_COMMENT);

        //  Set menu
        LinkedHashMap<MenuOption, Availability> menu = new LinkedHashMap<>();
        menu.put(MenuOption.REPLY, Availability.LOGGED_IN);
        menu.put(MenuOption.UPVOTE, Availability.LOGGED_IN);
        menu.put(MenuOption.DOWNVOTE, Availability.LOGGED_IN);
        menu.put(MenuOption.SELECT_REPLY, Availability.LOGGED_IN);
        menu.put(MenuOption.RETURN_TO_POST, Availability.ANYTIME);
        menu.put(MenuOption.QUIT, Availability.ANYTIME);

        onComment.setMenu(menu);

        //  Set commands
        HashMap<MenuOption, IMenuCommand> commands = new HashMap<>(Map.of(
                MenuOption.REPLY, new ReplyCommand(),
                MenuOption.UPVOTE, new UpvoteCommentCommand(),
                MenuOption.DOWNVOTE, new DownvoteCommentCommand(),
                MenuOption.SELECT_REPLY, new SelectReplyCommand(),
                MenuOption.RETURN_TO_POST,new ReturnToPostCommand(),
                MenuOption.QUIT, new QuitCommand()
        ));

        onComment.setCommands(commands);

        return onComment;
    }

    protected static View initOnReply() {
        View onReply = new View();

        //  Set id
        onReply.setViewID(ViewID.ON_REPLY);

        //  Set menu
        LinkedHashMap<MenuOption, Availability> menu = new LinkedHashMap<>();
        menu.put(MenuOption.UPVOTE, Availability.LOGGED_IN);
        menu.put(MenuOption.DOWNVOTE, Availability.LOGGED_IN);
        menu.put(MenuOption.RETURN_TO_COMMENT, Availability.ANYTIME);
        menu.put(MenuOption.QUIT, Availability.ANYTIME);

        onReply.setMenu(menu);

        //  Set commands
        HashMap<MenuOption, IMenuCommand> commands = new HashMap<>(Map.of(
                MenuOption.UPVOTE, new UpvoteReplyCommand(),
                MenuOption.DOWNVOTE, new DownvoteReplyCommand(),
                MenuOption.RETURN_TO_COMMENT, new ReturnToCommentCommand(),
                MenuOption.QUIT, new QuitCommand()
        ));

        onReply.setCommands(commands);

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
