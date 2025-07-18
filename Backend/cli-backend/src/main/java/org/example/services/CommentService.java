package org.example.services;

import org.example.dbconnection.DatabaseConnection;
import org.example.models.Post;
import org.example.models.User;
import org.example.models.Comment;
import org.example.loggerobjects.Logger;
import org.example.repositories.CommentRepo;
import org.example.textprocessors.AnsiColors;
import org.example.textprocessors.ProfanityFilter;
import org.example.userinterface.UIComment;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class CommentService extends AnsiColors {
    private static CommentService instance;
    private static final ProfanityFilter profanityFilter = ProfanityFilter.getInstance();
    private static final CommentRepo commentRepo = CommentRepo.getInstance();
    private static final UIComment uiComment = UIComment.getInstance();

    Scanner sc = new Scanner(System.in);

    private CommentService() {
    }

    public static CommentService getInstance() {
        if (instance == null) {
            instance = new CommentService();

            try {
                commentRepo.load();
            } catch (SQLException e) {
                Logger.error("Failed to load comments from the database: " + e.getMessage());
                DatabaseConnection.cannotConnect();
            }

        }
        return instance;
    }

    public void addComment(User user, Post post) {
        uiComment.pleaseEnter("comment");
        String commentText = sc.nextLine();
        try{
            commentText = profanityFilter.filter(commentText);
        } catch (FileNotFoundException e) {
            System.out.println("Config file could not be found.");
        }


        for (Post iter : PostService.posts) {
            if (iter.getPostID() == post.getPostID()) {
                Comment comment = new Comment(iter, user, commentText);
                iter.getCommentList().add(comment);
                try {
                    commentRepo.savePostComment(comment);
                } catch (SQLException e) {
                    Logger.error("Failed to save comment: " + e.getMessage());
                    System.out.println(AnsiColors.toRed("Failed to save comment to the database."));
                    return;
                }
                uiComment.addedSuccessfully("comment");
                return;
            }
        }
        uiComment.wentWrong("comment");
    }

    public void addReply(User user, Comment comment) {
        uiComment.pleaseEnter("reply");
        String replyText = sc.nextLine();
        try{
            replyText = profanityFilter.filter(replyText);
        } catch (FileNotFoundException e) {
            System.out.println("Config file could not be found.");
        }
        Logger.info("Adding reply!");
        Comment commentReply = new Comment(comment, user, replyText);
        try {
            commentRepo.saveReply(commentReply);
        } catch (SQLException e) {
            Logger.error("Failed to save reply: " + e.getMessage());
            System.out.println(AnsiColors.toRed("Failed to save reply to the database."));
            return;
        }
        comment.replyList.add(commentReply);
        uiComment.addedSuccessfully("reply");
        // TODO - add exception for illegal input
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

        for (Comment comm : post.getCommentList()) {
            if (comm.getCommentID() == cid) {
                return comm;
            }
        }
        throw new IllegalArgumentException(AnsiColors.toRed(String.format("Comment with ID %d not found", cid)));
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

        for (Comment reply : comment.replyList) {
            if (reply.getCommentID() == rid) {
                return reply;
            }
        }

        throw new IllegalArgumentException(AnsiColors.toRed("Comment with ID " + rid + " not found."));
    }

}
