// File: dao/ViewingDAO.java
package dao;

import model.Viewing;
import model.AuctionHouse;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ViewingDAO {
    public void createViewing(Viewing viewing) {
        String sql = "INSERT INTO viewing (auction_house_id, viewing_time) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            pstmt.setInt(1, viewing.getAuctionHouse().getId());
            pstmt.setTimestamp(2, Timestamp.valueOf(viewing.getViewingTime()));
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Optionally assign the generated ID.
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public Viewing getViewingById(int id, AuctionHouseDAO auctionHouseDAO) {
        String sql = "SELECT * FROM viewing WHERE viewing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int auctionHouseId = rs.getInt("auction_house_id");
                AuctionHouse ah = auctionHouseDAO.getAuctionHouseById(auctionHouseId);
                LocalDateTime time = rs.getTimestamp("viewing_time").toLocalDateTime();
                Viewing viewing = new Viewing(ah, time);
                return viewing;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Viewing> getAllViewings(AuctionHouseDAO auctionHouseDAO) {
        List<Viewing> viewings = new ArrayList<>();
        String sql = "SELECT * FROM viewing";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                int auctionHouseId = rs.getInt("auction_house_id");
                AuctionHouse ah = auctionHouseDAO.getAuctionHouseById(auctionHouseId);
                LocalDateTime time = rs.getTimestamp("viewing_time").toLocalDateTime();
                Viewing viewing = new Viewing(ah, time);
                viewings.add(viewing);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return viewings;
    }
    
    public void updateViewing(Viewing viewing, int id) {
        String sql = "UPDATE viewing SET auction_house_id = ?, viewing_time = ? WHERE viewing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, viewing.getAuctionHouse().getId());
            pstmt.setTimestamp(2, Timestamp.valueOf(viewing.getViewingTime()));
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteViewing(int id) {
        String sql = "DELETE FROM viewing WHERE viewing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
