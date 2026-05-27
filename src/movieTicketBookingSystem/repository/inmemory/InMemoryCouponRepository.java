package movieTicketBookingSystem.repository.inmemory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import movieTicketBookingSystem.model.Coupon;
import movieTicketBookingSystem.repository.CouponRepository;

public class InMemoryCouponRepository implements CouponRepository {
    private final Map<String, Coupon> couponsByCode = new ConcurrentHashMap<>();

    @Override
    public void save(Coupon coupon) {
        couponsByCode.put(coupon.getCouponCode(), coupon);
    }

    @Override
    public Optional<Coupon> findByCode(String couponCode) {
        if (couponCode == null || couponCode.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(couponsByCode.get(couponCode));
    }
}
