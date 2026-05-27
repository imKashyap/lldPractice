package carRentalSystem.utils.coupon;

public class FlatDiscountStrategy implements CouponDiscountStrategy {

    @Override
    public double generateBillAmount(double grossAmount, double discountValue) {
        return Math.max(0, grossAmount - discountValue);
    }

}
