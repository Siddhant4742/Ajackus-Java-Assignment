package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/bookstore";
    private static final String USER = "siddhant";
    private static final String PASSWORD = "Santa@4742";

    // Obtain a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Initialize the database: create the 'books' table if it doesn't exist
    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS books ("
                + "id INT PRIMARY KEY, "
                + "title VARCHAR(100) NOT NULL, "
                + "author VARCHAR(100) NOT NULL, "
                + "genre VARCHAR(50), "
                + "availabilityStatus VARCHAR(20)"
                + ")";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Books table is ready.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}