package org.example.userinterface;

import org.example.entities.Comment;
import org.example.entities.Post;
import org.example.entities.User;
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

    public void showAllComments(User user, Post post) {
        for (Comment comm : post.commentList) {
            if (comm.getParentUser().getUserID() == user.getUserID()) {
                showOneComment(comm, 0);
            }
        }
    }

    public void showOneComment(Comment comm, int indentLevel) {
        System.out.println(AnsiColors.toOrange("ID: " + comm.getCommentID() + " | USER: " + comm.getParentUser().getUsername()));
        System.out.println(comm.getCommentText());
        System.out.print(AnsiColors.toRed("UP ") + comm.getVoteCount() + AnsiColors.toBlue(" DOWN "));
        System.out.println("| " + comm.replyList.size() + " replies");
        System.out.println(TextSymbols.LINE_SEPARATOR);

        for (Comment reply : comm.replyList) {
            showOneReply(reply, indentLevel + 1);
        }
    }
    public void showOneReply(Comment reply, int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        System.out.println(indent + AnsiColors.toOrange("ID: " + reply.getCommentID() + " | USER: " + reply.getParentUser().getUsername()));
        System.out.println(indent + AnsiColors.addReward(reply.getCommentText(), reply.getVotes()));
        System.out.print(indent + AnsiColors.toRed("UP ") + reply.getVotes() + AnsiColors.toBlue(" DOWN "));
        System.out.println("| " + reply.replyList.size() + " replies");
        System.out.println(indent + TextSymbols.LINE_SEPARATOR);
    }

    public void showAllCommentsAndReplies(Post post) {
        for (Comment comment : post.commentList) {
            showOneCommentAndReplies(comment, 0);
        }
    }

    private void showOneCommentAndReplies(Comment comment, int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        System.out.println(indent + AnsiColors.toOrange(String.format(TextSymbols.HEADER,
                comment.getCommentID(), comment.getParentUser().getUsername())));
        System.out.println(indent + AnsiColors.addReward(comment.getCommentText(), comment.getVotes()));
        System.out.print(indent + AnsiColors.toRed("UP ") + comment.getVoteCount() + AnsiColors.toBlue(" DOWN "));
        System.out.println("| " + comment.replyList.size() + " replies");

        System.out.println(indent + TextSymbols.LINE_SEPARATOR);

        for (Comment reply : comment.replyList) {
            showOneCommentAndReplies(reply, indentLevel + 1);
        }
    }

}
