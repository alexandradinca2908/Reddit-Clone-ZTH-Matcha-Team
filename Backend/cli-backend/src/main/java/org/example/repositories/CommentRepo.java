package org.example.repositories;

import org.example.dbconnection.DatabaseConnection;
import org.example.models.Comment;
import org.example.models.Post;
import org.example.models.User;
import org.example.loggerobjects.Logger;
import org.example.services.PostService;
import org.example.services.UserService;

import java.sql.*;
import java.util.UUID;

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

            pstmt.setString(1, comment.getUsername());
            pstmt.setObject(2, comment.getParentPost().getPostID(), java.sql.Types.OTHER);
            pstmt.setString(3, comment.getCommentText());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        comment.setCommentID(generatedKeys.getObject(1, UUID.class));
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

            pstmt.setString(1, comment.getUsername());
            pstmt.setInt(2, comment.getParentComment().getDisplayIndex());
            pstmt.setString(3, comment.getCommentText());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        comment.setCommentID(generatedKeys.getObject(1, UUID.class));
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

        String sql = """
            SELECT c.comment_id, c.parent_post_id, c.parent_comment_id, c.text, pr.username
            FROM comment c
            LEFT JOIN profile pr ON c.profile_id = pr.profile_id
            WHERE c.is_deleted = FALSE
        """;

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                UUID commentId = rs.getObject("comment_id", UUID.class);
                UUID parentPostId = rs.getObject("parent_post_id", UUID.class);
                UUID parentCommentId = rs.getObject("parent_comment_id", UUID.class);
                String text = rs.getString("text");

                String username = rs.getString("username");



                if (parentPostId != null) {
                    Post post = PostService.findById(parentPostId);
                    if (post != null) {
                        Comment comment = new Comment(post, username, text);
                        comment.setCommentID(commentId);
                        post.getCommentList().add(comment);
                    } else {
                        Logger.warn("Post not found for comment " + commentId);
                    }
                } else if (parentCommentId != null) {
                    Comment parentComment = Comment.findById(parentCommentId);
                    if (parentComment != null) {
                        Comment comment = new Comment(parentComment, username, text);
                        comment.setCommentID(commentId);
                        parentComment.addReply(comment);
                    } else {
                        Logger.warn("Parent comment not found for reply " + commentId);
                    }
                } else {
                    Logger.warn("Orphan comment found (no parent): " + commentId);
                }
            }
        }
    }

}
