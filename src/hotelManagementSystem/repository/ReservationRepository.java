package hotelManagementSystem.repository;

import java.util.List;
import java.util.Optional;

import hotelManagementSystem.models.guest.Guest;
import hotelManagementSystem.models.reservation.Reservation;

public interface ReservationRepository {
    void save(Reservation reservation);

    Optional<Reservation> findById(String reservationId);

    Optional<Reservation> findActiveByGuest(Guest guest);

    List<Reservation> findAll();
}
