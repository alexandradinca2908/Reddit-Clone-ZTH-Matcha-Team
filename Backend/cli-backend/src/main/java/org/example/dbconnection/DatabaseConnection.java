package org.example.dbconnection;

import org.example.entities.User;
import org.example.services.PasswordService;

import java.sql.*;

public class DatabaseConnection {
    private static final String IP_SERVER = "34.116.164.52";
    private static final String DB_NAME = "postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Matcha1234!";
    private static final String CONNECTION_STRING = "jdbc:postgresql://" + IP_SERVER + ":5432/" + DB_NAME;


    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


}
