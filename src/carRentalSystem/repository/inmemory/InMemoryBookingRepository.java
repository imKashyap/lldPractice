package carRentalSystem.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import carRentalSystem.models.Booking;
import carRentalSystem.repository.BookingRepository;

public class InMemoryBookingRepository implements BookingRepository {
    private final Map<String, Booking> bookingsById = new ConcurrentHashMap<>();

    @Override
    public void save(Booking booking) {
        bookingsById.put(booking.getId(), booking);
    }

    @Override
    public Optional<Booking> findById(String id) {
        return Optional.ofNullable(bookingsById.get(id));
    }

    @Override
    public List<Booking> findAll() {
        return new ArrayList<>(bookingsById.values());
    }

    @Override
    public List<Booking> findByCarId(String carId) {
        return bookingsById.values().stream()
                .filter(booking -> booking.getCarId().equals(carId))
                .toList();
    }
}
