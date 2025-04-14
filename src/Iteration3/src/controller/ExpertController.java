package controller;

import model.*;
import view.ExpertView;
import dao.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExpertController {
    private Expert expert;
    private ExpertView expertView;
    private ArtAdvisorySystem system;
    private ServiceRequestDAO serviceRequestDAO;
    
    public ExpertController(ArtAdvisorySystem system, ExpertView expertView, Expert expert) {
        this.system = system;
        this.expertView = expertView;
        this.expert = expert;
        serviceRequestDAO = new ServiceRequestDAO();
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
    
    private void viewAuctionsByExpertise() {
        expertView.showSuccessMessage("Auctions matching your expertise:");
        // Here we simply filter auctions by matching the expert’s expertise areas with the auction's specialty.
        for (Auction auction : system.getAuctions()) {
            if (expert.getExpertiseAreas().contains(auction.getSpecialty())) {
                expertView.showSuccessMessage(auction.toString());
            }
        }
    }
    
    private void viewArtObjectsByExpertise() {
        expertView.showSuccessMessage("Art Objects matching your expertise:");
        // Filter art objects by checking if the object type is among the expert’s expertise.
        for (ArtObject obj : system.getArtObjects()) {
            if (expert.getExpertiseAreas().contains(obj.getType())) {
                expertView.showSuccessMessage(obj.toString());
            }
        }
    }
    
    private void updateExpertAvailability(Scanner scanner) {
        expertView.showSuccessMessage("Enter new availability start date-time (yyyy-MM-dd HH:mm):");
        String startInput = scanner.nextLine();
        LocalDateTime start = LocalDateTime.parse(startInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        expertView.showSuccessMessage("Enter new availability end date-time (yyyy-MM-dd HH:mm):");
        String endInput = scanner.nextLine();
        LocalDateTime end = LocalDateTime.parse(endInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Schedule newSchedule = new Schedule(start, end);
        expert.addAvailability(newSchedule);
        expertView.showSuccessMessage("Availability updated successfully!");
    }
    
    private void viewExpertServiceRequests() {
        expertView.showSuccessMessage("Service Requests assigned to you:");
        for (ServiceRequest sr : system.getServiceRequests()) {
            if (sr.getExpert() != null && sr.getExpert().getId() == expert.getId()) {
                expertView.showSuccessMessage(sr.toString());
            }
        }
    }
}
