package movieTicketBookingSystem.model;

import java.time.LocalDateTime;
import java.util.List;

public class Booking {
    private final String bookingId;
    private final String userId;
    private final String showId;
    private final List<String> bookedSeats;
    private final double amount;
    private final double discountAmount;
    private BookingStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;

    public Booking(
            String bookingId,
            String userId,
            String showId,
            List<String> bookedSeats,
            double amount,
            double discountAmount,
            BookingStatus status,
            LocalDateTime createdAt,
            LocalDateTime expiresAt) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showId = showId;
        this.bookedSeats = List.copyOf(bookedSeats);
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.status = status;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public String getShowId() {
        return showId;
    }

    public List<String> getBookedSeats() {
        return bookedSeats;
    }

    public double getAmount() {
        return amount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public synchronized BookingStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired(LocalDateTime now) {
        return !expiresAt.isAfter(now);
    }
}
