/**
 *
 * @author jananaamahathevan
 */

class ClientFactory implements UserFactory {
    private String affiliation;
    private String intent;

    public ClientFactory(String affilation, String intent) {
        this.affiliation = affilation;
        this.intent = intent;
    }

    @Override
    public User createUser(String username, String password, String name, String contactInfo) {
        return new Client(username, password, name, contactInfo, affiliation, intent); 
    }
}