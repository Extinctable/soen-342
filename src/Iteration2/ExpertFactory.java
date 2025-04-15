/**
 *
 * @author jananaamahathevan
 */

class ExpertFactory implements UserFactory {
    private String licenseNumber;

    public ExpertFactory(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public User createUser(String username, String password, String name, String contactInfo) {
        return new Expert(username, password, name, contactInfo, licenseNumber);
    }
}