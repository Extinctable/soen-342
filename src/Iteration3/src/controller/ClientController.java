package controller;

import dao.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import model.*;
import view.ClientView;

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
    
    private Expert findExpertBySpecialty(String specialty) {
        for (Expert expert : system.getExperts()) {
            if (expert.getExpertiseAreas().contains(specialty)) {
                return expert;
            }
        }
        return null; 
    }
    
    private void requestService(Scanner scanner) {
        clientView.showSuccessMessage("Choose the type of service you want:");
        clientView.showSuccessMessage("1. Consultation for Objects of Interest");
        clientView.showSuccessMessage("2. Consultation for Auction");
        clientView.showSuccessMessage("Enter 1 or 2:");   

        int choice = scanner.nextInt();
        scanner.nextLine();
        String serviceType;
        String specialty;
        Expert assignedExpert;
        switch (choice) {
            case 1:
                serviceType = "Consultation for Objects of Interest";
                clientView.showSuccessMessage("Enter object ID:");
                int objectID = scanner.nextInt();
                ArtObject selectedObject = null;
                for (ArtObject obj : system.getArtObjects()) {
                    if (obj.getId() == objectID) {
                        selectedObject = obj;
                        break;
                    }
                }

                if (selectedObject == null) {
                    System.out.println("ArtObject with ID " + objectID + " not found.");
                    return;
                }

                // Display specialty (type)
                clientView.showSuccessMessage("You selected: " + selectedObject.getTitle());
                clientView.showSuccessMessage("Specialty (Type): " + selectedObject.getType());

                //assign to expert
                specialty = selectedObject.getType();
                assignedExpert = findExpertBySpecialty(specialty);

                break;

            case 2:
                serviceType = "Consultation for Auction";
                clientView.showSuccessMessage("Enter auction ID:");
                int auctionID = scanner.nextInt();
                Auction selectedAuction = null;
                for (Auction auction : system.getAuctions()) {
                    if (auction.getId() == auctionID) {
                        selectedAuction = auction;
                        break;
                    }
                }
                if (selectedAuction == null) {
                    System.out.println("Auction with ID " + auctionID + " not found.");
                    return;
                }
                clientView.showSuccessMessage("You selected: " + selectedAuction.getTitle());
                clientView.showSuccessMessage("Specialty: " + selectedAuction.getSpecialty());

                //assign to expert
                specialty = selectedAuction.getSpecialty(); // e.g., "Modern Art"
                assignedExpert = findExpertBySpecialty(specialty);

                break;

            default:
                System.out.println("Invalid choice. Please enter 1 or 2.");
                return;
        }

        if (assignedExpert == null) {
            System.out.println("No expert found with specialty: " + specialty);
            return;
        }
        clientView.showSuccessMessage("Enter any additional notes for the service request:");
        String notes = scanner.nextLine();
        
        // Create a service request with the current time
        LocalDateTime now = LocalDateTime.now();
      
        ServiceRequest sr = new ServiceRequest(client, assignedExpert, serviceType, now, notes);
        system.addServiceRequest(sr);
        serviceRequestDAO.createServiceRequest(sr);
        
        clientView.showSuccessMessage("Service request submitted successfully!");
    }    
}
