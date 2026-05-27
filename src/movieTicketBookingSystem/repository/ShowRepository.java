package movieTicketBookingSystem.repository;

import java.util.List;
import java.util.Optional;

import movieTicketBookingSystem.model.Show;
import movieTicketBookingSystem.search.ShowSearchStrategy;

public interface ShowRepository {
    void save(Show show);
    List<Show> find(ShowSearchStrategy searchStrategy);
    Optional<Show> findById(String showId);
}
