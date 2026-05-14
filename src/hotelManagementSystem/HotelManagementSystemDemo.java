package hotelManagementSystem;

import java.time.LocalDate;
import java.util.List;

import hotelManagementSystem.models.guest.Gender;
import hotelManagementSystem.models.guest.Guest;
import hotelManagementSystem.models.reservation.Bill;
import hotelManagementSystem.models.reservation.Reservation;
import hotelManagementSystem.models.room.KeyCard;
import hotelManagementSystem.models.room.RoomFactory;
import hotelManagementSystem.models.room.RoomType;
import hotelManagementSystem.repository.ReservationRepository;
import hotelManagementSystem.repository.RoomRepository;
import hotelManagementSystem.repository.inmemory.InMemoryReservationRepository;
import hotelManagementSystem.repository.inmemory.InMemoryRoomRepository;
import hotelManagementSystem.services.CheckInService;
import hotelManagementSystem.services.ReportService;
import hotelManagementSystem.services.ReservationService;
import hotelManagementSystem.services.RoomService;

public class HotelManagementSystemDemo {
    public static void main(String[] args) {
        RoomRepository roomRepository = new InMemoryRoomRepository();
        ReservationRepository reservationRepository = new InMemoryReservationRepository();

        RoomService roomService = new RoomService(roomRepository);
        ReservationService reservationService = new ReservationService(roomRepository, reservationRepository);
        CheckInService checkInService = new CheckInService(roomRepository);
        ReportService reportService = new ReportService(roomRepository, reservationRepository);

        HotelManagementSystem hotel = new HotelManagementSystem(
                roomService,
                reservationService,
                checkInService,
                reportService);

        hotel.getRoomService().addRoom(RoomFactory.createRoom("101", RoomType.STANDARD));
        hotel.getRoomService().addRoom(RoomFactory.createRoom("201", RoomType.DELUXE));
        hotel.getRoomService().addRoom(RoomFactory.createRoom("301", RoomType.SUITE));

        Guest guest = new Guest("G-1", "Kashyap", "AADHAR-1111", Gender.MALE, 28, "kashyap@example.com");
        Guest coGuest = new Guest("G-2", "Aarav", "AADHAR-2222", Gender.MALE, 26, "aarav@example.com");

        System.out.println("=== Reservation Flow ===");
        Reservation reservation = hotel.getReservationService().reserveRoom(
                List.of(guest, coGuest),
                RoomType.DELUXE,
                LocalDate.now(),
                LocalDate.now().plusDays(2));
        System.out.println("Reservation created: " + reservation.getReservationId());
        System.out.println("Assigned room: " + reservation.getRoom());

        System.out.println("\n=== Check-In Flow ===");
        KeyCard keyCard = hotel.getCheckInService().checkIn(reservation);
        System.out.println("Checked in to room: " + reservation.getRoom().getRoomId());
        System.out.println("Key card issued: " + keyCard.getCardId() + " | status=" + keyCard.getCardStatus());

        System.out.println("\n=== Reports During Stay ===");
        System.out.println(hotel.getReportService().generateOccupancyReport());
        System.out.println(hotel.getReportService().generateReservationReport());

        System.out.println("\n=== Check-Out Flow ===");
        Bill bill = hotel.getCheckInService().checkOut(reservation);
        System.out.println("Bill generated: " + bill.getBillId() + " | amount=Rs " + bill.getAmount());
        System.out.println("Room after checkout: " + reservation.getRoom());

        System.out.println("\n=== Reports After Checkout ===");
        System.out.println(hotel.getReportService().generateOccupancyReport());
        System.out.println(hotel.getReportService().generateReservationReport());

        System.out.println("\n=== Cancellation Flow ===");
        Reservation suiteReservation = hotel.getReservationService().reserveRoom(
                List.of(guest),
                RoomType.SUITE,
                LocalDate.now().plusDays(7),
                LocalDate.now().plusDays(9));
        hotel.getReservationService().cancelReservation(suiteReservation.getReservationId());
        System.out.println("Cancelled reservation: " + suiteReservation.getReservationId());
        System.out.println(hotel.getReportService().generateReservationReport());
    }
}
