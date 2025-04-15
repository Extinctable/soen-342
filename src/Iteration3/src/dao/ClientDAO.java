// File: dao/ClientDAO.java
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Client;

public class ClientDAO {

    // Inserts a new client and sets the generated client_id on the client object.
    public void createClient(Client client) {
        String sql = "INSERT INTO client (client_email, client_password, client_affiliation, client_contact_info, intent_description, approved, institution_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, client.getUsername());
            pstmt.setString(2, client.getPassword());
            pstmt.setString(3, client.getAffiliation());
            pstmt.setString(4, client.getContactInfo());
            pstmt.setString(5, client.getIntent());
            pstmt.setBoolean(6, client.isApproved());
            pstmt.setNull(7, Types.INTEGER); // institution_id (not set yet)
            
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Retrieves a client by its ID and sets the persistent ID on the returned object.
    public Client getClientById(int id) {
        String sql = "SELECT * FROM client WHERE client_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Client client = new Client(
                        rs.getString("client_email"),
                        rs.getString("client_password"),
                        "Name not stored",  // Adjust if you store name separately
                        rs.getString("client_contact_info"),
                        rs.getString("client_affiliation"),
                        rs.getString("intent_description")
                );
                client.setApproved(rs.getBoolean("approved"));
                client.setId(rs.getInt("client_id")); // Set the persistent ID from the database
                return client;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    // Retrieves all clients from the database and sets their IDs.
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                Client client = new Client(
                        rs.getString("client_email"),
                        rs.getString("client_password"),
                        "Name not stored", // Adjust if you store a name column
                        rs.getString("client_contact_info"),
                        rs.getString("client_affiliation"),
                        rs.getString("intent_description")
                );
                client.setApproved(rs.getBoolean("approved"));
                client.setId(rs.getInt("client_id"));  // Update the client ID from DB
                clients.add(client);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return clients;
    }
    
    // Updates a client record.
    public void updateClient(Client client, int id) {
        String sql = "UPDATE client SET client_email = ?, client_password = ?, client_affiliation = ?, client_contact_info = ?, intent_description = ?, approved = ? WHERE client_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, client.getUsername());
            pstmt.setString(2, client.getPassword());
            pstmt.setString(3, client.getAffiliation());
            pstmt.setString(4, client.getContactInfo());
            pstmt.setString(5, client.getIntent());
            pstmt.setBoolean(6, client.isApproved());
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Deletes a client record.
    public void deleteClient(int id) {
        String sql = "DELETE FROM client WHERE client_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // New helper method to fetch a client by email (username).
    public Client getClientByEmail(String email) {
        String sql = "SELECT * FROM client WHERE client_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Client client = new Client(
                        rs.getString("client_email"),
                        rs.getString("client_password"),
                        "Name not stored",
                        rs.getString("client_contact_info"),
                        rs.getString("client_affiliation"),
                        rs.getString("intent_description")
                );
                client.setApproved(rs.getBoolean("approved"));
                client.setId(rs.getInt("client_id"));
                return client;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
