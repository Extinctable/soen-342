/**
 *
 * @author massimocaruso
 * @author jananaamahathevan
 * 
 */

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
        ArtAdvisorySystem.createMockData(system);

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
        AdminView adminView = new AdminView();
        AdminController adminController = new AdminController(system, adminView, admin);
        adminController.handleMenu(scanner, system, admin);
    }

    private static void handleExpertMenu(Scanner scanner, ArtAdvisorySystem system, Expert expert) {
        ExpertView expertView = new ExpertView();
        ExpertController expertController = new ExpertController(system, expertView, expert);
        expertController.handleMenu(scanner, expert);
    }

    private static void handleClientMenu(Scanner scanner, ArtAdvisorySystem system, Client client) {
        ClientView clientView = new ClientView();
        ClientController clientController = new ClientController(system, clientView, client);
        clientController.handleMenu(scanner, client);
    }

}
