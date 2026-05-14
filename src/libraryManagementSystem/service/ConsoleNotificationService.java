package libraryManagementSystem.service;

import libraryManagementSystem.model.person.Member;
import libraryManagementSystem.model.reservation.Reservation;

public class ConsoleNotificationService implements NotificationService {
    @Override
    public void notifyDueDate(Member member, String message) {
        System.out.println("[Notification] To " + member.getAccount().getUsername() + ": " + message);
    }

    @Override
    public void notifyReservationAvailable(Reservation reservation, String message) {
        System.out.println(
                "[Notification] Reservation " + reservation.getReservationId()
                        + " for " + reservation.getMember().getAccount().getUsername()
                        + ": " + message);
    }
}
