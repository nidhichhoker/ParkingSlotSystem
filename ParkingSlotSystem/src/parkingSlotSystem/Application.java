package parkingSlotSystem;

import java.time.Duration;  // Import for time duration calculations
import java.time.LocalDateTime;  // Import for working with date and time
import java.time.format.DateTimeFormatter;  // Import for date and time formatting
import java.util.ArrayList;  // Import for ArrayList data structure
import java.util.HashMap;  // Import for HashMap data structure
import java.util.InputMismatchException;  // Import for handling input mismatch exceptions
import java.util.List;  // Import for List interface
import java.util.Map;  // Import for Map interface
import java.util.Scanner;  // Import for user input handling
import java.util.regex.Pattern;  // Import for regular expressions

/**The Application class is the key control hub for a parking management system. 
 * In addition to interacting with users, 
 * It controls parking spaces and vehicles,
 * verifies user input, and offers options for navigating the programme.
 * @author   Nidhi Chhoker 104341457
 * @version  JDK version 20.0.2
 * @date     Created on 8 September 2023
 */

public class Application {
    public static void main(String args[]) {
        // Initialize a Scanner for user input
        Scanner scanner = new Scanner(System.in);
        // Create instances of ParkingSlot and CarPark classes
        ParkingSlot parkingSlot = new ParkingSlot();
        CarPark carPark = new CarPark();

        // Start an infinite loop for the main menu
        while (true) {
            // Display menu options to the user
            System.out.println("1 : Add a car slot");
            System.out.println("2 : Delete a car slot");
            System.out.println("3 : List all car slots");
            System.out.println("4 : Park a car");
            System.out.println("5 : Find a car by Registration number");
            System.out.println("6 : Remove a car by Registration number");
            System.out.println("7 : Find cars by make");
            System.out.println("8 : Exit");
            System.out.println();
            System.out.println("Please select an option (1-8) : ");

            try {
                // Read user input as an integer
                int userInput = scanner.nextInt();

                if (userInput == 0 || userInput >= 9) {
                    System.out.println("Invalid Input !!! Please select an option between 1-8");
                } else {
                    switch (userInput) {
                        case 1:
                            // Call the addParkingCarSlot method
                            parkingSlot.addParkingCarSlot();
                            break;
                        case 2:
                            // Check if parking slots are empty
                            if (parkingSlot.slots.isEmpty()) {
                                System.out.println("No slots found");
                                break;
                            }
                            System.out.println("Enter Slot ID");
                            String removeCarSlot = scanner.next();
                            // Get the HashMap from CarPark and call deleteCarSlot method
                            HashMap<String, ArrayList<Car>> hashmap2 = carPark.hashMap;
                            parkingSlot.deleteCarSlot(removeCarSlot, hashmap2);
                            break;
                        case 3:
                            // Check if parking slots are empty
                            if (parkingSlot.slots.isEmpty()) {
                                System.out.println("No slots for parking found");
                                break;
                            }
                            // Get the HashMap and ArrayList from CarPark and call listAllCarSlot method
                            HashMap<String, ArrayList<Car>> hashmap = carPark.hashMap;
                            ArrayList<Car> cars = carPark.cars;
                            parkingSlot.listAllCarSlot(hashmap, cars);
                            break;
                        case 4:
                            // Prompt the user to enter a slot ID for parking
                            System.out.println("Please enter the slot ID (e.g. A001) that you want to park at : ");
                            Scanner sc = new Scanner(System.in);
                            String slotId = sc.next();
                            // Check if the slot is already occupied
                            if (carPark.hashMap.containsKey(slotId)) {
                                System.out.println(" Slot " + slotId + " already occupied. Please select a different slot for parking ");
                            } else {
                                boolean containsBob = parkingSlot.slots.contains(slotId);
                                if (containsBob) {
                                    System.out.println("Please enter the car registration number (e.g., D1234) : ");
                                    String carReg = sc.next();
                                    String pattern = "^[A-Z]\\d{4}$";
                                    boolean found = false;
                                    ArrayList<Car> car1 = carPark.cars;
                                    // Check if the car with the same registration number is already parked
                                    for (Car car : car1) {
                                        if (car.getCarReg().equals(carReg)) {
                                            found = true;
                                        }
                                    }
                                    if (found == false) {
                                        if (Pattern.matches(pattern, carReg)) {
                                            // Prompt for car details
                                            System.out.println("Please enter the car's make (e.g., Toyota) : ");
                                            String carMake = sc.next();
                                            System.out.println("Please enter the car's model (e.g., Corolla) : ");
                                            String carModel = sc.next();
                                            System.out.println("Please enter the car's year (e.g., 2009) : ");
                                            int carYear = sc.nextInt();
                                            if (carYear <= 2023) {
                                                // Get the current date and time
                                                LocalDateTime currentDateTime = LocalDateTime.now();
                                                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                                String formattedDateTime = currentDateTime.format(outputFormatter);
                                                // Add the car to the parking
                                                carPark.addCar(slotId, carReg, carMake, carModel, carYear, formattedDateTime);
                                                System.out.println("Car added successfully");
                                            } else {
                                                System.out.println("Invalid input. Please enter a valid year.");
                                            }
                                        } else {
                                            System.out.println(carReg + " does not match the pattern. Please enter it correctly.");
                                        }
                                    } else {
                                        System.out.println("Car with registration number " + carReg + " is already parked in the parking.");
                                    }
                                } else {
                                    System.out.println("Slot " + slotId + " is not available");
                                }
                            }
                            break;
                        case 5:
                            // Prompt the user to enter a car registration number to find
                            System.out.println("Please enter car registration number");
                            String carReg = scanner.next();
                            // Get cars by registration number from CarPark
                            HashMap<String, ArrayList<Car>> getCarsByRegNumber = carPark.getCarsByRegNumber(carReg);
                            if (getCarsByRegNumber.isEmpty()) {
                                System.out.println("No cars parked with " + carReg + " registration number");
                            }
                            // Display car details
                            for (Map.Entry<String, ArrayList<Car>> entry : getCarsByRegNumber.entrySet()) {
                                ArrayList<Car> valueList = entry.getValue();
                                for (Car value : valueList) {
                                    if (value.getCarReg().equals(carReg)) {
                                        System.out.println("Car Slot ID: " + value.getSlotId());
                                        System.out.println("Car Registration: " + value.getCarReg());
                                        System.out.println("Car Make: " + value.getCarMake());
                                        System.out.println("Car Model: " + value.getCarModel());
                                        System.out.println("Car Year: " + value.getCarYear());
                                        System.out.println();
                                    }
                                }
                            }
                            break;
                        case 6:
                            // Prompt the user to enter a car registration number to remove
                            System.out.println("Enter car registration number ");
                            String removeCarReg = scanner.next();
                            // Call removeCarByReg method and display the result message
                            String message = carPark.removeCarByReg(removeCarReg);
                            System.out.println(message);
                            break;
                        case 7:
                            // Prompt the user to enter a car make to find
                            System.out.println("Please enter Car Make");
                            String carMake = scanner.next();
                            // Get cars by make from CarPark
                            HashMap<String, ArrayList<Car>> getCarsByMake = carPark.getCarsByMake(carMake);
                            if (getCarsByMake.isEmpty()) {
                                System.out.println("No cars are parked with " + carMake + " Make");
                            }
                            // Display car details including parking duration
                            for (Map.Entry<String, ArrayList<Car>> entry : getCarsByMake.entrySet()) {
                                String key = entry.getKey();
                                ArrayList<Car> valueList = entry.getValue();
                                for (Car value : valueList) {
                                    if (value.getCarMake().equals(carMake)) {
                                        String storedTimeString = value.getCarTime();
                                        LocalDateTime currentDateTime = LocalDateTime.now();
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                        LocalDateTime storedTime = LocalDateTime.parse(storedTimeString, formatter);
                                        Duration duration = Duration.between(storedTime, currentDateTime);
                                        long hours = duration.toHours();
                                        long minutes = duration.toMinutesPart();
                                        long seconds = duration.toSecondsPart();
                                        System.out.println("Car Slot ID: " + value.getSlotId());
                                        System.out.println("Car Registration: " + value.getCarReg());
                                        System.out.println("Car Make: " + value.getCarMake());
                                        System.out.println("Car Model: " + value.getCarModel());
                                        System.out.println("Car Year: " + value.getCarYear());
                                        System.out.println("Parking Time: " + value.getCarTime());
                                        System.out.println("Parking Duration: " + "Duration: " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds");
                                        System.out.println();
                                        System.out.println();
                                    }
                                }
                                System.out.println();
                            }
                            break;
                        case 8:
                            System.out.println("Program Closed");
                            scanner.close();  // Close the scanner before exiting
                            System.exit(0);   // Terminate the program
                    }
                    // Prompt the user to continue or exit the program
                    System.out.println("Do you want to continue? (Y/N)");
                    String userInput2 = scanner.next();
                    if (userInput2.equalsIgnoreCase("N")) {
                        System.out.println("Program closed");
                        break;
                    } else if (userInput2.equalsIgnoreCase("Y")) {
                        System.out.println("");
                    } else {
                        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                    }
                }
            } catch (InputMismatchException e) {
                // Handle input mismatch exception
                System.out.println("Invalid input. Please enter a valid year.");
                System.out.println("Do you want to continue? (Y/N)");
                String userInput2 = scanner.next();
                if (userInput2.equalsIgnoreCase("N")) {
                    System.out.println("Program closed");
                    break;
                }
            }
        }
    }
}
