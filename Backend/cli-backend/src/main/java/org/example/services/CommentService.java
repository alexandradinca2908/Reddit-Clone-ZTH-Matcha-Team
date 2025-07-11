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
        System.out.println(AnsiColors.toOrange("CID: " + comm.getCommentID() + " | USER: " + comm.getParentUser().getUsername()));
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
            System.out.print("Please enter the CID: ");
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

        System.out.println(indent + AnsiColors.toOrange("RID " + reply.getCommentReplyID() + " | USER: " + reply.getParentUser().getUsername()));
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
            System.out.print("Please enter the RID: ");
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
                    System.out.println(AnsiColors.toOrange("RID: " + reply.getCommentReplyID()+ " | USER: " + reply.getParentUser().getUsername()));
                    System.out.println(reply.getCommentReplyText());
                    System.out.print(AnsiColors.toRed("UP ") + reply.getVotes() + AnsiColors.toBlue(" DOWN \n"));
                    System.out.println(LINE_SEPARATOR);
                    return reply;
                }
            }

            throw new IllegalArgumentException(AnsiColors.toRed("Comment with ID " + rid + " not found."));
    }

    public void voteComment(int userID, int postID, int commentID, String vote) {
        for (Post iter : Post.posts) {
            if (iter.getPostID() == postID) {
                for (Comment comm : iter.commentList) {
                    if (comm.getCommentID() == commentID) {
                        if (vote.equalsIgnoreCase("upvote")) {
                            if (comm.votingUserID.containsKey(userID)) { // am votat deja dar nu stiu ce am votat
                                if (comm.votingUserID.get(userID).equals(1)) { //am votat deja upvote
                                    comm.downvote();
                                    comm.votingUserID.remove(userID);
                                    System.out.println("Upvote removed.");
                                }
                                else {
                                    comm.upvote();
                                    comm.upvote();
                                    comm.votingUserID.put(userID, 1);
                                    System.out.println("Downvote changed to upvote.");
                                }
                            }
                            else {
                                comm.upvote();
                                comm.votingUserID.put(userID, 1);
                                System.out.println("Comment upvoted.");
                            }
                        }
                        else if (vote.equalsIgnoreCase("downvote")) {
                            if (comm.votingUserID.containsKey(userID)) {
                                if(comm.votingUserID.get(userID).equals(-1)) {
                                    comm.upvote();
                                    comm.votingUserID.remove(userID);
                                    System.out.println("Downvote removed.");
                                }
                                else {
                                    comm.downvote();
                                    comm.downvote();
                                    comm.votingUserID.put(userID, -1);
                                    System.out.println("Upvote changed to downvote.");
                                }
                            }
                            else {
                                comm.downvote();
                                comm.votingUserID.put(userID, -1);
                                System.out.println("Comment downvoted.");
                            }
                        }
                    }

                    System.out.println();
                    break;
                }
            }
        }

    }

    public void voteReply(int userID, int postID, int commentID, int commentReplyID, String vote) {
        for (Post post : Post.posts) {
            if (post.getPostID() == postID) {
                for (Comment comm : post.commentList) {
                    if (comm.getCommentID() == commentID) {
                        for(CommentReply reply : comm.replyList) {
                            if(reply.getCommentReplyID() == commentReplyID) {
                                if (vote.equalsIgnoreCase("upvote")) {
                                    if (reply.votingUserID.containsKey(userID)) { // am votat deja dar nu stiu ce am votat
                                        if (reply.votingUserID.get(userID).equals(1)) { //am votat deja upvote
                                            reply.downvote();
                                            reply.votingUserID.remove(userID);
                                            System.out.println("Upvote removed.");
                                        }
                                        else {
                                            reply.upvote();
                                            reply.upvote();
                                            reply.votingUserID.put(userID, 1);
                                            System.out.println("Downvote changed to upvote.");
                                        }
                                    }
                                    else {
                                        reply.upvote();
                                        reply.votingUserID.put(userID, 1);
                                        System.out.println("Reply upvoted.");
                                    }
                                }
                                else if (vote.equalsIgnoreCase("downvote")) {
                                    if (reply.votingUserID.containsKey(userID)) {
                                        if(reply.votingUserID.get(userID).equals(-1)) {
                                            reply.upvote();
                                            reply.votingUserID.remove(userID);
                                            System.out.println("Downvote removed.");
                                        }
                                        else {
                                            reply.downvote();
                                            reply.downvote();
                                            reply.votingUserID.put(userID, -1);
                                            System.out.println("Upvote changed to downvote.");
                                        }
                                    }
                                    else {
                                        reply.downvote();
                                        reply.votingUserID.put(userID, -1);
                                        System.out.println("Reply downvoted.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
