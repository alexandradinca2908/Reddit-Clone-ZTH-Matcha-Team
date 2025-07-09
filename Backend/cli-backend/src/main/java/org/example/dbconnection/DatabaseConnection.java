package org.example.dbconnection;

import org.example.services.PasswordService;

import java.sql.*;

public class DatabaseConnection {
    private static final String IP_SERVER = "34.116.164.52";
    private static final String DB_NAME = "postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Matcha1234!";
    private static final String CONNECTION_STRING = "jdbc:postgresql://" + IP_SERVER + ":5432/" + DB_NAME;


    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void main(String[] args) {

        String createTableSQL = "CREATE TABLE IF NOT EXISTS utilizatori ("
                + "id SERIAL PRIMARY KEY,"
                + "nume_utilizator VARCHAR(50) NOT NULL UNIQUE,"
                + "email VARCHAR(100) NOT NULL UNIQUE"
                + ");";

        String insertUserSQL = "INSERT INTO utilizatori (nume_utilizator, email) VALUES (?, ?) ON CONFLICT (nume_utilizator) DO NOTHING;";

        System.out.println("Se încearcă conectarea la baza de date...");

        try (Connection connection = DatabaseConnection.connect()) {
            if (connection == null) {
                //TODO: Logging the failure to connect to the database
                System.out.println("Failed to connect to the database. Exiting...");
                return;
            }

            //TODO: Logging the successful connection

            try (Statement stmt = connection.createStatement()) {
                System.out.println("Se execută: " + createTableSQL);
                stmt.execute(createTableSQL);
                System.out.println("✅ Tabelul 'utilizatori' a fost creat sau există deja.");
            }

            // --- Pasul 2: Executarea operațiunii CREATE (INSERT) ---
            try (PreparedStatement pstmt = connection.prepareStatement(insertUserSQL)) {
                // Setăm valorile pentru placeholder-urile '?'
                String numeTest = "cosmin_test";
                String emailTest = "cosmin@test.com";

                pstmt.setString(1, numeTest);  // Primul '?' este nume_utilizator
                pstmt.setString(2, emailTest); // Al doilea '?' este email

                System.out.println("Se execută INSERT pentru utilizatorul: " + numeTest);

                // executeUpdate() returnează numărul de rânduri afectate
                int randuriAfectate = pstmt.executeUpdate();

                if (randuriAfectate > 0) {
                    System.out.println("✅ Utilizatorul '" + numeTest + "' a fost inserat cu succes!");
                } else {
                    System.out.println("⚠️ Utilizatorul '" + numeTest + "' există deja. Nu s-a inserat o înregistrare nouă.");
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            // TODO: Logging the exception
            return;
        }
    }

}
