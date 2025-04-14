package view;

public class ExpertView {

    public void displayMenu() {
        System.out.println("=== Expert Menu ===");
        System.out.println("1. View Auctions by Expertise");
        System.out.println("2. View Art Objects by Expertise");
        System.out.println("3. Update Availability");
        System.out.println("4. View Service Requests");
        System.out.println("0. Logout");
        System.out.print("Select an option: ");
    }
    
    public void showSuccessMessage(String message) {
        System.out.println("[Expert] " + message);
    }
}
