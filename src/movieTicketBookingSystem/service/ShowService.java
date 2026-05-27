package movieTicketBookingSystem.service;

import java.util.List;
import java.util.Set;

import movieTicketBookingSystem.model.Seat;
import movieTicketBookingSystem.model.SeatBookingStatus;
import movieTicketBookingSystem.model.SeatType;
import movieTicketBookingSystem.model.Show;
import movieTicketBookingSystem.model.ShowSeat;
import movieTicketBookingSystem.model.Theater;
import movieTicketBookingSystem.repository.ShowRepository;
import movieTicketBookingSystem.repository.ShowSeatRepository;
import movieTicketBookingSystem.repository.TheaterRepository;
import movieTicketBookingSystem.search.SearchShowByMovieStrategy;

public class ShowService {
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final TheaterRepository theaterRepository;

    public ShowService(
            ShowRepository showRepository,
            ShowSeatRepository showSeatRepository,
            TheaterRepository theaterRepository) {
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.theaterRepository = theaterRepository;
    }

    public void createShow(Show show, List<Seat> seats) {
        showRepository.save(show);
        for (Seat seat : seats) {
            showSeatRepository.save(new ShowSeat(
                    show.getShowId() + "-" + seat.getSeatId(),
                    show.getShowId(),
                    seat.getSeatId(),
                    priceFor(show.getBasePrice(), seat.getType()),
                    SeatBookingStatus.AVAILABLE));
        }
    }

    public List<Show> findShowsByMovie(String movieId) {
        return showRepository.find(new SearchShowByMovieStrategy(movieId));
    }

    public List<Show> findShowsByMovieAndCity(String movieId, String city) {
        Set<String> theaterIdsInCity = theaterRepository.find(theater -> theater.getCity().equalsIgnoreCase(city))
                .stream()
                .map(Theater::getTheaterId)
                .collect(java.util.stream.Collectors.toSet());
        return findShowsByMovie(movieId).stream()
                .filter(show -> theaterIdsInCity.contains(show.getTheaterId()))
                .toList();
    }

    public List<ShowSeat> getSeatAvailability(String showId) {
        return showSeatRepository.findByShowId(showId);
    }

    private double priceFor(double basePrice, SeatType seatType) {
        return switch (seatType) {
            case REGULAR -> basePrice;
            case PREMIUM -> basePrice * 1.3;
            case RECLINER -> basePrice * 1.8;
        };
    }
}
