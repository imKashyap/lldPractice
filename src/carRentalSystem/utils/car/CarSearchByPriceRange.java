package carRentalSystem.utils.car;

import java.util.List;

import carRentalSystem.models.Car;

public class CarSearchByPriceRange implements CarSearchStrategy {
    private final double minAmount;
    private final double maxAmount;

    public CarSearchByPriceRange(double minAmount, double maxAmount) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    @Override
    public List<Car> search(List<Car> cars) {
        return cars.stream()
                .filter(car -> car.getCarType().getBaseValue() >= minAmount)
                .filter(car -> car.getCarType().getBaseValue() <= maxAmount)
                .toList();
    }
}
