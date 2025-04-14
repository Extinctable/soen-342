// File: model/ArtAdvisorySystem.java
package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArtAdvisorySystem {
    private Administrator administrator;
    private List<Expert> experts;
    private List<Client> clients;
    private List<Client> pendingClients;
    private List<AuctionHouse> auctionHouses;
    private List<Auction> auctions;
    private List<ArtObject> artObjects;
    private List<ServiceRequest> serviceRequests;
    
    public ArtAdvisorySystem() {
        experts = new ArrayList<>();
        clients = new ArrayList<>();
        pendingClients = new ArrayList<>();
        auctionHouses = new ArrayList<>();
        auctions = new ArrayList<>();
        artObjects = new ArrayList<>();
        serviceRequests = new ArrayList<>();
    }
    
    public void setAdministrator(Administrator admin) {
        this.administrator = admin;
    }
    public Administrator getAdministrator() {
        return administrator;
    }
    
    public void addExpert(Expert expert) {
        experts.add(expert);
    }
    public void removeExpert(Expert expert) {
        experts.remove(expert);
    }
    public List<Expert> getExperts() {
        return experts;
    }
    
    public void addClient(Client client) {
        clients.add(client);
    }
    public void removeClient(Client client) {
        clients.remove(client);
    }
    public List<Client> getClients() {
        return clients;
    }
    
    public void addPendingClient(Client client) {
        pendingClients.add(client);
    }
    public List<Client> getPendingClients() {
        return pendingClients;
    }
    
    public void addAuctionHouse(AuctionHouse auctionHouse) {
        auctionHouses.add(auctionHouse);
    }
    public void removeAuctionHouse(AuctionHouse auctionHouse) {
        auctionHouses.remove(auctionHouse);
    }
    public List<AuctionHouse> getAuctionHouses() {
        return auctionHouses;
    }
    
    public void addAuction(Auction auction) {
        auctions.add(auction);
    }
    public void removeAuction(Auction auction) {
        auctions.remove(auction);
    }
    public List<Auction> getAuctions() {
        return auctions;
    }
    
    public void addArtObject(ArtObject artObject) {
        artObjects.add(artObject);
    }
    public void removeArtObject(ArtObject artObject) {
        artObjects.remove(artObject);
    }
    public List<ArtObject> getArtObjects() {
        return artObjects;
    }
    
    public void addServiceRequest(ServiceRequest sr) {
        serviceRequests.add(sr);
    }
    public void removeServiceRequest(ServiceRequest sr) {
        serviceRequests.remove(sr);
    }
    public List<ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }
    
    public Set<String> getAllExpertiseAreas() {
        Set<String> allAreas = new HashSet<>();
        for (Expert expert : experts) {
            allAreas.addAll(expert.getExpertiseAreas());
        }
        return allAreas;
    }
    
    // Lookup methods by id
    public Expert getExpertById(int id) {
        for (Expert expert : experts) {
            if (expert.getId() == id) return expert;
        }
        return null;
    }
    
    public Client getClientById(int id) {
        for (Client client : clients) {
            if (client.getId() == id) return client;
        }
        return null;
    }
    
    public AuctionHouse getAuctionHouseById(int id) {
        for (AuctionHouse ah : auctionHouses) {
            if (ah.getId() == id) return ah;
        }
        return null;
    }
    
    public Auction getAuctionById(int id) {
        for (Auction auction : auctions) {
            if (auction.getId() == id) return auction;
        }
        return null;
    }
    
    public ArtObject getArtObjectById(int id) {
        for (ArtObject ao : artObjects) {
            if (ao.getId() == id) return ao;
        }
        return null;
    }
    
    public ServiceRequest getServiceRequestById(int id) {
        for (ServiceRequest sr : serviceRequests) {
            if (sr.getId() == id) return sr;
        }
        return null;
    }
    
    // Additional lookup methods (e.g., by expertise, type) can be added here.
    
    // Populate system with mock data for testing
    public static void createMockData(ArtAdvisorySystem system) {
        Administrator admin = Administrator.getInstance("admin", "admin123", "System Admin", "admin@system.com", "555-1234");
        system.setAdministrator(admin);
        
        Client client1 = new Client("jdoe", "password", "John Doe", "jdoe@example.com", "ClientAffil", "I need consultation");
        system.addClient(client1);
        
        Expert expert1 = new Expert("asmith", "password", "Alice Smith", "alice@example.com", "LIC001");
        expert1.addExpertiseArea("Contemporary Art");
        system.addExpert(expert1);
        
        AuctionHouse ah1 = new AuctionHouse("Modern Auctions", "123 Auction St", "contact@modernauctions.com");
        system.addAuctionHouse(ah1);
        
        // More mock data can be added as needed…
    }
    
    @Override
    public String toString() {
        return "ArtAdvisorySystem with " + experts.size() + " experts, " + clients.size() + " clients.";
    }
}
