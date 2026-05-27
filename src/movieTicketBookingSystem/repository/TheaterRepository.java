package movieTicketBookingSystem.repository;

import java.util.List;
import java.util.Optional;

import movieTicketBookingSystem.model.Theater;
import movieTicketBookingSystem.search.TheaterSearchStrategy;

public interface TheaterRepository {
    void save(Theater theater);
    List<Theater> find(TheaterSearchStrategy searchStrategy);
    Optional<Theater> findById(String theaterId);
}
