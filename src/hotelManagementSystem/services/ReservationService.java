package hotelManagementSystem.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import hotelManagementSystem.models.guest.Guest;
import hotelManagementSystem.models.reservation.Reservation;
import hotelManagementSystem.models.reservation.ReservationStatus;
import hotelManagementSystem.models.room.Room;
import hotelManagementSystem.models.room.RoomStatus;
import hotelManagementSystem.models.room.RoomType;
import hotelManagementSystem.repository.ReservationRepository;
import hotelManagementSystem.repository.RoomRepository;

public class ReservationService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public Reservation reserveRoom(List<Guest> guests, RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        Room room = roomRepository.findAvailableByRoomType(roomType)
                .orElseThrow(() -> new IllegalStateException("No available " + roomType + " room found"));
        int guestCount = guests == null ? 0 : guests.size();
        if (guestCount > room.getRoomData().getCapacity()) {
            throw new IllegalArgumentException("Guest count exceeds room capacity");
        }

        Reservation reservation = new Reservation(UUID.randomUUID().toString(), guests, room, checkInDate, checkOutDate);
        room.setStatus(RoomStatus.BOOKED);
        roomRepository.save(room);
        reservationRepository.save(reservation);
        return reservation;
    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + reservationId));
        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Only active reservations can be cancelled");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.getRoom().setStatus(RoomStatus.AVAILABLE);
        roomRepository.save(reservation.getRoom());
        reservationRepository.save(reservation);
    }

    public Reservation findReservation(String reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + reservationId));
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}
