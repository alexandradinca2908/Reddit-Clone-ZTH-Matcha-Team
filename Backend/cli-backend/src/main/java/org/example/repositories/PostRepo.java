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

    private PostRepo() {}

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
            SELECT post_id, title, description, profile_id
            WHERE is_deleted = FALSE
        """;

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                UUID postId = rs.getObject("post_id", java.util.UUID.class);
                String title = rs.getString("title");
                String description = rs.getString("description");
                UUID profileId = rs.getObject("profile_id", java.util.UUID.class);

                Post post = new Post(title, description, profileId);
                post.setPostId(postId); // Make sure Post class uses UUID for postId
                posts.add(post);
            }
        }
    }

    public void save(Post post) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = """
        INSERT INTO post (profile_id, title, description)
        VALUES (?, ?, ?)
        RETURNING post_id
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, post.getProfileId()); // UUID profile_id
            pstmt.setString(2, post.getTitle());     // title
            pstmt.setString(3, post.getBody());      // description
//            pstmt.setString(4, post.getPhotoPath()); // photo_path (can be null)

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    post.setPostId(rs.getObject("post_id", UUID.class)); // Setează UUID-ul generat
                }
            }
        }
    }



}