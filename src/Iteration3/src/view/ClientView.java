package view;

public class ClientView {

    public void displayMenu() {
        System.out.println("\n=== Client Menu ===");
        System.out.println("1. View Auctions");
        System.out.println("2. View Art Objects");
        System.out.println("3. Request Service");
        System.out.println("0. Logout");
        System.out.print("Select an option: \n");
    }
    
    public void showSuccessMessage(String message) {
        System.out.println("[Client] " + message);
    }
}
