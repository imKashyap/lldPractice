package movieTicketBookingSystem.search;

import movieTicketBookingSystem.model.Theater;

public interface TheaterSearchStrategy {
    boolean matches(Theater theater);
}
