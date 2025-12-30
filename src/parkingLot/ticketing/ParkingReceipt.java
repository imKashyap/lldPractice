package parkingLot.ticketing;

public class ParkingReceipt {
    private final ParkingTicket ticket;
    private final FeeCalculator calculator;
    private double fees;

    public ParkingReceipt(ParkingTicket ticket, FeeCalculator calculator) {
        this.ticket = ticket;
        this.calculator = calculator;
    }

    public String generateReceipt(){
        fees = calculator.calculateFee(ticket);
        return toString();
    }

    @Override
    public String toString() {
        return "ParkingReceipt{" +
                "ticket=" + ticket.toString() +
                ", fees= Rs " + fees +
                '}';
    }
}
