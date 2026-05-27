package carRentalSystem.repository;

import java.util.List;
import java.util.Optional;

import carRentalSystem.models.Booking;

public interface BookingRepository {
    void save(Booking booking);

    Optional<Booking> findById(String id);

    List<Booking> findAll();

    List<Booking> findByCarId(String carId);
}
