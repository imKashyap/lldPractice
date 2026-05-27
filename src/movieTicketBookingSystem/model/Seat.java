package movieTicketBookingSystem.model;

public class Seat {
    private final String seatId;
    private final String screenId;
    private final String row;
    private final int number;
    private final SeatType type;

    public Seat(String seatId, String screenId, String row, int number, SeatType type) {
        this.seatId = seatId;
        this.screenId = screenId;
        this.row = row;
        this.number = number;
        this.type = type;
    }

    public String getSeatId() {
        return seatId;
    }

    public String getScreenId() {
        return screenId;
    }

    public String getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public SeatType getType() {
        return type;
    }
}
