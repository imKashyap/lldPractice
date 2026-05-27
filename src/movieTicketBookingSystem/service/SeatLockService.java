package movieTicketBookingSystem.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import movieTicketBookingSystem.model.SeatBookingStatus;
import movieTicketBookingSystem.model.ShowSeat;
import movieTicketBookingSystem.repository.ShowSeatRepository;

public class SeatLockService {
    private static final Duration LOCK_DURATION = Duration.ofMinutes(10);

    private final ShowSeatRepository showSeatRepository;
    private final Map<String, Object> locksByShowId = new ConcurrentHashMap<>();

    public SeatLockService(ShowSeatRepository showSeatRepository) {
        this.showSeatRepository = showSeatRepository;
    }

    public void lockSeats(String showId, List<String> seatIds, String userId) {
        Object showLock = locksByShowId.computeIfAbsent(showId, ignored -> new Object());
        synchronized (showLock) {
            List<ShowSeat> showSeats = showSeatRepository.findByShowIdAndSeatIds(showId, seatIds);
            if (showSeats.size() != seatIds.size()) {
                throw new IllegalArgumentException("One or more seats do not exist for show");
            }

            LocalDateTime now = LocalDateTime.now();
            for (ShowSeat showSeat : showSeats) {
                if (showSeat.isLockExpired(now)) {
                    showSeat.releaseLock();
                }
                if (showSeat.getStatus() != SeatBookingStatus.AVAILABLE) {
                    throw new IllegalStateException("Seat is not available: " + showSeat.getSeatId());
                }
            }

            LocalDateTime expiresAt = now.plus(LOCK_DURATION);
            for (ShowSeat showSeat : showSeats) {
                showSeat.lock(userId, expiresAt);
                showSeatRepository.save(showSeat);
            }
        }
    }

    public void releaseSeats(String showId, List<String> seatIds, String userId) {
        Object showLock = locksByShowId.computeIfAbsent(showId, ignored -> new Object());
        synchronized (showLock) {
            for (ShowSeat showSeat : showSeatRepository.findByShowIdAndSeatIds(showId, seatIds)) {
                if (showSeat.getStatus() == SeatBookingStatus.LOCKED
                        && userId.equals(showSeat.getLockedByUserId())) {
                    showSeat.releaseLock();
                    showSeatRepository.save(showSeat);
                }
            }
        }
    }

    public void expireLocks() {
        for (String showId : locksByShowId.keySet()) {
            expireLocks(showId);
        }
    }

    public void expireLocks(String showId) {
        LocalDateTime now = LocalDateTime.now();
        for (ShowSeat showSeat : showSeatRepository.findByShowId(showId)) {
            expireSeatIfNeeded(showSeat, now);
        }
    }

    private void expireSeatIfNeeded(ShowSeat showSeat, LocalDateTime now) {
        if (showSeat.isLockExpired(now)) {
            showSeat.releaseLock();
            showSeatRepository.save(showSeat);
        }
    }
}
