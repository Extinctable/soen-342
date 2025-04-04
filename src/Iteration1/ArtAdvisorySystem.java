/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author massimocaruso
 */
import java.util.ArrayList;
import java.util.List;

public class ArtAdvisorySystem {
    private List<ObjectOfInterest> objects;

    public ArtAdvisorySystem() {
        this.objects = new ArrayList<>();
    }

    public void addObject(ObjectOfInterest object) {
        objects.add(object);
        System.out.println("Object added successfully.\n");
    }

    public void displayObjects() {
        if (objects.isEmpty()) {
            System.out.println("No objects available.\n");
        } else {
            System.out.println("\n--- Objects of Interest ---");
            for (ObjectOfInterest object : objects) {
                System.out.println(object);
            }
        }
    }
}
