package movieTicketBookingSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import movieTicketBookingSystem.model.Show;
import movieTicketBookingSystem.repository.ShowRepository;
import movieTicketBookingSystem.search.ShowSearchStrategy;

public class InMemoryShowRepository implements ShowRepository {
    private final Map<String, Show> showsById = new ConcurrentHashMap<>();

    @Override
    public void save(Show show) {
        showsById.put(show.getShowId(), show);
    }

    @Override
    public List<Show> find(ShowSearchStrategy searchStrategy) {
        return List.copyOf(showsById.values()).stream()
                .filter(searchStrategy::matches)
                .toList();
    }

    @Override
    public Optional<Show> findById(String showId) {
        return Optional.ofNullable(showsById.get(showId));
    }
}
