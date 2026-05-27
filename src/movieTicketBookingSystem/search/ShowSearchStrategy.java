package movieTicketBookingSystem.search;

import movieTicketBookingSystem.model.Show;

public interface ShowSearchStrategy {
    boolean matches(Show show);
}
