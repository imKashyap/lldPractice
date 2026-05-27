package movieTicketBookingSystem.search;

import movieTicketBookingSystem.model.Show;

public class SearchShowByMovieStrategy implements ShowSearchStrategy {
    private final String movieId;

    public SearchShowByMovieStrategy(String movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean matches(Show show) {
        return show.getMovieId().equals(movieId);
    }
}
