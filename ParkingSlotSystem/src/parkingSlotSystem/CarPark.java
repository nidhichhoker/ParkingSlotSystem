package parkingSlotSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author   Nidhi Chhoker 104341457
 * @version  JDK version 20.0.2
 * @date     Created on 8 September 2023
 *The CarPark class provides functionality for adding, retrieving, and removing cars from a parking lot.
 *To efficiently maintain and access the parked cars, it makes use of data structures. 
 */
public class CarPark 
{
    // HashMap to store cars by slot ID
    HashMap<String, ArrayList<Car>> hashMap = new HashMap<>();

    // ArrayList to store cars
    ArrayList<Car> cars = new ArrayList<>();

    // Constructor for initializing the CarPark
    public CarPark() 
    {
        cars = new ArrayList<>();   // Initialize the cars ArrayList
        hashMap = new HashMap<>();  // Initialize the hashMap
    }

    // Method to add a car to the CarPark
    public void addCar(String slotId, String carReg, String carMake, String carModel, int carYear, String carTime)
    {
        Car car = new Car(slotId, carReg, carMake, carModel, carYear, carTime);

        cars.add(car); // Add the car to the cars ArrayList

        hashMap.put(slotId, cars); // Associate the car with its slot ID in the hashMap

        // Print car details
        System.out.println("Car Slot ID: " + car.getSlotId());
        System.out.println("Car Registration: " + car.getCarReg());
        System.out.println("Car Make: " + car.getCarMake());
        System.out.println("Car Model: " + car.getCarModel());
        System.out.println("Car Year: " + car.getCarYear());
        System.out.println("Car Parked Time: " + car.getCarTime());
    }

    // Method to retrieve cars by registration number
    public HashMap<String, ArrayList<Car>> getCarsByRegNumber(String carReg)
    {
        HashMap<String, ArrayList<Car>> matchingCars = new HashMap<>();

        for (Car car : cars) 
        {
            if (car.getCarReg().equals(carReg)) 
            {
                matchingCars.put(carReg, cars);
            }
        }

        return matchingCars;
    }

    // Method to retrieve cars by make
    public HashMap<String, ArrayList<Car>> getCarsByMake(String carMake)
    {
        HashMap<String, ArrayList<Car>> matchingCars = new HashMap<>();

        for (Car car : cars) 
        {
            if (car.getCarMake().equals(carMake)) 
            {
                matchingCars.put(carMake, cars);
            }
        }

        return matchingCars;
    }

    // Method to remove a car by registration number
    public String removeCarByReg(String removeCar) 
    {
        Iterator<Car> iterator = cars.iterator();

        while (iterator.hasNext()) 
        {
            Car car = iterator.next();

            if (car.getCarReg().equals(removeCar)) 
            {
                iterator.remove(); // Remove the car from the ArrayList
                return "Car removed successfully";
            }
        }

        return "No car found with this Registration number. Car cannot be removed.";
    }
}
