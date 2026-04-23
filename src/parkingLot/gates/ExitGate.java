package parkingLot.gates;

import parkingLot.payment.PaymentStrategy;
import parkingLot.ticketing.ParkingTicket;

public class ExitGate {
    public synchronized void exitVehicle(ParkingTicket ticket, PaymentStrategy strategy) {
        new ExitCommand(ticket, strategy).execute();
    }
}
