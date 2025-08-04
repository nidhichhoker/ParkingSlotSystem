package parkingSlotSysem;


/**
 * @author   Nidhi Chhoker 
 * @version  JDK version 20.0.2
 * @date     Created on 18 October 2023
 *The CarPark class provides functionality for adding, retrieving, and removing cars from a parking lot.
 *To efficiently maintain and access the parked cars, it makes use of data structures. 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
// Import statements for required Java classes and data structures.

public class CarPark {
    // This line declares the start of the "CarPark" class.

    HashMap<String, ArrayList<Car>> hashMap = new HashMap<>();
    // Declare a HashMap named "hashMap" that associates parking slot identifiers with lists of cars.

    ArrayList<Car> cars = new ArrayList<>();
    // Declare an ArrayList named "cars" to store instances of the "Car" class.

    public CarPark() {
        // Constructor for the "CarPark" class.
        cars = new ArrayList<>();  
        // Initialize the "cars" ArrayList.
        hashMap = new HashMap<>();  
        // Initialize the "hashMap" HashMap.
    }

    public void addCar(String slotId, String carReg, String carMake, String carModel, int carYear, String carTime) {
        // Method to add a car to the CarPark.

        Car car = new Car(slotId, carReg, carMake, carModel, carYear, carTime);
        // Create a new instance of the "Car" class with the provided details.

        cars.add(car);
        // Add the newly created car to the "cars" ArrayList.

        hashMap.put(slotId, cars);
        // Update the "hashMap" to associate the slotId with the "cars" ArrayList.
    }

    public HashMap<String, ArrayList<Car>> getCarsByRegNumber(String carReg) {
        // Method to retrieve cars by registration number.

        HashMap<String, ArrayList<Car>> matchingCars = new HashMap<>();
        // Create a new HashMap to store matching cars.

        for (Car car : cars) {
            // Iterate through the "cars" ArrayList.
            if (car.getCarReg().equals(carReg)) {
                // Check if the car's registration number matches the provided "carReg."
                matchingCars.put(carReg, cars);
                // Add matching cars to the result HashMap.
            }
        }

        return matchingCars;
        // Return the HashMap containing matching cars.
    }

    public HashMap<String, ArrayList<Car>> getCarsBySlot(String carSlot) {
        // Method to retrieve cars by registration number.

        HashMap<String, ArrayList<Car>> matchingCars = new HashMap<>();
        // Create a new HashMap to store matching cars.

        for (Car car : cars) {
            // Iterate through the "cars" ArrayList.
            if (car.getSlotId().equals(carSlot)) {
                // Check if the car's registration number matches the provided "carReg."
                matchingCars.put(carSlot, cars);
                // Add matching cars to the result HashMap.
            }
        }

        return matchingCars;
        // Return the HashMap containing matching cars.
    }

    
    public HashMap<String, ArrayList<Car>> getCarsByMake(String carMake) {
        // Method to retrieve cars by make.

        HashMap<String, ArrayList<Car>> matchingCars = new HashMap<>();
        // Create a new HashMap to store matching cars.

        for (Car car : cars) {
            // Iterate through the "cars" ArrayList.
            if (car.getCarMake().equals(carMake)) {
                // Check if the car's make matches the provided "carMake."
                matchingCars.put(carMake, cars);
                // Add matching cars to the result HashMap.
            }
        }

        return matchingCars;
        // Return the HashMap containing matching cars.
    }

    public String removeCarByReg(String removeCar) {
        // Method to remove a car by registration number.

        Iterator<Car> iterator = cars.iterator();
        // Create an iterator for the "cars" ArrayList.

        String slotId= " ";
        while (iterator.hasNext()) {
            Car car = iterator.next();

            if (car.getCarReg().equals(removeCar)) {
                slotId= car.getSlotId();
                iterator.remove();
                // Remove the car with the provided registration number from the "cars" ArrayList.
            }
        }

        HashMap<String, ArrayList<Car>> hashMap1 = hashMap;
        // Create a copy of the "hashMap."

        Iterator<Map.Entry<String, ArrayList<Car>>> iterator1 = hashMap1.entrySet().iterator();

        while (iterator1.hasNext()) {
            Map.Entry<String, ArrayList<Car>> entry = iterator1.next();

            if (entry.getKey().equals(slotId)) {
                iterator1.remove();
                // Remove the entry in "hashMap" associated with the removed car's slotId.
                return "Car removed successfully";
            }
        }

        return "No car found with this Registration number. Car cannot be removed.";
        // Return a message indicating whether the removal was successful or not.
    }
}
