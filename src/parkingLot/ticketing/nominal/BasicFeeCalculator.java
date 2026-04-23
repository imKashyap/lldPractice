package parkingLot.ticketing.nominal;

public class BasicFeeCalculator extends AbstractFeeCalculator {
    protected double baseRate() { return 10; }
    protected double perMinuteRate() { return 0.5; }
}
