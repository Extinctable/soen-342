
/**
 *
 * @author massimocaruso
 */
class Client extends User {
    private String affiliation;
    private String intent;
    private boolean approved;
    
    public Client(String username, String password, String name, String contactInfo, String affiliation, String intent) {
        super(username, password, name, contactInfo);
        this.affiliation = affiliation;
        this.intent = intent;
        this.approved = false;
    }
    
    public String getAffiliation() {
        return affiliation;
    }
    
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
    
    public String getIntent() {
        return intent;
    }
    
    public void setIntent(String intent) {
        this.intent = intent;
    }
    
    public boolean isApproved() {
        return approved;
    }
    
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}