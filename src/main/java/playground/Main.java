package playground;

public class Main {
    public static void main(String[] args) {
        Car car = new Car();
        System.out.println("Number of wheels: " + car.getNumberOfWheels()); // Outputs 4
        car.printVehicleType(); // Outputs "Vehicle Type: Car"
    }
}

