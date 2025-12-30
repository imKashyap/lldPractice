package parkingLot.vehicle;

public class VehicleFactory {

    public static Vehicle createVehicle(String licensePlate, VehicleType type){
        return switch(type){
            case BIKE -> new Bike(licensePlate);
            case CAR -> new Car(licensePlate);
            case TRUCK -> new Truck(licensePlate);
        };
    }
}
