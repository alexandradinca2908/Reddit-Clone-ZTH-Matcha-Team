package org.example.repositories;
import org.example.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.dbconnection.DatabaseConnection;
import org.example.services.UserService;

public class UserRepo {

    private static UserRepo instance;
    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepo();
        }
        return instance;
    }

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO profile (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.executeUpdate();
        }
    }

    public void load(ArrayList<User> users) throws SQLException {
        String sql = "SELECT username, email, password FROM profile";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (conn == null) {
            throw new SQLException("Database connection is null.");
        }
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            User user = new User(
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password")
            );
            users.add(user);
        }
    }

    public User findByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }

        for (User user : UserService.users) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    public void deleteUser(String username) {
        String sql = "DELETE FROM profile WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
