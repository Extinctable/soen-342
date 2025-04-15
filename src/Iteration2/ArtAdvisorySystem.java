import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * @author massimocaruso
 * @author jananaamahathevan
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

    public static void createMockData(ArtAdvisorySystem system) {
        // Add auction houses
        AuctionHouse christie = new AuctionHouse("Christie's", "London", "christie@example.com");
        AuctionHouse sotheby = new AuctionHouse("Sotheby's", "New York", "sotheby@example.com");
        system.addAuctionHouse(christie);
        system.addAuctionHouse(sotheby);
        
        // Add auctions
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime may12_12pm = LocalDateTime.parse("2025-05-12 12:00", formatter);
        LocalDateTime may12_6pm = LocalDateTime.parse("2025-05-12 18:00", formatter);
        LocalDateTime may10_10am = LocalDateTime.parse("2025-05-10 10:00", formatter);
        LocalDateTime may11_4pm = LocalDateTime.parse("2025-05-11 16:00", formatter);
        
        Schedule auctionSchedule1 = new Schedule(may12_12pm, may12_6pm);
        Schedule viewingSchedule1 = new Schedule(may10_10am, may11_4pm);
        
        Auction orientalAuction = new Auction("Oriental Auction", "Oriental", christie, auctionSchedule1, false, viewingSchedule1);
        system.addAuction(orientalAuction);
        
        LocalDateTime jun15_10am = LocalDateTime.parse("2025-06-15 10:00", formatter);
        LocalDateTime jun16_5pm = LocalDateTime.parse("2025-06-16 17:00", formatter);
        LocalDateTime jun13_11am = LocalDateTime.parse("2025-06-13 11:00", formatter);
        LocalDateTime jun14_3pm = LocalDateTime.parse("2025-06-14 15:00", formatter);
        
        Schedule auctionSchedule2 = new Schedule(jun15_10am, jun16_5pm);
        Schedule viewingSchedule2 = new Schedule(jun13_11am, jun14_3pm);
        
        Auction modernArtAuction = new Auction("Modern Art Auction", "Modern Art", sotheby, auctionSchedule2, false, viewingSchedule2);
        system.addAuction(modernArtAuction);
        
        // Add experts
        Expert expert1 = new Expert("jsmith", "pass123", "John Smith", "john@example.com", "EXP001");
        expert1.addExpertiseArea("Oriental");
        expert1.addExpertiseArea("Ceramics");
        
        Expert expert2 = new Expert("mlee", "pass456", "Maria Lee", "maria@example.com", "EXP002");
        expert2.addExpertiseArea("Modern Art");
        expert2.addExpertiseArea("Paintings");
        
        system.addExpert(expert1);
        system.addExpert(expert2);
        
        // Add client
        Client client = new Client("client@example.com", "clientpass", "Art Museum", "123-456-7890", "Museum", "Looking for Oriental art pieces");
        system.addClient(client);
        
        // Add art objects
        ArtObject object1 = new ArtObject("Ming Vase", "A beautiful Ming Dynasty vase from the 15th century", "Ceramics", false, true, orientalAuction);
        ArtObject object2 = new ArtObject("Abstract Painting", "Modern abstract painting by renowned artist", "Paintings", true, false, null);
        
        system.addArtObject(object1);
        system.addArtObject(object2);
        
        // Add availability for experts
        LocalDateTime avail1Start = LocalDateTime.parse("2025-05-11 09:00", formatter);
        LocalDateTime avail1End = LocalDateTime.parse("2025-05-11 17:00", formatter);
        expert1.addAvailability(new Schedule(avail1Start, avail1End));
        
        LocalDateTime avail2Start = LocalDateTime.parse("2025-06-14 10:00", formatter);
        LocalDateTime avail2End = LocalDateTime.parse("2025-06-14 16:00", formatter);
        expert2.addAvailability(new Schedule(avail2Start, avail2End));
    }
}