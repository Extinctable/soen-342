package controller;

import dao.ArtObjectDAO;
import dao.AuctionDAO;
import dao.AuctionHouseDAO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import model.ArtAdvisorySystem;
import model.ArtObject;
import model.Auction;
import model.Expert;
import view.ExpertView;

public class ExpertController {
    private Expert expert;
    private ExpertView expertView;
    private ArtAdvisorySystem system;
    // DAO references
    private AuctionDAO auctionDAO;
    private AuctionHouseDAO auctionHouseDAO;
    private ArtObjectDAO artObjectDAO;

    public ExpertController(ArtAdvisorySystem system, ExpertView expertView, Expert expert) {
        this.system = system;
        this.expertView = expertView;
        this.expert = expert;
        auctionDAO = new AuctionDAO();
        auctionHouseDAO = new AuctionHouseDAO();
        artObjectDAO = new ArtObjectDAO();
    }
    
    public void handleMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            expertView.displayMenu();
            String choice = scanner.nextLine();
            switch(choice) {
                case "1":
                    viewAuctionsByExpertise();
                    break;
                case "2":
                    viewArtObjectsByExpertise();
                    break;
                case "3":
                    updateExpertAvailability(scanner);
                    break;
                case "4":
                    viewExpertServiceRequests();
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    expertView.showSuccessMessage("Invalid option. Please try again.");
            }
        }
    }
    
    // Updated viewAuctionsByExpertise: reload auctions from DB and filter by expert's expertise.
    private void viewAuctionsByExpertise() {
        expertView.showSuccessMessage("=== Auctions by Your Expertise ===");
        List<Auction> auctions = auctionDAO.getAllAuctions(auctionHouseDAO);
        if (auctions == null || auctions.isEmpty()) {
            expertView.showSuccessMessage("No auctions available.");
            return;
        }
        boolean found = false;
        for (Auction auction : auctions) {
            // Assuming that auction.getSpecialty() returns a string that should match one of the expert's expertise areas.
            if (expert.getExpertiseAreas().contains(auction.getSpecialty())) {
                expertView.showSuccessMessage(auction.toString());
                found = true;
            }
        }
        if (!found) {
            expertView.showSuccessMessage("No auctions match your expertise.");
        }
    }
    
    // Updated viewArtObjectsByExpertise: reload art objects from DB and filter them.
    private void viewArtObjectsByExpertise() {
        expertView.showSuccessMessage("=== Art Objects by Your Expertise ===");
        List<ArtObject> artObjects = artObjectDAO.getAllArtObjects();
        if (artObjects == null || artObjects.isEmpty()) {
            expertView.showSuccessMessage("No art objects available.");
            return;
        }
        boolean found = false;
        for (ArtObject artObject : artObjects) {
            // Assuming that artObject.getType() is the property to compare with expert expertise.
            if (expert.getExpertiseAreas().contains(artObject.getType())) {
                expertView.showSuccessMessage(artObject.toString());
                found = true;
            }
        }
        if (!found) {
            expertView.showSuccessMessage("No art objects match your expertise.");
        }
    }
    
    private void updateExpertAvailability(Scanner scanner) {
        expertView.showSuccessMessage("Enter new availability start date-time (yyyy-MM-dd HH:mm):");
        String startInput = scanner.nextLine();
        LocalDateTime start = LocalDateTime.parse(startInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        expertView.showSuccessMessage("Enter new availability end date-time (yyyy-MM-dd HH:mm):");
        String endInput = scanner.nextLine();
        LocalDateTime end = LocalDateTime.parse(endInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        expert.addAvailability(new model.Schedule(start, end));
        expertView.showSuccessMessage("Availability updated successfully!");
    }
    
    private void viewExpertServiceRequests() {
        expertView.showSuccessMessage("=== Service Requests Assigned to You ===");
        boolean found = false;
        for (model.ServiceRequest sr : system.getServiceRequests()) {
            if (sr.getExpert() != null && sr.getExpert().getId() == expert.getId()) {
                expertView.showSuccessMessage(sr.toString());
                found = true;
            }
        }
        if (!found) {
            expertView.showSuccessMessage("No service requests assigned to you.");
        }
    }
}
