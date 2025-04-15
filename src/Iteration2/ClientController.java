import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *@author massimocaruso
 * @author jananaamahathevan
 */


//Model Class
public class ClientController {
    private ClientView clientView;
    private Client client; 
    private ArtAdvisorySystem system;

    public ClientController(ArtAdvisorySystem system, ClientView clientView, Client client) {
        this.system = system;
        this.client = client;
        this.clientView = clientView;
    }

    public void handleMenu(Scanner scanner, Client client) {
        boolean running = true;
        while (running) {
            clientView.displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewAuctions(system);
                    break;
                case 2:
                    viewArtObjects(system);
                    break;
                case 3:
                    requestService(scanner, client);
                    break;
                case 4:
                    viewClientServiceRequests(client);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
            System.out.println("\n1. Continue");
            System.out.println("2. Logout");
            System.out.print("Select an option: ");
            int continueChoice = scanner.nextInt();
            if (continueChoice == 2) {
                return;
            }
        }
    }
    
    private static void viewAuctions(ArtAdvisorySystem system) {
        List<Auction> auctions = system.getAuctions();
        
        if (auctions.isEmpty()) {
            System.out.println("No auctions available.");
            return;
        }
        
        System.out.println("Auctions:");
        for (Auction auction : auctions) {
            System.out.println(auction.getId() + ". " + auction.getTitle() + " (" + auction.getSpecialty() + ")");
            System.out.println("   Location: " + auction.getAuctionHouse().getName() + ", " + auction.getAuctionHouse().getLocation());
            System.out.println("   Schedule: " + auction.getSchedule().getStartTime() + " to " + auction.getSchedule().getEndTime());
            System.out.println("   Type: " + (auction.isOnline() ? "Online" : "In-person"));
            if (!auction.isOnline() && auction.getViewingSchedule() != null) {
                System.out.println("   Viewing: " + auction.getViewingSchedule().getStartTime() + " to " + auction.getViewingSchedule().getEndTime());
            }
            System.out.println();
        }
    }
    
    private static void viewArtObjects(ArtAdvisorySystem system) {
        List<ArtObject> artObjects = system.getArtObjects();
        
        if (artObjects.isEmpty()) {
            System.out.println("No art objects available.");
            return;
        }
        
        System.out.println("Art Objects:");
        for (ArtObject artObject : artObjects) {
            System.out.println(artObject.getId() + ". " + artObject.getTitle() + " (" + artObject.getType() + ")");
            System.out.println("   Description: " + artObject.getDescription());
            System.out.println("   Owned by Institution: " + (artObject.isOwned() ? "Yes" : "No"));
            System.out.println("   To Be Auctioned: " + (artObject.isToBeAuctioned() ? "Yes" : "No"));
            if (artObject.isToBeAuctioned() && artObject.getAuction() != null) {
                System.out.println("   Auction: " + artObject.getAuction().getTitle());
            }
            System.out.println();
        }
    }

    private void requestService(Scanner scanner, Client client) {
        System.out.println("\nService Types:");
        System.out.println("1. Object Consultation");
        System.out.println("2. Auction Viewing Accompaniment");
        System.out.println("3. Auction Attendance");
        System.out.print("Select service type: ");
        
        int serviceType = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String serviceTypeStr = "";
        switch (serviceType) {
            case 1:
                serviceTypeStr = "Object Consultation";
                break;
            case 2:
                serviceTypeStr = "Auction Viewing Accompaniment";
                break;
            case 3:
                serviceTypeStr = "Auction Attendance";
                break;
            default:
                System.out.println("Invalid service type.");
                return;
        }
        
        // Choose an expert (by expertise area)
        System.out.println("\nExpertise Areas:");
        Set<String> allExpertiseAreas = system.getAllExpertiseAreas();
        List<String> areasList = new ArrayList<>(allExpertiseAreas);
        
        for (int i = 0; i < areasList.size(); i++) {
            System.out.println((i + 1) + ". " + areasList.get(i));
        }
        
        System.out.print("Select expertise area: ");
        int areaIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (areaIndex <= 0 || areaIndex > areasList.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        String selectedArea = areasList.get(areaIndex - 1);
        List<Expert> matchingExperts = system.getExpertsByExpertiseArea(selectedArea);
        
        if (matchingExperts.isEmpty()) {
            System.out.println("No experts available for this expertise area.");
            return;
        }
        
        System.out.println("\nAvailable Experts:");
        for (int i = 0; i < matchingExperts.size(); i++) {
            Expert expert = matchingExperts.get(i);
            System.out.println((i + 1) + ". " + expert.getName() + " (License: " + expert.getLicenseNumber() + ")");
        }
        
        System.out.print("Select an expert: ");
        int expertIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (expertIndex <= 0 || expertIndex > matchingExperts.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        Expert selectedExpert = matchingExperts.get(expertIndex - 1);
        
        // Choose date and time
        System.out.print("Request Date and Time (yyyy-MM-dd HH:mm): ");
        String requestTimeStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime requestTime = LocalDateTime.parse(requestTimeStr, formatter);
        
        // Additional details based on service type
        ArtObject artObject = null;
        Auction auction = null;
        
        if (serviceType == 1) {
            // Object consultation
            viewArtObjectsByType(selectedArea);
            System.out.print("Select art object ID: ");
            int artObjectId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            artObject = system.getArtObjectById(artObjectId);
            if (artObject == null) {
                System.out.println("Invalid art object ID.");
                return;
            }
        } else {
            // Auction related services
            viewAuctionsBySpecialty(selectedArea);
            System.out.print("Select auction ID: ");
            int auctionId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            auction = system.getAuctionById(auctionId);
            if (auction == null) {
                System.out.println("Invalid auction ID.");
                return;
            }
        }
        
        System.out.print("Additional notes: ");
        String notes = scanner.nextLine();
        
        ServiceRequest serviceRequest = new ServiceRequest(
            client, selectedExpert, serviceTypeStr, requestTime, notes
        );
        
        if (artObject != null) {
            serviceRequest.setArtObject(artObject);
        }
        
        if (auction != null) {
            serviceRequest.setAuction(auction);
        }
        
        system.addServiceRequest(serviceRequest);
        System.out.println("Service request submitted successfully.");
    }

    private void viewArtObjectsByType(String type) {
        List<ArtObject> artObjects = system.getArtObjectsByType(type);
        
        if (artObjects.isEmpty()) {
            System.out.println("No art objects of type " + type + " available.");
            return;
        }
        
        System.out.println("Art Objects of type " + type + ":");
        for (ArtObject artObject : artObjects) {
            System.out.println(artObject.getId() + ". " + artObject.getTitle());
            System.out.println("   Description: " + artObject.getDescription());
            System.out.println();
        }
    }

    private void viewAuctionsBySpecialty(String specialty) {
        List<Auction> auctions = system.getAuctionsBySpecialty(specialty);
        
        if (auctions.isEmpty()) {
            System.out.println("No auctions for specialty " + specialty + " available.");
            return;
        }
        
        System.out.println("Auctions for specialty " + specialty + ":");
        for (Auction auction : auctions) {
            System.out.println(auction.getId() + ". " + auction.getTitle());
            System.out.println("   Location: " + auction.getAuctionHouse().getName() + ", " + auction.getAuctionHouse().getLocation());
            System.out.println("   Schedule: " + auction.getSchedule().getStartTime() + " to " + auction.getSchedule().getEndTime());
            System.out.println();
        }
    }

    private void viewClientServiceRequests(Client client) {
        List<ServiceRequest> requests = system.getServiceRequestsByClient(client);
        
        if (requests.isEmpty()) {
            System.out.println("You have no service requests.");
            return;
        }
        
        System.out.println("Your Service Requests:");
        for (ServiceRequest request : requests) {
            System.out.println(request.getId() + ". Type: " + request.getServiceType());
            System.out.println("Expert: " + request.getExpert().getName());
            System.out.println("   Requested Time: " + request.getRequestedTime());
            System.out.println("   Status: " + request.getStatus());
            System.out.println("   Notes: " + request.getNotes());
            
            if (request.getArtObject() != null) {
                System.out.println("   Art Object: " + request.getArtObject().getTitle());
            }
            
            if (request.getAuction() != null) {
                System.out.println("   Auction: " + request.getAuction().getTitle());
            }
            
            System.out.println();
        }
    }
}


