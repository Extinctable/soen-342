// File: dao/ServiceRequestDAO.java
package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.ArtObject;
import model.Auction;
import model.Client;
import model.Expert;
import model.ServiceRequest;

public class ServiceRequestDAO {
    
    public void createServiceRequest(ServiceRequest sr) {
        String sql = "INSERT INTO service_request (client_id, expert_id, service_type, requested_time, notes, status, object_id, auction_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            // Set client_id (assumed non-null)
            pstmt.setInt(1, sr.getClient().getId());
            
            // Set expert_id if available, else SQL null.
            if (sr.getExpert() != null) {
                pstmt.setInt(2, sr.getExpert().getId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            
            pstmt.setString(3, sr.getServiceType());
            pstmt.setTimestamp(4, Timestamp.valueOf(sr.getRequestedTime()));
            pstmt.setString(5, sr.getNotes());
            pstmt.setString(6, sr.getStatus());
            
            // Set art_object_id if not null.
            if (sr.getArtObject() != null) {
                pstmt.setInt(7, sr.getArtObject().getId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }
            // Set auction_id if not null.
            if (sr.getAuction() != null) {
                pstmt.setInt(8, sr.getAuction().getId());
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }
            
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Optionally update the service request object with the generated id.
                // sr.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public ServiceRequest getServiceRequestById(int id) {
        String sql = "SELECT * FROM service_request WHERE request_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Create minimal objects for client and expert.
                Client client = new Client("dummy", "dummy", "dummy", "dummy", "dummy", "dummy");
                client.setId(rs.getInt("client_id"));
                
                Expert expert = null;
                int expertId = rs.getInt("expert_id");
                if (!rs.wasNull()) {
                    expert = new Expert("dummy", "dummy", "dummy", "dummy", "dummy", "dummy");
                    expert.setId(expertId);
                }
                
                // Create ArtObject if available.
                ArtObject artObject = null;
                int artObjectId = rs.getInt("object_id");
                if (!rs.wasNull()) {
                    artObject = new ArtObject("dummy", "dummy", "dummy", false, false, null);
                    artObject.setId(artObjectId);
                }
                
                // Create Auction if available.
                Auction auction = null;
                int auctionId = rs.getInt("auction_id");
                if (!rs.wasNull()) {
                    auction = new Auction("dummy", "dummy", null, null, false, null);
                    auction.setId(auctionId);
                }
                
                LocalDateTime requestedTime = rs.getTimestamp("requested_time").toLocalDateTime();
                ServiceRequest sr = new ServiceRequest(client, expert, rs.getString("service_type"), requestedTime, rs.getString("notes"));
                sr.setStatus(rs.getString("status"));
                sr.setId(rs.getInt("request_id"));
                sr.setArtObject(artObject);
                sr.setAuction(auction);
                return sr;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<ServiceRequest> getAllServiceRequests() {
        List<ServiceRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM service_request";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()){
                // Create a minimal client object and set its ID.
                Client client = new Client("dummy", "dummy", "dummy", "dummy", "dummy", "dummy");
                client.setId(rs.getInt("client_id"));
                
                // Create expert object if expert_id is present.
                Expert expert = null;
                int expertId = rs.getInt("expert_id");
                if (!rs.wasNull()){
                    expert = new Expert("dummy", "dummy", "dummy", "dummy", "dummy", "dummy");
                    expert.setId(expertId);
                }
                
                // Create art object if object_id is not null.
                ArtObject artObject = null;
                int artObjectId = rs.getInt("object_id");
                if (!rs.wasNull()){
                    artObject = new ArtObject("dummy", "dummy", "dummy", false, false, null);
                    artObject.setId(artObjectId);
                }
                
                // Create auction if auction_id is not null.
                Auction auction = null;
                int auctionId = rs.getInt("auction_id");
                if (!rs.wasNull()){
                    auction = new Auction("dummy", "dummy", null, null, false, null);
                    auction.setId(auctionId);
                }
                
                LocalDateTime requestedTime = rs.getTimestamp("requested_time").toLocalDateTime();
                ServiceRequest sr = new ServiceRequest(client, expert, rs.getString("service_type"), requestedTime, rs.getString("notes"));
                sr.setStatus(rs.getString("status"));
                sr.setId(rs.getInt("request_id"));
                sr.setArtObject(artObject);
                sr.setAuction(auction);
                
                requests.add(sr);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        return requests;
    }
    
    public void updateServiceRequest(ServiceRequest sr, int id) {
        String sql = "UPDATE service_request SET client_id = ?, expert_id = ?, service_type = ?, requested_time = ?, notes = ?, status = ?, object_id = ?, auction_id = ? WHERE request_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, sr.getClient().getId());
            if (sr.getExpert() != null) {
                pstmt.setInt(2, sr.getExpert().getId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            pstmt.setString(3, sr.getServiceType());
            pstmt.setTimestamp(4, Timestamp.valueOf(sr.getRequestedTime()));
            pstmt.setString(5, sr.getNotes());
            pstmt.setString(6, sr.getStatus());
            if (sr.getArtObject() != null) {
                pstmt.setInt(7, sr.getArtObject().getId());
            } else {
                pstmt.setNull(7, Types.INTEGER);
            }
            if (sr.getAuction() != null) {
                pstmt.setInt(8, sr.getAuction().getId());
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }
            pstmt.setInt(9, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteServiceRequest(int id) {
        String sql = "DELETE FROM service_request WHERE request_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
