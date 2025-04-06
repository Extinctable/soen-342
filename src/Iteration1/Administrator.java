/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author massimocaruso
 */
import java.util.Scanner;

public class Administrator extends User {

    public Administrator(String username, String password) {
        super(username, password);
    }

    public void addObjectOfInterest(ArtAdvisorySystem system, Scanner scanner) {
        System.out.println("Enter object name:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter object type:");
        String type = scanner.nextLine().trim();

        System.out.println("Is the object owned by the institution? (yes/no):");
        String ownershipInput = scanner.nextLine().trim().toLowerCase();
        boolean isOwned = ownershipInput.equals("yes");

        System.out.println("Enter object description:");
        String description = scanner.nextLine().trim();

        system.addObject(new ObjectOfInterest(name, type, isOwned, description));
    }

    @Override
    public void showMenu(ArtAdvisorySystem system) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Administrator Menu ---");
            System.out.println("1. Add Object of Interest");
            System.out.println("2. View Objects");
            System.out.println("3. Sign Out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addObjectOfInterest(system, scanner);
                    break;
                case "2":
                    system.displayObjects();
                    break;
                case "3":
                    System.out.println("Signing out...\n");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
