package movieTicketBookingSystem.search;

import movieTicketBookingSystem.model.Movie;

public class SearchMovieByLanguageStrategy implements MovieSearchStrategy {
    private final String language;

    public SearchMovieByLanguageStrategy(String language) {
        this.language = language;
    }

    @Override
    public boolean matches(Movie movie) {
        return movie.getLanguage().equalsIgnoreCase(language);
    }
}
