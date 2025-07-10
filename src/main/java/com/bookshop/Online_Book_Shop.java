package com.bookshop;

import java.sql.*;
import java.util.Scanner;

public class Online_Book_Shop {

    // Database credentials
    private static final String URL = "jdbc:postgresql://localhost:5432/mydb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "sakshi";

    private static Connection con;
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database.");

            
            runMenu();

        } catch (SQLException e) {
            System.err.println("Could not connect to the database.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
        } finally {
            try {
                if (con != null && !con.isClosed()) con.close();
                sc.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createTables() {
        executeSQL("author",
            "CREATE TABLE IF NOT EXISTS author (" +
            "author_id SERIAL PRIMARY KEY," +
            "name VARCHAR(100) NOT NULL)");

        executeSQL("category",
            "CREATE TABLE IF NOT EXISTS category (" +
            "category_id SERIAL PRIMARY KEY," +
            "name VARCHAR(100) NOT NULL)");

        executeSQL("book",
            "CREATE TABLE IF NOT EXISTS book (" +
            "book_id SERIAL PRIMARY KEY," +
            "title VARCHAR(200) NOT NULL," +
            "price DECIMAL(10,2) NOT NULL," +
            "author_id INT REFERENCES author(author_id)," +
            "category_id INT REFERENCES category(category_id))");

        executeSQL("register_users",
            "CREATE TABLE IF NOT EXISTS register_users (" +
            "user_id SERIAL PRIMARY KEY," +
            "name VARCHAR(100)," +
            "email VARCHAR(150) UNIQUE NOT NULL," +
            "password VARCHAR(100) NOT NULL)");

        executeSQL("place_order",
            "CREATE TABLE IF NOT EXISTS place_order (" +
            "order_id SERIAL PRIMARY KEY," +
            "user_id INT REFERENCES register_users(user_id)," +
            "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

        executeSQL("order_items",
            "CREATE TABLE IF NOT EXISTS order_items (" +
            "order_item_id SERIAL PRIMARY KEY," +
            "order_id INT REFERENCES place_order(order_id)," +
            "book_id INT REFERENCES book(book_id)," +
            "quantity INT CHECK (quantity > 0))");
    }

    private static void executeSQL(String tableName, String sql) {
        try (Statement stmt = con.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table" + tableName + " is ready.");
        } catch (SQLException e) {
            System.err.println("Failed to create table '" + tableName + "'");
            e.printStackTrace();
        }
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n***** ONLINE BOOK SHOP *****");
            System.out.println("1. Add Author");
            System.out.println("2. Add Category");
            System.out.println("3. Add Book");
            System.out.println("4. Register User");
            System.out.println("5. View All Books");
            System.out.println("6. Place Order");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1": addAuthor(); break;
                case "2": addCategory(); break;
                case "3": addBook(); break;
                case "4": registerUser(); break;
                case "5": viewBooks(); break;
                case "6": placeOrder(); break;
                case "7":
                    System.out.println("Thank you for visiting the Book Shop!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int getCount(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Failed to get count from " + tableName);
        }
        return -1;
    }

    private static void addAuthor() {
        try {
            System.out.print("Enter Author Name: ");
            String name = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("INSERT INTO author(name) VALUES(?)");
            ps.setString(1, name);
            ps.executeUpdate();

            int count = getCount("author");
            System.out.println("Author added. Total authors: " + count);
        } catch (SQLException e) {
            System.err.println("Failed to add author.");
            e.printStackTrace();
        }
    }

    private static void addCategory() {
        try {
            System.out.print("Enter Category Name: ");
            String name = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("INSERT INTO category(name) VALUES(?)");
            ps.setString(1, name);
            ps.executeUpdate();

            int count = getCount("category");
            System.out.println("Category added. Total categories: " + count);
        } catch (SQLException e) {
            System.err.println("Failed to add category.");
            e.printStackTrace();
        }
    }

    private static void addBook() {
        try {
            System.out.print("Enter Book Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(sc.nextLine());

            System.out.print("Enter Author ID: ");
            int authorId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Category ID: ");
            int categoryId = Integer.parseInt(sc.nextLine());

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO book(title, price, author_id, category_id) VALUES (?, ?, ?, ?)");
            ps.setString(1, title);
            ps.setDouble(2, price);
            ps.setInt(3, authorId);
            ps.setInt(4, categoryId);
            ps.executeUpdate();

            
            System.out.println("Book added. Total books: ");
        } catch (SQLException e) {
            System.err.println("Failed to add book.");
            e.printStackTrace();
        }
    }

    private static void registerUser() {
        try {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO register_users(name, email, password) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.executeUpdate();

            int count = getCount("register_users");
            System.out.println("User registered. Total users: " + count);
        } catch (SQLException e) {
            System.err.println("Failed to register user.");
            e.printStackTrace();
        }
    }

    private static void viewBooks() {
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(
                "SELECT b.book_id, b.title, b.price, a.name AS author, c.name AS category " +
                "FROM book b " +
                "JOIN author a ON b.author_id = a.author_id " +
                "JOIN category c ON b.category_id = c.category_id");

            System.out.println("\nAvailable Books:");
            System.out.printf("%-5s %-40s %-10s %-20s %-15s%n",
                "ID", "Title", "Price", "Author", "Category");
            System.out.println("---------------------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d %-40s $%-9.2f %-20s %-15s%n",
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getDouble("price"),
                        rs.getString("author"),
                        rs.getString("category"));
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch books.");
            e.printStackTrace();
        }
    }

    private static void placeOrder() {
        try {
            System.out.print("Enter User ID: ");
            int userId = Integer.parseInt(sc.nextLine());

            // Check if the user exists
            PreparedStatement userCheck = con.prepareStatement("SELECT 1 FROM register_users WHERE user_id = ?");
            userCheck.setInt(1, userId);
            ResultSet userResult = userCheck.executeQuery();
            if (!userResult.next()) {
                System.out.println("User ID not found. Please register first.");
                return;
            }

            // Show available books
            viewBooks();

            PreparedStatement psOrder = con.prepareStatement(
                "INSERT INTO place_order(user_id) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, userId);
            psOrder.executeUpdate();

            ResultSet rs = psOrder.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);

            boolean hasItems = false;

            while (true) {
                System.out.print("Enter Book ID (0 to finish): ");
                int bookId = Integer.parseInt(sc.nextLine());
                if (bookId == 0) break;

                // Validate book ID
                PreparedStatement bookCheck = con.prepareStatement("SELECT 1 FROM book WHERE book_id = ?");
                bookCheck.setInt(1, bookId);
                ResultSet bookRs = bookCheck.executeQuery();
                if (!bookRs.next()) {
                    System.out.println("Invalid Book ID. Please try again.");
                    continue;
                }

                System.out.print("Enter Quantity: ");
                int quantity = Integer.parseInt(sc.nextLine());

                PreparedStatement psItem = con.prepareStatement(
                    "INSERT INTO order_items(order_id, book_id, quantity) VALUES (?, ?, ?)");
                psItem.setInt(1, orderId);
                psItem.setInt(2, bookId);
                psItem.setInt(3, quantity);
                psItem.executeUpdate();

                hasItems = true;
            }

            if (hasItems) {
                System.out.println("Order placed successfully (Order ID: " + orderId + ")");
            } else {
                // Delete empty order if no books were added
                PreparedStatement deleteOrder = con.prepareStatement("DELETE FROM place_order WHERE order_id = ?");
                deleteOrder.setInt(1, orderId);
                deleteOrder.executeUpdate();
                System.out.println("No items added. Order cancelled.");
            }

        } catch (SQLException e) {
            System.err.println("Failed to place order.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values.");
        }
    }
}
