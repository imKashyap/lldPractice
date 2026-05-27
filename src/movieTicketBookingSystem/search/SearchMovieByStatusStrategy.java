package movieTicketBookingSystem.search;

import movieTicketBookingSystem.model.Movie;
import movieTicketBookingSystem.model.MovieStatus;

public class SearchMovieByStatusStrategy implements MovieSearchStrategy {
    private final MovieStatus status;

    public SearchMovieByStatusStrategy(MovieStatus status) {
        this.status = status;
    }

    @Override
    public boolean matches(Movie movie) {
        return movie.getStatus() == status;
    }
}
