package org.example.repositories;

import org.example.dbconnection.DatabaseConnection;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.loggerobjects.Logger;
import org.example.services.PostService;
import org.example.services.UserService;

import java.sql.*;

public class CommentRepo {
    private static CommentRepo instance;

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

    public void saveReply(Comment comment) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = "INSERT INTO comment (username, parent_commentID, text) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, comment.getParentUser().getUsername());
            pstmt.setInt(2, comment.getParentComment().getCommentID());
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

    public void load() throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }
        String sql = "SELECT commentID, username, parent_postID, parent_commentID, text FROM comment";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int commentId = rs.getInt("commentID");
                int parent_postId = rs.getInt("parent_postID");
                if (rs.wasNull()) {
                    parent_postId = -1;
                }
                int parent_commentId = rs.getInt("parent_commentID");
                if (rs.wasNull()) {
                    parent_commentId = -1;
                }
                String username = rs.getString("username");
                String text = rs.getString("text");

                User parentUser = UserService.getInstance().findByUsername(username);
                if (parentUser == null) {
                    parentUser = new User("[deleted user]", "", "");
                }

                if (parent_postId != -1) {
                    Post parentPost = PostService.getInstance().findById(parent_postId);

                    Comment comment = new Comment(parentPost, parentUser, text);
                    comment.setCommentID(commentId);

                    parentPost.getCommentList().add(comment);
                }
                else if (commentId != -1) {
                    Comment parentComment = Comment.findById(parent_commentId);
                    Comment reply = new Comment(parentComment, parentUser, text);

                    reply.setCommentID(commentId);
                    parentComment.addReply(reply);
                }
                else {
                    Logger.warn("Warning: Could not load comment " + commentId +
                            " because its parent post or user could not be found.");
                    throw new UnsupportedOperationException("Implement loading comments wihout parentUser");
                }
            }
        }
    }
}
