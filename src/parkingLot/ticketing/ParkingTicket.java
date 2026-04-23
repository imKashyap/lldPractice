package parkingLot.ticketing;

import java.time.LocalDateTime;

import parkingLot.parking.ParkingFloor;
import parkingLot.parking.ParkingSpot;
import parkingLot.vehicle.Vehicle;

public class ParkingTicket {
    private ParkingFloor parkingFloor;
    private ParkingSpot parkingSpot;
    private LocalDateTime entryTime;
    private Vehicle vehicle;

    private ParkingTicket(Builder builder) {
        this.parkingFloor = builder.parkingFloor;
        this.parkingSpot = builder.parkingSpot;
        this.entryTime = builder.entryTime;
        this.vehicle = builder.vehicle;
    }

    public ParkingFloor getparkingFloor() {
        return parkingFloor;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                ", parkingFloor='" + parkingFloor.getId() + '\'' +
                ", parkingSpot='" + parkingSpot.getId()+ '\'' +
                ", entryTime=" + entryTime +
                ", vehicle=" + vehicle +
                '}';
    }

    public static class Builder {
        private ParkingFloor parkingFloor;
        private ParkingSpot parkingSpot;
        private LocalDateTime entryTime;
        private Vehicle vehicle;

        public Builder(Vehicle vehicle) {
            this.vehicle = vehicle;
            this.entryTime = LocalDateTime.now();
        }

        public Builder setparkingFloor(ParkingFloor parkingFloor) {
            this.parkingFloor = parkingFloor;
            return this;
        }

        public Builder setParkingSpot(ParkingSpot parkingSpot) {
            this.parkingSpot = parkingSpot;
            return this;
        }

        public ParkingTicket build() {
            return new ParkingTicket(this);
        }
    }
}
