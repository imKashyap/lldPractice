package parkingLot.ticketing;

import parkingLot.payment.PaymentStrategy;

public class ParkingReceipt {
    private final ParkingTicket ticket;
    private final FeeCalculatorStrategy calculator;
    private PaymentStrategy paymentStrategy;

    private double fees;

    public ParkingReceipt(ParkingTicket ticket, FeeCalculatorStrategy calculator, PaymentStrategy strategy) {
        this.ticket = ticket;
        this.calculator = calculator;
        this.paymentStrategy = strategy;
    }

    public String generateReceipt(){
        fees = calculator.calculateFee(ticket);
        paymentStrategy.makePayment(fees);
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
