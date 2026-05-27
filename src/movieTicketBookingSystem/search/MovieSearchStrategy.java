package movieTicketBookingSystem.search;

import movieTicketBookingSystem.model.Movie;

public interface MovieSearchStrategy {
    boolean matches(Movie movie);
}
