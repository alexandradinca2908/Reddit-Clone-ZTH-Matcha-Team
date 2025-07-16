package org.example.dbconnection;

import java.sql.*;

public class DatabaseConnection {
    private static final String IP_SERVER = "34.116.164.52";
    private static final String DB_NAME = "postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Matcha1234!";
    private static final String CONNECTION_STRING = "jdbc:postgresql://" + IP_SERVER + ":5432/" + DB_NAME;
    private static boolean isConnected = true;

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
        return connection;
    }

    public static void cannotConnect() {
        isConnected = false;
    }

    public static boolean isConnected() {
        return isConnected;
    }
}
