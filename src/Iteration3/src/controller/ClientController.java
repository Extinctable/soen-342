package controller;

import model.*;
import view.ClientView;
import dao.*;
import java.util.Scanner;
import java.time.LocalDateTime;

public class ClientController {
    private ClientView clientView;
    private Client client;
    private ArtAdvisorySystem system;
    private AuctionDAO auctionDAO;
    private ArtObjectDAO artObjectDAO;
    private ServiceRequestDAO serviceRequestDAO;
    
    public ClientController(ArtAdvisorySystem system, ClientView clientView, Client client) {
        this.system = system;
        this.clientView = clientView;
        this.client = client;
        auctionDAO = new AuctionDAO();
        artObjectDAO = new ArtObjectDAO();
        serviceRequestDAO = new ServiceRequestDAO();
    }
    
    public void handleMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            clientView.displayMenu();
            String choice = scanner.nextLine();
            switch(choice) {
                case "1":
                    viewAuctions();
                    break;
                case "2":
                    viewArtObjects();
                    break;
                case "3":
                    requestService(scanner);
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    clientView.showSuccessMessage("Invalid option. Please try again.");
            }
        }
    }
    
    private void viewAuctions() {
        clientView.showSuccessMessage("Available Auctions:");
        for (Auction auction : system.getAuctions()) {
            clientView.showSuccessMessage(auction.toString());
        }
    }
    
    private void viewArtObjects() {
        clientView.showSuccessMessage("Available Art Objects:");
        for (ArtObject obj : system.getArtObjects()) {
            clientView.showSuccessMessage(obj.toString());
        }
    }
    
    private void requestService(Scanner scanner) {
        clientView.showSuccessMessage("Enter service type (e.g., Consultation for Objects of Interest or Consultation for Auction):");
        String serviceType = scanner.nextLine();
        clientView.showSuccessMessage("Enter any additional notes for the service request:");
        String notes = scanner.nextLine();
        
        // Create a service request with the current time
        LocalDateTime now = LocalDateTime.now();
        // In a full implementation, the system should assign an expert; for demonstration we assign the first available expert.
        Expert assignedExpert = system.getExperts().isEmpty() ? null : system.getExperts().get(0);
        
        ServiceRequest sr = new ServiceRequest(client, assignedExpert, serviceType, now, notes);
        system.addServiceRequest(sr);
        serviceRequestDAO.createServiceRequest(sr);
        
        clientView.showSuccessMessage("Service request submitted successfully!");
    }
}
