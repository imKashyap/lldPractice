package parkingLot.ticketing.nominal;

import java.time.Duration;
import java.time.LocalDateTime;

import parkingLot.ticketing.FeeCalculatorStrategy;
import parkingLot.ticketing.ParkingTicket;

abstract class AbstractFeeCalculator implements FeeCalculatorStrategy {
    @Override
    public double calculateFee(ParkingTicket ticket) {
        long duration = Duration.between(ticket.getEntryTime(), LocalDateTime.now()).toMinutes();
        return baseRate() + duration * perMinuteRate();
    }

    protected abstract double baseRate();
    protected abstract double perMinuteRate();
}
