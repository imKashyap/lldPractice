package carRentalSystem.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import carRentalSystem.models.Car;
import carRentalSystem.models.Coupon;
import carRentalSystem.repository.CouponRepository;

public class PricingService {
    private static final double TAX_PERCENTAGE = 18;

    private final CouponRepository couponRepository;

    public PricingService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public double calculateGrossAmount(Car car, LocalDateTime from, LocalDateTime to) {
        long rentalDays = Math.max(1, ChronoUnit.DAYS.between(from.toLocalDate(), to.toLocalDate()));
        return rentalDays * car.getCarType().getBaseValue();
    }

    public double calculatePayableAmount(Car car, LocalDateTime from, LocalDateTime to, String couponCode) {
        double grossAmount = calculateGrossAmount(car, from, to);
        double amountAfterDiscount = applyCoupon(grossAmount, couponCode);
        return amountAfterDiscount + amountAfterDiscount * TAX_PERCENTAGE / 100;
    }

    private double applyCoupon(double grossAmount, String couponCode) {
        if (couponCode == null || couponCode.isBlank()) {
            return grossAmount;
        }
        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid coupon: " + couponCode));
        return coupon.getStrategy().generateBillAmount(grossAmount, Double.parseDouble(coupon.getDiscountValue()));
    }
}
