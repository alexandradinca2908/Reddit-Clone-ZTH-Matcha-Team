package org.example.userinterface;

import org.example.textprocessors.AnsiColors;

public class UIVote {
    private static UIVote instance;
    private static final String ERROR_DELETE_VOTE = AnsiColors.toRed("Failed to delete vote from the database.");
    private static final String ERROR_UPDATE_VOTE = AnsiColors.toRed("Failed to update vote in the database.");
    private static final String ERROR_SAVE_VOTE = AnsiColors.toRed("Failed to save vote in the database.");

    private UIVote() {}

    public static UIVote getInstance() {
        if (instance == null) {
            instance = new UIVote();
        }

        return instance;
    }

    public void printVoteError(String whatError) {
        switch (whatError) {
            case "delete":
                System.out.println(ERROR_DELETE_VOTE);
                break;

            case "update":
                System.out.println(ERROR_UPDATE_VOTE);
                break;

            case "save":
                System.out.println(ERROR_SAVE_VOTE);
                break;

            default:
                break;
        }
    }
}
