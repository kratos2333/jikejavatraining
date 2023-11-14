package playground;

public class Car implements Vehicle {

    @Override
    public int getNumberOfWheels() {
        return 4; // Most cars have 4 wheels
    }

    // The default method printVehicleType() is inherited and can be overridden if needed
}
