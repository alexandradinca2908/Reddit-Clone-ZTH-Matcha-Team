package org.example.views;

public enum MenuOption {
    LOGIN,
    REGISTER,
    SHOW_FEED,
    QUIT,
    CREATE_POST,
    LOGOUT,
    DELETE_ACCOUNT,
    EXPAND_POST,
    RETURN_TO_MENU,
    COMMENT,
    UPVOTE,
    DOWNVOTE,
    SELECT_COMMENT,
    RETURN_TO_FEED,
    REPLY,
    SELECT_REPLY,
    RETURN_TO_POST,
    RETURN_TO_COMMENT,
    UNKNOWN_COMMAND;

    @Override
    public String toString() {
        String lowercase = name().toLowerCase();
        String spaced = lowercase.replace('_', ' ');

        return spaced.substring(0, 1).toUpperCase() + spaced.substring(1);
    }
}
