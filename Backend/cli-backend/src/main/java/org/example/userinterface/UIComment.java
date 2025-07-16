package org.example.userinterface;

import org.example.models.Comment;
import org.example.models.Post;
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

    public void showAllCommentsAndReplies(Post post) {
        if (post.getCommentList().isEmpty()) {
            System.out.println(AnsiColors.toGreen("\t\t\t=== No comments yet. ==="));
        } else {
            System.out.println(AnsiColors.toGreen("\t=== Showing all comments and replies ==="));
            System.out.println(TextSymbols.UPPER_LEFT_CORNER + TextSymbols.LINE_SEPARATOR);
            for (Comment comment : post.getCommentList()) {
                showComment(comment, 0);
            }
        }

    }

    public void showAllCommentsAndReplies(Comment comm) {
        for (Comment comment : comm.getReplyList()) {
            showComment(comment, 0);
        }
    }

    private void showComment(Comment comment, int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        System.out.println(indent + TextSymbols.LEFT_BORDER + AnsiColors.toOrange(String.format(TextSymbols.HEADER,
                comment.getCommentID(), comment.getParentUser().getUsername())));
        System.out.println(indent + TextSymbols.LEFT_BORDER + TextSymbols.addReward(
                comment.getCommentText(), comment.getVotes()));
        System.out.print(indent + TextSymbols.LEFT_BORDER + AnsiColors.toRed("UP ") + comment.getVotes() +
                AnsiColors.toBlue(" DOWN "));
        System.out.println("| " + comment.replyList.size() + " replies");
        System.out.println(indent + TextSymbols.LOWER_LEFT_CORNER + TextSymbols.LINE_SEPARATOR);

        for (Comment reply : comment.replyList) {
            showComment(reply, indentLevel + 1);
        }
    }
}
