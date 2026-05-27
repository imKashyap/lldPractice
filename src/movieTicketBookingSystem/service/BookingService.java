package movieTicketBookingSystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import movieTicketBookingSystem.model.Booking;
import movieTicketBookingSystem.model.BookingStatus;
import movieTicketBookingSystem.model.PriceBreakup;
import movieTicketBookingSystem.model.Show;
import movieTicketBookingSystem.model.ShowSeat;
import movieTicketBookingSystem.model.ShowStatus;
import movieTicketBookingSystem.repository.BookingRepository;
import movieTicketBookingSystem.repository.ShowRepository;
import movieTicketBookingSystem.repository.ShowSeatRepository;

public class BookingService {
    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final SeatLockService seatLockService;
    private final PricingService pricingService;
    private final NotificationService notificationService;
    private final Map<String, Object> locksByBookingId = new ConcurrentHashMap<>();

    public BookingService(
            BookingRepository bookingRepository,
            ShowRepository showRepository,
            ShowSeatRepository showSeatRepository,
            SeatLockService seatLockService,
            PricingService pricingService,
            NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.seatLockService = seatLockService;
        this.pricingService = pricingService;
        this.notificationService = notificationService;
    }

    public Booking initiateBooking(String userId, String showId, List<String> seatIds, String couponCode) {
        try {
            Show show = showRepository.findById(showId)
                    .orElseThrow(() -> new IllegalArgumentException("Show not found"));
            if (show.getStatus() != ShowStatus.SCHEDULED || !show.getStartTime().isAfter(LocalDateTime.now())) {
                throw new IllegalStateException("Show is not available for booking");
            }

            seatLockService.lockSeats(showId, seatIds, userId);
            PriceBreakup priceBreakup = pricingService.calculateAmount(showId, seatIds, couponCode);
            List<String> showSeatIds = showSeatRepository.findByShowIdAndSeatIds(showId, seatIds).stream()
                    .map(ShowSeat::getShowSeatId)
                    .toList();

            Booking booking = new Booking(
                    "BOOK-" + UUID.randomUUID(),
                    userId,
                    showId,
                    showSeatIds,
                    priceBreakup.getPayableAmount(),
                    priceBreakup.getDiscount().getAmount(),
                    BookingStatus.INITIATED,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(10));
            bookingRepository.save(booking);
            return booking;
        } catch (RuntimeException exception) {
            seatLockService.releaseSeats(showId, seatIds, userId);
            throw exception;
        }
    }

    public Booking confirmBooking(String bookingId) {
        synchronized (lockForBooking(bookingId)) {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
            if (booking.getStatus() != BookingStatus.INITIATED) {
                throw new IllegalStateException("Booking is not awaiting confirmation");
            }
            if (booking.isExpired(LocalDateTime.now())) {
                expireBooking(bookingId);
                throw new IllegalStateException("Booking has expired");
            }

            List<ShowSeat> showSeats = findShowSeatsForBooking(booking);
            LocalDateTime now = LocalDateTime.now();
            for (ShowSeat showSeat : showSeats) {
                if (!showSeat.hasValidLockFor(booking.getUserId(), now)) {
                    throw new IllegalStateException("Seat lock is no longer valid");
                }
            }

            for (ShowSeat showSeat : showSeats) {
                showSeat.markBooked();
                showSeatRepository.save(showSeat);
            }
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
            notificationService.sendBookingConfirmation(booking.getBookingId());
            return booking;
        }
    }

    public void cancelBooking(String bookingId) {
        synchronized (lockForBooking(bookingId)) {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
            if (booking.getStatus() == BookingStatus.CANCELLED) {
                return;
            }
            if (booking.getStatus() == BookingStatus.CONFIRMED
                    && showRepository.findById(booking.getShowId())
                            .map(show -> show.getStartTime().minusHours(2).isBefore(LocalDateTime.now()))
                            .orElse(false)) {
                throw new IllegalStateException("Booking cannot be cancelled within 2 hours of showtime");
            }

            for (ShowSeat showSeat : findShowSeatsForBooking(booking)) {
                showSeat.markAvailable();
                showSeatRepository.save(showSeat);
            }
            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);
            notificationService.sendCancellation(booking.getBookingId());
        }
    }

    public void expireBooking(String bookingId) {
        synchronized (lockForBooking(bookingId)) {
            Booking booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
            if (booking.getStatus() != BookingStatus.INITIATED) {
                return;
            }
            for (ShowSeat showSeat : findShowSeatsForBooking(booking)) {
                showSeat.releaseLock();
                showSeatRepository.save(showSeat);
            }
            booking.setStatus(BookingStatus.EXPIRED);
            bookingRepository.save(booking);
        }
    }

    private Object lockForBooking(String bookingId) {
        return locksByBookingId.computeIfAbsent(bookingId, ignored -> new Object());
    }

    private List<ShowSeat> findShowSeatsForBooking(Booking booking) {
        Set<String> bookedShowSeatIds = Set.copyOf(booking.getBookedSeats());
        return showSeatRepository.findByShowId(booking.getShowId()).stream()
                .filter(showSeat -> bookedShowSeatIds.contains(showSeat.getShowSeatId()))
                .toList();
    }
}
