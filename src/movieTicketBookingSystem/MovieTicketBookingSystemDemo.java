package movieTicketBookingSystem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import movieTicketBookingSystem.model.Booking;
import movieTicketBookingSystem.model.Coupon;
import movieTicketBookingSystem.model.DiscountType;
import movieTicketBookingSystem.model.Movie;
import movieTicketBookingSystem.model.MovieStatus;
import movieTicketBookingSystem.model.Payment;
import movieTicketBookingSystem.model.PaymentMethod;
import movieTicketBookingSystem.model.Screen;
import movieTicketBookingSystem.model.Seat;
import movieTicketBookingSystem.model.SeatBookingStatus;
import movieTicketBookingSystem.model.SeatType;
import movieTicketBookingSystem.model.Show;
import movieTicketBookingSystem.model.ShowStatus;
import movieTicketBookingSystem.model.Theater;
import movieTicketBookingSystem.repository.BookingRepository;
import movieTicketBookingSystem.repository.CouponRepository;
import movieTicketBookingSystem.repository.MovieRepository;
import movieTicketBookingSystem.repository.PaymentRepository;
import movieTicketBookingSystem.repository.ShowRepository;
import movieTicketBookingSystem.repository.ShowSeatRepository;
import movieTicketBookingSystem.repository.TheaterRepository;
import movieTicketBookingSystem.repository.inmemory.InMemoryBookingRepository;
import movieTicketBookingSystem.repository.inmemory.InMemoryCouponRepository;
import movieTicketBookingSystem.repository.inmemory.InMemoryMovieRepository;
import movieTicketBookingSystem.repository.inmemory.InMemoryPaymentRepository;
import movieTicketBookingSystem.repository.inmemory.InMemoryShowRepository;
import movieTicketBookingSystem.repository.inmemory.InMemoryShowSeatRepository;
import movieTicketBookingSystem.repository.inmemory.InMemoryTheaterRepository;
import movieTicketBookingSystem.search.SearchMovieByStatusStrategy;
import movieTicketBookingSystem.service.BookingService;
import movieTicketBookingSystem.service.MovieService;
import movieTicketBookingSystem.service.NotificationService;
import movieTicketBookingSystem.service.PaymentService;
import movieTicketBookingSystem.service.PricingService;
import movieTicketBookingSystem.service.SeatLockService;
import movieTicketBookingSystem.service.ShowService;
import movieTicketBookingSystem.service.TheaterService;

public class MovieTicketBookingSystemDemo {
    public static void main(String[] args) {
        MovieRepository movieRepository = new InMemoryMovieRepository();
        TheaterRepository theaterRepository = new InMemoryTheaterRepository();
        ShowRepository showRepository = new InMemoryShowRepository();
        ShowSeatRepository showSeatRepository = new InMemoryShowSeatRepository();
        BookingRepository bookingRepository = new InMemoryBookingRepository();
        PaymentRepository paymentRepository = new InMemoryPaymentRepository();
        CouponRepository couponRepository = new InMemoryCouponRepository();

        MovieService movieService = new MovieService(movieRepository);
        TheaterService theaterService = new TheaterService(theaterRepository);
        ShowService showService = new ShowService(showRepository, showSeatRepository, theaterRepository);
        SeatLockService seatLockService = new SeatLockService(showSeatRepository);
        PricingService pricingService = new PricingService(couponRepository, showSeatRepository);
        NotificationService notificationService = new NotificationService();
        BookingService bookingService = new BookingService(
                bookingRepository,
                showRepository,
                showSeatRepository,
                seatLockService,
                pricingService,
                notificationService);
        PaymentService paymentService = new PaymentService(paymentRepository, bookingRepository);

        Movie movie = new Movie(
                "MOV-1",
                "Interstellar",
                "A team explores space to find humanity a future home.",
                List.of("Matthew McConaughey", "Anne Hathaway"),
                169,
                8.7,
                "Sci-Fi",
                "English",
                LocalDate.of(2014, 11, 7),
                MovieStatus.NOW_SHOWING);
        movieService.addMovie(movie);

        List<Seat> seats = List.of(
                new Seat("A1", "SCR-1", "A", 1, SeatType.REGULAR),
                new Seat("A2", "SCR-1", "A", 2, SeatType.REGULAR),
                new Seat("B1", "SCR-1", "B", 1, SeatType.PREMIUM),
                new Seat("C1", "SCR-1", "C", 1, SeatType.RECLINER));
        Screen screen = new Screen("SCR-1", "TH-1", "Audi 1", seats);
        Theater theater = new Theater("TH-1", "PVR Orion", "Malleshwaram", "Bengaluru", List.of(screen));
        theaterService.addTheater(theater);

        Show show = new Show(
                "SHOW-1",
                movie.getMovieId(),
                theater.getTheaterId(),
                screen.getScreenId(),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusMinutes(movie.getDurationInMinutes()),
                250,
                ShowStatus.SCHEDULED);
        showService.createShow(show, seats);

        couponRepository.save(new Coupon(
                "SAVE10",
                DiscountType.PERCENTAGE,
                10,
                100,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(30),
                true));

        System.out.println("Now showing: "
                + movieService.browseMovies(new SearchMovieByStatusStrategy(MovieStatus.NOW_SHOWING)).size());
        System.out.println("Shows in Bengaluru: "
                + showService.findShowsByMovieAndCity(movie.getMovieId(), "Bengaluru").size());

        Booking booking = bookingService.initiateBooking("USER-1", show.getShowId(), List.of("A1", "A2"), "SAVE10");
        Payment payment = paymentService.pay(booking.getBookingId(), PaymentMethod.UPI);
        Booking confirmedBooking = bookingService.confirmBooking(booking.getBookingId());

        System.out.println("Payment status: " + payment.getStatus());
        System.out.println("Booking status: " + confirmedBooking.getStatus());
        System.out.println("Seat A1 status: " + showService.getSeatAvailability(show.getShowId()).stream()
                .filter(showSeat -> showSeat.getSeatId().equals("A1"))
                .findFirst()
                .map(showSeat -> showSeat.getStatus().name())
                .orElse(SeatBookingStatus.AVAILABLE.name()));

        try {
            bookingService.initiateBooking("USER-2", show.getShowId(), List.of("A1"), null);
        } catch (IllegalStateException exception) {
            System.out.println("Double booking prevented: " + exception.getMessage());
        }
    }
}
