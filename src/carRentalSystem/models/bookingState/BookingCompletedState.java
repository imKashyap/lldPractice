package carRentalSystem.models.bookingState;

import carRentalSystem.models.Booking;

public class BookingCompletedState implements BookingState {

    @Override
    public void reserveCar(Booking booking) {
        throw new IllegalStateException("Completed booking cannot be confirmed again");
    }

    @Override
    public void cancelBooking(Booking booking) {
        throw new IllegalStateException("Completed booking cannot be cancelled");
    }

    @Override
    public void completeBooking(Booking booking) {
        throw new IllegalStateException("Booking is already completed");
    }
}
