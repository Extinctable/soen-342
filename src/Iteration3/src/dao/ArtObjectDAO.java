// File: dao/ArtObjectDAO.java
package dao;

import model.ArtObject;
import model.Auction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtObjectDAO {
    public void createArtObject(ArtObject obj) {
        String sql = "INSERT INTO art_object (admin_id, institution_id, object_name, object_description, object_type, is_owned_by_institution) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            // For simplicity, if admin and institution are not set, use SQL NULL.
            pstmt.setNull(1, Types.INTEGER);
            pstmt.setNull(2, Types.INTEGER);
            pstmt.setString(3, obj.getTitle());
            pstmt.setString(4, obj.getDescription());
            pstmt.setString(5, obj.getType());
            pstmt.setBoolean(6, obj.isOwned());
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Optionally assign the generated ID.
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public ArtObject getArtObjectById(int id) {
        String sql = "SELECT * FROM art_object WHERE object_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ArtObject obj = new ArtObject(
                     rs.getString("object_name"),
                     rs.getString("object_description"),
                     rs.getString("object_type"),
                     rs.getBoolean("is_owned_by_institution"),
                     false, // isToBeAuctioned is not stored in our schema
                     null   // auction is not linked here
                );
                return obj;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<ArtObject> getAllArtObjects() {
        List<ArtObject> objects = new ArrayList<>();
        String sql = "SELECT * FROM art_object";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                ArtObject obj = new ArtObject(
                     rs.getString("object_name"),
                     rs.getString("object_description"),
                     rs.getString("object_type"),
                     rs.getBoolean("is_owned_by_institution"),
                     false,
                     null
                );
                objects.add(obj);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return objects;
    }
    
    public void updateArtObject(ArtObject obj, int id) {
        String sql = "UPDATE art_object SET object_name = ?, object_description = ?, object_type = ?, is_owned_by_institution = ? WHERE object_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, obj.getTitle());
            pstmt.setString(2, obj.getDescription());
            pstmt.setString(3, obj.getType());
            pstmt.setBoolean(4, obj.isOwned());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteArtObject(int id) {
        String sql = "DELETE FROM art_object WHERE object_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
