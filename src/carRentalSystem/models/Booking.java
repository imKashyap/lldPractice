package carRentalSystem.models;

import java.time.LocalDateTime;

import carRentalSystem.models.bookingState.BookingInitiatedState;
import carRentalSystem.models.bookingState.BookingState;

public class Booking {
    private final String id;
    private final String carId;
    private final String customerId;
    private final LocalDateTime validFrom;
    private final LocalDateTime validTo;
    private BookingStatus status;
    private BookingState state;

    public Booking(String id, String carId, String customerId, LocalDateTime validFrom, LocalDateTime validTo) {
        this.id = id;
        this.carId = carId;
        this.customerId = customerId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = BookingStatus.INITIATED;
        this.state = new BookingInitiatedState();
    }

    public String getId() {
        return id;
    }

    public String getCarId() {
        return carId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public BookingState getState() {
        return state;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setState(BookingState state) {
        this.state = state;
    }

    public void reserveCar() {
        state.reserveCar(this);
    }

    public void cancelBooking() {
        state.cancelBooking(this);
    }

    public void completeBooking() {
        state.completeBooking(this);
    }

    public boolean overlaps(LocalDateTime from, LocalDateTime to) {
        return validFrom.isBefore(to) && from.isBefore(validTo);
    }
}
