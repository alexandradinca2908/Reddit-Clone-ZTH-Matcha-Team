package org.example;

public class InputTranslator {
    static String translateInput(String input, State currentState, boolean isLoggedIn) {
        switch (currentState) {
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

    static String translateMenuInputNotLoggedIn(String input) {
        // 1. Login, 2. Register, 3. Logout, 4. Quit
        return switch (input) {
            case "1" -> "login";
            case "2" -> "register";
            case "3" -> "show feed";
            case "4" -> "quit";
            default -> "";
        };
    }

    static String translateMenuInputLoggedIn(String input) {
        //  1. Show feed, 2. Create post, 3. Logout, 4. Delete Account, 5. Quit
        return switch (input) {
            case "1" -> "show feed";
            case "2" -> "create post";
            case "3" -> "logout";
            case "4" -> "delete account";
            case "5" -> "quit";
            default -> "";
        };
    }

    static String translateOnFeedInput(String input) {
        //  1. Expand post, 2. Return to menu, 3. Quit
        return switch (input) {
            case "1" -> "expand post";
            case "2" -> "return to menu";
            case "3" -> "quit";
            default -> "";
        };
    }

    static String translateOnPostInputNotLoggedIn(String input) {
        //  1. Return to feed, 2. Quit
        return switch (input) {
            case "1" -> "return to feed";
            case "2" -> "quit";
            default -> "";
        };
    }

    static String translatePostInputLoggedIn(String input) {
        //  1. Comment, 2. Upvote, 3. Downvote, 4. Select comment
        //  5. Return to feed, 6. Logout, 7. Quit
        return switch (input) {
            case "1" -> "comment";
            case "2" -> "upvote";
            case "3" -> "downvote";
            case "4" -> "select comment";
            case "5" -> "return to feed";
            case "6" -> "logout";
            case "7" -> "quit";
            default -> "";
        };
    }

    static String translateOnCommentInput(String input) {
        //  1. Reply, 2. Upvote, 3. Downvote, 4. Return to post, 5. Quit
        return switch (input) {
            case "1" -> "reply";
            case "2" -> "upvote";
            case "3" -> "downvote";
            case "4" -> "return to post";
            case "5" -> "quit";
            default -> "";
        };
    }
}
