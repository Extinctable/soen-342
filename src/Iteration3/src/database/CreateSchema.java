package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateSchema {
// Database connection parameters
private static final String URL = "jdbc:postgresql://localhost:5432/Soen342";
private static final String USER = "jananaamahathevan";  
private static final String PASSWORD = "admin123"; 

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
        
        // Connect to the database and create the schema
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (conn != null) {
                System.out.println("Connected to PostgreSQL database successfully.");
                
                // Create a Statement to execute SQL commands
                Statement stmt = conn.createStatement();

                // Array of SQL statements to create tables
                String[] sqlStatements = {
                    // Table: institution
                    "CREATE TABLE IF NOT EXISTS institution (" +
                        "institution_id SERIAL PRIMARY KEY, " +
                        "institution_name VARCHAR(255) NOT NULL, " +
                        "institution_location VARCHAR(255), " +
                        "institution_contact_info VARCHAR(255)" +
                    ");",
                    
                    // Table: expert
                    "CREATE TABLE IF NOT EXISTS expert (" +
                        "expert_id SERIAL PRIMARY KEY, " +
                        "username VARCHAR(255) UNIQUE NOT NULL, " +
                        "password VARCHAR(255) NOT NULL, " +
                        "institution_id INTEGER, " +
                        "expert_name VARCHAR(255) NOT NULL, " +
                        "expert_contact_address VARCHAR(255), " +
                        "expert_license_number VARCHAR(100), " +
                        "area_of_expertise VARCHAR(255), " +
                        "availability_schedule TEXT, " +
                        "FOREIGN KEY (institution_id) REFERENCES institution(institution_id) ON DELETE SET NULL" +
                    ");",
                    
                    // Table: client
                    "CREATE TABLE IF NOT EXISTS client (" +
                        "client_id SERIAL PRIMARY KEY, " +
                        "institution_id INTEGER, " +
                        "client_email VARCHAR(255) UNIQUE NOT NULL, " +
                        "client_password VARCHAR(255) NOT NULL, " +
                        "client_affiliation VARCHAR(255), " +
                        "client_contact_info VARCHAR(255), " +
                        "intent_description TEXT, " +
                        "approved BOOLEAN DEFAULT FALSE, " +
                        "FOREIGN KEY (institution_id) REFERENCES institution(institution_id) ON DELETE SET NULL" +
                    ");",
                    
                    // Table: administrator
                    "CREATE TABLE IF NOT EXISTS administrator (" +
                        "admin_id SERIAL PRIMARY KEY, " +
                        "admin_email VARCHAR(255) UNIQUE NOT NULL, " +
                        "admin_password VARCHAR(255) NOT NULL" +
                    ");",
                    
                    // Table: auction_house
                    "CREATE TABLE IF NOT EXISTS auction_house (" +
                        "auction_house_id SERIAL PRIMARY KEY, " +
                        "auction_house_name VARCHAR(255) NOT NULL, " +
                        "auction_house_location VARCHAR(255), " +
                        "auction_house_contact_info VARCHAR(255)" +
                    ");",
                    
                    // Table: auction
                    "CREATE TABLE IF NOT EXISTS auction (" +
                        "auction_id SERIAL PRIMARY KEY, " +
                        "auction_house_id INTEGER, " +
                        "auction_name VARCHAR(255) NOT NULL, " +
                        "auction_schedule TIMESTAMP, " +
                        "is_online BOOLEAN, " +
                        "viewing_schedule TIMESTAMP, " +
                        "auction_type VARCHAR(100), " +
                        "FOREIGN KEY (auction_house_id) REFERENCES auction_house(auction_house_id) ON DELETE CASCADE" +
                    ");",
                    
                    // Table: art_object (Objects of Interest)
                    "CREATE TABLE IF NOT EXISTS art_object (" +
                        "object_id SERIAL PRIMARY KEY, " +
                        "admin_id INTEGER, " +
                        "institution_id INTEGER, " +
                        "object_name VARCHAR(255) NOT NULL, " +
                        "object_description TEXT, " +
                        "object_type VARCHAR(100), " +
                        "is_owned_by_institution BOOLEAN, " +
                        "FOREIGN KEY (admin_id) REFERENCES administrator(admin_id) ON DELETE SET NULL, " +
                        "FOREIGN KEY (institution_id) REFERENCES institution(institution_id) ON DELETE SET NULL" +
                    ");",
                    
                    // Table: viewing
                    "CREATE TABLE IF NOT EXISTS viewing (" +
                        "viewing_id SERIAL PRIMARY KEY, " +
                        "auction_house_id INTEGER, " +
                        "viewing_time TIMESTAMP, " +
                        "FOREIGN KEY (auction_house_id) REFERENCES auction_house(auction_house_id) ON DELETE CASCADE" +
                    ");",
                    
                    // Table: service_request
                    "CREATE TABLE IF NOT EXISTS service_request (" +
                        "request_id SERIAL PRIMARY KEY, " +
                        "client_id INTEGER, " +
                        "expert_id INTEGER, " +
                        "service_type VARCHAR(100), " +
                        "requested_time TIMESTAMP, " +
                        "notes TEXT, " +
                        "status VARCHAR(50), " +
                        "object_id INTEGER, " +
                        "auction_id INTEGER, " +
                        "FOREIGN KEY (client_id) REFERENCES client(client_id) ON DELETE SET NULL, " +
                        "FOREIGN KEY (expert_id) REFERENCES expert(expert_id) ON DELETE SET NULL, " +
                        "FOREIGN KEY (object_id) REFERENCES art_object(object_id) ON DELETE SET NULL, " +
                        "FOREIGN KEY (auction_id) REFERENCES auction(auction_id) ON DELETE SET NULL" +
                    ");",
                    
                    // Optional: Generic users table (if needed for authentication)
                    "CREATE TABLE IF NOT EXISTS app_user (" +
                        "user_id SERIAL PRIMARY KEY, " +
                        "role VARCHAR(50), " +
                        "related_id INTEGER" +
                    ");"
                };

                // Execute each SQL statement
                for (String sql : sqlStatements) {
                    stmt.execute(sql);
                }

                System.out.println("Database schema created successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred while creating schema");
            e.printStackTrace();
        }
    }
}
