package movieTicketBookingSystem.repository.inmemory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import movieTicketBookingSystem.model.Payment;
import movieTicketBookingSystem.repository.PaymentRepository;

public class InMemoryPaymentRepository implements PaymentRepository {
    private final Map<String, Payment> paymentsByBookingId = new ConcurrentHashMap<>();

    @Override
    public void save(Payment payment) {
        paymentsByBookingId.put(payment.getBookingId(), payment);
    }

    @Override
    public Optional<Payment> findByBookingId(String bookingId) {
        return Optional.ofNullable(paymentsByBookingId.get(bookingId));
    }
}
