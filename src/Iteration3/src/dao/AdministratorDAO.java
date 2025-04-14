// File: dao/AdministratorDAO.java
package dao;

import java.sql.*;
import model.Administrator;

public class AdministratorDAO {
    
    public void createAdministrator(Administrator admin) {
        // First, check if the admin already exists by email
        String checkSql = "SELECT COUNT(*) FROM administrator WHERE admin_email = ?";
        String insertSql = "INSERT INTO administrator (admin_email, admin_password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, admin.getUsername());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("⚠️ Administrator with email " + admin.getUsername() + " already exists.");
                return; // Exit early
            }

            // Proceed with insert if not a duplicate
            PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, admin.getUsername());
            insertStmt.setString(2, admin.getPassword());
            insertStmt.executeUpdate();

            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Optionally assign the generated ID if needed later
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Administrator getAdministratorById(int id) {
        String sql = "SELECT * FROM administrator WHERE admin_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Administrator admin = Administrator.getInstance(
                        rs.getString("admin_email"),
                        rs.getString("admin_password"),
                        "AdminName",        // Placeholder
                        "ContactNotStored", // Placeholder
                        "PhoneNotStored"    // Placeholder
                );
                return admin;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void updateAdministrator(Administrator admin, int id) {
        String sql = "UPDATE administrator SET admin_email = ?, admin_password = ? WHERE admin_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, admin.getUsername());
            pstmt.setString(2, admin.getPassword());
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteAdministrator(int id) {
        String sql = "DELETE FROM administrator WHERE admin_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
