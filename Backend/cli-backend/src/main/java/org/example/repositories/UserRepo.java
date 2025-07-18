package org.example.repositories;
import org.example.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import org.example.dbconnection.DatabaseConnection;

public class UserRepo {

    private static UserRepo instance;
    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepo();
        }
        return instance;
    }

    public void load(ArrayList<User> users) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }
        String sql = """
            SELECT profile_id, username, email, password, photo_path, is_deleted, created_at
            FROM profile WHERE is_deleted = FALSE ORDER BY created_at DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (conn == null) {
                throw new SQLException("Database connection is null.");
            }

            while (rs.next()) {
                User user = new User(
                        (UUID) rs.getObject("profile_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("photo_path"),
                        rs.getBoolean("is_deleted"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                users.add(user);
            }
        }
    }

    public void save(User user) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = "INSERT INTO profile (username, email, password, photo_path) VALUES (?, ?, ?, ?) RETURNING profile_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPhotoPath()); // Can be null

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user.setUUID((UUID) rs.getObject("profile_id"));
                }
            }
        }
    }

//    public void deleteUser(UUID profileId) throws SQLException {
//        if (!DatabaseConnection.isConnected()) {
//            return;
//        }
//
//        String sql = "UPDATE profile SET is_deleted = TRUE WHERE profile_id = ?";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setObject(1, profileId);
//
//            int rowsAffected = pstmt.executeUpdate();
//            if (rowsAffected == 0) {
//                throw new SQLException("User not found");
//            }
//        }
//    }
}
