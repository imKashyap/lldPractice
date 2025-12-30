package parkingLot.ticketing;

import java.time.Duration;
import java.time.LocalDateTime;

abstract class AbstractFeeCalculator implements FeeCalculator {
    @Override
    public double calculateFee(ParkingTicket ticket) {
        long duration = Duration.between(ticket.getStartTime(), LocalDateTime.now()).toMinutes();
        return baseRate() + duration * perMinuteRate();
    }

    protected abstract double baseRate();
    protected abstract double perMinuteRate();
}