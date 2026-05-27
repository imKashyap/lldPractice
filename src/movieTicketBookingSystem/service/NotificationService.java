package movieTicketBookingSystem.service;

import java.time.LocalDateTime;
import java.util.UUID;

import movieTicketBookingSystem.model.BookingConfirmation;

public class NotificationService {
    public BookingConfirmation sendBookingConfirmation(String bookingId) {
        BookingConfirmation confirmation = new BookingConfirmation(
                "CONF-" + UUID.randomUUID(),
                bookingId,
                "Booking confirmed: " + bookingId,
                LocalDateTime.now());
        System.out.println(confirmation.getMessage());
        return confirmation;
    }

    public void sendCancellation(String bookingId) {
        System.out.println("Booking cancelled: " + bookingId);
    }
}
