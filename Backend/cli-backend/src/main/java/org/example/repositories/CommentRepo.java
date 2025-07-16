package org.example.repositories;

import jdk.jshell.spi.ExecutionControl;
import org.example.dbconnection.DatabaseConnection;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.loggerobjects.Logger;

import java.sql.*;

import static org.example.services.PostService.postRepo;
import static org.example.services.UserService.userRepo;

public class CommentRepo {
    private static CommentRepo instance;

    private CommentRepo() {}

    public static CommentRepo getInstance() {
        if (instance == null) {
            instance = new CommentRepo();
        }
        return instance;
    }

    public void savePostComment(Comment comment) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = "INSERT INTO comment (username, parent_postID, text) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, comment.getParentUser().getUsername());
            pstmt.setInt(2, comment.getParentPost().getPostID());
            pstmt.setString(3, comment.getCommentText());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        comment.setCommentID(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating comment failed, no ID obtained.");
                    }
                }
            }
        }
    }

    public void loadPostComments() throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }
        String sql = "SELECT commentID, username, parent_postID, text FROM comment";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int commentId = rs.getInt("commentID");
                int parent_postId = rs.getInt("parent_postID");
                String username = rs.getString("username");
                String text = rs.getString("text");

                Post parentPost = postRepo.findById(parent_postId);
                User parentUser = userRepo.findByUsername(username);

                if (parentPost != null && parentUser != null) {
                    Comment comment = new Comment(parentPost, parentUser, text);
                    comment.setCommentID(commentId);

                    // TODO: nu cred ca e good code practice aceasta linie
                    // trebuie refactorizata la un moment dat
                    parentPost.getCommentList().add(comment);
                } else {
                    Logger.warn("Warning: Could not load comment " + commentId +
                            " because its parent post or user could not be found.");
                    throw new UnsupportedOperationException("Implement loading comments wihout parentUser");
                }
            }
        }
    }

    public void loadReplies() throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = "SELECT commentID, username, parent_commentID, text FROM comment";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int replyId = rs.getInt("commentID");
                int commentId = rs.getInt("parent_commentID");
                String username = rs.getString("username");
                String text = rs.getString("text");

                Comment comment = findById(commentId);
                User parentUser = userRepo.findByUsername(username);

                if (comment != null && parentUser != null) {
                    Comment reply = new Comment(comment, parentUser, text);

                    reply.setCommentID(commentId);
                    // TODO: add reply to the comment's reply list
                } else {
                    Logger.warn("Warning: Could not load comment " + commentId +
                            " because its parent post or user could not be found.");
                }
            }
        }
    }

    private Comment findById(int parentCommentId) {

        return null;
    }
}
