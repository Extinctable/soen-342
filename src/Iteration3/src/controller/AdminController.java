package controller;

import model.*;
import view.AdminView;
import dao.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminController {
    private Administrator administrator;
    private AdminView adminView;
    private ArtAdvisorySystem system;
    
    // DAO objects for persistence
    private AuctionHouseDAO auctionHouseDAO;
    private AuctionDAO auctionDAO;
    private ArtObjectDAO artObjectDAO;
    private ExpertDAO expertDAO;
    private ClientDAO clientDAO;
    private ServiceRequestDAO serviceRequestDAO;
    
    public AdminController(ArtAdvisorySystem system, AdminView adminView, Administrator administrator) {
        this.system = system;
        this.adminView = adminView;
        this.administrator = administrator;
        // Initialize all DAO dependencies
        auctionHouseDAO = new AuctionHouseDAO();
        auctionDAO = new AuctionDAO();
        artObjectDAO = new ArtObjectDAO();
        expertDAO = new ExpertDAO();
        clientDAO = new ClientDAO();
        serviceRequestDAO = new ServiceRequestDAO();
    }
    
    public void handleMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            adminView.displayMenu();
            String choice = scanner.nextLine();
            switch(choice) {
                case "1":
                    addAuctionHouse(scanner);
                    break;
                case "2":
                    addAuction(scanner);
                    break;
                case "3":
                    addArtObject(scanner);
                    break;
                case "4":
                    addExpert(scanner);
                    break;
                case "5":
                    approveClients(scanner);
                    break;
                case "6":
                    viewAuctions();
                    break;
                case "7":
                    viewArtObjects();
                    break;
                case "8":
                    viewExperts();
                    break;
                case "9":
                    viewClients();
                    break;
                case "10":
                    viewServiceRequests();
                    break;
                case "11":
                    viewAuctionHouses();
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    adminView.showSuccessMessage("Invalid option. Please try again.");
            }
        }
    }
    
    public void addAuctionHouse(Scanner scanner) {
        adminView.showSuccessMessage("Enter Auction House Name:");
        String name = scanner.nextLine();
        adminView.showSuccessMessage("Enter Auction House Location:");
        String location = scanner.nextLine();
        adminView.showSuccessMessage("Enter Auction House Contact Info:");
        String contact = scanner.nextLine();
        
        AuctionHouse auctionHouse = new AuctionHouse(name, location, contact);
        system.addAuctionHouse(auctionHouse);
        auctionHouseDAO.createAuctionHouse(auctionHouse);
        
        adminView.showSuccessMessage("Auction House added successfully!");
    }
    
    public void addAuction(Scanner scanner) {
        adminView.showSuccessMessage("Enter Auction Name:");
        String auctionName = scanner.nextLine();
        adminView.showSuccessMessage("Enter Auction Specialty:");
        String specialty = scanner.nextLine();
        adminView.showSuccessMessage("Enter Auction House ID:");
        int auctionHouseId = Integer.parseInt(scanner.nextLine());
        AuctionHouse auctionHouse = system.getAuctionHouseById(auctionHouseId);
        if (auctionHouse == null) {
            adminView.showSuccessMessage("Auction House not found!");
            return;
        }
        
        adminView.showSuccessMessage("Enter Auction Schedule Start Date-Time (yyyy-MM-dd HH:mm):");
        String scheduleInput = scanner.nextLine();
        LocalDateTime scheduleStart = LocalDateTime.parse(scheduleInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        // For simplicity, we set the end time equal to the start time.
        Schedule schedule = new Schedule(scheduleStart, scheduleStart);
        
        adminView.showSuccessMessage("Is the auction online? (yes/no):");
        String onlineStr = scanner.nextLine();
        boolean isOnline = onlineStr.equalsIgnoreCase("yes");
        
        adminView.showSuccessMessage("Enter Viewing Schedule Date-Time (yyyy-MM-dd HH:mm):");
        String viewingInput = scanner.nextLine();
        LocalDateTime viewingStart = LocalDateTime.parse(viewingInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Schedule viewingSchedule = new Schedule(viewingStart, viewingStart);
        
        Auction auction = new Auction(auctionName, specialty, auctionHouse, schedule, isOnline, viewingSchedule);
        system.addAuction(auction);
        auctionDAO.createAuction(auction);
        
        adminView.showSuccessMessage("Auction added successfully!");
    }
    
    public void addArtObject(Scanner scanner) {
        adminView.showSuccessMessage("Enter Art Object Title:");
        String title = scanner.nextLine();
        adminView.showSuccessMessage("Enter Art Object Description:");
        String description = scanner.nextLine();
        adminView.showSuccessMessage("Enter Art Object Type:");
        String type = scanner.nextLine();
        adminView.showSuccessMessage("Is the Art Object owned by an institution? (yes/no):");
        String ownedInput = scanner.nextLine();
        boolean isOwned = ownedInput.equalsIgnoreCase("yes");
        
        // Optionally, link an auction if applicable
        adminView.showSuccessMessage("Enter Auction ID if applicable (or 0 if not):");
        int auctionId = Integer.parseInt(scanner.nextLine());
        Auction auction = null;
        if (auctionId != 0) {
            auction = system.getAuctionById(auctionId);
            if (auction == null) {
                adminView.showSuccessMessage("Auction not found, proceeding without auction association.");
            }
        }
        
        ArtObject artObject = new ArtObject(title, description, type, isOwned, false, auction);
        system.addArtObject(artObject);
        artObjectDAO.createArtObject(artObject);
        
        adminView.showSuccessMessage("Art Object added successfully!");
    }
    
    public void addExpert(Scanner scanner) {
        adminView.showSuccessMessage("Enter Expert Username:");
        String username = scanner.nextLine();
        adminView.showSuccessMessage("Enter Expert Password:");
        String password = scanner.nextLine();
        adminView.showSuccessMessage("Enter Expert Name:");
        String name = scanner.nextLine();
        adminView.showSuccessMessage("Enter Expert Contact Address:");
        String contact = scanner.nextLine();
        adminView.showSuccessMessage("Enter Expert License Number:");
        String license = scanner.nextLine();
        
        Expert expert = new Expert(username, password, name, contact, license);
        
        // Add expertise areas
        adminView.showSuccessMessage("Enter areas of expertise (comma-separated):");
        String areasInput = scanner.nextLine();
        String[] areas = areasInput.split(",");
        for (String area : areas) {
            expert.addExpertiseArea(area.trim());
        }
        
        // Optionally add an availability schedule
        adminView.showSuccessMessage("Do you want to add availability schedule? (yes/no)");
        String availResponse = scanner.nextLine();
        if (availResponse.equalsIgnoreCase("yes")) {
            adminView.showSuccessMessage("Enter availability start date-time (yyyy-MM-dd HH:mm):");
            String availStartStr = scanner.nextLine();
            LocalDateTime availStart = LocalDateTime.parse(availStartStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            adminView.showSuccessMessage("Enter availability end date-time (yyyy-MM-dd HH:mm):");
            String availEndStr = scanner.nextLine();
            LocalDateTime availEnd = LocalDateTime.parse(availEndStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            expert.addAvailability(new Schedule(availStart, availEnd));
        }
        
        system.addExpert(expert);
        expertDAO.createExpert(expert);
        adminView.showSuccessMessage("Expert added successfully!");
    }
    
    public void approveClients(Scanner scanner) {
        // Display pending clients (assuming they were added to pending list at signup)
        adminView.showSuccessMessage("Pending Clients:");
        for (Client pending : system.getPendingClients()) {
            adminView.showSuccessMessage("ID: " + pending.getId() + ", Email: " + pending.getUsername());
        }
        adminView.showSuccessMessage("Enter the Client ID to approve (or 0 to cancel):");
        int clientId = Integer.parseInt(scanner.nextLine());
        if (clientId != 0) {
            Client client = system.getClientById(clientId);
            if (client != null) {
                client.setApproved(true);
                system.getPendingClients().remove(client);
                clientDAO.updateClient(client, clientId);
                adminView.showSuccessMessage("Client approved successfully!");
            } else {
                adminView.showSuccessMessage("Client not found.");
            }
        }
    }
    
    public void viewAuctions() {
        adminView.showSuccessMessage("Listing all Auctions:");
        for (Auction auction : system.getAuctions()) {
            adminView.showSuccessMessage(auction.toString());
        }
    }
    
    public void viewArtObjects() {
        adminView.showSuccessMessage("Listing all Art Objects:");
        for (ArtObject obj : system.getArtObjects()) {
            adminView.showSuccessMessage(obj.toString());
        }
    }
    
    public void viewExperts() {
        adminView.showSuccessMessage("Listing all Experts:");
        for (Expert expert : system.getExperts()) {
            adminView.showSuccessMessage(expert.toString());
        }
    }
    
    public void viewClients() {
        adminView.showSuccessMessage("Listing all Clients:");
        for (Client client : system.getClients()) {
            adminView.showSuccessMessage(client.toString());
        }
    }
    
    public void viewServiceRequests() {
        adminView.showSuccessMessage("Listing all Service Requests:");
        for (ServiceRequest sr : system.getServiceRequests()) {
            adminView.showSuccessMessage(sr.toString());
        }
    }
    
    public void viewAuctionHouses() {
        adminView.showSuccessMessage("Listing all Auction Houses:");
        for (AuctionHouse ah : system.getAuctionHouses()) {
            adminView.showSuccessMessage(ah.toString());
        }
    }
}
