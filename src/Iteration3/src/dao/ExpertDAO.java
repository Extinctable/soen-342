// File: dao/ExpertDAO.java
package dao;

import model.Expert;
import model.Schedule;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpertDAO {
    
    // Utility: convert expertise set to a comma-separated string.
    private String expertiseSetToString(Expert expert) {
        if (expert.getExpertiseAreas().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String area : expert.getExpertiseAreas()) {
            sb.append(area).append(",");
        }
        // Remove the last comma.
        return sb.substring(0, sb.length() - 1);
    }
    
    // Utility: convert availability list to a string (each schedule as startTime-endTime separated by semicolons).
    private String scheduleListToString(Expert expert) {
        if (expert.getAvailability().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Schedule s : expert.getAvailability()) {
            sb.append(s.getStartTime()).append("-").append(s.getEndTime()).append(";");
        }
        return sb.toString();
    }
    
    public void createExpert(Expert expert) {
        String sql = "INSERT INTO expert (institution_id, expert_name, expert_contact_address, expert_license_number, area_of_expertise, availability_schedule) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            pstmt.setNull(1, Types.INTEGER); // institution_id (not set yet)
            pstmt.setString(2, expert.getName());
            pstmt.setString(3, expert.getContactInfo());
            pstmt.setString(4, expert.getLicenseNumber());
            pstmt.setString(5, expertiseSetToString(expert));
            pstmt.setString(6, scheduleListToString(expert));
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Optionally assign the generated ID.
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public Expert getExpertById(int id) {
        String sql = "SELECT * FROM expert WHERE expert_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Expert expert = new Expert(
                    "", // username is not stored separately
                    "", // password is not persisted here
                    rs.getString("expert_name"),
                    rs.getString("expert_contact_address"),
                    rs.getString("expert_license_number")
                );
                // Parsing of area_of_expertise and availability_schedule can be added as needed.
                return expert;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Expert> getAllExperts() {
        List<Expert> experts = new ArrayList<>();
        String sql = "SELECT * FROM expert";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Expert expert = new Expert(
                    "",
                    "",
                    rs.getString("expert_name"),
                    rs.getString("expert_contact_address"),
                    rs.getString("expert_license_number")
                );
                experts.add(expert);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return experts;
    }
    
    public void updateExpert(Expert expert, int id) {
        String sql = "UPDATE expert SET expert_name = ?, expert_contact_address = ?, expert_license_number = ?, area_of_expertise = ?, availability_schedule = ? WHERE expert_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, expert.getName());
            pstmt.setString(2, expert.getContactInfo());
            pstmt.setString(3, expert.getLicenseNumber());
            pstmt.setString(4, expertiseSetToString(expert));
            pstmt.setString(5, scheduleListToString(expert));
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteExpert(int id) {
        String sql = "DELETE FROM expert WHERE expert_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
