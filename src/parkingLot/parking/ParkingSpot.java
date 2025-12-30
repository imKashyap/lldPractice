package parkingLot.parking;

import parkingLot.utils.IdGenerator;
import parkingLot.vehicle.Vehicle;
import parkingLot.vehicle.VehicleType;

public class ParkingSpot {
    private final String id;
    private final VehicleType type;
    private SpotState state;
    private Vehicle currentVehicle;

    public ParkingSpot(VehicleType type) {
        this.id = IdGenerator.generateId("PS-");
        this.type = type;
        this.state = SpotState.AVAILABLE;
    }

    public synchronized boolean assignVehicle(Vehicle vehicle) {
        if (state == SpotState.AVAILABLE && vehicle.getType() == this.type) {
            state = SpotState.OCCUPIED;
            this.currentVehicle = vehicle;
            return true;
        }
        return false;
    }

    public synchronized void release() {
        state = SpotState.AVAILABLE;
        this.currentVehicle = null;
    }

    public SpotState getState() {
        return state;
    }

    public VehicleType getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
