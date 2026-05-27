package movieTicketBookingSystem.repository;

import java.util.List;
import java.util.Optional;

import movieTicketBookingSystem.model.Booking;

public interface BookingRepository {
    void save(Booking booking);
    Optional<Booking> findById(String bookingId);
    List<Booking> findByUserId(String userId);
}
