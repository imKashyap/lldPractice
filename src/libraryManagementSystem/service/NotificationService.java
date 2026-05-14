package libraryManagementSystem.service;

import libraryManagementSystem.model.person.Member;
import libraryManagementSystem.model.reservation.Reservation;

public interface NotificationService {
    void notifyDueDate(Member member, String message);

    void notifyReservationAvailable(Reservation reservation, String message);
}
