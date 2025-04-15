// File: model/Expert.java
package model;

import java.util.ArrayList;
import java.util.List;

public class Expert extends User {
    private String licenseNumber;
    private String expertiseAreas;
    private List<Schedule> availability;

    public Expert(String username, String password, String name, String contactInfo, String licenseNumber, String expertiseAreas) {
        super(username, password, name, contactInfo);
        this.licenseNumber = licenseNumber;
        this.expertiseAreas = expertiseAreas;
        this.availability = new ArrayList<>();
        this.role = "Expert";
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public String getExpertiseAreas() {
        return expertiseAreas;
    }
    
    public List<Schedule> getAvailability() {
        return availability;
    }
    public void setAvailability(List<Schedule> availability) {
        this.availability = availability;
    }
    public void addAvailability(Schedule schedule) {
        availability.add(schedule);
    }
    
    @Override
    public String toString() {
        return "Expert: " + username + ", License: " + licenseNumber + ", Expertise: " + expertiseAreas;
    }
}
