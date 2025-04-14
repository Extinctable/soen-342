// File: model/User.java
package model;

public abstract class User {
    protected int id;
    protected String username;
    protected String password;
    protected String name;
    protected String contactInfo;
    protected String role;
    protected static int nextId = 1;

    public User(String username, String password, String name, String contactInfo) {
        this.id = nextId++;
        this.username = username;
        this.password = password;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
    
    public String getRole() {
        return this.role;
    }
    
    public int getId() {
        return id;
    }
    // Add a setter for id so that the DAO can update it
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    
    @Override
    public String toString() {
        return "User: " + username + " (" + role + ")";
    }
}
