// File: model/AuctionHouse.java
package model;

public class AuctionHouse {
    private int id;
    private String name;
    private String location;
    private String contactInfo;
    private static int nextId = 1;
    
    public AuctionHouse(String name, String location, String contactInfo) {
        this.id = nextId++;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    
    @Override
    public String toString() {
        return "AuctionHouse [" + id + "]: " + name + ", " + location;
    }
}
