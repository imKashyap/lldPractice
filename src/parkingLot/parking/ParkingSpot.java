package parkingLot.parking;

import parkingLot.ticketing.ParkingTicket;
import parkingLot.vehicle.Vehicle;
import parkingLot.vehicle.VehicleType;

public class ParkingSpot {
    private final String id;
    private final VehicleType type;
    private SpotState state;
    private Vehicle currentVehicle;

    public ParkingSpot(String id, VehicleType type) {
        this.id = id;
        this.type = type;
        this.state = SpotState.AVAILABLE;
    }

    public synchronized ParkingTicket.Builder assignVehicle(Vehicle vehicle) {
        if (state == SpotState.AVAILABLE && vehicle.getType() == this.type) {
            state = SpotState.OCCUPIED;
            this.currentVehicle = vehicle;
            return new ParkingTicket.Builder(vehicle).setParkingSpot(this);
        }
        return null;
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
