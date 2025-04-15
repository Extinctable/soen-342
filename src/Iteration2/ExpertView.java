/**
 *
 * @author massimocaruso
 * @author jananaamahathevan
 * 
 */

public class ExpertView {
    public void displayMenu() {
        System.out.println("\n===== EXPERT MENU =====");
        System.out.println("1. View Auctions in My Expertise Areas");
        System.out.println("2. Update Availability");
        System.out.println("3. View Art Objects in My Expertise Areas");
        System.out.println("4. View My Service Requests");
        System.out.print("Select an option: ");
    }

    public void showSuccessMessage(String message) {
        System.out.println(message);
    }
}