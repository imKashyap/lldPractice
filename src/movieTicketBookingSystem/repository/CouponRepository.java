package movieTicketBookingSystem.repository;

import java.util.Optional;

import movieTicketBookingSystem.model.Coupon;

public interface CouponRepository {
    void save(Coupon coupon);
    Optional<Coupon> findByCode(String couponCode);
}
