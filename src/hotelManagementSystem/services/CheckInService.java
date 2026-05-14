package hotelManagementSystem.services;

import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import hotelManagementSystem.models.reservation.Bill;
import hotelManagementSystem.models.reservation.Reservation;
import hotelManagementSystem.models.reservation.ReservationStatus;
import hotelManagementSystem.models.room.KeyCard;
import hotelManagementSystem.models.room.RoomStatus;
import hotelManagementSystem.repository.RoomRepository;

public class CheckInService {
    private final RoomRepository roomRepository;
    private final Map<String, KeyCard> activeKeyCardsByReservationId = new ConcurrentHashMap<>();

    public CheckInService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public KeyCard checkIn(Reservation reservation) {
        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Only active reservations can be checked in");
        }
        if (reservation.getRoom().getStatus() != RoomStatus.BOOKED) {
            throw new IllegalStateException("Room must be booked before check-in");
        }
        reservation.getRoom().setStatus(RoomStatus.OCCUPIED);
        roomRepository.save(reservation.getRoom());

        KeyCard keyCard = new KeyCard(
                UUID.randomUUID().toString(),
                reservation.getValidFrom().atTime(12, 0),
                reservation.getValidTo().atTime(11, 0));
        keyCard.addRoomAccess(reservation.getRoom());
        activeKeyCardsByReservationId.put(reservation.getReservationId(), keyCard);
        return keyCard;
    }

    public Bill checkOut(Reservation reservation) {
        if (reservation.getRoom().getStatus() != RoomStatus.OCCUPIED) {
            throw new IllegalStateException("Reservation is not checked in");
        }

        KeyCard keyCard = activeKeyCardsByReservationId.remove(reservation.getReservationId());
        if (keyCard != null) {
            keyCard.removeRoomAccess();
        }

        long nights = Math.max(1, ChronoUnit.DAYS.between(reservation.getValidFrom(), reservation.getValidTo()));
        double amount = nights * reservation.getRoom().getRoomData().getBasePrice();

        reservation.setStatus(ReservationStatus.EXPIRED);
        reservation.getRoom().setStatus(RoomStatus.AVAILABLE);
        roomRepository.save(reservation.getRoom());
        return new Bill(reservation, amount);
    }

    public KeyCard getKeyCard(Reservation reservation) {
        return activeKeyCardsByReservationId.get(reservation.getReservationId());
    }
}
