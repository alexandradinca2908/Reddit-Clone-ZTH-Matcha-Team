package org.example.repositories;

import org.example.dbconnection.DatabaseConnection;
import org.example.models.Image;

import java.sql.*;

public class ImageRepo {
    private static ImageRepo instance;

    public static ImageRepo getInstance() {
        if (instance == null) {
            instance = new ImageRepo();
        }
        return instance;
    }

    public void saveImage(Image image) throws SQLException {
        if (!DatabaseConnection.isConnected()) {
            return;
        }

        String sql = "INSERT INTO image (imageID, imageURL) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, image.getImageURL());
            pstmt.setInt(2, image.getImageID());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        image.setImageID(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating comment failed, no ID obtained.");
                    }
                }
            }
        }
    }
}
