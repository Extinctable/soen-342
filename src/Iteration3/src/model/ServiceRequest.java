// File: model/ServiceRequest.java
package model;

import java.time.LocalDateTime;

public class ServiceRequest {
    private int id;
    private Client client;
    private Expert expert;
    private String serviceType;
    private LocalDateTime requestedTime;
    private String notes;
    private String status;
    private ArtObject artObject; // Can be null if not related to an art object consultation
    private Auction auction;     // Can be null if not related to an auction consultation
    private static int nextId = 1;
    
    public ServiceRequest(Client client, Expert expert, String serviceType, LocalDateTime requestedTime, String notes) {
        this.id = nextId++;
        this.client = client;
        this.expert = expert;
        this.serviceType = serviceType;
        this.requestedTime = requestedTime;
        this.notes = notes;
        this.status = "Pending";
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    
    public Expert getExpert() {
        return expert;
    }
    public void setExpert(Expert expert) {
        this.expert = expert;
    }
    
    public String getServiceType() {
        return serviceType;
    }
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    
    public LocalDateTime getRequestedTime() {
        return requestedTime;
    }
    public void setRequestedTime(LocalDateTime requestedTime) {
        this.requestedTime = requestedTime;
    }
    
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public ArtObject getArtObject() {
        return artObject;
    }
    public void setArtObject(ArtObject artObject) {
        this.artObject = artObject;
    }
    
    public Auction getAuction() {
        return auction;
    }
    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    
    @Override
    public String toString() {
        return "ServiceRequest [" + id + "]: " + serviceType + " for client " + client.getUsername();
    }
}
