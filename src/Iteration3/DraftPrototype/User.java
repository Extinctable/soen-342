
/**
 *
 * @author massimocaruso
 * @author jananaamahathevan
 */

abstract class User {
    private static int nextId = 1;
    
    private int id;
    private String username;
    private String password;
    private String name;
    private String contactInfo;
    
    public User(String username, String password, String name, String contactInfo) {
        this.id = nextId++;
        this.username = username;
        this.password = password;
        this.name = name;
        this.contactInfo = contactInfo;
    }
    
    public int getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
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
    
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public abstract String getRole();
}