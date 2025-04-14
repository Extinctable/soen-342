// File: model/Expert.java
package model;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

public class Expert extends User {
    private String licenseNumber;
    private Set<String> expertiseAreas;
    private List<Schedule> availability;

    public Expert(String username, String password, String name, String contactInfo, String licenseNumber) {
        super(username, password, name, contactInfo);
        this.licenseNumber = licenseNumber;
        this.expertiseAreas = new HashSet<>();
        this.availability = new ArrayList<>();
        this.role = "Expert";
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public Set<String> getExpertiseAreas() {
        return expertiseAreas;
    }
    public void addExpertiseArea(String area) {
        expertiseAreas.add(area);
    }
    public void removeExpertiseArea(String area) {
        expertiseAreas.remove(area);
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
