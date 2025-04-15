// File: model/Auction.java
package model;

public class Auction {
    private int id;
    private String title;
    private String specialty;
    private AuctionHouse auctionHouse;
    private Schedule schedule;
    private boolean isOnline;
    private Schedule viewingSchedule;
    private static int nextId = 1;
    
    public Auction(String title, String specialty, AuctionHouse auctionHouse, Schedule schedule, boolean isOnline, Schedule viewingSchedule) {
        this.id = nextId++;
        this.title = title;
        this.specialty = specialty;
        this.auctionHouse = auctionHouse;
        this.schedule = schedule;
        this.isOnline = isOnline;
        this.viewingSchedule = viewingSchedule;
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
    
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
    public AuctionHouse getAuctionHouse() {
        return auctionHouse;
    }
    public void setAuctionHouse(AuctionHouse auctionHouse) {
        this.auctionHouse = auctionHouse;
    }
    
    public Schedule getSchedule() {
        return schedule;
    }
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    public boolean isOnline() {
        return isOnline;
    }
    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
    
    public Schedule getViewingSchedule() {
        return viewingSchedule;
    }
    public void setViewingSchedule(Schedule viewingSchedule) {
        this.viewingSchedule = viewingSchedule;
    }
    
    @Override
    public String toString() {
        return "Auction [" + id + "]: " + title + " (Specialty: " + specialty + ")";
    }
}
