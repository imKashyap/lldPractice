package movieTicketBookingSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import movieTicketBookingSystem.model.Movie;
import movieTicketBookingSystem.repository.MovieRepository;
import movieTicketBookingSystem.search.MovieSearchStrategy;

public class InMemoryMovieRepository implements MovieRepository {
    private final Map<String, Movie> moviesById = new ConcurrentHashMap<>();

    @Override
    public void save(Movie movie) {
        moviesById.put(movie.getMovieId(), movie);
    }

    @Override
    public List<Movie> find(MovieSearchStrategy searchStrategy) {
        return List.copyOf(moviesById.values()).stream()
                .filter(searchStrategy::matches)
                .toList();
    }

    @Override
    public Optional<Movie> findById(String movieId) {
        return Optional.ofNullable(moviesById.get(movieId));
    }
}
