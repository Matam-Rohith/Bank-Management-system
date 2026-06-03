package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection - Singleton pattern for database connection.
 * Supports MySQL (default) and Oracle Database.
 *
 * OOP Concept: Singleton (Creational Design Pattern)
 */
public class DBConnection {

    // ── MySQL Configuration ──────────────────────────────────
    private static final String MYSQL_URL      = "jdbc:mysql://localhost:3306/bank_db";
    private static final String MYSQL_USER     = "root";
    private static final String MYSQL_PASSWORD = "your_password";  // Change this
    private static final String MYSQL_DRIVER   = "com.mysql.cj.jdbc.Driver";

    // ── Oracle Configuration (uncomment to use Oracle) ───────
    // private static final String ORACLE_URL    = "jdbc:oracle:thin:@localhost:1521:xe";
    // private static final String ORACLE_USER   = "system";
    // private static final String ORACLE_PASS   = "oracle";
    // private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    private static Connection connection = null;

    // Private constructor - prevents direct instantiation
    private DBConnection() {}

    /**
     * Returns a singleton Connection instance.
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(MYSQL_DRIVER);
                connection = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
                System.out.println("[DB] Connected to MySQL database successfully.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("[DB ERROR] JDBC Driver not found: " + e.getMessage());
            System.err.println("           Download mysql-connector-java.jar and add to classpath.");
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Connection failed: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("[DB] Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Error closing connection: " + e.getMessage());
        }
    }
}
