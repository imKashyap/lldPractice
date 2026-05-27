package movieTicketBookingSystem.model;

import java.time.LocalDateTime;

public class Show {
    private final String showId;
    private final String movieId;
    private final String theaterId;
    private final String screenId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final double basePrice;
    private ShowStatus status;

    public Show(
            String showId,
            String movieId,
            String theaterId,
            String screenId,
            LocalDateTime startTime,
            LocalDateTime endTime,
            double basePrice,
            ShowStatus status) {
        this.showId = showId;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.screenId = screenId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.basePrice = basePrice;
        this.status = status;
    }

    public String getShowId() {
        return showId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTheaterId() {
        return theaterId;
    }

    public String getScreenId() {
        return screenId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public ShowStatus getStatus() {
        return status;
    }

    public void setStatus(ShowStatus status) {
        this.status = status;
    }
}
