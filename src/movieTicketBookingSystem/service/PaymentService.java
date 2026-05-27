package movieTicketBookingSystem.service;

import java.util.UUID;

import movieTicketBookingSystem.model.Booking;
import movieTicketBookingSystem.model.BookingStatus;
import movieTicketBookingSystem.model.Payment;
import movieTicketBookingSystem.model.PaymentMethod;
import movieTicketBookingSystem.model.PaymentStatus;
import movieTicketBookingSystem.repository.BookingRepository;
import movieTicketBookingSystem.repository.PaymentRepository;

public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentService(PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    public Payment pay(String bookingId, PaymentMethod paymentMethod) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        if (booking.getStatus() != BookingStatus.INITIATED) {
            throw new IllegalStateException("Booking cannot accept payment in current status");
        }

        Payment payment = new Payment(
                "PAY-" + UUID.randomUUID(),
                bookingId,
                booking.getAmount(),
                paymentMethod,
                PaymentStatus.SUCCESS,
                "TXN-" + UUID.randomUUID());
        paymentRepository.save(payment);
        return payment;
    }

    public Payment refund(String bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);
        return payment;
    }
}
