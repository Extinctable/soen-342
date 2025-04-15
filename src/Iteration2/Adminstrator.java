/**
 *
 * @author massimocaruso
 * @author jananaamahathevan
 */

class Administrator extends User {
    private static Administrator instance;

    public Administrator(String username, String password, String name, String contactInfo, String phoneNumber) {
        super(username, password, name, contactInfo);
    }

    //singleton for one admin
    public static Administrator getInstance(String username, String password, String name, String contactInfo, String phoneNumber) {
        if (instance == null) {
            instance = new Administrator(username, password, name, contactInfo, phoneNumber);
        }
        return instance;
    }

    @Override
    public String getRole() {
        return "Adminstrator";
    }
}