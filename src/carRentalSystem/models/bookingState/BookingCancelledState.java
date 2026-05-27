package carRentalSystem.models.bookingState;

import carRentalSystem.models.Booking;

public class BookingCancelledState implements BookingState {

    @Override
    public void reserveCar(Booking booking) {
        throw new IllegalStateException("Cancelled booking cannot be confirmed");
    }

    @Override
    public void cancelBooking(Booking booking) {
        throw new IllegalStateException("Booking is already cancelled");
    }

    @Override
    public void completeBooking(Booking booking) {
        throw new IllegalStateException("Cancelled booking cannot be completed");
    }
}
