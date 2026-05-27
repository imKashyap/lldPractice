package movieTicketBookingSystem.model;

import java.time.LocalDateTime;

public class ShowSeat {
    private final String showSeatId;
    private final String showId;
    private final String seatId;
    private final double price;
    private SeatBookingStatus status;
    private String lockedByUserId;
    private LocalDateTime lockExpiresAt;

    public ShowSeat(String showSeatId, String showId, String seatId, double price, SeatBookingStatus status) {
        this.showSeatId = showSeatId;
        this.showId = showId;
        this.seatId = seatId;
        this.price = price;
        this.status = status;
    }

    public String getShowSeatId() {
        return showSeatId;
    }

    public String getShowId() {
        return showId;
    }

    public String getSeatId() {
        return seatId;
    }

    public double getPrice() {
        return price;
    }

    public synchronized SeatBookingStatus getStatus() {
        return status;
    }

    public synchronized String getLockedByUserId() {
        return lockedByUserId;
    }

    public synchronized LocalDateTime getLockExpiresAt() {
        return lockExpiresAt;
    }

    public synchronized boolean isLockExpired(LocalDateTime now) {
        return status == SeatBookingStatus.LOCKED && lockExpiresAt != null && !lockExpiresAt.isAfter(now);
    }

    public synchronized boolean hasValidLockFor(String userId, LocalDateTime now) {
        return status == SeatBookingStatus.LOCKED
                && userId.equals(lockedByUserId)
                && lockExpiresAt != null
                && lockExpiresAt.isAfter(now);
    }

    public synchronized void lock(String userId, LocalDateTime expiresAt) {
        this.status = SeatBookingStatus.LOCKED;
        this.lockedByUserId = userId;
        this.lockExpiresAt = expiresAt;
    }

    public synchronized void releaseLock() {
        if (status == SeatBookingStatus.LOCKED) {
            this.status = SeatBookingStatus.AVAILABLE;
            this.lockedByUserId = null;
            this.lockExpiresAt = null;
        }
    }

    public synchronized void markBooked() {
        this.status = SeatBookingStatus.BOOKED;
        this.lockedByUserId = null;
        this.lockExpiresAt = null;
    }

    public synchronized void markAvailable() {
        this.status = SeatBookingStatus.AVAILABLE;
        this.lockedByUserId = null;
        this.lockExpiresAt = null;
    }

    public synchronized void markBlocked() {
        this.status = SeatBookingStatus.BLOCKED;
        this.lockedByUserId = null;
        this.lockExpiresAt = null;
    }
}
