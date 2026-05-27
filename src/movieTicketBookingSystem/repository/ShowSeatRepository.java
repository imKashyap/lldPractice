package movieTicketBookingSystem.repository;

import java.util.List;

import movieTicketBookingSystem.model.ShowSeat;

public interface ShowSeatRepository {
    void save(ShowSeat showSeat);
    List<ShowSeat> findByShowId(String showId);
    List<ShowSeat> findByShowIdAndSeatIds(String showId, List<String> seatIds);
}
