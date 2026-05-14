package libraryManagementSystem.repository;

import java.util.List;

import libraryManagementSystem.model.reservation.Reservation;

public interface ReservationRepository {
    void save(Reservation reservation);

    List<Reservation> findByBookIsbn(String isbn);

    List<Reservation> findByMemberId(String memberId);
}
