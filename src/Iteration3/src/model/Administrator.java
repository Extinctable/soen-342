// File: model/Administrator.java
package model;

public class Administrator extends User {
    private String phoneNumber;
    private static Administrator instance;

    private Administrator(String username, String password, String name, String contactInfo, String phoneNumber) {
        super(username, password, name, contactInfo);
        this.phoneNumber = phoneNumber;
        this.role = "Administrator";
    }
    
    public static Administrator getInstance(String username, String password, String name, String contactInfo, String phoneNumber) {
        if (instance == null) {
            instance = new Administrator(username, password, name, contactInfo, phoneNumber);
        }
        return instance;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public String toString() {
        return "Administrator: " + username + ", Phone: " + phoneNumber;
    }
}
