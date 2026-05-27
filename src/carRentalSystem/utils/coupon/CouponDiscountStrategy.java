package carRentalSystem.utils.coupon;

public interface CouponDiscountStrategy {
    public double generateBillAmount(double grossAmount, double discountValue);

}
