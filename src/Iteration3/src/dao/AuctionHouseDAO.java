// File: dao/AuctionHouseDAO.java
package dao;

import model.AuctionHouse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuctionHouseDAO {
    public void createAuctionHouse(AuctionHouse ah) {
        String sql = "INSERT INTO auction_house (auction_house_name, auction_house_location, auction_house_contact_info) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            pstmt.setString(1, ah.getName());
            pstmt.setString(2, ah.getLocation());
            pstmt.setString(3, ah.getContactInfo());
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Optionally assign the generated ID.
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public AuctionHouse getAuctionHouseById(int id) {
        String sql = "SELECT * FROM auction_house WHERE auction_house_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                AuctionHouse ah = new AuctionHouse(
                    rs.getString("auction_house_name"),
                    rs.getString("auction_house_location"),
                    rs.getString("auction_house_contact_info")
                );
                return ah;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<AuctionHouse> getAllAuctionHouses() {
        List<AuctionHouse> houses = new ArrayList<>();
        String sql = "SELECT * FROM auction_house";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                AuctionHouse ah = new AuctionHouse(
                    rs.getString("auction_house_name"),
                    rs.getString("auction_house_location"),
                    rs.getString("auction_house_contact_info")
                );
                houses.add(ah);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return houses;
    }
    
    public void updateAuctionHouse(AuctionHouse ah, int id) {
        String sql = "UPDATE auction_house SET auction_house_name = ?, auction_house_location = ?, auction_house_contact_info = ? WHERE auction_house_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, ah.getName());
            pstmt.setString(2, ah.getLocation());
            pstmt.setString(3, ah.getContactInfo());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteAuctionHouse(int id) {
        String sql = "DELETE FROM auction_house WHERE auction_house_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
