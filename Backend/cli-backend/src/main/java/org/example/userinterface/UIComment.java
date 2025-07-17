package org.example.userinterface;

import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.textprocessors.AnsiColors;
import org.example.textprocessors.TextSymbols;

public class UIComment {
    private static UIComment instance;
    public static final int COMMENT_INDENT = 0;

    public UIComment() {}
    public static UIComment getInstance() {
        if (instance == null) {
            instance = new UIComment();
        }
        return instance;
    }

    public void showAllCommentsAndReplies(Post post, User user) {
        if (post.getCommentList().isEmpty()) {
            System.out.println(AnsiColors.toGreen("\t\t\t=== No comments yet. ==="));
        } else {
            System.out.println(AnsiColors.toGreen("\t=== Showing all comments and replies ==="));
            System.out.println(TextSymbols.UPPER_LEFT_CORNER + TextSymbols.LINE_SEPARATOR);
            String username;
            if (user == null) {
                username = "";
            } else {
                username = user.getUsername();
            }
            for (Comment comment : post.getCommentList()) {
                showComment(username, comment, 0);
            }
        }
    }

    public void showComment(String username, Comment comment, int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        System.out.println(indent + TextSymbols.LEFT_BORDER + AnsiColors.toOrange(String.format(TextSymbols.HEADER,
                comment.getCommentID(), comment.getParentUser().getUsername())));
        System.out.println(indent + TextSymbols.LEFT_BORDER + TextSymbols.addReward(
                comment.getCommentText(), comment.getVotes()));

        String votes;
        if (comment.votingUserID.containsKey(username)) {
            if (comment.votingUserID.get(username) == 1) {
                votes = AnsiColors.toRed("UP " + comment.getVotes()) + " DOWN";
            } else {
                votes = "UP " + AnsiColors.toBlue(comment.getVotes() + " DOWN");
            }
        } else {
            votes = "UP " + comment.getVotes() + " DOWN";
        }
        System.out.print(indent + TextSymbols.LEFT_BORDER + votes);
        System.out.println(" | " + comment.replyList.size() + " replies");
        System.out.println(indent + TextSymbols.LOWER_LEFT_CORNER + TextSymbols.LINE_SEPARATOR);

        for (Comment reply : comment.replyList) {
            showComment(username, reply, indentLevel + 1);
        }
    }
}
