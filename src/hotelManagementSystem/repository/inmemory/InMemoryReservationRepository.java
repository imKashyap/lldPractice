package hotelManagementSystem.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import hotelManagementSystem.models.guest.Guest;
import hotelManagementSystem.models.reservation.Reservation;
import hotelManagementSystem.models.reservation.ReservationStatus;
import hotelManagementSystem.repository.ReservationRepository;

public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<String, Reservation> reservationsById = new ConcurrentHashMap<>();

    @Override
    public void save(Reservation reservation) {
        reservationsById.put(reservation.getReservationId(), reservation);
    }

    @Override
    public Optional<Reservation> findById(String reservationId) {
        return Optional.ofNullable(reservationsById.get(reservationId));
    }

    @Override
    public Optional<Reservation> findActiveByGuest(Guest guest) {
        return reservationsById.values().stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.ACTIVE)
                .filter(reservation -> reservation.getGuests().stream()
                        .anyMatch(existingGuest -> existingGuest.getGuestId().equals(guest.getGuestId())))
                .findFirst();
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservationsById.values());
    }
}
