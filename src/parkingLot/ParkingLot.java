package parkingLot;

import parkingLot.parking.ParkingFloor;
import parkingLot.parking.ParkingSpot;
import parkingLot.ticketing.BasicFeeCalculator;
import parkingLot.ticketing.FeeCalculator;
import parkingLot.ticketing.ParkingReceipt;
import parkingLot.ticketing.ParkingTicket;
import parkingLot.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingLot {
private static final ParkingLot INSTANCE = new ParkingLot();
private final List<ParkingFloor> floors = new ArrayList<>();
private FeeCalculator feeCalculator = new BasicFeeCalculator();

private ParkingLot() {}

public static ParkingLot getInstance() { return INSTANCE; }

public void setFeeCalculator(FeeCalculator feeCalculator) {
    this.feeCalculator = feeCalculator;
}

public void addfloor(ParkingFloor floor) {
    floors.add(floor);
}

public synchronized ParkingTicket parkVehicle(Vehicle vehicle) {
    for (ParkingFloor floor : floors) {
        Optional<ParkingSpot> spot = floor.findAvailableSpot(vehicle.getType());
        if (spot.isPresent() && spot.get().assignVehicle(vehicle)) {
            floor.notifyObservers();
            return new ParkingTicket(vehicle, spot.get());
        }
    }
    throw new RuntimeException("No available spot");
}

public synchronized void exitVehicle(ParkingTicket ticket) {
    ParkingSpot spot = ticket.getSpot();
    spot.release();
    ParkingReceipt parkingReceipt = new ParkingReceipt(ticket, feeCalculator);
    parkingReceipt.generateReceipt();
}
}
