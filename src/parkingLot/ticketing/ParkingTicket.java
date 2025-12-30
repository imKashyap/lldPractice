package parkingLot.ticketing;

import parkingLot.parking.ParkingFloor;
import parkingLot.parking.ParkingSpot;
import parkingLot.vehicle.Vehicle;
import java.time.LocalDateTime;

public class ParkingTicket {
    private final Vehicle vehicle;
    private ParkingFloor floor;
    private final ParkingSpot spot;
    private final LocalDateTime startTime;

    public ParkingTicket(Vehicle vehicle, ParkingSpot spot) {
        this.vehicle = vehicle;
        this.spot = spot;
        this.startTime = java.time.LocalDateTime.now();
    }

    public LocalDateTime getStartTime() { return startTime; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return spot; }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                "vehicle=" + vehicle +
                ", floor=" + floor +
                ", spot=" + spot +
                ", startTime=" + startTime +
                '}';
    }
}