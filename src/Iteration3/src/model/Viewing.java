// File: model/Viewing.java
package model;

import java.time.LocalDateTime;

public class Viewing {
    private int id;
    private AuctionHouse auctionHouse;
    private LocalDateTime viewingTime;
    private static int nextId = 1;
    
    public Viewing(AuctionHouse auctionHouse, LocalDateTime viewingTime) {
        this.id = nextId++;
        this.auctionHouse = auctionHouse;
        this.viewingTime = viewingTime;
    }
    
    public int getId() {
        return id;
    }
    
    public AuctionHouse getAuctionHouse() {
        return auctionHouse;
    }
    public void setAuctionHouse(AuctionHouse auctionHouse) {
        this.auctionHouse = auctionHouse;
    }
    
    public LocalDateTime getViewingTime() {
        return viewingTime;
    }
    public void setViewingTime(LocalDateTime viewingTime) {
        this.viewingTime = viewingTime;
    }
    
    public String getViewingDetails() {
        return "Viewing at " + auctionHouse.getName() + " on " + viewingTime;
    }
    
    @Override
    public String toString() {
        return "Viewing [" + id + "]: " + getViewingDetails();
    }
}
