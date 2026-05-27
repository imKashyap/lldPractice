package movieTicketBookingSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import movieTicketBookingSystem.model.Theater;
import movieTicketBookingSystem.repository.TheaterRepository;
import movieTicketBookingSystem.search.TheaterSearchStrategy;

public class InMemoryTheaterRepository implements TheaterRepository {
    private final Map<String, Theater> theatersById = new ConcurrentHashMap<>();

    @Override
    public void save(Theater theater) {
        theatersById.put(theater.getTheaterId(), theater);
    }

    @Override
    public List<Theater> find(TheaterSearchStrategy searchStrategy) {
        return List.copyOf(theatersById.values()).stream()
                .filter(searchStrategy::matches)
                .toList();
    }

    @Override
    public Optional<Theater> findById(String theaterId) {
        return Optional.ofNullable(theatersById.get(theaterId));
    }
}
