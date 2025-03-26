import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Addbook {
    public static void addBook(Scanner scanner) {
        try {
            System.out.print("Enter Book ID (must be unique): ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Title: ");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Title cannot be empty.");
                return;
            }

            System.out.print("Enter Author: ");
            String author = scanner.nextLine().trim();
            if (author.isEmpty()) {
                System.out.println("Author cannot be empty.");
                return;
            }

            System.out.print("Enter Genre: ");
            String genre = scanner.nextLine();

            String status;
            while (true) {
                System.out.print("Enter Availability Status (Available/Checked Out): ");
                status = scanner.nextLine().trim();
                if (status.equalsIgnoreCase("Available") || status.equalsIgnoreCase("Checked Out")) {
                    break;
                }
                System.out.println("Invalid status. Please enter 'Available' or 'Checked Out'.");
            }

            String insertSQL = "INSERT INTO books (id, title, author, genre, availabilityStatus) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = LibrarySystemJDBC.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, title);
                pstmt.setString(3, author);
                pstmt.setString(4, genre);
                pstmt.setString(5, status);
                pstmt.executeUpdate();
                System.out.println("Book added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }
}
