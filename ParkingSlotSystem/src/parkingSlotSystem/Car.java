package parkingSlotSystem;

/**
 * This class represents a Car object with various attributes.
 * @author   Nidhi Chhoker 104341457
 * @version  JDK version 20.0.2
 * @date     Created on 8 September 2023

 */
public class Car {

    // Private fields to store car information.
    private String slotId;   // Unique identifier for the parking slot.
    private String carReg;   // Car registration number.
    private String carMake;  // Car make (e.g., Toyota, Honda).
    private String carModel; // Car model (e.g., Corolla, Civic).
    private int carYear;     // Year of the car's manufacture.
    private String carTime;  // Time when the car is parked.

    // Constructor to initialize a Car object with all its attributes.
    public Car(String slotId, String carReg, String carMake, String carModel, int carYear, String carTime) 
    {
        this.setSlotId(slotId);
        this.setCarReg(carReg);
        this.setCarMake(carMake);
        this.setCarModel(carModel);
        this.setCarYear(carYear);
        this.setCarTime(carTime);
    }

    // Getter method for retrieving the car's parking time.
    public String getCarTime() {
        return carTime;
    }

    // Setter method for updating the car's parking time.
    public void setCarTime(String carTime) {
        this.carTime = carTime;
    }

    // Getter method for retrieving the parking slot identifier.
    public String getSlotId() {
        return slotId;
    }

    // Setter method for updating the parking slot identifier.
    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    // Getter method for retrieving the car's registration number.
    public String getCarReg() {
        return carReg;
    }

    // Setter method for updating the car's registration number.
    public void setCarReg(String carReg) {
        this.carReg = carReg;
    }

    // Getter method for retrieving the car's make.
    public String getCarMake() {
        return carMake;
    }

    // Setter method for updating the car's make.
    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    // Getter method for retrieving the car's model.
    public String getCarModel() {
        return carModel;
    }

    // Setter method for updating the car's model.
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    // Getter method for retrieving the car's manufacturing year.
    public int getCarYear() {
        return carYear;
    }

    // Setter method for updating the car's manufacturing year.
    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }
}
