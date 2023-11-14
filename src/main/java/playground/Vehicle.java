package playground;

public interface Vehicle {

    // Abstract method
    int getNumberOfWheels();

    // Default method
    default void printVehicleType() {
        System.out.println("Vehicle Type: " + getClass().getSimpleName());
    }
}