package hotelManagementSystem.services;

import java.util.List;

import hotelManagementSystem.models.reservation.Reservation;
import hotelManagementSystem.models.reservation.ReservationStatus;
import hotelManagementSystem.models.room.Room;
import hotelManagementSystem.models.room.RoomStatus;
import hotelManagementSystem.repository.ReservationRepository;
import hotelManagementSystem.repository.RoomRepository;

public class ReportService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public ReportService(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public String generateOccupancyReport() {
        List<Room> rooms = roomRepository.findAll();
        long occupiedRooms = rooms.stream()
                .filter(room -> room.getStatus() == RoomStatus.OCCUPIED)
                .count();
        double occupancyRate = rooms.isEmpty() ? 0 : (occupiedRooms * 100.0) / rooms.size();
        return "Occupancy Report: " + occupiedRooms + "/" + rooms.size() + " rooms occupied ("
                + String.format("%.2f", occupancyRate) + "%)";
    }

    public String generateReservationReport() {
        List<Reservation> reservations = reservationRepository.findAll();
        long active = reservations.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.ACTIVE)
                .count();
        long cancelled = reservations.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CANCELLED)
                .count();
        long expired = reservations.stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.EXPIRED)
                .count();
        return "Reservation Report: active=" + active + ", cancelled=" + cancelled + ", expired=" + expired;
    }
}
