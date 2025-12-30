package parkingLot.gates;

import parkingLot.ParkingLot;
import parkingLot.ticketing.ParkingTicket;
import parkingLot.vehicle.Vehicle;

public class EntryCommand {

    private final Vehicle vehicle;

    public EntryCommand(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingTicket execute() {
        return ParkingLot.getInstance().parkVehicle(vehicle);
    }
}
