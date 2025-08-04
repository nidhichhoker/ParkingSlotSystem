package parkingSlotSysem;


/**
 * This class represents a Car object with various attributes.
 * @author   Nidhi Chhoker 
 * @version  JDK version 20.0.2
 * @date     Created on 18 October 2023

 */

public class Car {
    // This line declares the start of the "Car" class.

    private String slotId;   
    // Declares a private String variable "slotId" to store the parking slot identifier.

    private String carReg;   
    // Declares a private String variable "carReg" to store the car's registration number.

    private String carMake;  
    // Declares a private String variable "carMake" to store the car's make.

    private String carModel; 
    // Declares a private String variable "carModel" to store the car's model.

    private int carYear;     
    // Declares a private integer variable "carYear" to store the car's manufacturing year.

    private String carTime;  
    // Declares a private String variable "carTime" to store the car's parking time.

    // Constructor for the "Car" class.
    public Car(String slotId, String carReg, String carMake, String carModel, int carYear, String carTime) 
    {
        // This is the constructor method for the "Car" class, which is called when an instance of the class is created.

        // Initialize the instance variables with values passed as arguments to the constructor.
        this.setSlotId(slotId);
        // Calls the "setSlotId" method to set the parking slot identifier.
        this.setCarReg(carReg);
        // Calls the "setCarReg" method to set the car's registration number.
        this.setCarMake(carMake);
        // Calls the "setCarMake" method to set the car's make.
        this.setCarModel(carModel);
        // Calls the "setCarModel" method to set the car's model.
        this.setCarYear(carYear);
        // Calls the "setCarYear" method to set the car's manufacturing year.
        this.setCarTime(carTime);
        // Calls the "setCarTime" method to set the car's parking time.
    }

    // Getter method for retrieving the car's parking time.
    public String getCarTime() {
        return carTime;
        // Returns the value of the "carTime" instance variable.
    }

    // Setter method for updating the car's parking time.
    public void setCarTime(String carTime) {
        this.carTime = carTime;
        // Sets the value of the "carTime" instance variable to the provided value.
    }

    // Getter method for retrieving the parking slot identifier.
    public String getSlotId() {
        return slotId;
        // Returns the value of the "slotId" instance variable.
    }

    // Setter method for updating the parking slot identifier.
    public void setSlotId(String slotId) {
        this.slotId = slotId;
        // Sets the value of the "slotId" instance variable to the provided value.
    }

    // Getter method for retrieving the car's registration number.
    public String getCarReg() {
        return carReg;
        // Returns the value of the "carReg" instance variable.
    }

    // Setter method for updating the car's registration number.
    public void setCarReg(String carReg) {
        this.carReg = carReg;
        // Sets the value of the "carReg" instance variable to the provided value.
    }

    // Getter method for retrieving the car's make.
    public String getCarMake() {
        return carMake;
        // Returns the value of the "carMake" instance variable.
    }

    // Setter method for updating the car's make.
    public void setCarMake(String carMake) {
        this.carMake = carMake;
        // Sets the value of the "carMake" instance variable to the provided value.
    }

    // Getter method for retrieving the car's model.
    public String getCarModel() {
        return carModel;
        // Returns the value of the "carModel" instance variable.
    }

    // Setter method for updating the car's model.
    public void setCarModel(String carModel) {
        this.carModel = carModel;
        // Sets the value of the "carModel" instance variable to the provided value.
    }

    // Getter method for retrieving the car's manufacturing year.
    public int getCarYear() {
        return carYear;
        // Returns the value of the "carYear" instance variable.
    }

    // Setter method for updating the car's manufacturing year.
    public void setCarYear(int carYear) {
        this.carYear = carYear;
        // Sets the value of the "carYear" instance variable to the provided value.
    }
}
