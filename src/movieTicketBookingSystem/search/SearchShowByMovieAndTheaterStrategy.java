package movieTicketBookingSystem.search;

import movieTicketBookingSystem.model.Show;

public class SearchShowByMovieAndTheaterStrategy implements ShowSearchStrategy {
    private final String movieId;
    private final String theaterId;

    public SearchShowByMovieAndTheaterStrategy(String movieId, String theaterId) {
        this.movieId = movieId;
        this.theaterId = theaterId;
    }

    @Override
    public boolean matches(Show show) {
        return show.getMovieId().equals(movieId) && show.getTheaterId().equals(theaterId);
    }
}
