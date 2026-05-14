package hotelManagementSystem.models.reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hotelManagementSystem.models.guest.Guest;
import hotelManagementSystem.models.room.Room;

public class Reservation {
    private final String reservationId;
    private final List<Guest> guests;
    private final Room room;
    private volatile ReservationStatus status;
    private final LocalDate validFrom;
    private final LocalDate validTo;

    public Reservation(String reservationId, List<Guest> guests, Room room) {
        this(reservationId, guests, room, LocalDate.now(), LocalDate.now().plusDays(1));
    }

    public Reservation(String reservationId, List<Guest> guests, Room room, LocalDate validFrom, LocalDate validTo) {
        if (guests == null || guests.isEmpty()) {
            throw new IllegalArgumentException("Reservation must have at least one guest");
        }
        if (validFrom == null || validTo == null || !validTo.isAfter(validFrom)) {
            throw new IllegalArgumentException("Reservation checkout date must be after checkin date");
        }
        this.reservationId = reservationId;
        this.guests = new ArrayList<>(guests);
        this.room = room;
        this.status = ReservationStatus.ACTIVE;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public String getReservationId() {
        return reservationId;
    }

    public List<Guest> getGuest() {
        return new ArrayList<>(guests);
    }

    public Room getRoom() {
        return room;
    }

    public synchronized ReservationStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public List<Guest> getGuests() {
        return new ArrayList<>(guests);
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    @Override
    public String toString() {
        return "Reservation [reservationId=" + reservationId + ", guests=" + guests + ", room=" + room + ", validFrom="
                + validFrom + ", validTo=" + validTo + "]";
    }

}
