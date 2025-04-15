/**
 *
 * @author massimocaruso
 * @author jananaamahathevan
 * 
 */

public class ClientView {
    public void displayMenu() {
        System.out.println("\n===== CLIENT MENU =====");
        System.out.println("1. View Auctions");
        System.out.println("2. View Art Objects");
        System.out.println("3. Request Service");
        System.out.println("4. View My Service Requests");
        System.out.print("Select an option: ");
    }

    public void showSuccessMessage(String message) {
        System.out.println(message);
    }
}
