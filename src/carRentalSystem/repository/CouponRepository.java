package carRentalSystem.repository;

import java.util.Optional;

import carRentalSystem.models.Coupon;

public interface CouponRepository {
    void save(Coupon coupon);

    Optional<Coupon> findByCode(String couponCode);
}
