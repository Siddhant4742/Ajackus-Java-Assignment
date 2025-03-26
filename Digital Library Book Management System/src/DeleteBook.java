import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteBook {
    public static void deleteBook(Scanner scanner) {
        System.out.print("Enter the Book ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        String deleteSQL = "DELETE FROM books WHERE id = ?";
        try (Connection conn = LibrarySystemJDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("No book found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }

}
