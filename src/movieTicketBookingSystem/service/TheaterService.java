package movieTicketBookingSystem.service;

import java.util.List;

import movieTicketBookingSystem.model.Theater;
import movieTicketBookingSystem.repository.TheaterRepository;
import movieTicketBookingSystem.search.SearchTheaterByCityStrategy;

public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public void addTheater(Theater theater) {
        theaterRepository.save(theater);
    }

    public List<Theater> findTheatersByCity(String city) {
        return theaterRepository.find(new SearchTheaterByCityStrategy(city));
    }
}
