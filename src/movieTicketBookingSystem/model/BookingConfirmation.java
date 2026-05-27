package movieTicketBookingSystem.model;

import java.time.LocalDateTime;

public class BookingConfirmation {
    private final String confirmationId;
    private final String bookingId;
    private final String message;
    private final LocalDateTime sentAt;

    public BookingConfirmation(String confirmationId, String bookingId, String message, LocalDateTime sentAt) {
        this.confirmationId = confirmationId;
        this.bookingId = bookingId;
        this.message = message;
        this.sentAt = sentAt;
    }

    public String getConfirmationId() {
        return confirmationId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}
