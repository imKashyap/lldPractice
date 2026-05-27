package carRentalSystem.utils.coupon;

public class PercentageDiscountStrategy implements CouponDiscountStrategy {

    private final double limit;

    public PercentageDiscountStrategy(double limit) {
        this.limit = limit;
    }

    @Override
    public double generateBillAmount(double grossAmount, double discountValue) {
        double discountedAmount = Math.min(limit, discountValue * grossAmount / 100);
        return Math.max(0, grossAmount - discountedAmount);
    }

}
