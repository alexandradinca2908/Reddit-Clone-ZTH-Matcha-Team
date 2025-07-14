package org.example.repositories;

import org.example.dbconnection.DatabaseConnection;
import org.example.entities.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepo {
    private static PostRepo instance;

    private PostRepo() {}

    public static PostRepo getInstance() {
        if (instance == null) {
            instance = new PostRepo();
        }
        return instance;
    }

    public void save(Post post) {
        // The SQL query to insert a post. We ask for the generated keys back.
        String sql = "INSERT INTO post (username, title, description) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             // Statement.RETURN_GENERATED_KEYS tells the driver to return the auto-generated post_id
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, post.getUsername());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getBody());

            int affectedRows = pstmt.executeUpdate();

            // Check if the insert was successful
            if (affectedRows > 0) {
                // Get the generated post_id
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Update the original post object with its new ID from the database
                        post.setPostId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating post failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void load(ArrayList<Post> posts) {
        String sql = "SELECT postID, username, title, description FROM post";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Post post = new Post(
                        rs.getString("title"),
                        rs.getString("description"), // 'description' is the column name in the DB
                        rs.getString("username")
                );
                post.setPostId(rs.getInt("post_id"));
                posts.add(post);
            }
        } catch (SQLException e) {
            // In a real app, use a proper logger
            System.err.println("Error loading posts from database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Post findById(int postId) {
        for (Post post : Post.posts) {
            if (post.getPostID() == postId) {
                return post;
            }
        }
        return null;
    }
}