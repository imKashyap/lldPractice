package movieTicketBookingSystem.repository;

import java.util.Optional;

import movieTicketBookingSystem.model.Payment;

public interface PaymentRepository {
    void save(Payment payment);
    Optional<Payment> findByBookingId(String bookingId);
}
