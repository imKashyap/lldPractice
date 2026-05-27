package movieTicketBookingSystem.model;

public class Discount {
    private final String couponCode;
    private final double amount;

    public Discount(String couponCode, double amount) {
        this.couponCode = couponCode;
        this.amount = amount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public double getAmount() {
        return amount;
    }
}
