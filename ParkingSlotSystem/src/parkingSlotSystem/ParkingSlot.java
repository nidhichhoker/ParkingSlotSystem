package parkingSlotSystem;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
/**
 * The ParkingSlot class represents a parking slot management system.
 * @author   Nidhi Chhoker 104341457
 * @version  JDK version 20.0.2
 * @date     Created on 8 September 2023
 *
 */
public class ParkingSlot 
{
    // ArrayList to store parking slot IDs.
    ArrayList<String> slots = new ArrayList<>();

    // Method to add a new parking slot.
    public void addParkingCarSlot()
    {
        // Ask the user to enter a Slot ID.
        System.out.println("Please enter a Slot ID (e.g. A001) : ");

        // Create a Scanner object to read user input.
        Scanner scanner = new Scanner(System.in);
        String slotId = scanner.nextLine(); 

        // Check if the slot already exists.
        if(slots.contains(slotId))
        {
            System.out.println(slotId +" Already created. Create slot with a different Slot ID");
        }
        else {
            // Define a pattern for a valid slot ID.
            String pattern = "^[A-Z]\\d{3}$";

            // Check if the entered Slot ID matches the pattern.
            if (Pattern.matches(pattern, slotId))
            {
                // Add the slot to the ArrayList.
                slots.add(slotId);
                System.out.println("The Slot is added successfully.");
            } 
            else {
                System.out.println(slotId + " does not match the pattern. Please enter a correct pattern");
            }
        }
    }

    // Method to delete a parking slot.
    public void deleteCarSlot(String removeCarSlot, HashMap<String, ArrayList<Car>> carslots)
    { 
        // Get the slot to be removed.
        String slotToFind = removeCarSlot;

        // Check if the slot is occupied by a car.
        ArrayList<Car> value = carslots.get(slotToFind);

        if(value != null) {
            System.out.println("Slot Id  '" + slotToFind + " is occupied. It cannot be deleted");
        } 
        else 
        {
            // Check if the slot exists in the ArrayList.
            if(slots.contains(slotToFind))
            {
                // Remove the slot from the ArrayList.
                slots.remove(slotToFind);
                System.out.println("The Slot is deleted successfully.");
            }
            else {
                System.out.println("Slot "+slotToFind + " is not present");
            }
        }
    }

    // Method to list all parking slots and their occupancy status.
    public void listAllCarSlot(HashMap<String, ArrayList<Car>> carslots, ArrayList<Car> cars) 
    {
        // Iterate through all slots.
        for (int i = 0; i < slots.size(); i++) 
        {
            String slotToFind = slots.get(i);

            String storedTimeString = " ";
            boolean found = false;

            // Checking if a car is parked in the slot.
            for (Car car : cars) {
                if (car.getSlotId().equals(slotToFind)) {
                    found = true;
                    storedTimeString = car.getCarTime();
                    break; 
                }
            }

            //  calculate the duration of the parked car
            if (found) {
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime storedTime = LocalDateTime.parse(storedTimeString, formatter);
                Duration duration = Duration.between(storedTime, currentDateTime);

                long hours = duration.toHours();
                long minutes = duration.toMinutesPart();
                long seconds = duration.toSecondsPart();

                System.out.println("Slot Id '" + slotToFind + "' is occupied. Duration: " +
                    hours + " hours, " + minutes + " minutes, " + seconds + " seconds");
            }
            else 
            {
                System.out.println("Slot Id '" + slotToFind + "' is not occupied. ");
            }
        }
    }
}
