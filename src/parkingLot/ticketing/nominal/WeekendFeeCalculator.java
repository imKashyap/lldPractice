package parkingLot.ticketing.nominal;

public class WeekendFeeCalculator extends AbstractFeeCalculator {
    protected double baseRate() { return 20; }
    protected double perMinuteRate() { return 1.0; }
}

