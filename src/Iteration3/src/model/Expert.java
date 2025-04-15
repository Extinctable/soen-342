// File: model/Expert.java
package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Expert extends User {
    private String licenseNumber;
    // expertiseAreas stored internally as a comma-separated String.
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
    
    // Returns the comma-separated string of expertise.
    public String getExpertiseAreas() {
        return expertiseAreas;
    }
    
    /**
     * Sets the expertise areas from a Set<String>.
     * The Set is converted to a comma-separated String.
     */
    public void setExpertiseAreas(Set<String> expertiseSet) {
        if (expertiseSet == null || expertiseSet.isEmpty()) {
            this.expertiseAreas = "";
        } else {
            // Join the set elements with ", " as the delimiter.
            this.expertiseAreas = String.join(", ", expertiseSet);
        }
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
    
    /**
     * (Optional) Helper method to get the expertise areas as a Set<String>.
     */
    public Set<String> getExpertiseAreaSet() {
        Set<String> areaSet = new HashSet<>();
        if (this.expertiseAreas != null && !this.expertiseAreas.isEmpty()) {
            String[] areas = this.expertiseAreas.split(",");
            for (String area : areas) {
                areaSet.add(area.trim());
            }
        }
        return areaSet;
    }
}
