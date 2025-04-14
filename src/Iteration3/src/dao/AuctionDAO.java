// File: dao/AuctionDAO.java
package dao;

import model.Auction;
import model.AuctionHouse;
import model.Schedule;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionDAO {
    public void createAuction(Auction auction) {
        String sql = "INSERT INTO auction (auction_house_id, auction_name, auction_schedule, auction_type, is_online, viewing_schedule) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            // Assume auctionHouse is set in the Auction object.
            pstmt.setInt(1, auction.getAuctionHouse().getId());
            pstmt.setString(2, auction.getTitle());
            // Store auction_schedule using the start time (for simplicity).
            pstmt.setTimestamp(3, Timestamp.valueOf(auction.getSchedule().getStartTime()));
            pstmt.setString(4, auction.getSpecialty());
            pstmt.setBoolean(5, auction.isOnline());
            // Store viewing_schedule using the start time.
            pstmt.setTimestamp(6, Timestamp.valueOf(auction.getViewingSchedule().getStartTime()));
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Optionally assign the generated ID.
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public Auction getAuctionById(int id, AuctionHouseDAO auctionHouseDAO) {
        String sql = "SELECT * FROM auction WHERE auction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int auctionHouseId = rs.getInt("auction_house_id");
                AuctionHouse auctionHouse = auctionHouseDAO.getAuctionHouseById(auctionHouseId);
                Timestamp ts = rs.getTimestamp("auction_schedule");
                // For demonstration, we use the same timestamp for both start and end.
                Schedule schedule = new Schedule(ts.toLocalDateTime(), ts.toLocalDateTime());
                Timestamp vts = rs.getTimestamp("viewing_schedule");
                Schedule viewingSchedule = new Schedule(vts.toLocalDateTime(), vts.toLocalDateTime());
                Auction auction = new Auction(
                    rs.getString("auction_name"),
                    rs.getString("auction_type"),
                    auctionHouse,
                    schedule,
                    rs.getBoolean("is_online"),
                    viewingSchedule
                );
                return auction;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Auction> getAllAuctions(AuctionHouseDAO auctionHouseDAO) {
        List<Auction> auctions = new ArrayList<>();
        String sql = "SELECT * FROM auction";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                int auctionHouseId = rs.getInt("auction_house_id");
                AuctionHouse auctionHouse = auctionHouseDAO.getAuctionHouseById(auctionHouseId);
                Timestamp ts = rs.getTimestamp("auction_schedule");
                Schedule schedule = new Schedule(ts.toLocalDateTime(), ts.toLocalDateTime());
                Timestamp vts = rs.getTimestamp("viewing_schedule");
                Schedule viewingSchedule = new Schedule(vts.toLocalDateTime(), vts.toLocalDateTime());
                Auction auction = new Auction(
                    rs.getString("auction_name"),
                    rs.getString("auction_type"),
                    auctionHouse,
                    schedule,
                    rs.getBoolean("is_online"),
                    viewingSchedule
                );
                auctions.add(auction);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return auctions;
    }
    
    public void updateAuction(Auction auction, int id) {
        String sql = "UPDATE auction SET auction_house_id = ?, auction_name = ?, auction_schedule = ?, auction_type = ?, is_online = ?, viewing_schedule = ? WHERE auction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, auction.getAuctionHouse().getId());
            pstmt.setString(2, auction.getTitle());
            pstmt.setTimestamp(3, Timestamp.valueOf(auction.getSchedule().getStartTime()));
            pstmt.setString(4, auction.getSpecialty());
            pstmt.setBoolean(5, auction.isOnline());
            pstmt.setTimestamp(6, Timestamp.valueOf(auction.getViewingSchedule().getStartTime()));
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteAuction(int id) {
        String sql = "DELETE FROM auction WHERE auction_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
