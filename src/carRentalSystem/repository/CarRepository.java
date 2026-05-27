package carRentalSystem.repository;

import java.util.List;
import java.util.Optional;

import carRentalSystem.models.Car;
import carRentalSystem.utils.car.CarSearchStrategy;

public interface CarRepository {
    void save(Car car);

    Optional<Car> findById(String id);

    List<Car> search(CarSearchStrategy strategy);

    List<Car> findAll();
}
