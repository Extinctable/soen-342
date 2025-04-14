// File: dao/AdministratorDAO.java
package dao;

import model.Administrator;
import java.sql.*;

public class AdministratorDAO {
    
    public void createAdministrator(Administrator admin) {
        String sql = "INSERT INTO administrator (admin_email, admin_password) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            pstmt.setString(1, admin.getUsername());
            pstmt.setString(2, admin.getPassword());
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Optionally assign the generated ID.
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
                        "AdminName",          // Not stored in table
                        "ContactNotStored",     // Not stored in table
                        "PhoneNotStored"        // Not stored in table
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
