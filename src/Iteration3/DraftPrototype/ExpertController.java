/**
 *
 * @author massimocaruso
 * @author jananaamahathevan
 * 
 */

import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;

public class ExpertController {
    private Expert expert;
    private ExpertView expertView;
    private ArtAdvisorySystem system;

    public ExpertController(ArtAdvisorySystem system, ExpertView expertView, Expert expert) {
        this.system = system;
        this.expert = expert;
        this.expertView = expertView;
    }

    public void handleMenu(Scanner scanner, Expert expert) {
        boolean running = true;
        while (running) {
            expertView.displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewAuctionsByExpertise(expert);
                    break;
                case 2:
                    updateExpertAvailability(scanner, expert);
                    break;
                case 3:
                    viewArtObjectsByExpertise(expert);
                    break;
                case 4:
                    viewExpertServiceRequests(expert);
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

    private void viewAuctionsByExpertise(Expert expert) {
        List<Auction> auctions = system.getAuctionsByExpertise(expert.getExpertiseAreas());
        
        if (auctions.isEmpty()) {
            System.out.println("No auctions matching your expertise areas.");
            return;
        }
        
        System.out.println("Auctions matching your expertise areas:");
        for (Auction auction : auctions) {
            System.out.println(auction.getId() + ". " + auction.getTitle() + " (" + auction.getSpecialty() + ")");
            System.out.println("   Location: " + auction.getAuctionHouse().getName() + ", " + auction.getAuctionHouse().getLocation());
            System.out.println("   Schedule: " + auction.getSchedule().getStartTime() + " to " + auction.getSchedule().getEndTime());
            System.out.println();
        }
    }

    private void viewArtObjectsByExpertise(Expert expert) {
        List<ArtObject> artObjects = system.getArtObjectsByExpertise(expert.getExpertiseAreas());
        
        if (artObjects.isEmpty()) {
            System.out.println("No art objects matching your expertise areas.");
            return;
        }
        
        System.out.println("Art Objects matching your expertise areas:");
        for (ArtObject artObject : artObjects) {
            System.out.println(artObject.getId() + ". " + artObject.getTitle() + " (" + artObject.getType() + ")");
            System.out.println("   Description: " + artObject.getDescription());
            System.out.println();
        }
    }

    private void updateExpertAvailability(Scanner scanner, Expert expert) {
        System.out.println("Current Availability:");
        for (Schedule schedule : expert.getAvailability()) {
            System.out.println("From " + schedule.getStartTime() + " to " + schedule.getEndTime());
        }
        
        System.out.println("\n1. Add Availability");
        System.out.println("2. Remove Availability");
        System.out.print("Select an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (choice == 1) {
            System.out.print("Start Date and Time (yyyy-MM-dd HH:mm): ");
            String startTimeStr = scanner.nextLine();
            System.out.print("End Date and Time (yyyy-MM-dd HH:mm): ");
            String endTimeStr = scanner.nextLine();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);
            
            Schedule schedule = new Schedule(startTime, endTime);
            expert.addAvailability(schedule);
            System.out.println("Availability added successfully.");
        } else if (choice == 2) {
            if (expert.getAvailability().isEmpty()) {
                System.out.println("No availability to remove.");
                return;
            }
            
            for (int i = 0; i < expert.getAvailability().size(); i++) {
                Schedule schedule = expert.getAvailability().get(i);
                System.out.println((i + 1) + ". From " + schedule.getStartTime() + " to " + schedule.getEndTime());
            }
            
            System.out.print("Select availability to remove: ");
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (index > 0 && index <= expert.getAvailability().size()) {
                expert.removeAvailability(index - 1);
                System.out.println("Availability removed successfully.");
            } else {
                System.out.println("Invalid selection.");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void viewExpertServiceRequests(Expert expert) {
        List<ServiceRequest> requests = system.getServiceRequestsByExpert(expert);
        
        if (requests.isEmpty()) {
            System.out.println("No service requests assigned to you.");
            return;
        }
        
        System.out.println("Your Service Requests:");
        for (ServiceRequest request : requests) {
            System.out.println(request.getId() + ". Type: " + request.getServiceType());
            System.out.println("   Client: " + request.getClient().getName());
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