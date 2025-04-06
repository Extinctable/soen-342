/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author massimocaruso
 */
public class ObjectOfInterest {
    private String name;
    private String type;
    private boolean isOwned;
    private String description;

    public ObjectOfInterest(String name, String type, boolean isOwned, String description) {
        this.name = name;
        this.type = type;
        this.isOwned = isOwned;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nType: %s\nOwned: %s\nDescription: %s\n",
                name, type, isOwned ? "Yes" : "No", description);
    }
} 
