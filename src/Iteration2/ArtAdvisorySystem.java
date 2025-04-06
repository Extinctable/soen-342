import java.util.*;

/**
 *
 * @author massimocaruso
 */
class ArtAdvisorySystem {
    private Administrator administrator;
    private List<Expert> experts;
    private List<Client> clients;
    private List<Client> pendingClients;
    private List<AuctionHouse> auctionHouses;
    private List<Auction> auctions;
    private List<ArtObject> artObjects;
    private List<ServiceRequest> serviceRequests;
    
    public ArtAdvisorySystem() {
        this.experts = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.pendingClients = new ArrayList<>();
        this.auctionHouses = new ArrayList<>();
        this.auctions = new ArrayList<>();
        this.artObjects = new ArrayList<>();
        this.serviceRequests = new ArrayList<>();
    }
    
    // Administrator methods
    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }
    
    public Administrator getAdministrator() {
        return administrator;
    }
    
    // Expert methods
    public void addExpert(Expert expert) {
        experts.add(expert);
    }
    
    public void removeExpert(Expert expert) {
        experts.remove(expert);
    }
    
    public List<Expert> getExperts() {
        return experts;
    }
    
    public Expert getExpertById(int id) {
        for (Expert expert : experts) {
            if (expert.getId() == id) {
                return expert;
            }
        }
        return null;
    }
    
    public List<Expert> getExpertsByExpertiseArea(String area) {
        List<Expert> matchingExperts = new ArrayList<>();
        for (Expert expert : experts) {
            if (expert.getExpertiseAreas().contains(area)) {
                matchingExperts.add(expert);
            }
        }
        return matchingExperts;
    }
    
    public Set<String> getAllExpertiseAreas() {
        Set<String> allAreas = new HashSet<>();
        for (Expert expert : experts) {
            allAreas.addAll(expert.getExpertiseAreas());
        }
        return allAreas;
    }
    
    // Client methods
    public void addClient(Client client) {
        client.setApproved(true);
        clients.add(client);
    }
    
    public void removeClient(Client client) {
        clients.remove(client);
    }
    
    public List<Client> getClients() {
        return clients;
    }
    
    public Client getClientById(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }
    
    public void addPendingClient(Client client) {
        pendingClients.add(client);
    }
    
    public void approveClient(Client client) {
        client.setApproved(true);
        pendingClients.remove(client);
        clients.add(client);
    }
    
    public List<Client> getPendingClients() {
        return pendingClients;
    }
    
    // Auction house methods
    public void addAuctionHouse(AuctionHouse auctionHouse) {
        auctionHouses.add(auctionHouse);
    }
    
    public void removeAuctionHouse(AuctionHouse auctionHouse) {
        auctionHouses.remove(auctionHouse);
    }
    
    public List<AuctionHouse> getAuctionHouses() {
        return auctionHouses;
    }
    
    public AuctionHouse getAuctionHouseById(int id) {
        for (AuctionHouse auctionHouse : auctionHouses) {
            if (auctionHouse.getId() == id) {
                return auctionHouse;
            }
        }
        return null;
    }
    
    // Auction methods
    public void addAuction(Auction auction) {
        auctions.add(auction);
    }
    
    public void removeAuction(Auction auction) {
        auctions.remove(auction);
    }
    
    public List<Auction> getAuctions() {
        return auctions;
    }
    
    public Auction getAuctionById(int id) {
        for (Auction auction : auctions) {
            if (auction.getId() == id) {
                return auction;
            }
        }
        return null;
    }
    
    public List<Auction> getAuctionsByExpertise(Set<String> expertiseAreas) {
        List<Auction> matchingAuctions = new ArrayList<>();
        for (Auction auction : auctions) {
            if (expertiseAreas.contains(auction.getSpecialty())) {
                matchingAuctions.add(auction);
            }
        }
        return matchingAuctions;
    }
    
    public List<Auction> getAuctionsBySpecialty(String specialty) {
        List<Auction> matchingAuctions = new ArrayList<>();
        for (Auction auction : auctions) {
            if (auction.getSpecialty().equalsIgnoreCase(specialty)) {
                matchingAuctions.add(auction);
            }
        }
        return matchingAuctions;
    }
    
    // Art object methods
    public void addArtObject(ArtObject artObject) {
        artObjects.add(artObject);
    }
    
    public void removeArtObject(ArtObject artObject) {
        artObjects.remove(artObject);
    }
    
    public List<ArtObject> getArtObjects() {
        return artObjects;
    }
    
    public ArtObject getArtObjectById(int id) {
        for (ArtObject artObject : artObjects) {
            if (artObject.getId() == id) {
                return artObject;
            }
        }
        return null;
    }
    
    public List<ArtObject> getArtObjectsByExpertise(Set<String> expertiseAreas) {
        List<ArtObject> matchingObjects = new ArrayList<>();
        for (ArtObject artObject : artObjects) {
            if (expertiseAreas.contains(artObject.getType())) {
                matchingObjects.add(artObject);
            }
        }
        return matchingObjects;
    }
    
    public List<ArtObject> getArtObjectsByType(String type) {
        List<ArtObject> matchingObjects = new ArrayList<>();
        for (ArtObject artObject : artObjects) {
            if (artObject.getType().equalsIgnoreCase(type)) {
                matchingObjects.add(artObject);
            }
        }
        return matchingObjects;
    }
    
    // Service request methods
    public void addServiceRequest(ServiceRequest request) {
        serviceRequests.add(request);
    }
    
    public List<ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }
    
    public ServiceRequest getServiceRequestById(int id) {
        for (ServiceRequest request : serviceRequests) {
            if (request.getId() == id) {
                return request;
            }
        }
        return null;
    }
    
    public List<ServiceRequest> getServiceRequestsByExpert(Expert expert) {
        List<ServiceRequest> matchingRequests = new ArrayList<>();
        for (ServiceRequest request : serviceRequests) {
            if (request.getExpert().getId() == expert.getId()) {
                matchingRequests.add(request);
            }
        }
        return matchingRequests;
    }
    
    public List<ServiceRequest> getServiceRequestsByClient(Client client) {
        List<ServiceRequest> matchingRequests = new ArrayList<>();
        for (ServiceRequest request : serviceRequests) {
            if (request.getClient().getId() == client.getId()) {
                matchingRequests.add(request);
            }
        }
        return matchingRequests;
    }
    
    // Login method
    public User login(String username, String password) {
        if (administrator != null && administrator.getUsername().equals(username)) {
            if (administrator.authenticate(password)) {
                return administrator;
            }
        }
        
        for (Expert expert : experts) {
            if (expert.getUsername().equals(username) && expert.authenticate(password)) {
                return expert;
            }
        }
        
        for (Client client : clients) {
            if (client.getUsername().equals(username) && client.authenticate(password) && client.isApproved()) {
                return client;
            }
        }
        
        return null;
    }
}