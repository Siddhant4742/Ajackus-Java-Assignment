import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SearchBook {
    public  static void searchBook(Scanner scanner) {
        System.out.println("Search by: 1. ID, 2. Title");
        int option = Integer.parseInt(scanner.nextLine());
        if (option == 1) {
            System.out.print("Enter Book ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            String query = "SELECT * FROM books WHERE id = ?";
            try (Connection conn = LibrarySystemJDBC.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("ID: " + rs.getInt("id")
                                + ", Title: " + rs.getString("title")
                                + ", Author: " + rs.getString("author")
                                + ", Genre: " + rs.getString("genre")
                                + ", Status: " + rs.getString("availabilityStatus"));
                    } else {
                        System.out.println("No book found with the given ID.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error searching for book: " + e.getMessage());
            }
        } else if (option == 2) {
            System.out.print("Enter Title to search: ");
            String title = scanner.nextLine().trim();
            String query = "SELECT * FROM books WHERE title LIKE ?";
            try (Connection conn = LibrarySystemJDBC.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, "%" + title + "%");
                try (ResultSet rs = pstmt.executeQuery()) {
                    boolean found = false;
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getInt("id")
                                + ", Title: " + rs.getString("title")
                                + ", Author: " + rs.getString("author")
                                + ", Genre: " + rs.getString("genre")
                                + ", Status: " + rs.getString("availabilityStatus"));
                        found = true;
                    }
                    if (!found) {
                        System.out.println("No book found with the given title.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error searching for book: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

}
