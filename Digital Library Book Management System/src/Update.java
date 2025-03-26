import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Update {
    public static void updateBook(Scanner scanner) {
        System.out.print("Enter the Book ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        // Check if book exists
        String checkSQL = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = LibrarySystemJDBC.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
            checkStmt.setInt(1, id);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Book not found.");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking book: " + e.getMessage());
            return;
        }

        // Prompt for new details (if left blank, keep current value)
        System.out.print("Enter new Title (leave blank to keep current): ");
        String newTitle = scanner.nextLine().trim();

        System.out.print("Enter new Author (leave blank to keep current): ");
        String newAuthor = scanner.nextLine().trim();

        System.out.print("Enter new Genre (leave blank to keep current): ");
        String newGenre = scanner.nextLine().trim();

        System.out.print("Enter new Availability Status (Available/Checked Out, leave blank to keep current): ");
        String newStatus = scanner.nextLine().trim();
        if (!newStatus.isEmpty() && !newStatus.equalsIgnoreCase("Available") && !newStatus.equalsIgnoreCase("Checked Out")) {
            System.out.println("Invalid status. Update cancelled.");
            return;
        }

        // Use SQL COALESCE trick to update only provided fields
        String updateSQL = "UPDATE books SET "
                + "title = COALESCE(NULLIF(?, ''), title), "
                + "author = COALESCE(NULLIF(?, ''), author), "
                + "genre = COALESCE(NULLIF(?, ''), genre), "
                + "availabilityStatus = COALESCE(NULLIF(?, ''), availabilityStatus) "
                + "WHERE id = ?";
        try (Connection conn = LibrarySystemJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newAuthor);
            pstmt.setString(3, newGenre);
            pstmt.setString(4, newStatus);
            pstmt.setInt(5, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Update failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

}
