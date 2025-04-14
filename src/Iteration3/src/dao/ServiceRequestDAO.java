// File: dao/ServiceRequestDAO.java
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Client;
import model.Expert;
import model.ServiceRequest;

public class ServiceRequestDAO {
    public void createServiceRequest(ServiceRequest sr) {
        String sql = "INSERT INTO service_request (client_id, expert_id, service_type, requested_time, notes, status, object_id, auction_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            // Client ID should not be null since the client exists.
            pstmt.setInt(1, sr.getClient().getId());
            
            // Check if expert is provided. If not, set SQL null.
            if (sr.getExpert() != null) {
                pstmt.setInt(2, sr.getExpert().getId());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }
            
            pstmt.setString(3, sr.getServiceType());
            pstmt.setTimestamp(4, Timestamp.valueOf(sr.getRequestedTime()));
            pstmt.setString(5, sr.getNotes());
            pstmt.setString(6, sr.getStatus());
            
            // For art object and auction, if they are null, set SQL null.
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
            
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Optionally update the service request ID in the object.
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
                // For retrieval, you would normally join and get the full Client/Expert objects.
                Client client = new Client("dummy", "dummy", "Dummy", "dummy", "dummy", "dummy");
                Expert expert = new Expert("dummy", "dummy", "Dummy", "dummy", "dummy");
                ServiceRequest sr = new ServiceRequest(
                     client,
                     expert,
                     rs.getString("service_type"),
                     rs.getTimestamp("requested_time").toLocalDateTime(),
                     rs.getString("notes")
                );
                sr.setStatus(rs.getString("status"));
                return sr;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<ServiceRequest> getAllServiceRequests() {
        List<ServiceRequest> srs = new ArrayList<>();
        String sql = "SELECT * FROM service_request";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Client client = new Client("dummy", "dummy", "Dummy", "dummy", "dummy", "dummy");
                Expert expert = new Expert("dummy", "dummy", "Dummy", "dummy", "dummy");
                ServiceRequest sr = new ServiceRequest(
                     client,
                     expert,
                     rs.getString("service_type"),
                     rs.getTimestamp("requested_time").toLocalDateTime(),
                     rs.getString("notes")
                );
                sr.setStatus(rs.getString("status"));
                srs.add(sr);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return srs;
    }
    
    public void updateServiceRequest(ServiceRequest sr, int id) {
        String sql = "UPDATE service_request SET client_id = ?, expert_id = ?, service_type = ?, requested_time = ?, notes = ?, status = ?, object_id = ?, auction_id = ? WHERE request_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, sr.getClient().getId());
            pstmt.setInt(2, sr.getExpert().getId());
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
