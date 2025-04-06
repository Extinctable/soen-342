
/**
 *
 * @author massimocaruso
 */
// Main.java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArtAdvisorySystem system = new ArtAdvisorySystem();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Create admin account
        Administrator admin = new Administrator("admin", "admin123", "System Administrator", "admin@artadvisory.com", "123-456-7890");
        system.setAdministrator(admin);

        // Mock data for testing
        createMockData(system);

        User currentUser = null;

        while (running) {
            if (currentUser == null) {
                System.out.println("\n===== ART ADVISORY SYSTEM =====");
                System.out.println("1. Login");
                System.out.println("2. Client Sign Up");
                System.out.println("3. Exit");
                System.out.print("Select an option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        System.out.print("Username: ");
                        String username = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();
                        
                        currentUser = system.login(username, password);
                        if (currentUser == null) {
                            System.out.println("Invalid credentials or account not approved yet.");
                        } else {
                            System.out.println("Login successful. Welcome, " + currentUser.getName() + "!");
                        }
                        break;
                    case 2:
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Password: ");
                        String clientPassword = scanner.nextLine();
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Affiliation: ");
                        String affiliation = scanner.nextLine();
                        System.out.print("Contact Info: ");
                        String contactInfo = scanner.nextLine();
                        System.out.print("Intent: ");
                        String intent = scanner.nextLine();
                        
                        Client newClient = new Client(email, clientPassword, name, contactInfo, affiliation, intent);
                        system.addPendingClient(newClient);
                        System.out.println("Sign up successful. Wait for admin approval.");
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } else {
                if (currentUser instanceof Administrator) {
                    handleAdminMenu(scanner, system, (Administrator) currentUser);
                } else if (currentUser instanceof Expert) {
                    handleExpertMenu(scanner, system, (Expert) currentUser);
                } else if (currentUser instanceof Client) {
                    handleClientMenu(scanner, system, (Client) currentUser);
                }
                
                System.out.println("\n1. Continue");
                System.out.println("2. Logout");
                System.out.print("Select an option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (choice == 2) {
                    currentUser = null;
                    System.out.println("Logged out successfully.");
                }
            }
        }
        
        System.out.println("Thank you for using the Art Advisory System. Goodbye!");
        scanner.close();
    }

    private static void handleAdminMenu(Scanner scanner, ArtAdvisorySystem system, Administrator admin) {
        System.out.println("\n===== ADMINISTRATOR MENU =====");
        System.out.println("1. Add Auction House");
        System.out.println("2. Add Auction");
        System.out.println("3. Add Art Object");
        System.out.println("4. Add Expert");
        System.out.println("5. Approve Client Accounts");
        System.out.println("6. View Auctions");
        System.out.println("7. View Art Objects");
        System.out.println("8. View Experts");
        System.out.println("9. View Clients");
        System.out.println("10. View Service Requests");
        System.out.print("Select an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                addAuctionHouse(scanner, system);
                break;
            case 2:
                addAuction(scanner, system);
                break;
            case 3:
                addArtObject(scanner, system);
                break;
            case 4:
                addExpert(scanner, system);
                break;
            case 5:
                approveClients(scanner, system);
                break;
            case 6:
                viewAuctions(system);
                break;
            case 7:
                viewArtObjects(system);
                break;
            case 8:
                viewExperts(system);
                break;
            case 9:
                viewClients(system);
                break;
            case 10:
                viewServiceRequests(system);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void handleExpertMenu(Scanner scanner, ArtAdvisorySystem system, Expert expert) {
        System.out.println("\n===== EXPERT MENU =====");
        System.out.println("1. View Auctions in My Expertise Areas");
        System.out.println("2. Update Availability");
        System.out.println("3. View Art Objects in My Expertise Areas");
        System.out.println("4. View My Service Requests");
        System.out.print("Select an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                viewAuctionsByExpertise(system, expert);
                break;
            case 2:
                updateExpertAvailability(scanner, system, expert);
                break;
            case 3:
                viewArtObjectsByExpertise(system, expert);
                break;
            case 4:
                viewExpertServiceRequests(system, expert);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void handleClientMenu(Scanner scanner, ArtAdvisorySystem system, Client client) {
        System.out.println("\n===== CLIENT MENU =====");
        System.out.println("1. View Auctions");
        System.out.println("2. View Art Objects");
        System.out.println("3. Request Service");
        System.out.println("4. View My Service Requests");
        System.out.print("Select an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                viewAuctions(system);
                break;
            case 2:
                viewArtObjects(system);
                break;
            case 3:
                requestService(scanner, system, client);
                break;
            case 4:
                viewClientServiceRequests(system, client);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Helper methods for menu actions
    private static void addAuctionHouse(Scanner scanner, ArtAdvisorySystem system) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Location: ");
        String location = scanner.nextLine();
        System.out.print("Contact Info: ");
        String contactInfo = scanner.nextLine();
        
        AuctionHouse auctionHouse = new AuctionHouse(name, location, contactInfo);
        system.addAuctionHouse(auctionHouse);
        System.out.println("Auction house added successfully.");
    }

    private static void addAuction(Scanner scanner, ArtAdvisorySystem system) {
        viewAuctionHouses(system);
        System.out.print("Select auction house ID: ");
        int auctionHouseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        AuctionHouse auctionHouse = system.getAuctionHouseById(auctionHouseId);
        if (auctionHouse == null) {
            System.out.println("Invalid auction house ID.");
            return;
        }
        
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Specialty: ");
        String specialty = scanner.nextLine();
        System.out.print("Start Date and Time (yyyy-MM-dd HH:mm): ");
        String startTimeStr = scanner.nextLine();
        System.out.print("End Date and Time (yyyy-MM-dd HH:mm): ");
        String endTimeStr = scanner.nextLine();
        System.out.print("Is Online (true/false): ");
        boolean isOnline = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);
        
        // Viewing details
        LocalDateTime viewingStartTime = null;
        LocalDateTime viewingEndTime = null;
        
        if (!isOnline) {
            System.out.print("Viewing Start Date and Time (yyyy-MM-dd HH:mm): ");
            String viewingStartStr = scanner.nextLine();
            System.out.print("Viewing End Date and Time (yyyy-MM-dd HH:mm): ");
            String viewingEndStr = scanner.nextLine();
            
            viewingStartTime = LocalDateTime.parse(viewingStartStr, formatter);
            viewingEndTime = LocalDateTime.parse(viewingEndStr, formatter);
        }
        
        Schedule schedule = new Schedule(startTime, endTime);
        Schedule viewingSchedule = isOnline ? null : new Schedule(viewingStartTime, viewingEndTime);
        
        Auction auction = new Auction(title, specialty, auctionHouse, schedule, isOnline, viewingSchedule);
        system.addAuction(auction);
        System.out.println("Auction added successfully.");
    }

    private static void addArtObject(Scanner scanner, ArtAdvisorySystem system) {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Type (e.g., Painting, Manuscript): ");
        String type = scanner.nextLine();
        System.out.print("Is Owned By Institution (true/false): ");
        boolean isOwned = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Is To Be Auctioned (true/false): ");
        boolean isToBeAuctioned = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline
        
        Auction auction = null;
        if (isToBeAuctioned) {
            viewAuctions(system);
            System.out.print("Select auction ID (0 for none): ");
            int auctionId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (auctionId > 0) {
                auction = system.getAuctionById(auctionId);
                if (auction == null) {
                    System.out.println("Invalid auction ID. Setting to none.");
                }
            }
        }
        
        ArtObject artObject = new ArtObject(title, description, type, isOwned, isToBeAuctioned, auction);
        system.addArtObject(artObject);
        System.out.println("Art object added successfully.");
    }

    private static void addExpert(Scanner scanner, ArtAdvisorySystem system) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Contact Info: ");
        String contactInfo = scanner.nextLine();
        System.out.print("License Number: ");
        String licenseNumber = scanner.nextLine();
        
        Expert expert = new Expert(username, password, name, contactInfo, licenseNumber);
        
        System.out.println("Enter areas of expertise (comma separated): ");
        String expertiseAreas = scanner.nextLine();
        String[] areas = expertiseAreas.split(",");
        for (String area : areas) {
            expert.addExpertiseArea(area.trim());
        }
        
        system.addExpert(expert);
        System.out.println("Expert added successfully.");
    }

    private static void approveClients(Scanner scanner, ArtAdvisorySystem system) {
        List<Client> pendingClients = system.getPendingClients();
        
        if (pendingClients.isEmpty()) {
            System.out.println("No pending client accounts.");
            return;
        }
        
        System.out.println("Pending Client Accounts:");
        for (int i = 0; i < pendingClients.size(); i++) {
            Client client = pendingClients.get(i);
            System.out.println((i + 1) + ". " + client.getName() + " (" + client.getUsername() + ") - " + client.getAffiliation());
            System.out.println("   Intent: " + client.getIntent());
        }
        
        System.out.print("Enter client number to approve (0 to cancel): ");
        int clientNum = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (clientNum > 0 && clientNum <= pendingClients.size()) {
            Client client = pendingClients.get(clientNum - 1);
            system.approveClient(client);
            System.out.println("Client " + client.getName() + " approved successfully.");
        } else if (clientNum != 0) {
            System.out.println("Invalid client number.");
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

    private static void viewAuctionHouses(ArtAdvisorySystem system) {
        List<AuctionHouse> auctionHouses = system.getAuctionHouses();
        
        if (auctionHouses.isEmpty()) {
            System.out.println("No auction houses available.");
            return;
        }
        
        System.out.println("Auction Houses:");
        for (AuctionHouse auctionHouse : auctionHouses) {
            System.out.println(auctionHouse.getId() + ". " + auctionHouse.getName() + " - " + auctionHouse.getLocation());
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

    private static void viewExperts(ArtAdvisorySystem system) {
        List<Expert> experts = system.getExperts();
        
        if (experts.isEmpty()) {
            System.out.println("No experts available.");
            return;
        }
        
        System.out.println("Experts:");
        for (Expert expert : experts) {
            System.out.println(expert.getId() + ". " + expert.getName() + " (License: " + expert.getLicenseNumber() + ")");
            System.out.println("   Contact: " + expert.getContactInfo());
            System.out.println("   Areas of Expertise: " + String.join(", ", expert.getExpertiseAreas()));
            System.out.println();
        }
    }

    private static void viewClients(ArtAdvisorySystem system) {
        List<Client> clients = system.getClients();
        
        if (clients.isEmpty()) {
            System.out.println("No approved clients available.");
            return;
        }
        
        System.out.println("Clients:");
        for (Client client : clients) {
            System.out.println(client.getId() + ". " + client.getName() + " (" + client.getUsername() + ")");
            System.out.println("   Affiliation: " + client.getAffiliation());
            System.out.println("   Contact: " + client.getContactInfo());
            System.out.println();
        }
    }

    private static void viewAuctionsByExpertise(ArtAdvisorySystem system, Expert expert) {
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

    private static void viewArtObjectsByExpertise(ArtAdvisorySystem system, Expert expert) {
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

    private static void updateExpertAvailability(Scanner scanner, ArtAdvisorySystem system, Expert expert) {
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

    private static void requestService(Scanner scanner, ArtAdvisorySystem system, Client client) {
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
            viewArtObjectsByType(system, selectedArea);
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
            viewAuctionsBySpecialty(system, selectedArea);
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

    private static void viewArtObjectsByType(ArtAdvisorySystem system, String type) {
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

    private static void viewAuctionsBySpecialty(ArtAdvisorySystem system, String specialty) {
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

    private static void viewServiceRequests(ArtAdvisorySystem system) {
        List<ServiceRequest> requests = system.getServiceRequests();
        
        if (requests.isEmpty()) {
            System.out.println("No service requests available.");
            return;
        }
        
        System.out.println("Service Requests:");
        for (ServiceRequest request : requests) {
            System.out.println(request.getId() + ". Type: " + request.getServiceType());
            System.out.println("   Client: " + request.getClient().getName());
            System.out.println("   Expert: " + request.getExpert().getName());
            System.out.println("   Requested Time: " + request.getRequestedTime());
            System.out.println("   Status: " + request.getStatus());
            System.out.println("   Notes: " + request.getNotes());
            System.out.println();
        }
    }

    private static void viewExpertServiceRequests(ArtAdvisorySystem system, Expert expert) {
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

    private static void viewClientServiceRequests(ArtAdvisorySystem system, Client client) {
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

    private static void createMockData(ArtAdvisorySystem system) {
        // Add auction houses
        AuctionHouse christie = new AuctionHouse("Christie's", "London", "christie@example.com");
        AuctionHouse sotheby = new AuctionHouse("Sotheby's", "New York", "sotheby@example.com");
        system.addAuctionHouse(christie);
        system.addAuctionHouse(sotheby);
        
        // Add auctions
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime may12_12pm = LocalDateTime.parse("2025-05-12 12:00", formatter);
        LocalDateTime may12_6pm = LocalDateTime.parse("2025-05-12 18:00", formatter);
        LocalDateTime may10_10am = LocalDateTime.parse("2025-05-10 10:00", formatter);
        LocalDateTime may11_4pm = LocalDateTime.parse("2025-05-11 16:00", formatter);
        
        Schedule auctionSchedule1 = new Schedule(may12_12pm, may12_6pm);
        Schedule viewingSchedule1 = new Schedule(may10_10am, may11_4pm);
        
        Auction orientalAuction = new Auction("Oriental Auction", "Oriental", christie, auctionSchedule1, false, viewingSchedule1);
        system.addAuction(orientalAuction);
        
        LocalDateTime jun15_10am = LocalDateTime.parse("2025-06-15 10:00", formatter);
        LocalDateTime jun16_5pm = LocalDateTime.parse("2025-06-16 17:00", formatter);
        LocalDateTime jun13_11am = LocalDateTime.parse("2025-06-13 11:00", formatter);
        LocalDateTime jun14_3pm = LocalDateTime.parse("2025-06-14 15:00", formatter);
        
        Schedule auctionSchedule2 = new Schedule(jun15_10am, jun16_5pm);
        Schedule viewingSchedule2 = new Schedule(jun13_11am, jun14_3pm);
        
        Auction modernArtAuction = new Auction("Modern Art Auction", "Modern Art", sotheby, auctionSchedule2, false, viewingSchedule2);
        system.addAuction(modernArtAuction);
        
        // Add experts
        Expert expert1 = new Expert("jsmith", "pass123", "John Smith", "john@example.com", "EXP001");
        expert1.addExpertiseArea("Oriental");
        expert1.addExpertiseArea("Ceramics");
        
        Expert expert2 = new Expert("mlee", "pass456", "Maria Lee", "maria@example.com", "EXP002");
        expert2.addExpertiseArea("Modern Art");
        expert2.addExpertiseArea("Paintings");
        
        system.addExpert(expert1);
        system.addExpert(expert2);
        
        // Add client
        Client client = new Client("client@example.com", "clientpass", "Art Museum", "123-456-7890", "Museum", "Looking for Oriental art pieces");
        system.addClient(client);
        
        // Add art objects
        ArtObject object1 = new ArtObject("Ming Vase", "A beautiful Ming Dynasty vase from the 15th century", "Ceramics", false, true, orientalAuction);
        ArtObject object2 = new ArtObject("Abstract Painting", "Modern abstract painting by renowned artist", "Paintings", true, false, null);
        
        system.addArtObject(object1);
        system.addArtObject(object2);
        
        // Add availability for experts
        LocalDateTime avail1Start = LocalDateTime.parse("2025-05-11 09:00", formatter);
        LocalDateTime avail1End = LocalDateTime.parse("2025-05-11 17:00", formatter);
        expert1.addAvailability(new Schedule(avail1Start, avail1End));
        
        LocalDateTime avail2Start = LocalDateTime.parse("2025-06-14 10:00", formatter);
        LocalDateTime avail2End = LocalDateTime.parse("2025-06-14 16:00", formatter);
        expert2.addAvailability(new Schedule(avail2Start, avail2End));
    }
}