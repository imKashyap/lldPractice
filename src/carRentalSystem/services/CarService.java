package carRentalSystem.services;

import java.time.LocalDateTime;
import java.util.List;

import carRentalSystem.models.BookingStatus;
import carRentalSystem.models.Car;
import carRentalSystem.models.CarType;
import carRentalSystem.repository.BookingRepository;
import carRentalSystem.repository.CarRepository;
import carRentalSystem.utils.car.CarSearchByPriceRange;
import carRentalSystem.utils.car.CarSearchByType;

public class CarService {
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;

    public CarService(CarRepository carRepository, BookingRepository bookingRepository) {
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
    }

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public Car getCar(String carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found: " + carId));
    }

    public List<Car> searchByType(CarType carType) {
        return carRepository.search(new CarSearchByType(carType));
    }

    public List<Car> searchByPriceRange(double minAmount, double maxAmount) {
        return carRepository.search(new CarSearchByPriceRange(minAmount, maxAmount));
    }

    public List<Car> searchAvailable(LocalDateTime from, LocalDateTime to) {
        validateDateRange(from, to);
        return carRepository.findAll().stream()
                .filter(car -> isAvailable(car.getId(), from, to))
                .toList();
    }

    public boolean isAvailable(String carId, LocalDateTime from, LocalDateTime to) {
        validateDateRange(from, to);
        return bookingRepository.findByCarId(carId).stream()
                .filter(booking -> booking.getStatus() == BookingStatus.CONFIRMED
                        || booking.getStatus() == BookingStatus.INITIATED)
                .noneMatch(booking -> booking.overlaps(from, to));
    }

    private void validateDateRange(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null || !from.isBefore(to)) {
            throw new IllegalArgumentException("Valid from date must be before valid to date");
        }
    }
}
