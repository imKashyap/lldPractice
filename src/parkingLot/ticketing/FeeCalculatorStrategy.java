package parkingLot.ticketing;

public interface FeeCalculatorStrategy {
    double calculateFee(ParkingTicket ticket);
}
