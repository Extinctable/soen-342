// File: controller/ClientController.java
package controller;

import dao.ArtObjectDAO;
import dao.AuctionDAO;
import dao.AuctionHouseDAO;
import dao.ExpertDAO;
import dao.ServiceRequestDAO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import model.ArtAdvisorySystem;
import model.ArtObject;
import model.Auction;
import model.Client;
import model.Expert;
import model.ServiceRequest;
import view.ClientView;

public class ClientController {
    private ClientView clientView;
    private Client client;
    private ArtAdvisorySystem system;
    private AuctionDAO auctionDAO;
    private AuctionHouseDAO auctionHouseDAO;
    private ArtObjectDAO artObjectDAO;
    private ServiceRequestDAO serviceRequestDAO;
    
    public ClientController(ArtAdvisorySystem system, ClientView clientView, Client client) {
        this.system = system;
        this.clientView = clientView;
        this.client = client;
        // Instantiate DAO objects to query persistent data.
        this.auctionDAO = new AuctionDAO();
        this.auctionHouseDAO = new AuctionHouseDAO();
        this.artObjectDAO = new ArtObjectDAO();
        this.serviceRequestDAO = new ServiceRequestDAO();
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
    
    // Queries the database for auctions.
    private void viewAuctions() {
        clientView.showSuccessMessage("=== Available Auctions ===");
        List<Auction> auctions = auctionDAO.getAllAuctions(auctionHouseDAO);
        if (auctions == null || auctions.isEmpty()) {
            clientView.showSuccessMessage("No auctions available.");
        } else {
            for (Auction auction : auctions) {
                clientView.showSuccessMessage(auction.toString());
            }
        }
    }
    
    // Queries the database for art objects.
    private void viewArtObjects() {
        clientView.showSuccessMessage("=== Available Art Objects ===");
        List<ArtObject> artObjects = artObjectDAO.getAllArtObjects();
        if (artObjects == null || artObjects.isEmpty()) {
            clientView.showSuccessMessage("No art objects available.");
        } else {
            for (ArtObject obj : artObjects) {
                clientView.showSuccessMessage(obj.toString());
            }
        }
    }
    
    // Retrieves a fresh list of experts from the database and looks for one whose expertise matches the given specialty.
    private Expert findExpertBySpecialty(String specialty) {
        ExpertDAO expertDAO = new ExpertDAO();
        List<Expert> experts = expertDAO.getAllExperts();
        if (experts != null) {
            for (Expert expert : experts) {
                String expertiseStr = expert.getExpertiseAreas();
                if (expertiseStr != null && !expertiseStr.isEmpty()) {
                    // Split the expertise string by commas.
                    String[] areas = expertiseStr.split(",");
                    for (String area : areas) {
                        if (area.trim().equalsIgnoreCase(specialty)) {
                            return expert;
                        }
                    }
                }
            }
        }
        return null;
    }
    
    // Allows the client to request a service by selecting a service type and item.
    private void requestService(Scanner scanner) {
        clientView.showSuccessMessage("Choose the type of service you want:");
        clientView.showSuccessMessage("1. Consultation for Objects of Interest");
        clientView.showSuccessMessage("2. Consultation for Auction");
        clientView.showSuccessMessage("Enter 1 or 2:");   
    
        int choice = scanner.nextInt();
        scanner.nextLine();  // consume newline
        String serviceType;
        String specialty = "";
        Expert assignedExpert = null;
        ArtObject selectedObject = null;
        Auction selectedAuction = null;
    
        switch (choice) {
            case 1:
                serviceType = "Consultation for Objects of Interest";
                clientView.showSuccessMessage("Enter object ID:");
                int objectID = scanner.nextInt();
                scanner.nextLine();  // consume newline
    
                // Use the DAO to retrieve the latest art objects.
                List<ArtObject> artObjects = artObjectDAO.getAllArtObjects();
                for (ArtObject obj : artObjects) {
                    if (obj.getId() == objectID) {
                        selectedObject = obj;
                        break;
                    }
                }
                if (selectedObject == null) {
                    clientView.showSuccessMessage("ArtObject with ID " + objectID + " not found.");
                    return;
                }
                clientView.showSuccessMessage("You selected: " + selectedObject.getTitle());
                clientView.showSuccessMessage("Specialty (Type): " + selectedObject.getType());
                specialty = selectedObject.getType();
                assignedExpert = findExpertBySpecialty(specialty);
                break;
    
            case 2:
                serviceType = "Consultation for Auction";
                clientView.showSuccessMessage("Enter auction ID:");
                int auctionID = scanner.nextInt();
                scanner.nextLine(); // consume newline
    
                // Use the DAO to retrieve the latest auctions.
                List<Auction> auctions = auctionDAO.getAllAuctions(auctionHouseDAO);
                for (Auction auction : auctions) {
                    if (auction.getId() == auctionID) {
                        selectedAuction = auction;
                        break;
                    }
                }
                if (selectedAuction == null) {
                    clientView.showSuccessMessage("Auction with ID " + auctionID + " not found.");
                    return;
                }
                clientView.showSuccessMessage("You selected: " + selectedAuction.getTitle());
                clientView.showSuccessMessage("Specialty: " + selectedAuction.getSpecialty());
                specialty = selectedAuction.getSpecialty();
                assignedExpert = findExpertBySpecialty(specialty);
                break;
    
            default:
                clientView.showSuccessMessage("Invalid choice. Please enter 1 or 2.");
                return;
        }
    
        if (assignedExpert == null) {
            clientView.showSuccessMessage("No expert found with specialty: " + specialty);
            return;
        }
        
        clientView.showSuccessMessage("Enter any additional notes for the service request:");
        String notes = scanner.nextLine();
        
        LocalDateTime now = LocalDateTime.now();
        
        ServiceRequest sr = new ServiceRequest(client, assignedExpert, serviceType, now, notes);
        system.addServiceRequest(sr);
        serviceRequestDAO.createServiceRequest(sr);
        
        clientView.showSuccessMessage("Service request submitted successfully!");
    }    
}
