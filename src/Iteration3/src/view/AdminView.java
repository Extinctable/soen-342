package view;

public class AdminView {

    public void displayMenu() {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. Add Auction House");
        System.out.println("2. Add Auction");
        System.out.println("3. Add Art Object");
        System.out.println("4. Add Expert");
        System.out.println("5. Approve Clients");
        System.out.println("6. View Auctions");
        System.out.println("7. View Art Objects");
        System.out.println("8. View Experts");
        System.out.println("9. View Clients");
        System.out.println("10. View Service Requests");
        System.out.println("11. View Auction Houses");
        System.out.println("0. Logout");
        System.out.print("Select an option: \n");
    }
    
    public void showSuccessMessage(String message) {
        System.out.println("[Admin] " + message);
    }
}
