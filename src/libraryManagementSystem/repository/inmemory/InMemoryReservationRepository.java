package libraryManagementSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import libraryManagementSystem.model.reservation.Reservation;
import libraryManagementSystem.repository.ReservationRepository;

public class InMemoryReservationRepository implements ReservationRepository {
    private final Map<String, Reservation> reservationsById = new ConcurrentHashMap<>();

    @Override
    public void save(Reservation reservation) {
        reservationsById.put(reservation.getReservationId(), reservation);
    }

    @Override
    public List<Reservation> findByBookIsbn(String isbn) {
        return reservationsById.values().stream()
                .filter(reservation -> reservation.getBook().getIsbn().equals(isbn))
                .toList();
    }

    @Override
    public List<Reservation> findByMemberId(String memberId) {
        return reservationsById.values().stream()
                .filter(reservation -> reservation.getMember().getAccount().getUsername().equals(memberId))
                .toList();
    }
}
