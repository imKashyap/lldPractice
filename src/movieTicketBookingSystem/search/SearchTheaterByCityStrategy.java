package movieTicketBookingSystem.search;

import movieTicketBookingSystem.model.Theater;

public class SearchTheaterByCityStrategy implements TheaterSearchStrategy {
    private final String city;

    public SearchTheaterByCityStrategy(String city) {
        this.city = city;
    }

    @Override
    public boolean matches(Theater theater) {
        return theater.getCity().equalsIgnoreCase(city);
    }
}
