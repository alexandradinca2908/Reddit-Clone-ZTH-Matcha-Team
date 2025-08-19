package org.example.textprocessors;

import org.example.menu.MenuOption;
import org.example.menu.views.ViewID;

public class InputTranslator {
    public static MenuOption translateInput(String input, ViewID viewID, boolean isLoggedIn) {
        String stringInput = translateInputToString(input, viewID, isLoggedIn);

        return switch (stringInput) {
            case "login" -> MenuOption.LOGIN;
            case "register" -> MenuOption.REGISTER;
            case "show feed" -> MenuOption.SHOW_FEED;
            case "quit" -> MenuOption.QUIT;
            case "create post" -> MenuOption.CREATE_POST;
            case "delete post" -> MenuOption.DELETE_POST;
            case "edit post"  -> MenuOption.EDIT_POST;
            case "logout" -> MenuOption.LOGOUT;
            case "delete account" -> MenuOption.DELETE_ACCOUNT;
            case "expand post" -> MenuOption.EXPAND_POST;
            case "comment" -> MenuOption.COMMENT;
            case "upvote" -> MenuOption.UPVOTE;
            case "downvote" -> MenuOption.DOWNVOTE;
            case "select comment" -> MenuOption.SELECT_COMMENT;
            case "edit comment" -> MenuOption.EDIT_COMMENT;
            case "delete comment" -> MenuOption.DELETE_COMMENT;
            case "reply" -> MenuOption.REPLY;
            case "select reply" -> MenuOption.SELECT_REPLY;
            case "back" -> MenuOption.BACK;
            default -> MenuOption.UNKNOWN_COMMAND;
        };
    }

    private static String translateInputToString(String input, ViewID viewID, boolean isLoggedIn) {
        switch (viewID) {
            case MAIN_MENU:
                if (!isLoggedIn) {
                    return translateMenuInputNotLoggedIn(input);
                } else {
                    return translateMenuInputLoggedIn(input);
                }

            case ON_FEED:
                return translateOnFeedInput(input);

            case ON_POST:
                if (!isLoggedIn) {
                    return translateOnPostInputNotLoggedIn(input);
                } else {
                    return translatePostInputLoggedIn(input);
                }

            case ON_COMMENT:
                return translateOnCommentInput(input);
        }

        return input;
    }

    private static String translateMenuInputNotLoggedIn(String input) {
        // 1. Login, 2. Register, 3. Logout, 4. Quit
        return switch (input) {
            case "1" -> "login";
            case "2" -> "register";
            case "3" -> "show feed";
            case "4" -> "quit";
            default -> input;
        };
    }

    private static String translateMenuInputLoggedIn(String input) {
        //  1. Show feed, 2. Logout, 3. Delete Account, 4. Quit
        return switch (input) {
            case "1" -> "show feed";
            case "2" -> "logout";
            case "3" -> "delete account";
            case "4" -> "quit";
            default -> input;
        };
    }

    private static String translateOnFeedInput(String input) {
        //  1. Expand post, 2. Create post, 3. Delete post, 4. Back, 5. Quit
        return switch (input) {
            case "1" -> "expand post";
            case "2" -> "create post";
            case "3" -> "delete post";
            case "4" -> "back";
            case "5" -> "quit";
            default -> input;
        };
    }

    private static String translateOnPostInputNotLoggedIn(String input) {
        //  1. Return to feed, 2. Quit
        return switch (input) {
            case "1" -> "return to feed";
            case "2" -> "quit";
            default -> input;
        };
    }

    private static String translatePostInputLoggedIn(String input) {
        //  1. Comment, 2. Upvote, 3. Downvote, 4. Edit post, 5. Select comment
        //  6. Back, 7. Logout, 8. Quit
        return switch (input) {
            case "1" -> "comment";
            case "2" -> "upvote";
            case "3" -> "downvote";
            case "4" -> "edit post";
            case "5" -> "select comment";
            case "6" -> "back";
            case "7" -> "quit";
            default -> input;
        };
    }

    private static String translateOnCommentInput(String input) {
        //  1. Reply, 2. Upvote, 3. Downvote, 4. Edit Comment,
        //  5. Delete Comment, 6. Select reply, 7. Back, 8. Quit
        return switch (input) {
            case "1" -> "reply";
            case "2" -> "upvote";
            case "3" -> "downvote";
            case "4" -> "edit comment";
            case "5" -> "delete comment";
            case "6" -> "select reply";
            case "7" -> "back";
            case "8" -> "quit";
            default -> input;
        };
    }
}
