/**
 *
 * @author massimocaruso
 * @author jananaamahathevan
 * 
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//Model Class
public class AdminController {
    private Administrator administrator;
    private AdminView adminView;
    private ArtAdvisorySystem system;

    public AdminController(ArtAdvisorySystem system, AdminView adminView, Administrator administrator) {
        this.system = system;
        this.adminView = adminView;
        this.administrator = administrator;
    }

    public void handleMenu(Scanner scanner, ArtAdvisorySystem system, Administrator admin) {
        boolean running = true;
        while (running) {
            adminView.displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addAuctionHouse(scanner);
                    break;
                case 2:
                    addAuction(scanner);
                    break;
                case 3:
                    addArtObject(scanner);
                    break;
                case 4:
                    addExpert(scanner);
                    break;
                case 5:
                    approveClients(scanner);
                    break;
                case 6:
                    viewAuctions();
                    break;
                case 7:
                    viewArtObjects();
                    break;
                case 8:
                    viewExperts();
                    break;
                case 9:
                    viewClients();
                    break;
                case 10:
                    viewServiceRequests();
                    break;
                default:
                    System.out.println("Invalid option.");
            }

            System.out.println("\n1. Continue");
            System.out.println("2. Logout");
            System.out.print("Select an option: ");
            int continueChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (continueChoice == 2) {
                running = false;
            }
        }
    }

    private void addAuctionHouse(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Location: ");
        String location = scanner.nextLine();
        System.out.print("Contact Info: ");
        String contactInfo = scanner.nextLine();

        AuctionHouse auctionHouse = new AuctionHouse(name, location, contactInfo);
        system.addAuctionHouse(auctionHouse);
        adminView.showSuccessMessage("Auction house added successfully.");
    }

    private void addAuction(Scanner scanner) {
        viewAuctionHouses();
        System.out.print("Select auction house ID: ");
        int auctionHouseId = scanner.nextInt();
        scanner.nextLine();

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
        scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr, formatter);
        LocalDateTime endTime = LocalDateTime.parse(endTimeStr, formatter);

        Schedule schedule = new Schedule(startTime, endTime);
        Auction auction = new Auction(title, specialty, auctionHouse, schedule, isOnline, null);
        system.addAuction(auction);
        adminView.showSuccessMessage("Auction added successfully.");
    }

    private void addArtObject(Scanner scanner) {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.print("Type (e.g., Painting, Manuscript): ");
        String type = scanner.nextLine();
        System.out.print("Is Owned By Institution (true/false): ");
        boolean isOwned = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Is To Be Auctioned (true/false): ");
        boolean isToBeAuctioned = scanner.nextBoolean();
        scanner.nextLine();

        Auction auction = null;
        if (isToBeAuctioned) {
            viewAuctions();
            System.out.print("Select auction ID (0 for none): ");
            int auctionId = scanner.nextInt();
            scanner.nextLine();

            if (auctionId > 0) {
                auction = system.getAuctionById(auctionId);
                if (auction == null) {
                    System.out.println("Invalid auction ID. Setting to none.");
                }
            }
        }

        ArtObject artObject = new ArtObject(title, description, type, isOwned, isToBeAuctioned, auction);
        system.addArtObject(artObject);
        adminView.showSuccessMessage("Art object added successfully.");
    }

    private void addExpert(Scanner scanner) {
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
        system.addExpert(expert);
        adminView.showSuccessMessage("Expert added successfully.");
    }

    private void approveClients(Scanner scanner) {
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
        scanner.nextLine();

        if (clientNum > 0 && clientNum <= pendingClients.size()) {
            Client client = pendingClients.get(clientNum - 1);
            system.approveClient(client);
            adminView.showSuccessMessage("Client " + client.getName() + " approved successfully.");
        } else if (clientNum != 0) {
            System.out.println("Invalid client number.");
        }
    }

    private void viewAuctions() {
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

    private void viewArtObjects() {
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

    private void viewExperts() {
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

    private void viewClients() {
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

    private void viewServiceRequests() {
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

    private void viewAuctionHouses() {
        List<AuctionHouse> auctionHouses = system.getAuctionHouses();
        System.out.println("Auction Houses:");
        for (AuctionHouse ah : auctionHouses) {
            System.out.println(ah.getId() + ". " + ah.getName() + " (" + ah.getLocation() + ")");
        }
    }
}


