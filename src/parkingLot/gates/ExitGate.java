package parkingLot.gates;

import parkingLot.ticketing.ParkingTicket;

public class ExitGate {
    public synchronized void exitVehicle(ParkingTicket ticket) {
        new ExitCommand(ticket).execute();
    }
}