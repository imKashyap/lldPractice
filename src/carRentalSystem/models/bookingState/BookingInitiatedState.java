package carRentalSystem.models.bookingState;

import carRentalSystem.models.Booking;
import carRentalSystem.models.BookingStatus;

public class BookingInitiatedState implements BookingState {

    @Override
    public void reserveCar(Booking booking) {
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setState(new BookingConfirmedState());
    }

    @Override
    public void cancelBooking(Booking booking) {
        booking.setStatus(BookingStatus.CANCELLED);
        booking.setState(new BookingCancelledState());
    }

    @Override
    public void completeBooking(Booking booking) {
        throw new IllegalStateException("Initiated booking must be confirmed before completion");
    }

}
