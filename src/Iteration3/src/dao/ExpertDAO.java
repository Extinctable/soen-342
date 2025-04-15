// File: dao/ExpertDAO.java
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Expert;
import model.Schedule;

public class ExpertDAO {
    
    // Utility method: converts the expert's availability list into a string.
    // Each Schedule is represented as "startTime-endTime" separated by semicolons.
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
    
    // Inserts a new Expert into the database and sets its generated ID.
    public void createExpert(Expert expert) {
        String sql = "INSERT INTO expert (username, password, institution_id, expert_name, expert_contact_address, expert_license_number, area_of_expertise, availability_schedule) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            pstmt.setString(1, expert.getUsername());
            pstmt.setString(2, expert.getPassword());
            pstmt.setNull(3, Types.INTEGER); // institution_id (not set yet)
            pstmt.setString(4, expert.getName());
            pstmt.setString(5, expert.getContactInfo());
            pstmt.setString(6, expert.getLicenseNumber());
            // Simply use the expertise string from the model.
            pstmt.setString(7, expert.getExpertiseAreas());
            pstmt.setString(8, scheduleListToString(expert));
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                expert.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Retrieves a single Expert by its ID and sets its persistent ID.
    public Expert getExpertById(int id) {
        String sql = "SELECT * FROM expert WHERE expert_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Expert expert = new Expert(
                    rs.getString("username"),  // username
                    rs.getString("password"),  // password
                    rs.getString("expert_name"),
                    rs.getString("expert_contact_address"),
                    rs.getString("expert_license_number"),
                    rs.getString("area_of_expertise")
                );
                expert.setId(rs.getInt("expert_id"));
                // (Optional: parse and set the availability_schedule if needed.)
                return expert;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    // Retrieves all Experts from the database, setting each object's ID.
    public List<Expert> getAllExperts() {
        List<Expert> experts = new ArrayList<>();
        String sql = "SELECT * FROM expert";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Expert expert = new Expert(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("expert_name"),
                    rs.getString("expert_contact_address"),
                    rs.getString("expert_license_number"),
                    rs.getString("area_of_expertise")
                );
                // Set the persistent ID from the database.
                expert.setId(rs.getInt("expert_id"));
                experts.add(expert);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return experts;
    }
    
    // Updates an existing Expert record.
    public void updateExpert(Expert expert, int id) {
        String sql = "UPDATE expert SET username = ?, password = ?, expert_name = ?, expert_contact_address = ?, expert_license_number = ?, area_of_expertise = ?, availability_schedule = ? WHERE expert_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, expert.getUsername());
            pstmt.setString(2, expert.getPassword());
            pstmt.setString(3, expert.getName());
            pstmt.setString(4, expert.getContactInfo());
            pstmt.setString(5, expert.getLicenseNumber());
            pstmt.setString(6, expert.getExpertiseAreas());
            pstmt.setString(7, scheduleListToString(expert));
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Deletes an Expert record.
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
