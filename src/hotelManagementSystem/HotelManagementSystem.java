package hotelManagementSystem;

import hotelManagementSystem.services.CheckInService;
import hotelManagementSystem.services.ReportService;
import hotelManagementSystem.services.ReservationService;
import hotelManagementSystem.services.RoomService;

public class HotelManagementSystem {
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final CheckInService checkInService;
    private final ReportService reportService;

    public HotelManagementSystem(
            RoomService roomService,
            ReservationService reservationService,
            CheckInService checkInService,
            ReportService reportService) {
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.checkInService = checkInService;
        this.reportService = reportService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public CheckInService getCheckInService() {
        return checkInService;
    }

    public ReportService getReportService() {
        return reportService;
    }
}
