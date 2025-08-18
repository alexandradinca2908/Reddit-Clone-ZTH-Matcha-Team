package org.example.menu;

public enum MenuOption {
    LOGIN,
    REGISTER,
    SHOW_FEED,
    QUIT,
    CREATE_POST,
    DELETE_POST,
    EDIT_POST,
    LOGOUT,
    DELETE_ACCOUNT,
    EXPAND_POST,
    COMMENT,
    UPVOTE,
    DOWNVOTE,
    SELECT_COMMENT,
    BACK,
    REPLY,
    SELECT_REPLY,
    UNKNOWN_COMMAND;

    @Override
    public String toString() {
        String lowercase = name().toLowerCase();
        String spaced = lowercase.replace('_', ' ');

        return spaced.substring(0, 1).toUpperCase() + spaced.substring(1);
    }
}
