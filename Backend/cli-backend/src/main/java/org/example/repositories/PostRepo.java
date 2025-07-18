package org.example.repositories;

import org.example.dbconnection.DatabaseConnection;
import org.example.models.Post;
import org.example.loggerobjects.Logger;
import org.example.models.User;
import org.example.services.PostService;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class PostRepo {
    private static PostRepo instance;

    public static PostRepo getInstance() {
        if (instance == null) {
            instance = new PostRepo();
        }
        return instance;
    }

    public void load(ArrayList<Post> posts) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = """
            SELECT p.post_id, p.title, p.description, pr.username
            FROM post p
            LEFT JOIN profile pr ON p.profile_id = pr.profile_id
            WHERE p.is_deleted = FALSE
        """;

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                String username = rs.getString("username");
                if (username == null) {
                    username = "[deleted user]";
                }

                UUID postId = rs.getObject("post_id", java.util.UUID.class);
                String title = rs.getString("title");
                String description = rs.getString("description");

                Post post = new Post(title, description, username);
                post.setPostId(postId); // Make sure Post class uses UUID for postId
                posts.add(post);
            }
        }
    }

//    public void save(Post post) {
//        if (!DatabaseConnection.isConnected()) {
//            return;
//        }
//
//        // The SQL query to insert a post. We ask for the generated keys back.
//        String sql = "INSERT INTO post (username, title, description) VALUES (?, ?, ?)";
//        try {
//            Connection conn = DatabaseConnection.getConnection();
//            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//            pstmt.setString(1, post.getUsername());
//            pstmt.setString(2, post.getTitle());
//            pstmt.setString(3, post.getBody());
//
//            int affectedRows = pstmt.executeUpdate();
//
//            // Check if the insert was successful
//            if (affectedRows > 0) {
//                // Get the generated post_id
//                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        // Update the original post object with its new ID from the database
//                        post.setPostId(generatedKeys.get(1));
//                    } else {
//                        throw new SQLException("Creating post failed, no ID obtained.");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            Logger.error("Failed to save post to the database: " + e.getMessage());
//            System.out.println("\nPlease restart the application");
//            DatabaseConnection.cannotConnect();
//        }
//
//    }


}