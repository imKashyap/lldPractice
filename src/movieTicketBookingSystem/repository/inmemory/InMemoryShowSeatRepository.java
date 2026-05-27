package movieTicketBookingSystem.repository.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import movieTicketBookingSystem.model.ShowSeat;
import movieTicketBookingSystem.repository.ShowSeatRepository;

public class InMemoryShowSeatRepository implements ShowSeatRepository {
    private final Map<String, ShowSeat> showSeatsById = new ConcurrentHashMap<>();

    @Override
    public void save(ShowSeat showSeat) {
        showSeatsById.put(showSeat.getShowSeatId(), showSeat);
    }

    @Override
    public List<ShowSeat> findByShowId(String showId) {
        return List.copyOf(showSeatsById.values()).stream()
                .filter(showSeat -> showSeat.getShowId().equals(showId))
                .toList();
    }

    @Override
    public List<ShowSeat> findByShowIdAndSeatIds(String showId, List<String> seatIds) {
        Set<String> requestedSeatIds = Set.copyOf(seatIds);
        return findByShowId(showId).stream()
                .filter(showSeat -> requestedSeatIds.contains(showSeat.getSeatId()))
                .toList();
    }
}
