package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DropSchema {
    // Database connection parameters
    private static final String URL = "jdbc:postgresql://localhost:5432/Soen342";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Extinctable4*";

    public static void main(String[] args) {
        // Load the PostgreSQL JDBC Driver
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        // Connect to the database and drop the schema
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (conn != null) {
                System.out.println("Connected to PostgreSQL database successfully.");

                Statement stmt = conn.createStatement();

                // Order matters due to foreign key constraints
                String[] dropStatements = {
                    "DROP TABLE IF EXISTS service_request CASCADE;",
                    "DROP TABLE IF EXISTS viewing CASCADE;",
                    "DROP TABLE IF EXISTS art_object CASCADE;",
                    "DROP TABLE IF EXISTS auction CASCADE;",
                    "DROP TABLE IF EXISTS auction_house CASCADE;",
                    "DROP TABLE IF EXISTS administrator CASCADE;",
                    "DROP TABLE IF EXISTS client CASCADE;",
                    "DROP TABLE IF EXISTS expert CASCADE;",
                    "DROP TABLE IF EXISTS institution CASCADE;",
                    "DROP TABLE IF EXISTS app_user CASCADE;"
                };

                for (String sql : dropStatements) {
                    stmt.execute(sql);
                }

                System.out.println("All tables dropped successfully. Database has been reset.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred while dropping schema");
            e.printStackTrace();
        }
    }
}
