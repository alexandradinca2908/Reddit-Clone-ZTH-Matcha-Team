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
                System.out.println(AnsiColors.toOrange("CID: " + comm.getCommentID() + " | USER: " + comm.getParentUser().getUsername()));
                System.out.println(comm.getCommentText());
                System.out.print(AnsiColors.toRed("UP ") + comm.getVoteCount() + AnsiColors.toBlue(" DOWN "));
                System.out.println("| " + comm.replyList.size() + " replies");
                System.out.println(LINE_SEPARATOR);

                return comm;
            }
        }
        throw new IllegalArgumentException(AnsiColors.toRed("Comment with ID " + cid + " not found."));
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
                                }
                                else {
                                    comm.upvote();
                                    comm.upvote();
                                    comm.votingUserID.put(userID, 1);
                                }
                            }
                            else {
                                comm.upvote();
                                comm.votingUserID.put(userID, 1);
                            }
                        }
                        else if (vote.equalsIgnoreCase("downvote")) {
                            if (comm.votingUserID.containsKey(userID)) {
                                if(comm.votingUserID.get(userID).equals(-1)) {
                                    comm.upvote();
                                    comm.votingUserID.remove(userID);
                                }
                                else {
                                    comm.downvote();
                                    comm.downvote();
                                    comm.votingUserID.put(userID, -1);
                                }
                            }
                            else {
                                comm.downvote();
                                comm.votingUserID.put(userID, -1);
                            }
                        }
                    }
                    break;
                }
            }
        }

    }
}
