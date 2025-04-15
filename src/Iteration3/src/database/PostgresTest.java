package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Soen342";
        String user = "postgres";     // your DB username
        String password = "Extinctable4*"; // your DB password

        try {
            // Load the PostgreSQL JDBC driver explicitly
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to PostgreSQL successfully!");
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Connection failed!");
            e.printStackTrace();
        }
    }
}
