package coffeeVendingMachine;

public enum CupSize {
    SMALL(1.0, 0), MEDIUM(1.5, 10), LARGE(2.0, 20);

    private final double multiplier;
    private final double extraCost;

    CupSize(double multiplier, double extraCost) {
        this.multiplier = multiplier;
        this.extraCost = extraCost;
    }

    public double getMultiplier() { return multiplier; }
    public double getExtraCost() { return extraCost; }
}
