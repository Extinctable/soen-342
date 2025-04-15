// File: model/Institution.java
package model;

public class Institution {
    private int id;
    private String institutionName;
    private String institutionLocation;
    private String institutionContactInfo;
    private static int nextId = 1;
    
    public Institution(String institutionName, String institutionLocation, String institutionContactInfo) {
        this.id = nextId++;
        this.institutionName = institutionName;
        this.institutionLocation = institutionLocation;
        this.institutionContactInfo = institutionContactInfo;
    }
    
    public int getId() {
        return id;
    }
    
    public String getInstitutionName() {
        return institutionName;
    }
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }
    
    public String getInstitutionLocation() {
        return institutionLocation;
    }
    public void setInstitutionLocation(String institutionLocation) {
        this.institutionLocation = institutionLocation;
    }
    
    public String getInstitutionContactInfo() {
        return institutionContactInfo;
    }
    public void setInstitutionContactInfo(String institutionContactInfo) {
        this.institutionContactInfo = institutionContactInfo;
    }
    
    // Methods to manage affiliated entities
    public void manageClients(Client client) {
        System.out.println("Managing client: " + client.getUsername());
    }
    
    public void manageExperts(Expert expert) {
        System.out.println("Managing expert: " + expert.getUsername());
    }
    
    public void manageAuctionHouse(AuctionHouse auctionHouse) {
        System.out.println("Managing auction house: " + auctionHouse.getName());
    }
    
    public void manageObjectsOfInterest(ArtObject artObject) {
        System.out.println("Managing art object: " + artObject.getTitle());
    }
    
    @Override
    public String toString() {
        return "Institution [" + id + "]: " + institutionName;
    }
}
