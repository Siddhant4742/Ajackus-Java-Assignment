package server;

import static spark.Spark.*;
import com.google.gson.Gson;
import model.Book;
import repository.BookRepository;
import database.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public class LibraryServer {
    public static void main(String[] args) {
        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Make sure it is added to your project.");
            return;
        }

        // Initialize the database/table
        DatabaseManager.initializeDatabase();

        // Set the server port
        port(5000);

        // Create a Gson instance for JSON conversion
        Gson gson = new Gson();
        BookRepository bookRepository = new BookRepository();

        // REST API Endpoints (same as previous implementation)
        // 1. Add a new book (POST /books)
        post("/books", (req, res) -> {
            res.type("application/json");
            try {
                Book book = gson.fromJson(req.body(), Book.class);
                Book addedBook = bookRepository.addBook(book);
                res.status(201);
                return gson.toJson(addedBook);
            } catch (SQLException ex) {
                res.status(500);
                return gson.toJson("Error adding book: " + ex.getMessage());
            }
        });
        // 2. View all books (GET /books)
        get("/books", (req, res) -> {
            res.type("application/json");
            try {
                List<Book> books = bookRepository.getAllBooks();
                return gson.toJson(books);
            } catch (SQLException ex) {
                res.status(500);
                return gson.toJson("Error retrieving books: " + ex.getMessage());
            }
        });

        // 3. Search for books by title (GET /books/search?title=...)
        get("/books/search", (req, res) -> {
            res.type("application/json");
            String titleParam = req.queryParams("title");
            if (titleParam == null || titleParam.isEmpty()) {
                res.status(400);
                return gson.toJson("Missing 'title' query parameter");
            }
            try {
                List<Book> books = bookRepository.searchBooksByTitle(titleParam);
                return gson.toJson(books);
            } catch (SQLException ex) {
                res.status(500);
                return gson.toJson("Error retrieving books: " + ex.getMessage());
            }
        });

        // 4. Search for a book by ID (GET /books/:id)
        get("/books/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id"));
            try {
                Book book = bookRepository.findBookById(id);
                if (book == null) {
                    res.status(404);
                    return gson.toJson("Book not found");
                }
                return gson.toJson(book);
            } catch (SQLException ex) {
                res.status(500);
                return gson.toJson("Error retrieving book: " + ex.getMessage());
            }
        });

        // 5. Update a book (PUT /books/:id)
        put("/books/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id"));
            try {
                Book updatedBook = gson.fromJson(req.body(), Book.class);
                boolean updated = bookRepository.updateBook(id, updatedBook);
                if (!updated) {
                    res.status(404);
                    return gson.toJson("Book not found");
                }
                return gson.toJson("Book updated successfully");
            } catch (SQLException ex) {
                res.status(500);
                return gson.toJson("Error updating book: " + ex.getMessage());
            }
        });

        // 6. Delete a book (DELETE /books/:id)
        delete("/books/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params("id"));
            try {
                boolean deleted = bookRepository.deleteBook(id);
                if (!deleted) {
                    res.status(404);
                    return gson.toJson("Book not found");
                }
                return gson.toJson("Book deleted successfully");
            } catch (SQLException ex) {
                res.status(500);
                return gson.toJson("Error deleting book: " + ex.getMessage());
            }
        });

        // Remaining endpoints (GET, PUT, DELETE) remain the same
        // ... (keep the implementation from the previous artifact)

        System.out.println("Library server is running on port 5000");
    }
}