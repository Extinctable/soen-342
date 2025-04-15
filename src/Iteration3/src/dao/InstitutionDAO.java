// File: dao/InstitutionDAO.java
package dao;

import model.Institution;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstitutionDAO {
    public void createInstitution(Institution inst) {
        String sql = "INSERT INTO institution (institution_name, institution_location, institution_contact_info) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            pstmt.setString(1, inst.getInstitutionName());
            pstmt.setString(2, inst.getInstitutionLocation());
            pstmt.setString(3, inst.getInstitutionContactInfo());
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Optionally assign the generated ID.
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public Institution getInstitutionById(int id) {
        String sql = "SELECT * FROM institution WHERE institution_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Institution inst = new Institution(
                     rs.getString("institution_name"),
                     rs.getString("institution_location"),
                     rs.getString("institution_contact_info")
                );
                return inst;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Institution> getAllInstitutions() {
        List<Institution> institutions = new ArrayList<>();
        String sql = "SELECT * FROM institution";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Institution inst = new Institution(
                     rs.getString("institution_name"),
                     rs.getString("institution_location"),
                     rs.getString("institution_contact_info")
                );
                institutions.add(inst);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return institutions;
    }
    
    public void updateInstitution(Institution inst, int id) {
        String sql = "UPDATE institution SET institution_name = ?, institution_location = ?, institution_contact_info = ? WHERE institution_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, inst.getInstitutionName());
            pstmt.setString(2, inst.getInstitutionLocation());
            pstmt.setString(3, inst.getInstitutionContactInfo());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteInstitution(int id) {
        String sql = "DELETE FROM institution WHERE institution_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
