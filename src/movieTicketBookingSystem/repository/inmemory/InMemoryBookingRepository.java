package movieTicketBookingSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import movieTicketBookingSystem.model.Booking;
import movieTicketBookingSystem.repository.BookingRepository;

public class InMemoryBookingRepository implements BookingRepository {
    private final Map<String, Booking> bookingsById = new ConcurrentHashMap<>();

    @Override
    public void save(Booking booking) {
        bookingsById.put(booking.getBookingId(), booking);
    }

    @Override
    public Optional<Booking> findById(String bookingId) {
        return Optional.ofNullable(bookingsById.get(bookingId));
    }

    @Override
    public List<Booking> findByUserId(String userId) {
        return List.copyOf(bookingsById.values()).stream()
                .filter(booking -> booking.getUserId().equals(userId))
                .toList();
    }
}
