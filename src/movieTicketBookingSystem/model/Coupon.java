package movieTicketBookingSystem.model;

import java.time.LocalDate;

public class Coupon {
    private final String couponCode;
    private final DiscountType discountType;
    private final double discountValue;
    private final double maxDiscount;
    private final LocalDate validFrom;
    private final LocalDate validTo;
    private boolean active;

    public Coupon(
            String couponCode,
            DiscountType discountType,
            double discountValue,
            double maxDiscount,
            LocalDate validFrom,
            LocalDate validTo,
            boolean active) {
        this.couponCode = couponCode;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.maxDiscount = maxDiscount;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isValidOn(LocalDate date) {
        return active && !date.isBefore(validFrom) && !date.isAfter(validTo);
    }
}
