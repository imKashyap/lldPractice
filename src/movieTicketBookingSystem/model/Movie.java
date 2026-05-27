package movieTicketBookingSystem.model;

import java.time.LocalDate;
import java.util.List;

public class Movie {
    private final String movieId;
    private final String title;
    private final String synopsis;
    private final List<String> cast;
    private final int durationInMinutes;
    private final double rating;
    private final String genre;
    private final String language;
    private final LocalDate releaseDate;
    private MovieStatus status;

    public Movie(
            String movieId,
            String title,
            String synopsis,
            List<String> cast,
            int durationInMinutes,
            double rating,
            String genre,
            String language,
            LocalDate releaseDate,
            MovieStatus status) {
        this.movieId = movieId;
        this.title = title;
        this.synopsis = synopsis;
        this.cast = List.copyOf(cast);
        this.durationInMinutes = durationInMinutes;
        this.rating = rating;
        this.genre = genre;
        this.language = language;
        this.releaseDate = releaseDate;
        this.status = status;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public List<String> getCast() {
        return cast;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public double getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public MovieStatus getStatus() {
        return status;
    }

    public void setStatus(MovieStatus status) {
        this.status = status;
    }
}
