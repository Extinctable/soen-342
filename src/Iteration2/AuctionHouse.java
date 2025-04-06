
/**
 *
 * @author massimocaruso
 */
class AuctionHouse {
    private static int nextId = 1;
    
    private int id;
    private String name;
    private String location;
    private String contactInfo;
    
    public AuctionHouse(String name, String location, String contactInfo) {
        this.id = nextId++;
        this.name = name;
        this.location = location;
        this.contactInfo = contactInfo;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getContactInfo() {
        return contactInfo;
    }
    
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}