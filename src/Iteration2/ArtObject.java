
/**
 *
 * @author massimocaruso
 */
class ArtObject {
    private static int nextId = 1;
    
    private int id;
    private String title;
    private String description;
    private String type;
    private boolean isOwned;
    private boolean isToBeAuctioned;
    private Auction auction;
    
    public ArtObject(String title, String description, String type, boolean isOwned, 
                     boolean isToBeAuctioned, Auction auction) {
        this.id = nextId++;
        this.title = title;
        this.description = description;
        this.type = type;
        this.isOwned = isOwned;
        this.isToBeAuctioned = isToBeAuctioned;
        this.auction = auction;
    }
    
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public boolean isOwned() {
        return isOwned;
    }
    
    public void setOwned(boolean owned) {
        isOwned = owned;
    }
    
    public boolean isToBeAuctioned() {
        return isToBeAuctioned;
    }
    
    public void setToBeAuctioned(boolean toBeAuctioned) {
        isToBeAuctioned = toBeAuctioned;
    }
    
    public Auction getAuction() {
        return auction;
    }
    
    public void setAuction(Auction auction) {
        this.auction = auction;
        if (auction != null) {
            this.isToBeAuctioned = true;
        }
    }
}
