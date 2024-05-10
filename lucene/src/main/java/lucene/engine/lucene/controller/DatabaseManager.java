package lucene.engine.lucene.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String user = "root";
    private static final String password = "password";

    // JDBC variables for opening and managing connection
    private static Connection con = null;
    private static Statement stmt = null;

    public static void createTestDatabase() {
        String query = "CREATE DATABASE IF NOT EXISTS testMed";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing the SQL query and retrieving the result
            stmt.executeUpdate(query);
            System.out.println("Database 'testMed' created successfully");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            // Close statement and connection here only if they were opened
            try { if (stmt != null) stmt.close(); } catch (SQLException se) { /* handle exception */ }
            try { if (con != null) con.close(); } catch (SQLException se) { /* handle exception */ }
        }
    }

    public static void main(String[] args) {
        createTestDatabase();
    }
}
