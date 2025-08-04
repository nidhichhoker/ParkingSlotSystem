package parkingSlotSysem;

/**
 * The ParkingSlot class represents a parking slot management system.
 * @author   Nidhi Chhoker 
 * @version  JDK version 20.0.2
 * @date     Created on 18 October 2023
 *
 */


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;
// Import statements for required Java classes and data structures.

public class ParkingSlot {
    // This line declares the start of the "ParkingSlot" class.

    ArrayList<String> slots = new ArrayList<>();
    // Declare an ArrayList named "slots" to store parking slot identifiers.

    public String addParkingCarSlot(String slotId) {
        // Method to add a parking slot.

        if (slots.contains(slotId)) {
            // Check if the provided slotId is already in the "slots" ArrayList.
            System.out.println(slotId + " Already created. Create slot with a different Slot ID");
            String s = slotId + " Already created. Create slot with a different Slot ID";
            return s;
        } else {
            String pattern = "^[A-Z]\\d{3}$";
            // Define a regular expression pattern for a valid slotId.

            if (Pattern.matches(pattern, slotId)) {
                // Check if the provided slotId matches the defined pattern.
                slots.add(slotId);
                // Add the new slotId to the "slots" ArrayList.
                System.out.println("The Slot is added successfully.");
                String s = "The Slot is added successfully.";
                return s;
            } else {
                System.out.println(slotId + " does not match the pattern. Please enter a correct pattern");
                String s = slotId + " does not match the pattern. Please enter a correct pattern.";
                return s;
            }
        }
    }

    public String deleteCarSlot(String removeCarSlot, HashMap<String, ArrayList<Car>> carslots) {
        // Method to delete a parking slot.

        String slotToFind = removeCarSlot;
        String s = "";

        ArrayList<Car> value = carslots.get(slotToFind);

        if (value != null) {
            // Check if the slot is occupied by a car.
            System.out.println("Slot Id  '" + slotToFind + " is occupied. It cannot be deleted");
            s = "Slot Id  '" + slotToFind + " is occupied. It cannot be deleted";
            return s;
        } else {
            if (slots.contains(slotToFind)) {
                // Check if the slot exists in the "slots" ArrayList.
                slots.remove(slotToFind);
                // Remove the slot from the "slots" ArrayList.
                System.out.println("The Slot is deleted successfully.");
                s = "The Slot is deleted successfully.";
                return s;
            } else if (slotToFind.isBlank()) {
                System.out.println("Slot " + slotToFind + " is not present");
                s = "Please enter slot id which you want to delete.";
                return s;
            } else {
                System.out.println("Slot " + slotToFind + " is not present");
                s = "Slot " + slotToFind + " is not present.";
                return s;
            }
        }
    }

    public ArrayList<String> listAllCarSlot(HashMap<String, ArrayList<Car>> carslots, ArrayList<Car> cars) {
        // Method to list all parking slots and their occupancy status.

        ArrayList<String> stringArrayList = new ArrayList<>();

        for (int i = 0; i < slots.size(); i++) {
            // Iterate through the "slots" ArrayList to check each slot.

            String slotToFind = slots.get(i);
            String storedTimeString = " ";
            boolean found = false;
            String carReg = " ";
            String carMake = " ";

            for (Car car : cars) {
                if (car.getSlotId().equals(slotToFind)) {
                    // Check if the car is parked in the slot.
                    found = true;
                    storedTimeString = car.getCarTime();
                    carReg = car.getCarReg();
                    carMake = car.getCarMake();
                    break;
                }
            }

            if (found) {
                // If the slot is occupied, calculate the duration of parking.
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime storedTime = LocalDateTime.parse(storedTimeString, formatter);
                Duration duration = Duration.between(storedTime, currentDateTime);

                long hours = duration.toHours();
                long minutes = duration.toMinutesPart();
                long seconds = duration.toSecondsPart();

                stringArrayList.add("Slot Id '" + slotToFind + "' is occupied by a car with reg:" + carReg + ", make: " + carMake + ".  Duration: " +
                    hours + " hours, " + minutes + " minutes, " + seconds + " seconds");
            } else {
                // If the slot is not occupied, mark it as vacant.
                stringArrayList.add("Slot Id '" + slotToFind + "' is not occupied. ");
            }
        }

        return stringArrayList;
    }
}
