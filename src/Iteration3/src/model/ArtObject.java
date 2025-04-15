// File: model/ArtObject.java
package model;

public class ArtObject {
    private int id;
    private String title;
    private String description;
    private String type;
    private boolean isOwned;
    private boolean isToBeAuctioned;
    private Auction auction; // May be null if not associated with an auction
    private static int nextId = 1;
    
    public ArtObject(String title, String description, String type, boolean isOwned, boolean isToBeAuctioned, Auction auction) {
        this.id = nextId++;
        this.title = title;
        this.description = description;
        this.type = type;
        this.isOwned = isOwned;
        this.isToBeAuctioned = isToBeAuctioned;
        this.auction = auction;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public boolean isOwned() {
        return isOwned;
    }
    public void setOwned(boolean isOwned) {
        this.isOwned = isOwned;
    }
    
    public boolean isToBeAuctioned() {
        return isToBeAuctioned;
    }
    public void setToBeAuctioned(boolean isToBeAuctioned) {
        this.isToBeAuctioned = isToBeAuctioned;
    }
    
    public Auction getAuction() {
        return auction;
    }
    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    
    @Override
    public String toString() {
        return "ArtObject [" + id + "]: " + title + " (" + type + ")";
    }
}
