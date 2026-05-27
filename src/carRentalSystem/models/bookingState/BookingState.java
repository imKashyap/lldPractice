package carRentalSystem.models.bookingState;

import carRentalSystem.models.Booking;

public interface BookingState {
    void reserveCar(Booking booking);

    void cancelBooking(Booking booking);

    void completeBooking(Booking booking);
}
