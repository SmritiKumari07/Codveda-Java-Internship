import java.sql.*;
import java.util.Scanner;

public class LibraryManagementJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/codveda_library?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {
            System.out.println("Connected to DB.");
            while (true) {
                System.out.println("\n1) Add Book 2) List Books 3) Borrow 4) Return 5) Exit");
                System.out.print("Choose: ");
                int choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1 -> addBook(conn, sc);
                    case 2 -> listBooks(conn);
                    case 3 -> borrowBook(conn, sc);
                    case 4 -> returnBook(conn, sc);
                    case 5 -> { System.out.println("Exiting."); return; }
                    default -> System.out.println("Invalid option.");
                }
            }
        } catch (SQLException e) {
            System.err.println("DB Error: " + e.getMessage());
        }
    }

    private static void addBook(Connection conn, Scanner sc) {
        try {
            System.out.print("Title: "); String title = sc.nextLine();
            System.out.print("Author: "); String author = sc.nextLine();
            System.out.print("Total copies: "); int total = Integer.parseInt(sc.nextLine().trim());
            int available = Math.max(0, total);

            String sql = "INSERT INTO books (title, author, total_copies, available_copies) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setInt(3, total);
                ps.setInt(4, available);
                ps.executeUpdate();
                System.out.println("Book added.");
            }
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    private static void listBooks(Connection conn) {
        String sql = "SELECT id, title, author, total_copies, available_copies FROM books";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.printf("ID:%d | '%s' by %s | total:%d | available:%d%n",
                        rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                        rs.getInt("total_copies"), rs.getInt("available_copies"));
            }
            if (!any) System.out.println("No books found.");
        } catch (SQLException e) {
            System.out.println("Error listing books: " + e.getMessage());
        }
    }

    private static void borrowBook(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter book ID to borrow: "); int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Borrower name: "); String borrower = sc.nextLine();

            conn.setAutoCommit(false);
            String checkSql = "SELECT available_copies FROM books WHERE id = ? FOR UPDATE";
            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                checkPs.setInt(1, id);
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Book not found.");
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return;
                    }
                    int available = rs.getInt("available_copies");
                    if (available <= 0) {
                        System.out.println("No copies available.");
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return;
                    }
                }
            }
            String upd = "UPDATE books SET available_copies = available_copies - 1 WHERE id = ?";
            try (PreparedStatement updPs = conn.prepareStatement(upd)) {
                updPs.setInt(1, id);
                updPs.executeUpdate();
            }
            String insTrans = "INSERT INTO transactions (book_id, borrower, action) VALUES (?, ?, 'BORROW')";
            try (PreparedStatement tps = conn.prepareStatement(insTrans)) {
                tps.setInt(1, id);
                tps.setString(2, borrower);
                tps.executeUpdate();
            }
            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("Borrowed successfully.");
        } catch (Exception e) {
            try { if (!conn.isClosed()) conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            System.out.println("Error during borrow: " + e.getMessage());
            try { conn.setAutoCommit(true); } catch (SQLException ex) { /* ignore */ }
        }
    }

    private static void returnBook(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter book ID to return: "); int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Returner name: "); String borrower = sc.nextLine();

            conn.setAutoCommit(false);
            // increment available
            String upd = "UPDATE books SET available_copies = available_copies + 1 WHERE id = ?";
            try (PreparedStatement updPs = conn.prepareStatement(upd)) {
                updPs.setInt(1, id);
                int affected = updPs.executeUpdate();
                if (affected == 0) {
                    System.out.println("Book not found.");
                    conn.rollback();
                    conn.setAutoCommit(true);
                    return;
                }
            }
            // record transaction
            String insTrans = "INSERT INTO transactions (book_id, borrower, action) VALUES (?, ?, 'RETURN')";
            try (PreparedStatement tps = conn.prepareStatement(insTrans)) {
                tps.setInt(1, id);
                tps.setString(2, borrower);
                tps.executeUpdate();
            }
            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("Return recorded.");
        } catch (Exception e) {
            try { if (!conn.isClosed()) conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            System.out.println("Error during return: " + e.getMessage());
            try { conn.setAutoCommit(true); } catch (SQLException ex) { /* ignore */ }
        }
    }
}

