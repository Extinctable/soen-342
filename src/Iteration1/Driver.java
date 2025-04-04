/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author massimocaruso
 */
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        ArtAdvisorySystem system = new ArtAdvisorySystem();

        Administrator admin = new Administrator("admin", "adminpass");
        Expert expert = new Expert("expert1", "expertpass");
        Client client = new Client("client1", "clientpass");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Art Advisory System!\n");

        while (true) {
            System.out.println("--- Main Menu ---");
            System.out.println("1. Login as Administrator");
            System.out.println("2. Login as Expert");
            System.out.println("3. Login as Client");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("4")) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            }

            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (admin.getUsername().equals(username) && admin.authenticate(password)) {
                        System.out.println("Logged in as Administrator.");
                        admin.showMenu(system);
                    } else {
                        System.out.println("Invalid administrator credentials.\n");
                    }
                    break;
                case "2":
                    if (expert.getUsername().equals(username) && expert.authenticate(password)) {
                        System.out.println("Logged in as Expert.");
                        expert.showMenu(system);
                    } else {
                        System.out.println("Invalid expert credentials.\n");
                    }
                    break;
                case "3":
                    if (client.getUsername().equals(username) && client.authenticate(password)) {
                        System.out.println("Logged in as Client.");
                        client.showMenu(system);
                    } else {
                        System.out.println("Invalid client credentials.\n");
                    }
                    break;
                default:
                    System.out.println("Invalid option. Please select from the menu.\n");
            }
        }

        scanner.close();
    }
}
