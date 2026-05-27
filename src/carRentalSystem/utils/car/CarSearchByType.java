package carRentalSystem.utils.car;

import java.util.List;

import carRentalSystem.models.Car;
import carRentalSystem.models.CarType;

public class CarSearchByType implements CarSearchStrategy {
    private final CarType type;

    public CarSearchByType(CarType type) {
        this.type = type;
    }

    @Override
    public List<Car> search(List<Car> cars) {
        return List.copyOf(cars.stream().filter(car -> car.getCarType() == type).toList());
    }

}
