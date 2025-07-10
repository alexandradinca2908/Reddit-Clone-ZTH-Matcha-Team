package org.example.services;
import org.example.entities.CommentReply;
import org.example.entities.Post;
import org.example.entities.User;
import org.example.entities.Comment;
import org.example.textprocessors.AnsiColors;

import java.util.Scanner;

public class CommentService {
    Scanner sc = new Scanner(System.in);
    private Post post;
    private User user;

    public void addComment(User user, Post post) {
        System.out.println(AnsiColors.toGreen("Please enter a comment: "));
        String commentText = sc.nextLine();

        for (Post iter : Post.posts) {
            if (iter.getPostID() == post.getPostID()) {
                iter.addComment(user, commentText);
                System.out.println(AnsiColors.toGreen("Successfully added comment!"));
                return;
            }
        }

        System.out.println(AnsiColors.toRed("Error: Post not found."));
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

        System.out.println(AnsiColors.toRed("Comment not found in this post."));
    }


    public Comment selectComment(User user, Post post) {
        System.out.println("Please enter the CID: ");
        String cid = sc.nextLine();
        for (Comment comm: post.commentList) {
            if (comm.getCommentID() == Integer.parseInt(cid)) {
                return comm;
            }
        }
        throw new IllegalArgumentException(AnsiColors.toRed("Comment with ID " + cid + " not found."));
    }

    public void voteComment(int userID, int postID, int commentID, String vote) {
        for(Post iter : Post.posts) {
            if(iter.getPostID() == postID) {
                for(Comment comm : iter.commentList) {
                    if(comm.getCommentID() == commentID) {
                        if(vote.equalsIgnoreCase("upvote")) {
                            if(comm.votingUserID.containsKey(userID)) { // am votat deja dar nu stiu ce am votat
                                if(comm.votingUserID.get(userID).equals(1)) { //am votat deja upvote
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
                        else if(vote.equalsIgnoreCase("downvote")) {
                            if(comm.votingUserID.containsKey(userID)) {
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
