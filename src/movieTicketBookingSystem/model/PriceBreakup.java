package movieTicketBookingSystem.model;

public class PriceBreakup {
    private final double grossAmount;
    private final Discount discount;
    private final double payableAmount;

    public PriceBreakup(double grossAmount, Discount discount) {
        this.grossAmount = grossAmount;
        this.discount = discount;
        this.payableAmount = Math.max(0, grossAmount - discount.getAmount());
    }

    public double getGrossAmount() {
        return grossAmount;
    }

    public Discount getDiscount() {
        return discount;
    }

    public double getPayableAmount() {
        return payableAmount;
    }
}
