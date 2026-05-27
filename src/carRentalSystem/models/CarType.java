package carRentalSystem.models;

public enum CarType {
    HATCHBACK(2000),
    MICRO(2500),
    PICKUP(4000),
    SEDAN(4500),
    OFFROAD(5000),
    SPORT(5500),
    SUV(6000);

    private final int baseValue;

    CarType(int baseValue) {
        this.baseValue = baseValue;
    }

    public int getBaseValue() {
        return this.baseValue;
    }
}
