package carRentalSystem.models;

public class Bill {
    private final String bookingId;
    private final double amount;

    public Bill(String bookingId, double amount) {
        this.bookingId = bookingId;
        this.amount = amount;
    }

    public String getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

}
