package org.example.repositories;

import org.example.dbconnection.DatabaseConnection;
import org.example.models.Post;
import org.example.loggerobjects.Logger;
import org.example.services.PostService;
import org.example.userinterface.UIView;

import java.sql.*;
import java.util.ArrayList;

public class PostRepo {
    private static PostRepo instance;
    private static UIView uiView = UIView.getInstance();

    public static PostRepo getInstance() {
        if (instance == null) {
            instance = new PostRepo();
        }
        return instance;
    }

    public void save(Post post) {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        // The SQL query to insert a post. We ask for the generated keys back.
        String sql = "INSERT INTO post (username, title, description) VALUES (?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
            Logger.error("Failed to save post to the database: " + e.getMessage());
            uiView.printRestartAppMessage();
            DatabaseConnection.cannotConnect();
        }

    }

    public void load(ArrayList<Post> posts) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = "SELECT postID, username, title, description FROM post";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();



        while (rs.next()) {
            String username = rs.getString("username");
            if (username == null) {
                username = "[deleted user]";
            }
            Post post = new Post(
                    rs.getString("title"),
                    rs.getString("description"),
                    username
            );
            post.setPostId(rs.getInt("postID"));
            posts.add(post);
        }

    }
}