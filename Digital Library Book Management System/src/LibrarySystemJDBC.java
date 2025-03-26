import java.sql.*;
import java.util.Scanner;

public class LibrarySystemJDBC {

    // Database connection details (update these values as necessary)
    private static final String URL = "jdbc:mysql://localhost:3306/bookstore";
    private static final String USER = "root";
    private static final String PASSWORD = "Santa@4742";

    // Obtain a connection
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Initialize the database: create the 'books' table if it doesn't exist.
    private static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS books ("
                + "id INT PRIMARY KEY, "
                + "title VARCHAR(100) NOT NULL, "
                + "author VARCHAR(100) NOT NULL, "
                + "genre VARCHAR(50), "
                + "availabilityStatus VARCHAR(20) CHECK (availabilityStatus IN ('Available', 'Checked Out'))"
                + ")";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Books table is ready.");
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    // Add a new book record to the database
    

    // View all book records
   
    // Search for a book by ID or title
  
    // Update an existing book record
   
    // Delete a book record from the database
    
    public static void main(String[] args) {
        
        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Make sure it is added to your project.");
            return;
        }

        // Initialize database and table
        initializeDatabase();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Library System Menu ---");
            System.out.println("1. Add a Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book by ID or Title");
            System.out.println("4. Update Book Details");
            System.out.println("5. Delete a Book Record");
            System.out.println("6. Exit System");
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    Addbook.addBook(scanner);
                    break;
                case 2:
                    ViewAllBooks.viewAllBooks();
                    break;
                case 3:
                    SearchBook.searchBook(scanner);
                    break;
                case 4:
                    Update.updateBook(scanner);
                    break;
                case 5:
                    deleteBook(scanner);
                    break;
                case 6:
                    System.out.println("Exiting the system. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void deleteBook(Scanner scanner) {
    }
}
