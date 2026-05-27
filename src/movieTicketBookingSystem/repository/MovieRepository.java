package movieTicketBookingSystem.repository;

import java.util.List;
import java.util.Optional;

import movieTicketBookingSystem.model.Movie;
import movieTicketBookingSystem.search.MovieSearchStrategy;

public interface MovieRepository {
    void save(Movie movie);
    List<Movie> find(MovieSearchStrategy searchStrategy);
    Optional<Movie> findById(String movieId);
}
