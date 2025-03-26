import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewAllBooks {
    public static void viewAllBooks() {
        String query = "SELECT * FROM books";
        try (Connection conn = LibrarySystemJDBC.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n--- All Books ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id")
                        + ", Title: " + rs.getString("title")
                        + ", Author: " + rs.getString("author")
                        + ", Genre: " + rs.getString("genre")
                        + ", Status: " + rs.getString("availabilityStatus"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving books: " + e.getMessage());
        }
    }

}
