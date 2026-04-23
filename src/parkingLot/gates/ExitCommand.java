package parkingLot.gates;

import parkingLot.ParkingLot;
import parkingLot.payment.PaymentStrategy;
import parkingLot.ticketing.ParkingTicket;

public class ExitCommand {
    private final ParkingTicket ticket;
    private final PaymentStrategy strategy;

    public ExitCommand(ParkingTicket ticket, PaymentStrategy paymentStrategy) {
        this.ticket = ticket;
        this.strategy = paymentStrategy;
    }

    public void execute() {
        ParkingLot.getInstance().exitVehicle(ticket, strategy);
    }
}
