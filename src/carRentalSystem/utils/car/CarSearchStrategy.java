package carRentalSystem.utils.car;

import java.util.List;

import carRentalSystem.models.Car;

public interface CarSearchStrategy {

    List<Car> search(List<Car> cars);

}
