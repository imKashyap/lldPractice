package movieTicketBookingSystem.search;

import movieTicketBookingSystem.model.Movie;

public class SearchMovieByGenreStrategy implements MovieSearchStrategy {
    private final String genre;

    public SearchMovieByGenreStrategy(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean matches(Movie movie) {
        return movie.getGenre().equalsIgnoreCase(genre);
    }
}
