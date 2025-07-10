package org.example.repositories;

import org.example.dbconnection.DatabaseConnection;
import org.example.entities.Comment;
import org.example.entities.Post;
import org.example.entities.User;

import java.sql.*;
import java.util.ArrayList;

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

    public void save(Comment comment) {
        String sql = "INSERT INTO comment (post_id, username, text) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, comment.getParentPost().getPostID());
            pstmt.setString(2, comment.getParentUser().getUsername());
            pstmt.setString(3, comment.getCommentText());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        comment.setCommentID(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Crearea comentariului a eșuat, nu s-a obținut un ID.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void load() {
        String sql = "SELECT comment_id, post_id, username, text FROM comment";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int commentId = rs.getInt("comment_id");
                int postId = rs.getInt("post_id");
                String username = rs.getString("username");
                String text = rs.getString("text");

                Post parentPost = postRepo.findById(postId);
                User parentUser = userRepo.findByUsername(username);

                if (parentPost != null && parentUser != null) {
                    Comment comment = new Comment(parentPost, parentUser, text);

                    comment.setCommentID(commentId);

                    parentPost.addComment(parentUser, text);
                } else {
                    System.err.println("Warning: Could not load comment " + commentId +
                            " because its parent post or user could not be found.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error loading comments from database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
