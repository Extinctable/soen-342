// File: Main.java
import controller.*;
import dao.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.*;
import view.*;

public class Main {

    public static void main(String[] args) {
        // Initialize the in-memory system model.
        ArtAdvisorySystem system = new ArtAdvisorySystem();
        
        // Create and set a default administrator.
        Administrator defaultAdmin = Administrator.getInstance(
                "admin@system.com", 
                "adminPass", 
                "Default Admin", 
                "admin@system.com", 
                "1234567890"
        );
        system.setAdministrator(defaultAdmin);
        // Persist the default admin.
        AdministratorDAO adminDAO = new AdministratorDAO();
        adminDAO.createAdministrator(defaultAdmin);
        
        Scanner scanner = new Scanner(System.in);
        String mainChoice = "";
        
        // Main menu: Login, Sign Up, or Exit.
        while (true) {
            System.out.println("\n=== Welcome to the Art Advisory System ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            mainChoice = scanner.nextLine();
            
            switch (mainChoice) {
                case "1":
                    loginProcedure(scanner, system);
                    break;
                case "2":
                    signUpProcedure(scanner, system);
                    break;
                case "0":
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }
    
    // Login procedure: displays a login menu until a valid role selection is made,
    // or the user opts to return to the main menu.
    private static void loginProcedure(Scanner scanner, ArtAdvisorySystem system) {
        while (true) {
            System.out.println("\n=== Login Menu ===");
            System.out.println("Select role to login:");
            System.out.println("1. Administrator");
            System.out.println("2. Client");
            System.out.println("3. Expert");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");
            String roleChoice = scanner.nextLine();
            
            switch (roleChoice) {
                case "0":
                    return;
                case "1": {
                    System.out.print("Enter admin username (email): ");
                    String adminUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String adminPassword = scanner.nextLine();
                    Administrator admin = system.getAdministrator();
                    if (admin != null && admin.getUsername().equals(adminUsername) && admin.getPassword().equals(adminPassword)) {
                        System.out.println("Administrator login successful!");
                        AdminView adminView = new AdminView();
                        AdminController adminController = new AdminController(system, adminView, admin);
                        adminController.handleMenu(scanner);
                    } else {
                        System.out.println("Invalid administrator credentials. Please try again.");
                    }
                    break;
                }
                case "2": {
                    System.out.print("Enter client username (email): ");
                    String clientUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String clientPassword = scanner.nextLine();
                    ClientDAO clientDAO = new ClientDAO();
                    Client client = authenticateClient(clientUsername, clientPassword, clientDAO);
                    if (client != null) {
                        System.out.println("Client login successful!");
                        ClientView clientView = new ClientView();
                        ClientController clientController = new ClientController(system, clientView, client);
                        clientController.handleMenu(scanner);
                    } else {
                        System.out.println("Invalid client credentials. Please try again.");
                    }
                    break;
                }
                case "3": {
                    System.out.print("Enter expert username: ");
                    String expertUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String expertPassword = scanner.nextLine();
                    ExpertDAO expertDAO = new ExpertDAO();
                    Expert expert = authenticateExpert(expertUsername, expertPassword, expertDAO);
                    if (expert != null) {
                        System.out.println("Expert login successful!");
                        ExpertView expertView = new ExpertView();
                        ExpertController expertController = new ExpertController(system, expertView, expert);
                        expertController.handleMenu(scanner);
                    } else {
                        System.out.println("Invalid expert credentials. Please try again.");
                    }
                    break;
                }
                default:
                    System.out.println("Invalid role selection. Please try again.");
            }
        }
    }
    
    // Sign-up procedure: allows new clients or experts to register.
    // If an invalid option is entered, it re-displays the sign-up menu.
    private static void signUpProcedure(Scanner scanner, ArtAdvisorySystem system) {
        while (true) {
            System.out.println("\n=== Sign Up Menu ===");
            System.out.println("Select role to sign up:");
            System.out.println("1. Client");
            System.out.println("2. Expert");
            System.out.println("0. Return to Main Menu");
            System.out.print("Enter your choice: ");
            String roleChoice = scanner.nextLine();
            
            switch (roleChoice) {
                case "0":
                    return;
                case "1": {
                    Client newClient = registerClient(scanner);
                    if (newClient != null) {
                        ClientDAO clientDAO = new ClientDAO();
                        clientDAO.createClient(newClient);  // Persist in the DB.
                        system.addClient(newClient);         // Add to in‑memory system.
                        System.out.println("Client registration successful! You may now login.");
                    }
                    break;
                }
                case "2": {
                    Expert newExpert = registerExpert(scanner);
                    if (newExpert != null) {
                        ExpertDAO expertDAO = new ExpertDAO();
                        expertDAO.createExpert(newExpert);  // Persist in the DB.
                        system.addExpert(newExpert);         // Add to in‑memory system.
                        System.out.println("Expert registration successful! You may now login.");
                    }
                    break;
                }
                default:
                    System.out.println("Invalid role selection. Please try again.");
                    continue;
            }
            // After a successful registration, break out of the sign-up loop.
            break;
        }
    }
    
    // Helper method to register a new client.
    private static Client registerClient(Scanner scanner) {
        System.out.println("\n=== Client Registration ===");
        System.out.print("Enter your email (username): ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your contact information: ");
        String contact = scanner.nextLine();
        System.out.print("Enter your affiliation: ");
        String affiliation = scanner.nextLine();
        System.out.print("Enter your intent description: ");
        String intent = scanner.nextLine();
        Client client = new Client(email, password, name, contact, affiliation, intent);
        // For this demo, mark the client as approved immediately.
        client.setApproved(false);
        return client;
    }
    
    // Helper method to register a new expert.
    private static Expert registerExpert(Scanner scanner) {
        System.out.println("\n=== Expert Registration ===");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your contact information: ");
        String contact = scanner.nextLine();
        System.out.print("Enter your contact address: ");
        String address = scanner.nextLine();
        System.out.print("Enter your license number: ");
        String license = scanner.nextLine();
        Expert expert = new Expert(username, password, name, contact, license);
        System.out.print("Enter areas of expertise (comma-separated): ");
        String expertiseInput = scanner.nextLine();
        String[] areas = expertiseInput.split(",");
        for (String area : areas) {
            expert.addExpertiseArea(area.trim());
        }
        System.out.print("Would you like to add an availability schedule? (yes/no): ");
        String availResponse = scanner.nextLine();
        if (availResponse.equalsIgnoreCase("yes")) {
            System.out.print("Enter availability start date-time (yyyy-MM-dd HH:mm): ");
            String startInput = scanner.nextLine();
            System.out.print("Enter availability end date-time (yyyy-MM-dd HH:mm): ");
            String endInput = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime start = LocalDateTime.parse(startInput, formatter);
            LocalDateTime end = LocalDateTime.parse(endInput, formatter);
            expert.addAvailability(new Schedule(start, end));
        }
        return expert;
    }
    
    // Helper method to authenticate a client using the ClientDAO.
    private static Client authenticateClient(String username, String password, ClientDAO clientDAO) {
        for (Client client : clientDAO.getAllClients()) {
            if (client.getUsername().equals(username) && client.getPassword().equals(password)) {
                return client;
            }
        }
        return null;
    }
    
    // Helper method to authenticate an expert using the ExpertDAO.
    private static Expert authenticateExpert(String username, String password, ExpertDAO expertDAO) {
        for (Expert expert : expertDAO.getAllExperts()) {
            if (expert.getUsername().equals(username) && expert.getPassword().equals(password)) {
                return expert;
            }
        }
        return null;
    }
}
