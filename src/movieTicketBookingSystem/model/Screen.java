package movieTicketBookingSystem.model;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private final String screenId;
    private final String theaterId;
    private final String name;
    private final List<Seat> seats;

    public Screen(String screenId, String theaterId, String name, List<Seat> seats) {
        this.screenId = screenId;
        this.theaterId = theaterId;
        this.name = name;
        this.seats = new ArrayList<>(seats);
    }

    public String getScreenId() {
        return screenId;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return List.copyOf(seats);
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }
}
