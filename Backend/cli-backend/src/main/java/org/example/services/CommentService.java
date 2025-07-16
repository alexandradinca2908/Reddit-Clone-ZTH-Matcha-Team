package org.example.services;
import org.example.dbconnection.DatabaseConnection;
import org.example.models.Post;
import org.example.models.User;
import org.example.models.Comment;
import org.example.loggerobjects.Logger;
import org.example.repositories.CommentRepo;
import org.example.textprocessors.AnsiColors;

import java.io.IOException;
import java.util.Scanner;

public class CommentService extends AnsiColors {
    private static CommentService instance;
    private static final CommentRepo commentRepo = CommentRepo.getInstance();

    Scanner sc = new Scanner(System.in);

    private CommentService() {}

    public static CommentService getInstance() {
        if (instance == null) {
            instance = new CommentService();

            try {
                commentRepo.loadPostComments();
//                commentRepo.loadReplyComments();
            } catch (Exception e) {
                Logger.error("Failed to load comments from the database: " + e.getMessage());
                DatabaseConnection.cannotConnect();
            }

        }
        return instance;
    }

    public void addComment(User user, Post post) {
        System.out.println(AnsiColors.toGreen("Please enter a comment: "));
        String commentText = sc.nextLine();

        for (Post iter : PostService.posts) {
            if (iter.getPostID() == post.getPostID()) {
                Comment comment = new Comment(iter, user, commentText);
                iter.getCommentList().add(comment);
                commentRepo.savePostComment(comment);
                System.out.println(AnsiColors.toGreen("Comment added successfully."));
                return;
            }
        }

        System.out.println(AnsiColors.toRed("Something went wrong while adding a comment!"));
    }

    public void addReply(User user, Comment comment) {
        System.out.println(AnsiColors.toGreen("Please enter a reply: "));
            String replyText = sc.nextLine();
            Logger.fatal("Adding reply!");
            comment.addReply(replyText, user);
    }

    public Comment selectComment(Post post) {
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

        for (Comment comm: post.getCommentList()) {
            if (comm.getCommentID() == cid) {
                return comm;
            }
        }
        throw new IllegalArgumentException(AnsiColors.toRed("Comment with ID " + cid + " not found."));
    }

    public Comment selectReply(Comment comment) {
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

            for(Comment reply : comment.replyList) {
                if(reply.getCommentID() == rid) {
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

    public void voteReply(User user, Comment reply, boolean vote) {
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
