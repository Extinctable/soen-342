/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author massimocaruso
 */
import java.util.Scanner;

public class Client extends User {

    public Client(String username, String password) {
        super(username, password);
    }

    @Override
    public void showMenu(ArtAdvisorySystem system) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Client Menu ---");
            System.out.println("1. View Objects");
            System.out.println("2. Sign Out");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    system.displayObjects();
                    break;
                case "2":
                    System.out.println("Signing out...\n");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
