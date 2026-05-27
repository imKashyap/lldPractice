package movieTicketBookingSystem.service;

import java.util.List;

import movieTicketBookingSystem.model.Movie;
import movieTicketBookingSystem.repository.MovieRepository;
import movieTicketBookingSystem.search.MovieSearchStrategy;

public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public List<Movie> browseMovies(MovieSearchStrategy searchStrategy) {
        return movieRepository.find(searchStrategy);
    }

    public Movie getMovieDetails(String movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));
    }
}
