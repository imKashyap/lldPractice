package carRentalSystem.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import carRentalSystem.models.Car;
import carRentalSystem.repository.CarRepository;
import carRentalSystem.utils.car.CarSearchStrategy;

public class InMemoryCarRepository implements CarRepository {
    private final Map<String, Car> carsById = new ConcurrentHashMap<>();

    @Override
    public void save(Car car) {
        carsById.put(car.getId(), car);
    }

    @Override
    public Optional<Car> findById(String id) {
        return Optional.ofNullable(carsById.get(id));
    }

    @Override
    public List<Car> search(CarSearchStrategy strategy) {
        return strategy.search(findAll());
    }

    @Override
    public List<Car> findAll() {
        return new ArrayList<>(carsById.values());
    }
}
