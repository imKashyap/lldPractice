package carRentalSystem.models;

import carRentalSystem.utils.coupon.CouponDiscountStrategy;

public class Coupon {
    private final String id;
    private final String couponCode;
    private final String discountValue;
    private final CouponDiscountStrategy strategy;

    public Coupon(String id, String couponCode, String discountValue, CouponDiscountStrategy strategy) {
        this.id = id;
        this.couponCode = couponCode;
        this.discountValue = discountValue;
        this.strategy = strategy;
    }

    public String getId() {
        return id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public CouponDiscountStrategy getStrategy() {
        return strategy;
    }

}
