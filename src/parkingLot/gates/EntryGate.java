package parkingLot.gates;

import parkingLot.ticketing.ParkingTicket;
import parkingLot.vehicle.Vehicle;

public class EntryGate {
        public synchronized ParkingTicket enterVehicle(Vehicle vehicle) {
            return new EntryCommand(vehicle).execute();
        }
    }

