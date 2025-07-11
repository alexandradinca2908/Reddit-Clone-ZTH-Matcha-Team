package org.example.services;
import org.example.entities.CommentReply;
import org.example.entities.Post;
import org.example.entities.User;
import org.example.entities.Comment;
import org.example.loggerobjects.LogManager;
import org.example.loggerobjects.Logger;
import org.example.textprocessors.AnsiColors;

import java.util.Scanner;

public class CommentService extends AnsiColors {
    Scanner sc = new Scanner(System.in);
    private Post post;
    private User user;
    public static final int COMMENT_INDENT = 0;

    public void addComment(User user, Post post) {
        System.out.println(AnsiColors.toGreen("Please enter a comment: "));
        String commentText = sc.nextLine();

        for (Post iter : Post.posts) {
            if (iter.getPostID() == post.getPostID()) {
                iter.addComment(user, commentText);
                System.out.println(AnsiColors.toGreen("Comment added successfully."));
                return;
            }
        }

        System.out.println(AnsiColors.toRed("Something went wrong while adding a comment!"));
    }

    public void addReply(User user, Post post, Comment comment) {
        System.out.println(AnsiColors.toGreen("Please enter a reply: "));
        String replyText = sc.nextLine();
        Logger.fatal("Adding reply!");
        for (Comment comm : post.commentList) {
            if (comm.getCommentID() == comment.getCommentID()) {
                CommentReply reply = new CommentReply(comm, user, replyText);
                comm.replyList.add(reply);
                System.out.println(AnsiColors.toGreen("Reply added successfully."));
                return;
            }
        }

        System.out.println(AnsiColors.toRed("Something went wrong while adding a reply!"));
    }


    public void printComment(Comment comm, int indentLevel) {
        System.out.println(AnsiColors.toOrange("ID: " + comm.getCommentID() + " | USER: " + comm.getParentUser().getUsername()));
        System.out.println(comm.getCommentText());
        System.out.print(AnsiColors.toRed("UP ") + comm.getVoteCount() + AnsiColors.toBlue(" DOWN "));
        System.out.println("| " + comm.replyList.size() + " replies");
        System.out.println(LINE_SEPARATOR);

        for (CommentReply reply : comm.replyList) {
            printReply(reply, indentLevel + 1);
        }
    }

    public Comment selectComment(User user, Post post) {
        int cid;
        while (true) {
            System.out.print("Please enter the CommentID: ");
            try {
                cid = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(AnsiColors.toYellow("Invalid input. Please enter a valid number."));
            }
        }

        for (Comment comm: post.commentList) {
            if (comm.getCommentID() == cid) {
                // show comment
                int indentLevel = 0;

                printComment(comm, COMMENT_INDENT);

                return comm;
            }
        }
        throw new IllegalArgumentException(AnsiColors.toRed("Comment with ID " + cid + " not found."));
    }

    public void printReply(CommentReply reply, int indentLevel) {
        String indent = "    ".repeat(indentLevel);

        System.out.println(indent + AnsiColors.toOrange("ID " + reply.getCommentReplyID() + " | USER: " + reply.getParentUser().getUsername()));
        System.out.println(AnsiColors.addReward(reply.getCommentReplyText(), reply.getVotes()));
        System.out.print(indent + AnsiColors.toRed("UP ") + reply.getVotes() + AnsiColors.toBlue(" DOWN "));
        System.out.println("| " + reply.commentReplies.size() + " replies");
        System.out.println(indent + LINE_SEPARATOR);

        // daca o sa avem nested replies
        for (CommentReply nested : reply.getCommentReplies()) {
            printReply(nested, indentLevel + 1);
        }
    }

    public CommentReply selectReply(User user, Comment comment) {
        int rid;
        while (true) {
            System.out.print("Please enter the ReplyID: ");
            try {
                rid = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println(AnsiColors.toYellow("Invalid input. Please enter a valid number."));
            }
        }

            for(CommentReply reply : comment.replyList) {
                if(reply.getCommentReplyID() == rid) {
                    //show reply
                    System.out.println(AnsiColors.toOrange("ID: " + reply.getCommentReplyID()+ " | USER: " + reply.getParentUser().getUsername()));
                    System.out.println(reply.getCommentReplyText());
                    System.out.print(AnsiColors.toRed("UP ") + reply.getVotes() + AnsiColors.toBlue(" DOWN \n"));
                    System.out.println(LINE_SEPARATOR);
                    return reply;
                }
            }

            throw new IllegalArgumentException(AnsiColors.toRed("Comment with ID " + rid + " not found."));
    }

    public void voteComment(User user, Comment comment, boolean vote) {
        if(vote) {
            if(comment.votingUserID.containsKey(user.getUserID())) {
                if(comment.votingUserID.get(user.getUserID()).equals(1)) {
                    comment.downvote();
                    comment.votingUserID.remove(user.getUserID());
                }
                else {
                    comment.upvote();
                    comment.upvote();
                    comment.votingUserID.put(user.getUserID(), 1);
                }
            }
            else {
                comment.upvote();
                comment.votingUserID.put(user.getUserID(), 1);
            }
        }
        else {
            if(comment.votingUserID.containsKey(user.getUserID())) {
                if(comment.votingUserID.get(user.getUserID()).equals(-1)) {
                    comment.upvote();
                    comment.votingUserID.remove(user.getUserID());
                }
                else {
                    comment.downvote();
                    comment.downvote();
                    comment.votingUserID.put(user.getUserID(), -1);
                }
            }
            else {
                comment.downvote();
                comment.votingUserID.put(user.getUserID(), -1);
            }
        }
    }

    public void voteReply(User user, CommentReply reply, boolean vote) {
        if(vote) {
            if(reply.votingUserID.containsKey(user.getUserID())) {
                if(reply.votingUserID.get(user.getUserID()).equals(1)) {
                    reply.downvote();
                    reply.votingUserID.remove(user.getUserID());
                }
                else {
                    reply.upvote();
                    reply.upvote();
                    reply.votingUserID.put(user.getUserID(), 1);
                }
            }
            else {
                reply.upvote();
                reply.votingUserID.put(user.getUserID(), 1);
            }
        }
        else {
            if(reply.votingUserID.containsKey(user.getUserID())) {
                if(reply.votingUserID.get(user.getUserID()).equals(-1)) {
                    reply.upvote();
                    reply.votingUserID.remove(user.getUserID());
                }
                else {
                    reply.downvote();
                    reply.downvote();
                    reply.votingUserID.put(user.getUserID(), -1);
                }
            }
            else {
                reply.downvote();
                reply.votingUserID.put(user.getUserID(), -1);
            }
        }
    }
}
