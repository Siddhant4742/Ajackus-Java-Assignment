package repository;

import model.Book;
import database.DatabaseManager;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private Gson gson;

    public BookRepository() {
        this.gson = new Gson();
    }

    // Add a new book to the database
    public Book addBook(Book book) throws SQLException {
        String insertSQL = "INSERT INTO books (id, title, author, genre, availabilityStatus) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, book.getId());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getGenre());
            pstmt.setString(5, book.getAvailabilityStatus());
            pstmt.executeUpdate();
            return book;
        }
    }
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getString("availabilityStatus")
                );
                books.add(book);
            }
        }
        return books;
    }

    // Search books by title
    public List<Book> searchBooksByTitle(String title) throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE title LIKE ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + title + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getString("availabilityStatus")
                    );
                    books.add(book);
                }
            }
        }
        return books;
    }

    // Find a book by ID
    public Book findBookById(int id) throws SQLException {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getString("availabilityStatus")
                    );
                }
            }
        }
        return null;
    }

    // Update a book
    public boolean updateBook(int id, Book updatedBook) throws SQLException {
        String updateSQL = "UPDATE books SET title = ?, author = ?, genre = ?, availabilityStatus = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, updatedBook.getTitle());
            pstmt.setString(2, updatedBook.getAuthor());
            pstmt.setString(3, updatedBook.getGenre());
            pstmt.setString(4, updatedBook.getAvailabilityStatus());
            pstmt.setInt(5, id);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        }
    }

    // Delete a book
    public boolean deleteBook(int id) throws SQLException {
        String deleteSQL = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        }
    }

    // Other methods remain the same as in the previous implementation
    // (getAllBooks, searchBooksByTitle, findBookById, updateBook, deleteBook)
    // ... (keep the implementation from the previous artifact)
}