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
        for (Comment comment : post.getCommentList()) {
            showComment(comment, 0);
        }
    }

    private void showComment(Comment comment, int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        System.out.println(indent + AnsiColors.toOrange(String.format(TextSymbols.HEADER,
                comment.getCommentID(), comment.getParentUser().getUsername())));
        System.out.println(indent + AnsiColors.addReward(comment.getCommentText(), comment.getVotes()));
        System.out.print(indent + AnsiColors.toRed("UP ") + comment.getVotes() +
                AnsiColors.toBlue(" DOWN "));
        System.out.println("| " + comment.replyList.size() + " replies");
        System.out.println(indent + TextSymbols.LINE_SEPARATOR);

        for (Comment reply : comment.replyList) {
            showComment(reply, indentLevel + 1);
        }
    }
}
